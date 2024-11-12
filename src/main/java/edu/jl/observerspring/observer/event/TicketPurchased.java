package edu.jl.observerspring.observer.event;

import edu.jl.observerspring.model.TicketModel;
import org.springframework.context.ApplicationEvent;

public class TicketPurchased extends ApplicationEvent {
    private final TicketModel ticket;
    public TicketPurchased(Object source, TicketModel ticket){
        super(source);
        this.ticket = ticket;
    }

    public TicketModel getTicket() {
        return ticket;
    }
}
