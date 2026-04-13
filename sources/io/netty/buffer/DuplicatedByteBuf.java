package io.netty.buffer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

@Deprecated
public class DuplicatedByteBuf extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;

    public DuplicatedByteBuf(ByteBuf buffer2) {
        super(buffer2.maxCapacity());
        if (buffer2 instanceof DuplicatedByteBuf) {
            this.buffer = ((DuplicatedByteBuf) buffer2).buffer;
        } else {
            this.buffer = buffer2;
        }
        setIndex(buffer2.readerIndex(), buffer2.writerIndex());
        markReaderIndex();
        markWriterIndex();
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
        return unwrap().capacity();
    }

    public ByteBuf capacity(int newCapacity) {
        unwrap().capacity(newCapacity);
        return this;
    }

    public boolean hasArray() {
        return unwrap().hasArray();
    }

    public byte[] array() {
        return unwrap().array();
    }

    public int arrayOffset() {
        return unwrap().arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress();
    }

    public byte getByte(int index) {
        return unwrap().getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return unwrap().getByte(index);
    }

    public short getShort(int index) {
        return unwrap().getShort(index);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return unwrap().getShort(index);
    }

    public int getUnsignedMedium(int index) {
        return unwrap().getUnsignedMedium(index);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return unwrap().getUnsignedMedium(index);
    }

    public int getInt(int index) {
        return unwrap().getInt(index);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return unwrap().getInt(index);
    }

    public long getLong(int index) {
        return unwrap().getLong(index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return unwrap().getLong(index);
    }

    public ByteBuf copy(int index, int length) {
        return unwrap().copy(index, length);
    }

    public ByteBuf slice(int index, int length) {
        return unwrap().slice(index, length);
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        unwrap().getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        unwrap().getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        unwrap().getBytes(index, dst);
        return this;
    }

    public ByteBuf setByte(int index, int value) {
        unwrap().setByte(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        unwrap().setByte(index, value);
    }

    public ByteBuf setShort(int index, int value) {
        unwrap().setShort(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        unwrap().setShort(index, value);
    }

    public ByteBuf setMedium(int index, int value) {
        unwrap().setMedium(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        unwrap().setMedium(index, value);
    }

    public ByteBuf setInt(int index, int value) {
        unwrap().setInt(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        unwrap().setInt(index, value);
    }

    public ByteBuf setLong(int index, long value) {
        unwrap().setLong(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        unwrap().setLong(index, value);
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        unwrap().setBytes(index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        unwrap().setBytes(index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        unwrap().setBytes(index, src);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        unwrap().getBytes(index, out, length);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        return unwrap().getBytes(index, out, length);
    }

    public int setBytes(int index, InputStream in, int length) {
        return unwrap().setBytes(index, in, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        return unwrap().setBytes(index, in, length);
    }

    public int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return unwrap().nioBuffers(index, length);
    }

    public int forEachByte(int index, int length, ByteBufProcessor processor) {
        return unwrap().forEachByte(index, length, processor);
    }

    public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
        return unwrap().forEachByteDesc(index, length, processor);
    }
}
