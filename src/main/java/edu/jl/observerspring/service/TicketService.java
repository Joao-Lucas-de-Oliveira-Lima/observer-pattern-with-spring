package edu.jl.observerspring.service;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketSaveDTO;
import edu.jl.observerspring.model.TicketModel;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketResponseDTO> findByEventId(UUID eventId);

    TicketResponseDTO updateTicket(UUID id, TicketSaveDTO updatedTicketData);
}
