package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import edu.jl.observerspring.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ticket", description = "Endpoint for Managing Tickets")
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(
            tags = "Ticket",
            summary = "Retrieve tickets by rodeo event ID",
            description = "Fetches a list of tickets associated with a specific rodeo event ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = TicketResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketResponseDTO>> findByRodeoEventId(
            @Parameter(
                    required = true,
                    in = ParameterIn.QUERY,
                    description = "UUID associated with a rodeo event. It must be a UUID in valid format.")
            @RequestParam(name = "rodeoEventId") UUID rodeoEventId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.findByRodeoEventId(rodeoEventId));
    }

    @Operation(
            tags = "Ticket",
            summary = "Update ticket information",
            description = "Updates the details of an existing ticket specified by its ID. " +
                    "Requires the updated ticket data in the request body.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = TicketResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @Parameter(required = true, description = "Unique identifier of the ticket to be updated. It must be a UUID in valid format.")
            @PathVariable(name = "id")
            UUID id,
            @RequestBody
            @Valid
            TicketRequestDTO updatedTicketData) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.updateTicket(id, updatedTicketData));
    }
}
