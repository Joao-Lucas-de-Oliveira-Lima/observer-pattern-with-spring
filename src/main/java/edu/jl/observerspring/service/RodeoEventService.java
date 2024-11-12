package edu.jl.observerspring.service;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RodeoEventService {
    Page<RodeoEventResponseDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);
    RodeoEventResponseDTO create(RodeoEventRequestDTO newRodeoEvent);
}
