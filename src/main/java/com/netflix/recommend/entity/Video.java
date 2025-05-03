package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Country;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    private String director;

    private String cast;

    @Enumerated(EnumType.STRING)
    private Country country;

    private LocalDate dateAdded;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private Rate rating;

    private Integer duration;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video")
    private List<VideoGenre> genres;
}
