package org.glassfish.grizzly.memory;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public class ByteBufferWrapper implements Buffer {
    public static volatile boolean DEBUG_MODE = false;
    protected boolean allowBufferDispose;
    protected Exception disposeStackTrace;
    protected int mark;
    protected ByteBuffer visible;

    protected ByteBufferWrapper() {
        this((ByteBuffer) null);
    }

    public ByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
        this.mark = -1;
        this.allowBufferDispose = false;
        this.visible = underlyingByteBuffer;
    }

    public final boolean isComposite() {
        return false;
    }

    public ByteBufferWrapper prepend(Buffer header) {
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

    public boolean isDirect() {
        checkDispose();
        return this.visible.isDirect();
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
        this.visible = null;
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
        return this.visible;
    }

    public final int capacity() {
        return this.visible.capacity();
    }

    public final int position() {
        checkDispose();
        return this.visible.position();
    }

    public final ByteBufferWrapper position(int newPosition) {
        checkDispose();
        this.visible.position(newPosition);
        if (this.mark > newPosition) {
            this.mark = -1;
        }
        return this;
    }

    public final int limit() {
        checkDispose();
        return this.visible.limit();
    }

    public final ByteBufferWrapper limit(int newLimit) {
        checkDispose();
        this.visible.limit(newLimit);
        if (this.mark > newLimit) {
            this.mark = -1;
        }
        return this;
    }

    public final ByteBufferWrapper mark() {
        checkDispose();
        this.mark = this.visible.position();
        return this;
    }

    public final ByteBufferWrapper reset() {
        checkDispose();
        int i = this.mark;
        if (i >= 0) {
            this.visible.position(i);
            return this;
        }
        throw new InvalidMarkException();
    }

    public final ByteBufferWrapper clear() {
        checkDispose();
        this.visible.clear();
        this.mark = -1;
        return this;
    }

    public final ByteBufferWrapper flip() {
        checkDispose();
        this.visible.flip();
        this.mark = -1;
        return this;
    }

    public final ByteBufferWrapper rewind() {
        checkDispose();
        this.visible.rewind();
        this.mark = -1;
        return this;
    }

    public final int remaining() {
        checkDispose();
        return this.visible.remaining();
    }

    public final boolean hasRemaining() {
        checkDispose();
        return this.visible.hasRemaining();
    }

    public boolean isReadOnly() {
        checkDispose();
        return this.visible.isReadOnly();
    }

    public Buffer split(int splitPosition) {
        checkDispose();
        int cap = capacity();
        if (splitPosition < 0 || splitPosition > cap) {
            throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
        } else if (splitPosition == cap) {
            return Buffers.EMPTY_BUFFER;
        } else {
            if (this.mark >= splitPosition) {
                this.mark = -1;
            }
            int oldPosition = position();
            int oldLimit = limit();
            Buffers.setPositionLimit(this.visible, 0, splitPosition);
            ByteBuffer slice1 = this.visible.slice();
            ByteBuffer byteBuffer = this.visible;
            Buffers.setPositionLimit(byteBuffer, splitPosition, byteBuffer.capacity());
            ByteBuffer slice2 = this.visible.slice();
            if (oldPosition < splitPosition) {
                slice1.position(oldPosition);
            } else {
                slice1.position(slice1.capacity());
                slice2.position(oldPosition - splitPosition);
            }
            if (oldLimit < splitPosition) {
                slice1.limit(oldLimit);
                slice2.limit(0);
            } else {
                slice2.limit(oldLimit - splitPosition);
            }
            this.visible = slice1;
            return wrapByteBuffer(slice2);
        }
    }

    public ByteBufferWrapper slice() {
        return slice(position(), limit());
    }

    /* JADX INFO: finally extract failed */
    public ByteBufferWrapper slice(int position, int limit) {
        checkDispose();
        int oldPosition = position();
        int oldLimit = limit();
        try {
            Buffers.setPositionLimit(this.visible, position, limit);
            ByteBufferWrapper wrapByteBuffer = wrapByteBuffer(this.visible.slice());
            Buffers.setPositionLimit(this.visible, oldPosition, oldLimit);
            return wrapByteBuffer;
        } catch (Throwable th) {
            Buffers.setPositionLimit(this.visible, oldPosition, oldLimit);
            throw th;
        }
    }

    public ByteBufferWrapper duplicate() {
        checkDispose();
        return wrapByteBuffer(this.visible.duplicate());
    }

    public ByteBufferWrapper asReadOnlyBuffer() {
        checkDispose();
        return wrapByteBuffer(this.visible.asReadOnlyBuffer());
    }

    public byte get() {
        checkDispose();
        return this.visible.get();
    }

    public byte get(int index) {
        checkDispose();
        return this.visible.get(index);
    }

    public ByteBufferWrapper put(byte b) {
        checkDispose();
        this.visible.put(b);
        return this;
    }

    public ByteBufferWrapper put(int index, byte b) {
        checkDispose();
        this.visible.put(index, b);
        return this;
    }

    public ByteBufferWrapper get(byte[] dst) {
        return get(dst, 0, dst.length);
    }

    public ByteBufferWrapper get(byte[] dst, int offset, int length) {
        checkDispose();
        Buffers.get(this.visible, dst, offset, length);
        return this;
    }

    public ByteBufferWrapper put(Buffer src) {
        put(src, src.position(), src.remaining());
        src.position(src.limit());
        return this;
    }

    public ByteBufferWrapper put(Buffer src, int position, int length) {
        int oldPos = src.position();
        int oldLim = limit();
        src.position(position);
        limit(position() + length);
        try {
            src.get(this.visible);
            return this;
        } finally {
            src.position(oldPos);
            limit(oldLim);
        }
    }

    public Buffer get(ByteBuffer dst) {
        checkDispose();
        int length = dst.remaining();
        if (this.visible.remaining() >= length) {
            int srcPos = this.visible.position();
            int oldSrcLim = this.visible.limit();
            try {
                this.visible.limit(srcPos + length);
                dst.put(this.visible);
                return this;
            } finally {
                this.visible.limit(oldSrcLim);
            }
        } else {
            throw new BufferUnderflowException();
        }
    }

    public Buffer get(ByteBuffer dst, int position, int length) {
        checkDispose();
        if (this.visible.remaining() >= length) {
            int srcPos = this.visible.position();
            int oldSrcLim = this.visible.limit();
            int oldDstPos = dst.position();
            int oldDstLim = dst.limit();
            Buffers.setPositionLimit(dst, position, position + length);
            try {
                this.visible.limit(srcPos + length);
                dst.put(this.visible);
                return this;
            } finally {
                this.visible.limit(oldSrcLim);
                Buffers.setPositionLimit(dst, oldDstPos, oldDstLim);
            }
        } else {
            throw new BufferUnderflowException();
        }
    }

    public Buffer put(ByteBuffer src) {
        checkDispose();
        this.visible.put(src);
        return this;
    }

    /* JADX INFO: finally extract failed */
    public Buffer put(ByteBuffer src, int position, int length) {
        checkDispose();
        int oldPos = src.position();
        int oldLim = src.limit();
        try {
            Buffers.setPositionLimit(src, position, position + length);
            this.visible.put(src);
            Buffers.setPositionLimit(src, oldPos, oldLim);
            return this;
        } catch (Throwable th) {
            Buffers.setPositionLimit(src, oldPos, oldLim);
            throw th;
        }
    }

    public ByteBufferWrapper put(byte[] src) {
        return put(src, 0, src.length);
    }

    public ByteBufferWrapper put(byte[] src, int offset, int length) {
        checkDispose();
        Buffers.put(src, offset, length, this.visible);
        return this;
    }

    public Buffer put8BitString(String s) {
        checkDispose();
        int len = s.length();
        if (remaining() >= len) {
            for (int i = 0; i < len; i++) {
                this.visible.put((byte) s.charAt(i));
            }
            return this;
        }
        throw new BufferOverflowException();
    }

    public ByteBufferWrapper compact() {
        checkDispose();
        this.visible.compact();
        return this;
    }

    public ByteOrder order() {
        checkDispose();
        return this.visible.order();
    }

    public ByteBufferWrapper order(ByteOrder bo) {
        checkDispose();
        this.visible.order(bo);
        return this;
    }

    public char getChar() {
        checkDispose();
        return this.visible.getChar();
    }

    public char getChar(int index) {
        checkDispose();
        return this.visible.getChar(index);
    }

    public ByteBufferWrapper putChar(char value) {
        checkDispose();
        this.visible.putChar(value);
        return this;
    }

    public ByteBufferWrapper putChar(int index, char value) {
        checkDispose();
        this.visible.putChar(index, value);
        return this;
    }

    public short getShort() {
        checkDispose();
        return this.visible.getShort();
    }

    public short getShort(int index) {
        checkDispose();
        return this.visible.getShort(index);
    }

    public ByteBufferWrapper putShort(short value) {
        checkDispose();
        this.visible.putShort(value);
        return this;
    }

    public ByteBufferWrapper putShort(int index, short value) {
        checkDispose();
        this.visible.putShort(index, value);
        return this;
    }

    public int getInt() {
        checkDispose();
        return this.visible.getInt();
    }

    public int getInt(int index) {
        checkDispose();
        return this.visible.getInt(index);
    }

    public ByteBufferWrapper putInt(int value) {
        checkDispose();
        this.visible.putInt(value);
        return this;
    }

    public ByteBufferWrapper putInt(int index, int value) {
        checkDispose();
        this.visible.putInt(index, value);
        return this;
    }

    public long getLong() {
        checkDispose();
        return this.visible.getLong();
    }

    public long getLong(int index) {
        checkDispose();
        return this.visible.getLong(index);
    }

    public ByteBufferWrapper putLong(long value) {
        checkDispose();
        this.visible.putLong(value);
        return this;
    }

    public ByteBufferWrapper putLong(int index, long value) {
        checkDispose();
        this.visible.putLong(index, value);
        return this;
    }

    public float getFloat() {
        checkDispose();
        return this.visible.getFloat();
    }

    public float getFloat(int index) {
        checkDispose();
        return this.visible.getFloat(index);
    }

    public ByteBufferWrapper putFloat(float value) {
        checkDispose();
        this.visible.putFloat(value);
        return this;
    }

    public ByteBufferWrapper putFloat(int index, float value) {
        checkDispose();
        this.visible.putFloat(index, value);
        return this;
    }

    public double getDouble() {
        checkDispose();
        return this.visible.getDouble();
    }

    public double getDouble(int index) {
        checkDispose();
        return this.visible.getDouble(index);
    }

    public ByteBufferWrapper putDouble(double value) {
        checkDispose();
        this.visible.putDouble(value);
        return this;
    }

    public ByteBufferWrapper putDouble(int index, double value) {
        checkDispose();
        this.visible.putDouble(index, value);
        return this;
    }

    public int hashCode() {
        return this.visible.hashCode();
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
        if (this.visible == null) {
            throw new IllegalStateException("BufferWrapper has already been disposed", this.disposeStackTrace);
        }
    }

    public String toString() {
        return ("ByteBufferWrapper (" + System.identityHashCode(this) + ") [") + "visible=[" + this.visible + ']' + ']';
    }

    public String toStringContent() {
        return toStringContent(Charset.defaultCharset(), position(), limit());
    }

    public String toStringContent(Charset charset) {
        return toStringContent(charset, position(), limit());
    }

    public String toStringContent(Charset charset, int position, int limit) {
        checkDispose();
        return Buffers.toStringContent(this.visible, charset, position, limit);
    }

    public void dumpHex(Appendable appendable) {
        Buffers.dumpBuffer(appendable, this);
    }

    public final ByteBuffer toByteBuffer() {
        checkDispose();
        return this.visible;
    }

    public final ByteBuffer toByteBuffer(int position, int limit) {
        checkDispose();
        int currentPosition = this.visible.position();
        int currentLimit = this.visible.limit();
        if (position == currentPosition && limit == currentLimit) {
            return toByteBuffer();
        }
        Buffers.setPositionLimit(this.visible, position, limit);
        ByteBuffer resultBuffer = this.visible.slice();
        Buffers.setPositionLimit(this.visible, currentPosition, currentLimit);
        return resultBuffer;
    }

    public final ByteBufferArray toByteBufferArray() {
        checkDispose();
        ByteBufferArray array = ByteBufferArray.create();
        array.add(this.visible);
        return array;
    }

    public final ByteBufferArray toByteBufferArray(int position, int limit) {
        return toByteBufferArray(ByteBufferArray.create(), position, limit);
    }

    public final ByteBufferArray toByteBufferArray(ByteBufferArray array) {
        checkDispose();
        array.add(this.visible);
        return array;
    }

    public final ByteBufferArray toByteBufferArray(ByteBufferArray array, int position, int limit) {
        checkDispose();
        int oldPos = this.visible.position();
        int oldLim = this.visible.limit();
        Buffers.setPositionLimit(this.visible, position, limit);
        array.add(this.visible, oldPos, oldLim);
        return array;
    }

    public final BufferArray toBufferArray() {
        checkDispose();
        BufferArray array = BufferArray.create();
        array.add(this);
        return array;
    }

    public final BufferArray toBufferArray(int position, int limit) {
        return toBufferArray(BufferArray.create(), position, limit);
    }

    public final BufferArray toBufferArray(BufferArray array) {
        checkDispose();
        array.add(this);
        return array;
    }

    public final BufferArray toBufferArray(BufferArray array, int position, int limit) {
        checkDispose();
        int oldPos = this.visible.position();
        int oldLim = this.visible.limit();
        Buffers.setPositionLimit(this.visible, position, limit);
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
        return this.visible.hasArray();
    }

    public byte[] array() {
        return this.visible.array();
    }

    public int arrayOffset() {
        return this.visible.arrayOffset();
    }

    /* access modifiers changed from: protected */
    public ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
        return new ByteBufferWrapper(byteBuffer);
    }

    public static class DebugLogic {
        private DebugLogic() {
        }

        static void doDebug(ByteBufferWrapper wrapper) {
            wrapper.visible.clear();
            while (wrapper.visible.hasRemaining()) {
                wrapper.visible.put((byte) -1);
            }
            wrapper.visible.flip();
            wrapper.disposeStackTrace = new Exception("ByteBufferWrapper was disposed from: ");
        }
    }
}
