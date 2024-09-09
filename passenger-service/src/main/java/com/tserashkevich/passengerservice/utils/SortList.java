package com.tserashkevich.passengerservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum SortList {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    BIRTH_DATE_ASC(Sort.by(Sort.Direction.ASC, "birthDate")),
    BIRTH_DATE_DESC(Sort.by(Sort.Direction.DESC, "birthDate"));

    private final Sort value;
}
