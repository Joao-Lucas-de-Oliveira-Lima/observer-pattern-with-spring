package edu.jl.observerspring.service;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;

import java.util.UUID;

public interface FinancialReportService {
    FinancialReportResponseDTO findByRodeoEventId(UUID eventId);
}
