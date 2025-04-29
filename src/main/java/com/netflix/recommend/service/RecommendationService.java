package com.netflix.recommend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Content;
import com.netflix.recommend.entity.WatchHistory;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.repository.WatchHistoryRepository;
import com.netflix.recommend.repository.ContentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final WatchHistoryRepository watchHistoryRepository;
    private final ContentRepository contentRepository;

    public List<Content> recommendByPreferences(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String genre = firstFromCsv(user.getPreferredGenres());
        String country = firstFromCsv(user.getPreferredCountries());
        String ageRating = user.getAgeRatingPreference();

        return contentRepository.findMatchingContent(
                genre.toLowerCase(),
                country.toLowerCase(),
                ageRating);
    }

    public List<Content> recommendByWatchHistory(Long userId) {
        List<WatchHistory> history = watchHistoryRepository.findByUserId(userId);
        if (history.isEmpty())
            return List.of();

        // 최근 시청한 콘텐츠 하나 기준
        Content recent = history.get(history.size() - 1).getContent();

        String genre = firstFromCsv(recent.getGenres());
        String country = recent.getCountry();
        String ageRating = recent.getAgeRating();

        List<Long> watchedIds = history.stream()
                .map(h -> h.getContent().getId())
                .toList();

        return contentRepository.findSimilarContentExcludingWatched(
                watchedIds,
                genre.toLowerCase(),
                country.toLowerCase(),
                ageRating);
    }

    private String firstFromCsv(String csv) {
        return (csv != null && csv.contains(",")) ? csv.split(",")[0] : (csv != null ? csv : "");
    }
}
