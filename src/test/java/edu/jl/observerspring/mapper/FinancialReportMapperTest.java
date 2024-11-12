package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.model.FinancialReportModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link FinancialReportMapper}
 */
class FinancialReportMapperTest {

    private final FinancialReportMapper financialReportMapper = new FinancialReportMapper();

    private static FinancialReportModel financialReportModel;

    @BeforeAll
    static void setupForAllTests() {
        financialReportModel = new FinancialReportModel(0, 0.0, null);
    }

    @Test
    @DisplayName("Should map FinancialReportModel to FinancialReportResponseDTO correctly")
    void mapToResponseDTO() {
        FinancialReportResponseDTO responseDTO = financialReportMapper.mapToResponseDTO(financialReportModel);

        assertThat(responseDTO.amountCollected()).isEqualTo(financialReportModel.getAmountCollected());
        assertThat(responseDTO.totalTicketsSold()).isEqualTo(financialReportModel.getTotalTicketsSold());
        assertThat(responseDTO.id()).isNull();
    }
}