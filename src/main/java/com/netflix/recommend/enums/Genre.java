package com.netflix.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    ACTION_ADVENTURE("Action & Adventure"),
    ANIME_FEATURES("Anime Features"),
    ANIME_SERIES("Anime Series"),
    BRITISH_TV_SHOWS("British TV Shows"),
    CHILDREN_FAMILY_MOVIES("Children & Family Movies"),
    CLASSIC_CULT_TV("Classic & Cult TV"),
    CLASSIC_MOVIES("Classic Movies"),
    COMEDIES("Comedies"),
    CRIME_TV_SHOWS("Crime TV Shows"),
    CULT_MOVIES("Cult Movies"),
    DOCUMENTARIES("Documentaries"),
    DOCUSERIES("Docuseries"),
    DRAMAS("Dramas"),
    FAITH_SPIRITUALITY("Faith & Spirituality"),
    HORROR_MOVIES("Horror Movies"),
    INDEPENDENT_MOVIES("Independent Movies"),
    INTERNATIONAL_MOVIES("International Movies"),
    INTERNATIONAL_TV_SHOWS("International TV Shows"),
    KIDS_TV("Kids' TV"),
    KOREAN_TV_SHOWS("Korean TV Shows"),
    LGBTQ_MOVIES("LGBTQ Movies"),
    MOVIES("Movies"),
    MUSIC_MUSICALS("Music & Musicals"),
    REALITY_TV("Reality TV"),
    ROMANTIC_MOVIES("Romantic Movies"),
    ROMANTIC_TV_SHOWS("Romantic TV Shows"),
    SCIENCE_NATURE_TV("Science & Nature TV"),
    SCI_FI_FANTASY("Sci-Fi & Fantasy"),
    SPANISH_LANGUAGE_TV_SHOWS("Spanish-Language TV Shows"),
    SPORTS_MOVIES("Sports Movies"),
    STAND_UP_COMEDY("Stand-Up Comedy"),
    STAND_UP_COMEDY_TALK_SHOWS("Stand-Up Comedy & Talk Shows"),
    TEEN_TV_SHOWS("Teen TV Shows"),
    THRILLERS("Thrillers"),
    TV_ACTION_ADVENTURE("TV Action & Adventure"),
    TV_COMEDIES("TV Comedies"),
    TV_DRAMAS("TV Dramas"),
    TV_HORROR("TV Horror"),
    TV_MYSTERIES("TV Mysteries"),
    TV_SCI_FI_FANTASY("TV Sci-Fi & Fantasy"),
    TV_SHOWS("TV Shows"),
    TV_THRILLERS("TV Thrillers");

    private final String name;
}
