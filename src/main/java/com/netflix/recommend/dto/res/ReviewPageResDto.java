package com.netflix.recommend.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewPageResDto {
    private Integer totalPage;
    private List<ReviewElementResDto> reviews;
}
