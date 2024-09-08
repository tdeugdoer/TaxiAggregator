package com.tserashkevich.passengerservice.dtos;

import java.util.List;


public record ValidationErrorResponse(List<Violation> violations) {

}

