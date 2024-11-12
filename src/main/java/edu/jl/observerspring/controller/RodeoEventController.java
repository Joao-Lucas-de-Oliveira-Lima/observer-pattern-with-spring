package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.service.RodeoEventService;
import edu.jl.observerspring.swagger.presentation.RodeoEventResponsePage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Rodeo event", description = "Endpoint for Managing Rodeo Events")
@RestController
@RequestMapping("/api/v1/rodeoEvents")
public class RodeoEventController {
    private final RodeoEventService rodeoEventService;

    @Autowired
    public RodeoEventController(RodeoEventService rodeoEventService) {
        this.rodeoEventService = rodeoEventService;
    }

    @Operation(
            tags = "Rodeo event",
            summary = "Search rodeo events by name",
            description = "Returns a page of rodeo events that contain the word specified in the 'name' " +
                    "parameter. The search is case-insensitive and the response is paginated.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RodeoEventResponsePage.class))),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @PageableAsQueryParam
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RodeoEventResponseDTO>> findByNameContainingIgnoreCase(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Name of the event to search for, " +
                            "allowing partial (case insensitive) search. Returns events whose name " +
                            "contains the given string, sorted by name in ascending order by default."
            )
            @RequestParam(name = "name", defaultValue = "")
            String name,
            @Parameter(hidden = true)
            @PageableDefault(size = 20, direction = Sort.Direction.ASC, sort = {"name"})
            Pageable pageable) {
        Page<RodeoEventResponseDTO> rodeoEventResponsePage =
                rodeoEventService.findByNameContainingIgnoreCase(name, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rodeoEventResponsePage);
    }

    @Operation(
            tags = "Rodeo event",
            summary = "Create a new rodeo event",
            description = "Create a new rodeo event with details provided in the request body. " +
                    "Validates the input and returns the created event data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RodeoEventResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RodeoEventResponseDTO> create(
            @RequestBody
            @Valid
            RodeoEventRequestDTO newRodeoEvent) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rodeoEventService.create(newRodeoEvent));
    }
}
