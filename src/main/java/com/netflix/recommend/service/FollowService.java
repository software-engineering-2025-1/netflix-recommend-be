package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.UserElementResDto;

import java.util.List;

public interface FollowService {
    void postFollow(Long senderId, Long receiverId);

    List<UserElementResDto> getFollowerList(Long userId);

    List<UserElementResDto> getFollowingList(Long userId);
}
