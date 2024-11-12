package edu.jl.observerspring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import org.junit.jupiter.api.*;
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

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link RodeoEventController}
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RodeoEventControllerIT {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private static RodeoEventRequestDTO firstRodeoEventToBeSaved;
    private static RodeoEventRequestDTO secondRodeoEventToBeSaved;
    private static RodeoEventRequestDTO thirdRodeoEventToBeSaved;

    @Autowired
    public RodeoEventControllerIT(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    static void initializeTestData() {
        firstRodeoEventToBeSaved = new RodeoEventRequestDTO("Test event 01", 10);
        secondRodeoEventToBeSaved = new RodeoEventRequestDTO("Test event 02", 20);
        thirdRodeoEventToBeSaved = new RodeoEventRequestDTO("Test event 03", 30);
    }

    @RepeatedTest(3)
    @DisplayName("Should successfully create a RodeoEvent with valid data")
    @Order(1)
    void shouldCreateRodeoEventSuccessfully(RepetitionInfo currentRepetition) throws Exception {
        RodeoEventRequestDTO currentRodeoEventToBeTested = switch (currentRepetition.getCurrentRepetition()) {
            case 1 -> firstRodeoEventToBeSaved;
            case 2 -> secondRodeoEventToBeSaved;
            default -> thirdRodeoEventToBeSaved;
        };

        String requestBody = objectMapper.writeValueAsString(currentRodeoEventToBeTested);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        byte[] contentRequestResponseInBytes =
                resultActions.andReturn().getResponse().getContentAsByteArray();

        String contentRequestResponseAsString =
                new String(contentRequestResponseInBytes, StandardCharsets.UTF_8);

        RodeoEventResponseDTO resultObtained =
                objectMapper.readValue(contentRequestResponseAsString, new TypeReference<>() {
                });

        assertThat(resultObtained.name()).isEqualTo(currentRodeoEventToBeTested.name());
        assertThat(resultObtained.numberOfTickets()).isEqualTo(currentRodeoEventToBeTested.numberOfTickets());
        assertThat(resultObtained.id()).isNotNull();
    }

    @Test
    @DisplayName("Should fail to create a RodeoEvent with null name")
    @Order(2)
    void shouldFailToCreateRodeoEventWithNullName() throws Exception {
        RodeoEventRequestDTO invalidRequestData =
                new RodeoEventRequestDTO(null, 10);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(objectMapper.writeValueAsString(invalidRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create a RodeoEvent with empty name")
    @Order(3)
    void shouldFailToCreateRodeoEventWithEmptyName() throws Exception {
        RodeoEventRequestDTO invalidRequestData =
                new RodeoEventRequestDTO("", 10);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(objectMapper.writeValueAsString(invalidRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("d a RodeoEvent due to blank name field")
    @Order(4)
    void shouldFailToCreateRodeoEventWithBlankName() throws Exception {
        RodeoEventRequestDTO invalidRequestData =
                new RodeoEventRequestDTO("    ", 10);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(objectMapper.writeValueAsString(invalidRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create a RodeoEvent due to null ticket number")
    @Order(5)
    void shouldFailToCreateRodeoEventWithNullTicketNumber() throws Exception {
        RodeoEventRequestDTO invalidRequestData =
                new RodeoEventRequestDTO("Evento Teste", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(objectMapper.writeValueAsString(invalidRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to create a RodeoEvent with negative tickets")
    @Order(6)
    void shouldFailToCreateRodeoEventWithNegativeTickets() throws Exception {
        RodeoEventRequestDTO invalidRequestData =
                new RodeoEventRequestDTO("Evento Teste", -1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rodeoEvents")
                        .content(objectMapper.writeValueAsString(invalidRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return a paginated list of RodeoEvents sorted in ascending order by name")
    @Order(7)
    void shouldReturnPaginatedRodeoEventsPageSortedByNameAscending() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/rodeoEvents")
                        .queryParam("size", "2")
                        .queryParam("page", "0")
                        .queryParam("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.page.size").value(2))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.totalPages").value(2))
                .andExpect(jsonPath("$.content[0].name").value(firstRodeoEventToBeSaved.name()))
                .andExpect(jsonPath("$.content[0].numberOfTickets").value(firstRodeoEventToBeSaved.numberOfTickets()))
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[1].name").value(secondRodeoEventToBeSaved.name()))
                .andExpect(jsonPath("$.content[1].numberOfTickets").value(secondRodeoEventToBeSaved.numberOfTickets()))
                .andExpect(jsonPath("$.content[1].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should return a paginated list of RodeoEvents sorted in descending order by name")
    @Order(8)
    void shouldReturnPaginatedRodeoEventsPageSortedByNameDescending() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/rodeoEvents")
                        .queryParam("size", "2")
                        .queryParam("page", "0")
                        .queryParam("sort", "name,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.page.size").value(2))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.totalPages").value(2))
                .andExpect(jsonPath("$.content[0].name").value(thirdRodeoEventToBeSaved.name()))
                .andExpect(jsonPath("$.content[0].numberOfTickets").value(thirdRodeoEventToBeSaved.numberOfTickets()))
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[1].name").value(secondRodeoEventToBeSaved.name()))
                .andExpect(jsonPath("$.content[1].numberOfTickets").value(secondRodeoEventToBeSaved.numberOfTickets()))
                .andExpect(jsonPath("$.content[1].id").isNotEmpty());
    }
}