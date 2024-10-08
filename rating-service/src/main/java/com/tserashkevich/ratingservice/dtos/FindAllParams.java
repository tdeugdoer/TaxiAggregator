package com.tserashkevich.ratingservice.dtos;

import com.tserashkevich.ratingservice.utils.RatingSortList;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FindAllParams {
    private final Integer limit;
    private final RatingSortList sort;
    private final UUID sourceId;
    private final UUID targetId;
    private final Integer rating;
}