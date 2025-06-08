package com.netflix.recommend.dto.res;

import com.netflix.recommend.entity.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class VideoDetailResDto {
    private Long id;
    private String type;
    private String title;
    private String director;
    private String cast;
    private String countries;
    private LocalDate dateAdded;
    private Integer releaseYear;
    private String rating;
    private String duration;
    private String description;
    private List<String> genres;

    public static VideoDetailResDto from(Video video) {
        return VideoDetailResDto.builder()
                .id(video.getId())
                .type(video.getType() != null ? video.getType().getName() : null)
                .title(video.getTitle())
                .director(video.getDirector())
                .cast(video.getCast())
                .countries(video.getCountries().stream().map(videoCountry ->
                        videoCountry.getCountry().getName()
                ).collect(Collectors.joining(", ")))
                .dateAdded(video.getDateAdded())
                .releaseYear(video.getReleaseYear())
                .rating(video.getRating() != null ? video.getRating().getName() : null)
                .duration(video.getDuration())
                .description(video.getDescription())
                .genres(video.getGenres().stream().map(videoGenre ->
                        videoGenre.getGenre().getName()
                ).toList())
                .build();
    }
}
