package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Country;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private Long kakaoId;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    private Country country;

    @Builder
    User(Long kakaoId) {
        this.name = "익명의 사용자";
        this.kakaoId = kakaoId;
    }

    public void updateDetail(String name, Integer age, Country country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }
}
