package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.mapper.RodeoEventMapper;
import edu.jl.observerspring.model.RodeoEventModel;
import edu.jl.observerspring.observer.event.NewRodeoEventRegistered;
import edu.jl.observerspring.repository.RodeoEventRepository;
import edu.jl.observerspring.service.RodeoEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RodeoEventServiceImplementation implements RodeoEventService {
    private final ApplicationEventPublisher eventPublisher;
    private final RodeoEventRepository rodeoEventRepository;
    private final RodeoEventMapper rodeoEventMapper;

    @Autowired
    public RodeoEventServiceImplementation(ApplicationEventPublisher eventPublisher, RodeoEventRepository rodeoEventRepository, RodeoEventMapper rodeoEventMapper) {
        this.eventPublisher = eventPublisher;
        this.rodeoEventRepository = rodeoEventRepository;
        this.rodeoEventMapper = rodeoEventMapper;
    }

    private void placeEventModel(RodeoEventModel rodeoEventModel) {
        eventPublisher.publishEvent(new NewRodeoEventRegistered(this, rodeoEventModel));
    }

    @Override
    public Page<RodeoEventResponseDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Page<RodeoEventModel> rodeoEventModelPage
                = rodeoEventRepository.findByNameContainingIgnoreCase(name, pageable);
        return rodeoEventModelPage.map(rodeoEventMapper::mapToResponseDTO);
    }

    @Override
    @Transactional
    public RodeoEventResponseDTO create(RodeoEventRequestDTO newEvent) {
        RodeoEventModel eventToBeSaved = rodeoEventMapper.mapToEventModel(newEvent);
        RodeoEventModel eventSaved = rodeoEventRepository.save(eventToBeSaved);
        placeEventModel(eventSaved);
        return rodeoEventMapper.mapToResponseDTO(eventSaved);
    }

}
