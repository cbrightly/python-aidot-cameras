package io.netty.buffer;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    byte[] array;
    private ByteBuffer tmpNioBuf;

    public UnpooledHeapByteBuf(ByteBufAllocator alloc2, int initialCapacity, int maxCapacity) {
        super(maxCapacity);
        ObjectUtil.checkNotNull(alloc2, "alloc");
        if (initialCapacity <= maxCapacity) {
            this.alloc = alloc2;
            setArray(allocateArray(initialCapacity));
            setIndex(0, 0);
            return;
        }
        throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
    }

    protected UnpooledHeapByteBuf(ByteBufAllocator alloc2, byte[] initialArray, int maxCapacity) {
        super(maxCapacity);
        ObjectUtil.checkNotNull(alloc2, "alloc");
        ObjectUtil.checkNotNull(initialArray, "initialArray");
        if (initialArray.length <= maxCapacity) {
            this.alloc = alloc2;
            setArray(initialArray);
            setIndex(0, initialArray.length);
            return;
        }
        throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialArray.length), Integer.valueOf(maxCapacity)}));
    }

    /* access modifiers changed from: package-private */
    public byte[] allocateArray(int initialCapacity) {
        return new byte[initialCapacity];
    }

    /* access modifiers changed from: package-private */
    public void freeArray(byte[] array2) {
    }

    private void setArray(byte[] initialArray) {
        this.array = initialArray;
        this.tmpNioBuf = null;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public boolean isDirect() {
        return false;
    }

    public int capacity() {
        ensureAccessible();
        return this.array.length;
    }

    public ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        int oldCapacity = this.array.length;
        byte[] oldArray = this.array;
        if (newCapacity > oldCapacity) {
            byte[] newArray = allocateArray(newCapacity);
            System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
            setArray(newArray);
            freeArray(oldArray);
        } else if (newCapacity < oldCapacity) {
            byte[] newArray2 = allocateArray(newCapacity);
            int readerIndex = readerIndex();
            if (readerIndex < newCapacity) {
                int writerIndex = writerIndex();
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    writerIndex(newCapacity);
                }
                System.arraycopy(oldArray, readerIndex, newArray2, readerIndex, writerIndex - readerIndex);
            } else {
                setIndex(newCapacity, newCapacity);
            }
            setArray(newArray2);
            freeArray(oldArray);
        }
        return this;
    }

    public boolean hasArray() {
        return true;
    }

    public byte[] array() {
        ensureAccessible();
        return this.array;
    }

    public int arrayOffset() {
        return 0;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.array, index, dst.memoryAddress() + ((long) dstIndex), (long) length);
        } else if (dst.hasArray()) {
            getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        } else {
            dst.setBytes(dstIndex, this.array, index, length);
        }
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        System.arraycopy(this.array, index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        checkIndex(index, dst.remaining());
        dst.put(this.array, index, dst.remaining());
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) {
        ensureAccessible();
        out.write(this.array, index, length);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        ensureAccessible();
        return getBytes(index, out, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) {
        ByteBuffer tmpBuf;
        ensureAccessible();
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = ByteBuffer.wrap(this.array);
        }
        return out.write((ByteBuffer) tmpBuf.clear().position(index).limit(index + length));
    }

    public int readBytes(GatheringByteChannel out, int length) {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.hasMemoryAddress()) {
            PlatformDependent.copyMemory(src.memoryAddress() + ((long) srcIndex), this.array, index, (long) length);
        } else if (src.hasArray()) {
            setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
        } else {
            src.getBytes(srcIndex, this.array, index, length);
        }
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        System.arraycopy(src, srcIndex, this.array, index, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        ensureAccessible();
        src.get(this.array, index, src.remaining());
        return this;
    }

    public int setBytes(int index, InputStream in, int length) {
        ensureAccessible();
        return in.read(this.array, index, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        ensureAccessible();
        try {
            return in.read((ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length));
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        ensureAccessible();
        return ByteBuffer.wrap(this.array, index, length).slice();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        return (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
    }

    public byte getByte(int index) {
        ensureAccessible();
        return _getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return HeapByteBufUtil.getByte(this.array, index);
    }

    public short getShort(int index) {
        ensureAccessible();
        return _getShort(index);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return HeapByteBufUtil.getShort(this.array, index);
    }

    public int getUnsignedMedium(int index) {
        ensureAccessible();
        return _getUnsignedMedium(index);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return HeapByteBufUtil.getUnsignedMedium(this.array, index);
    }

    public int getInt(int index) {
        ensureAccessible();
        return _getInt(index);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return HeapByteBufUtil.getInt(this.array, index);
    }

    public long getLong(int index) {
        ensureAccessible();
        return _getLong(index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return HeapByteBufUtil.getLong(this.array, index);
    }

    public ByteBuf setByte(int index, int value) {
        ensureAccessible();
        _setByte(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        HeapByteBufUtil.setByte(this.array, index, value);
    }

    public ByteBuf setShort(int index, int value) {
        ensureAccessible();
        _setShort(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        HeapByteBufUtil.setShort(this.array, index, value);
    }

    public ByteBuf setMedium(int index, int value) {
        ensureAccessible();
        _setMedium(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        HeapByteBufUtil.setMedium(this.array, index, value);
    }

    public ByteBuf setInt(int index, int value) {
        ensureAccessible();
        _setInt(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        HeapByteBufUtil.setInt(this.array, index, value);
    }

    public ByteBuf setLong(int index, long value) {
        ensureAccessible();
        _setLong(index, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        HeapByteBufUtil.setLong(this.array, index, value);
    }

    public ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        byte[] copiedArray = new byte[length];
        System.arraycopy(this.array, index, copiedArray, 0, length);
        return new UnpooledHeapByteBuf(alloc(), copiedArray, maxCapacity());
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf2 = this.tmpNioBuf;
        if (tmpNioBuf2 != null) {
            return tmpNioBuf2;
        }
        ByteBuffer wrap = ByteBuffer.wrap(this.array);
        ByteBuffer tmpNioBuf3 = wrap;
        this.tmpNioBuf = wrap;
        return tmpNioBuf3;
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        freeArray(this.array);
        this.array = null;
    }

    public ByteBuf unwrap() {
        return null;
    }
}
