package com.netflix.recommend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "`group`")
@NoArgsConstructor
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<Participant> participants;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<Review> reviews;

    @Builder
    Group(String name) {
        this.name = name;
    }
}
