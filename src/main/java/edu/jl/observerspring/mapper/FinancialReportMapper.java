package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.model.FinancialReportModel;
import org.springframework.stereotype.Component;

@Component
public class FinancialReportMapper {
    public FinancialReportResponseDTO mapToResponseDTO(FinancialReportModel financialReportModel) {
        return new FinancialReportResponseDTO(
                financialReportModel.getId(),
                financialReportModel.getTotalTicketsSold(),
                financialReportModel.getAmountCollected());
    }
}
