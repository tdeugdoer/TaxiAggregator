package com.tserashkevich.rideservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum RideSortList {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    DISTANCE_ASC(Sort.by(Sort.Direction.ASC, "distance")),
    DISTANCE_DESC(Sort.by(Sort.Direction.DESC, "distance"));

    private final Sort value;
}
