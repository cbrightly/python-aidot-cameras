package org.junit;

import org.hamcrest.c;

public class AssumptionViolatedException extends org.junit.internal.AssumptionViolatedException {
    private static final long serialVersionUID = 1;

    public <T> AssumptionViolatedException(T actual, c<T> matcher) {
        super((Object) actual, (c<?>) matcher);
    }

    public <T> AssumptionViolatedException(String message, T expected, c<T> matcher) {
        super(message, expected, matcher);
    }

    public AssumptionViolatedException(String message) {
        super(message);
    }

    public AssumptionViolatedException(String assumption, Throwable t) {
        super(assumption, t);
    }
}
