package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import edu.jl.observerspring.mapper.TicketMapper;
import edu.jl.observerspring.model.RodeoEventModel;
import edu.jl.observerspring.model.TicketModel;
import edu.jl.observerspring.observer.event.NewRodeoEventRegistered;
import edu.jl.observerspring.observer.event.TicketPurchased;
import edu.jl.observerspring.repository.TicketRepository;
import edu.jl.observerspring.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class TicketServiceImplementation implements TicketService {
    private final ApplicationEventPublisher eventPublisher;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImplementation(ApplicationEventPublisher eventPublisher, TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.eventPublisher = eventPublisher;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<TicketResponseDTO> findByRodeoEventId(UUID eventId) {
        List<TicketModel> ticketModels  = ticketRepository.findByRodeoEventId(eventId);
        return ticketModels.stream().map(ticketMapper::mapToResponseDTO).toList();
    }

    @Override
    @Transactional
    public TicketResponseDTO updateTicket(UUID id, TicketRequestDTO updatedTicketData){
        TicketModel ticketToBeUpdated = ticketRepository.findById(id)
                .orElseThrow();
        BeanUtils.copyProperties(updatedTicketData, ticketToBeUpdated);
        TicketModel ticketUpdated = ticketRepository.save(ticketToBeUpdated);
        publishTicketPurchasedEvent(ticketUpdated);
        return ticketMapper.mapToResponseDTO(ticketUpdated);
    }

    private void publishTicketPurchasedEvent(TicketModel ticketModel){
        eventPublisher.publishEvent(new TicketPurchased(this, ticketModel));
    }

    @EventListener
    public void handlerEventModelCreated(NewRodeoEventRegistered newRodeoEventRegistered) {
        createRodeoEventTicketsWithDefaultValues(newRodeoEventRegistered.getRodeoEvent());
    }

    private void createRodeoEventTicketsWithDefaultValues(RodeoEventModel rodeoEvent) {
        Integer totalNumberOfEventTickets = rodeoEvent.getNumberOfTickets();
        List<TicketModel> eventTickets =
                IntStream.rangeClosed(1, totalNumberOfEventTickets)
                        .mapToObj(ticketNumber -> TicketModel.builder()
                                .number(ticketNumber)
                                .sold(false)
                                .rodeoEvent(rodeoEvent)
                                .build()).toList();
        ticketRepository.saveAll(eventTickets);
    }
}
