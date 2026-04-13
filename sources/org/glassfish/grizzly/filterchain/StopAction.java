package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Appender;

public final class StopAction extends AbstractNextAction {
    static final int TYPE = 1;
    private Appender appender;
    private Object incompleteChunk;

    StopAction() {
        super(1);
    }

    public Object getIncompleteChunk() {
        return this.incompleteChunk;
    }

    public Appender getAppender() {
        return this.appender;
    }

    public <E> void setIncompleteChunk(E incompleteChunk2, Appender<E> appender2) {
        this.incompleteChunk = incompleteChunk2;
        this.appender = appender2;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.incompleteChunk = null;
        this.appender = null;
    }
}
