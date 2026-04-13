package org.glassfish.grizzly.asyncqueue;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.DebugPoint;

public abstract class AsyncQueueRecord<R> implements Cacheable {
    private static final Logger LOGGER = Grizzly.logger(AsyncQueue.class);
    protected CompletionHandler completionHandler;
    protected Connection connection;
    protected boolean isRecycled = false;
    protected Object message;
    protected DebugPoint recycleTrack;

    public abstract R getCurrentResult();

    protected AsyncQueueRecord() {
    }

    public AsyncQueueRecord(Connection connection2, Object message2, CompletionHandler completionHandler2) {
        set(connection2, message2, completionHandler2);
    }

    /* access modifiers changed from: protected */
    public final void set(Connection connection2, Object message2, CompletionHandler completionHandler2) {
        checkRecycled();
        this.connection = connection2;
        this.message = message2;
        this.completionHandler = completionHandler2;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public final <T> T getMessage() {
        checkRecycled();
        return this.message;
    }

    public final void setMessage(Object message2) {
        checkRecycled();
        this.message = message2;
    }

    public void notifyFailure(Throwable e) {
        CompletionHandler completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.failed(e);
            return;
        }
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, LogMessages.FINE_GRIZZLY_ASYNCQUEUE_ERROR_NOCALLBACK_ERROR(e));
        }
    }

    public final void notifyIncomplete() {
        CompletionHandler completionHandler2 = this.completionHandler;
        if (completionHandler2 != null) {
            completionHandler2.updated(getCurrentResult());
        }
    }

    /* access modifiers changed from: protected */
    public final void checkRecycled() {
        if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
            DebugPoint track = this.recycleTrack;
            if (track != null) {
                throw new IllegalStateException("AsyncReadQueueRecord has been recycled at: " + track);
            }
            throw new IllegalStateException("AsyncReadQueueRecord has been recycled");
        }
    }
}
