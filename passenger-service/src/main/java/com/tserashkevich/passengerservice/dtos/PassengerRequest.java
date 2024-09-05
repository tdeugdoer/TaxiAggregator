package com.tserashkevich.passengerservice.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequest {

    private String name;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDate;

}
