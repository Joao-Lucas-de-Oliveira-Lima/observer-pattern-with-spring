package edu.jl.observerspring.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private UUID id;

    private String name;
    @Column(name = "number_of_tickets")
    private Integer numberOfTickets;

    @OneToMany(mappedBy = "event")
    private List<TicketModel> tickets;

    @OneToOne(mappedBy = "event")
    private FinancialReportModel financialReport;

    public EventModel(String name, Integer numberOfTickets, List<TicketModel> tickets, FinancialReportModel financialReport) {
        this.name = name;
        this.numberOfTickets = numberOfTickets;
        this.tickets = tickets;
        this.financialReport = financialReport;
    }

    public EventModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public List<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketModel> tickets) {
        this.tickets = tickets;
    }

    public FinancialReportModel getFinancialReport() {
        return financialReport;
    }

    public void setFinancialReport(FinancialReportModel financialReport) {
        this.financialReport = financialReport;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EventModel that = (EventModel) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfTickets, that.numberOfTickets) && Objects.equals(tickets, that.tickets) && Objects.equals(financialReport, that.financialReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfTickets, tickets, financialReport);
    }
}
