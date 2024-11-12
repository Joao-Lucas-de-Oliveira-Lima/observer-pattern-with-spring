package edu.jl.observerspring.observer.event;

import edu.jl.observerspring.model.RodeoEventModel;
import org.springframework.context.ApplicationEvent;

public class NewRodeoEventRegistered extends ApplicationEvent {
    private final RodeoEventModel rodeoEvent;

    public NewRodeoEventRegistered(Object source, RodeoEventModel rodeoEvent) {
        super(source);
        this.rodeoEvent = rodeoEvent;
    }

    public RodeoEventModel getRodeoEvent() {
        return rodeoEvent;
    }
}
