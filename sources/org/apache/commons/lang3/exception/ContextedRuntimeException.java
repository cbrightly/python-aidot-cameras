package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.b;

public class ContextedRuntimeException extends RuntimeException implements c {
    private static final long serialVersionUID = 20110706;
    private final c exceptionContext;

    public ContextedRuntimeException() {
        this.exceptionContext = new b();
    }

    public ContextedRuntimeException(String message) {
        super(message);
        this.exceptionContext = new b();
    }

    public ContextedRuntimeException(Throwable cause) {
        super(cause);
        this.exceptionContext = new b();
    }

    public ContextedRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new b();
    }

    public ContextedRuntimeException(String message, Throwable cause, c context) {
        super(message, cause);
        this.exceptionContext = context == null ? new b() : context;
    }

    public ContextedRuntimeException addContextValue(String label, Object value) {
        this.exceptionContext.addContextValue(label, value);
        return this;
    }

    public ContextedRuntimeException setContextValue(String label, Object value) {
        this.exceptionContext.setContextValue(label, value);
        return this;
    }

    public List<Object> getContextValues(String label) {
        return this.exceptionContext.getContextValues(label);
    }

    public Object getFirstContextValue(String label) {
        return this.exceptionContext.getFirstContextValue(label);
    }

    public List<b<String, Object>> getContextEntries() {
        return this.exceptionContext.getContextEntries();
    }

    public Set<String> getContextLabels() {
        return this.exceptionContext.getContextLabels();
    }

    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        return this.exceptionContext.getFormattedExceptionMessage(baseMessage);
    }
}
