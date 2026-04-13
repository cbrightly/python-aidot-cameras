package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

public final class ReadOnlyUnsafeDirectByteBuf extends ReadOnlyByteBufferBuf {
    private final long memoryAddress = PlatformDependent.directBufferAddress(this.buffer);

    ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator allocator, ByteBuffer byteBuffer) {
        super(allocator, byteBuffer);
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
        checkIndex(index, length);
        if (dst == null) {
            throw new NullPointerException("dst");
        } else if (dstIndex < 0 || dstIndex > dst.capacity() - length) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        } else {
            if (dst.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr(index), dst.memoryAddress() + ((long) dstIndex), (long) length);
            } else if (dst.hasArray()) {
                PlatformDependent.copyMemory(addr(index), dst.array(), dst.arrayOffset() + dstIndex, (long) length);
            } else {
                dst.setBytes(dstIndex, (ByteBuf) this, index, length);
            }
            return this;
        }
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkIndex(index, length);
        if (dst == null) {
            throw new NullPointerException("dst");
        } else if (dstIndex < 0 || dstIndex > dst.length - length) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[]{Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length)}));
        } else {
            if (length != 0) {
                PlatformDependent.copyMemory(addr(index), dst, dstIndex, (long) length);
            }
            return this;
        }
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

    public ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        ByteBuf copy = alloc().directBuffer(length, maxCapacity());
        if (length != 0) {
            if (copy.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr(index), copy.memoryAddress(), (long) length);
                copy.setIndex(0, length);
            } else {
                copy.writeBytes((ByteBuf) this, index, length);
            }
        }
        return copy;
    }

    public boolean hasMemoryAddress() {
        return true;
    }

    public long memoryAddress() {
        return this.memoryAddress;
    }

    private long addr(int index) {
        return this.memoryAddress + ((long) index);
    }
}
