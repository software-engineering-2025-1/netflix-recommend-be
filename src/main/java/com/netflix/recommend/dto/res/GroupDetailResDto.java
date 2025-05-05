package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Group;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GroupDetailResDto {
    private Long id;
    private String name;
    private List<UserElementResDto> members;

    public static GroupDetailResDto from(Group group) {
        return GroupDetailResDto.builder()
                .id(group.getId())
                .name(group.getName())
                .members(group.getParticipants().stream().map(participant ->
                        UserElementResDto.from(participant.getUser())).toList())
                .build();
    }
}
