package com.tserashkevich.passengerservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PatternList {
    public static final String PHONE_PATTERN = "\\+375\\d{9}";
    public static final String GENDER_PATTERN = "^(?:Men|Women|Other)$";
    public static final String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
}
