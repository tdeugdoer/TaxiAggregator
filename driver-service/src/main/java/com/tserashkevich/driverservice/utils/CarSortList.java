package com.tserashkevich.driverservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum CarSortList {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NUMBER_ASC(Sort.by(Sort.Direction.ASC, "number")),
    NUMBER_DESC(Sort.by(Sort.Direction.DESC, "number")),
    BRAND_ASC(Sort.by(Sort.Direction.ASC, "brand")),
    BRAND_DESC(Sort.by(Sort.Direction.DESC, "brand")),
    MODEL_ASC(Sort.by(Sort.Direction.ASC, "model")),
    MODEL_DESC(Sort.by(Sort.Direction.DESC, "model")),
    COLOR_ASC(Sort.by(Sort.Direction.ASC, "color")),
    COLOR_DESC(Sort.by(Sort.Direction.DESC, "color"));

    private final Sort value;
}
