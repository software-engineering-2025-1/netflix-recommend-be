package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Video;
import com.netflix.recommend.enums.Genre;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v left join fetch v.genres left join fetch v.countries where v.id = :videoId")
    Optional<Video> findVideoDetailByIdFetch(Long videoId);

    @Query("select distinct v from Video v left join v.genres vg " +
            "where (:type is null or v.type = :type) " +
            "and (:rate is null or v.rating = :rate) " +
            "and (:genre is null or :genre = vg.genre) " +
            "and (:keyword is null or v.title like concat('%', :keyword, '%'))")
    Page<Video> findAllByGenreAndRateAndTypeAndKeywordWithPaging(Genre genre, Rate rate, Type type, String keyword, Pageable pageable);

    List<Video> findTop10ByOrderById();
}
