package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.VideoElementResDto;

import java.util.List;

public interface RecommendService {

    List<VideoElementResDto> recommendForIndividual(Long userId);

    List<VideoElementResDto> recommendForGroup(Long groupId);
}
