package ru.job4j.job4j_url_shortcut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.job4j_url_shortcut.models.Statistic;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 07.03.2021.
 */
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

    Statistic findByUrl(String url);
}
