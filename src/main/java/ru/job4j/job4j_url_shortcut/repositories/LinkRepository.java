package ru.job4j.job4j_url_shortcut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.job4j_url_shortcut.models.Link;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 06.03.2021.
 */
public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findByFullUrl(String fullUrl);
    Link findByShortUrl(String shortUrl);
}
