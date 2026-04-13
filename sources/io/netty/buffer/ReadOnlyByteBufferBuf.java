package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.glassfish.grizzly.http.server.util.MappingData;

public class ReadOnlyByteBufferBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator allocator;
    protected final ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;

    ReadOnlyByteBufferBuf(ByteBufAllocator allocator2, ByteBuffer buffer2) {
        super(buffer2.remaining());
        if (buffer2.isReadOnly()) {
            this.allocator = allocator2;
            ByteBuffer order = buffer2.slice().order(ByteOrder.BIG_ENDIAN);
            this.buffer = order;
            writerIndex(order.limit());
            return;
        }
        throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName((Object) buffer2));
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
    }

    public byte getByte(int index) {
        ensureAccessible();
        return _getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return this.buffer.get(index);
    }

    public short getShort(int index) {
        ensureAccessible();
        return _getShort(index);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return this.buffer.getShort(index);
    }

    public int getUnsignedMedium(int index) {
        ensureAccessible();
        return _getUnsignedMedium(index);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return ((getByte(index) & 255) << MappingData.PATH) | ((getByte(index + 1) & 255) << 8) | (getByte(index + 2) & 255);
    }

    public int getInt(int index) {
        ensureAccessible();
        return _getInt(index);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return this.buffer.getInt(index);
    }

    public long getLong(int index) {
        ensureAccessible();
        return _getLong(index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return this.buffer.getLong(index);
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasArray()) {
            getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        } else if (dst.nioBufferCount() > 0) {
            for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
                int bbLen = bb.remaining();
                getBytes(index, bb);
                index += bbLen;
            }
        } else {
            dst.setBytes(dstIndex, (ByteBuf) this, index, length);
        }
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        if (dstIndex < 0 || dstIndex > dst.length - length) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[]{Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length)}));
        }
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        checkIndex(index);
        if (dst != null) {
            int bytesToCopy = Math.min(capacity() - index, dst.remaining());
            ByteBuffer tmpBuf = internalNioBuffer();
            tmpBuf.clear().position(index).limit(index + bytesToCopy);
            dst.put(tmpBuf);
            return this;
        }
        throw new NullPointerException("dst");
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

    public int capacity() {
        return maxCapacity();
    }

    public ByteBuf capacity(int newCapacity) {
        throw new ReadOnlyBufferException();
    }

    public ByteBufAllocator alloc() {
        return this.allocator;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public ByteBuf unwrap() {
        return null;
    }

    public boolean isDirect() {
        return this.buffer.isDirect();
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        ensureAccessible();
        if (length == 0) {
            return this;
        }
        if (this.buffer.hasArray()) {
            out.write(this.buffer.array(), this.buffer.arrayOffset() + index, length);
        } else {
            byte[] tmp = new byte[length];
            ByteBuffer tmpBuf = internalNioBuffer();
            tmpBuf.clear().position(index);
            tmpBuf.get(tmp);
            out.write(tmp);
        }
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        ensureAccessible();
        if (length == 0) {
            return 0;
        }
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        return out.write(tmpBuf);
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

    public int setBytes(int index, InputStream in, int length) {
        throw new ReadOnlyBufferException();
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        throw new ReadOnlyBufferException();
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf2 = this.tmpNioBuf;
        if (tmpNioBuf2 != null) {
            return tmpNioBuf2;
        }
        ByteBuffer duplicate = this.buffer.duplicate();
        ByteBuffer tmpNioBuf3 = duplicate;
        this.tmpNioBuf = duplicate;
        return tmpNioBuf3;
    }

    public ByteBuf copy(int index, int length) {
        ensureAccessible();
        try {
            ByteBuffer src = (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
            ByteBuf dst = src.isDirect() ? alloc().directBuffer(length) : alloc().heapBuffer(length);
            dst.writeBytes(src);
            return dst;
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
        }
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuffer nioBuffer(int index, int length) {
        return (ByteBuffer) this.buffer.duplicate().position(index).limit(index + length);
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        ensureAccessible();
        return (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
    }

    public boolean hasArray() {
        return this.buffer.hasArray();
    }

    public byte[] array() {
        return this.buffer.array();
    }

    public int arrayOffset() {
        return this.buffer.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
}
