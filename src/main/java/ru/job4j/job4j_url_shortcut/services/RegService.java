package ru.job4j.job4j_url_shortcut.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.generator.Generator;
import ru.job4j.job4j_url_shortcut.models.Site;
import ru.job4j.job4j_url_shortcut.repositories.SiteRepository;

import java.util.regex.Pattern;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 04.03.2021.
 */
@Slf4j
@Service
public class RegService {
    private Generator generator;
    private SiteRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegService(Generator generator, SiteRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.generator = generator;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Site registration(Site site) {
        Site resultSite = null;
        if (checkNameOfSite(site)) {
            String login = this.generator.generate(6);
            while (this.repository.findByName(login) != null) {
                login = this.generator.generate(6);
            }
            site.setLogin(login);
            String password = this.generator.generate(10);
            site.setPassword(this.bCryptPasswordEncoder.encode(password));
            try {
                resultSite = this.repository.save(site);
                resultSite.setPassword(password);
                log.info("Successfully register site - {}", site.getName());
            } catch (Exception e) {
                log.error("Name is already exist - {}", site.getName());
            }
        } else {
            log.error("Wrong name of site - {}", site.getName());
        }
        return resultSite;
    }

    private boolean checkNameOfSite(Site site) {
        return Pattern.matches("((http|https|)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.[ru|com|ua|bu]+", site.getName());

    }

}
