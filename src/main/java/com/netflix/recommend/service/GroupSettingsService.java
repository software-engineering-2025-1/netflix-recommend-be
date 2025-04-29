package com.netflix.recommend.service;

import org.springframework.stereotype.Service;

import com.netflix.recommend.dto.GroupSettingsDto;
import com.netflix.recommend.entity.GroupEntity;
import com.netflix.recommend.entity.GroupSettings;
import com.netflix.recommend.repository.GroupRepository;
import com.netflix.recommend.repository.GroupSettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupSettingsService {

        private final GroupRepository groupRepository;
        private final GroupSettingsRepository groupSettingsRepository;

        public void saveOrUpdateGroupSettings(GroupSettingsDto dto) {
                GroupEntity group = groupRepository.findById(dto.getGroupId())
                                .orElseThrow(() -> new RuntimeException("Group not found"));

                GroupSettings settings = groupSettingsRepository.findByGroup_Id(dto.getGroupId())
                                .orElse(new GroupSettings());

                settings.setGroup(group);
                settings.setPreferredGenres(dto.getPreferredGenres());
                settings.setAgeRatingPreference(dto.getAgeRatingPreference());

                groupSettingsRepository.save(settings);
        }

        public GroupSettingsDto getSettingsByGroupId(Long groupId) {
                GroupSettings settings = groupSettingsRepository.findByGroup_Id(groupId)
                                .orElseThrow(() -> new RuntimeException("Group settings not found"));

                return new GroupSettingsDto(
                                settings.getGroup().getId(),
                                settings.getPreferredGenres(),
                                settings.getAgeRatingPreference());
        }
}
