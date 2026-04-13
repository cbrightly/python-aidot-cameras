package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class RangeEncoder {
    private static final int[] ProbPrices = new int[512];
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    public static final int kNumBitPriceShiftBits = 6;
    static final int kNumMoveBits = 5;
    static final int kNumMoveReducingBits = 2;
    static final int kTopMask = -16777216;
    long Low;
    int Range;
    int _cache;
    int _cacheSize;
    long _position;
    Buffer dst;
    MemoryManager mm;

    public void setBuffer(Buffer dst2, MemoryManager mm2) {
        this.dst = dst2;
        this.mm = mm2;
    }

    public Buffer releaseBuffer() {
        this.mm = null;
        try {
            return this.dst;
        } finally {
            this.dst = null;
        }
    }

    public void init() {
        this._position = 0;
        this.Low = 0;
        this.Range = -1;
        this._cacheSize = 1;
        this._cache = 0;
    }

    public void flushData() {
        for (int i = 0; i < 5; i++) {
            shiftLow();
        }
    }

    public void shiftLow() {
        int i;
        long j = this.Low;
        int LowHi = (int) (j >>> 32);
        if (LowHi != 0 || j < 4278190080L) {
            this._position += (long) this._cacheSize;
            int temp = this._cache;
            do {
                if (!this.dst.hasRemaining()) {
                    this.dst = resizeBuffer(this.mm, this.dst, 1);
                }
                this.dst.put((byte) (temp + LowHi));
                temp = 255;
                i = this._cacheSize - 1;
                this._cacheSize = i;
            } while (i != 0);
            this._cache = ((int) this.Low) >>> 24;
        }
        this._cacheSize++;
        this.Low = (this.Low & 16777215) << 8;
    }

    public void encodeDirectBits(int v, int numTotalBits) {
        for (int i = numTotalBits - 1; i >= 0; i--) {
            int i2 = this.Range >>> 1;
            this.Range = i2;
            if (((v >>> i) & 1) == 1) {
                this.Low += (long) i2;
            }
            if ((-16777216 & i2) == 0) {
                this.Range = i2 << 8;
                shiftLow();
            }
        }
    }

    public long getProcessedSizeAdd() {
        return ((long) this._cacheSize) + this._position + 4;
    }

    public static void initBitModels(short[] probs) {
        for (int i = 0; i < probs.length; i++) {
            probs[i] = 1024;
        }
    }

    public void encode(short[] probs, int index, int symbol) {
        short prob = probs[index];
        int i = this.Range;
        int newBound = (i >>> 11) * prob;
        if (symbol == 0) {
            this.Range = newBound;
            probs[index] = (short) (((2048 - prob) >>> 5) + prob);
        } else {
            this.Low += ((long) newBound) & 4294967295L;
            this.Range = i - newBound;
            probs[index] = (short) (prob - (prob >>> 5));
        }
        int i2 = this.Range;
        if ((-16777216 & i2) == 0) {
            this.Range = i2 << 8;
            shiftLow();
        }
    }

    static {
        for (int i = 9 - 1; i >= 0; i--) {
            int end = 1 << (9 - i);
            for (int j = 1 << ((9 - i) - 1); j < end; j++) {
                ProbPrices[j] = (i << 6) + (((end - j) << 6) >>> ((9 - i) - 1));
            }
        }
    }

    public static int getPrice(int Prob, int symbol) {
        return ProbPrices[(((Prob - symbol) ^ (-symbol)) & 2047) >>> 2];
    }

    public static int getPrice0(int Prob) {
        return ProbPrices[Prob >>> 2];
    }

    public static int getPrice1(int Prob) {
        return ProbPrices[(2048 - Prob) >>> 2];
    }

    private static Buffer resizeBuffer(MemoryManager memoryManager, Buffer headerBuffer, int grow) {
        return memoryManager.reallocate(headerBuffer, Math.max(headerBuffer.capacity() + grow, ((headerBuffer.capacity() * 3) / 2) + 1));
    }
}
