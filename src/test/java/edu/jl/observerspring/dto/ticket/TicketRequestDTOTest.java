package edu.jl.observerspring.dto.ticket;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TicketRequestDTO}
 */
class TicketRequestDTOTest {
    @Test
    @DisplayName("Should verify that equals and hashCode are implemented correctly")
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(TicketRequestDTO.class)
                .verify();
    }
}