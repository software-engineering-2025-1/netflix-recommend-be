package com.netflix.recommend.repository;

import com.netflix.recommend.entity.History;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Boolean existsByUserAndVideo(User user, Video video);
}
