package edu.jl.observerspring.dto.ticket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TicketRequestDTO(
        @NotNull @Min(0)
        Double value,
        @NotNull
        Boolean sold) {
}
