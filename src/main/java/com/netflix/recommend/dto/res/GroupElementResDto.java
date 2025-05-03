package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupElementResDto {
    private Long id;
    private String name;
}
