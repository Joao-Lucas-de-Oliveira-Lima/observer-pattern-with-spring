package edu.jl.observerspring.dto.financialreport;

import edu.jl.observerspring.model.EventModel;

import java.util.UUID;

public record FinancialReportResponseDTO(
        UUID id,
        Integer totalTicketsSold,
        Double amountCollected) {
}
