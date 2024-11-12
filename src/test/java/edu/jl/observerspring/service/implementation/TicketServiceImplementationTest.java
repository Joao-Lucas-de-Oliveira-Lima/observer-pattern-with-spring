package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.SavedRodeoEventTestHelper;
import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link TicketServiceImplementation}.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TicketServiceImplementationTest extends SavedRodeoEventTestHelper {

    private final TicketServiceImplementation ticketService;
    private final FinancialReportServiceImplementation financialReportService;

    @Autowired
    public TicketServiceImplementationTest(TicketServiceImplementation ticketService, FinancialReportServiceImplementation financialReportService) {
        this.ticketService = ticketService;
        this.financialReportService = financialReportService;
    }

    @Test
    @DisplayName("Should successfully update ticket and trigger financial report update event")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateTicketAndTriggerFinancialReportUpdate() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test event", 2);
        List<TicketResponseDTO> tickets = ticketService.findByRodeoEventId(savedRodeoEvent.id());

        TicketRequestDTO ticketUpdateRequest = new TicketRequestDTO(20.0, true);
        ticketService.updateTicket(tickets.get(1).id(), ticketUpdateRequest);

        FinancialReportResponseDTO updatedFinancialReport
                = financialReportService.findByRodeoEventId(savedRodeoEvent.id());

        assertThat(updatedFinancialReport.amountCollected()).isEqualTo(20);
        assertThat(updatedFinancialReport.totalTicketsSold()).isEqualTo(1);

        List<TicketResponseDTO> ticketsAfterUpdate
                = ticketService.findByRodeoEventId(savedRodeoEvent.id());

        assertThat(ticketsAfterUpdate.get(0).id()).isEqualTo(tickets.get(0).id());
        assertThat(ticketsAfterUpdate.get(0).value()).isEqualTo(tickets.get(0).value());
        assertThat(ticketsAfterUpdate.get(0).sold()).isEqualTo(tickets.get(0).sold());
        assertThat(ticketsAfterUpdate.get(1).id()).isEqualTo(tickets.get(1).id());
        assertThat(ticketsAfterUpdate.get(1).value()).isEqualTo(ticketUpdateRequest.value());
        assertThat(ticketsAfterUpdate.get(1).sold()).isEqualTo(ticketUpdateRequest.sold());
    }
}
