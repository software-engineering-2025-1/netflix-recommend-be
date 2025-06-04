package com.netflix.recommend.service;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;

public interface UserService {
    void updateUserDetail(Long userId, UserDetailReqDto userDetailReqDto);

    UserDetailResDto getUserDetail(Long userId);
}
