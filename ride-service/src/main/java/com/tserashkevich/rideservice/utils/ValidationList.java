package com.tserashkevich.rideservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationList {
    public static final String NEGATIVE_VALUE = "{negative.value}";
    public static final String START_ADDRESS_REQUIRED = "{start.address.required}";
    public static final String END_ADDRESS_REQUIRED = "{end.address.required}";
    public static final String PASSENGER_ID_REQUIRED = "{passenger.id.required}";
    public static final String DRIVER_ID_REQUIRED = "{driver.id.required}";
    public static final String CAR_ID_REQUIRED = "{car.id.required}";
    public static final String STATUS_REQUIRED = "{status.required}";
    public static final String WRONG_GEO_POINT_FORMAT = "{wrong.geo.point.format}";
    public static final String WRONG_UUID_FORMAT = "{wrong.uuid.format}";
    public static final String WRONG_STATUS = "{wrong.status}";
}