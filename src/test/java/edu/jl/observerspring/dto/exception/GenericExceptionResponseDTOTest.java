package edu.jl.observerspring.dto.exception;

import edu.jl.observerspring.dto.ticket.TicketRequestDTO;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GenericExceptionResponseDTO}
 */
class GenericExceptionResponseDTOTest {
    @Test
    @DisplayName("Should verify that equals and hashCode are implemented correctly")
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(GenericExceptionResponseDTO.class)
                .verify();
    }
}