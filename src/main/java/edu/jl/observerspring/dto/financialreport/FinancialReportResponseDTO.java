package edu.jl.observerspring.dto.financialreport;

import java.util.UUID;

public record FinancialReportResponseDTO(
        UUID id,
        Integer totalTicketsSold,
        Double amountCollected) {
}
