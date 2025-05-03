package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rate {
    ALL("all"),
    UP19("19+"),
    UP15("15+"),
    UP12("12+");

    private final String name;
}
