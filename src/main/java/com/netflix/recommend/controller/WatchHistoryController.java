package com.netflix.recommend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.recommend.dto.WatchHistoryDto;
import com.netflix.recommend.service.WatchHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/watch-history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @PostMapping
    public ResponseEntity<String> addWatchHistory(@RequestBody WatchHistoryDto dto) {
        watchHistoryService.addWatchHistory(dto);
        return ResponseEntity.ok("Watch history added.");
    }

    @GetMapping("/user/{userId}")
    public List<WatchHistoryDto> getWatchHistory(@PathVariable Long userId) {
        return watchHistoryService.getUserWatchHistory(userId);
    }
}
