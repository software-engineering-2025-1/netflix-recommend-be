package com.netflix.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String preferredGenres;
    private String preferredCountries;
    private String ageRatingPreference;
}
