package com.tserashkevich.ratingservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum RatingSortList {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    RATING_ASC(Sort.by(Sort.Direction.ASC, "rating")),
    RATING_DESC(Sort.by(Sort.Direction.DESC, "rating")),
    CREATION_TIME_ASC(Sort.by(Sort.Direction.ASC, "creation_time")),
    CREATION_TIME_DESC(Sort.by(Sort.Direction.DESC, "creation_time"));

    private final Sort value;
}
