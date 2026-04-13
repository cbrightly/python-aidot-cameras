package org.apache.commons.math3.ode.events;

import org.apache.commons.math3.exception.MaxCountExceededException;

public class EventState$LocalMaxCountExceededException extends RuntimeException {
    private static final long serialVersionUID = 20120901;
    private final MaxCountExceededException wrapped;

    EventState$LocalMaxCountExceededException(MaxCountExceededException exception) {
        this.wrapped = exception;
    }

    public MaxCountExceededException getException() {
        return this.wrapped;
    }
}
