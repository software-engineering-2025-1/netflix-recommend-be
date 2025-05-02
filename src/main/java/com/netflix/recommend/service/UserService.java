package com.netflix.recommend.service;

import com.netflix.recommend.dto.UserDetailReqDto;

public interface UserService {
    void postUserDetail(Long userId, UserDetailReqDto userDetailReqDto);
}
