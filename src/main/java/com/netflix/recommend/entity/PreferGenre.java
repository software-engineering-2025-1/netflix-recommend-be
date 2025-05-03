package com.netflix.recommend.entity;

import com.netflix.recommend.enums.Genre;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(name= "genre-user-uk", columnNames={"genre", "user_id"}) })
@Getter
@NoArgsConstructor
public class PreferGenre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    PreferGenre(Genre genre, User user) {
        this.genre = genre;
        this.user = user;
    }
}
