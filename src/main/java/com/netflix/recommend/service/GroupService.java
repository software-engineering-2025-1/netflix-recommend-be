package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.GroupDetailResDto;

public interface GroupService {

    void postGroup(Long userId, String title);

    void joinGroup(Long userId, Long groupId);

    GroupDetailResDto getGroupDetail(Long groupId);
}
