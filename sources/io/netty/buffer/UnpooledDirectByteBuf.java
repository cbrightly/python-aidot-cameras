package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.glassfish.grizzly.http.server.util.MappingData;

public class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private int capacity;
    private boolean doNotFree;
    private ByteBuffer tmpNioBuf;

    public UnpooledDirectByteBuf(ByteBufAllocator alloc2, int initialCapacity, int maxCapacity) {
        super(maxCapacity);
        if (alloc2 == null) {
            throw new NullPointerException("alloc");
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
        } else if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
        } else if (initialCapacity <= maxCapacity) {
            this.alloc = alloc2;
            setByteBuffer(ByteBuffer.allocateDirect(initialCapacity));
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        }
    }

    protected UnpooledDirectByteBuf(ByteBufAllocator alloc2, ByteBuffer initialBuffer, int maxCapacity) {
        super(maxCapacity);
        if (alloc2 == null) {
            throw new NullPointerException("alloc");
        } else if (initialBuffer == null) {
            throw new NullPointerException("initialBuffer");
        } else if (!initialBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        } else if (!initialBuffer.isReadOnly()) {
            int initialCapacity = initialBuffer.remaining();
            if (initialCapacity <= maxCapacity) {
                this.alloc = alloc2;
                this.doNotFree = true;
                setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
                writerIndex(initialCapacity);
                return;
            }
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        } else {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuffer allocateDirect(int initialCapacity) {
        return ByteBuffer.allocateDirect(initialCapacity);
    }

    /* access modifiers changed from: protected */
    public void freeDirect(ByteBuffer buffer2) {
        PlatformDependent.freeDirectBuffer(buffer2);
    }

    private void setByteBuffer(ByteBuffer buffer2) {
        ByteBuffer oldBuffer = this.buffer;
        if (oldBuffer != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                freeDirect(oldBuffer);
            }
        }
        this.buffer = buffer2;
        this.tmpNioBuf = null;
        this.capacity = buffer2.remaining();
    }

    public boolean isDirect() {
        return true;
    }

    public int capacity() {
        return this.capacity;
    }

    public ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int oldCapacity = this.capacity;
        if (newCapacity > oldCapacity) {
            ByteBuffer oldBuffer = this.buffer;
            ByteBuffer newBuffer = allocateDirect(newCapacity);
            oldBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.put(oldBuffer);
            newBuffer.clear();
            setByteBuffer(newBuffer);
        } else if (newCapacity < oldCapacity) {
            ByteBuffer oldBuffer2 = this.buffer;
            ByteBuffer newBuffer2 = allocateDirect(newCapacity);
            if (readerIndex < newCapacity) {
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    writerIndex(newCapacity);
                }
                oldBuffer2.position(readerIndex).limit(writerIndex);
                newBuffer2.position(readerIndex).limit(writerIndex);
                newBuffer2.put(oldBuffer2);
                newBuffer2.clear();
            } else {
                setIndex(newCapacity, newCapacity);
            }
            setByteBuffer(newBuffer2);
        }
        return this;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
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
        getBytes(index, dst, dstIndex, length, false);
        return this;
    }

    private void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
        ByteBuffer tmpBuf;
        checkDstIndex(index, length, dstIndex, dst.length);
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
    }

    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst, dstIndex, length, true);
        this.readerIndex += length;
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        getBytes(index, dst, false);
        return this;
    }

    private void getBytes(int index, ByteBuffer dst, boolean internal) {
        ByteBuffer tmpBuf;
        checkIndex(index, dst.remaining());
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(dst.remaining() + index);
        dst.put(tmpBuf);
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        int length = dst.remaining();
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst, true);
        this.readerIndex += length;
        return this;
    }

    public ByteBuf setByte(int index, int value) {
        ensureAccessible();
        _setByte(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        this.buffer.put(index, (byte) value);
    }

    public ByteBuf setShort(int index, int value) {
        ensureAccessible();
        _setShort(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        this.buffer.putShort(index, (short) value);
    }

    public ByteBuf setMedium(int index, int value) {
        ensureAccessible();
        _setMedium(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        setByte(index, (byte) (value >>> 16));
        setByte(index + 1, (byte) (value >>> 8));
        setByte(index + 2, (byte) value);
    }

    public ByteBuf setInt(int index, int value) {
        ensureAccessible();
        _setInt(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        this.buffer.putInt(index, value);
    }

    public ByteBuf setLong(int index, long value) {
        ensureAccessible();
        _setLong(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        this.buffer.putLong(index, value);
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.nioBufferCount() > 0) {
            for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
                int bbLen = bb.remaining();
                setBytes(index, bb);
                index += bbLen;
            }
        } else {
            src.getBytes(srcIndex, (ByteBuf) this, index, length);
        }
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.put(src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        ensureAccessible();
        ByteBuffer tmpBuf = internalNioBuffer();
        if (src == tmpBuf) {
            src = src.duplicate();
        }
        tmpBuf.clear().position(index).limit(src.remaining() + index);
        tmpBuf.put(src);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        getBytes(index, out, length, false);
        return this;
    }

    private void getBytes(int index, OutputStream out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        ensureAccessible();
        if (length != 0) {
            if (this.buffer.hasArray()) {
                out.write(this.buffer.array(), this.buffer.arrayOffset() + index, length);
                return;
            }
            byte[] tmp = new byte[length];
            if (internal) {
                tmpBuf = internalNioBuffer();
            } else {
                tmpBuf = this.buffer.duplicate();
            }
            tmpBuf.clear().position(index);
            tmpBuf.get(tmp);
            out.write(tmp);
        }
    }

    public ByteBuf readBytes(OutputStream out, int length) {
        checkReadableBytes(length);
        getBytes(this.readerIndex, out, length, true);
        this.readerIndex += length;
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        return getBytes(index, out, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        ensureAccessible();
        if (length == 0) {
            return 0;
        }
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + length);
        return out.write(tmpBuf);
    }

    public int readBytes(GatheringByteChannel out, int length) {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    public int setBytes(int index, InputStream in, int length) {
        ensureAccessible();
        if (this.buffer.hasArray()) {
            return in.read(this.buffer.array(), this.buffer.arrayOffset() + index, length);
        }
        byte[] tmp = new byte[length];
        int readBytes = in.read(tmp);
        if (readBytes <= 0) {
            return readBytes;
        }
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index);
        tmpBuf.put(tmp, 0, readBytes);
        return readBytes;
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        ensureAccessible();
        internalNioBuffer().clear().position(index).limit(index + length);
        try {
            return in.read(this.tmpNioBuf);
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuf copy(int index, int length) {
        ensureAccessible();
        try {
            return alloc().directBuffer(length, maxCapacity()).writeBytes((ByteBuffer) this.buffer.duplicate().clear().position(index).limit(index + length));
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
        }
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        return (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf2 = this.tmpNioBuf;
        if (tmpNioBuf2 != null) {
            return tmpNioBuf2;
        }
        ByteBuffer duplicate = this.buffer.duplicate();
        ByteBuffer tmpNioBuf3 = duplicate;
        this.tmpNioBuf = duplicate;
        return tmpNioBuf3;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        return ((ByteBuffer) this.buffer.duplicate().position(index).limit(index + length)).slice();
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        ByteBuffer buffer2 = this.buffer;
        if (buffer2 != null) {
            this.buffer = null;
            if (!this.doNotFree) {
                freeDirect(buffer2);
            }
        }
    }

    public ByteBuf unwrap() {
        return null;
    }
}
