package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Type type;

    private String title;

    private String director;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String cast;

    private LocalDate dateAdded;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Rate rating;

    private String duration;

    @Column(columnDefinition = "VARCHAR(500)")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video")
    private Set<VideoGenre> genres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video")
    private Set<VideoCountry> countries;
}
