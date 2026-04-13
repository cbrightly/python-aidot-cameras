package io.netty.channel;

import java.util.ArrayDeque;
import java.util.Queue;

public final class ChannelFlushPromiseNotifier {
    private final Queue<FlushCheckpoint> flushCheckpoints;
    private final boolean tryNotify;
    private long writeCounter;

    public interface FlushCheckpoint {
        long flushCheckpoint();

        void flushCheckpoint(long j);

        ChannelPromise promise();
    }

    public ChannelFlushPromiseNotifier(boolean tryNotify2) {
        this.flushCheckpoints = new ArrayDeque();
        this.tryNotify = tryNotify2;
    }

    public ChannelFlushPromiseNotifier() {
        this(false);
    }

    @Deprecated
    public ChannelFlushPromiseNotifier add(ChannelPromise promise, int pendingDataSize) {
        return add(promise, (long) pendingDataSize);
    }

    public ChannelFlushPromiseNotifier add(ChannelPromise promise, long pendingDataSize) {
        if (promise == null) {
            throw new NullPointerException("promise");
        } else if (pendingDataSize >= 0) {
            long checkpoint = this.writeCounter + pendingDataSize;
            if (promise instanceof FlushCheckpoint) {
                FlushCheckpoint cp = (FlushCheckpoint) promise;
                cp.flushCheckpoint(checkpoint);
                this.flushCheckpoints.add(cp);
            } else {
                this.flushCheckpoints.add(new DefaultFlushCheckpoint(checkpoint, promise));
            }
            return this;
        } else {
            throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + pendingDataSize);
        }
    }

    public ChannelFlushPromiseNotifier increaseWriteCounter(long delta) {
        if (delta >= 0) {
            this.writeCounter += delta;
            return this;
        }
        throw new IllegalArgumentException("delta must be >= 0 but was " + delta);
    }

    public long writeCounter() {
        return this.writeCounter;
    }

    public ChannelFlushPromiseNotifier notifyPromises() {
        notifyPromises0((Throwable) null);
        return this;
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures() {
        return notifyPromises();
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable cause) {
        notifyPromises();
        while (true) {
            FlushCheckpoint cp = this.flushCheckpoints.poll();
            if (cp == null) {
                return this;
            }
            if (this.tryNotify) {
                cp.promise().tryFailure(cause);
            } else {
                cp.promise().setFailure(cause);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause) {
        return notifyPromises(cause);
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable cause1, Throwable cause2) {
        notifyPromises0(cause1);
        while (true) {
            FlushCheckpoint cp = this.flushCheckpoints.poll();
            if (cp == null) {
                return this;
            }
            if (this.tryNotify) {
                cp.promise().tryFailure(cause2);
            } else {
                cp.promise().setFailure(cause2);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause1, Throwable cause2) {
        return notifyPromises(cause1, cause2);
    }

    private void notifyPromises0(Throwable cause) {
        if (this.flushCheckpoints.isEmpty()) {
            this.writeCounter = 0;
            return;
        }
        long writeCounter2 = this.writeCounter;
        while (true) {
            FlushCheckpoint cp = this.flushCheckpoints.peek();
            if (cp == null) {
                this.writeCounter = 0;
                break;
            } else if (cp.flushCheckpoint() <= writeCounter2) {
                this.flushCheckpoints.remove();
                ChannelPromise promise = cp.promise();
                if (cause == null) {
                    if (this.tryNotify) {
                        promise.trySuccess();
                    } else {
                        promise.setSuccess();
                    }
                } else if (this.tryNotify) {
                    promise.tryFailure(cause);
                } else {
                    promise.setFailure(cause);
                }
            } else if (writeCounter2 > 0 && this.flushCheckpoints.size() == 1) {
                this.writeCounter = 0;
                cp.flushCheckpoint(cp.flushCheckpoint() - writeCounter2);
            }
        }
        long newWriteCounter = this.writeCounter;
        if (newWriteCounter >= 549755813888L) {
            this.writeCounter = 0;
            for (FlushCheckpoint cp2 : this.flushCheckpoints) {
                cp2.flushCheckpoint(cp2.flushCheckpoint() - newWriteCounter);
            }
        }
    }

    public static class DefaultFlushCheckpoint implements FlushCheckpoint {
        private long checkpoint;
        private final ChannelPromise future;

        DefaultFlushCheckpoint(long checkpoint2, ChannelPromise future2) {
            this.checkpoint = checkpoint2;
            this.future = future2;
        }

        public long flushCheckpoint() {
            return this.checkpoint;
        }

        public void flushCheckpoint(long checkpoint2) {
            this.checkpoint = checkpoint2;
        }

        public ChannelPromise promise() {
            return this.future;
        }
    }
}
