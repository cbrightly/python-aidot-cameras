package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

public class BitTreeDecoder {
    final short[] Models;
    final int NumBitLevels;
    int bitIndex;
    int decodeMethodState;
    int m;
    int reverseDecodeMethodState;
    int symbol;

    public BitTreeDecoder(int numBitLevels) {
        this.NumBitLevels = numBitLevels;
        this.Models = new short[(1 << numBitLevels)];
    }

    public void init() {
        this.decodeMethodState = 0;
        this.reverseDecodeMethodState = 0;
        RangeDecoder.initBitModels(this.Models);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0031 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean decode(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r5, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r6) {
        /*
            r4 = this;
        L_0x0000:
            int r0 = r4.decodeMethodState
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x0014;
                case 1: goto L_0x001c;
                case 2: goto L_0x0027;
                case 3: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0000
        L_0x0008:
            int r0 = r4.m
            int r3 = r4.NumBitLevels
            int r3 = r2 << r3
            int r0 = r0 - r3
            r5.lastMethodResult = r0
            r4.decodeMethodState = r1
            return r2
        L_0x0014:
            r4.m = r2
            int r0 = r4.NumBitLevels
            r4.bitIndex = r0
            r4.decodeMethodState = r2
        L_0x001c:
            int r0 = r4.bitIndex
            if (r0 != 0) goto L_0x0024
            r0 = 3
            r4.decodeMethodState = r0
            goto L_0x0000
        L_0x0024:
            r0 = 2
            r4.decodeMethodState = r0
        L_0x0027:
            short[] r0 = r4.Models
            int r3 = r4.m
            boolean r0 = r6.decodeBit(r5, r0, r3)
            if (r0 != 0) goto L_0x0032
            return r1
        L_0x0032:
            int r0 = r4.m
            int r0 = r0 << r2
            int r1 = r5.lastMethodResult
            int r0 = r0 + r1
            r4.m = r0
            int r0 = r4.bitIndex
            int r0 = r0 - r2
            r4.bitIndex = r0
            r4.decodeMethodState = r2
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeDecoder.decode(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002e A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean reverseDecode(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r6, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r7) {
        /*
            r5 = this;
        L_0x0000:
            int r0 = r5.reverseDecodeMethodState
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x000f;
                case 1: goto L_0x0017;
                case 2: goto L_0x0024;
                case 3: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0000
        L_0x0008:
            int r0 = r5.symbol
            r6.lastMethodResult = r0
            r5.reverseDecodeMethodState = r1
            return r2
        L_0x000f:
            r5.m = r2
            r5.symbol = r1
            r5.bitIndex = r1
            r5.reverseDecodeMethodState = r2
        L_0x0017:
            int r0 = r5.bitIndex
            int r3 = r5.NumBitLevels
            if (r0 < r3) goto L_0x0021
            r0 = 3
            r5.reverseDecodeMethodState = r0
            goto L_0x0000
        L_0x0021:
            r0 = 2
            r5.reverseDecodeMethodState = r0
        L_0x0024:
            short[] r0 = r5.Models
            int r3 = r5.m
            boolean r0 = r7.decodeBit(r6, r0, r3)
            if (r0 != 0) goto L_0x002f
            return r1
        L_0x002f:
            int r0 = r6.lastMethodResult
            int r1 = r5.m
            int r1 = r1 << r2
            r5.m = r1
            int r1 = r1 + r0
            r5.m = r1
            int r1 = r5.symbol
            int r3 = r5.bitIndex
            int r4 = r0 << r3
            r1 = r1 | r4
            r5.symbol = r1
            int r3 = r3 + r2
            r5.bitIndex = r3
            r5.reverseDecodeMethodState = r2
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeDecoder.reverseDecode(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean reverseDecode(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r5, short[] r6, int r7, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r8, int r9) {
        /*
        L_0x0000:
            int r0 = r5.staticReverseDecodeMethodState
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x000f;
                case 1: goto L_0x0017;
                case 2: goto L_0x0022;
                case 3: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0000
        L_0x0008:
            int r0 = r5.staticSymbol
            r5.lastMethodResult = r0
            r5.staticReverseDecodeMethodState = r1
            return r2
        L_0x000f:
            r5.staticM = r2
            r5.staticSymbol = r1
            r5.staticBitIndex = r1
            r5.staticReverseDecodeMethodState = r2
        L_0x0017:
            int r0 = r5.staticBitIndex
            if (r0 < r9) goto L_0x001f
            r0 = 3
            r5.staticReverseDecodeMethodState = r0
            goto L_0x0000
        L_0x001f:
            r0 = 2
            r5.staticReverseDecodeMethodState = r0
        L_0x0022:
            int r0 = r5.staticM
            int r0 = r0 + r7
            boolean r0 = r8.decodeBit(r5, r6, r0)
            if (r0 != 0) goto L_0x002c
            return r1
        L_0x002c:
            int r0 = r5.lastMethodResult
            int r1 = r5.staticM
            int r1 = r1 << r2
            r5.staticM = r1
            int r1 = r1 + r0
            r5.staticM = r1
            int r1 = r5.staticSymbol
            int r3 = r5.staticBitIndex
            int r4 = r0 << r3
            r1 = r1 | r4
            r5.staticSymbol = r1
            int r3 = r3 + r2
            r5.staticBitIndex = r3
            r5.staticReverseDecodeMethodState = r2
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeDecoder.reverseDecode(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, short[], int, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder, int):boolean");
    }
}
