package edu.jl.observerspring.observer.event;

import edu.jl.observerspring.model.EventModel;
import org.springframework.context.ApplicationEvent;

public class NewEventRegistered extends ApplicationEvent {
    private final EventModel eventModel;
    public NewEventRegistered(Object source, EventModel eventModel) {
        super(source);
        this.eventModel = eventModel;
    }
    public EventModel getEventModel(){
        return this.eventModel;
    }
}
