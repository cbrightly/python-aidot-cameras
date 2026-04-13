package org.glassfish.grizzly.compression.lzma.impl.lz;

public class BinTree extends InWindow {
    private static final int[] CrcTable = new int[256];
    static final int kBT2HashSize = 65536;
    static final int kEmptyHashValue = 0;
    static final int kHash2Size = 1024;
    static final int kHash3Offset = 1024;
    static final int kHash3Size = 65536;
    static final int kMaxValForNormalize = 1073741823;
    static final int kStartMaxLen = 1;
    boolean HASH_ARRAY = true;
    int _cutValue = 255;
    int _cyclicBufferPos;
    int _cyclicBufferSize = 0;
    int[] _hash;
    int _hashMask;
    int _hashSizeSum = 0;
    int _matchMaxLen;
    int[] _son;
    int kFixHashSize = 66560;
    int kMinMatchCheck = 4;
    int kNumHashDirectBytes = 0;

    public void setType(int numHashBytes) {
        boolean z = numHashBytes > 2;
        this.HASH_ARRAY = z;
        if (z) {
            this.kNumHashDirectBytes = 0;
            this.kMinMatchCheck = 4;
            this.kFixHashSize = 66560;
            return;
        }
        this.kNumHashDirectBytes = 2;
        this.kMinMatchCheck = 3;
        this.kFixHashSize = 0;
    }

    public void init() {
        super.init();
        for (int i = 0; i < this._hashSizeSum; i++) {
            this._hash[i] = 0;
        }
        this._cyclicBufferPos = 0;
        reduceOffsets(-1);
    }

    public void movePos() {
        int i = this._cyclicBufferPos + 1;
        this._cyclicBufferPos = i;
        if (i >= this._cyclicBufferSize) {
            this._cyclicBufferPos = 0;
        }
        super.movePos();
        if (this._pos == kMaxValForNormalize) {
            normalize();
        }
    }

    public boolean create(int historySize, int keepAddBufferBefore, int matchMaxLen, int keepAddBufferAfter) {
        if (historySize > 1073741567) {
            return false;
        }
        this._cutValue = (matchMaxLen >> 1) + 16;
        super.create(historySize + keepAddBufferBefore, matchMaxLen + keepAddBufferAfter, ((((historySize + keepAddBufferBefore) + matchMaxLen) + keepAddBufferAfter) / 2) + 256);
        this._matchMaxLen = matchMaxLen;
        int cyclicBufferSize = historySize + 1;
        if (this._cyclicBufferSize != cyclicBufferSize) {
            this._cyclicBufferSize = cyclicBufferSize;
            this._son = new int[(cyclicBufferSize * 2)];
        }
        int hs = 65536;
        if (this.HASH_ARRAY) {
            int hs2 = historySize - 1;
            int hs3 = hs2 | (hs2 >> 1);
            int hs4 = hs3 | (hs3 >> 2);
            int hs5 = hs4 | (hs4 >> 4);
            int hs6 = ((hs5 | (hs5 >> 8)) >> 1) | 65535;
            if (hs6 > 16777216) {
                hs6 >>= 1;
            }
            this._hashMask = hs6;
            hs = hs6 + 1 + this.kFixHashSize;
        }
        if (hs != this._hashSizeSum) {
            this._hashSizeSum = hs;
            this._hash = new int[hs];
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01c5, code lost:
        r5 = r0._son;
        r5[r4] = 0;
        r5[r9] = 0;
        r6 = r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMatches(int[] r23) {
        /*
            r22 = this;
            r0 = r22
            int r1 = r0._pos
            int r2 = r0._matchMaxLen
            int r2 = r2 + r1
            int r3 = r0._streamPos
            r4 = 0
            if (r2 > r3) goto L_0x000f
            int r2 = r0._matchMaxLen
            goto L_0x0019
        L_0x000f:
            int r2 = r3 - r1
            int r3 = r0.kMinMatchCheck
            if (r2 >= r3) goto L_0x0019
            r22.movePos()
            return r4
        L_0x0019:
            r3 = 0
            int r5 = r0._cyclicBufferSize
            if (r1 <= r5) goto L_0x0021
            int r5 = r1 - r5
            goto L_0x0022
        L_0x0021:
            r5 = r4
        L_0x0022:
            int r6 = r0._bufferOffset
            int r7 = r6 + r1
            r8 = 1
            r9 = 0
            r10 = 0
            boolean r11 = r0.HASH_ARRAY
            if (r11 == 0) goto L_0x005d
            int[] r12 = CrcTable
            byte[] r13 = r0._bufferBase
            byte r14 = r13[r7]
            r14 = r14 & 255(0xff, float:3.57E-43)
            r14 = r12[r14]
            int r15 = r7 + 1
            byte r15 = r13[r15]
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14 = r14 ^ r15
            r9 = r14 & 1023(0x3ff, float:1.434E-42)
            int r15 = r7 + 2
            byte r15 = r13[r15]
            r15 = r15 & 255(0xff, float:3.57E-43)
            int r15 = r15 << 8
            r14 = r14 ^ r15
            r15 = 65535(0xffff, float:9.1834E-41)
            r10 = r14 & r15
            int r15 = r7 + 3
            byte r13 = r13[r15]
            r13 = r13 & 255(0xff, float:3.57E-43)
            r12 = r12[r13]
            int r12 = r12 << 5
            r12 = r12 ^ r14
            int r13 = r0._hashMask
            r12 = r12 & r13
            goto L_0x006c
        L_0x005d:
            byte[] r12 = r0._bufferBase
            byte r13 = r12[r7]
            r13 = r13 & 255(0xff, float:3.57E-43)
            int r14 = r7 + 1
            byte r12 = r12[r14]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r12 = r12 << 8
            r12 = r12 ^ r13
        L_0x006c:
            int[] r13 = r0._hash
            int r14 = r0.kFixHashSize
            int r15 = r14 + r12
            r15 = r13[r15]
            if (r11 == 0) goto L_0x00d9
            r11 = r13[r9]
            int r4 = r10 + 1024
            r4 = r13[r4]
            r13[r9] = r1
            r16 = r8
            int r8 = r10 + 1024
            r13[r8] = r1
            if (r11 <= r5) goto L_0x00a4
            byte[] r8 = r0._bufferBase
            int r17 = r6 + r11
            r18 = r9
            byte r9 = r8[r17]
            byte r8 = r8[r7]
            if (r9 != r8) goto L_0x00a6
            int r8 = r3 + 1
            r9 = 2
            r16 = r9
            r23[r3] = r9
            int r3 = r8 + 1
            int r9 = r1 - r11
            int r9 = r9 + -1
            r23[r8] = r9
            r8 = r16
            goto L_0x00a8
        L_0x00a4:
            r18 = r9
        L_0x00a6:
            r8 = r16
        L_0x00a8:
            if (r4 <= r5) goto L_0x00cd
            byte[] r9 = r0._bufferBase
            int r16 = r6 + r4
            r17 = r8
            byte r8 = r9[r16]
            byte r9 = r9[r7]
            if (r8 != r9) goto L_0x00cf
            if (r4 != r11) goto L_0x00ba
            int r3 = r3 + -2
        L_0x00ba:
            int r8 = r3 + 1
            r9 = 3
            r16 = r9
            r23[r3] = r9
            int r3 = r8 + 1
            int r9 = r1 - r4
            int r9 = r9 + -1
            r23[r8] = r9
            r11 = r4
            r8 = r16
            goto L_0x00d1
        L_0x00cd:
            r17 = r8
        L_0x00cf:
            r8 = r17
        L_0x00d1:
            if (r3 == 0) goto L_0x00dd
            if (r11 != r15) goto L_0x00dd
            int r3 = r3 + -2
            r8 = 1
            goto L_0x00dd
        L_0x00d9:
            r16 = r8
            r18 = r9
        L_0x00dd:
            int r14 = r14 + r12
            r13[r14] = r1
            int r4 = r0._cyclicBufferPos
            int r9 = r4 << 1
            int r9 = r9 + 1
            int r4 = r4 << 1
            int r11 = r0.kNumHashDirectBytes
            r13 = r11
            r14 = r11
            if (r11 == 0) goto L_0x010e
            if (r15 <= r5) goto L_0x010b
            r16 = r4
            byte[] r4 = r0._bufferBase
            int r6 = r6 + r15
            int r6 = r6 + r11
            byte r6 = r4[r6]
            int r17 = r7 + r11
            byte r4 = r4[r17]
            if (r6 == r4) goto L_0x0110
            int r4 = r3 + 1
            r8 = r11
            r23[r3] = r11
            int r3 = r4 + 1
            int r1 = r1 - r15
            int r1 = r1 + -1
            r23[r4] = r1
            goto L_0x0110
        L_0x010b:
            r16 = r4
            goto L_0x0110
        L_0x010e:
            r16 = r4
        L_0x0110:
            int r1 = r0._cutValue
            r4 = r16
        L_0x0114:
            if (r15 <= r5) goto L_0x01bf
            int r6 = r1 + -1
            if (r1 != 0) goto L_0x0123
            r16 = r5
            r1 = r6
            r21 = r10
            r20 = r12
            goto L_0x01c5
        L_0x0123:
            int r1 = r0._pos
            int r1 = r1 - r15
            int r11 = r0._cyclicBufferPos
            if (r1 > r11) goto L_0x012e
            int r11 = r11 - r1
            r16 = r5
            goto L_0x0134
        L_0x012e:
            int r11 = r11 - r1
            r16 = r5
            int r5 = r0._cyclicBufferSize
            int r11 = r11 + r5
        L_0x0134:
            int r5 = r11 << 1
            int r11 = r0._bufferOffset
            int r11 = r11 + r15
            int r17 = java.lang.Math.min(r14, r13)
            r19 = r6
            byte[] r6 = r0._bufferBase
            int r20 = r11 + r17
            r21 = r10
            byte r10 = r6[r20]
            int r20 = r7 + r17
            byte r6 = r6[r20]
            if (r10 != r6) goto L_0x018b
        L_0x014d:
            int r6 = r17 + 1
            if (r6 == r2) goto L_0x0165
            byte[] r10 = r0._bufferBase
            int r17 = r11 + r6
            r20 = r12
            byte r12 = r10[r17]
            int r17 = r7 + r6
            byte r10 = r10[r17]
            if (r12 == r10) goto L_0x0160
            goto L_0x0167
        L_0x0160:
            r17 = r6
            r12 = r20
            goto L_0x014d
        L_0x0165:
            r20 = r12
        L_0x0167:
            if (r8 >= r6) goto L_0x0188
            int r10 = r3 + 1
            r8 = r6
            r23[r3] = r6
            int r3 = r10 + 1
            int r12 = r1 + -1
            r23[r10] = r12
            if (r6 != r2) goto L_0x0185
            int[] r10 = r0._son
            r12 = r10[r5]
            r10[r4] = r12
            int r12 = r5 + 1
            r12 = r10[r12]
            r10[r9] = r12
            r6 = r19
            goto L_0x01cd
        L_0x0185:
            r17 = r6
            goto L_0x018d
        L_0x0188:
            r17 = r6
            goto L_0x018d
        L_0x018b:
            r20 = r12
        L_0x018d:
            byte[] r6 = r0._bufferBase
            int r10 = r11 + r17
            byte r10 = r6[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r12 = r7 + r17
            byte r6 = r6[r12]
            r6 = r6 & 255(0xff, float:3.57E-43)
            if (r10 >= r6) goto L_0x01aa
            int[] r6 = r0._son
            r6[r4] = r15
            int r4 = r5 + 1
            r6 = r6[r4]
            r10 = r17
            r15 = r6
            r13 = r10
            goto L_0x01b5
        L_0x01aa:
            int[] r6 = r0._son
            r6[r9] = r15
            r9 = r5
            r6 = r6[r9]
            r10 = r17
            r15 = r6
            r14 = r10
        L_0x01b5:
            r5 = r16
            r1 = r19
            r12 = r20
            r10 = r21
            goto L_0x0114
        L_0x01bf:
            r16 = r5
            r21 = r10
            r20 = r12
        L_0x01c5:
            int[] r5 = r0._son
            r6 = 0
            r5[r4] = r6
            r5[r9] = r6
            r6 = r1
        L_0x01cd:
            r22.movePos()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.lz.BinTree.getMatches(int[]):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x011b, code lost:
        r4 = r0._son;
        r4[r2] = 0;
        r4[r8] = 0;
        r13 = r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skip(int r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
        L_0x0004:
            int r2 = r0._pos
            int r3 = r0._matchMaxLen
            int r3 = r3 + r2
            int r4 = r0._streamPos
            if (r3 > r4) goto L_0x0010
            int r3 = r0._matchMaxLen
            goto L_0x001b
        L_0x0010:
            int r3 = r4 - r2
            int r4 = r0.kMinMatchCheck
            if (r3 >= r4) goto L_0x001b
            r19.movePos()
            goto L_0x0126
        L_0x001b:
            int r4 = r0._cyclicBufferSize
            if (r2 <= r4) goto L_0x0022
            int r4 = r2 - r4
            goto L_0x0023
        L_0x0022:
            r4 = 0
        L_0x0023:
            int r6 = r0._bufferOffset
            int r6 = r6 + r2
            boolean r7 = r0.HASH_ARRAY
            if (r7 == 0) goto L_0x0061
            int[] r7 = CrcTable
            byte[] r8 = r0._bufferBase
            byte r9 = r8[r6]
            r9 = r9 & 255(0xff, float:3.57E-43)
            r9 = r7[r9]
            int r10 = r6 + 1
            byte r10 = r8[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            r9 = r9 ^ r10
            r10 = r9 & 1023(0x3ff, float:1.434E-42)
            int[] r11 = r0._hash
            r11[r10] = r2
            int r12 = r6 + 2
            byte r12 = r8[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r12 = r12 << 8
            r9 = r9 ^ r12
            r12 = 65535(0xffff, float:9.1834E-41)
            r12 = r12 & r9
            int r13 = r12 + 1024
            r11[r13] = r2
            int r11 = r6 + 3
            byte r8 = r8[r11]
            r8 = r8 & 255(0xff, float:3.57E-43)
            r7 = r7[r8]
            int r7 = r7 << 5
            r7 = r7 ^ r9
            int r8 = r0._hashMask
            r7 = r7 & r8
            goto L_0x0070
        L_0x0061:
            byte[] r7 = r0._bufferBase
            byte r8 = r7[r6]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r9 = r6 + 1
            byte r7 = r7[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r7 = r7 ^ r8
        L_0x0070:
            int[] r8 = r0._hash
            int r9 = r0.kFixHashSize
            int r10 = r9 + r7
            r10 = r8[r10]
            int r9 = r9 + r7
            r8[r9] = r2
            int r2 = r0._cyclicBufferPos
            int r8 = r2 << 1
            int r8 = r8 + 1
            int r2 = r2 << 1
            int r9 = r0.kNumHashDirectBytes
            r11 = r9
            int r12 = r0._cutValue
        L_0x0088:
            if (r10 <= r4) goto L_0x0117
            int r13 = r12 + -1
            if (r12 != 0) goto L_0x0095
            r18 = r4
            r17 = r7
            r12 = r13
            goto L_0x011b
        L_0x0095:
            int r12 = r0._pos
            int r12 = r12 - r10
            int r14 = r0._cyclicBufferPos
            if (r12 > r14) goto L_0x009e
            int r14 = r14 - r12
            goto L_0x00a2
        L_0x009e:
            int r14 = r14 - r12
            int r15 = r0._cyclicBufferSize
            int r14 = r14 + r15
        L_0x00a2:
            int r14 = r14 << 1
            int r15 = r0._bufferOffset
            int r15 = r15 + r10
            int r16 = java.lang.Math.min(r9, r11)
            byte[] r5 = r0._bufferBase
            int r17 = r15 + r16
            r18 = r4
            byte r4 = r5[r17]
            int r17 = r6 + r16
            byte r5 = r5[r17]
            if (r4 != r5) goto L_0x00e5
        L_0x00b9:
            int r4 = r16 + 1
            if (r4 == r3) goto L_0x00d1
            byte[] r5 = r0._bufferBase
            int r16 = r15 + r4
            r17 = r7
            byte r7 = r5[r16]
            int r16 = r6 + r4
            byte r5 = r5[r16]
            if (r7 == r5) goto L_0x00cc
            goto L_0x00d3
        L_0x00cc:
            r16 = r4
            r7 = r17
            goto L_0x00b9
        L_0x00d1:
            r17 = r7
        L_0x00d3:
            if (r4 != r3) goto L_0x00e2
            int[] r5 = r0._son
            r7 = r5[r14]
            r5[r2] = r7
            int r7 = r14 + 1
            r7 = r5[r7]
            r5[r8] = r7
            goto L_0x0123
        L_0x00e2:
            r16 = r4
            goto L_0x00e7
        L_0x00e5:
            r17 = r7
        L_0x00e7:
            byte[] r4 = r0._bufferBase
            int r5 = r15 + r16
            byte r5 = r4[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r7 = r6 + r16
            byte r4 = r4[r7]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r5 >= r4) goto L_0x0104
            int[] r4 = r0._son
            r4[r2] = r10
            int r2 = r14 + 1
            r4 = r4[r2]
            r5 = r16
            r10 = r4
            r11 = r5
            goto L_0x0110
        L_0x0104:
            int[] r4 = r0._son
            r4[r8] = r10
            r5 = r14
            r4 = r4[r5]
            r7 = r16
            r10 = r4
            r8 = r5
            r9 = r7
        L_0x0110:
            r12 = r13
            r7 = r17
            r4 = r18
            goto L_0x0088
        L_0x0117:
            r18 = r4
            r17 = r7
        L_0x011b:
            int[] r4 = r0._son
            r5 = 0
            r4[r2] = r5
            r4[r8] = r5
            r13 = r12
        L_0x0123:
            r19.movePos()
        L_0x0126:
            int r1 = r1 + -1
            if (r1 != 0) goto L_0x012b
            return
        L_0x012b:
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.lz.BinTree.skip(int):void");
    }

    /* access modifiers changed from: package-private */
    public void normalizeLinks(int[] items, int numItems, int subValue) {
        int value;
        for (int i = 0; i < numItems; i++) {
            int value2 = items[i];
            if (value2 <= subValue) {
                value = 0;
            } else {
                value = value2 - subValue;
            }
            items[i] = value;
        }
    }

    /* access modifiers changed from: package-private */
    public void normalize() {
        int i = this._pos;
        int i2 = this._cyclicBufferSize;
        int subValue = i - i2;
        normalizeLinks(this._son, i2 * 2, subValue);
        normalizeLinks(this._hash, this._hashSizeSum, subValue);
        reduceOffsets(subValue);
    }

    static {
        for (int i = 0; i < 256; i++) {
            int r = i;
            for (int j = 0; j < 8; j++) {
                if ((r & 1) != 0) {
                    r = (r >>> 1) ^ -306674912;
                } else {
                    r >>>= 1;
                }
            }
            CrcTable[i] = r;
        }
    }
}
