package io.netty.buffer;

import io.netty.util.Recycler;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class PooledDirectByteBuf extends PooledByteBuf<ByteBuffer> {
    private static final Recycler<PooledDirectByteBuf> RECYCLER = new Recycler<PooledDirectByteBuf>() {
        /* access modifiers changed from: protected */
        public PooledDirectByteBuf newObject(Recycler.Handle handle) {
            return new PooledDirectByteBuf(handle, 0);
        }
    };

    static PooledDirectByteBuf newInstance(int maxCapacity) {
        PooledDirectByteBuf buf = RECYCLER.get();
        buf.reuse(maxCapacity);
        return buf;
    }

    private PooledDirectByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
        super(recyclerHandle, maxCapacity);
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
        return ((ByteBuffer) this.memory).get(idx(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return ((ByteBuffer) this.memory).getShort(idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        int index2 = idx(index);
        return ((((ByteBuffer) this.memory).get(index2) & 255) << MappingData.PATH) | ((((ByteBuffer) this.memory).get(index2 + 1) & 255) << 8) | (((ByteBuffer) this.memory).get(index2 + 2) & 255);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return ((ByteBuffer) this.memory).getInt(idx(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return ((ByteBuffer) this.memory).getLong(idx(index));
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
            tmpBuf = this.memory.duplicate();
        }
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(index2 + length);
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
            tmpBuf = this.memory.duplicate();
        }
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(dst.remaining() + index2);
        dst.put(tmpBuf);
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        int length = dst.remaining();
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst, true);
        this.readerIndex += length;
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        getBytes(index, out, length, false);
        return this;
    }

    private void getBytes(int index, OutputStream out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        checkIndex(index, length);
        if (length != 0) {
            byte[] tmp = new byte[length];
            if (internal) {
                tmpBuf = internalNioBuffer();
            } else {
                tmpBuf = this.memory.duplicate();
            }
            tmpBuf.clear().position(idx(index));
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
        ((ByteBuffer) this.memory).put(idx(index), (byte) value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        ((ByteBuffer) this.memory).putShort(idx(index), (short) value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        int index2 = idx(index);
        ((ByteBuffer) this.memory).put(index2, (byte) (value >>> 16));
        ((ByteBuffer) this.memory).put(index2 + 1, (byte) (value >>> 8));
        ((ByteBuffer) this.memory).put(index2 + 2, (byte) value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        ((ByteBuffer) this.memory).putInt(idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        ((ByteBuffer) this.memory).putLong(idx(index), value);
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.hasArray()) {
            setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
        } else if (src.nioBufferCount() > 0) {
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
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(index2 + length);
        tmpBuf.put(src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        checkIndex(index, src.remaining());
        ByteBuffer tmpBuf = internalNioBuffer();
        if (src == tmpBuf) {
            src = src.duplicate();
        }
        int index2 = idx(index);
        tmpBuf.clear().position(index2).limit(src.remaining() + index2);
        tmpBuf.put(src);
        return this;
    }

    public int setBytes(int index, InputStream in, int length) {
        checkIndex(index, length);
        byte[] tmp = new byte[length];
        int readBytes = in.read(tmp);
        if (readBytes <= 0) {
            return readBytes;
        }
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(idx(index));
        tmpBuf.put(tmp, 0, readBytes);
        return readBytes;
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
        checkIndex(index, length);
        ByteBuf copy = alloc().directBuffer(length, maxCapacity());
        copy.writeBytes((ByteBuf) this, index, length);
        return copy;
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        int index2 = idx(index);
        return ((ByteBuffer) ((ByteBuffer) this.memory).duplicate().position(index2).limit(index2 + length)).slice();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
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
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public Recycler<?> recycler() {
        return RECYCLER;
    }
}
