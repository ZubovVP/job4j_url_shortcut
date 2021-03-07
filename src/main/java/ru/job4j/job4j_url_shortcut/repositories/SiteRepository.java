package ru.job4j.job4j_url_shortcut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.job4j_url_shortcut.models.Site;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 04.03.2021.
 */
public interface SiteRepository extends JpaRepository<Site, Integer> {
    Site findByName(String name);

    Site findByLogin(String login);
}
