package edu.jl.observerspring.service.implementation;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link RodeoEventServiceImplementation}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RodeoEventServiceImplementationIT {

    private final RodeoEventServiceImplementation rodeoEventService;
    private final FinancialReportServiceImplementation financialReportService;
    private final TicketServiceImplementation ticketService;

    @Autowired
    public RodeoEventServiceImplementationIT(RodeoEventServiceImplementation rodeoEventService, FinancialReportServiceImplementation financialReportService, TicketServiceImplementation ticketService) {
        this.rodeoEventService = rodeoEventService;
        this.financialReportService = financialReportService;
        this.ticketService = ticketService;
    }

    private static RodeoEventRequestDTO firstEventRequest;
    private static RodeoEventRequestDTO secondEventRequest;
    private static RodeoEventRequestDTO thirdEventRequest;
    private static Pageable pageable;
    private static Integer pageSize;
    private static Integer pageNumber;

    @BeforeAll
    static void initializeTestResources() {
        firstEventRequest = new RodeoEventRequestDTO("Test event 01", 4);
        secondEventRequest = new RodeoEventRequestDTO("Test event 02", 8);
        thirdEventRequest = new RodeoEventRequestDTO("Test event 03", 10);

        pageSize = 2;
        pageNumber = 0;
        Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
    }

    @Order(1)
    @RepeatedTest(3)
    @DisplayName("Should save a valid RodeoEvent and generate a FinancialReport and tickets")
    void shouldSaveRodeoEventAndGenerateRelatedEntities(RepetitionInfo repetitionInfo) {
        RodeoEventRequestDTO currentEventRequest = switch (repetitionInfo.getCurrentRepetition()) {
            case 1 -> firstEventRequest;
            case 2 -> secondEventRequest;
            default -> thirdEventRequest;
        };

        RodeoEventResponseDTO savedEvent = rodeoEventService.create(currentEventRequest);

        assertThat(savedEvent.name()).isEqualTo(currentEventRequest.name());
        assertThat(savedEvent.numberOfTickets()).isEqualTo(currentEventRequest.numberOfTickets());
        assertThat(savedEvent.id()).isNotNull();

        FinancialReportResponseDTO generatedReport = financialReportService.findByRodeoEventId(savedEvent.id());
        assertThat(generatedReport).isNotNull();

        List<TicketResponseDTO> generatedTickets = ticketService.findByRodeoEventId(savedEvent.id());
        assertThat(generatedTickets).hasSize(savedEvent.numberOfTickets());
    }

    @Test
    @Order(2)
    @DisplayName("Should return a paginated list of 2 RodeoEvents sorted by name in ascending order")
    void shouldReturnPaginatedEventsSortedByNameAscending() {
        String searchName = ""; // empty name to include all events
        Page<RodeoEventResponseDTO> eventPage = rodeoEventService.findByNameContainingIgnoreCase(searchName, pageable);

        assertThat(eventPage.getTotalElements()).isEqualTo(3);
        assertThat(eventPage.getTotalPages()).isEqualTo(2);
        assertThat(eventPage.getNumber()).isEqualTo(pageNumber);
        assertThat(eventPage.getSize()).isEqualTo(pageSize);
        assertThat(eventPage.getContent().get(0).name()).isEqualTo(firstEventRequest.name());
        assertThat(eventPage.getContent().get(0).numberOfTickets()).isEqualTo(firstEventRequest.numberOfTickets());
        assertThat(eventPage.getContent().get(1).name()).isEqualTo(secondEventRequest.name());
        assertThat(eventPage.getContent().get(1).numberOfTickets()).isEqualTo(secondEventRequest.numberOfTickets());
    }

    @Test
    @Order(3)
    @DisplayName("Should return a paginated list of 2 RodeoEvents sorted by name in descending order")
    void shouldReturnPaginatedEventsSortedByNameDescending() {
        pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "name"));

        String searchName = ""; // empty name to include all events
        Page<RodeoEventResponseDTO> eventPage = rodeoEventService.findByNameContainingIgnoreCase(searchName, pageable);

        assertThat(eventPage.getTotalElements()).isEqualTo(3);
        assertThat(eventPage.getTotalPages()).isEqualTo(2);
        assertThat(eventPage.getNumber()).isEqualTo(pageNumber);
        assertThat(eventPage.getSize()).isEqualTo(pageSize);
        assertThat(eventPage.getContent().get(0).name()).isEqualTo(thirdEventRequest.name());
        assertThat(eventPage.getContent().get(0).numberOfTickets()).isEqualTo(thirdEventRequest.numberOfTickets());
        assertThat(eventPage.getContent().get(1).name()).isEqualTo(secondEventRequest.name());
        assertThat(eventPage.getContent().get(1).numberOfTickets()).isEqualTo(secondEventRequest.numberOfTickets());
    }
}
