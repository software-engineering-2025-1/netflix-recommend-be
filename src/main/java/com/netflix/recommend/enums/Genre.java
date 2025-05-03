package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    ROMANCE("romance"),
    CRIMINAL("criminal"),
    DRAMA("drama"),
    MOVIE("movie");

    // TODO: 추후에 수정 및 추가

    private final String name;
}
