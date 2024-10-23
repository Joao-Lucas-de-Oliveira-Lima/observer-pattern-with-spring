package edu.jl.observerspring.dto.event;

import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String name,
        Integer numberOfTickets) {
}
