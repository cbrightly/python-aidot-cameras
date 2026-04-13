package io.netty.channel;

import androidx.core.app.NotificationCompat;
import io.netty.channel.MessageSizeEstimator;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class PendingWriteQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int PENDING_WRITE_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.pendingWriteSizeOverhead", 64);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) PendingWriteQueue.class);
    private final ChannelOutboundBuffer buffer;
    private long bytes;
    private final ChannelHandlerContext ctx;
    private final MessageSizeEstimator.Handle estimatorHandle;
    private PendingWrite head;
    private int size;
    private PendingWrite tail;

    public PendingWriteQueue(ChannelHandlerContext ctx2) {
        if (ctx2 != null) {
            this.ctx = ctx2;
            this.buffer = ctx2.channel().unsafe().outboundBuffer();
            this.estimatorHandle = ctx2.channel().config().getMessageSizeEstimator().newHandle();
            return;
        }
        throw new NullPointerException("ctx");
    }

    public boolean isEmpty() {
        if (this.ctx.executor().inEventLoop()) {
            return this.head == null;
        }
        throw new AssertionError();
    }

    public int size() {
        if (this.ctx.executor().inEventLoop()) {
            return this.size;
        }
        throw new AssertionError();
    }

    public long bytes() {
        if (this.ctx.executor().inEventLoop()) {
            return this.bytes;
        }
        throw new AssertionError();
    }

    private int size(Object msg) {
        int messageSize = this.estimatorHandle.size(msg);
        if (messageSize < 0) {
            messageSize = 0;
        }
        return PENDING_WRITE_OVERHEAD + messageSize;
    }

    public void add(Object msg, ChannelPromise promise) {
        if (!this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (msg == null) {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        } else if (promise != null) {
            int messageSize = size(msg);
            PendingWrite write = PendingWrite.newInstance(msg, messageSize, promise);
            PendingWrite currentTail = this.tail;
            if (currentTail == null) {
                this.head = write;
                this.tail = write;
            } else {
                PendingWrite unused = currentTail.next = write;
                this.tail = write;
            }
            this.size++;
            this.bytes += (long) messageSize;
            ChannelOutboundBuffer channelOutboundBuffer = this.buffer;
            if (channelOutboundBuffer != null) {
                channelOutboundBuffer.incrementPendingOutboundBytes(write.size);
            }
        } else {
            throw new NullPointerException("promise");
        }
    }

    public ChannelFuture removeAndWriteAll() {
        if (!this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (isEmpty()) {
            return null;
        } else {
            ChannelPromise p = this.ctx.newPromise();
            PromiseCombiner combiner = new PromiseCombiner();
            try {
                PendingWrite write = this.head;
                while (write != null) {
                    this.tail = null;
                    this.head = null;
                    this.size = 0;
                    this.bytes = 0;
                    while (write != null) {
                        PendingWrite next = write.next;
                        Object msg = write.msg;
                        ChannelPromise promise = write.promise;
                        recycle(write, false);
                        combiner.add(promise);
                        this.ctx.write(msg, promise);
                        write = next;
                    }
                    write = this.head;
                }
                combiner.finish(p);
            } catch (Throwable cause) {
                p.setFailure(cause);
            }
            assertEmpty();
            return p;
        }
    }

    public void removeAndFailAll(Throwable cause) {
        if (!this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (cause != null) {
            PendingWrite write = this.head;
            while (write != null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
                while (write != null) {
                    PendingWrite next = write.next;
                    ReferenceCountUtil.safeRelease(write.msg);
                    ChannelPromise promise = write.promise;
                    recycle(write, false);
                    safeFail(promise, cause);
                    write = next;
                }
                write = this.head;
            }
            assertEmpty();
        } else {
            throw new NullPointerException("cause");
        }
    }

    public void removeAndFail(Throwable cause) {
        if (!this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (cause != null) {
            PendingWrite write = this.head;
            if (write != null) {
                ReferenceCountUtil.safeRelease(write.msg);
                safeFail(write.promise, cause);
                recycle(write, true);
            }
        } else {
            throw new NullPointerException("cause");
        }
    }

    private void assertEmpty() {
        if (this.tail != null || this.head != null || this.size != 0) {
            throw new AssertionError();
        }
    }

    public ChannelFuture removeAndWrite() {
        if (this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            Object msg = write.msg;
            ChannelPromise promise = write.promise;
            recycle(write, true);
            return this.ctx.write(msg, promise);
        }
        throw new AssertionError();
    }

    public ChannelPromise remove() {
        if (this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            ChannelPromise promise = write.promise;
            ReferenceCountUtil.safeRelease(write.msg);
            recycle(write, true);
            return promise;
        }
        throw new AssertionError();
    }

    public Object current() {
        if (this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            return write.msg;
        }
        throw new AssertionError();
    }

    private void recycle(PendingWrite write, boolean update) {
        PendingWrite next = write.next;
        long writeSize = write.size;
        if (update) {
            if (next == null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
            } else {
                this.head = next;
                int i = this.size - 1;
                this.size = i;
                long j = this.bytes - writeSize;
                this.bytes = j;
                if (i <= 0 || j < 0) {
                    throw new AssertionError();
                }
            }
        }
        write.recycle();
        ChannelOutboundBuffer channelOutboundBuffer = this.buffer;
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.decrementPendingOutboundBytes(writeSize);
        }
    }

    private static void safeFail(ChannelPromise promise, Throwable cause) {
        if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
            logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
        }
    }

    public static final class PendingWrite {
        private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
            /* access modifiers changed from: protected */
            public PendingWrite newObject(Recycler.Handle handle) {
                return new PendingWrite(handle);
            }
        };
        private final Recycler.Handle handle;
        /* access modifiers changed from: private */
        public Object msg;
        /* access modifiers changed from: private */
        public PendingWrite next;
        /* access modifiers changed from: private */
        public ChannelPromise promise;
        /* access modifiers changed from: private */
        public long size;

        private PendingWrite(Recycler.Handle handle2) {
            this.handle = handle2;
        }

        static PendingWrite newInstance(Object msg2, int size2, ChannelPromise promise2) {
            PendingWrite write = RECYCLER.get();
            write.size = (long) size2;
            write.msg = msg2;
            write.promise = promise2;
            return write;
        }

        /* access modifiers changed from: private */
        public void recycle() {
            this.size = 0;
            this.next = null;
            this.msg = null;
            this.promise = null;
            RECYCLER.recycle(this, this.handle);
        }
    }
}
