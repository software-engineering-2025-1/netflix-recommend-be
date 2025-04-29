package com.netflix.recommend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.recommend.dto.GroupSettingsDto;
import com.netflix.recommend.service.GroupSettingsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/group-settings")
@RequiredArgsConstructor
public class GroupSettingController {

    private final GroupSettingsService groupSettingsService;

    @PostMapping
    public ResponseEntity<String> saveOrUpdate(@RequestBody GroupSettingsDto dto) {
        groupSettingsService.saveOrUpdateGroupSettings(dto);
        return ResponseEntity.ok("Group settings saved or updated.");
    }

    @GetMapping("/{groupId}")
    public GroupSettingsDto getSettings(@PathVariable Long groupId) {
        return groupSettingsService.getSettingsByGroupId(groupId);
    }
}
