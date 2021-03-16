package ru.job4j.job4j_url_shortcut.models;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 07.03.2021.
 */
@Data
@Table(name = "statistics")
@Entity
public class Statistic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "total")
    private int total;
}
