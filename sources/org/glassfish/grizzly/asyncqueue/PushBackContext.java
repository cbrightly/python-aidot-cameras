package org.glassfish.grizzly.asyncqueue;

@Deprecated
public abstract class PushBackContext {
    protected final AsyncWriteQueueRecord queueRecord;

    public abstract void cancel();

    public abstract void retryNow();

    public abstract void retryWhenPossible();

    public PushBackContext(AsyncWriteQueueRecord queueRecord2) {
        this.queueRecord = queueRecord2;
    }

    public PushBackHandler getPushBackHandler() {
        return this.queueRecord.getPushBackHandler();
    }

    public final long size() {
        return this.queueRecord.remaining();
    }
}
