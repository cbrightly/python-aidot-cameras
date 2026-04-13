package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;

public final class PooledUnsafeHeapByteBuf extends PooledHeapByteBuf {
    private static final Recycler<PooledUnsafeHeapByteBuf> RECYCLER = new Recycler<PooledUnsafeHeapByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledUnsafeHeapByteBuf newObject(Recycler.Handle handle) {
            return new PooledUnsafeHeapByteBuf(handle, 0);
        }
    };

    static PooledUnsafeHeapByteBuf newUnsafeInstance(int maxCapacity) {
        PooledUnsafeHeapByteBuf buf = RECYCLER.get();
        buf.reuse(maxCapacity);
        return buf;
    }

    private PooledUnsafeHeapByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
        super(recyclerHandle, maxCapacity);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return UnsafeByteBufUtil.getByte((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return UnsafeByteBufUtil.getShort((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return UnsafeByteBufUtil.getUnsignedMedium((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return UnsafeByteBufUtil.getInt((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return UnsafeByteBufUtil.getLong((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        UnsafeByteBufUtil.setShort((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        UnsafeByteBufUtil.setMedium((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        UnsafeByteBufUtil.setInt((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        UnsafeByteBufUtil.setLong((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public Recycler<?> recycler() {
        return RECYCLER;
    }

    public ByteBuf setZero(int index, int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.setZero(index, length);
        }
        checkIndex(index, length);
        UnsafeByteBufUtil.setZero((byte[]) this.memory, idx(index), length);
        return this;
    }

    public ByteBuf writeZero(int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.writeZero(length);
        }
        ensureWritable(length);
        int wIndex = this.writerIndex;
        UnsafeByteBufUtil.setZero((byte[]) this.memory, idx(wIndex), length);
        this.writerIndex = wIndex + length;
        return this;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeHeapSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }
}
