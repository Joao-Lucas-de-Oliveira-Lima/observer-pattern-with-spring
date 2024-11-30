package edu.jl.observerspring.controller;

import edu.jl.observerspring.SavedRodeoEventTestHelper;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests for {@link FinancialReportController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class FinancialReportControllerIT extends SavedRodeoEventTestHelper {

    private final MockMvc mockMvc;

    @Autowired
    public FinancialReportControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Should successfully return a financial report associated with a RodeoEvent")
    void shouldReturnFinancialReportByRodeoEventId() throws Exception {
        RodeoEventResponseDTO rodeoEventSaved = createAndSaveRodeoEvent("Event Test", 10);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/financialReports")
                        .queryParam("rodeoEventId", rodeoEventSaved.id().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTicketsSold").value(0))
                .andExpect(jsonPath("$.amountCollected").value(0))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Should not return a financial report due to an invalid UUID that does " +
            "not belong to a registered RodeoEvent")
    void shouldNotReturnFinancialReportForInvalidRodeoEventId() throws Exception {
        RodeoEventResponseDTO rodeoEventSaved = createAndSaveRodeoEvent("Event Test", 10);

        UUID invalidId;

        do {
            invalidId = UUID.randomUUID();
        } while (invalidId.equals(rodeoEventSaved.id()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/financialReports")
                        .queryParam("rodeoEventId", invalidId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}