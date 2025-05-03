package com.netflix.recommend.service.impl;

import com.netflix.recommend.entity.History;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Video;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.HistoryRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.repository.VideoRepository;
import com.netflix.recommend.service.VideoService;
import lombok.RequiredArgsConstructor;
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

        if (historyRepository.existsByUserAndVideo(user, video)) throw new CustomException(ErrorCode.ALREADY_REGISTERED_HISTORY);

        historyRepository.save(History.builder()
                .user(user)
                .video(video)
                .build()
        );
    }
}
