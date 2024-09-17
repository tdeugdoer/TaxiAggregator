package com.tserashkevich.ratingservice.utils;

import com.tserashkevich.ratingservice.models.Rating;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RatingSorter {
    public static List<Rating> sortRating(List<Rating> ratings, RatingSortList sortList) {
        Comparator<Rating> comparator = switch (sortList) {
            case ID_ASC -> Comparator.comparing(Rating::getId);
            case ID_DESC -> Comparator.comparing(Rating::getId).reversed();
            case RATING_ASC -> Comparator.comparing(Rating::getRating);
            case RATING_DESC -> Comparator.comparing(Rating::getRating).reversed();
            case CREATION_TIME_ASC -> Comparator.comparing(Rating::getCreationTime);
            case CREATION_TIME_DESC -> Comparator.comparing(Rating::getCreationTime).reversed();
        };

        return ratings.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}