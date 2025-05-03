package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GroupDetailResDto {
    private Long id;
    private String name;
    private List<UserElementResDto> members;
}
