package com.netflix.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(name= "user-video-uk", columnNames={"user_id", "video_id"})})
@Getter
public class History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
