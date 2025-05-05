package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    MOVIE("movie"),
    DRAMA("drama");

    private final String name;
}
