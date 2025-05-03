package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Country;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PreferGenre> genres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<History> histories;

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
