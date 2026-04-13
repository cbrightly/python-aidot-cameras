package io.netty.buffer;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator implements ByteBufAllocatorMetricProvider {
    public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    private final boolean disableLeakDetector;
    private final UnpooledByteBufAllocatorMetric metric;
    private final boolean noCleaner;

    public UnpooledByteBufAllocator(boolean preferDirect) {
        this(preferDirect, false);
    }

    public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector2) {
        this(preferDirect, disableLeakDetector2, PlatformDependent.useDirectBufferNoCleaner());
    }

    public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector2, boolean tryNoCleaner) {
        super(preferDirect);
        this.metric = new UnpooledByteBufAllocatorMetric();
        this.disableLeakDetector = disableLeakDetector2;
        this.noCleaner = tryNoCleaner && PlatformDependent.hasUnsafe() && PlatformDependent.hasDirectBufferNoCleanerConstructor();
    }

    /* access modifiers changed from: protected */
    public ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
        return PlatformDependent.hasUnsafe() ? new InstrumentedUnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new InstrumentedUnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
    }

    /* access modifiers changed from: protected */
    public ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf;
        if (PlatformDependent.hasUnsafe()) {
            buf = this.noCleaner ? new InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(this, initialCapacity, maxCapacity) : new InstrumentedUnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
        } else {
            buf = new InstrumentedUnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
        }
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
        CompositeByteBuf buf = new CompositeByteBuf(this, false, maxNumComponents);
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
        CompositeByteBuf buf = new CompositeByteBuf(this, true, maxNumComponents);
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public boolean isDirectBufferPooled() {
        return false;
    }

    public ByteBufAllocatorMetric metric() {
        return this.metric;
    }

    /* access modifiers changed from: package-private */
    public void incrementDirect(int amount) {
        this.metric.directCounter.add((long) amount);
    }

    /* access modifiers changed from: package-private */
    public void decrementDirect(int amount) {
        this.metric.directCounter.add((long) (-amount));
    }

    /* access modifiers changed from: package-private */
    public void incrementHeap(int amount) {
        this.metric.heapCounter.add((long) amount);
    }

    /* access modifiers changed from: package-private */
    public void decrementHeap(int amount) {
        this.metric.heapCounter.add((long) (-amount));
    }

    public static final class InstrumentedUnpooledUnsafeHeapByteBuf extends UnpooledUnsafeHeapByteBuf {
        InstrumentedUnpooledUnsafeHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
            super(alloc, initialCapacity, maxCapacity);
        }

        /* access modifiers changed from: package-private */
        public byte[] allocateArray(int initialCapacity) {
            byte[] bytes = super.allocateArray(initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementHeap(bytes.length);
            return bytes;
        }

        /* access modifiers changed from: package-private */
        public void freeArray(byte[] array) {
            int length = array.length;
            super.freeArray(array);
            ((UnpooledByteBufAllocator) alloc()).decrementHeap(length);
        }
    }

    public static final class InstrumentedUnpooledHeapByteBuf extends UnpooledHeapByteBuf {
        InstrumentedUnpooledHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
            super((ByteBufAllocator) alloc, initialCapacity, maxCapacity);
        }

        /* access modifiers changed from: package-private */
        public byte[] allocateArray(int initialCapacity) {
            byte[] bytes = super.allocateArray(initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementHeap(bytes.length);
            return bytes;
        }

        /* access modifiers changed from: package-private */
        public void freeArray(byte[] array) {
            int length = array.length;
            super.freeArray(array);
            ((UnpooledByteBufAllocator) alloc()).decrementHeap(length);
        }
    }

    public static final class InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeNoCleanerDirectByteBuf {
        InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
            super(alloc, initialCapacity, maxCapacity);
        }

        /* access modifiers changed from: protected */
        public ByteBuffer allocateDirect(int initialCapacity) {
            ByteBuffer buffer = super.allocateDirect(initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementDirect(buffer.capacity());
            return buffer;
        }

        /* access modifiers changed from: package-private */
        public ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
            int capacity = oldBuffer.capacity();
            ByteBuffer buffer = super.reallocateDirect(oldBuffer, initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementDirect(buffer.capacity() - capacity);
            return buffer;
        }

        /* access modifiers changed from: protected */
        public void freeDirect(ByteBuffer buffer) {
            int capacity = buffer.capacity();
            super.freeDirect(buffer);
            ((UnpooledByteBufAllocator) alloc()).decrementDirect(capacity);
        }
    }

    public static final class InstrumentedUnpooledUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
        InstrumentedUnpooledUnsafeDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
            super((ByteBufAllocator) alloc, initialCapacity, maxCapacity);
        }

        /* access modifiers changed from: protected */
        public ByteBuffer allocateDirect(int initialCapacity) {
            ByteBuffer buffer = super.allocateDirect(initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementDirect(buffer.capacity());
            return buffer;
        }

        /* access modifiers changed from: protected */
        public void freeDirect(ByteBuffer buffer) {
            int capacity = buffer.capacity();
            super.freeDirect(buffer);
            ((UnpooledByteBufAllocator) alloc()).decrementDirect(capacity);
        }
    }

    public static final class InstrumentedUnpooledDirectByteBuf extends UnpooledDirectByteBuf {
        InstrumentedUnpooledDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
            super((ByteBufAllocator) alloc, initialCapacity, maxCapacity);
        }

        /* access modifiers changed from: protected */
        public ByteBuffer allocateDirect(int initialCapacity) {
            ByteBuffer buffer = super.allocateDirect(initialCapacity);
            ((UnpooledByteBufAllocator) alloc()).incrementDirect(buffer.capacity());
            return buffer;
        }

        /* access modifiers changed from: protected */
        public void freeDirect(ByteBuffer buffer) {
            int capacity = buffer.capacity();
            super.freeDirect(buffer);
            ((UnpooledByteBufAllocator) alloc()).decrementDirect(capacity);
        }
    }

    public static final class UnpooledByteBufAllocatorMetric implements ByteBufAllocatorMetric {
        final LongCounter directCounter;
        final LongCounter heapCounter;

        private UnpooledByteBufAllocatorMetric() {
            this.directCounter = PlatformDependent.newLongCounter();
            this.heapCounter = PlatformDependent.newLongCounter();
        }

        public long usedHeapMemory() {
            return this.heapCounter.value();
        }

        public long usedDirectMemory() {
            return this.directCounter.value();
        }

        public String toString() {
            return StringUtil.simpleClassName((Object) this) + "(usedHeapMemory: " + usedHeapMemory() + "; usedDirectMemory: " + usedDirectMemory() + ')';
        }
    }
}
