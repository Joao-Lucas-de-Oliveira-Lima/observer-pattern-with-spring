package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketSaveDTO;
import edu.jl.observerspring.mapper.TicketMapper;
import edu.jl.observerspring.model.EventModel;
import edu.jl.observerspring.model.TicketModel;
import edu.jl.observerspring.observer.event.NewEventRegistered;
import edu.jl.observerspring.observer.event.TicketPurchased;
import edu.jl.observerspring.repository.TicketRepository;
import edu.jl.observerspring.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
    public List<TicketResponseDTO> findByEventId(UUID eventId) {
        List<TicketModel> ticketModels  = ticketRepository.findByEventId(eventId);
        return ticketModels.stream().map(ticketMapper::mapToResponseDTO).toList();
    }

    @Override
    @Transactional
    public TicketResponseDTO updateTicket(UUID id, TicketSaveDTO updatedTicketData) throws RuntimeException{
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
    public void handlerEventModelCreated(NewEventRegistered systemEventListened) {
        createEventTicketsWithDefaultValues(systemEventListened.getEventModel());
    }

    private void createEventTicketsWithDefaultValues(EventModel eventModel) {
        Integer totalNumberOfEventTickets = eventModel.getNumberOfTickets();
        List<TicketModel> eventTickets =
                IntStream.rangeClosed(1, totalNumberOfEventTickets)
                        .mapToObj(ticketNumber -> TicketModel.builder()
                                .number(ticketNumber)
                                .sold(false)
                                .event(eventModel)
                                .build()).toList();
        ticketRepository.saveAll(eventTickets);
    }
}
