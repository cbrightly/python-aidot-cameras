package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.utils.DebugPoint;

public final class AsyncReadQueueRecord extends AsyncQueueRecord<ReadResult> {
    private static final ThreadCache.CachedTypeIndex<AsyncReadQueueRecord> CACHE_IDX = ThreadCache.obtainIndex(AsyncReadQueueRecord.class, 2);
    protected Interceptor interceptor;
    private final RecordReadResult readResult = new RecordReadResult();

    public static AsyncReadQueueRecord create(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor<ReadResult> interceptor2) {
        AsyncReadQueueRecord asyncReadQueueRecord = (AsyncReadQueueRecord) ThreadCache.takeFromCache(CACHE_IDX);
        if (asyncReadQueueRecord == null) {
            return new AsyncReadQueueRecord(connection, message, completionHandler, interceptor2);
        }
        asyncReadQueueRecord.isRecycled = false;
        asyncReadQueueRecord.set(connection, message, completionHandler, interceptor2);
        return asyncReadQueueRecord;
    }

    private AsyncReadQueueRecord(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor<ReadResult> interceptor2) {
        set(connection, message, completionHandler, interceptor2);
    }

    public Interceptor getInterceptor() {
        checkRecycled();
        return this.interceptor;
    }

    public void notifyComplete() {
        CompletionHandler completionHandler = this.completionHandler;
        if (completionHandler != null) {
            completionHandler.completed(this.readResult);
        }
    }

    public boolean isFinished() {
        return this.readResult.getReadSize() > 0 || !((Buffer) this.message).hasRemaining();
    }

    public ReadResult getCurrentResult() {
        return this.readResult;
    }

    /* access modifiers changed from: protected */
    public void set(Connection connection, Object message, CompletionHandler completionHandler, Interceptor interceptor2) {
        set(connection, message, completionHandler);
        this.interceptor = interceptor2;
        this.readResult.set(connection, message, null, 0);
    }

    /* access modifiers changed from: protected */
    public void reset() {
        set((Connection) null, (Object) null, (CompletionHandler) null);
        this.readResult.recycle();
        this.interceptor = null;
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
