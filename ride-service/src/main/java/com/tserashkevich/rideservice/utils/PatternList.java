package com.tserashkevich.rideservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PatternList {
    public static final String GEO_PATTERN = "^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)$";
    public static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static final String STATUS_PATTERN = "^(?:OPENED|ACCEPTED|CREATED|FINISHED|CANCELLED)$";
}
