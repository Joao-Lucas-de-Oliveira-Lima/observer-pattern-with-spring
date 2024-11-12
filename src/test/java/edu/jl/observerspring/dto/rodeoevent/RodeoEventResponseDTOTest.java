package edu.jl.observerspring.dto.rodeoevent;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RodeoEventResponseDTO}
 */
class RodeoEventResponseDTOTest {
    @Test
    @DisplayName("Should verify that equals and hashCode are implemented correctly")
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(RodeoEventResponseDTO.class)
                .verify();
    }
}