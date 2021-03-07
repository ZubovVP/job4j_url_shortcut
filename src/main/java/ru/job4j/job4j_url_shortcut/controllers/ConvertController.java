package ru.job4j.job4j_url_shortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_url_shortcut.models.Link;
import ru.job4j.job4j_url_shortcut.models.Url;
import ru.job4j.job4j_url_shortcut.services.ConvertService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 06.03.2021.
 */
@RestController
public class ConvertController {
    private ConvertService service;

    public ConvertController(ConvertService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity<Url> convert(@RequestBody Url url) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Url converted = new Url();
        Link link = new Link();
        link.setFullUrl(url.getUrl());
        Link result = this.service.convert(link, auth.getName());
        converted.setUrl(result.getShortUrl());
        return new ResponseEntity<>(
                converted,
                HttpStatus.OK
        );
    }

    @GetMapping("/redirect/{url}")
    public void redirect(@PathVariable String url, HttpServletResponse response) {
        Link link = new Link();
        link.setShortUrl(url);
        Url foundUrl = this.service.getFullUrl(link);
        if(foundUrl != null) {
            response.addHeader("HTTP_CODE", "302");
            response.addHeader("REDIRECT", foundUrl.getUrl());
            response.setStatus(302);
        } else {
            response.addHeader("HTTP_CODE", "404");
            response.setStatus(404);
        }
    }

}
