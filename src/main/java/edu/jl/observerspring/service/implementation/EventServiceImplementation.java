package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.event.EventResponseDTO;
import edu.jl.observerspring.dto.event.EventSaveDTO;
import edu.jl.observerspring.mapper.EventMapper;
import edu.jl.observerspring.model.EventModel;
import edu.jl.observerspring.observer.event.NewEventRegistered;
import edu.jl.observerspring.repository.EventRepository;
import edu.jl.observerspring.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImplementation implements EventService {
    private final ApplicationEventPublisher eventPublisher;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImplementation(ApplicationEventPublisher eventPublisher, EventRepository eventRepository, EventMapper eventMapper) {
        this.eventPublisher = eventPublisher;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    private void placeEventModel(EventModel eventModel) {
        eventPublisher.publishEvent(new NewEventRegistered(this, eventModel));
    }

    @Override
    public Page<EventResponseDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Page<EventModel> eventModelPage
                = eventRepository.findByNameContainingIgnoreCase(name, pageable);
        return eventModelPage.map(eventMapper::mapToResponseDTO);
    }

    @Override
    @Transactional
    public EventResponseDTO create(EventSaveDTO newEvent) {
        EventModel eventToBeSaved = eventMapper.mapToEventModel(newEvent);
        EventModel eventSaved = eventRepository.save(eventToBeSaved);
        placeEventModel(eventSaved);
        return eventMapper.mapToResponseDTO(eventSaved);
    }

}
