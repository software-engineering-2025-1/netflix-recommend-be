package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.dto.res.UserElementResDto;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void postGroup(Long userId, String name) {
        User user = userRepository.getReferenceById(userId);

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

    @Override
    public void joinGroup(Long userId, Long groupId) {
        User user = userRepository.getReferenceById(userId);
        Group group = groupRepository.getReferenceById(groupId);

        if (participantRepository.existsByUserAndGroup(user, group)) throw new CustomException(ErrorCode.ALREADY_JOINED_GROUP);

        participantRepository.save(Participant.builder()
                .user(user)
                .group(group)
                .build()
        );
    }

    @Override
    public GroupDetailResDto getGroupDetail(Long groupId) {
        Group group = groupRepository.findGroupDetailByIdFetch(groupId).orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_GROUP));

        return GroupDetailResDto.builder()
                .id(group.getId())
                .name(group.getName())
                .members(group.getParticipants().stream().map(participant -> {
                    User user = participant.getUser();
                    return UserElementResDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .build();
                }).toList())
                .build();
    }
}
