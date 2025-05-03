package com.netflix.recommend.service;

public interface FollowService {
    void postFollow(Long senderId, Long receiverId);
}
