package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReviewElementResDto {
    private Long id;
    private String author;
    private String comment;
    private LocalDateTime createdAt;
}
