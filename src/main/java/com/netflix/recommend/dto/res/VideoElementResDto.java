package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Video;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoElementResDto {
    private Long id;
    private String title;
    private String director;

    public static VideoElementResDto from(Video video) {
        return VideoElementResDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .director(video.getDirector())
                .build();
    }
}
