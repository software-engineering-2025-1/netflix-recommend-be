package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.VideoDetailResDto;

public interface VideoService {

    void postHistory(Long userId, Long videoId);

    VideoDetailResDto getVideoDetail(Long videoId);
}
