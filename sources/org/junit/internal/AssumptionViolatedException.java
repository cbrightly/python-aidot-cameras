package org.junit.internal;

import org.hamcrest.b;
import org.hamcrest.c;
import org.hamcrest.d;
import org.hamcrest.e;

public class AssumptionViolatedException extends RuntimeException implements d {
    private static final long serialVersionUID = 2;
    private final String fAssumption;
    private final c<?> fMatcher;
    private final Object fValue;
    private final boolean fValueMatcher;

    @Deprecated
    public AssumptionViolatedException(String assumption, boolean hasValue, Object value, c<?> matcher) {
        this.fAssumption = assumption;
        this.fValue = value;
        this.fMatcher = matcher;
        this.fValueMatcher = hasValue;
        if (value instanceof Throwable) {
            initCause((Throwable) value);
        }
    }

    @Deprecated
    public AssumptionViolatedException(Object value, c<?> matcher) {
        this((String) null, true, value, matcher);
    }

    @Deprecated
    public AssumptionViolatedException(String assumption, Object value, c<?> matcher) {
        this(assumption, true, value, matcher);
    }

    @Deprecated
    public AssumptionViolatedException(String assumption) {
        this(assumption, false, (Object) null, (c<?>) null);
    }

    @Deprecated
    public AssumptionViolatedException(String assumption, Throwable e) {
        this(assumption, false, (Object) null, (c<?>) null);
        initCause(e);
    }

    public String getMessage() {
        return e.k(this);
    }

    public void describeTo(b description) {
        String str = this.fAssumption;
        if (str != null) {
            description.b(str);
        }
        if (this.fValueMatcher) {
            if (this.fAssumption != null) {
                description.b(": ");
            }
            description.b("got: ");
            description.c(this.fValue);
            if (this.fMatcher != null) {
                description.b(", expected: ");
                description.a(this.fMatcher);
            }
        }
    }
}
