package com.netflix.recommend.service.impl;

import com.netflix.recommend.entity.Group;
import com.netflix.recommend.entity.Participant;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.GroupRepository;
import com.netflix.recommend.repository.ParticipantRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Override
    public void postGroup(Long userId, String name) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));

        Group group = groupRepository.save(Group.builder()
                .name(name)
                .build()
        );
        participantRepository.save(Participant.builder()
                .user(user)
                .group(group)
                .build()
        );
    }
}
