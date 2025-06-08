package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.PreferGenre;
import com.netflix.recommend.entity.User;
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
    private String country;
    private List<String> genres;
    private List<VideoElementResDto> histories;

    public static UserDetailResDto from(User user) {
        return UserDetailResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .country(user.getCountry() != null ? user.getCountry().getName() : null)
                .genres(user.getGenres().stream().map(preferGenre -> preferGenre.getGenre().getName()).toList())
                .histories(user.getHistories().stream().map(history -> VideoElementResDto.from(history.getVideo())).toList())
                .build();
    }
}
