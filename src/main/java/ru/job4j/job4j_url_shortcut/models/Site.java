package ru.job4j.job4j_url_shortcut.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 03.03.2021.
 */
@Data
@Table(name = "sites")
@Entity
@JsonIgnoreProperties(
        value = {"created"},
        allowGetters = true
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Site extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "site")
    private List<Link> links;
}
