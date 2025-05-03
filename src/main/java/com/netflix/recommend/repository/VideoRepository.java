package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v left join fetch v.genres where v.id = :videoId")
    Optional<Video> findVideoDetailByIdFetch(Long videoId);
}
