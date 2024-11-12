package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.rodeoevent.RodeoEventRequestDTO;
import edu.jl.observerspring.dto.rodeoevent.RodeoEventResponseDTO;
import edu.jl.observerspring.model.RodeoEventModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link RodeoEventMapper}
 */
class RodeoEventMapperTest {
    private final RodeoEventMapper rodeoEventMapper = new RodeoEventMapper();

    private static RodeoEventModel rodeoEventModel;
    private static RodeoEventRequestDTO rodeoEventRequestDTO;

    @BeforeAll
    static void setupForAllTests() {
        rodeoEventModel = RodeoEventModel.builder()
                .id(UUID.randomUUID())
                .name("Test event")
                .numberOfTickets(10)
                .build();

        rodeoEventRequestDTO = new RodeoEventRequestDTO("Test Event", 10);
    }

    @Test
    @DisplayName("Should map RodeoEventModel to RodeoEventResponseDTO correctly")
    void mapToResponseDTO() {
        RodeoEventResponseDTO responseDTO = rodeoEventMapper.mapToResponseDTO(rodeoEventModel);

        assertThat(responseDTO.id()).isEqualTo(rodeoEventModel.getId());
        assertThat(responseDTO.name()).isEqualTo(rodeoEventModel.getName());
        assertThat(responseDTO.numberOfTickets()).isEqualTo(rodeoEventModel.getNumberOfTickets());
    }

    @Test
    @DisplayName("Should map RodeoEventRequestDTO to RodeoEventModel correctly")
    void mapToEventModel() {
        RodeoEventModel model = rodeoEventMapper.mapToEventModel(rodeoEventRequestDTO);

        assertThat(model.getId()).isNull();
        assertThat(model.getFinancialReport()).isNull();
        assertThat(model.getTickets()).isNull();
        assertThat(model.getName()).isEqualTo(rodeoEventRequestDTO.name());
        assertThat(model.getNumberOfTickets()).isEqualTo(rodeoEventRequestDTO.numberOfTickets());
    }
}