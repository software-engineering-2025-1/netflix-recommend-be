package com.netflix.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netflix.recommend.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

        @Query("SELECT c FROM Content c WHERE " +
                        "(LOWER(c.genres) LIKE %:genre% OR LOWER(c.country) LIKE %:country%) " +
                        "AND c.ageRating = :ageRating")
        List<Content> findMatchingContent(
                        @Param("genre") String genre,
                        @Param("country") String country,
                        @Param("ageRating") String ageRating);

        @Query("SELECT c FROM Content c WHERE c.id NOT IN :excludeIds AND " +
                        "(LOWER(c.genres) LIKE %:genre% OR LOWER(c.country) LIKE %:country%) " +
                        "AND c.ageRating = :ageRating")
        List<Content> findSimilarContentExcludingWatched(
                        @Param("excludeIds") List<Long> excludeIds,
                        @Param("genre") String genre,
                        @Param("country") String country,
                        @Param("ageRating") String ageRating);

}
