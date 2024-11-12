package edu.jl.observerspring.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
public class RodeoEventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rodeo_event_id")
    private UUID id;

    private String name;
    @Column(name = "number_of_tickets")
    private Integer numberOfTickets;

    @OneToMany(mappedBy = "rodeoEvent")
    private List<TicketModel> tickets;

    @OneToOne(mappedBy = "rodeoEvent")
    private FinancialReportModel financialReport;

    private RodeoEventModel(UUID id, String name, Integer numberOfTickets, List<TicketModel> tickets, FinancialReportModel financialReport) {
        this.id = id;
        this.name = name;
        this.numberOfTickets = numberOfTickets;
        this.tickets = tickets;
        this.financialReport = financialReport;
    }

    public RodeoEventModel() {
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
        RodeoEventModel that = (RodeoEventModel) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfTickets, that.numberOfTickets) && Objects.equals(tickets, that.tickets) && Objects.equals(financialReport, that.financialReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfTickets, tickets, financialReport);
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static class EventBuilder {
        private UUID id = null;
        private String name = null;
        private Integer numberOfTickets = null;
        private List<TicketModel> tickets = null;
        private FinancialReportModel financialReport = null;

        public EventBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder numberOfTickets(Integer numberOfTickets) {
            this.numberOfTickets = numberOfTickets;
            return this;
        }

        public EventBuilder tickets(List<TicketModel> tickets) {
            this.tickets = tickets;
            return this;
        }

        public EventBuilder financialReport(FinancialReportModel financialReport) {
            this.financialReport = financialReport;
            return this;
        }

        public RodeoEventModel build() {
            return new RodeoEventModel(id, name, numberOfTickets, tickets, financialReport);
        }

    }
}
