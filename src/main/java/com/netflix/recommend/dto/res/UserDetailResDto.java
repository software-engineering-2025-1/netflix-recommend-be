package com.netflix.recommend.dto.res;

import com.netflix.recommend.enums.Country;
import com.netflix.recommend.enums.Genre;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserDetailResDto {
    private Long id;
    private String name;
    private Integer age;
    private Country country;
    private List<Genre> genres;
    private List<VideoElementResDto> histories;
}
