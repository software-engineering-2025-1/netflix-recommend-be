package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class VideoGenre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
