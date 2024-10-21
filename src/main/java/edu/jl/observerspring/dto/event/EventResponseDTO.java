package edu.jl.observerspring.dto.event;

import jakarta.persistence.Column;

import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String name,
        Integer numberOfTickets) {
}
