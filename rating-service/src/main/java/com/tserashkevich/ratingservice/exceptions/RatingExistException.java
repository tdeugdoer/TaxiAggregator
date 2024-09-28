package com.tserashkevich.ratingservice.exceptions;

import com.tserashkevich.ratingservice.utils.ExceptionList;

public class RatingExistException extends RuntimeException {
    public RatingExistException() {
        super(ExceptionList.RATING_EXIST.getValue());
    }
}
