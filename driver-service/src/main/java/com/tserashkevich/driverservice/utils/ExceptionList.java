package com.tserashkevich.driverservice.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@AllArgsConstructor
public enum ExceptionList {
    DRIVER_NOT_FOUND("driver.not.found"),
    CAR_NOT_FOUND("car.not.found"),
    BAD_REQUEST_TO_OTHER_SERVICE("bad.request.to.other.service");

    private final String key;
    private static MessageSource messageSource;

    static {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("exceptionMessages");
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        ExceptionList.messageSource = messageSource;
    }

    public String getValue() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}

