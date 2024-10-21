package edu.jl.observerspring.repository;

import edu.jl.observerspring.model.FinancialReportModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FinancialReportRepository extends JpaRepository<FinancialReportModel, UUID> {
}
