package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public class UnpooledUnsafeHeapByteBuf extends UnpooledHeapByteBuf {
    UnpooledUnsafeHeapByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(alloc, initialCapacity, maxCapacity);
    }

    /* access modifiers changed from: package-private */
    public byte[] allocateArray(int initialCapacity) {
        return PlatformDependent.allocateUninitializedArray(initialCapacity);
    }

    public byte getByte(int index) {
        checkIndex(index);
        return _getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return UnsafeByteBufUtil.getByte(this.array, index);
    }

    public short getShort(int index) {
        checkIndex(index, 2);
        return _getShort(index);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return UnsafeByteBufUtil.getShort(this.array, index);
    }

    public int getUnsignedMedium(int index) {
        checkIndex(index, 3);
        return _getUnsignedMedium(index);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return UnsafeByteBufUtil.getUnsignedMedium(this.array, index);
    }

    public int getInt(int index) {
        checkIndex(index, 4);
        return _getInt(index);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return UnsafeByteBufUtil.getInt(this.array, index);
    }

    public long getLong(int index) {
        checkIndex(index, 8);
        return _getLong(index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return UnsafeByteBufUtil.getLong(this.array, index);
    }

    public ByteBuf setByte(int index, int value) {
        checkIndex(index);
        _setByte(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte(this.array, index, value);
    }

    public ByteBuf setShort(int index, int value) {
        checkIndex(index, 2);
        _setShort(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        UnsafeByteBufUtil.setShort(this.array, index, value);
    }

    public ByteBuf setMedium(int index, int value) {
        checkIndex(index, 3);
        _setMedium(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        UnsafeByteBufUtil.setMedium(this.array, index, value);
    }

    public ByteBuf setInt(int index, int value) {
        checkIndex(index, 4);
        _setInt(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        UnsafeByteBufUtil.setInt(this.array, index, value);
    }

    public ByteBuf setLong(int index, long value) {
        checkIndex(index, 8);
        _setLong(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        UnsafeByteBufUtil.setLong(this.array, index, value);
    }

    public ByteBuf setZero(int index, int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.setZero(index, length);
        }
        checkIndex(index, length);
        UnsafeByteBufUtil.setZero(this.array, index, length);
        return this;
    }

    public ByteBuf writeZero(int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.writeZero(length);
        }
        ensureWritable(length);
        int wIndex = this.writerIndex;
        UnsafeByteBufUtil.setZero(this.array, wIndex, length);
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
