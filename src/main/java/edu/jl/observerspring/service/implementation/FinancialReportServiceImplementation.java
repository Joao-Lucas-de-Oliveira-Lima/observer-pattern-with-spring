package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.mapper.FinancialReportMapper;
import edu.jl.observerspring.model.RodeoEventModel;
import edu.jl.observerspring.model.FinancialReportModel;
import edu.jl.observerspring.model.TicketModel;
import edu.jl.observerspring.observer.event.NewRodeoEventRegistered;
import edu.jl.observerspring.observer.event.TicketPurchased;
import edu.jl.observerspring.repository.FinancialReportRepository;
import edu.jl.observerspring.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinancialReportServiceImplementation implements FinancialReportService {
    private final FinancialReportRepository financialReportRepository;
    private final FinancialReportMapper financialReportMapper;

    @Autowired
    public FinancialReportServiceImplementation(FinancialReportRepository financialReportRepository, FinancialReportMapper financialReportMapper) {
        this.financialReportRepository = financialReportRepository;
        this.financialReportMapper = financialReportMapper;
    }

    @Override
    public FinancialReportResponseDTO findByRodeoEventId(UUID rodeoEventId){
        FinancialReportModel financialReport = financialReportRepository.findByRodeoEventId(rodeoEventId)
                .orElseThrow();
        return financialReportMapper.mapToResponseDTO(financialReport);
    }

    @EventListener
    public void handleNewRodeoEventRegistered(NewRodeoEventRegistered newRodeoEventRegistered) {
        createFinancialReport(newRodeoEventRegistered.getRodeoEvent());
    }

    private void createFinancialReport(RodeoEventModel rodeoEvent) {
        FinancialReportModel financialReport
                = new FinancialReportModel(0, 0.0, rodeoEvent);
        financialReportRepository.save(financialReport);
    }

    @EventListener
    public void handlerTicketPurchasedEvent(TicketPurchased ticketPurchasedEvent){
        TicketModel ticketPurchased = ticketPurchasedEvent.getTicket();
        updateFinancialReportOnTicketPurchase(ticketPurchased);
    }

    private void updateFinancialReportOnTicketPurchase(TicketModel ticket) {
        RodeoEventModel rodeoEvent = ticket.getEvent();
        FinancialReportModel financialReportToBeUpdated =
                financialReportRepository.findByRodeoEventId(rodeoEvent.getId())
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
