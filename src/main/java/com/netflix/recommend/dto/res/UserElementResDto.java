package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserElementResDto {
    private Long id;
    private String name;
}
