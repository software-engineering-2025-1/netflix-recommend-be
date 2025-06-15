package com.netflix.recommend.unit;

import com.netflix.recommend.dto.res.VideoDetailResDto;
import com.netflix.recommend.dto.res.VideoPageResDto;
import com.netflix.recommend.entity.History;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.entity.Video;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.HistoryRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.repository.VideoRepository;
import com.netflix.recommend.service.impl.VideoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoServiceImpl videoService;

    @Mock private VideoRepository videoRepository;
    @Mock private UserRepository userRepository;
    @Mock private HistoryRepository historyRepository;

    @Test
    @DisplayName("영상 시청기록 등록 - 정상 등록")
    void 영상_아이디를_통해_시청기록을_등록할_수_있다() {
        // given
        Long userId = 1L;
        Long videoId = 1L;

        User user = new User();
        Video video = new Video();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(videoRepository.getReferenceById(videoId)).thenReturn(video);
        when(historyRepository.existsByUserAndVideo(user, video)).thenReturn(false);

        // when
        videoService.postHistory(userId, videoId);

        // then
        verify(historyRepository).save(any(History.class));
    }

    @Test
    @DisplayName("영상 시청기록 등록 - 이미 등록된 기록 예외")
    void 이미_등록한_시청기록을_등록할_경우_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long videoId = 1L;

        User user = new User();
        Video video = new Video();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(videoRepository.getReferenceById(videoId)).thenReturn(video);
        when(historyRepository.existsByUserAndVideo(user, video)).thenReturn(true);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            videoService.postHistory(userId, videoId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.ALREADY_REGISTERED_HISTORY);
    }

    @Test
    @DisplayName("영상 정보 조회 - 정상 조회")
    void 영상_아이디를_통해_정보를_조회할_수_있다() {
        // given
        Long videoId = 1L;

        Video video = new Video();
        ReflectionTestUtils.setField(video, "title", "title1");
        ReflectionTestUtils.setField(video, "type", Type.MOVIE);
        ReflectionTestUtils.setField(video, "rating", Rate.NC_17);
        ReflectionTestUtils.setField(video, "genres", Set.of());
        ReflectionTestUtils.setField(video, "countries", Set.of());

        when(videoRepository.findVideoDetailByIdFetch(videoId)).thenReturn(Optional.of(video));

        // when
        VideoDetailResDto videoDetail = videoService.getVideoDetail(videoId);

        // then
        assertThat(videoDetail.getTitle()).isEqualTo("title1");
        assertThat(videoDetail.getType()).isEqualTo(Type.MOVIE.getName());
        assertThat(videoDetail.getRating()).isEqualTo(Rate.NC_17.getName());
    }

    @Test
    @DisplayName("영상 정보 조회 - 존재하지 않는 영상 예외")
    void 존재하지_않는_영상의_정보를_조회할_경우_예외가_발생한다() {
        // given
        Long videoId = 1L;

        when(videoRepository.findVideoDetailByIdFetch(videoId)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            videoService.getVideoDetail(videoId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.CANNOT_FIND_VIDEO);
    }

    @Test
    @DisplayName("영상 리스트 조회 - 정상 조회")
    void 페이지네이션을_통해_영상_리스트를_조회할_수_있다() {
        // given
        Long videoId = 1L;
        PageRequest pageable = PageRequest.of(0, 1);

        Video video = new Video();
        ReflectionTestUtils.setField(video, "title", "title1");

        when(videoRepository.findAllByGenreAndRateAndTypeAndKeywordWithPaging(null, null, null, null, pageable))
                .thenReturn(new PageImpl<>(List.of(video), pageable, 1));

        // when
        VideoPageResDto videoListWithFiltering = videoService.getVideoListWithFiltering(null, null, null, null, pageable);

        assertThat(videoListWithFiltering.getTotalPage()).isEqualTo(1);
        assertThat(videoListWithFiltering.getVideos().size()).isEqualTo(1);
        assertThat(videoListWithFiltering.getVideos().get(0).getTitle()).isEqualTo("title1");
    }
}