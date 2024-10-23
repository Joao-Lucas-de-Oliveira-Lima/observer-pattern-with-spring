package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketSaveDTO;
import edu.jl.observerspring.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets/v1")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findByEventId(
            @RequestParam(name = "eventId") UUID eventId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.findByEventId(eventId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid TicketSaveDTO updatedTicketData)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.updateTicket(id, updatedTicketData));
    }
}
