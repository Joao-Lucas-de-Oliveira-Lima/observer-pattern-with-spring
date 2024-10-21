package edu.jl.observerspring.observer.event;

import edu.jl.observerspring.model.EventModel;
import org.springframework.context.ApplicationEvent;

public class EventModelCreated extends ApplicationEvent {
    private final EventModel eventModel;
    public EventModelCreated(Object source, EventModel eventModel) {
        super(source);
        this.eventModel = eventModel;
    }
    public EventModel getEventModel(){
        return this.eventModel;
    }
}
