package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class PooledHeapByteBuf extends PooledByteBuf<byte[]> {
    private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledHeapByteBuf newObject(Recycler.Handle handle) {
            return new PooledHeapByteBuf(handle, 0);
        }
    };

    static PooledHeapByteBuf newInstance(int maxCapacity) {
        PooledHeapByteBuf buf = RECYCLER.get();
        buf.reuse(maxCapacity);
        return buf;
    }

    PooledHeapByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
        super(recyclerHandle, maxCapacity);
    }

    public final boolean isDirect() {
        return false;
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return HeapByteBufUtil.getByte((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return HeapByteBufUtil.getShort((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return HeapByteBufUtil.getUnsignedMedium((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return HeapByteBufUtil.getInt((byte[]) this.memory, idx(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return HeapByteBufUtil.getLong((byte[]) this.memory, idx(index));
    }

    public final ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory((byte[]) this.memory, idx(index), dst.memoryAddress() + ((long) dstIndex), (long) length);
        } else if (dst.hasArray()) {
            getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        } else {
            dst.setBytes(dstIndex, (byte[]) this.memory, idx(index), length);
        }
        return this;
    }

    public final ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        System.arraycopy(this.memory, idx(index), dst, dstIndex, length);
        return this;
    }

    public final ByteBuf getBytes(int index, ByteBuffer dst) {
        checkIndex(index, dst.remaining());
        dst.put((byte[]) this.memory, idx(index), dst.remaining());
        return this;
    }

    public final ByteBuf getBytes(int index, OutputStream out, int length) {
        checkIndex(index, length);
        out.write((byte[]) this.memory, idx(index), length);
        return this;
    }

    public final int getBytes(int index, GatheringByteChannel out, int length) {
        return getBytes(index, out, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        checkIndex(index, length);
        int index2 = idx(index);
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = ByteBuffer.wrap((byte[]) this.memory);
        }
        return out.write((ByteBuffer) tmpBuf.clear().position(index2).limit(index2 + length));
    }

    public final int readBytes(GatheringByteChannel out, int length) {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        HeapByteBufUtil.setByte((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        HeapByteBufUtil.setShort((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        HeapByteBufUtil.setMedium((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        HeapByteBufUtil.setInt((byte[]) this.memory, idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        HeapByteBufUtil.setLong((byte[]) this.memory, idx(index), value);
    }

    public final ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.hasMemoryAddress()) {
            PlatformDependent.copyMemory(src.memoryAddress() + ((long) srcIndex), (byte[]) this.memory, idx(index), (long) length);
        } else if (src.hasArray()) {
            setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
        } else {
            src.getBytes(srcIndex, (byte[]) this.memory, idx(index), length);
        }
        return this;
    }

    public final ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        System.arraycopy(src, srcIndex, this.memory, idx(index), length);
        return this;
    }

    public final ByteBuf setBytes(int index, ByteBuffer src) {
        int length = src.remaining();
        checkIndex(index, length);
        src.get((byte[]) this.memory, idx(index), length);
        return this;
    }

    public final int setBytes(int index, InputStream in, int length) {
        checkIndex(index, length);
        return in.read((byte[]) this.memory, idx(index), length);
    }

    public final int setBytes(int index, ScatteringByteChannel in, int length) {
        checkIndex(index, length);
        int index2 = idx(index);
        try {
            return in.read((ByteBuffer) internalNioBuffer().clear().position(index2).limit(index2 + length));
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public final ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        ByteBuf copy = alloc().heapBuffer(length, maxCapacity());
        copy.writeBytes((byte[]) this.memory, idx(index), length);
        return copy;
    }

    public final int nioBufferCount() {
        return 1;
    }

    public final ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public final ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        return ByteBuffer.wrap((byte[]) this.memory, idx(index), length).slice();
    }

    public final ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        int index2 = idx(index);
        return (ByteBuffer) internalNioBuffer().clear().position(index2).limit(index2 + length);
    }

    public final boolean hasArray() {
        return true;
    }

    public final byte[] array() {
        ensureAccessible();
        return (byte[]) this.memory;
    }

    public final int arrayOffset() {
        return this.offset;
    }

    public final boolean hasMemoryAddress() {
        return false;
    }

    public final long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer newInternalNioBuffer(byte[] memory) {
        return ByteBuffer.wrap(memory);
    }

    /* access modifiers changed from: protected */
    public Recycler<?> recycler() {
        return RECYCLER;
    }
}
