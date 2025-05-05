package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Review;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class ReviewPageResDto {
    private Integer totalPage;
    private List<ReviewElementResDto> reviews;

    public static ReviewPageResDto from(Page<Review> reviewPage) {
        return ReviewPageResDto.builder()
                .totalPage(reviewPage.getTotalPages())
                .reviews(reviewPage.getContent().stream().map(ReviewElementResDto::from).toList())
                .build();
    }
}
