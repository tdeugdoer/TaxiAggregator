package com.tserashkevich.passengerservice.dtos;

import java.time.LocalDate;
import java.util.UUID;


public record PassengerResponse(UUID id, String name, String gender, String phoneNumber, LocalDate birthDate) {

}
