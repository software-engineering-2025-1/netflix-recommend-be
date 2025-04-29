package com.netflix.recommend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.netflix.recommend.dto.WatchHistoryDto;
import com.netflix.recommend.entity.Content;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.WatchHistory;
import com.netflix.recommend.repository.WatchHistoryRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.repository.ContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public void addWatchHistory(WatchHistoryDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Content content = contentRepository.findById(dto.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        WatchHistory history = new WatchHistory();
        history.setUser(user);
        history.setContent(content);
        history.setWatchedAt(dto.getWatchedAt() != null ? dto.getWatchedAt() : LocalDateTime.now());

        watchHistoryRepository.save(history);
    }

    public List<WatchHistoryDto> getUserWatchHistory(Long userId) {
        return watchHistoryRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private WatchHistoryDto toDto(WatchHistory history) {
        return new WatchHistoryDto(
                history.getUser().getId(),
                history.getContent().getId(),
                history.getWatchedAt());
    }
}
