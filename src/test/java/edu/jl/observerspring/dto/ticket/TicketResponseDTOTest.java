package edu.jl.observerspring.dto.ticket;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TicketResponseDTO}
 */
class TicketResponseDTOTest {
    @Test
    @DisplayName("Should verify that equals and hashCode are implemented correctly")
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(TicketResponseDTO.class)
                .verify();
    }

}