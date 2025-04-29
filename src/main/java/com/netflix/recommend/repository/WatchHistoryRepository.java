package com.netflix.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netflix.recommend.entity.WatchHistory;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByUserId(Long userId);
}
