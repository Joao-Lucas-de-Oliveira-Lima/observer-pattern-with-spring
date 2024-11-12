package edu.jl.observerspring.dto.rodeoevent;

import java.util.UUID;

public record RodeoEventResponseDTO(
        UUID id,
        String name,
        Integer numberOfTickets) {
}
