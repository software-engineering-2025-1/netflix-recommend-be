package com.netflix.recommend.service;

public interface GroupService {

    void postGroup(Long userId, String title);

    void joinGroup(Long userId, Long groupId);
}
