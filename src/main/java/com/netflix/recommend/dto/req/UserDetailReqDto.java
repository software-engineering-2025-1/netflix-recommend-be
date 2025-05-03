package com.netflix.recommend.dto.req;

import com.netflix.recommend.enums.Country;
import com.netflix.recommend.enums.Genre;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserDetailReqDto {
    private String name;
    private Integer age;
    private Country country;
    private Set<Genre> genres;
}
