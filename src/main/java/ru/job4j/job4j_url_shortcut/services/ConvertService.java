package ru.job4j.job4j_url_shortcut.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.generator.Generator;
import ru.job4j.job4j_url_shortcut.models.Link;
import ru.job4j.job4j_url_shortcut.models.Statistic;
import ru.job4j.job4j_url_shortcut.models.Url;
import ru.job4j.job4j_url_shortcut.repositories.LinkRepository;
import ru.job4j.job4j_url_shortcut.repositories.SiteRepository;
import ru.job4j.job4j_url_shortcut.repositories.StatisticRepository;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 06.03.2021.
 */
@Slf4j
@Service
public class ConvertService {
    private LinkRepository repository;
    private Generator generator;
    private SiteRepository siteRepository;
    private StatisticRepository statisticRepository;

    @Autowired
    public ConvertService(Generator generator, LinkRepository linkRepository, SiteRepository siteRepository, StatisticRepository statisticRepository) {
        this.generator = generator;
        this.repository = linkRepository;
        this.siteRepository = siteRepository;
        this.statisticRepository = statisticRepository;
    }

    public Link convert(Link site, String login) {
        Link result = this.findByFullUrl(site);
        if (result == null) {
            site.setShortUrl(this.generator.generate(6));
            try {
                site.setSite(siteRepository.findByLogin(login));
                result = this.repository.save(site);
                Thread thread = new Thread(() -> createStat(site.getFullUrl()));
                thread.start();
                log.info("Successfully create and save short Url for site - {}", site.getFullUrl());
            } catch (Exception e) {
                log.error("Wrong create and save short Url for site - {}", site.getFullUrl());
            }
        }
        return result;
    }

    public Url getFullUrl(Link site) {
        Link resultLink = findByShortUrl(site);
        Url result = new Url();
        if (resultLink != null) {
            Thread thread = new Thread(() -> updateStatistic(resultLink.getFullUrl()));
            thread.start();
            result.setUrl(resultLink.getFullUrl());
        }
        return result;
    }

    private Link findByShortUrl(Link site) {
        Link result = this.repository.findByShortUrl(site.getShortUrl());
        log.info("Successfully find link use full url - {}", site.getShortUrl());
        return result;
    }

    private Link findByFullUrl(Link site) {
        Link result = this.repository.findByFullUrl(site.getFullUrl());
        log.info("Successfully find link use full url - {}", site.getFullUrl());
        return result;
    }

    private void updateStatistic(String url) {
        Statistic statistic = this.statisticRepository.findByUrl(url);
        if (statistic != null) {
            statistic.setTotal(statistic.getTotal() + 1);
            this.statisticRepository.save(statistic);
            log.info("Successfully update statistic for url - {} , total - {}", url, statistic.getTotal());
        } else {
            log.error("Don't found statistic with url - {}", url);
        }
    }

    private void createStat(String url) {
        Statistic statistic = new Statistic();
        statistic.setUrl(url);
        this.statisticRepository.save(statistic);
        log.info("Successfully create statistic for url - {}", url);
    }
}
