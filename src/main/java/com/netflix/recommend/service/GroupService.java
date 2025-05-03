package com.netflix.recommend.service;

import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.dto.res.GroupElementResDto;

import java.util.List;

public interface GroupService {

    void postGroup(Long userId, String title);

    void joinGroup(Long userId, Long groupId);

    GroupDetailResDto getGroupDetail(Long groupId);

    List<GroupElementResDto> searchGroupList(String keyword);
}
