package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.res.UserElementResDto;
import com.netflix.recommend.entity.Follow;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.FollowRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void postFollow(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) throw new CustomException(ErrorCode.CANNOT_FOLLOW_MYSELF);

        User sender = userRepository.getReferenceById(senderId);
        User receiver = userRepository.getReferenceById(receiverId);

        if (followRepository.existsBySenderAndReceiver(sender, receiver)) throw new CustomException(ErrorCode.ALREADY_FOLLOWED_USER);

        followRepository.save(Follow.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .build()
        );
    }

    @Override
    public List<UserElementResDto> getFollowerList(Long userId) {
        return followRepository.findFollowersByIdFetch(userId).stream().map(follow -> {
            User follower = follow.getSender();
            return UserElementResDto.builder()
                    .id(follower.getId())
                    .name(follower.getName())
                    .build();
        }).toList();
    }
}
