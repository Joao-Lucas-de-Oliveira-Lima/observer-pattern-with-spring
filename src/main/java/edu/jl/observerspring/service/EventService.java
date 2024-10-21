package edu.jl.observerspring.service;

import edu.jl.observerspring.dto.event.EventResponseDTO;
import edu.jl.observerspring.dto.event.EventSaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<EventResponseDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);

    EventResponseDTO create(EventSaveDTO newEvent);
}
