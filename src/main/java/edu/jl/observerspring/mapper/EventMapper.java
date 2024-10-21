package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.event.EventResponseDTO;
import edu.jl.observerspring.dto.event.EventSaveDTO;
import edu.jl.observerspring.model.EventModel;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventResponseDTO mapToResponseDTO(EventModel eventModel) {
        return new EventResponseDTO(
                eventModel.getId(),
                eventModel.getName(),
                eventModel.getNumberOfTickets());
    }
    public EventModel mapToEventModel(EventSaveDTO eventSaveDTO){
        EventModel eventModel = new EventModel();
        eventModel.setName(eventSaveDTO.name());
        eventModel.setNumberOfTickets(eventSaveDTO.numberOfTickets());
        return eventModel;
    }
}
