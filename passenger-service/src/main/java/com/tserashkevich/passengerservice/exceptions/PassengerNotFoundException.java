package com.tserashkevich.passengerservice.exceptions;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException() {
        super("Passanger not found");
    }

}
