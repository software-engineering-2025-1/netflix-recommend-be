package com.netflix.recommend.unit;

import com.netflix.recommend.dto.res.UserElementResDto;
import com.netflix.recommend.entity.Follow;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.FollowRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.impl.FollowServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @InjectMocks
    private FollowServiceImpl followService;

    @Mock private FollowRepository followRepository;
    @Mock private UserRepository userRepository;

    @Test
    @DisplayName("팔로우 등록 - 정상 팔로우")
    void 신청자와_대상유저를_지정하면_정상적으로_팔로우할_수_있다() {
        // given
        Long senderId = 1L;
        Long receiverId = 2L;

        User user = new User();

        when(userRepository.getReferenceById(senderId)).thenReturn(user);
        when(userRepository.getReferenceById(receiverId)).thenReturn(user);
        when(followRepository.existsBySenderAndReceiver(user, user)).thenReturn(false);

        // when
        followService.postFollow(senderId, receiverId);

        // then
        verify(followRepository).save(any(Follow.class));
    }

    @Test
    @DisplayName("팔로우 등록 - 본인 팔로우 예외")
    void 신청자와_대상자가_동일할_경우_예외가_발생한다() {
        // given
        Long sameId = 1L;

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            followService.postFollow(sameId, sameId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.CANNOT_FOLLOW_MYSELF);
    }

    @Test
    @DisplayName("팔로우 등록 - 이미 등록된 팔로우 예외")
    void 이미_등록된_팔로우일_경우_예외가_발생한다() {
        // given
        Long senderId = 1L;
        Long receiverId = 2L;

        User user = new User();

        when(userRepository.getReferenceById(senderId)).thenReturn(user);
        when(userRepository.getReferenceById(receiverId)).thenReturn(user);
        when(followRepository.existsBySenderAndReceiver(user, user)).thenReturn(true);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            followService.postFollow(senderId, receiverId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.ALREADY_FOLLOWED_USER);
    }

    @Test
    @DisplayName("팔로워 조회 - 정상 조회")
    void 사용자_아이디를_통해_팔로워_리스트를_조회할_수_있다() {
        // given
        Long userId = 1L;

        User sender = new User();
        sender.updateDetail("sender", null, null);
        User receiver = new User();
        receiver.updateDetail("receiver", null, null);

        Follow follow = Follow.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        when(followRepository.findFollowersByIdFetch(userId)).thenReturn(List.of(follow));

        // when
        List<UserElementResDto> followerList = followService.getFollowerList(userId);

        // then
        assertThat(followerList.size()).isEqualTo(1);
        assertThat(followerList.get(0).getName()).isEqualTo("sender");
    }

    @Test
    @DisplayName("팔로잉 조회 - 정상 조회")
    void 사용자_아이디를_통해_팔로잉_리스트를_조회할_수_있다() {
        // given
        Long userId = 1L;

        User sender = new User();
        sender.updateDetail("sender", null, null);
        User receiver = new User();
        receiver.updateDetail("receiver", null, null);

        Follow follow = Follow.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        when(followRepository.findFollowingsByIdFetch(userId)).thenReturn(List.of(follow));

        // when
        List<UserElementResDto> followingList = followService.getFollowingList(userId);

        // then
        assertThat(followingList.size()).isEqualTo(1);
        assertThat(followingList.get(0).getName()).isEqualTo("receiver");
    }
}