package org.glassfish.grizzly.memory;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.utils.ArrayUtils;

public final class BuffersBuffer extends CompositeBuffer {
    private static final ThreadCache.CachedTypeIndex<BuffersBuffer> CACHE_IDX = ThreadCache.obtainIndex(BuffersBuffer.class, Integer.getInteger(BuffersBuffer.class.getName() + ".bb-cache-size", 5).intValue());
    public static volatile boolean DEBUG_MODE = false;
    private Buffer activeBuffer;
    private int activeBufferLowerBound;
    private boolean allowBufferDispose = false;
    private boolean allowInternalBuffersDispose = true;
    private boolean bigEndian = true;
    private int[] bufferBounds;
    private Buffer[] buffers;
    private int buffersSize;
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private int capacity;
    protected Exception disposeStackTrace;
    private boolean isDisposed;
    private boolean isReadOnly;
    private int lastSegmentIndex;
    private int limit;
    private int lowerBound;
    private int mark = -1;
    private MemoryManager memoryManager;
    private int position;
    private final SetterImpl setter = new SetterImpl();
    private int upperBound;

    public static BuffersBuffer create() {
        return create(MemoryManager.DEFAULT_MEMORY_MANAGER, (Buffer[]) null, 0, false);
    }

    public static BuffersBuffer create(MemoryManager memoryManager2) {
        return create(memoryManager2, (Buffer[]) null, 0, false);
    }

    public static BuffersBuffer create(MemoryManager memoryManager2, Buffer... buffers2) {
        return create(memoryManager2, buffers2, buffers2.length, false);
    }

    public static BuffersBuffer create(MemoryManager memoryManager2, Buffer[] buffers2, boolean isReadOnly2) {
        return create(memoryManager2, buffers2, buffers2.length, isReadOnly2);
    }

    private static BuffersBuffer create(MemoryManager memoryManager2, Buffer[] buffers2, int buffersSize2, boolean isReadOnly2) {
        return create(memoryManager2, buffers2, buffersSize2, ByteOrder.BIG_ENDIAN, isReadOnly2);
    }

    private static BuffersBuffer create(MemoryManager memoryManager2, Buffer[] buffers2, int buffersSize2, ByteOrder byteOrder2, boolean isReadOnly2) {
        BuffersBuffer buffer = (BuffersBuffer) ThreadCache.takeFromCache(CACHE_IDX);
        if (buffer == null) {
            return new BuffersBuffer(memoryManager2, buffers2, buffersSize2, isReadOnly2);
        }
        buffer.isDisposed = false;
        buffer.order(byteOrder2);
        buffer.set(memoryManager2, buffers2, buffersSize2, isReadOnly2);
        return buffer;
    }

    protected BuffersBuffer(MemoryManager memoryManager2, Buffer[] buffers2, int buffersSize2, boolean isReadOnly2) {
        set(memoryManager2, buffers2, buffersSize2, isReadOnly2);
    }

    private void set(MemoryManager memoryManager2, Buffer[] buffers2, int buffersSize2, boolean isReadOnly2) {
        this.memoryManager = memoryManager2 != null ? memoryManager2 : MemoryManager.DEFAULT_MEMORY_MANAGER;
        if (buffers2 != null || this.buffers == null) {
            initBuffers(buffers2, buffersSize2);
            refreshBuffers();
            this.limit = this.capacity;
        }
        this.isReadOnly = isReadOnly2;
    }

    private void initBuffers(Buffer[] buffers2, int bufferSize) {
        Buffer[] bufferArr = buffers2 != null ? buffers2 : new Buffer[4];
        this.buffers = bufferArr;
        this.buffersSize = bufferSize;
        int[] iArr = this.bufferBounds;
        if (iArr == null || iArr.length < bufferArr.length) {
            this.bufferBounds = new int[bufferArr.length];
        }
    }

    private BuffersBuffer duplicateFrom(BuffersBuffer that) {
        this.memoryManager = that.memoryManager;
        Buffer[] ba = new Buffer[that.buffers.length];
        int len = that.buffersSize;
        for (int i = 0; i < len; i++) {
            ba[i] = that.buffers[i].duplicate();
        }
        initBuffers(ba, that.buffersSize);
        System.arraycopy(that.bufferBounds, 0, this.bufferBounds, 0, that.buffersSize);
        this.position = that.position;
        this.limit = that.limit;
        this.capacity = that.capacity;
        this.isReadOnly = that.isReadOnly;
        this.byteOrder = that.byteOrder;
        return this;
    }

    public boolean tryDispose() {
        if (this.allowBufferDispose) {
            dispose();
            return true;
        } else if (!this.allowInternalBuffersDispose) {
            return false;
        } else {
            removeAndDisposeBuffers();
            return false;
        }
    }

    public void dispose() {
        checkDispose();
        this.isDisposed = true;
        removeAndDisposeBuffers();
        if (DEBUG_MODE) {
            DebugLogic.doDebug(this);
        }
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public boolean isComposite() {
        return true;
    }

    public BuffersBuffer append(Buffer buffer) {
        if (buffer != this) {
            checkDispose();
            checkReadOnly();
            ensureBuffersCapacity(1);
            buffer.order(this.byteOrder);
            int remaining = this.capacity + buffer.remaining();
            this.capacity = remaining;
            int[] iArr = this.bufferBounds;
            int i = this.buffersSize;
            iArr[i] = remaining;
            Buffer[] bufferArr = this.buffers;
            this.buffersSize = i + 1;
            bufferArr[i] = buffer;
            this.limit = remaining;
            resetLastLocation();
            return this;
        }
        throw new IllegalArgumentException("CompositeBuffer can not append itself");
    }

    public BuffersBuffer prepend(Buffer buffer) {
        if (buffer != this) {
            checkDispose();
            checkReadOnly();
            ensureBuffersCapacity(1);
            buffer.order(this.byteOrder);
            Buffer[] bufferArr = this.buffers;
            System.arraycopy(bufferArr, 0, bufferArr, 1, this.buffersSize);
            this.buffers[0] = buffer;
            this.buffersSize++;
            refreshBuffers();
            this.position = 0;
            this.limit += buffer.remaining();
            resetLastLocation();
            return this;
        }
        throw new IllegalArgumentException("CompositeBuffer can not append itself");
    }

    public boolean replace(Buffer oldBuffer, Buffer newBuffer) {
        if (newBuffer != this) {
            int i = 0;
            while (i < this.buffersSize) {
                Buffer[] bufferArr = this.buffers;
                Buffer b = bufferArr[i];
                if (b == oldBuffer) {
                    bufferArr[i] = newBuffer;
                    refreshBuffers();
                    int i2 = this.capacity;
                    this.limit = i2;
                    if (this.position > i2) {
                        this.position = i2;
                    }
                    resetLastLocation();
                    return true;
                } else if (b.isComposite() && ((CompositeBuffer) b).replace(oldBuffer, newBuffer)) {
                    return false;
                } else {
                    i++;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("CompositeBuffer can not append itself");
    }

    private void ensureBuffersCapacity(int newElementsNum) {
        int newSize = this.buffersSize + newElementsNum;
        Buffer[] bufferArr = this.buffers;
        if (newSize > bufferArr.length) {
            int newCapacity = Math.max(newSize, ((bufferArr.length * 3) / 2) + 1);
            this.buffers = (Buffer[]) Arrays.copyOf(this.buffers, newCapacity);
            this.bufferBounds = Arrays.copyOf(this.bufferBounds, newCapacity);
        }
    }

    public Buffer[] underlying() {
        checkDispose();
        return this.buffers;
    }

    public int position() {
        checkDispose();
        return this.position;
    }

    public BuffersBuffer position(int newPosition) {
        checkDispose();
        setPosLim(newPosition, this.limit);
        if (this.mark > this.position) {
            this.mark = -1;
        }
        return this;
    }

    public int limit() {
        checkDispose();
        return this.limit;
    }

    public BuffersBuffer limit(int newLimit) {
        checkDispose();
        int i = this.position;
        if (i > newLimit) {
            i = newLimit;
        }
        setPosLim(i, newLimit);
        if (this.mark > this.limit) {
            this.mark = -1;
        }
        return this;
    }

    public int capacity() {
        checkDispose();
        return this.capacity;
    }

    public BuffersBuffer mark() {
        this.mark = this.position;
        return this;
    }

    public BuffersBuffer reset() {
        int m = this.mark;
        if (m >= 0) {
            this.position = m;
            return this;
        }
        throw new InvalidMarkException();
    }

    public boolean isDirect() {
        return this.buffers[0].isDirect();
    }

    public BuffersBuffer clear() {
        checkDispose();
        refreshBuffers();
        setPosLim(0, this.capacity);
        this.mark = -1;
        return this;
    }

    public BuffersBuffer flip() {
        checkDispose();
        setPosLim(0, this.position);
        this.mark = -1;
        return this;
    }

    public BuffersBuffer rewind() {
        checkDispose();
        setPosLim(0, this.limit);
        this.mark = -1;
        return this;
    }

    public int remaining() {
        checkDispose();
        return this.limit - this.position;
    }

    public boolean hasRemaining() {
        checkDispose();
        return this.limit > this.position;
    }

    public boolean isReadOnly() {
        checkDispose();
        return this.isReadOnly;
    }

    public BuffersBuffer asReadOnlyBuffer() {
        checkDispose();
        BuffersBuffer buffer = create().duplicateFrom(this);
        buffer.isReadOnly = true;
        return buffer;
    }

    public Buffer split(int splitPosition) {
        int i;
        checkDispose();
        if (splitPosition < 0 || splitPosition > (i = this.capacity)) {
            throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
        }
        if (this.mark >= splitPosition) {
            this.mark = -1;
        }
        int oldPosition = this.position;
        int oldLimit = this.limit;
        if (splitPosition == i) {
            return Buffers.EMPTY_BUFFER;
        }
        if (splitPosition == 0) {
            BuffersBuffer slice2Buffer = create(this.memoryManager, this.buffers, this.buffersSize, this.byteOrder, this.isReadOnly);
            slice2Buffer.setPosLim(this.position, this.limit);
            initBuffers((Buffer[]) null, 0);
            this.position = 0;
            this.limit = 0;
            this.capacity = 0;
            resetLastLocation();
            return slice2Buffer;
        }
        checkIndex(splitPosition);
        int splitBufferIdx = this.lastSegmentIndex;
        int splitBufferPos = toActiveBufferPos(splitPosition);
        BuffersBuffer slice2Buffer2 = create(this.memoryManager, (Buffer[]) null, 0, this.byteOrder, false);
        Buffer splitBuffer = this.activeBuffer;
        int newSize = splitBufferIdx + 1;
        if (splitBufferPos == 0) {
            slice2Buffer2.append(splitBuffer);
            this.buffers[splitBufferIdx] = null;
            newSize = splitBufferIdx;
        } else if (splitBufferPos < splitBuffer.limit()) {
            slice2Buffer2.append(splitBuffer.split(splitBufferPos));
        }
        for (int i2 = splitBufferIdx + 1; i2 < this.buffersSize; i2++) {
            slice2Buffer2.append(this.buffers[i2]);
            this.buffers[i2] = null;
        }
        this.buffersSize = newSize;
        refreshBuffers();
        if (oldPosition < splitPosition) {
            this.position = oldPosition;
        } else {
            this.position = this.capacity;
            slice2Buffer2.position(oldPosition - splitPosition);
        }
        if (oldLimit < splitPosition) {
            this.limit = oldLimit;
            slice2Buffer2.limit(0);
        } else {
            slice2Buffer2.limit(oldLimit - splitPosition);
            this.limit = this.capacity;
        }
        resetLastLocation();
        return slice2Buffer2;
    }

    public void shrink() {
        checkDispose();
        int i = this.position;
        if (i == this.limit) {
            removeAndDisposeBuffers();
            return;
        }
        checkIndex(i);
        int posBufferIndex = this.lastSegmentIndex;
        int posBufferPosition = toActiveBufferPos(this.position);
        checkIndex(this.limit - 1);
        int rightTrim = (this.buffersSize - this.lastSegmentIndex) - 1;
        int shift = 0;
        for (int i2 = 0; i2 < posBufferIndex; i2++) {
            Buffer buffer = this.buffers[i2];
            shift += buffer.remaining();
            if (this.allowInternalBuffersDispose) {
                buffer.tryDispose();
            }
        }
        Buffer posBuffer = this.buffers[posBufferIndex];
        int diff = posBufferPosition - posBuffer.position();
        if (diff > 0) {
            posBuffer.position(posBufferPosition);
            posBuffer.shrink();
            shift += diff;
        }
        setPosLim(this.position - shift, this.limit - shift);
        if (this.mark > this.position) {
            this.mark = -1;
        }
        for (int i3 = 0; i3 < rightTrim; i3++) {
            int idx = (this.buffersSize - i3) - 1;
            Buffer[] bufferArr = this.buffers;
            Buffer buffer2 = bufferArr[idx];
            bufferArr[idx] = null;
            if (this.allowInternalBuffersDispose) {
                buffer2.tryDispose();
            }
        }
        int i4 = this.buffersSize - (posBufferIndex + rightTrim);
        this.buffersSize = i4;
        if (posBufferIndex > 0) {
            Buffer[] bufferArr2 = this.buffers;
            System.arraycopy(bufferArr2, posBufferIndex, bufferArr2, 0, i4);
            Buffer[] bufferArr3 = this.buffers;
            int i5 = this.buffersSize;
            Arrays.fill(bufferArr3, i5, i5 + posBufferIndex, (Object) null);
        }
        refreshBuffers();
        resetLastLocation();
    }

    public void trim() {
        flip();
        int i = this.limit;
        if (i == 0) {
            removeRightBuffers(0);
            this.capacity = 0;
        } else {
            checkIndex(i - 1);
            this.capacity -= removeRightBuffers(this.lastSegmentIndex + 1);
        }
        resetLastLocation();
    }

    /* access modifiers changed from: protected */
    public int removeRightBuffers(int startIndex) {
        int removedBytes = 0;
        for (int i = startIndex; i < this.buffersSize; i++) {
            Buffer[] bufferArr = this.buffers;
            Buffer buffer = bufferArr[i];
            bufferArr[i] = null;
            removedBytes += buffer.remaining();
            if (this.allowInternalBuffersDispose) {
                buffer.tryDispose();
            }
        }
        this.buffersSize = startIndex;
        return removedBytes;
    }

    public Buffer slice() {
        return slice(this.position, this.limit);
    }

    public Buffer slice(int position2, int limit2) {
        checkDispose();
        int i = this.buffersSize;
        if (i == 0 || position2 == limit2) {
            return Buffers.EMPTY_BUFFER;
        }
        if (i == 1) {
            return this.buffers[0].slice(position2, limit2);
        }
        checkIndex(position2);
        int posBufferIndex = this.lastSegmentIndex;
        int posBufferPosition = toActiveBufferPos(position2);
        checkIndex(limit2 - 1);
        int limitBufferIndex = this.lastSegmentIndex;
        int limitBufferPosition = toActiveBufferPos(limit2);
        if (posBufferIndex == limitBufferIndex) {
            return this.buffers[posBufferIndex].slice(posBufferPosition, limitBufferPosition);
        }
        Buffer[] newList = new Buffer[((limitBufferIndex - posBufferIndex) + 1)];
        Buffer posBuffer = this.buffers[posBufferIndex];
        newList[0] = posBuffer.slice(posBufferPosition, posBuffer.limit());
        int index = 1;
        int i2 = posBufferIndex + 1;
        while (i2 < limitBufferIndex) {
            newList[index] = this.buffers[i2].slice();
            i2++;
            index++;
        }
        Buffer limitBuffer = this.buffers[limitBufferIndex];
        newList[index] = limitBuffer.slice(limitBuffer.position(), limitBufferPosition);
        return create(this.memoryManager, newList, newList.length, this.byteOrder, this.isReadOnly);
    }

    public BuffersBuffer duplicate() {
        checkDispose();
        return create().duplicateFrom(this);
    }

    public BuffersBuffer compact() {
        checkDispose();
        int i = this.buffersSize;
        if (i == 0) {
            return this;
        }
        if (i == 1) {
            Buffer buffer = this.buffers[0];
            Buffers.setPositionLimit(buffer, buffer.position() + this.position, buffer.position() + this.limit);
            buffer.compact();
        } else {
            checkIndex(this.position);
            int posBufferIndex = this.lastSegmentIndex;
            this.activeBuffer.position(toActiveBufferPos(this.position));
            checkIndex(this.limit - 1);
            int limitBufferIndex = this.lastSegmentIndex;
            this.activeBuffer.limit(toActiveBufferPos(this.limit));
            for (int i2 = posBufferIndex; i2 <= limitBufferIndex; i2++) {
                Buffer[] bufferArr = this.buffers;
                Buffer b1 = bufferArr[i2 - posBufferIndex];
                bufferArr[i2 - posBufferIndex] = bufferArr[i2];
                bufferArr[i2] = b1;
            }
        }
        setPosLim(0, this.position);
        refreshBuffers();
        resetLastLocation();
        return this;
    }

    public ByteOrder order() {
        checkDispose();
        return this.byteOrder;
    }

    public BuffersBuffer order(ByteOrder bo) {
        checkDispose();
        if (bo != this.byteOrder) {
            this.byteOrder = bo;
            this.bigEndian = bo == ByteOrder.BIG_ENDIAN;
            for (int i = 0; i < this.buffersSize; i++) {
                this.buffers[i].order(bo);
            }
        }
        return this;
    }

    public boolean allowBufferDispose() {
        return this.allowBufferDispose;
    }

    public void allowBufferDispose(boolean allow) {
        this.allowBufferDispose = allow;
    }

    public boolean allowInternalBuffersDispose() {
        return this.allowInternalBuffersDispose;
    }

    public void allowInternalBuffersDispose(boolean allowInternalBuffersDispose2) {
        this.allowInternalBuffersDispose = allowInternalBuffersDispose2;
    }

    public byte get() {
        if (hasRemaining()) {
            int i = this.position;
            this.position = i + 1;
            return get(i);
        }
        throw new BufferUnderflowException();
    }

    public BuffersBuffer put(byte b) {
        int i = this.position;
        this.position = i + 1;
        return put(i, b);
    }

    public byte get(int index) {
        checkDispose();
        checkIndex(index);
        return this.activeBuffer.get(toActiveBufferPos(index));
    }

    public BuffersBuffer put(int index, byte b) {
        checkDispose();
        checkReadOnly();
        checkIndex(index);
        this.activeBuffer.put(toActiveBufferPos(index), b);
        return this;
    }

    private void checkIndex(int index) {
        boolean z = true;
        boolean z2 = index >= this.lowerBound;
        if (index >= this.upperBound) {
            z = false;
        }
        if (!z2 || !z) {
            recalcIndex(index);
        }
    }

    private void recalcIndex(int index) {
        int[] iArr = this.bufferBounds;
        int i = 0;
        if (index >= iArr[0]) {
            i = ArrayUtils.binarySearch(iArr, 0, this.buffersSize - 1, index + 1);
        }
        int idx = i;
        Buffer buffer = this.buffers[idx];
        this.activeBuffer = buffer;
        int i2 = this.bufferBounds[idx];
        this.upperBound = i2;
        int remaining = i2 - buffer.remaining();
        this.lowerBound = remaining;
        this.lastSegmentIndex = idx;
        this.activeBufferLowerBound = remaining - this.activeBuffer.position();
    }

    private int toActiveBufferPos(int index) {
        return index - this.activeBufferLowerBound;
    }

    public BuffersBuffer get(byte[] dst) {
        return get(dst, 0, dst.length);
    }

    public BuffersBuffer get(byte[] dst, int offset, int length) {
        checkDispose();
        if (length == 0) {
            return this;
        }
        if (remaining() >= length) {
            checkIndex(this.position);
            int bufferIdx = this.lastSegmentIndex;
            Buffer buffer = this.activeBuffer;
            int bufferPosition = toActiveBufferPos(this.position);
            while (true) {
                int oldPos = buffer.position();
                buffer.position(bufferPosition);
                int bytesToCopy = Math.min(buffer.remaining(), length);
                buffer.get(dst, offset, bytesToCopy);
                buffer.position(oldPos);
                length -= bytesToCopy;
                offset += bytesToCopy;
                this.position += bytesToCopy;
                if (length == 0) {
                    return this;
                }
                bufferIdx++;
                buffer = this.buffers[bufferIdx];
                bufferPosition = buffer.position();
            }
        } else {
            throw new BufferUnderflowException();
        }
    }

    public BuffersBuffer put(byte[] src) {
        return put(src, 0, src.length);
    }

    public BuffersBuffer put(byte[] src, int offset, int length) {
        checkDispose();
        checkReadOnly();
        if (remaining() >= length) {
            checkIndex(this.position);
            int bufferIdx = this.lastSegmentIndex;
            Buffer buffer = this.activeBuffer;
            int bufferPosition = toActiveBufferPos(this.position);
            while (true) {
                int oldPos = buffer.position();
                buffer.position(bufferPosition);
                int bytesToCopy = Math.min(buffer.remaining(), length);
                buffer.put(src, offset, bytesToCopy);
                buffer.position(oldPos);
                length -= bytesToCopy;
                offset += bytesToCopy;
                this.position += bytesToCopy;
                if (length == 0) {
                    return this;
                }
                bufferIdx++;
                buffer = this.buffers[bufferIdx];
                bufferPosition = buffer.position();
            }
        } else {
            throw new BufferOverflowException();
        }
    }

    public BuffersBuffer put8BitString(String s) {
        int len = s.length();
        if (remaining() >= len) {
            for (int i = 0; i < len; i++) {
                put((byte) s.charAt(i));
            }
            return this;
        }
        throw new BufferOverflowException();
    }

    public BuffersBuffer get(ByteBuffer dst) {
        get(dst, dst.position(), dst.remaining());
        dst.position(dst.limit());
        return this;
    }

    public BuffersBuffer get(ByteBuffer dst, int offset, int length) {
        if (length == 0) {
            return this;
        }
        checkDispose();
        checkReadOnly();
        if (remaining() >= length) {
            checkIndex(this.position);
            int bufferIdx = this.lastSegmentIndex;
            Buffer buffer = this.activeBuffer;
            int bufferPosition = toActiveBufferPos(this.position);
            while (true) {
                int oldPos = buffer.position();
                buffer.position(bufferPosition);
                int bytesToCopy = Math.min(buffer.remaining(), length);
                buffer.get(dst, offset, bytesToCopy);
                buffer.position(oldPos);
                length -= bytesToCopy;
                offset += bytesToCopy;
                this.position += bytesToCopy;
                if (length == 0) {
                    return this;
                }
                bufferIdx++;
                buffer = this.buffers[bufferIdx];
                bufferPosition = buffer.position();
            }
        } else {
            throw new BufferOverflowException();
        }
    }

    public BuffersBuffer put(ByteBuffer src) {
        put(src, 0, src.remaining());
        src.position(src.limit());
        return this;
    }

    public BuffersBuffer put(ByteBuffer src, int offset, int length) {
        checkDispose();
        checkReadOnly();
        if (remaining() >= length) {
            checkIndex(this.position);
            int bufferIdx = this.lastSegmentIndex;
            Buffer buffer = this.activeBuffer;
            int bufferPosition = toActiveBufferPos(this.position);
            while (true) {
                int oldPos = buffer.position();
                buffer.position(bufferPosition);
                int bytesToCopy = Math.min(buffer.remaining(), length);
                buffer.put(src, offset, bytesToCopy);
                buffer.position(oldPos);
                length -= bytesToCopy;
                offset += bytesToCopy;
                this.position += bytesToCopy;
                if (length == 0) {
                    return this;
                }
                bufferIdx++;
                buffer = this.buffers[bufferIdx];
                bufferPosition = buffer.position();
            }
        } else {
            throw new BufferOverflowException();
        }
    }

    public BuffersBuffer put(Buffer src) {
        put(src, src.position(), src.remaining());
        src.position(src.limit());
        return this;
    }

    public Buffer put(Buffer src, int position2, int length) {
        checkDispose();
        checkReadOnly();
        Buffers.put(src, position2, length, (Buffer) this);
        return this;
    }

    public char getChar() {
        char value = getChar(this.position);
        this.position += 2;
        return value;
    }

    public BuffersBuffer putChar(char value) {
        putChar(this.position, value);
        this.position += 2;
        return this;
    }

    public char getChar(int index) {
        checkDispose();
        checkIndex(index);
        if (this.upperBound - index >= 2) {
            return this.activeBuffer.getChar(toActiveBufferPos(index));
        }
        return this.bigEndian ? makeCharB(index) : makeCharL(index);
    }

    public BuffersBuffer putChar(int index, char value) {
        checkDispose();
        checkReadOnly();
        checkIndex(index);
        if (this.upperBound - index >= 2) {
            this.activeBuffer.putChar(toActiveBufferPos(index), value);
        } else if (this.bigEndian) {
            putCharB(index, value);
        } else {
            putCharL(index, value);
        }
        return this;
    }

    public short getShort() {
        short value = getShort(this.position);
        this.position += 2;
        return value;
    }

    public BuffersBuffer putShort(short value) {
        putShort(this.position, value);
        this.position += 2;
        return this;
    }

    public short getShort(int index) {
        checkDispose();
        checkIndex(index);
        if (this.upperBound - index >= 2) {
            return this.activeBuffer.getShort(toActiveBufferPos(index));
        }
        return this.bigEndian ? makeShortB(index) : makeShortL(index);
    }

    public BuffersBuffer putShort(int index, short value) {
        checkDispose();
        checkReadOnly();
        checkIndex(index);
        if (this.upperBound - index >= 2) {
            this.activeBuffer.putShort(toActiveBufferPos(index), value);
        } else if (this.bigEndian) {
            putShortB(index, value);
        } else {
            putShortL(index, value);
        }
        return this;
    }

    public int getInt() {
        int value = getInt(this.position);
        this.position += 4;
        return value;
    }

    public BuffersBuffer putInt(int value) {
        putInt(this.position, value);
        this.position += 4;
        return this;
    }

    public int getInt(int index) {
        checkDispose();
        checkIndex(index);
        if (this.upperBound - index >= 4) {
            return this.activeBuffer.getInt(toActiveBufferPos(index));
        }
        return this.bigEndian ? makeIntB(index) : makeIntL(index);
    }

    public BuffersBuffer putInt(int index, int value) {
        checkDispose();
        checkReadOnly();
        checkIndex(index);
        if (this.upperBound - index >= 4) {
            this.activeBuffer.putInt(toActiveBufferPos(index), value);
        } else if (this.bigEndian) {
            putIntB(index, value);
        } else {
            putIntL(index, value);
        }
        return this;
    }

    public long getLong() {
        long value = getLong(this.position);
        this.position += 8;
        return value;
    }

    public BuffersBuffer putLong(long value) {
        putLong(this.position, value);
        this.position += 8;
        return this;
    }

    public long getLong(int index) {
        checkDispose();
        checkIndex(index);
        if (this.upperBound - index >= 8) {
            return this.activeBuffer.getLong(toActiveBufferPos(index));
        }
        return this.bigEndian ? makeLongB(index) : makeLongL(index);
    }

    public BuffersBuffer putLong(int index, long value) {
        checkDispose();
        checkReadOnly();
        checkIndex(index);
        if (this.upperBound - index >= 8) {
            this.activeBuffer.putLong(toActiveBufferPos(index), value);
        } else if (this.bigEndian) {
            putLongB(index, value);
        } else {
            putLongL(index, value);
        }
        return this;
    }

    public float getFloat() {
        return Float.intBitsToFloat(getInt());
    }

    public BuffersBuffer putFloat(float value) {
        return putInt(Float.floatToIntBits(value));
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    public BuffersBuffer putFloat(int index, float value) {
        return putInt(index, Float.floatToIntBits(value));
    }

    public double getDouble() {
        return Double.longBitsToDouble(getLong());
    }

    public BuffersBuffer putDouble(double value) {
        return putLong(Double.doubleToLongBits(value));
    }

    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public BuffersBuffer putDouble(int index, double value) {
        return putLong(index, Double.doubleToLongBits(value));
    }

    public int bulk(CompositeBuffer.BulkOperation operation) {
        return bulk(operation, this.position, this.limit);
    }

    public int bulk(CompositeBuffer.BulkOperation operation, int position2, int limit2) {
        checkDispose();
        int length = limit2 - position2;
        if (length == 0) {
            return -1;
        }
        int offset = position2;
        checkIndex(position2);
        int bufferIdx = this.lastSegmentIndex;
        Buffer buffer = this.activeBuffer;
        int bufferPosition = toActiveBufferPos(position2);
        while (true) {
            int bytesToProcess = Math.min(buffer.limit() - bufferPosition, length);
            if (buffer.isComposite()) {
                int findPos = ((CompositeBuffer) buffer).bulk(operation, bufferPosition, bufferPosition + bytesToProcess);
                if (findPos != -1) {
                    return (offset + findPos) - bufferPosition;
                }
            } else {
                Buffer unused = this.setter.buffer = buffer;
                for (int i = bufferPosition; i < bufferPosition + bytesToProcess; i++) {
                    int unused2 = this.setter.position = i;
                    if (operation.processByte(buffer.get(i), this.setter)) {
                        return (offset + i) - bufferPosition;
                    }
                }
            }
            length -= bytesToProcess;
            if (length == 0) {
                return -1;
            }
            offset += bytesToProcess;
            bufferIdx++;
            buffer = this.buffers[bufferIdx];
            bufferPosition = buffer.position();
        }
    }

    public static final class SetterImpl implements CompositeBuffer.Setter {
        /* access modifiers changed from: private */
        public Buffer buffer;
        /* access modifiers changed from: private */
        public int position;

        private SetterImpl() {
        }

        public void set(byte value) {
            this.buffer.put(this.position, value);
        }
    }

    public int compareTo(Buffer that) {
        checkDispose();
        int n = position() + Math.min(remaining(), that.remaining());
        int i = position();
        int j = that.position();
        while (i < n) {
            byte v1 = get(i);
            byte v2 = that.get(j);
            if (v1 == v2) {
                i++;
                j++;
            } else if (v1 < v2) {
                return -1;
            } else {
                return 1;
            }
        }
        return remaining() - that.remaining();
    }

    public String toString() {
        return ("BuffersBuffer (" + System.identityHashCode(this) + ") [") + "pos=" + this.position + " lim=" + this.limit + " cap=" + this.capacity + " bufferSize=" + this.buffersSize + " buffers=" + Arrays.toString(this.buffers) + ']';
    }

    public String toStringContent() {
        return toStringContent((Charset) null, this.position, this.limit);
    }

    public String toStringContent(Charset charset) {
        return toStringContent(charset, this.position, this.limit);
    }

    public String toStringContent(Charset charset, int position2, int limit2) {
        checkDispose();
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        byte[] tmpBuffer = new byte[(limit2 - position2)];
        int oldPosition = this.position;
        int oldLimit = this.limit;
        setPosLim(position2, limit2);
        get(tmpBuffer);
        setPosLim(oldPosition, oldLimit);
        try {
            return new String(tmpBuffer, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("We took charset name from Charset, why it's not unsupported?", e);
        }
    }

    public void dumpHex(Appendable appendable) {
        Buffers.dumpBuffer(appendable, this);
    }

    public ByteBuffer toByteBuffer() {
        return toByteBuffer(this.position, this.limit);
    }

    public ByteBuffer toByteBuffer(int position2, int limit2) {
        int i;
        if (position2 < 0 || position2 > (i = this.capacity) || limit2 < 0 || limit2 > i) {
            throw new IndexOutOfBoundsException("position=" + position2 + " limit=" + limit2 + "on " + toString());
        }
        int i2 = this.buffersSize;
        if (i2 == 0 || position2 == limit2) {
            return Buffers.EMPTY_BYTE_BUFFER;
        }
        if (i2 == 1) {
            Buffer buffer = this.buffers[0];
            int bufferPos = buffer.position();
            return buffer.toByteBuffer(bufferPos + position2, bufferPos + limit2);
        }
        checkIndex(position2);
        int pos1 = this.lastSegmentIndex;
        int bufPosition = toActiveBufferPos(position2);
        checkIndex(limit2 - 1);
        int pos2 = this.lastSegmentIndex;
        int bufLimit = toActiveBufferPos(limit2);
        if (pos1 == pos2) {
            return this.buffers[pos1].toByteBuffer(bufPosition, bufLimit);
        }
        ByteBuffer resultByteBuffer = MemoryUtils.allocateByteBuffer(this.memoryManager, limit2 - position2);
        Buffer startBuffer = this.buffers[pos1];
        ByteBufferArray array = ByteBufferArray.create();
        fillByteBuffer(resultByteBuffer, startBuffer.toByteBufferArray(array, bufPosition, startBuffer.limit()));
        for (int i3 = pos1 + 1; i3 < pos2; i3++) {
            fillByteBuffer(resultByteBuffer, this.buffers[i3].toByteBufferArray(array));
        }
        Buffer endBuffer = this.buffers[pos2];
        fillByteBuffer(resultByteBuffer, endBuffer.toByteBufferArray(array, endBuffer.position(), bufLimit));
        array.restore();
        array.recycle();
        return (ByteBuffer) resultByteBuffer.flip();
    }

    public ByteBufferArray toByteBufferArray() {
        return toByteBufferArray(this.position, this.limit);
    }

    public ByteBufferArray toByteBufferArray(ByteBufferArray array) {
        int i = this.position;
        if (i != 0 || this.limit != this.capacity) {
            return toByteBufferArray(array, i, this.limit);
        }
        for (int i2 = 0; i2 < this.buffersSize; i2++) {
            this.buffers[i2].toByteBufferArray(array);
        }
        return array;
    }

    public ByteBufferArray toByteBufferArray(int position2, int limit2) {
        ByteBufferArray array = ByteBufferArray.create();
        if (position2 != 0 || limit2 != this.capacity) {
            return toByteBufferArray(array, position2, limit2);
        }
        for (int i = 0; i < this.buffersSize; i++) {
            this.buffers[i].toByteBufferArray(array);
        }
        return array;
    }

    public ByteBufferArray toByteBufferArray(ByteBufferArray array, int position2, int limit2) {
        int i;
        if (position2 < 0 || position2 > (i = this.capacity) || limit2 < 0 || limit2 > i) {
            throw new IndexOutOfBoundsException("position=" + position2 + " limit=" + limit2 + "on " + toString());
        }
        int i2 = this.buffersSize;
        if (i2 == 0 || position2 == limit2) {
            return array;
        }
        if (i2 == 1) {
            Buffer b = this.buffers[0];
            int startPos = b.position();
            return b.toByteBufferArray(array, position2 + startPos, limit2 + startPos);
        } else if (position2 == 0 && limit2 == i) {
            for (int i3 = 0; i3 < this.buffersSize; i3++) {
                this.buffers[i3].toByteBufferArray(array);
            }
            return array;
        } else {
            checkIndex(position2);
            int pos1 = this.lastSegmentIndex;
            int bufPosition = toActiveBufferPos(position2);
            checkIndex(limit2 - 1);
            int pos2 = this.lastSegmentIndex;
            int bufLimit = toActiveBufferPos(limit2);
            if (pos1 == pos2) {
                return this.buffers[pos1].toByteBufferArray(array, bufPosition, bufLimit);
            }
            Buffer startBuffer = this.buffers[pos1];
            startBuffer.toByteBufferArray(array, bufPosition, startBuffer.limit());
            for (int i4 = pos1 + 1; i4 < pos2; i4++) {
                this.buffers[i4].toByteBufferArray(array);
            }
            Buffer endBuffer = this.buffers[pos2];
            endBuffer.toByteBufferArray(array, endBuffer.position(), bufLimit);
            return array;
        }
    }

    public BufferArray toBufferArray() {
        return toBufferArray(this.position, this.limit);
    }

    public BufferArray toBufferArray(BufferArray array) {
        int i = this.position;
        if (i != 0 || this.limit != this.capacity) {
            return toBufferArray(array, i, this.limit);
        }
        for (int i2 = 0; i2 < this.buffersSize; i2++) {
            this.buffers[i2].toBufferArray(array);
        }
        return array;
    }

    public BufferArray toBufferArray(int position2, int limit2) {
        BufferArray array = BufferArray.create();
        if (position2 != 0 || limit2 != this.capacity) {
            return toBufferArray(array, position2, limit2);
        }
        for (int i = 0; i < this.buffersSize; i++) {
            this.buffers[i].toBufferArray(array);
        }
        return array;
    }

    public BufferArray toBufferArray(BufferArray array, int position2, int limit2) {
        int i;
        if (position2 < 0 || position2 > (i = this.capacity) || limit2 < 0 || limit2 > i) {
            throw new IndexOutOfBoundsException("position=" + position2 + " limit=" + limit2 + "on " + toString());
        }
        int i2 = this.buffersSize;
        if (i2 == 0 || position2 == limit2) {
            return array;
        }
        if (i2 == 1) {
            Buffer b = this.buffers[0];
            int startPos = b.position();
            return b.toBufferArray(array, position2 + startPos, limit2 + startPos);
        } else if (position2 == 0 && limit2 == i) {
            for (int i3 = 0; i3 < this.buffersSize; i3++) {
                this.buffers[i3].toBufferArray(array);
            }
            return array;
        } else {
            checkIndex(position2);
            int pos1 = this.lastSegmentIndex;
            int bufPosition = toActiveBufferPos(position2);
            checkIndex(limit2 - 1);
            int pos2 = this.lastSegmentIndex;
            int bufLimit = toActiveBufferPos(limit2);
            if (pos1 == pos2) {
                return this.buffers[pos1].toBufferArray(array, bufPosition, bufLimit);
            }
            Buffer startBuffer = this.buffers[pos1];
            startBuffer.toBufferArray(array, bufPosition, startBuffer.limit());
            for (int i4 = pos1 + 1; i4 < pos2; i4++) {
                this.buffers[i4].toBufferArray(array);
            }
            Buffer endBuffer = this.buffers[pos2];
            endBuffer.toBufferArray(array, endBuffer.position(), bufLimit);
            return array;
        }
    }

    public void removeAll() {
        this.position = 0;
        this.limit = 0;
        this.capacity = 0;
        Arrays.fill(this.buffers, 0, this.buffersSize, (Object) null);
        this.buffersSize = 0;
        resetLastLocation();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer that = (Buffer) obj;
        if (remaining() != that.remaining()) {
            return false;
        }
        int p = position();
        int i = limit() - 1;
        int j = that.limit() - 1;
        while (i >= p) {
            if (get(i) != that.get(j)) {
                return false;
            }
            i--;
            j--;
        }
        return true;
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

    public int hashCode() {
        int h = 1;
        int p = position();
        for (int i = limit() - 1; i >= p; i--) {
            h = (h * 31) + get(i);
        }
        return (h * 31) + this.mark;
    }

    public boolean release() {
        return tryDispose();
    }

    public boolean isExternal() {
        return false;
    }

    private void fillByteBuffer(ByteBuffer bb, ByteBufferArray array) {
        ByteBuffer[] bbs = (ByteBuffer[]) array.getArray();
        int size = array.size();
        for (int i = 0; i < size; i++) {
            bb.put(bbs[i]);
        }
    }

    private void removeAndDisposeBuffers() {
        boolean isNulled = false;
        if (this.allowInternalBuffersDispose) {
            if (this.disposeOrder != CompositeBuffer.DisposeOrder.FIRST_TO_LAST) {
                for (int i = this.buffersSize - 1; i >= 0; i--) {
                    this.buffers[i].tryDispose();
                    this.buffers[i] = null;
                }
            } else {
                for (int i2 = 0; i2 < this.buffersSize; i2++) {
                    this.buffers[i2].tryDispose();
                    this.buffers[i2] = null;
                }
            }
            isNulled = true;
        }
        this.position = 0;
        this.limit = 0;
        this.capacity = 0;
        this.mark = -1;
        if (!isNulled) {
            Arrays.fill(this.buffers, 0, this.buffersSize, (Object) null);
        }
        this.buffersSize = 0;
        this.disposeOrder = CompositeBuffer.DisposeOrder.LAST_TO_FIRST;
        this.allowBufferDispose = false;
        this.allowInternalBuffersDispose = true;
        resetLastLocation();
    }

    private void setPosLim(int position2, int limit2) {
        if (position2 <= limit2) {
            this.position = position2;
            this.limit = limit2;
            return;
        }
        throw new IllegalArgumentException("Position exceeds a limit: " + position2 + '>' + limit2);
    }

    private void checkDispose() {
        if (this.isDisposed) {
            throw new IllegalStateException("CompositeBuffer has already been disposed", this.disposeStackTrace);
        }
    }

    private void checkReadOnly() {
        if (this.isReadOnly) {
            throw new IllegalStateException("Buffer is in read-only mode");
        }
    }

    private void refreshBuffers() {
        int currentCapacity = 0;
        for (int i = 0; i < this.buffersSize; i++) {
            Buffer buffer = this.buffers[i];
            currentCapacity += buffer.remaining();
            this.bufferBounds[i] = currentCapacity;
            buffer.order(this.byteOrder);
        }
        this.capacity = currentCapacity;
    }

    private void resetLastLocation() {
        this.lowerBound = 0;
        this.upperBound = 0;
        this.activeBuffer = null;
    }

    private long makeLongL(int index) {
        int index2 = index + 7;
        checkIndex(index2);
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index2));
        int index3 = index2 - 1;
        checkIndex(index3);
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index3));
        int index4 = index3 - 1;
        checkIndex(index4);
        byte b3 = this.activeBuffer.get(toActiveBufferPos(index4));
        int index5 = index4 - 1;
        checkIndex(index5);
        byte b4 = this.activeBuffer.get(toActiveBufferPos(index5));
        int index6 = index5 - 1;
        checkIndex(index6);
        byte b5 = this.activeBuffer.get(toActiveBufferPos(index6));
        int index7 = index6 - 1;
        checkIndex(index7);
        byte b6 = this.activeBuffer.get(toActiveBufferPos(index7));
        int index8 = index7 - 1;
        checkIndex(index8);
        byte b7 = this.activeBuffer.get(toActiveBufferPos(index8));
        int index9 = index8 - 1;
        checkIndex(index9);
        return Bits.makeLong(b1, b2, b3, b4, b5, b6, b7, this.activeBuffer.get(toActiveBufferPos(index9)));
    }

    private long makeLongB(int index) {
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index2));
        int index3 = index2 + 1;
        checkIndex(index3);
        byte b3 = this.activeBuffer.get(toActiveBufferPos(index3));
        int index4 = index3 + 1;
        checkIndex(index4);
        byte b4 = this.activeBuffer.get(toActiveBufferPos(index4));
        int index5 = index4 + 1;
        checkIndex(index5);
        byte b5 = this.activeBuffer.get(toActiveBufferPos(index5));
        int index6 = index5 + 1;
        checkIndex(index6);
        byte b6 = this.activeBuffer.get(toActiveBufferPos(index6));
        int index7 = index6 + 1;
        checkIndex(index7);
        byte b7 = this.activeBuffer.get(toActiveBufferPos(index7));
        int index8 = index7 + 1;
        checkIndex(index8);
        return Bits.makeLong(b1, b2, b3, b4, b5, b6, b7, this.activeBuffer.get(toActiveBufferPos(index8)));
    }

    private void putLongL(int index, long value) {
        int index2 = index + 7;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.long7(value));
        int index3 = index2 - 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.long6(value));
        int index4 = index3 - 1;
        checkIndex(index4);
        this.activeBuffer.put(toActiveBufferPos(index4), Bits.long5(value));
        int index5 = index4 - 1;
        checkIndex(index5);
        this.activeBuffer.put(toActiveBufferPos(index5), Bits.long4(value));
        int index6 = index5 - 1;
        checkIndex(index6);
        this.activeBuffer.put(toActiveBufferPos(index6), Bits.long3(value));
        int index7 = index6 - 1;
        checkIndex(index7);
        this.activeBuffer.put(toActiveBufferPos(index7), Bits.long2(value));
        int index8 = index7 - 1;
        checkIndex(index8);
        this.activeBuffer.put(toActiveBufferPos(index8), Bits.long1(value));
        int index9 = index8 - 1;
        checkIndex(index9);
        this.activeBuffer.put(toActiveBufferPos(index9), Bits.long0(value));
    }

    private void putLongB(int index, long value) {
        this.activeBuffer.put(toActiveBufferPos(index), Bits.long7(value));
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.long6(value));
        int index3 = index2 + 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.long5(value));
        int index4 = index3 + 1;
        checkIndex(index4);
        this.activeBuffer.put(toActiveBufferPos(index4), Bits.long4(value));
        int index5 = index4 + 1;
        checkIndex(index5);
        this.activeBuffer.put(toActiveBufferPos(index5), Bits.long3(value));
        int index6 = index5 + 1;
        checkIndex(index6);
        this.activeBuffer.put(toActiveBufferPos(index6), Bits.long2(value));
        int index7 = index6 + 1;
        checkIndex(index7);
        this.activeBuffer.put(toActiveBufferPos(index7), Bits.long1(value));
        int index8 = index7 + 1;
        checkIndex(index8);
        this.activeBuffer.put(toActiveBufferPos(index8), Bits.long0(value));
    }

    private void putIntL(int index, int value) {
        int index2 = index + 3;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.int3(value));
        int index3 = index2 - 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.int2(value));
        int index4 = index3 - 1;
        checkIndex(index4);
        this.activeBuffer.put(toActiveBufferPos(index4), Bits.int1(value));
        int index5 = index4 - 1;
        checkIndex(index5);
        this.activeBuffer.put(toActiveBufferPos(index5), Bits.int0(value));
    }

    private void putIntB(int index, int value) {
        this.activeBuffer.put(toActiveBufferPos(index), Bits.int3(value));
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.int2(value));
        int index3 = index2 + 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.int1(value));
        int index4 = index3 + 1;
        checkIndex(index4);
        this.activeBuffer.put(toActiveBufferPos(index4), Bits.int0(value));
    }

    private int makeIntL(int index) {
        int index2 = index + 3;
        checkIndex(index2);
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index2));
        int index3 = index2 - 1;
        checkIndex(index3);
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index3));
        int index4 = index3 - 1;
        checkIndex(index4);
        byte b3 = this.activeBuffer.get(toActiveBufferPos(index4));
        int index5 = index4 - 1;
        checkIndex(index5);
        return Bits.makeInt(b1, b2, b3, this.activeBuffer.get(toActiveBufferPos(index5)));
    }

    private int makeIntB(int index) {
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index2));
        int index3 = index2 + 1;
        checkIndex(index3);
        byte b3 = this.activeBuffer.get(toActiveBufferPos(index3));
        int index4 = index3 + 1;
        checkIndex(index4);
        return Bits.makeInt(b1, b2, b3, this.activeBuffer.get(toActiveBufferPos(index4)));
    }

    private void putShortL(int index, short value) {
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.short0(value));
        int index3 = index2 - 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.short1(value));
    }

    private void putShortB(int index, short value) {
        this.activeBuffer.put(toActiveBufferPos(index), Bits.short1(value));
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.short0(value));
    }

    private short makeShortL(int index) {
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        return Bits.makeShort(b2, this.activeBuffer.get(toActiveBufferPos(index2)));
    }

    private short makeShortB(int index) {
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        return Bits.makeShort(b1, this.activeBuffer.get(toActiveBufferPos(index2)));
    }

    private void putCharL(int index, char value) {
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.char0(value));
        int index3 = index2 - 1;
        checkIndex(index3);
        this.activeBuffer.put(toActiveBufferPos(index3), Bits.char1(value));
    }

    private void putCharB(int index, char value) {
        this.activeBuffer.put(toActiveBufferPos(index), Bits.char1(value));
        int index2 = index + 1;
        checkIndex(index2);
        this.activeBuffer.put(toActiveBufferPos(index2), Bits.char0(value));
    }

    private char makeCharL(int index) {
        byte b2 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        return Bits.makeChar(b2, this.activeBuffer.get(toActiveBufferPos(index2)));
    }

    private char makeCharB(int index) {
        byte b1 = this.activeBuffer.get(toActiveBufferPos(index));
        int index2 = index + 1;
        checkIndex(index2);
        return Bits.makeChar(b1, this.activeBuffer.get(toActiveBufferPos(index2)));
    }

    public static class DebugLogic {
        private DebugLogic() {
        }

        static void doDebug(BuffersBuffer buffersBuffer) {
            buffersBuffer.disposeStackTrace = new Exception("BuffersBuffer was disposed from: ");
        }
    }
}
