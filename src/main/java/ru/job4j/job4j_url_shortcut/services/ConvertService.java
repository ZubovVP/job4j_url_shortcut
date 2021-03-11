package ru.job4j.job4j_url_shortcut.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_url_shortcut.models.Link;
import ru.job4j.job4j_url_shortcut.models.Url;
import ru.job4j.job4j_url_shortcut.repositories.LinkRepository;
import ru.job4j.job4j_url_shortcut.repositories.SiteRepository;

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
    private SiteRepository siteRepository;
    private StatisticService statisticService;


    @Autowired
    public ConvertService(LinkRepository linkRepository, SiteRepository siteRepository, StatisticService statisticService) {
        this.repository = linkRepository;
        this.siteRepository = siteRepository;
        this.statisticService = statisticService;
    }

    public Link convert(Link site, String login) {
        Link result = this.findByFullUrl(site);
        if (result == null) {
            site.setShortUrl(RandomStringUtils.randomAlphabetic(6));
            try {
                site.setSite(siteRepository.findByLogin(login));
                result = this.repository.save(site);
                this.statisticService.createStat(site.getFullUrl());
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
            result.setUrl(resultLink.getFullUrl());
            this.statisticService.updateStatistic(result.getUrl());
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

}
