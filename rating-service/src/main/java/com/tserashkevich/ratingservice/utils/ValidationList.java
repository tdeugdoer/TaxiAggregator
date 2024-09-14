package com.tserashkevich.ratingservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationList {
    public static final String SOURCE_ID_REQUIRED = "{source.id.required}";
    public static final String TARGET_ID_REQUIRED = "{target.id.required}";
    public static final String WRONG_UUID_FORMAT = "{wrong.uuid.format}";
    public static final String RATING_REQUIRED = "{rating.required}";
    public static final String LESS_ZERO = "{less.zero}";
    public static final String MORE_TEN = "{more.ten}";
}
