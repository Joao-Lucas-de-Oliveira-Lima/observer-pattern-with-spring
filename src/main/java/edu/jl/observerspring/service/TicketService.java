package edu.jl.observerspring.service;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketRequestDTO;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketResponseDTO> findByRodeoEventId(UUID eventId);

    TicketResponseDTO updateTicket(UUID id, TicketRequestDTO updatedTicketData);
}
