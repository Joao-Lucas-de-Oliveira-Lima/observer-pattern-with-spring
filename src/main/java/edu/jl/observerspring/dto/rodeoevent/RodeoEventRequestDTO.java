package edu.jl.observerspring.dto.rodeoevent;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RodeoEventRequestDTO(
        @NotBlank
        String name,
        @NotNull @Min(0)
        Integer numberOfTickets) {
}
