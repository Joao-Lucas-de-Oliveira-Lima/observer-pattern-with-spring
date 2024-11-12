package edu.jl.observerspring.dto.exception;

import java.util.Date;

public record GenericExceptionResponseDTO(
        Date timestamp,
        String details,
        String message) {
}
