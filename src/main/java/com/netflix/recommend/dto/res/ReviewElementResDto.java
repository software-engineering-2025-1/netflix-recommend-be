package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Review;
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

    public static ReviewElementResDto from(Review review) {
        return ReviewElementResDto.builder()
                .id(review.getId())
                .author(review.getUser().getName())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
