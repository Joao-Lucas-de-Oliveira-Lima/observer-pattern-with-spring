package edu.jl.observerspring.controller;

import edu.jl.observerspring.dto.financialreport.FinancialReportResponseDTO;
import edu.jl.observerspring.service.FinancialReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Financial Report", description = "Endpoint for Managing Financial Reports")
@RestController
@RequestMapping("/api/v1/financialReports")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    @Autowired
    public FinancialReportController(FinancialReportService financialReportService) {
        this.financialReportService = financialReportService;
    }

    @Operation(
            tags = "Financial Report",
            summary = "Retrieve financial report by rodeo event ID",
            description = "Fetches the financial report associated with a specific rodeo event ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = FinancialReportResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinancialReportResponseDTO> findByRodeoEventId(
            @Parameter(
                    in = ParameterIn.QUERY,
                    required = true,
                    description = "UUID associated with a rodeo event. It must be a UUID in valid format."
            )
            @RequestParam(name = "rodeoEventId") UUID rodeoEventId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(financialReportService.findByRodeoEventId(rodeoEventId));
    }
}
