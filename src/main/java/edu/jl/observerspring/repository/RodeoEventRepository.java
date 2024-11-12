package edu.jl.observerspring.repository;

import edu.jl.observerspring.model.RodeoEventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RodeoEventRepository extends JpaRepository<RodeoEventModel, UUID> {
    Page<RodeoEventModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
