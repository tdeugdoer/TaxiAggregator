package com.tserashkevich.driverservice.dtos;

import com.tserashkevich.driverservice.utils.PatternList;
import com.tserashkevich.driverservice.utils.ValidationList;
import com.tserashkevich.driverservice.validators.validAnnotations.ValidDriverPhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DriverRequest {
    @NotBlank(message = ValidationList.USERNAME_REQUIRED)
    @Size(max = 50, message = ValidationList.WRONG_MAX_USERNAME_LENGTH)
    private final String name;

    @NotBlank(message = ValidationList.GENDER_REQUIRED)
    @Pattern(regexp = PatternList.GENDER_PATTERN, message = ValidationList.WRONG_GENDER)
    private final String gender;

    @ValidDriverPhoneNumber(message = ValidationList.PHONE_NUMBER_ALREADY_EXIST)
    @NotBlank(message = ValidationList.PHONE_REQUIRED)
    @Pattern(regexp = PatternList.PHONE_PATTERN, message = ValidationList.WRONG_PHONE_FORMAT)
    private final String phoneNumber;

    @Past(message = ValidationList.WRONG_BIRTH_DATE)
    private final LocalDate birthDate;

    @Valid
    private final List<CarWithoutDriverRequest> cars;
}