package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    MOVIE("Movie"),
    TV_SHOW("TV Show");

    private final String name;
}
