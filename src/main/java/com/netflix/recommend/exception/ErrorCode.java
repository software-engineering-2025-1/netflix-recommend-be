package com.netflix.recommend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CANNOT_FIND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    ALREADY_REGISTERED_DETAIL(HttpStatus.BAD_REQUEST, "이미 초기 등록된 사용자입니다."),
    CANNOT_FIND_GROUP(HttpStatus.BAD_REQUEST, "그룹을 찾을 수 없습니다."),
    ALREADY_JOINED_GROUP(HttpStatus.BAD_REQUEST, "이미 가입한 그룹입니다."),
    CANNOT_FOLLOW_MYSELF(HttpStatus.BAD_REQUEST, "자신은 팔로우할 수 없습니다."),
    ALREADY_FOLLOWED_USER(HttpStatus.BAD_REQUEST, "이미 팔로우 하고 있는 사용자입니다."),
    ALREADY_REGISTERED_HISTORY(HttpStatus.BAD_REQUEST, "이미 등록된 시청 기록입니다."),
    CANNOT_FIND_VIDEO(HttpStatus.BAD_REQUEST, "영상을 찾을 수 없습니다."),

    NEED_GROUP_PERMISSION(HttpStatus.FORBIDDEN, "그룹에 대한 권한이 없습니다.");

    private final HttpStatus code;
    private final String message;
}