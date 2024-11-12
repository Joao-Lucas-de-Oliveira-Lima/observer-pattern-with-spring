package edu.jl.observerspring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jl.observerspring.SavedRodeoEventTestHelper;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link TicketController}
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TicketControllerIT extends SavedRodeoEventTestHelper {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public TicketControllerIT(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Successfully retrieves a list of tickets associated with a Rodeo Event")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldRetrieveTicketListByRodeoEventId() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Sample Event", 2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tickets")
                        .param("rodeoEventId", savedRodeoEvent.id().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].number").value(1))
                .andExpect(jsonPath("$[0].value").isEmpty())
                .andExpect(jsonPath("$[0].sold").value(false))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[1].number").value(2))
                .andExpect(jsonPath("$[1].value").isEmpty())
                .andExpect(jsonPath("$[1].sold").value(false))
                .andExpect(jsonPath("$[1].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should return an empty list when an invalid Rodeo Event ID is provided")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldReturnEmptyListForInvalidRodeoEventId() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Sample Event", 10);

        UUID invalidRodeoEventId;

        do {
            invalidRodeoEventId = UUID.randomUUID();
        } while (invalidRodeoEventId.equals(savedRodeoEvent.id()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tickets")
                        .queryParam("rodeoEventId", invalidRodeoEventId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    @DisplayName("Should successfully update a ticket and verify the changes")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateTicketAndVerifyChanges() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test Event", 2);

        List<TicketResponseDTO> initialTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());
        UUID ticketIdToUpdate = initialTickets.get(1).id();
        TicketRequestDTO updateRequest = new TicketRequestDTO(2000.0, true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/{id}", ticketIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketIdToUpdate.toString()))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.value").value(updateRequest.value()))
                .andExpect(jsonPath("$.sold").value(updateRequest.sold()));

        List<TicketResponseDTO> updatedTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());

        assertThat(updatedTickets.get(0)).isEqualTo(initialTickets.get(0));
        assertThat(updatedTickets.get(1)).isNotEqualTo(initialTickets.get(1));
        assertThat(updatedTickets.get(1).value()).isEqualTo(updateRequest.value());
        assertThat(updatedTickets.get(1).sold()).isEqualTo(updateRequest.sold());
        assertThat(updatedTickets.get(1).number()).isEqualTo(initialTickets.get(1).number());
    }

    @Test
    @DisplayName("Should not update a ticket when the provided ID is invalid")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldNotUpdateTicketWithInvalidUUID() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test Event", 2);

        List<TicketResponseDTO> savedTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());

        UUID invalidTicketId;
        do {
            invalidTicketId = UUID.randomUUID();
        } while (invalidTicketId.equals(savedTickets.get(0).id()) || invalidTicketId.equals(savedTickets.get(1).id()));

        TicketRequestDTO updateRequest = new TicketRequestDTO(2000.0, true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/{id}", invalidTicketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().is5xxServerError());
    }


    @Test
    @DisplayName("Should not update a ticket when the 'value' field is null")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldNotUpdateTicketWhenValueIsNull() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test Event", 2);

        List<TicketResponseDTO> initialTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());
        UUID ticketIdToUpdate = initialTickets.get(0).id();

        TicketRequestDTO updateRequest = new TicketRequestDTO(null, true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/{id}", ticketIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Should not update a ticket when the 'value' field is negative")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldNotUpdateTicketWhenValueIsNegative() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test Event", 2);

        List<TicketResponseDTO> initialTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());
        UUID ticketIdToUpdate = initialTickets.get(0).id();

        TicketRequestDTO updateRequest = new TicketRequestDTO(-2000.0, true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/{id}", ticketIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Should not update a ticket when the 'sold' field is null")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldNotUpdateTicketWhenSoldFieldIsNull() throws Exception {
        RodeoEventResponseDTO savedRodeoEvent = createAndSaveRodeoEvent("Test Event", 2);

        List<TicketResponseDTO> initialTickets = getTicketsByRodeoEventId(savedRodeoEvent.id());
        UUID ticketIdToUpdate = initialTickets.get(0).id();

        TicketRequestDTO updateRequest = new TicketRequestDTO(-2000.0, null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/{id}", ticketIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }


    private List<TicketResponseDTO> getTicketsByRodeoEventId(UUID rodeoEventId) throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tickets")
                        .queryParam("rodeoEventId", rodeoEventId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        return objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                }
        );
    }

}