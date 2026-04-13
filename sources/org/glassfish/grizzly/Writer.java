package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public interface Writer<L> {
    boolean canWrite(Connection<L> connection);

    void notifyWritePossible(Connection<L> connection, WriteHandler writeHandler);

    GrizzlyFuture<WriteResult<WritableMessage, L>> write(Connection<L> connection, L l, WritableMessage writableMessage);

    GrizzlyFuture<WriteResult<WritableMessage, L>> write(Connection<L> connection, WritableMessage writableMessage);

    void write(Connection<L> connection, L l, WritableMessage writableMessage, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler);

    void write(Connection<L> connection, L l, WritableMessage writableMessage, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler, MessageCloner<WritableMessage> messageCloner);

    @Deprecated
    void write(Connection<L> connection, L l, WritableMessage writableMessage, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler, PushBackHandler pushBackHandler);

    void write(Connection<L> connection, WritableMessage writableMessage, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler);

    public static final class Reentrant {
        private static final ThreadLocal<Reentrant> REENTRANTS_COUNTER = new ThreadLocal<Reentrant>() {
            /* access modifiers changed from: protected */
            public Reentrant initialValue() {
                return new Reentrant();
            }
        };
        private static final int maxWriteReentrants = Integer.getInteger("org.glassfish.grizzly.Writer.max-write-reentrants", 10).intValue();
        private int counter;

        public static int getMaxReentrants() {
            return maxWriteReentrants;
        }

        public static Reentrant getWriteReentrant() {
            return REENTRANTS_COUNTER.get();
        }

        public int get() {
            return this.counter;
        }

        public boolean inc() {
            int i = this.counter + 1;
            this.counter = i;
            return i <= maxWriteReentrants;
        }

        public boolean dec() {
            int i = this.counter - 1;
            this.counter = i;
            return i <= maxWriteReentrants;
        }

        public boolean isMaxReentrantsReached() {
            return get() >= getMaxReentrants();
        }
    }
}
