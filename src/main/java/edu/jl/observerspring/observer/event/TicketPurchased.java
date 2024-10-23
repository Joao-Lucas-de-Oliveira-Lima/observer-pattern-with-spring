package edu.jl.observerspring.observer.event;

import edu.jl.observerspring.model.TicketModel;
import org.springframework.context.ApplicationEvent;

public class TicketPurchased extends ApplicationEvent {
    private final TicketModel ticketModel;
    public TicketPurchased(Object source, TicketModel ticketModel){
        super(source);
        this.ticketModel = ticketModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }
}
