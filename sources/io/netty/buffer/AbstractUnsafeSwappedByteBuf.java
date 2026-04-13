package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;

public abstract class AbstractUnsafeSwappedByteBuf extends SwappedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean nativeByteOrder;
    private final AbstractByteBuf wrapped;

    /* access modifiers changed from: protected */
    public abstract int _getInt(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract long _getLong(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract short _getShort(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract void _setInt(AbstractByteBuf abstractByteBuf, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void _setLong(AbstractByteBuf abstractByteBuf, int i, long j);

    /* access modifiers changed from: protected */
    public abstract void _setShort(AbstractByteBuf abstractByteBuf, int i, short s);

    static {
        Class<AbstractUnsafeSwappedByteBuf> cls = AbstractUnsafeSwappedByteBuf.class;
    }

    AbstractUnsafeSwappedByteBuf(AbstractByteBuf buf) {
        super(buf);
        if (PlatformDependent.isUnaligned()) {
            this.wrapped = buf;
            this.nativeByteOrder = UnsafeByteBufUtil.BIG_ENDIAN_NATIVE_ORDER != (order() == ByteOrder.BIG_ENDIAN) ? false : true;
            return;
        }
        throw new AssertionError();
    }

    public final long getLong(int index) {
        this.wrapped.checkIndex(index, 8);
        long v = _getLong(this.wrapped, index);
        return this.nativeByteOrder ? v : Long.reverseBytes(v);
    }

    public final float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    public final double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public final char getChar(int index) {
        return (char) getShort(index);
    }

    public final long getUnsignedInt(int index) {
        return ((long) getInt(index)) & 4294967295L;
    }

    public final int getInt(int index) {
        this.wrapped.checkIndex0(index, 4);
        int v = _getInt(this.wrapped, index);
        return this.nativeByteOrder ? v : Integer.reverseBytes(v);
    }

    public final int getUnsignedShort(int index) {
        return getShort(index) & 65535;
    }

    public final short getShort(int index) {
        this.wrapped.checkIndex0(index, 2);
        short v = _getShort(this.wrapped, index);
        return this.nativeByteOrder ? v : Short.reverseBytes(v);
    }

    public final ByteBuf setShort(int index, int value) {
        this.wrapped.checkIndex0(index, 2);
        _setShort(this.wrapped, index, this.nativeByteOrder ? (short) value : Short.reverseBytes((short) value));
        return this;
    }

    public final ByteBuf setInt(int index, int value) {
        this.wrapped.checkIndex0(index, 4);
        _setInt(this.wrapped, index, this.nativeByteOrder ? value : Integer.reverseBytes(value));
        return this;
    }

    public final ByteBuf setLong(int index, long value) {
        this.wrapped.checkIndex(index, 8);
        _setLong(this.wrapped, index, this.nativeByteOrder ? value : Long.reverseBytes(value));
        return this;
    }

    public final ByteBuf setChar(int index, int value) {
        setShort(index, value);
        return this;
    }

    public final ByteBuf setFloat(int index, float value) {
        setInt(index, Float.floatToRawIntBits(value));
        return this;
    }

    public final ByteBuf setDouble(int index, double value) {
        setLong(index, Double.doubleToRawLongBits(value));
        return this;
    }

    public final ByteBuf writeShort(int value) {
        this.wrapped.ensureWritable0(2);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        _setShort(abstractByteBuf, abstractByteBuf.writerIndex, this.nativeByteOrder ? (short) value : Short.reverseBytes((short) value));
        this.wrapped.writerIndex += 2;
        return this;
    }

    public final ByteBuf writeInt(int value) {
        this.wrapped.ensureWritable0(4);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        _setInt(abstractByteBuf, abstractByteBuf.writerIndex, this.nativeByteOrder ? value : Integer.reverseBytes(value));
        this.wrapped.writerIndex += 4;
        return this;
    }

    public final ByteBuf writeLong(long value) {
        this.wrapped.ensureWritable0(8);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        _setLong(abstractByteBuf, abstractByteBuf.writerIndex, this.nativeByteOrder ? value : Long.reverseBytes(value));
        this.wrapped.writerIndex += 8;
        return this;
    }

    public final ByteBuf writeChar(int value) {
        writeShort(value);
        return this;
    }

    public final ByteBuf writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
        return this;
    }

    public final ByteBuf writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
        return this;
    }
}
