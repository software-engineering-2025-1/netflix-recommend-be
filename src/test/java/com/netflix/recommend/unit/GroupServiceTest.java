package com.netflix.recommend.unit;

import com.netflix.recommend.dto.req.ReviewDetailReqDto;
import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.dto.res.GroupElementResDto;
import com.netflix.recommend.dto.res.ReviewPageResDto;
import com.netflix.recommend.entity.*;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.GroupRepository;
import com.netflix.recommend.repository.ParticipantRepository;
import com.netflix.recommend.repository.ReviewRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.impl.GroupServiceImpl;
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
class GroupServiceTest {
    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock private GroupRepository groupRepository;
    @Mock private ParticipantRepository participantRepository;
    @Mock private UserRepository userRepository;
    @Mock private ReviewRepository reviewRepository;

    @Test
    @DisplayName("그룹 등록 - 정상 등록")
    void 사용자_아이디와_그룹명을_지정하면_그룹을_생성할_수_있다() {
        // given
        Long userId = 1L;
        String name = "group1";

        User user = User.builder().kakaoId(userId).build();

        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // when
        groupService.postGroup(userId, name);

        // then
        verify(groupRepository).save(any(Group.class));
        verify(participantRepository).save(any(Participant.class));
    }

    @Test
    @DisplayName("그룹 참여 - 정상 참여")
    void 사용자_아이디와_그룹_아이디를_지정하면_그룹에_참여할_수_있다() {
        // given
        Long userId = 1L;
        Long groupId = 1L;

        User user = new User();
        Group group = new Group();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(false);

        // when
        groupService.joinGroup(userId, groupId);

        // then
        verify(participantRepository).save(any(Participant.class));
    }

    @Test
    @DisplayName("그룹 참여 - 이미 참여한 그룹 예외")
    void 이미_참여한_그룹에_참여할_경우_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long groupId = 1L;

        User user = new User();
        Group group = new Group();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(true);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            groupService.joinGroup(userId, groupId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.ALREADY_JOINED_GROUP);
    }

    @Test
    @DisplayName("그룹 정보 조회 - 정상 조회")
    void 그룹_아이디를_통해_그룹_정보를_조회할_수_있다() {
        // given
        Long groupId = 1L;
        String groupName = "group1";

        Group group = Group.builder().name(groupName).build();
        ReflectionTestUtils.setField(group, "participants", Set.of(Participant.builder().user(new User()).build()));

        when(groupRepository.findGroupDetailByIdFetch(groupId)).thenReturn(Optional.of(group));

        // when
        GroupDetailResDto groupDetail = groupService.getGroupDetail(groupId);

        // then
        assertThat(groupDetail.getName()).isEqualTo(groupName);
    }

    @Test
    @DisplayName("그룹 정보 조회 - 존재하지 않는 그룹 예외")
    void 존재하지_않는_그룹을_조회할_경우_예외가_발생한다() {
        // given
        Long groupId = 1L;

        when(groupRepository.findGroupDetailByIdFetch(groupId)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            groupService.getGroupDetail(groupId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.CANNOT_FIND_GROUP);
    }

    @Test
    @DisplayName("그룹 키워드 검색 - 정상 검색")
    void 키워드를_통해_그룹_리스트를_검색할_수_있다() {
        // given
        String keyword = "keyword";
        String groupName = "group1";

        when(groupRepository.findAllByNameContaining(keyword)).thenReturn(List.of(Group.builder().name(groupName).build()));

        // when
        List<GroupElementResDto> groupList = groupService.searchGroupList(keyword);

        // then
        assertThat(groupList.size()).isEqualTo(1);
        assertThat(groupList.get(0).getName()).isEqualTo(groupName);
    }

    @Test
    @DisplayName("그룹 리뷰 리스트 조회 - 정상 조회")
    void 그룹_아이디를_통해_참여한_그룹의_리뷰_리스트를_조회할_수_있다() {
        // given
        Long groupId = 1L;
        Long userId = 1L;
        PageRequest pageable = PageRequest.of(0, 1);
        String comment = "comment1";

        User user = new User();
        user.updateDetail("user1", null, null);
        Group group = new Group();
        Review review = Review.builder()
                .comment(comment)
                .user(user)
                .build();
        List<Review> reviewList = List.of(review);

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(true);
        when(reviewRepository.findAllReviewDetailByGroupId(groupId, pageable)).thenReturn(new PageImpl<>(reviewList, pageable, reviewList.size()));

        // when
        ReviewPageResDto reviewWithPaging = groupService.getReviewWithPaging(userId, groupId, pageable);

        // then
        assertThat(reviewWithPaging.getTotalPage()).isEqualTo(1);
        assertThat(reviewWithPaging.getReviews().size()).isEqualTo(1);
        assertThat(reviewWithPaging.getReviews().get(0).getComment()).isEqualTo(comment);
    }

    @Test
    @DisplayName("그룹 리뷰 리스트 조회 - 그룹 권한 없음 예외")
    void 참여하지_않은_그룹의_리뷰를_조회할_경우_예외가_발생한다() {
        // given
        Long groupId = 1L;
        Long userId = 1L;
        PageRequest pageable = PageRequest.of(0, 1);

        User user = new User();
        Group group = new Group();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(false);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            groupService.getReviewWithPaging(userId, groupId, pageable);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.NEED_GROUP_PERMISSION);
    }

    @Test
    @DisplayName("그룹 리뷰 등록 - 정상 등록")
    void 그룹_아이디를_통해_특정_그룹에_리뷰를_작성할_수_있다() {
        // given
        Long groupId = 1L;
        Long userId = 1L;
        ReviewDetailReqDto reviewDetailReqDto = new ReviewDetailReqDto();
        ReflectionTestUtils.setField(reviewDetailReqDto, "comment", "comment1");

        User user = new User();
        Group group = new Group();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(true);

        // when
        groupService.postReview(userId, groupId, reviewDetailReqDto);

        // then
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("그룹 리뷰 등록 -  그룹 권한 없음 예외")
    void 참여하지_않은_그룹의_리뷰를_작성할_경우_예외가_발생한다() {
        // given
        Long groupId = 1L;
        Long userId = 1L;
        ReviewDetailReqDto reviewDetailReqDto = new ReviewDetailReqDto();
        ReflectionTestUtils.setField(reviewDetailReqDto, "comment", "comment1");

        User user = new User();
        Group group = new Group();

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(groupRepository.getReferenceById(groupId)).thenReturn(group);
        when(participantRepository.existsByUserAndGroup(user, group)).thenReturn(false);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            groupService.postReview(userId, groupId, reviewDetailReqDto);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.NEED_GROUP_PERMISSION);
    }

    @Test
    @DisplayName("내 그룹 조회 - 정상 조회")
    void 사용자_아이디를_통해_참여하고_있는_그룹의_리스트를_조회할_수_있다() {
        // given
        Long userId = 1L;
        String groupName = "group1";

        when(groupRepository.findAllByUserId(userId)).thenReturn(List.of(Group.builder().name(groupName).build()));

        // when
        List<GroupElementResDto> groupList = groupService.getMyGroupList(userId);

        // then
        assertThat(groupList.size()).isEqualTo(1);
        assertThat(groupList.get(0).getName()).isEqualTo(groupName);
    }
}