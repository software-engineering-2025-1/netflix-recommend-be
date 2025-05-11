package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Country;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class VideoCountry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
