package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/financialReport/v1")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    @Autowired
    public FinancialReportController(FinancialReportService financialReportService) {
        this.financialReportService = financialReportService;
    }

    @GetMapping
    public ResponseEntity<FinancialReportResponseDTO> findByEventId(
            @RequestParam(name = "eventId") UUID eventId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(financialReportService.findByEventId(eventId));
    }
}
