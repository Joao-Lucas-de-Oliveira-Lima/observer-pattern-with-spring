package edu.jl.observerspring.repository;

import edu.jl.observerspring.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, UUID> {
    List<TicketModel> findByRodeoEventId(UUID rodeoEventId);
}
