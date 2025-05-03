package com.netflix.recommend.service;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;

public interface UserService {
    void postUserDetail(Long userId, UserDetailReqDto userDetailReqDto);
    void updateUserDetail(Long userId, UserDetailReqDto userDetailReqDto);
    UserDetailResDto getUserDetail(Long userId);
}
