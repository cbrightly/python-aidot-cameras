package org.glassfish.grizzly.compression.lzma.impl.lz;

import org.glassfish.grizzly.Buffer;

public class InWindow {
    public int _blockSize;
    Buffer _buffer;
    public byte[] _bufferBase;
    public int _bufferOffset;
    int _keepSizeAfter;
    int _keepSizeBefore;
    int _pointerToLastSafePosition;
    public int _pos;
    int _posLimit;
    boolean _streamEndWasReached;
    public int _streamPos;

    public void moveBlock() {
        int i = this._bufferOffset;
        int offset = (this._pos + i) - this._keepSizeBefore;
        if (offset > 0) {
            offset--;
        }
        byte[] bArr = this._bufferBase;
        System.arraycopy(bArr, offset, bArr, 0, (i + this._streamPos) - offset);
        this._bufferOffset -= offset;
    }

    public void readBlock() {
        if (!this._streamEndWasReached) {
            while (true) {
                int size = ((0 - this._bufferOffset) + this._blockSize) - this._streamPos;
                if (size != 0) {
                    int pos = this._buffer.position();
                    this._buffer.get(this._bufferBase, this._bufferOffset + this._streamPos, Math.min(size, this._buffer.remaining()));
                    int numReadBytes = this._buffer.position() - pos;
                    if (numReadBytes == 0) {
                        int i = this._streamPos;
                        this._posLimit = i;
                        int i2 = this._bufferOffset;
                        int pointerToPostion = i + i2;
                        int i3 = this._pointerToLastSafePosition;
                        if (pointerToPostion > i3) {
                            this._posLimit = i3 - i2;
                        }
                        this._streamEndWasReached = true;
                        return;
                    }
                    int i4 = this._streamPos + numReadBytes;
                    this._streamPos = i4;
                    int i5 = this._pos;
                    int i6 = this._keepSizeAfter;
                    if (i4 >= i5 + i6) {
                        this._posLimit = i4 - i6;
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void free() {
        this._bufferBase = null;
    }

    public void create(int keepSizeBefore, int keepSizeAfter, int keepSizeReserv) {
        this._keepSizeBefore = keepSizeBefore;
        this._keepSizeAfter = keepSizeAfter;
        int blockSize = keepSizeBefore + keepSizeAfter + keepSizeReserv;
        if (this._bufferBase == null || this._blockSize != blockSize) {
            free();
            this._blockSize = blockSize;
            this._bufferBase = new byte[blockSize];
        }
        this._pointerToLastSafePosition = this._blockSize - keepSizeAfter;
    }

    public void setBuffer(Buffer buffer) {
        this._buffer = buffer;
    }

    public void releaseBuffer() {
        this._buffer = null;
    }

    public void init() {
        this._bufferOffset = 0;
        this._pos = 0;
        this._streamPos = 0;
        this._streamEndWasReached = false;
        readBlock();
    }

    public void movePos() {
        int i = this._pos + 1;
        this._pos = i;
        if (i > this._posLimit) {
            if (this._bufferOffset + i > this._pointerToLastSafePosition) {
                moveBlock();
            }
            readBlock();
        }
    }

    public byte getIndexByte(int index) {
        return this._bufferBase[this._bufferOffset + this._pos + index];
    }

    public int getMatchLen(int index, int distance, int limit) {
        if (this._streamEndWasReached) {
            int i = this._pos;
            int i2 = i + index + limit;
            int i3 = this._streamPos;
            if (i2 > i3) {
                limit = i3 - (i + index);
            }
        }
        int distance2 = distance + 1;
        int pby = this._bufferOffset + this._pos + index;
        int i4 = 0;
        while (i4 < limit) {
            byte[] bArr = this._bufferBase;
            if (bArr[pby + i4] != bArr[(pby + i4) - distance2]) {
                break;
            }
            i4++;
        }
        return i4;
    }

    public int getNumAvailableBytes() {
        return this._streamPos - this._pos;
    }

    public void reduceOffsets(int subValue) {
        this._bufferOffset += subValue;
        this._posLimit -= subValue;
        this._pos -= subValue;
        this._streamPos -= subValue;
    }
}
