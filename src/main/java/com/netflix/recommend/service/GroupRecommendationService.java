package com.netflix.recommend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Content;
import com.netflix.recommend.entity.GroupEntity;
import com.netflix.recommend.entity.GroupMember;
import com.netflix.recommend.entity.GroupSettings;
import com.netflix.recommend.repository.ContentRepository;
import com.netflix.recommend.repository.GroupMemberRepository;
import com.netflix.recommend.repository.GroupRepository;
import com.netflix.recommend.repository.GroupSettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupRecommendationService {

    private final ContentRepository contentRepository;
    private final GroupSettingsRepository groupSettingsRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    public List<Content> recommendForGroup(Long groupId) {
        // 그룹이 존재하는지 먼저 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // 그룹 설정이 있으면 이를 우선 사용
        Optional<GroupSettings> settingsOpt = groupSettingsRepository.findByGroup(group);
        String genre;
        String ageRating;
        String country = ""; // 그룹 설정에는 국가정보가 없으므로 빈 문자열 처리

        if (settingsOpt.isPresent()) {
            GroupSettings settings = settingsOpt.get();
            genre = firstFromCsv(settings.getPreferredGenres());
            ageRating = settings.getAgeRatingPreference();
        } else {
            // 그룹 설정이 없으면 그룹 구성원 중 첫 번째 사용자의 개인 선호를 사용
            List<GroupMember> members = groupMemberRepository.findByGroup_Id(groupId);
            if (members.isEmpty()) {
                throw new RuntimeException("No group members found.");
            }
            User user = members.get(0).getUser();
            genre = firstFromCsv(user.getPreferredGenres());
            ageRating = user.getAgeRatingPreference();
        }

        // 콘텐츠 추천: 설정 값(또는 사용자 선호)을 기준으로 매칭
        return contentRepository.findMatchingContent(
                genre.toLowerCase(),
                country.toLowerCase(), // 만약 국가 필터링을 원한다면 여기서 수정 가능
                ageRating);
    }

    // CSV 문자열에서 첫 번째 값을 반환 (예: "Action,Drama" → "Action")
    private String firstFromCsv(String csv) {
        return (csv != null && csv.contains(",")) ? csv.split(",")[0].trim() : (csv != null ? csv.trim() : "");
    }
}
