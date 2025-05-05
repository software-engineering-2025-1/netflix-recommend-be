package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.VideoDetailResDto;
import com.netflix.recommend.dto.res.VideoPageResDto;
import com.netflix.recommend.enums.Genre;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import org.springframework.data.domain.Pageable;

public interface VideoService {

    void postHistory(Long userId, Long videoId);

    VideoDetailResDto getVideoDetail(Long videoId);

    VideoPageResDto getVideoListWithFiltering(Genre genre, Rate rate, Type type, Pageable pageable);
}
