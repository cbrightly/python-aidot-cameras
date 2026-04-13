package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public final class ChannelOutboundBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
    private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>() {
        /* access modifiers changed from: protected */
        public ByteBuffer[] initialValue() {
            return new ByteBuffer[1024];
        }
    };
    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER;
    private static final InternalLogger logger;
    private final Channel channel;
    private volatile Runnable fireChannelWritabilityChangedTask;
    private int flushed;
    private Entry flushedEntry;
    private boolean inFail;
    private int nioBufferCount;
    private long nioBufferSize;
    private Entry tailEntry;
    private volatile long totalPendingSize;
    private Entry unflushedEntry;
    private volatile int unwritable;

    public interface MessageProcessor {
        boolean processMessage(Object obj);
    }

    static {
        Class<ChannelOutboundBuffer> cls = ChannelOutboundBuffer.class;
        logger = InternalLoggerFactory.getInstance((Class<?>) cls);
        TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(cls, "totalPendingSize");
        UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "unwritable");
    }

    ChannelOutboundBuffer(AbstractChannel channel2) {
        this.channel = channel2;
    }

    public void addMessage(Object msg, int size, ChannelPromise promise) {
        Entry entry = Entry.newInstance(msg, size, total(msg), promise);
        if (this.tailEntry == null) {
            this.flushedEntry = null;
            this.tailEntry = entry;
        } else {
            this.tailEntry.next = entry;
            this.tailEntry = entry;
        }
        if (this.unflushedEntry == null) {
            this.unflushedEntry = entry;
        }
        incrementPendingOutboundBytes((long) entry.pendingSize, false);
    }

    public void addFlush() {
        Entry entry = this.unflushedEntry;
        if (entry != null) {
            if (this.flushedEntry == null) {
                this.flushedEntry = entry;
            }
            do {
                this.flushed++;
                if (!entry.promise.setUncancellable()) {
                    decrementPendingOutboundBytes((long) entry.cancel(), false, true);
                }
                entry = entry.next;
            } while (entry != null);
            this.unflushedEntry = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void incrementPendingOutboundBytes(long size) {
        incrementPendingOutboundBytes(size, true);
    }

    private void incrementPendingOutboundBytes(long size, boolean invokeLater) {
        if (size != 0 && TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size) > ((long) this.channel.config().getWriteBufferHighWaterMark())) {
            setUnwritable(invokeLater);
        }
    }

    /* access modifiers changed from: package-private */
    public void decrementPendingOutboundBytes(long size) {
        decrementPendingOutboundBytes(size, true, true);
    }

    private void decrementPendingOutboundBytes(long size, boolean invokeLater, boolean notifyWritability) {
        if (size != 0) {
            long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
            if (notifyWritability && newWriteBufferSize < ((long) this.channel.config().getWriteBufferLowWaterMark())) {
                setWritable(invokeLater);
            }
        }
    }

    private static long total(Object msg) {
        if (msg instanceof ByteBuf) {
            return (long) ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion) msg).count();
        }
        if (msg instanceof ByteBufHolder) {
            return (long) ((ByteBufHolder) msg).content().readableBytes();
        }
        return -1;
    }

    public Object current() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return null;
        }
        return entry.msg;
    }

    public void progress(long amount) {
        Entry e = this.flushedEntry;
        if (e != null) {
            ChannelPromise p = e.promise;
            if (p instanceof ChannelProgressivePromise) {
                long progress = e.progress + amount;
                e.progress = progress;
                ((ChannelProgressivePromise) p).tryProgress(progress, e.total);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public boolean remove() {
        Entry e = this.flushedEntry;
        if (e == null) {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;
        ChannelPromise promise = e.promise;
        int size = e.pendingSize;
        removeEntry(e);
        if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeSuccess(promise);
            decrementPendingOutboundBytes((long) size, false, true);
        }
        e.recycle();
        return true;
    }

    public boolean remove(Throwable cause) {
        return remove0(cause, true);
    }

    private boolean remove0(Throwable cause, boolean notifyWritability) {
        Entry e = this.flushedEntry;
        if (e == null) {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;
        ChannelPromise promise = e.promise;
        int size = e.pendingSize;
        removeEntry(e);
        if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeFail(promise, cause);
            decrementPendingOutboundBytes((long) size, false, notifyWritability);
        }
        e.recycle();
        return true;
    }

    private void removeEntry(Entry e) {
        int i = this.flushed - 1;
        this.flushed = i;
        if (i == 0) {
            this.flushedEntry = null;
            if (e == this.tailEntry) {
                this.tailEntry = null;
                this.unflushedEntry = null;
                return;
            }
            return;
        }
        this.flushedEntry = e.next;
    }

    public void removeBytes(long writtenBytes) {
        while (true) {
            Object msg = current();
            if (msg instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf) msg;
                int readerIndex = buf.readerIndex();
                int readableBytes = buf.writerIndex() - readerIndex;
                if (((long) readableBytes) <= writtenBytes) {
                    if (writtenBytes != 0) {
                        progress((long) readableBytes);
                        writtenBytes -= (long) readableBytes;
                    }
                    remove();
                } else if (writtenBytes != 0) {
                    buf.readerIndex(((int) writtenBytes) + readerIndex);
                    progress(writtenBytes);
                }
            } else if (writtenBytes != 0) {
                throw new AssertionError();
            }
        }
        clearNioBuffers();
    }

    private void clearNioBuffers() {
        int count = this.nioBufferCount;
        if (count > 0) {
            this.nioBufferCount = 0;
            Arrays.fill((Object[]) NIO_BUFFERS.get(), 0, count, (Object) null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0021, code lost:
        r6 = (io.netty.buffer.ByteBuf) r6;
        r7 = r6.readerIndex();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.nio.ByteBuffer[] nioBuffers() {
        /*
            r13 = this;
            r0 = 0
            r2 = 0
            io.netty.util.internal.InternalThreadLocalMap r3 = io.netty.util.internal.InternalThreadLocalMap.get()
            io.netty.util.concurrent.FastThreadLocal<java.nio.ByteBuffer[]> r4 = NIO_BUFFERS
            java.lang.Object r4 = r4.get(r3)
            java.nio.ByteBuffer[] r4 = (java.nio.ByteBuffer[]) r4
            io.netty.channel.ChannelOutboundBuffer$Entry r5 = r13.flushedEntry
        L_0x0011:
            boolean r6 = r13.isFlushedEntry(r5)
            if (r6 == 0) goto L_0x007a
            java.lang.Object r6 = r5.msg
            boolean r7 = r6 instanceof io.netty.buffer.ByteBuf
            if (r7 == 0) goto L_0x007a
            boolean r7 = r5.cancelled
            if (r7 != 0) goto L_0x0077
            io.netty.buffer.ByteBuf r6 = (io.netty.buffer.ByteBuf) r6
            int r7 = r6.readerIndex()
            int r8 = r6.writerIndex()
            int r8 = r8 - r7
            if (r8 <= 0) goto L_0x0077
            r9 = 2147483647(0x7fffffff, float:NaN)
            int r9 = r9 - r8
            long r9 = (long) r9
            int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r9 >= 0) goto L_0x0038
            goto L_0x007a
        L_0x0038:
            long r9 = (long) r8
            long r0 = r0 + r9
            int r9 = r5.count
            r10 = -1
            if (r9 != r10) goto L_0x0046
            int r10 = r6.nioBufferCount()
            r9 = r10
            r5.count = r10
        L_0x0046:
            int r10 = r2 + r9
            int r11 = r4.length
            if (r10 <= r11) goto L_0x0054
            java.nio.ByteBuffer[] r4 = expandNioBufferArray(r4, r10, r2)
            io.netty.util.concurrent.FastThreadLocal<java.nio.ByteBuffer[]> r11 = NIO_BUFFERS
            r11.set(r3, r4)
        L_0x0054:
            r11 = 1
            if (r9 != r11) goto L_0x0068
            java.nio.ByteBuffer r11 = r5.buf
            if (r11 != 0) goto L_0x0062
            java.nio.ByteBuffer r12 = r6.internalNioBuffer(r7, r8)
            r11 = r12
            r5.buf = r12
        L_0x0062:
            int r12 = r2 + 1
            r4[r2] = r11
            r2 = r12
            goto L_0x0077
        L_0x0068:
            java.nio.ByteBuffer[] r11 = r5.bufs
            if (r11 != 0) goto L_0x0073
            java.nio.ByteBuffer[] r12 = r6.nioBuffers()
            r11 = r12
            r5.bufs = r12
        L_0x0073:
            int r2 = fillBufferArray(r11, r4, r2)
        L_0x0077:
            io.netty.channel.ChannelOutboundBuffer$Entry r5 = r5.next
            goto L_0x0011
        L_0x007a:
            r13.nioBufferCount = r2
            r13.nioBufferSize = r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.ChannelOutboundBuffer.nioBuffers():java.nio.ByteBuffer[]");
    }

    private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount2) {
        int length = nioBufs.length;
        int i = 0;
        while (i < length) {
            ByteBuffer nioBuf = nioBufs[i];
            if (nioBuf == null) {
                break;
            }
            nioBuffers[nioBufferCount2] = nioBuf;
            i++;
            nioBufferCount2++;
        }
        return nioBufferCount2;
    }

    private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size) {
        int newCapacity = array.length;
        do {
            newCapacity <<= 1;
            if (newCapacity < 0) {
                throw new IllegalStateException();
            }
        } while (neededSpace > newCapacity);
        ByteBuffer[] newArray = new ByteBuffer[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public long nioBufferSize() {
        return this.nioBufferSize;
    }

    public boolean isWritable() {
        return this.unwritable == 0;
    }

    public boolean getUserDefinedWritability(int index) {
        return (this.unwritable & writabilityMask(index)) == 0;
    }

    public void setUserDefinedWritability(int index, boolean writable) {
        if (writable) {
            setUserDefinedWritability(index);
        } else {
            clearUserDefinedWritability(index);
        }
    }

    private void setUserDefinedWritability(int index) {
        int oldValue;
        int newValue;
        int mask = ~writabilityMask(index);
        do {
            oldValue = this.unwritable;
            newValue = oldValue & mask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue != 0 && newValue == 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private void clearUserDefinedWritability(int index) {
        int oldValue;
        int newValue;
        int mask = writabilityMask(index);
        do {
            oldValue = this.unwritable;
            newValue = oldValue | mask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue == 0 && newValue != 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private static int writabilityMask(int index) {
        if (index >= 1 && index <= 31) {
            return 1 << index;
        }
        throw new IllegalArgumentException("index: " + index + " (expected: 1~31)");
    }

    private void setWritable(boolean invokeLater) {
        int oldValue;
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue & -2;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue != 0 && newValue == 0) {
            fireChannelWritabilityChanged(invokeLater);
        }
    }

    private void setUnwritable(boolean invokeLater) {
        int oldValue;
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue | 1;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue == 0 && newValue != 0) {
            fireChannelWritabilityChanged(invokeLater);
        }
    }

    private void fireChannelWritabilityChanged(boolean invokeLater) {
        final ChannelPipeline pipeline = this.channel.pipeline();
        if (invokeLater) {
            Runnable task = this.fireChannelWritabilityChangedTask;
            if (task == null) {
                AnonymousClass2 r2 = new Runnable() {
                    public void run() {
                        pipeline.fireChannelWritabilityChanged();
                    }
                };
                task = r2;
                this.fireChannelWritabilityChangedTask = r2;
            }
            this.channel.eventLoop().execute(task);
            return;
        }
        pipeline.fireChannelWritabilityChanged();
    }

    public int size() {
        return this.flushed;
    }

    public boolean isEmpty() {
        return this.flushed == 0;
    }

    /* access modifiers changed from: package-private */
    public void failFlushed(Throwable cause, boolean notify) {
        if (!this.inFail) {
            boolean z = true;
            z = false;
            try {
                do {
                } while (remove0(cause, notify));
                this.inFail = z;
            } finally {
                this.inFail = z;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void close(final Throwable cause, final boolean allowChannelOpen) {
        if (this.inFail) {
            this.channel.eventLoop().execute(new Runnable() {
                public void run() {
                    ChannelOutboundBuffer.this.close(cause, allowChannelOpen);
                }
            });
            return;
        }
        this.inFail = true;
        if (!allowChannelOpen && this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        } else if (isEmpty()) {
            try {
                for (Entry e = this.unflushedEntry; e != null; e = e.recycleAndGetNext()) {
                    TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, (long) (-e.pendingSize));
                    if (!e.cancelled) {
                        ReferenceCountUtil.safeRelease(e.msg);
                        safeFail(e.promise, cause);
                    }
                }
                this.inFail = false;
                clearNioBuffers();
            } catch (Throwable th) {
                this.inFail = false;
                throw th;
            }
        } else {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
    }

    /* access modifiers changed from: package-private */
    public void close(ClosedChannelException cause) {
        close(cause, false);
    }

    private static void safeSuccess(ChannelPromise promise) {
        PromiseNotificationUtil.trySuccess(promise, null, promise instanceof VoidChannelPromise ? null : logger);
    }

    private static void safeFail(ChannelPromise promise, Throwable cause) {
        PromiseNotificationUtil.tryFailure(promise, cause, promise instanceof VoidChannelPromise ? null : logger);
    }

    @Deprecated
    public void recycle() {
    }

    public long totalPendingWriteBytes() {
        return this.totalPendingSize;
    }

    public long bytesBeforeUnwritable() {
        long bytes = ((long) this.channel.config().getWriteBufferHighWaterMark()) - this.totalPendingSize;
        if (bytes <= 0 || !isWritable()) {
            return 0;
        }
        return bytes;
    }

    public long bytesBeforeWritable() {
        long bytes = this.totalPendingSize - ((long) this.channel.config().getWriteBufferLowWaterMark());
        if (bytes <= 0 || isWritable()) {
            return 0;
        }
        return bytes;
    }

    public void forEachFlushedMessage(MessageProcessor processor) {
        if (processor != null) {
            Entry entry = this.flushedEntry;
            if (entry != null) {
                do {
                    if (entry.cancelled || processor.processMessage(entry.msg)) {
                        entry = entry.next;
                    } else {
                        return;
                    }
                } while (isFlushedEntry(entry));
                return;
            }
            return;
        }
        throw new NullPointerException("processor");
    }

    private boolean isFlushedEntry(Entry e) {
        return (e == null || e == this.unflushedEntry) ? false : true;
    }

    public static final class Entry {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
            /* access modifiers changed from: protected */
            public Entry newObject(Recycler.Handle handle) {
                return new Entry(handle);
            }
        };
        ByteBuffer buf;
        ByteBuffer[] bufs;
        boolean cancelled;
        int count;
        private final Recycler.Handle handle;
        Object msg;
        Entry next;
        int pendingSize;
        long progress;
        ChannelPromise promise;
        long total;

        private Entry(Recycler.Handle handle2) {
            this.count = -1;
            this.handle = handle2;
        }

        static Entry newInstance(Object msg2, int size, long total2, ChannelPromise promise2) {
            Entry entry = RECYCLER.get();
            entry.msg = msg2;
            entry.pendingSize = ChannelOutboundBuffer.CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD + size;
            entry.total = total2;
            entry.promise = promise2;
            return entry;
        }

        /* access modifiers changed from: package-private */
        public int cancel() {
            if (this.cancelled) {
                return 0;
            }
            this.cancelled = true;
            int pSize = this.pendingSize;
            ReferenceCountUtil.safeRelease(this.msg);
            this.msg = Unpooled.EMPTY_BUFFER;
            this.pendingSize = 0;
            this.total = 0;
            this.progress = 0;
            this.bufs = null;
            this.buf = null;
            return pSize;
        }

        /* access modifiers changed from: package-private */
        public void recycle() {
            this.next = null;
            this.bufs = null;
            this.buf = null;
            this.msg = null;
            this.promise = null;
            this.progress = 0;
            this.total = 0;
            this.pendingSize = 0;
            this.count = -1;
            this.cancelled = false;
            RECYCLER.recycle(this, this.handle);
        }

        /* access modifiers changed from: package-private */
        public Entry recycleAndGetNext() {
            Entry next2 = this.next;
            recycle();
            return next2;
        }
    }
}
