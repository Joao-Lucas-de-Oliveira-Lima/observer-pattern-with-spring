package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.mapper.FinancialReportMapper;
import edu.jl.observerspring.model.EventModel;
import edu.jl.observerspring.model.FinancialReportModel;
import edu.jl.observerspring.model.TicketModel;
import edu.jl.observerspring.observer.event.NewEventRegistered;
import edu.jl.observerspring.observer.event.TicketPurchased;
import edu.jl.observerspring.repository.FinancialReportRepository;
import edu.jl.observerspring.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
public class FinancialReportImplementation implements FinancialReportService {
    private final FinancialReportRepository financialReportRepository;
    private final FinancialReportMapper financialReportMapper;

    @Autowired
    public FinancialReportImplementation(FinancialReportRepository financialReportRepository, FinancialReportMapper financialReportMapper) {
        this.financialReportRepository = financialReportRepository;
        this.financialReportMapper = financialReportMapper;
    }

    @Override
    public FinancialReportResponseDTO findByEventId(UUID eventId){
        FinancialReportModel financialReport = financialReportRepository.findByEventId(eventId)
                .orElseThrow();
        return financialReportMapper.mapToResponseDTO(financialReport);
    }

    @EventListener
    public void handleEventModelCreated(NewEventRegistered systemEventListened) {
        createFinancialReport(systemEventListened.getEventModel());
    }

    private void createFinancialReport(EventModel eventModel) {
        FinancialReportModel financialReport
                = new FinancialReportModel(0, 0.0, eventModel);
        financialReportRepository.save(financialReport);
    }

    @EventListener
    public void handlerTicketPurchasedEvent(TicketPurchased ticketPurchasedEvent){
        TicketModel ticketPurchased = ticketPurchasedEvent.getTicketModel();
        updateFinancialReportOnTicketPurchase(ticketPurchased);
    }

    private void updateFinancialReportOnTicketPurchase(TicketModel ticket) {
        EventModel event = ticket.getEvent();
        FinancialReportModel financialReportToBeUpdated = financialReportRepository.findByEventId(event.getId())
                .orElseThrow();

        Double updatedAmountCollected
                = financialReportToBeUpdated.getAmountCollected()+ticket.getValue();
        Integer updatedTotalTicketsSold
                = financialReportToBeUpdated.getTotalTicketsSold() + 1;

        financialReportToBeUpdated.setAmountCollected(updatedAmountCollected);
        financialReportToBeUpdated.setTotalTicketsSold(updatedTotalTicketsSold);

        financialReportRepository.save(financialReportToBeUpdated);
    }
}
