package edu.jl.observerspring.dto.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventSaveDTO(
        @NotBlank
        String name,
        @NotNull @Min(0)
        Integer numberOfTickets) {
}
