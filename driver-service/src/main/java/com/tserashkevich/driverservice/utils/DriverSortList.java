package com.tserashkevich.driverservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum DriverSortList {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    BIRTH_DATE_ASC(Sort.by(Sort.Direction.ASC, "birthDate")),
    BIRTH_DATE_DESC(Sort.by(Sort.Direction.DESC, "birthDate")),
    NUMBER_ASC(Sort.by(Sort.Direction.ASC, "number")),
    NUMBER_DESC(Sort.by(Sort.Direction.DESC, "number")),
    BRAND_ASC(Sort.by(Sort.Direction.ASC, "brand")),
    BRAND_DESC(Sort.by(Sort.Direction.DESC, "brand")),
    COLOR_ASC(Sort.by(Sort.Direction.ASC, "color")),
    COLOR_DESC(Sort.by(Sort.Direction.DESC, "color"));

    private final Sort value;
}
