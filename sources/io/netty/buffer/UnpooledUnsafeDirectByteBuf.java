package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class UnpooledUnsafeDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    ByteBuffer buffer;
    private int capacity;
    private boolean doNotFree;
    long memoryAddress;
    private ByteBuffer tmpNioBuf;

    public UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc2, int initialCapacity, int maxCapacity) {
        super(maxCapacity);
        if (alloc2 == null) {
            throw new NullPointerException("alloc");
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
        } else if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
        } else if (initialCapacity <= maxCapacity) {
            this.alloc = alloc2;
            setByteBuffer(allocateDirect(initialCapacity), false);
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        }
    }

    protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc2, ByteBuffer initialBuffer, int maxCapacity) {
        this(alloc2, initialBuffer.slice(), maxCapacity, false);
    }

    UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc2, ByteBuffer initialBuffer, int maxCapacity, boolean doFree) {
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
                this.doNotFree = !doFree;
                setByteBuffer(initialBuffer.order(ByteOrder.BIG_ENDIAN), false);
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

    /* access modifiers changed from: package-private */
    public final void setByteBuffer(ByteBuffer buffer2, boolean tryFree) {
        ByteBuffer oldBuffer;
        if (tryFree && (oldBuffer = this.buffer) != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                freeDirect(oldBuffer);
            }
        }
        this.buffer = buffer2;
        this.memoryAddress = PlatformDependent.directBufferAddress(buffer2);
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
            setByteBuffer(newBuffer, true);
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
            setByteBuffer(newBuffer2, true);
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
        return true;
    }

    public long memoryAddress() {
        ensureAccessible();
        return this.memoryAddress;
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return UnsafeByteBufUtil.getByte(addr(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return UnsafeByteBufUtil.getShort(addr(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return UnsafeByteBufUtil.getUnsignedMedium(addr(index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return UnsafeByteBufUtil.getInt(addr(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return UnsafeByteBufUtil.getLong(addr(index));
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(index), index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf) this, addr(index), index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        UnsafeByteBufUtil.getBytes(this, addr(index), index, dst);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        int length = dst.remaining();
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst);
        this.readerIndex += length;
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte(addr(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        UnsafeByteBufUtil.setShort(addr(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        UnsafeByteBufUtil.setMedium(addr(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        UnsafeByteBufUtil.setInt(addr(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        UnsafeByteBufUtil.setLong(addr(index), value);
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(index), index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf) this, addr(index), index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        UnsafeByteBufUtil.setBytes(this, addr(index), index, src);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        UnsafeByteBufUtil.getBytes(this, addr(index), index, out, length);
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
        return UnsafeByteBufUtil.setBytes(this, addr(index), index, in, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        ensureAccessible();
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        try {
            return in.read(tmpBuf);
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
        return UnsafeByteBufUtil.copy(this, addr(index), index, length);
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

    /* access modifiers changed from: package-private */
    public long addr(int index) {
        return this.memoryAddress + ((long) index);
    }

    /* access modifiers changed from: protected */
    public SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeDirectSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }

    public ByteBuf setZero(int index, int length) {
        checkIndex(index, length);
        UnsafeByteBufUtil.setZero(addr(index), length);
        return this;
    }

    public ByteBuf writeZero(int length) {
        ensureWritable(length);
        int wIndex = this.writerIndex;
        UnsafeByteBufUtil.setZero(addr(wIndex), length);
        this.writerIndex = wIndex + length;
        return this;
    }
}
