package com.tserashkevich.passengerservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationList {
    public static final String USERNAME_REQUIRED = "{username.required}";
    public static final String WRONG_MAX_USERNAME_LENGTH = "{wrong.max.username.length}";
    public static final String GENDER_REQUIRED = "{gender.required}";
    public static final String PHONE_REQUIRED = "{phone.required}";
    public static final String WRONG_PHONE_FORMAT = "{wrong.phone.format}";
    public static final String PHONE_NUMBER_ALREADY_EXIST = "{number.already.exists}";
    public static final String WRONG_BIRTH_DATE = "{wrong.birth.date}";
    public static final String WRONG_GENDER = "{wrong.gender}";
}