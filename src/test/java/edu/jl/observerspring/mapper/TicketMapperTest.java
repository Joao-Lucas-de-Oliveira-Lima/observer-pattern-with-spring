package edu.jl.observerspring.mapper;

import edu.jl.observerspring.dto.ticket.TicketResponseDTO;
import edu.jl.observerspring.model.TicketModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TicketMapper}
 */
class TicketMapperTest {

    private final TicketMapper ticketMapper = new TicketMapper();

    private static TicketModel ticketModel;

    @BeforeAll
    static void setupForAllTests() {
        ticketModel = TicketModel.builder()
                .id(UUID.randomUUID())
                .number(1)
                .sold(false)
                .value(1000.00)
                .build();
    }

    @Test
    @DisplayName("Should map TicketModel to TicketResponseDTO correctly")
    void mapToResponseDTO() {
        TicketResponseDTO responseDTO = ticketMapper.mapToResponseDTO(ticketModel);

        assertThat(responseDTO.id()).isEqualTo(ticketModel.getId());
        assertThat(responseDTO.number()).isEqualTo(ticketModel.getNumber());
        assertThat(responseDTO.value()).isEqualTo(ticketModel.getValue());
        assertThat(responseDTO.sold()).isEqualTo(ticketModel.getSold());
    }
}