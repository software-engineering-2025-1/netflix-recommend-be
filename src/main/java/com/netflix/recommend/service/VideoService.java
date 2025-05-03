package com.netflix.recommend.service;

public interface VideoService {

    void postHistory(Long userId, Long videoId);
}
