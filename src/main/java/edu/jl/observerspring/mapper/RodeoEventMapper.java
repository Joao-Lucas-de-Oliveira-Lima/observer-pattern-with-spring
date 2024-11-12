package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.model.RodeoEventModel;
import org.springframework.stereotype.Component;

@Component
public class RodeoEventMapper {
    public RodeoEventResponseDTO mapToResponseDTO(RodeoEventModel rodeoEventModel) {
        return new RodeoEventResponseDTO(
                rodeoEventModel.getId(),
                rodeoEventModel.getName(),
                rodeoEventModel.getNumberOfTickets());
    }
    public RodeoEventModel mapToEventModel(RodeoEventRequestDTO rodeoEventRequestDTO){
        return RodeoEventModel.builder()
                .name(rodeoEventRequestDTO.name())
                .numberOfTickets(rodeoEventRequestDTO.numberOfTickets())
                .build();
    }
}
