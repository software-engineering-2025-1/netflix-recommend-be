package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Builder
@Getter
public class VideoDetailResDto {
    private Long id;
    private String type;
    private String title;
    private String director;
    private String cast;
    private String country;
    private LocalDate dateAdded;
    private Integer releaseYear;
    private String rating;
    private Integer duration;
    private String description;
    private String genres;

    public static VideoDetailResDto of (Video video) {
        return VideoDetailResDto.builder()
                .id(video.getId())
                .type(video.getType().getName())
                .title(video.getTitle())
                .director(video.getDirector())
                .cast(video.getCast())
                .country(video.getCountry().getName())
                .dateAdded(video.getDateAdded())
                .releaseYear(video.getReleaseYear())
                .rating(video.getRating().getName())
                .duration(video.getDuration())
                .description(video.getDescription())
                .genres(video.getGenres().stream().map(videoGenre ->
                        videoGenre.getGenre().getName()
                ).collect(Collectors.joining(", ")))
                .build();
    }
}
