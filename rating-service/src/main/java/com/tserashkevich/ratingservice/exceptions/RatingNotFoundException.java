package com.tserashkevich.ratingservice.exceptions;

import com.tserashkevich.ratingservice.utils.ExceptionList;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException() {
        super(ExceptionList.RATING_NOT_FOUND.getValue());
    }
}
