package ru.job4j.job4j_url_shortcut.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.models.Site;
import ru.job4j.job4j_url_shortcut.repositories.SiteRepository;

import java.util.Iterator;

import static java.util.Collections.emptyList;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 24.02.2021.
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SiteRepository repository;

    public UserDetailsServiceImpl(SiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Site result = null;
        Iterator<Site> persons = this.repository.findAll().iterator();
        while (persons.hasNext()) {
            Site p = persons.next();
            if (p.getLogin().equals(s)) {
                result = p;
                break;
            }
        }

        if (result == null) {
            throw new UsernameNotFoundException("Login - " + s);
        }
        log.info("IN loadUserByUsername - login with name : {} successfully loaded", s);
        return new User(result.getLogin(), result.getPassword(), emptyList());
    }
}
