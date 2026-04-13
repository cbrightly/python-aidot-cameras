package org.glassfish.grizzly.memory;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;

public class HeapBuffer implements Buffer {
    public static volatile boolean DEBUG_MODE = false;
    protected boolean allowBufferDispose = false;
    protected boolean bigEndian = true;
    protected ByteBuffer byteBuffer;
    protected int cap;
    protected Exception disposeStackTrace;
    protected byte[] heap;
    protected int lim;
    protected int mark = -1;
    protected int offset;
    protected ByteOrder order = ByteOrder.BIG_ENDIAN;
    protected int pos;
    protected boolean readOnly;

    protected HeapBuffer() {
    }

    protected HeapBuffer(byte[] heap2, int offset2, int cap2) {
        this.heap = heap2;
        this.offset = offset2;
        this.cap = cap2;
        this.lim = cap2;
    }

    public final boolean isComposite() {
        return false;
    }

    public HeapBuffer prepend(Buffer header) {
        checkDispose();
        return this;
    }

    public void trim() {
        checkDispose();
        flip();
    }

    public void shrink() {
        checkDispose();
    }

    public final boolean allowBufferDispose() {
        return this.allowBufferDispose;
    }

    public final void allowBufferDispose(boolean allowBufferDispose2) {
        this.allowBufferDispose = allowBufferDispose2;
    }

    public final boolean tryDispose() {
        if (!this.allowBufferDispose) {
            return false;
        }
        dispose();
        return true;
    }

    public void dispose() {
        prepareDispose();
        this.byteBuffer = null;
        this.heap = null;
        this.pos = 0;
        this.offset = 0;
        this.lim = 0;
        this.cap = 0;
        this.order = ByteOrder.BIG_ENDIAN;
        this.bigEndian = true;
    }

    /* access modifiers changed from: protected */
    public final void prepareDispose() {
        checkDispose();
        if (DEBUG_MODE) {
            DebugLogic.doDebug(this);
        }
    }

    public ByteBuffer underlying() {
        checkDispose();
        return toByteBuffer();
    }

    public final int capacity() {
        checkDispose();
        return this.cap;
    }

    public final int position() {
        checkDispose();
        return this.pos;
    }

    public final HeapBuffer position(int newPosition) {
        checkDispose();
        this.pos = newPosition;
        if (this.mark > newPosition) {
            this.mark = -1;
        }
        return this;
    }

    public final int limit() {
        checkDispose();
        return this.lim;
    }

    public final HeapBuffer limit(int newLimit) {
        checkDispose();
        this.lim = newLimit;
        if (this.mark > newLimit) {
            this.mark = -1;
        }
        return this;
    }

    public final HeapBuffer mark() {
        this.mark = this.pos;
        return this;
    }

    public final HeapBuffer reset() {
        int m = this.mark;
        if (m >= 0) {
            this.pos = m;
            return this;
        }
        throw new InvalidMarkException();
    }

    public final HeapBuffer clear() {
        this.pos = 0;
        this.lim = this.cap;
        this.mark = -1;
        return this;
    }

    public final HeapBuffer flip() {
        this.lim = this.pos;
        this.pos = 0;
        this.mark = -1;
        return this;
    }

    public final HeapBuffer rewind() {
        this.pos = 0;
        this.mark = -1;
        return this;
    }

    public final int remaining() {
        return this.lim - this.pos;
    }

    public final boolean hasRemaining() {
        return this.pos < this.lim;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public final boolean isDirect() {
        return false;
    }

    public Buffer split(int splitPosition) {
        int i;
        checkDispose();
        if (splitPosition < 0 || splitPosition > (i = this.cap)) {
            throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
        }
        if (this.mark >= splitPosition) {
            this.mark = -1;
        }
        int oldPosition = this.pos;
        int oldLimit = this.lim;
        HeapBuffer ret = createHeapBuffer(splitPosition, i - splitPosition);
        this.cap = splitPosition;
        if (oldPosition < splitPosition) {
            this.pos = oldPosition;
        } else {
            this.pos = splitPosition;
            ret.position(oldPosition - splitPosition);
        }
        if (oldLimit < splitPosition) {
            this.lim = oldLimit;
            ret.limit(0);
        } else {
            this.lim = this.cap;
            ret.limit(oldLimit - splitPosition);
        }
        return ret;
    }

    public HeapBuffer slice() {
        return slice(this.pos, this.lim);
    }

    public HeapBuffer slice(int position, int limit) {
        checkDispose();
        return createHeapBuffer(position, limit - position);
    }

    public HeapBuffer duplicate() {
        checkDispose();
        HeapBuffer duplicate = createHeapBuffer(0, this.cap);
        duplicate.position(this.pos);
        duplicate.limit(this.lim);
        return duplicate;
    }

    public HeapBuffer asReadOnlyBuffer() {
        checkDispose();
        onShareHeap();
        HeapBuffer b = new ReadOnlyHeapBuffer(this.heap, this.offset, this.cap);
        b.pos = this.pos;
        b.lim = this.lim;
        return b;
    }

    public byte get() {
        if (hasRemaining()) {
            byte[] bArr = this.heap;
            int i = this.offset;
            int i2 = this.pos;
            this.pos = i2 + 1;
            return bArr[i + i2];
        }
        throw new BufferUnderflowException();
    }

    public byte get(int index) {
        if (index >= 0 && index < this.lim) {
            return this.heap[this.offset + index];
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer put(byte b) {
        if (hasRemaining()) {
            byte[] bArr = this.heap;
            int i = this.offset;
            int i2 = this.pos;
            this.pos = i2 + 1;
            bArr[i + i2] = b;
            return this;
        }
        throw new BufferOverflowException();
    }

    public HeapBuffer put(int index, byte b) {
        if (index < 0 || index >= this.lim) {
            throw new IndexOutOfBoundsException();
        }
        this.heap[this.offset + index] = b;
        return this;
    }

    public HeapBuffer get(byte[] dst) {
        return get(dst, 0, dst.length);
    }

    public HeapBuffer get(byte[] dst, int offset2, int length) {
        if (remaining() >= length) {
            System.arraycopy(this.heap, this.offset + this.pos, dst, offset2, length);
            this.pos += length;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer put(Buffer src) {
        put(src, src.position(), src.remaining());
        src.position(src.limit());
        return this;
    }

    public HeapBuffer put(Buffer src, int position, int length) {
        if (remaining() >= length) {
            int oldPos = src.position();
            int oldLim = src.limit();
            int thisPos = this.pos;
            Buffers.setPositionLimit(src, position, position + length);
            src.get(this.heap, this.offset + thisPos, length);
            Buffers.setPositionLimit(src, oldPos, oldLim);
            this.pos = thisPos + length;
            return this;
        }
        throw new BufferOverflowException();
    }

    public Buffer get(ByteBuffer dst) {
        int length = dst.remaining();
        dst.put(this.heap, this.offset + this.pos, length);
        this.pos += length;
        return this;
    }

    /* JADX INFO: finally extract failed */
    public Buffer get(ByteBuffer dst, int position, int length) {
        int oldPos = dst.position();
        int oldLim = dst.limit();
        try {
            Buffers.setPositionLimit(dst, position, position + length);
            dst.put(this.heap, this.offset + this.pos, length);
            this.pos += length;
            Buffers.setPositionLimit(dst, oldPos, oldLim);
            return this;
        } catch (Throwable th) {
            Buffers.setPositionLimit(dst, oldPos, oldLim);
            throw th;
        }
    }

    public Buffer put(ByteBuffer src) {
        int length = src.remaining();
        src.get(this.heap, this.offset + this.pos, length);
        this.pos += length;
        return this;
    }

    /* JADX INFO: finally extract failed */
    public Buffer put(ByteBuffer src, int position, int length) {
        int oldPos = src.position();
        int oldLim = src.limit();
        try {
            Buffers.setPositionLimit(src, position, position + length);
            src.get(this.heap, this.offset + this.pos, length);
            this.pos += length;
            Buffers.setPositionLimit(src, oldPos, oldLim);
            return this;
        } catch (Throwable th) {
            Buffers.setPositionLimit(src, oldPos, oldLim);
            throw th;
        }
    }

    public static HeapBuffer wrap(byte[] heap2) {
        return wrap(heap2, 0, heap2.length);
    }

    public static HeapBuffer wrap(byte[] heap2, int off, int len) {
        return new HeapBuffer(heap2, off, len);
    }

    public HeapBuffer put(byte[] src) {
        return put(src, 0, src.length);
    }

    public HeapBuffer put(byte[] src, int offset2, int length) {
        if (remaining() >= length) {
            System.arraycopy(src, offset2, this.heap, this.offset + this.pos, length);
            this.pos += length;
            return this;
        }
        throw new BufferOverflowException();
    }

    public HeapBuffer put8BitString(String s) {
        int len = s.length();
        if (remaining() >= len) {
            s.getBytes(0, len, this.heap, this.offset + this.pos);
            this.pos += len;
            return this;
        }
        throw new BufferOverflowException();
    }

    public HeapBuffer compact() {
        int length = remaining();
        byte[] bArr = this.heap;
        int i = this.offset;
        System.arraycopy(bArr, this.pos + i, bArr, i, length);
        this.pos = length;
        this.lim = this.cap;
        return this;
    }

    public ByteOrder order() {
        return this.order;
    }

    public HeapBuffer order(ByteOrder bo) {
        this.order = bo;
        this.bigEndian = bo == ByteOrder.BIG_ENDIAN;
        return this;
    }

    public char getChar() {
        if (remaining() >= 2) {
            char c = Bits.getChar(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 2;
            return c;
        }
        throw new BufferUnderflowException();
    }

    public char getChar(int index) {
        if (index >= 0 && index < this.lim - 1) {
            return Bits.getChar(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putChar(char value) {
        if (remaining() >= 2) {
            Bits.putChar(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 2;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putChar(int index, char value) {
        if (index < 0 || index >= this.lim - 1) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putChar(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public short getShort() {
        if (remaining() >= 2) {
            short s = Bits.getShort(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 2;
            return s;
        }
        throw new BufferUnderflowException();
    }

    public short getShort(int index) {
        if (index >= 0 && index < this.lim - 1) {
            return Bits.getShort(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putShort(short value) {
        if (remaining() >= 2) {
            Bits.putShort(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 2;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putShort(int index, short value) {
        if (index < 0 || index >= this.lim - 1) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putShort(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public int getInt() {
        if (remaining() >= 4) {
            int i = Bits.getInt(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 4;
            return i;
        }
        throw new BufferUnderflowException();
    }

    public int getInt(int index) {
        if (index >= 0 && index < this.lim - 3) {
            return Bits.getInt(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putInt(int value) {
        if (remaining() >= 4) {
            Bits.putInt(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 4;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putInt(int index, int value) {
        if (index < 0 || index >= this.lim - 3) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putInt(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public long getLong() {
        if (remaining() >= 8) {
            long l = Bits.getLong(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 8;
            return l;
        }
        throw new BufferUnderflowException();
    }

    public long getLong(int index) {
        if (index >= 0 && index < this.lim - 7) {
            return Bits.getLong(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putLong(long value) {
        if (remaining() >= 8) {
            Bits.putLong(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 8;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putLong(int index, long value) {
        if (index < 0 || index >= this.lim - 7) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putLong(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public float getFloat() {
        if (remaining() >= 4) {
            float f = Bits.getFloat(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 4;
            return f;
        }
        throw new BufferUnderflowException();
    }

    public float getFloat(int index) {
        if (index >= 0 && index < this.lim - 3) {
            return Bits.getFloat(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putFloat(float value) {
        if (remaining() >= 4) {
            Bits.putFloat(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 4;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putFloat(int index, float value) {
        if (index < 0 || index >= this.lim - 3) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putFloat(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public double getDouble() {
        if (remaining() >= 8) {
            double d = Bits.getDouble(this.heap, this.offset + this.pos, this.bigEndian);
            this.pos += 8;
            return d;
        }
        throw new BufferUnderflowException();
    }

    public double getDouble(int index) {
        if (index >= 0 && index < this.lim - 7) {
            return Bits.getDouble(this.heap, this.offset + index, this.bigEndian);
        }
        throw new IndexOutOfBoundsException();
    }

    public HeapBuffer putDouble(double value) {
        if (remaining() >= 8) {
            Bits.putDouble(this.heap, this.offset + this.pos, value, this.bigEndian);
            this.pos += 8;
            return this;
        }
        throw new BufferUnderflowException();
    }

    public HeapBuffer putDouble(int index, double value) {
        if (index < 0 || index >= this.lim - 7) {
            throw new IndexOutOfBoundsException();
        }
        Bits.putDouble(this.heap, this.offset + index, value, this.bigEndian);
        return this;
    }

    public int hashCode() {
        int i = ((int) this.allowBufferDispose) * true;
        Exception exc = this.disposeStackTrace;
        int i2 = 0;
        int result = (i + (exc != null ? exc.hashCode() : 0)) * 31;
        byte[] bArr = this.heap;
        int result2 = (((((((((((((result + (bArr != null ? Arrays.hashCode(bArr) : 0)) * 31) + this.offset) * 31) + this.pos) * 31) + this.cap) * 31) + this.lim) * 31) + this.mark) * 31) + (this.readOnly ? 1 : 0)) * 31;
        ByteOrder byteOrder = this.order;
        if (byteOrder != null) {
            i2 = byteOrder.hashCode();
        }
        return ((result2 + i2) * 31) + (this.bigEndian ? 1 : 0);
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

    public int compareTo(Buffer o) {
        int n = position() + Math.min(remaining(), o.remaining());
        int i = position();
        int j = o.position();
        while (i < n) {
            byte v1 = get(i);
            byte v2 = o.get(j);
            if (v1 == v2) {
                i++;
                j++;
            } else if (v1 < v2) {
                return -1;
            } else {
                return 1;
            }
        }
        return remaining() - o.remaining();
    }

    /* access modifiers changed from: protected */
    public void checkDispose() {
        if (this.heap == null) {
            throw new IllegalStateException("HeapBuffer has already been disposed", this.disposeStackTrace);
        }
    }

    public String toString() {
        return ("HeapBuffer (" + System.identityHashCode(this) + ") ") + "[pos=" + this.pos + " lim=" + this.lim + " cap=" + this.cap + ']';
    }

    public String toStringContent() {
        return toStringContent(Charset.defaultCharset(), this.pos, this.lim);
    }

    public String toStringContent(Charset charset) {
        return toStringContent(charset, this.pos, this.lim);
    }

    public String toStringContent(Charset charset, int position, int limit) {
        checkDispose();
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        ByteBuffer byteBuffer2 = this.byteBuffer;
        boolean isRestoreByteBuffer = byteBuffer2 != null;
        int oldPosition = 0;
        int oldLimit = 0;
        if (isRestoreByteBuffer) {
            oldPosition = byteBuffer2.position();
            oldLimit = this.byteBuffer.limit();
        }
        try {
            return charset.decode(toByteBuffer0(position, limit, false)).toString();
        } finally {
            if (isRestoreByteBuffer) {
                Buffers.setPositionLimit(this.byteBuffer, oldPosition, oldLimit);
            }
        }
    }

    public void dumpHex(Appendable appendable) {
        Buffers.dumpBuffer(appendable, this);
    }

    public ByteBuffer toByteBuffer() {
        return toByteBuffer(this.pos, this.lim);
    }

    public ByteBuffer toByteBuffer(int position, int limit) {
        return toByteBuffer0(position, limit, false);
    }

    public final ByteBufferArray toByteBufferArray() {
        ByteBufferArray array = ByteBufferArray.create();
        array.add(toByteBuffer());
        return array;
    }

    public final ByteBufferArray toByteBufferArray(int position, int limit) {
        return toByteBufferArray(ByteBufferArray.create(), position, limit);
    }

    public final ByteBufferArray toByteBufferArray(ByteBufferArray array) {
        array.add(toByteBuffer());
        return array;
    }

    public final ByteBufferArray toByteBufferArray(ByteBufferArray array, int position, int limit) {
        array.add(toByteBuffer(position, limit));
        return array;
    }

    public final BufferArray toBufferArray() {
        BufferArray array = BufferArray.create();
        array.add(this);
        return array;
    }

    public final BufferArray toBufferArray(int position, int limit) {
        return toBufferArray(BufferArray.create(), position, limit);
    }

    public final BufferArray toBufferArray(BufferArray array) {
        array.add(this);
        return array;
    }

    public final BufferArray toBufferArray(BufferArray array, int position, int limit) {
        int oldPos = this.pos;
        int oldLim = this.lim;
        this.pos = position;
        this.lim = limit;
        array.add(this, oldPos, oldLim);
        return array;
    }

    public boolean release() {
        return tryDispose();
    }

    public boolean isExternal() {
        return false;
    }

    public boolean hasArray() {
        return true;
    }

    public int arrayOffset() {
        return this.offset;
    }

    public byte[] array() {
        return this.heap;
    }

    /* access modifiers changed from: protected */
    public void onShareHeap() {
    }

    /* access modifiers changed from: protected */
    public HeapBuffer createHeapBuffer(int offs, int capacity) {
        onShareHeap();
        return new HeapBuffer(this.heap, this.offset + offs, capacity);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer toByteBuffer0(int pos2, int lim2, boolean slice) {
        if (this.byteBuffer == null) {
            this.byteBuffer = ByteBuffer.wrap(this.heap);
        }
        ByteBuffer byteBuffer2 = this.byteBuffer;
        int i = this.offset;
        Buffers.setPositionLimit(byteBuffer2, i + pos2, i + lim2);
        ByteBuffer byteBuffer3 = this.byteBuffer;
        return slice ? byteBuffer3.slice() : byteBuffer3;
    }

    public static class DebugLogic {
        private DebugLogic() {
        }

        static void doDebug(HeapBuffer heapBuffer) {
            heapBuffer.clear();
            while (heapBuffer.hasRemaining()) {
                heapBuffer.put((byte) -1);
            }
            heapBuffer.flip();
            heapBuffer.disposeStackTrace = new Exception("HeapBuffer was disposed from: ");
        }
    }
}
