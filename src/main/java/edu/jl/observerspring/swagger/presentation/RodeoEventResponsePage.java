package edu.jl.observerspring.swagger.presentation;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;

import java.util.List;

public record RodeoEventResponsePage(
        List<RodeoEventResponseDTO> content,
        Page page) {

    public record Page(
            Long size,
            Long number,
            Long totalElements,
            Long totalPages) {
    }
}
