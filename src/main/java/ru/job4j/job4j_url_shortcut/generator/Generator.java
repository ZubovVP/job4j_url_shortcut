package ru.job4j.job4j_url_shortcut.generator;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 04.03.2021.
 */
public class Generator {
    private static Generator ourInstance = new Generator();

    public static Generator getInstance() {
        return ourInstance;
    }

    private Generator() {
    }

    public String generate(int count){
        return RandomStringUtils.randomAlphabetic(count);
    }
}
