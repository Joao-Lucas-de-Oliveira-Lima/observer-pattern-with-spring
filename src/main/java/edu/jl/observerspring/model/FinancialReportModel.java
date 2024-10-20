package edu.jl.observerspring.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "financial_reports")
public class FinancialReportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "financial_report_id")
    private UUID id;
    private Integer totalTicketsSold;
    private Double amountCollected;
    @OneToOne
    @JoinColumn(name = "event_id")
    private EventModel event;

    public FinancialReportModel() {
    }

    public FinancialReportModel(Integer totalTicketsSold, Double amountCollected, EventModel event) {
        this.totalTicketsSold = totalTicketsSold;
        this.amountCollected = amountCollected;
        this.event = event;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Integer totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public Double getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(Double amountCollected) {
        this.amountCollected = amountCollected;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FinancialReportModel that = (FinancialReportModel) object;
        return Objects.equals(id, that.id) && Objects.equals(totalTicketsSold, that.totalTicketsSold) && Objects.equals(amountCollected, that.amountCollected) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalTicketsSold, amountCollected, event);
    }
}
