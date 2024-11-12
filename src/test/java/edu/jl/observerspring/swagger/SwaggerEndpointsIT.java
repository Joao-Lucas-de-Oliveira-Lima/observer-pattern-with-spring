package edu.jl.observerspring.swagger;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SwaggerEndpointsIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should confirm that Swagger UI is accessible")
    void shouldVerifySwaggerUiIsAccessible() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("Should confirm that the Swagger JSON file is available")
    void shouldVerifySwaggerJsonFileAvailability() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
