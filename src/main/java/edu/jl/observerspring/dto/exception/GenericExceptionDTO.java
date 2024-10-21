package edu.jl.observerspring.dto.exception;

import java.util.Date;

public record GenericExceptionDTO(
        Date timestamp,
        String details,
        String message) {
}
