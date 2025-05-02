package com.netflix.recommend.repository;

import com.netflix.recommend.entity.PreferGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferGenreRepository extends JpaRepository<PreferGenre, Long> {
}
