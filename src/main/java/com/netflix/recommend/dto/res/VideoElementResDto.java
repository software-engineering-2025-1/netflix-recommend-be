package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoElementResDto {
    private Long id;
    private String title;
    private String director;
}
