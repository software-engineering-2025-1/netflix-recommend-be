package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.req.ReviewDetailReqDto;
import com.netflix.recommend.dto.res.*;
import com.netflix.recommend.entity.*;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.*;
import com.netflix.recommend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void postGroup(Long userId, String name) {
        User user = userRepository.getReferenceById(userId);
        Group group = Group.builder().name(name).build();

        groupRepository.save(group);
        participantRepository.save(Participant.builder()
                .user(user)
                .group(group)
                .build()
        );
    }

    @Override
    public void joinGroup(Long userId, Long groupId) {
        User user = userRepository.getReferenceById(userId);
        Group group = groupRepository.getReferenceById(groupId);

        if (participantRepository.existsByUserAndGroup(user, group))
            throw new CustomException(ErrorCode.ALREADY_JOINED_GROUP);

        participantRepository.save(Participant.builder()
                .user(user)
                .group(group)
                .build()
        );
    }

    @Override
    public GroupDetailResDto getGroupDetail(Long groupId) {
        Group group = groupRepository.findGroupDetailByIdFetch(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_GROUP));

        return GroupDetailResDto.from(group);
    }

    @Override
    public List<GroupElementResDto> searchGroupList(String keyword) {
        return groupRepository.findAllByNameContaining(keyword)
                .stream().map(GroupElementResDto::from).toList();
    }

    @Override
    public ReviewPageResDto getReviewWithPaging(Long userId, Long groupId, Pageable pageable) {
        User user = userRepository.getReferenceById(userId);
        Group group = groupRepository.getReferenceById(groupId);

        if (!participantRepository.existsByUserAndGroup(user, group))
            throw new CustomException(ErrorCode.NEED_GROUP_PERMISSION);

        return ReviewPageResDto.from(reviewRepository.findAllReviewDetailByGroupId(groupId, pageable));
    }

    @Override
    public void postReview(Long userId, Long groupId, ReviewDetailReqDto reviewDetailReqDto) {
        User user = userRepository.getReferenceById(userId);
        Group group = groupRepository.getReferenceById(groupId);

        if (!participantRepository.existsByUserAndGroup(user, group))
            throw new CustomException(ErrorCode.NEED_GROUP_PERMISSION);

        reviewRepository.save(Review.builder()
                .user(user)
                .group(group)
                .comment(reviewDetailReqDto.getComment())
                .build()
        );
    }

    @Override
    public List<GroupElementResDto> getMyGroupList(Long userId) {
        return groupRepository.findAllByUserId(userId).stream().map(GroupElementResDto::from).toList();
    }

    @Override
    public List<VideoElementResDto> getGroupHistory(Long groupId) {
        return groupRepository.findGroupHistory(groupId).stream().map(VideoElementResDto::from).toList();
    }
}
