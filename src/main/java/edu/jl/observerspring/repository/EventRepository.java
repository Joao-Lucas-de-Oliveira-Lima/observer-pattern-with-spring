package edu.jl.observerspring.repository;

import edu.jl.observerspring.model.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventModel, UUID> {
    Page<EventModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
