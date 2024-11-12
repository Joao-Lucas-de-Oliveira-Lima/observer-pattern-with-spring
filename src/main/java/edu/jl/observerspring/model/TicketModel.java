package edu.jl.observerspring.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ticket_id")
    private UUID id;
    private Integer number;
    @Column(name = "ticket_value")
    private Double value;
    private Boolean sold;
    @ManyToOne
    @JoinColumn(name = "rodeo_event_id")
    private RodeoEventModel rodeoEvent;

    public TicketModel() {
    }

    private TicketModel(UUID id, Integer number, Double value, Boolean sold, RodeoEventModel rodeoEvent) {
        this.id = id;
        this.number = number;
        this.value = value;
        this.sold = sold;
        this.rodeoEvent = rodeoEvent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public RodeoEventModel getEvent() {
        return rodeoEvent;
    }

    public void setEvent(RodeoEventModel rodeoEvent) {
        this.rodeoEvent = rodeoEvent;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TicketModel that = (TicketModel) object;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(value, that.value) && Objects.equals(sold, that.sold) && Objects.equals(rodeoEvent, that.rodeoEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, value, sold, rodeoEvent);
    }

    public static TicketBuilder builder() {
        return new TicketBuilder();
    }

    public static class TicketBuilder {
        private UUID id = null;
        private Integer number = null;
        private Double value = null;
        private Boolean sold = null;
        private RodeoEventModel rodeoEvent = null;

        public TicketBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public TicketBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public TicketBuilder value(Double value) {
            this.value = value;
            return this;
        }

        public TicketBuilder sold(Boolean sold) {
            this.sold = sold;
            return this;
        }

        public TicketBuilder rodeoEvent(RodeoEventModel rodeoEvent) {
            this.rodeoEvent = rodeoEvent;
            return this;
        }

        public TicketModel build() {
            return new TicketModel(this.id, this.number, this.value, this.sold, this.rodeoEvent);
        }
    }
}
