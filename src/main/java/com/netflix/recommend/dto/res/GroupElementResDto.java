package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Group;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupElementResDto {
    private Long id;
    private String name;

    public static GroupElementResDto from(Group group) {
        return GroupElementResDto.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}
