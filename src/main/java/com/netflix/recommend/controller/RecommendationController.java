package com.netflix.recommend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.recommend.entity.Content;
import com.netflix.recommend.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/preference/{userId}")
    public List<Content> recommendByPreferences(@PathVariable Long userId) {
        return recommendationService.recommendByPreferences(userId);
    }

    @GetMapping("/history/{userId}")
    public List<Content> recommendByWatchHistory(@PathVariable Long userId) {
        return recommendationService.recommendByWatchHistory(userId);
    }
}
