package org.glassfish.grizzly.nio;

import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.Buffers;

public final class DirectByteBufferRecord {
    private static final ThreadCache.CachedTypeIndex<DirectByteBufferRecord> CACHE_IDX = ThreadCache.obtainIndex("direct-buffer-cache", DirectByteBufferRecord.class, 1);
    private ByteBuffer[] array = new ByteBuffer[8];
    private int arraySize;
    private ByteBuffer directBuffer;
    private ByteBuffer directBufferSlice;
    private int sliceOffset;
    private SoftReference<ByteBuffer> softRef;

    public static DirectByteBufferRecord get() {
        ThreadCache.CachedTypeIndex cachedTypeIndex = CACHE_IDX;
        DirectByteBufferRecord record = (DirectByteBufferRecord) ThreadCache.getFromCache(cachedTypeIndex);
        if (record != null) {
            return record;
        }
        DirectByteBufferRecord recordLocal = new DirectByteBufferRecord();
        ThreadCache.putToCache(cachedTypeIndex, recordLocal);
        return recordLocal;
    }

    DirectByteBufferRecord() {
    }

    public ByteBuffer getDirectBuffer() {
        return this.directBuffer;
    }

    public ByteBuffer getDirectBufferSlice() {
        return this.directBufferSlice;
    }

    public ByteBuffer allocate(int size) {
        ByteBuffer switchToStrong = switchToStrong();
        ByteBuffer byteBuffer = switchToStrong;
        if (switchToStrong != null && byteBuffer.remaining() >= size) {
            return byteBuffer;
        }
        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(size);
        reset(byteBuffer2);
        return byteBuffer2;
    }

    public ByteBuffer sliceBuffer() {
        int oldLim = this.directBuffer.limit();
        ByteBuffer byteBuffer = this.directBuffer;
        Buffers.setPositionLimit(byteBuffer, this.sliceOffset, byteBuffer.capacity());
        this.directBufferSlice = this.directBuffer.slice();
        Buffers.setPositionLimit(this.directBuffer, 0, oldLim);
        return this.directBufferSlice;
    }

    public void finishBufferSlice() {
        ByteBuffer byteBuffer = this.directBufferSlice;
        if (byteBuffer != null) {
            byteBuffer.flip();
            int sliceSz = this.directBufferSlice.remaining();
            this.sliceOffset += sliceSz;
            if (sliceSz > 0) {
                putToArray(this.directBufferSlice);
            }
            this.directBufferSlice = null;
        }
    }

    public ByteBuffer[] getArray() {
        return this.array;
    }

    public int getArraySize() {
        return this.arraySize;
    }

    public void putToArray(ByteBuffer byteBuffer) {
        ensureArraySize();
        ByteBuffer[] byteBufferArr = this.array;
        int i = this.arraySize;
        this.arraySize = i + 1;
        byteBufferArr[i] = byteBuffer;
    }

    public void release() {
        ByteBuffer byteBuffer = this.directBuffer;
        if (byteBuffer != null) {
            byteBuffer.clear();
            switchToSoft();
        }
        Arrays.fill(this.array, 0, this.arraySize, (Object) null);
        this.arraySize = 0;
        this.directBufferSlice = null;
        this.sliceOffset = 0;
    }

    private ByteBuffer switchToStrong() {
        SoftReference<ByteBuffer> softReference;
        if (this.directBuffer == null && (softReference = this.softRef) != null) {
            ByteBuffer byteBuffer = softReference.get();
            this.directBufferSlice = byteBuffer;
            this.directBuffer = byteBuffer;
        }
        return this.directBuffer;
    }

    private void switchToSoft() {
        if (this.directBuffer != null && this.softRef == null) {
            this.softRef = new SoftReference<>(this.directBuffer);
        }
        this.directBuffer = null;
    }

    private void reset(ByteBuffer byteBuffer) {
        this.directBufferSlice = byteBuffer;
        this.directBuffer = byteBuffer;
        this.softRef = null;
    }

    private void ensureArraySize() {
        int i = this.arraySize;
        ByteBuffer[] byteBufferArr = this.array;
        if (i == byteBufferArr.length) {
            this.array = (ByteBuffer[]) Arrays.copyOf(byteBufferArr, ((i * 3) / 2) + 1);
        }
    }
}
