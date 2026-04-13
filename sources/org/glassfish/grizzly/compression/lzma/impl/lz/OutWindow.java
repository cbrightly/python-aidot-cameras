package org.glassfish.grizzly.compression.lzma.impl.lz;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class OutWindow {
    byte[] _buffer;
    LZMADecoder.LZMAInputState _decoderState;
    int _pos;
    int _streamPos;
    int _windowSize = 0;

    public void create(int windowSize) {
        if (this._buffer == null || this._windowSize != windowSize) {
            this._buffer = new byte[windowSize];
        }
        this._windowSize = windowSize;
        this._pos = 0;
        this._streamPos = 0;
    }

    public void initFromState(LZMADecoder.LZMAInputState decoderState) {
        this._decoderState = decoderState;
    }

    public void releaseBuffer() {
        this._decoderState = null;
    }

    public void init(boolean solid) {
        if (!solid) {
            this._streamPos = 0;
            this._pos = 0;
        }
    }

    public void flush() {
        int size = this._pos - this._streamPos;
        if (size != 0) {
            Buffer dst = this._decoderState.getDst();
            if (dst == null || dst.remaining() < size) {
                dst = resizeBuffer(this._decoderState.getMemoryManager(), dst, size);
                this._decoderState.setDst(dst);
            }
            dst.put(this._buffer, this._streamPos, size);
            dst.trim();
            dst.position(dst.limit());
            if (this._pos >= this._windowSize) {
                this._pos = 0;
            }
            this._streamPos = this._pos;
        }
    }

    public void copyBlock(int distance, int len) {
        int pos = (this._pos - distance) - 1;
        if (pos < 0) {
            pos += this._windowSize;
        }
        while (len != 0) {
            int i = this._windowSize;
            if (pos >= i) {
                pos = 0;
            }
            byte[] bArr = this._buffer;
            int i2 = this._pos;
            int i3 = i2 + 1;
            this._pos = i3;
            int pos2 = pos + 1;
            bArr[i2] = bArr[pos];
            if (i3 >= i) {
                flush();
            }
            len--;
            pos = pos2;
        }
    }

    public void putByte(byte b) {
        byte[] bArr = this._buffer;
        int i = this._pos;
        int i2 = i + 1;
        this._pos = i2;
        bArr[i] = b;
        if (i2 >= this._windowSize) {
            flush();
        }
    }

    public byte getByte(int distance) {
        int pos = (this._pos - distance) - 1;
        if (pos < 0) {
            pos += this._windowSize;
        }
        return this._buffer[pos];
    }

    private static Buffer resizeBuffer(MemoryManager memoryManager, Buffer buffer, int grow) {
        if (buffer == null) {
            return memoryManager.allocate(Math.max(grow, 4096));
        }
        return memoryManager.reallocate(buffer, Math.max(buffer.capacity() + grow, ((buffer.capacity() * 3) / 2) + 1));
    }
}
