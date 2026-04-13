package io.netty.buffer;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.RecyclableArrayList;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Collections;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class FixedCompositeByteBuf extends AbstractReferenceCountedByteBuf {
    private static final ByteBuf[] EMPTY = {Unpooled.EMPTY_BUFFER};
    private final ByteBufAllocator allocator;
    private final Object[] buffers;
    private final int capacity;
    private final boolean direct;
    private final int nioBufferCount;
    private final ByteOrder order;

    FixedCompositeByteBuf(ByteBufAllocator allocator2, ByteBuf... buffers2) {
        super(Integer.MAX_VALUE);
        if (buffers2.length == 0) {
            this.buffers = EMPTY;
            this.order = ByteOrder.BIG_ENDIAN;
            this.nioBufferCount = 1;
            this.capacity = 0;
            this.direct = false;
        } else {
            ByteBuf b = buffers2[0];
            Object[] objArr = new Object[buffers2.length];
            this.buffers = objArr;
            objArr[0] = b;
            boolean direct2 = true;
            int nioBufferCount2 = b.nioBufferCount();
            int capacity2 = b.readableBytes();
            this.order = b.order();
            int i = 1;
            while (i < buffers2.length) {
                ByteBuf b2 = buffers2[i];
                if (buffers2[i].order() == this.order) {
                    nioBufferCount2 += b2.nioBufferCount();
                    capacity2 += b2.readableBytes();
                    if (!b2.isDirect()) {
                        direct2 = false;
                    }
                    this.buffers[i] = b2;
                    i++;
                } else {
                    throw new IllegalArgumentException("All ByteBufs need to have same ByteOrder");
                }
            }
            this.nioBufferCount = nioBufferCount2;
            this.capacity = capacity2;
            this.direct = direct2;
        }
        setIndex(0, capacity());
        this.allocator = allocator2;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int size) {
        return false;
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

    public int capacity() {
        return this.capacity;
    }

    public int maxCapacity() {
        return this.capacity;
    }

    public ByteBuf capacity(int newCapacity) {
        throw new ReadOnlyBufferException();
    }

    public ByteBufAllocator alloc() {
        return this.allocator;
    }

    public ByteOrder order() {
        return this.order;
    }

    public ByteBuf unwrap() {
        return null;
    }

    public boolean isDirect() {
        return this.direct;
    }

    private Component findComponent(int index) {
        boolean isBuffer;
        ByteBuf b;
        int readable = 0;
        int i = 0;
        while (true) {
            Object[] objArr = this.buffers;
            if (i < objArr.length) {
                Component comp = null;
                Object obj = objArr[i];
                if (obj instanceof ByteBuf) {
                    b = (ByteBuf) obj;
                    isBuffer = true;
                } else {
                    comp = (Component) obj;
                    b = comp.buf;
                    isBuffer = false;
                }
                readable += b.readableBytes();
                if (index >= readable) {
                    i++;
                } else if (!isBuffer) {
                    return comp;
                } else {
                    Component comp2 = new Component(i, readable - b.readableBytes(), b);
                    this.buffers[i] = comp2;
                    return comp2;
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private ByteBuf buffer(int i) {
        Object obj = this.buffers[i];
        if (obj instanceof ByteBuf) {
            return (ByteBuf) obj;
        }
        return ((Component) obj).buf;
    }

    public byte getByte(int index) {
        return _getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        Component c = findComponent(index);
        return c.buf.getByte(index - c.offset);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        Component c = findComponent(index);
        if (index + 2 <= c.endOffset) {
            return c.buf.getShort(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(index) & 255) << 8) | (_getByte(index + 1) & 255));
        }
        return (short) ((_getByte(index) & 255) | ((_getByte(index + 1) & 255) << 8));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        Component c = findComponent(index);
        if (index + 3 <= c.endOffset) {
            return c.buf.getUnsignedMedium(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShort(index) & 65535) << 8) | (_getByte(index + 2) & 255);
        }
        return (_getShort(index) & 65535) | ((_getByte(index + 2) & 255) << MappingData.PATH);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        Component c = findComponent(index);
        if (index + 4 <= c.endOffset) {
            return c.buf.getInt(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShort(index) & 65535) << 16) | (_getShort(index + 2) & 65535);
        }
        return (_getShort(index) & 65535) | ((_getShort(index + 2) & 65535) << 16);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        Component c = findComponent(index);
        if (index + 8 <= c.endOffset) {
            return c.buf.getLong(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((((long) _getInt(index)) & 4294967295L) << 32) | (((long) _getInt(index + 4)) & 4294967295L);
        }
        return (((long) _getInt(index)) & 4294967295L) | ((4294967295L & ((long) _getInt(index + 4))) << 32);
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        if (length == 0) {
            return this;
        }
        Component c = findComponent(index);
        int i = c.index;
        int adjustment = c.offset;
        ByteBuf s = c.buf;
        while (true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
                return this;
            }
            i++;
            s = buffer(i);
        }
    }

    /* JADX INFO: finally extract failed */
    public ByteBuf getBytes(int index, ByteBuffer dst) {
        int limit = dst.limit();
        int length = dst.remaining();
        checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        try {
            Component c = findComponent(index);
            int i = c.index;
            int adjustment = c.offset;
            ByteBuf s = c.buf;
            while (true) {
                int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
                dst.limit(dst.position() + localLength);
                s.getBytes(index - adjustment, dst);
                index += localLength;
                length -= localLength;
                adjustment += s.readableBytes();
                if (length <= 0) {
                    dst.limit(limit);
                    return this;
                }
                i++;
                s = buffer(i);
            }
        } catch (Throwable th) {
            dst.limit(limit);
            throw th;
        }
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (length == 0) {
            return this;
        }
        Component c = findComponent(index);
        int i = c.index;
        int adjustment = c.offset;
        ByteBuf s = c.buf;
        while (true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
                return this;
            }
            i++;
            s = buffer(i);
        }
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        if (nioBufferCount() == 1) {
            return out.write(internalNioBuffer(index, length));
        }
        long writtenBytes = out.write(nioBuffers(index, length));
        if (writtenBytes > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) writtenBytes;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        Component c = findComponent(index);
        int i = c.index;
        int adjustment = c.offset;
        ByteBuf s = c.buf;
        while (true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, out, localLength);
            index += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
                return this;
            }
            i++;
            s = buffer(i);
        }
    }

    public ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        boolean release = true;
        ByteBuf buf = alloc().buffer(length);
        try {
            buf.writeBytes((ByteBuf) this, index, length);
            release = false;
            return buf;
        } finally {
            if (release) {
                buf.release();
            }
        }
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        if (this.buffers.length == 1) {
            ByteBuf buf = buffer(0);
            if (buf.nioBufferCount() == 1) {
                return buf.nioBuffer(index, length);
            }
        }
        ByteBuffer merged = ByteBuffer.allocate(length).order(order());
        ByteBuffer[] buffers2 = nioBuffers(index, length);
        for (ByteBuffer put : buffers2) {
            merged.put(put);
        }
        merged.flip();
        return merged;
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        if (this.buffers.length == 1) {
            return buffer(0).internalNioBuffer(index, length);
        }
        throw new UnsupportedOperationException();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return EmptyArrays.EMPTY_BYTE_BUFFERS;
        }
        RecyclableArrayList array = RecyclableArrayList.newInstance(this.buffers.length);
        try {
            Component c = findComponent(index);
            int i = c.index;
            int adjustment = c.offset;
            ByteBuf s = c.buf;
            while (true) {
                int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
                switch (s.nioBufferCount()) {
                    case 0:
                        throw new UnsupportedOperationException();
                    case 1:
                        array.add(s.nioBuffer(index - adjustment, localLength));
                        break;
                    default:
                        Collections.addAll(array, s.nioBuffers(index - adjustment, localLength));
                        break;
                }
                index += localLength;
                length -= localLength;
                adjustment += s.readableBytes();
                if (length <= 0) {
                    return (ByteBuffer[]) array.toArray(new ByteBuffer[array.size()]);
                }
                i++;
                s = buffer(i);
            }
        } finally {
            array.recycle();
        }
    }

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        for (int i = 0; i < this.buffers.length; i++) {
            buffer(i).release();
        }
    }

    public String toString() {
        String result = super.toString();
        String result2 = result.substring(0, result.length() - 1);
        return result2 + ", components=" + this.buffers.length + ')';
    }

    public static final class Component {
        /* access modifiers changed from: private */
        public final ByteBuf buf;
        /* access modifiers changed from: private */
        public final int endOffset;
        /* access modifiers changed from: private */
        public final int index;
        /* access modifiers changed from: private */
        public final int offset;

        Component(int index2, int offset2, ByteBuf buf2) {
            this.index = index2;
            this.offset = offset2;
            this.endOffset = buf2.readableBytes() + offset2;
            this.buf = buf2;
        }
    }
}
