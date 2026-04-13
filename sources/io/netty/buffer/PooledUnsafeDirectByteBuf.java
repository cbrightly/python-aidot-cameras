package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public final class PooledUnsafeDirectByteBuf extends PooledByteBuf<ByteBuffer> {
    private static final Recycler<PooledUnsafeDirectByteBuf> RECYCLER = new Recycler<PooledUnsafeDirectByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledUnsafeDirectByteBuf newObject(Recycler.Handle handle) {
            return new PooledUnsafeDirectByteBuf(handle, 0);
        }
    };
    private long memoryAddress;

    static PooledUnsafeDirectByteBuf newInstance(int maxCapacity) {
        PooledUnsafeDirectByteBuf buf = RECYCLER.get();
        buf.reuse(maxCapacity);
        return buf;
    }

    private PooledUnsafeDirectByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
        super(recyclerHandle, maxCapacity);
    }

    /* access modifiers changed from: package-private */
    public void init(PoolChunk<ByteBuffer> chunk, long handle, int offset, int length, int maxLength, PoolThreadCache cache) {
        super.init(chunk, handle, offset, length, maxLength, cache);
        initMemoryAddress();
    }

    /* access modifiers changed from: package-private */
    public void initUnpooled(PoolChunk<ByteBuffer> chunk, int length) {
        super.initUnpooled(chunk, length);
        initMemoryAddress();
    }

    private void initMemoryAddress() {
        this.memoryAddress = PlatformDependent.directBufferAddress((ByteBuffer) this.memory) + ((long) this.offset);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer newInternalNioBuffer(ByteBuffer memory) {
        return memory.duplicate();
    }

    public boolean isDirect() {
        return true;
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

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        UnsafeByteBufUtil.getBytes(this, addr(index), index, out, length);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        return getBytes(index, out, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        checkIndex(index, length);
        if (length == 0) {
            return 0;
        }
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.memory.duplicate();
        }
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(index2 + length);
        return out.write(tmpBuf);
    }

    public int readBytes(GatheringByteChannel out, int length) {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte(addr(index), (byte) value);
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

    public int setBytes(int index, InputStream in, int length) {
        return UnsafeByteBufUtil.setBytes(this, addr(index), index, in, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        checkIndex(index, length);
        ByteBuffer tmpBuf = internalNioBuffer();
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(index2 + length);
        try {
            return in.read(tmpBuf);
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public ByteBuf copy(int index, int length) {
        return UnsafeByteBufUtil.copy(this, addr(index), index, length);
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        int index2 = idx(index);
        return ((ByteBuffer) ((ByteBuffer) this.memory).duplicate().position(index2).limit(index2 + length)).slice();
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        int index2 = idx(index);
        return (ByteBuffer) internalNioBuffer().clear().position(index2).limit(index2 + length);
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

    private long addr(int index) {
        return this.memoryAddress + ((long) index);
    }

    /* access modifiers changed from: protected */
    public Recycler<?> recycler() {
        return RECYCLER;
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
