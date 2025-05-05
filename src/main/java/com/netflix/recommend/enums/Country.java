package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    SOUTH_KOREA("South Korea"),
    UNITED_STATES("United States"),
    GHANA("Ghana"),
    BURKINA_FASO("Burkina Faso"),
    UNITED_KINGDOM("United Kingdom");

    // TODO: 추후에 추가

    private final String name;
}
