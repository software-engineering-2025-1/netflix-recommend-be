package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Review;
import com.netflix.recommend.entity.Video;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class VideoPageResDto {
    private Integer totalPage;
    private List<VideoElementResDto> videos;

    public static VideoPageResDto from(Page<Video> videoPage) {
        return VideoPageResDto.builder()
                .totalPage(videoPage.getTotalPages())
                .videos(videoPage.getContent().stream().map(VideoElementResDto::from).toList())
                .build();
    }
}
