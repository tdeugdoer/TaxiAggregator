package com.tserashkevich.driverservice.utils;

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
    public static final String CAR_NUMBER_REQUIRED = "{car.number.required}";
    public static final String CAR_NUMBER_NUMBER_ALREADY_EXIST = "{car.already.exist}";
    public static final String WRONG_MAX_CAR_NUMBER_LENGTH = "{wrong.max.car.number.length}";
    public static final String CAR_BRAND_REQUIRED = "{car.brand.required}";
    public static final String WRONG_CAR_BRAND_LENGTH = "{wrong.max.car.brand.length}";
    public static final String CAR_MODEL_REQUIRED = "{car.model.required}";
    public static final String WRONG_CAR_MODEL_LENGTH = "{wrong.max.car.model.length}";
    public static final String CAR_COLOR_REQUIRED = "{car.color.required}";
    public static final String WRONG_COLOR = "{wrong.color}";
    public static final String DRIVER_ID_REQUIRED = "{driver.id.required}";
    public static final String WRONG_UUID_FORMAT = "{wrong.uuid.format}";
    public static final String DRIVER_NOT_EXSIST = "{driver.not.exsist}";
}