package ru.job4j.job4j_url_shortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.job4j_url_shortcut.models.Statistic;
import ru.job4j.job4j_url_shortcut.repositories.StatisticRepository;

import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 07.03.2021.
 */
@RestController
public class StatisticController {
    private StatisticRepository repository;

    public StatisticController(StatisticRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<Statistic>> getStat() {
       List<Statistic> result = repository.findAll();
        return new ResponseEntity<>(
                result,
                result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }
}
