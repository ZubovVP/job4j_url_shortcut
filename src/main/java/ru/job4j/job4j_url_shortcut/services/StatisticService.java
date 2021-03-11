package ru.job4j.job4j_url_shortcut.services;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.models.Statistic;
import ru.job4j.job4j_url_shortcut.repositories.StatisticRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 11.03.2021.
 */
@Slf4j
@Service
@ThreadSafe
public class StatisticService {
    private final StatisticRepository statisticRepository;
    @GuardedBy("queue")
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private volatile boolean flag = true;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public void updateStatistic(String url) {
        checkPool();
        this.queue.add(() -> {
            Statistic statistic = statisticRepository.findByUrl(url);
            if (statistic != null) {
                AtomicInteger count = new AtomicInteger(statistic.getTotal());
                statistic.setTotal(count.addAndGet(1));
                statisticRepository.save(statistic);
                log.info("Successfully update statistic for url - {} , total - {}", url, count.get());
                return;
            }
            log.error("Don't found statistic with url - {}", url);
        });
    }

    public void createStat(String url) {
        checkPool();
        this.queue.add(() -> {
            Statistic statistic = new Statistic();
            statistic.setUrl(url);
            statisticRepository.save(statistic);
            log.info("Successfully create statistic for url - {}", url);
        });
    }

    private void checkPool() {
        if (!this.threadPool.isShutdown()) {
            IntStream.range(0, Runtime.getRuntime().availableProcessors())
                    .forEach(i -> threadPool.submit(() -> {
                        while (this.flag) {
                            try {
                                this.queue.poll(1, TimeUnit.HOURS).run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }));
        }
    }
}
