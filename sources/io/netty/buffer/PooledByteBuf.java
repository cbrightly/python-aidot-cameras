package io.netty.buffer;

import io.netty.util.Recycler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class PooledByteBuf<T> extends AbstractReferenceCountedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ByteBufAllocator allocator;
    PoolThreadCache cache;
    protected PoolChunk<T> chunk;
    protected long handle;
    protected int length;
    int maxLength;
    protected T memory;
    protected int offset;
    private final Recycler.Handle recyclerHandle;
    private ByteBuffer tmpNioBuf;

    /* access modifiers changed from: protected */
    public abstract ByteBuffer newInternalNioBuffer(T t);

    /* access modifiers changed from: protected */
    public abstract Recycler<?> recycler();

    static {
        Class<PooledByteBuf> cls = PooledByteBuf.class;
    }

    protected PooledByteBuf(Recycler.Handle recyclerHandle2, int maxCapacity) {
        super(maxCapacity);
        this.recyclerHandle = recyclerHandle2;
    }

    /* access modifiers changed from: package-private */
    public void init(PoolChunk<T> chunk2, long handle2, int offset2, int length2, int maxLength2, PoolThreadCache cache2) {
        init0(chunk2, handle2, offset2, length2, maxLength2, cache2);
    }

    /* access modifiers changed from: package-private */
    public void initUnpooled(PoolChunk<T> chunk2, int length2) {
        init0(chunk2, 0, chunk2.offset, length2, length2, (PoolThreadCache) null);
    }

    private void init0(PoolChunk<T> chunk2, long handle2, int offset2, int length2, int maxLength2, PoolThreadCache cache2) {
        if (handle2 < 0) {
            throw new AssertionError();
        } else if (chunk2 != null) {
            this.chunk = chunk2;
            this.memory = chunk2.memory;
            this.allocator = chunk2.arena.parent;
            this.cache = cache2;
            this.handle = handle2;
            this.offset = offset2;
            this.length = length2;
            this.maxLength = maxLength2;
            this.tmpNioBuf = null;
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: package-private */
    public final void reuse(int maxCapacity) {
        maxCapacity(maxCapacity);
        setRefCnt(1);
        setIndex0(0, 0);
        discardMarks();
    }

    public final int capacity() {
        return this.length;
    }

    public final ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        PoolChunk<T> poolChunk = this.chunk;
        if (!poolChunk.unpooled) {
            int i = this.length;
            if (newCapacity > i) {
                if (newCapacity <= this.maxLength) {
                    this.length = newCapacity;
                    return this;
                }
            } else if (newCapacity >= i) {
                return this;
            } else {
                int i2 = this.maxLength;
                if (newCapacity > (i2 >>> 1)) {
                    if (i2 > 512) {
                        this.length = newCapacity;
                        setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
                        return this;
                    } else if (newCapacity > i2 - 16) {
                        this.length = newCapacity;
                        setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
                        return this;
                    }
                }
            }
        } else if (newCapacity == this.length) {
            return this;
        }
        poolChunk.arena.reallocate(this, newCapacity, true);
        return this;
    }

    public final ByteBufAllocator alloc() {
        return this.allocator;
    }

    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public final ByteBuf unwrap() {
        return null;
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf2 = this.tmpNioBuf;
        if (tmpNioBuf2 != null) {
            return tmpNioBuf2;
        }
        ByteBuffer newInternalNioBuffer = newInternalNioBuffer(this.memory);
        ByteBuffer tmpNioBuf3 = newInternalNioBuffer;
        this.tmpNioBuf = newInternalNioBuffer;
        return tmpNioBuf3;
    }

    /* access modifiers changed from: protected */
    public final void deallocate() {
        if (this.handle >= 0) {
            long handle2 = this.handle;
            this.handle = -1;
            this.memory = null;
            this.tmpNioBuf = null;
            PoolChunk<T> poolChunk = this.chunk;
            poolChunk.arena.free(poolChunk, handle2, this.maxLength, this.cache);
            this.chunk = null;
            recycle();
        }
    }

    private void recycle() {
        Recycler.Handle recyclerHandle2 = this.recyclerHandle;
        if (recyclerHandle2 != null) {
            recycler().recycle(this, recyclerHandle2);
        }
    }

    /* access modifiers changed from: protected */
    public final int idx(int index) {
        return this.offset + index;
    }
}
