package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.model.TicketModel;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketResponseDTO mapToResponseDTO(TicketModel ticketModel) {
        return new TicketResponseDTO(
                ticketModel.getId(),
                ticketModel.getNumber(),
                ticketModel.getValue(),
                ticketModel.getSold());
    }
}
