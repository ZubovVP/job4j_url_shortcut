package ru.job4j.job4j_url_shortcut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.job4j_url_shortcut.models.Statistic;

import javax.transaction.Transactional;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 07.03.2021.
 */
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Statistic  st set st.total = st.total + 1 where  st.url = :url")
     void updateTotal(@Param("url")String url);
}
