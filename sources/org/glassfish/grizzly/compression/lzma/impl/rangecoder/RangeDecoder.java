package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;

public class RangeDecoder {
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    static final int kNumMoveBits = 5;
    static final int kTopMask = -16777216;
    int Code;
    int Range;
    int decodeBitState;
    int decodeDirectBitsI;
    int decodeDirectBitsResult;
    int decodeDirectBitsState;
    Buffer inputBuffer;
    int newBound;

    public final void initFromState(LZMADecoder.LZMAInputState decoderState) {
        this.inputBuffer = decoderState.getSrc();
    }

    public final void releaseBuffer() {
        this.inputBuffer = null;
    }

    public final void init() {
        this.Code = 0;
        this.Range = -1;
        this.decodeBitState = 0;
        this.decodeDirectBitsState = 0;
        for (int i = 0; i < 5; i++) {
            this.Code = (this.Code << 8) | (this.inputBuffer.get() & 255);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003d, code lost:
        if (r6.decodeDirectBitsI != 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003f, code lost:
        r6.decodeDirectBitsState = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0043, code lost:
        r0 = r6.Range >>> 1;
        r6.Range = r0;
        r3 = r6.Code;
        r4 = (r3 - r0) >>> 31;
        r6.Code = r3 - ((r4 - 1) & r0);
        r6.decodeDirectBitsResult = (r6.decodeDirectBitsResult << 1) | (1 - r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005f, code lost:
        if ((r0 & -16777216) != 0) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0061, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0063, code lost:
        if (r1 == false) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0065, code lost:
        r1 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0067, code lost:
        r1 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        r6.decodeDirectBitsState = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002d, code lost:
        r6.decodeDirectBitsI--;
        r6.decodeDirectBitsState = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean decodeDirectBits(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r7, int r8) {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r6.decodeDirectBitsState
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x0035;
                case 1: goto L_0x003b;
                case 2: goto L_0x000f;
                case 3: goto L_0x002d;
                case 4: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0000
        L_0x0008:
            int r0 = r6.decodeDirectBitsResult
            r7.lastMethodResult = r0
            r6.decodeDirectBitsState = r1
            return r2
        L_0x000f:
            org.glassfish.grizzly.Buffer r0 = r6.inputBuffer
            boolean r0 = r0.hasRemaining()
            if (r0 != 0) goto L_0x0018
            return r1
        L_0x0018:
            int r0 = r6.Code
            int r0 = r0 << 8
            org.glassfish.grizzly.Buffer r1 = r6.inputBuffer
            byte r1 = r1.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            r6.Code = r0
            int r0 = r6.Range
            int r0 = r0 << 8
            r6.Range = r0
        L_0x002d:
            int r0 = r6.decodeDirectBitsI
            int r0 = r0 - r2
            r6.decodeDirectBitsI = r0
            r6.decodeDirectBitsState = r2
            goto L_0x0000
        L_0x0035:
            r6.decodeDirectBitsResult = r1
            r6.decodeDirectBitsI = r8
            r6.decodeDirectBitsState = r2
        L_0x003b:
            int r0 = r6.decodeDirectBitsI
            if (r0 != 0) goto L_0x0043
            r0 = 4
            r6.decodeDirectBitsState = r0
            goto L_0x0000
        L_0x0043:
            int r0 = r6.Range
            int r0 = r0 >>> r2
            r6.Range = r0
            int r3 = r6.Code
            int r4 = r3 - r0
            int r4 = r4 >>> 31
            int r5 = r4 + -1
            r5 = r5 & r0
            int r3 = r3 - r5
            r6.Code = r3
            int r3 = r6.decodeDirectBitsResult
            int r3 = r3 << r2
            int r5 = 1 - r4
            r3 = r3 | r5
            r6.decodeDirectBitsResult = r3
            r3 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r3
            if (r0 != 0) goto L_0x0062
            r1 = r2
        L_0x0062:
            r0 = r1
            if (r0 == 0) goto L_0x0067
            r1 = 2
            goto L_0x0068
        L_0x0067:
            r1 = 3
        L_0x0068:
            r6.decodeDirectBitsState = r1
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder.decodeDirectBits(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0029, code lost:
        r9.lastMethodResult = 1;
        r8.decodeBitState = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean decodeBit(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r9, short[] r10, int r11) {
        /*
            r8 = this;
        L_0x0000:
            int r0 = r8.decodeBitState
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r2 = 5
            r3 = 1
            r4 = 0
            switch(r0) {
                case 0: goto L_0x0090;
                case 1: goto L_0x0073;
                case 2: goto L_0x0050;
                case 3: goto L_0x006e;
                case 4: goto L_0x002e;
                case 5: goto L_0x000b;
                case 6: goto L_0x0029;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0000
        L_0x000b:
            org.glassfish.grizzly.Buffer r0 = r8.inputBuffer
            boolean r0 = r0.hasRemaining()
            if (r0 != 0) goto L_0x0014
            return r4
        L_0x0014:
            int r0 = r8.Code
            int r0 = r0 << 8
            org.glassfish.grizzly.Buffer r1 = r8.inputBuffer
            byte r1 = r1.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            r8.Code = r0
            int r0 = r8.Range
            int r0 = r0 << 8
            r8.Range = r0
        L_0x0029:
            r9.lastMethodResult = r3
            r8.decodeBitState = r4
            return r3
        L_0x002e:
            short r0 = r10[r11]
            int r5 = r8.Range
            int r6 = r8.newBound
            int r5 = r5 - r6
            r8.Range = r5
            int r7 = r8.Code
            int r7 = r7 - r6
            r8.Code = r7
            int r6 = r0 >>> 5
            int r6 = r0 - r6
            short r6 = (short) r6
            r10[r11] = r6
            r1 = r1 & r5
            if (r1 != 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r3 = r4
        L_0x0048:
            r1 = r3
            if (r1 == 0) goto L_0x004c
            goto L_0x004d
        L_0x004c:
            r2 = 6
        L_0x004d:
            r8.decodeBitState = r2
            goto L_0x0000
        L_0x0050:
            org.glassfish.grizzly.Buffer r0 = r8.inputBuffer
            boolean r0 = r0.hasRemaining()
            if (r0 != 0) goto L_0x0059
            return r4
        L_0x0059:
            int r0 = r8.Code
            int r0 = r0 << 8
            org.glassfish.grizzly.Buffer r1 = r8.inputBuffer
            byte r1 = r1.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            r8.Code = r0
            int r0 = r8.Range
            int r0 = r0 << 8
            r8.Range = r0
        L_0x006e:
            r9.lastMethodResult = r4
            r8.decodeBitState = r4
            return r3
        L_0x0073:
            short r0 = r10[r11]
            int r5 = r8.newBound
            r8.Range = r5
            int r6 = 2048 - r0
            int r2 = r6 >>> 5
            int r2 = r2 + r0
            short r2 = (short) r2
            r10[r11] = r2
            r1 = r1 & r5
            if (r1 != 0) goto L_0x0085
            goto L_0x0086
        L_0x0085:
            r3 = r4
        L_0x0086:
            r1 = r3
            if (r1 == 0) goto L_0x008b
            r2 = 2
            goto L_0x008c
        L_0x008b:
            r2 = 3
        L_0x008c:
            r8.decodeBitState = r2
            goto L_0x0000
        L_0x0090:
            short r0 = r10[r11]
            int r1 = r8.Range
            int r1 = r1 >>> 11
            int r1 = r1 * r0
            r8.newBound = r1
            int r2 = r8.Code
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 ^ r5
            r1 = r1 ^ r5
            if (r2 >= r1) goto L_0x00a2
            r4 = r3
        L_0x00a2:
            r1 = r4
            if (r1 == 0) goto L_0x00a6
            goto L_0x00a7
        L_0x00a6:
            r3 = 4
        L_0x00a7:
            r8.decodeBitState = r3
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder.decodeBit(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, short[], int):boolean");
    }

    public static void initBitModels(short[] probs) {
        for (int i = 0; i < probs.length; i++) {
            probs[i] = 1024;
        }
    }
}
