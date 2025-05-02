package com.netflix.recommend.repository;

import com.netflix.recommend.entity.PreferGenre;
import com.netflix.recommend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferGenreRepository extends JpaRepository<PreferGenre, Long> {

//    @Query("DELETE FROM PreferGenre pg WHERE pg.user = :user")
    void deleteByUser(User user);
}
