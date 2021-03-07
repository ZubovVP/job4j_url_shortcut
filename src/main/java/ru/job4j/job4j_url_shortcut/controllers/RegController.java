package ru.job4j.job4j_url_shortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_url_shortcut.models.Site;
import ru.job4j.job4j_url_shortcut.models.StatusRegister;
import ru.job4j.job4j_url_shortcut.services.RegService;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 04.03.2021.
 */
@RestController
public class RegController {
    private RegService service;

    public RegController(RegService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<StatusRegister> create(@RequestBody Site site) {
        Site result = this.service.registration(site);
        StatusRegister status = new StatusRegister();
        if (result != null) {
            status.setRegistration(true);
            status.setLogin(result.getLogin());
            status.setPassword(result.getPassword());
        } else {
            status.setRegistration(false);
        }
        return new ResponseEntity<>(
                status,
                result != null ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE
        );
    }
}
