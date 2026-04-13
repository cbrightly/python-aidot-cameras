package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import java.util.ArrayList;
import java.util.List;

public class AdaptiveRecvByteBufAllocator implements RecvByteBufAllocator {
    public static final AdaptiveRecvByteBufAllocator DEFAULT = new AdaptiveRecvByteBufAllocator();
    static final int DEFAULT_INITIAL = 1024;
    static final int DEFAULT_MAXIMUM = 65536;
    static final int DEFAULT_MINIMUM = 64;
    private static final int INDEX_DECREMENT = 1;
    private static final int INDEX_INCREMENT = 4;
    /* access modifiers changed from: private */
    public static final int[] SIZE_TABLE;
    private final int initial;
    private final int maxIndex;
    private final int minIndex;

    static {
        List<Integer> sizeTable = new ArrayList<>();
        for (int i = 16; i < 512; i += 16) {
            sizeTable.add(Integer.valueOf(i));
        }
        for (int i2 = 512; i2 > 0; i2 <<= 1) {
            sizeTable.add(Integer.valueOf(i2));
        }
        SIZE_TABLE = new int[sizeTable.size()];
        int i3 = 0;
        while (true) {
            int[] iArr = SIZE_TABLE;
            if (i3 < iArr.length) {
                iArr[i3] = sizeTable.get(i3).intValue();
                i3++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static int getSizeTableIndex(int size) {
        int low = 0;
        int high = SIZE_TABLE.length - 1;
        while (high >= low) {
            if (high == low) {
                return high;
            }
            int mid = (low + high) >>> 1;
            int[] iArr = SIZE_TABLE;
            int a = iArr[mid];
            if (size > iArr[mid + 1]) {
                low = mid + 1;
            } else if (size < a) {
                high = mid - 1;
            } else if (size == a) {
                return mid;
            } else {
                return mid + 1;
            }
        }
        return low;
    }

    public static final class HandleImpl implements RecvByteBufAllocator.Handle {
        private boolean decreaseNow;
        private int index;
        private final int maxIndex;
        private final int minIndex;
        private int nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];

        HandleImpl(int minIndex2, int maxIndex2, int initial) {
            this.minIndex = minIndex2;
            this.maxIndex = maxIndex2;
            this.index = AdaptiveRecvByteBufAllocator.getSizeTableIndex(initial);
        }

        public ByteBuf allocate(ByteBufAllocator alloc) {
            return alloc.ioBuffer(this.nextReceiveBufferSize);
        }

        public int guess() {
            return this.nextReceiveBufferSize;
        }

        public void record(int actualReadBytes) {
            if (actualReadBytes <= AdaptiveRecvByteBufAllocator.SIZE_TABLE[Math.max(0, (this.index - 1) - 1)]) {
                if (this.decreaseNow) {
                    this.index = Math.max(this.index - 1, this.minIndex);
                    this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
                    this.decreaseNow = false;
                    return;
                }
                this.decreaseNow = true;
            } else if (actualReadBytes >= this.nextReceiveBufferSize) {
                this.index = Math.min(this.index + 4, this.maxIndex);
                this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
                this.decreaseNow = false;
            }
        }
    }

    private AdaptiveRecvByteBufAllocator() {
        this(64, 1024, 65536);
    }

    public AdaptiveRecvByteBufAllocator(int minimum, int initial2, int maximum) {
        if (minimum <= 0) {
            throw new IllegalArgumentException("minimum: " + minimum);
        } else if (initial2 < minimum) {
            throw new IllegalArgumentException("initial: " + initial2);
        } else if (maximum >= initial2) {
            int minIndex2 = getSizeTableIndex(minimum);
            int[] iArr = SIZE_TABLE;
            if (iArr[minIndex2] < minimum) {
                this.minIndex = minIndex2 + 1;
            } else {
                this.minIndex = minIndex2;
            }
            int maxIndex2 = getSizeTableIndex(maximum);
            if (iArr[maxIndex2] > maximum) {
                this.maxIndex = maxIndex2 - 1;
            } else {
                this.maxIndex = maxIndex2;
            }
            this.initial = initial2;
        } else {
            throw new IllegalArgumentException("maximum: " + maximum);
        }
    }

    public RecvByteBufAllocator.Handle newHandle() {
        return new HandleImpl(this.minIndex, this.maxIndex, this.initial);
    }
}
