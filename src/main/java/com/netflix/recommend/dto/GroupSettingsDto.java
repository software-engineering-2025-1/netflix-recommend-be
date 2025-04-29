package com.netflix.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSettingsDto {
    private Long groupId;
    private String preferredGenres; // 예: "Action,Drama"
    private String ageRatingPreference; // 예: "PG-13"
}
