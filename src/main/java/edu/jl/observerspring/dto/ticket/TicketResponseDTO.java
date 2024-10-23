package edu.jl.observerspring.dto.ticket;

import java.util.UUID;

public record TicketResponseDTO(
        UUID id,
        Integer number,
        Double value,
        Boolean sold) {
}
