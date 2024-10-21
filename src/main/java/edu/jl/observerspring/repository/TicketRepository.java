package edu.jl.observerspring.repository;

import edu.jl.observerspring.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, UUID> {
}