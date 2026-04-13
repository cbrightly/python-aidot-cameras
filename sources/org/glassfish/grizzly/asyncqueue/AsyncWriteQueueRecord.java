package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.utils.DebugPoint;

public class AsyncWriteQueueRecord extends AsyncQueueRecord<RecordWriteResult> {
    private static final ThreadCache.CachedTypeIndex<AsyncWriteQueueRecord> CACHE_IDX = ThreadCache.obtainIndex(AsyncWriteQueueRecord.class, Writer.Reentrant.getMaxReentrants());
    public static final int UNCOUNTABLE_RECORD_SPACE_VALUE = 1;
    private Object dstAddress;
    private long initialMessageSize;
    private boolean isUncountable;
    private PushBackHandler pushBackHandler;
    private final RecordWriteResult writeResult = new RecordWriteResult();

    public static AsyncWriteQueueRecord create(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress2, PushBackHandler pushbackHandler, boolean isUncountable2) {
        AsyncWriteQueueRecord asyncWriteQueueRecord = (AsyncWriteQueueRecord) ThreadCache.takeFromCache(CACHE_IDX);
        if (asyncWriteQueueRecord == null) {
            return new AsyncWriteQueueRecord(connection, message, completionHandler, dstAddress2, pushbackHandler, isUncountable2);
        }
        asyncWriteQueueRecord.isRecycled = false;
        asyncWriteQueueRecord.set(connection, message, completionHandler, dstAddress2, pushbackHandler, isUncountable2);
        return asyncWriteQueueRecord;
    }

    protected AsyncWriteQueueRecord(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress2, PushBackHandler pushBackHandler2, boolean isUncountable2) {
        set(connection, message, completionHandler, dstAddress2, pushBackHandler2, isUncountable2);
    }

    /* access modifiers changed from: protected */
    public void set(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress2, PushBackHandler pushBackHandler2, boolean isUncountable2) {
        super.set(connection, message, completionHandler);
        this.dstAddress = dstAddress2;
        this.isUncountable = isUncountable2;
        this.initialMessageSize = message != null ? (long) message.remaining() : 0;
        this.pushBackHandler = pushBackHandler2;
        this.writeResult.set(connection, message, dstAddress2, 0);
    }

    public final Object getDstAddress() {
        checkRecycled();
        return this.dstAddress;
    }

    public final WritableMessage getWritableMessage() {
        return (WritableMessage) this.message;
    }

    public boolean isUncountable() {
        return this.isUncountable;
    }

    public void setUncountable(boolean isUncountable2) {
        this.isUncountable = isUncountable2;
    }

    public long getBytesToReserve() {
        if (this.isUncountable) {
            return 1;
        }
        return this.initialMessageSize;
    }

    public long getInitialMessageSize() {
        return this.initialMessageSize;
    }

    public long remaining() {
        return (long) getWritableMessage().remaining();
    }

    public RecordWriteResult getCurrentResult() {
        return this.writeResult;
    }

    @Deprecated
    public PushBackHandler getPushBackHandler() {
        return this.pushBackHandler;
    }

    public boolean canBeAggregated() {
        return !getWritableMessage().isExternal();
    }

    public void notifyCompleteAndRecycle() {
        CompletionHandler<WriteResult> completionHandlerLocal = this.completionHandler;
        WritableMessage messageLocal = getWritableMessage();
        if (completionHandlerLocal != null) {
            completionHandlerLocal.completed(this.writeResult);
        }
        recycle();
        messageLocal.release();
    }

    public boolean isFinished() {
        return !getWritableMessage().hasRemaining();
    }

    /* access modifiers changed from: protected */
    public final void reset() {
        set((Connection) null, (WritableMessage) null, (CompletionHandler) null, (Object) null, (PushBackHandler) null, false);
        this.writeResult.recycle();
    }

    public void recycle() {
        checkRecycled();
        reset();
        this.isRecycled = true;
        if (Grizzly.isTrackingThreadCache()) {
            this.recycleTrack = new DebugPoint(new Exception(), Thread.currentThread().getName());
        }
        ThreadCache.putToCache(CACHE_IDX, this);
    }
}
