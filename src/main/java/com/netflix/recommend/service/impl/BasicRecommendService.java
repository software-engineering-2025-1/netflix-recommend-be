package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.res.VideoElementResDto;
import com.netflix.recommend.repository.VideoRepository;
import com.netflix.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicRecommendService implements RecommendService {

    private final VideoRepository videoRepository;

    @Override
    public List<VideoElementResDto> recommendForIndividual(Long userId) {
        return videoRepository.findTop10OrderById()
                .stream().map(VideoElementResDto::from).toList();
    }

    @Override
    public List<VideoElementResDto> recommendForGroup(Long groupId) {
        return videoRepository.findTop10OrderById()
                .stream().map(VideoElementResDto::from).toList();
    }
}
