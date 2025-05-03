package com.netflix.recommend.service;

import com.netflix.recommend.dto.req.ReviewDetailReqDto;
import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.dto.res.GroupElementResDto;
import com.netflix.recommend.dto.res.ReviewPageResDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {

    void postGroup(Long userId, String title);

    void joinGroup(Long userId, Long groupId);

    GroupDetailResDto getGroupDetail(Long groupId);

    List<GroupElementResDto> searchGroupList(String keyword);

    ReviewPageResDto getReviewWithPaging(Long userId, Long groupId, Pageable pageable);

    void postReview(Long userId, Long groupId, ReviewDetailReqDto reviewDetailReqDto);
}
