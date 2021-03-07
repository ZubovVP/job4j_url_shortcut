package ru.job4j.job4j_url_shortcut.models;


import lombok.Data;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 05.03.2021.
 */
@Data
public class StatusRegister {
    private boolean registration;
    private String login;
    private String password;
}
