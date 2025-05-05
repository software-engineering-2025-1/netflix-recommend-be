package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserElementResDto {
    private Long id;
    private String name;

    public static UserElementResDto from(User user) {
        return UserElementResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
