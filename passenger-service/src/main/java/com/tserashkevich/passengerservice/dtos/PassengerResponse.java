package com.tserashkevich.passengerservice.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PassengerResponse {

    private UUID id;
    private String name;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDate;

}
