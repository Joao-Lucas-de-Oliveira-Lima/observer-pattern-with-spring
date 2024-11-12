package edu.jl.observerspring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public abstract class SavedRodeoEventTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public RodeoEventResponseDTO createAndSaveRodeoEvent(String eventName, Integer numberOfTickets) throws Exception {
        RodeoEventRequestDTO rodeoEventRequestDTO =
                new RodeoEventRequestDTO(eventName, numberOfTickets);

        String requestBody = objectMapper.writeValueAsString(rodeoEventRequestDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/rodeoEvents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        return objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }
}
