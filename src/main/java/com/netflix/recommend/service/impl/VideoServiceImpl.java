package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.res.VideoDetailResDto;
import com.netflix.recommend.dto.res.VideoPageResDto;
import com.netflix.recommend.entity.History;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Video;
import com.netflix.recommend.enums.Genre;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.HistoryRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.repository.VideoRepository;
import com.netflix.recommend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Override
    public void postHistory(Long userId, Long videoId) {
        User user = userRepository.getReferenceById(userId);
        Video video = videoRepository.getReferenceById(videoId);

        if (historyRepository.existsByUserAndVideo(user, video))
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_HISTORY);

        historyRepository.save(History.builder()
                .user(user)
                .video(video)
                .build()
        );
    }

    @Override
    public VideoDetailResDto getVideoDetail(Long videoId) {
        return VideoDetailResDto.from(
                videoRepository.findVideoDetailByIdFetch(videoId)
                        .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_VIDEO))
        );
    }

    @Override
    public VideoPageResDto getVideoListWithFiltering(Genre genre, Rate rate, Type type, String keyword, Pageable pageable) {
        return VideoPageResDto.from(videoRepository.findAllByGenreAndRateAndTypeAndKeywordWithPaging(genre, rate, type, keyword, pageable));
    }
}
