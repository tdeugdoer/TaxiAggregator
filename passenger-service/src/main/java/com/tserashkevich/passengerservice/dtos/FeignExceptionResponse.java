package com.tserashkevich.passengerservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeignExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
}