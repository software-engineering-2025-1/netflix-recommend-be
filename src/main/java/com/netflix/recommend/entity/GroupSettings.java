package com.netflix.recommend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 그룹에 하나의 설정 (1:1)
    @OneToOne
    @JoinColumn(name = "group_id", nullable = false, unique = true)
    private GroupEntity group;

    @Column(name = "preferred_genres")
    private String preferredGenres; // CSV 형태 (예: "Action,Drama")

    @Column(name = "age_rating_preference")
    private String ageRatingPreference; // 예: "PG-13"
}