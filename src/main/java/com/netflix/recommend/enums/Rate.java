package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rate {
    G("G"),
    NC_17("NC-17"),
    NR("NR"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    TV_14("TV-14"),
    TV_G("TV-G"),
    TV_MA("TV-MA"),
    TV_PG("TV-PG"),
    TV_Y("TV-Y"),
    TV_Y7("TV-Y7"),
    TV_Y7_FV("TV-Y7-FV"),
    UR("UR");

    private final String name;
}
