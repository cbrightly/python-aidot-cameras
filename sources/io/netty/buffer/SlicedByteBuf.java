package io.netty.buffer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

@Deprecated
public class SlicedByteBuf extends AbstractDerivedByteBuf {
    private final int adjustment;
    private final ByteBuf buffer;
    private final int length;

    public SlicedByteBuf(ByteBuf buffer2, int index, int length2) {
        super(length2);
        if (index < 0 || index > buffer2.capacity() - length2) {
            throw new IndexOutOfBoundsException(buffer2 + ".slice(" + index + ", " + length2 + ')');
        }
        if (buffer2 instanceof SlicedByteBuf) {
            this.buffer = ((SlicedByteBuf) buffer2).buffer;
            this.adjustment = ((SlicedByteBuf) buffer2).adjustment + index;
        } else if (buffer2 instanceof DuplicatedByteBuf) {
            this.buffer = buffer2.unwrap();
            this.adjustment = index;
        } else {
            this.buffer = buffer2;
            this.adjustment = index;
        }
        this.length = length2;
        writerIndex(length2);
    }

    public ByteBuf unwrap() {
        return this.buffer;
    }

    public ByteBufAllocator alloc() {
        return unwrap().alloc();
    }

    @Deprecated
    public ByteOrder order() {
        return unwrap().order();
    }

    public boolean isDirect() {
        return unwrap().isDirect();
    }

    public int capacity() {
        return this.length;
    }

    public ByteBuf capacity(int newCapacity) {
        throw new UnsupportedOperationException("sliced buffer");
    }

    public boolean hasArray() {
        return unwrap().hasArray();
    }

    public byte[] array() {
        return unwrap().array();
    }

    public int arrayOffset() {
        return idx(unwrap().arrayOffset());
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress() + ((long) this.adjustment);
    }

    public byte getByte(int index) {
        checkIndex0(index, 1);
        return unwrap().getByte(idx(index));
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return unwrap().getByte(idx(index));
    }

    public short getShort(int index) {
        checkIndex0(index, 2);
        return unwrap().getShort(idx(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return unwrap().getShort(idx(index));
    }

    public int getUnsignedMedium(int index) {
        checkIndex0(index, 3);
        return unwrap().getUnsignedMedium(idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return unwrap().getUnsignedMedium(idx(index));
    }

    public int getInt(int index) {
        checkIndex0(index, 4);
        return unwrap().getInt(idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return unwrap().getInt(idx(index));
    }

    public long getLong(int index) {
        checkIndex0(index, 8);
        return unwrap().getLong(idx(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return unwrap().getLong(idx(index));
    }

    public ByteBuf duplicate() {
        return unwrap().duplicate().setIndex(idx(readerIndex()), idx(writerIndex()));
    }

    public ByteBuf copy(int index, int length2) {
        checkIndex0(index, length2);
        return unwrap().copy(idx(index), length2);
    }

    public ByteBuf slice(int index, int length2) {
        checkIndex0(index, length2);
        return unwrap().slice(idx(index), length2);
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length2) {
        checkIndex0(index, length2);
        unwrap().getBytes(idx(index), dst, dstIndex, length2);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length2) {
        checkIndex0(index, length2);
        unwrap().getBytes(idx(index), dst, dstIndex, length2);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        checkIndex0(index, dst.remaining());
        unwrap().getBytes(idx(index), dst);
        return this;
    }

    public ByteBuf setByte(int index, int value) {
        checkIndex0(index, 1);
        unwrap().setByte(idx(index), value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        unwrap().setByte(idx(index), value);
    }

    public ByteBuf setShort(int index, int value) {
        checkIndex0(index, 2);
        unwrap().setShort(idx(index), value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        unwrap().setShort(idx(index), value);
    }

    public ByteBuf setMedium(int index, int value) {
        checkIndex0(index, 3);
        unwrap().setMedium(idx(index), value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        unwrap().setMedium(idx(index), value);
    }

    public ByteBuf setInt(int index, int value) {
        checkIndex0(index, 4);
        unwrap().setInt(idx(index), value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        unwrap().setInt(idx(index), value);
    }

    public ByteBuf setLong(int index, long value) {
        checkIndex0(index, 8);
        unwrap().setLong(idx(index), value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        unwrap().setLong(idx(index), value);
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length2) {
        checkIndex0(index, length2);
        unwrap().setBytes(idx(index), src, srcIndex, length2);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length2) {
        checkIndex0(index, length2);
        unwrap().setBytes(idx(index), src, srcIndex, length2);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        checkIndex0(index, src.remaining());
        unwrap().setBytes(idx(index), src);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length2) {
        checkIndex0(index, length2);
        unwrap().getBytes(idx(index), out, length2);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length2) {
        checkIndex0(index, length2);
        return unwrap().getBytes(idx(index), out, length2);
    }

    public int setBytes(int index, InputStream in, int length2) {
        checkIndex0(index, length2);
        return unwrap().setBytes(idx(index), in, length2);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length2) {
        checkIndex0(index, length2);
        return unwrap().setBytes(idx(index), in, length2);
    }

    public int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public ByteBuffer nioBuffer(int index, int length2) {
        checkIndex0(index, length2);
        return unwrap().nioBuffer(idx(index), length2);
    }

    public ByteBuffer[] nioBuffers(int index, int length2) {
        checkIndex0(index, length2);
        return unwrap().nioBuffers(idx(index), length2);
    }

    public int forEachByte(int index, int length2, ByteBufProcessor processor) {
        checkIndex0(index, length2);
        int ret = unwrap().forEachByte(idx(index), length2, processor);
        int i = this.adjustment;
        if (ret >= i) {
            return ret - i;
        }
        return -1;
    }

    public int forEachByteDesc(int index, int length2, ByteBufProcessor processor) {
        checkIndex0(index, length2);
        int ret = unwrap().forEachByteDesc(idx(index), length2, processor);
        int i = this.adjustment;
        if (ret >= i) {
            return ret - i;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public final int idx(int index) {
        return this.adjustment + index;
    }
}
