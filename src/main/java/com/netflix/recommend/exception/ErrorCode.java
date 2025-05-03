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
    ALREADY_JOINED_GROUP(HttpStatus.BAD_REQUEST, "이미 가입한 그룹입니다.");

    private final HttpStatus code;
    private final String message;
}