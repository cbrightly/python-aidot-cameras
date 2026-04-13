package io.netty.buffer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

@Deprecated
public class ReadOnlyByteBuf extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;

    public ReadOnlyByteBuf(ByteBuf buffer2) {
        super(buffer2.maxCapacity());
        if ((buffer2 instanceof ReadOnlyByteBuf) || (buffer2 instanceof DuplicatedByteBuf)) {
            this.buffer = buffer2.unwrap();
        } else {
            this.buffer = buffer2;
        }
        setIndex(buffer2.readerIndex(), buffer2.writerIndex());
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int numBytes) {
        return false;
    }

    public int ensureWritable(int minWritableBytes, boolean force) {
        return 1;
    }

    public ByteBuf ensureWritable(int minWritableBytes) {
        throw new ReadOnlyBufferException();
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

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        throw new ReadOnlyBufferException();
    }

    public int arrayOffset() {
        throw new ReadOnlyBufferException();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public long memoryAddress() {
        return unwrap().memoryAddress();
    }

    public ByteBuf discardReadBytes() {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setByte(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setShort(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setMedium(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setInt(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        throw new ReadOnlyBufferException();
    }

    public ByteBuf setLong(int index, long value) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int index, InputStream in, int length) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        throw new ReadOnlyBufferException();
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        return unwrap().getBytes(index, out, length);
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        unwrap().getBytes(index, out, length);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        unwrap().getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        unwrap().getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        unwrap().getBytes(index, dst);
        return this;
    }

    public ByteBuf duplicate() {
        return new ReadOnlyByteBuf(this);
    }

    public ByteBuf copy(int index, int length) {
        return unwrap().copy(index, length);
    }

    public ByteBuf slice(int index, int length) {
        return Unpooled.unmodifiableBuffer(unwrap().slice(index, length));
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

    public int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public ByteBuffer nioBuffer(int index, int length) {
        return unwrap().nioBuffer(index, length).asReadOnlyBuffer();
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

    public int capacity() {
        return unwrap().capacity();
    }

    public ByteBuf capacity(int newCapacity) {
        throw new ReadOnlyBufferException();
    }
}
