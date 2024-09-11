package com.tserashkevich.passengerservice.dtos;

import com.tserashkevich.passengerservice.utils.PatternList;
import com.tserashkevich.passengerservice.utils.ValidationList;
import com.tserashkevich.passengerservice.validators.validAnnotations.ValidPassengerPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PassengerRequest {
    @NotBlank(message = ValidationList.USERNAME_REQUIRED)
    @Size(max = 50, message = ValidationList.WRONG_MAX_USERNAME_LENGTH)
    private final String name;

    @NotBlank(message = ValidationList.GENDER_REQUIRED)
    @Pattern(regexp = PatternList.GENDER_PATTERN, message = ValidationList.WRONG_GENDER)
    private final String gender;

    @ValidPassengerPhoneNumber(message = ValidationList.PHONE_NUMBER_ALREADY_EXIST)
    @NotBlank(message = ValidationList.PHONE_REQUIRED)
    @Pattern(regexp = PatternList.PHONE_PATTERN, message = ValidationList.WRONG_PHONE_FORMAT)
    private final String phoneNumber;

    @Past(message = ValidationList.WRONG_BIRTH_DATE)
    private final LocalDate birthDate;
}
