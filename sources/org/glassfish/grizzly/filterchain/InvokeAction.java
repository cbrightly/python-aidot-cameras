package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Appender;

public final class InvokeAction extends AbstractNextAction {
    static final int TYPE = 0;
    private Appender appender;
    private Object chunk;
    private boolean isIncomplete;

    InvokeAction() {
        super(0);
    }

    public Object getChunk() {
        return this.chunk;
    }

    public Appender getAppender() {
        return this.appender;
    }

    public boolean isIncomplete() {
        return this.isIncomplete;
    }

    public void setUnparsedChunk(Object unparsedChunk) {
        this.chunk = unparsedChunk;
        this.appender = null;
        this.isIncomplete = false;
    }

    public <E> void setIncompleteChunk(E incompleteChunk, Appender<E> appender2) {
        this.chunk = incompleteChunk;
        this.appender = appender2;
        this.isIncomplete = true;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.isIncomplete = false;
        this.chunk = null;
        this.appender = null;
    }
}
