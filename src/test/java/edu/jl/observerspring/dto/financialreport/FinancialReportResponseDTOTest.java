package edu.jl.observerspring.dto.financialreport;

import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**Unit tests for {@link FinancialReportResponseDTO} */
class FinancialReportResponseDTOTest {
    @Test
    @DisplayName("Should verify that equals and hashCode are implemented correctly")
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(FinancialReportResponseDTO.class)
                .verify();
    }
}