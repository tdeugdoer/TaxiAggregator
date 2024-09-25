package com.tserashkevich.rideservice.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@AllArgsConstructor
public enum ExceptionList {
    RIDE_NOT_FOUND("ride.not.found"),
    BAD_REQUEST_OTHER_SERVICE("bad.request.other.service"),
    NOT_FOUND_OTHER_SERVICE("not.found.other.service"),
    SERVER_OTHER_SERVICE("server.other.service"),
    EXTERNAL_SERVICE("external.service");


    private static MessageSource messageSource;

    static {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("exceptionMessages");
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        ExceptionList.messageSource = messageSource;
    }

    private final String key;

    public String getValue() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}

