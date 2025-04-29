package com.netflix.recommend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.recommend.entity.Content;
import com.netflix.recommend.service.GroupRecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recommend/group")
@RequiredArgsConstructor
public class GroupRecommendationController {

    private final GroupRecommendationService groupRecommendationService;

    @GetMapping("/{groupId}")
    public List<Content> recommendForGroup(@PathVariable Long groupId) {
        return groupRecommendationService.recommendForGroup(groupId);
    }
}
