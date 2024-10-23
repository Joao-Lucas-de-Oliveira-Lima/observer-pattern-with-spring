package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.event.EventResponseDTO;
import edu.jl.observerspring.dto.event.EventSaveDTO;
import edu.jl.observerspring.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events/v1")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Page<EventResponseDTO>> findByNameContainingIgnoreCase(
            @RequestParam(name = "name", defaultValue = "")
            String name,
            @PageableDefault(size = 20, direction = Sort.Direction.ASC, sort = {"name"})
            Pageable pageable) {
        Page<EventResponseDTO> page = eventService.findByNameContainingIgnoreCase(name, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(page);
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> create(@RequestBody @Valid EventSaveDTO newEvent) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.create(newEvent));
    }
}
