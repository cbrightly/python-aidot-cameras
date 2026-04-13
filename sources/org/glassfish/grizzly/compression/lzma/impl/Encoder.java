package org.glassfish.grizzly.compression.lzma.impl;

import com.alibaba.fastjson.asm.Opcodes;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMAEncoder;
import org.glassfish.grizzly.compression.lzma.impl.lz.BinTree;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeEncoder;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class Encoder {
    public static final int EMatchFinderTypeBT2 = 0;
    public static final int EMatchFinderTypeBT4 = 1;
    static final byte[] g_FastPos;
    static final int kDefaultDictionaryLogSize = 22;
    static final int kIfinityPrice = 268435455;
    static final int kNumFastBytesDefault = 32;
    public static final int kNumLenSpecSymbols = 16;
    static final int kNumOpts = 4096;
    int _additionalOffset;
    int _alignPriceCount;
    final int[] _alignPrices = new int[16];
    int _dictionarySize = 4194304;
    int _dictionarySizePrev = -1;
    int _distTableSize = 44;
    final int[] _distancesPrices = new int[512];
    boolean _finished;
    final short[] _isMatch = new short[Opcodes.CHECKCAST];
    final short[] _isRep = new short[12];
    final short[] _isRep0Long = new short[Opcodes.CHECKCAST];
    final short[] _isRepG0 = new short[12];
    final short[] _isRepG1 = new short[12];
    final short[] _isRepG2 = new short[12];
    final LenPriceTableEncoder _lenEncoder = new LenPriceTableEncoder();
    final LiteralEncoder _literalEncoder = new LiteralEncoder();
    int _longestMatchLength;
    boolean _longestMatchWasFound;
    final int[] _matchDistances = new int[548];
    BinTree _matchFinder = null;
    int _matchFinderType = 1;
    int _matchPriceCount;
    boolean _needReleaseMFStream = false;
    int _numDistancePairs;
    int _numFastBytes = 32;
    int _numFastBytesPrev = -1;
    int _numLiteralContextBits = 3;
    int _numLiteralPosStateBits = 0;
    final Optimal[] _optimum = new Optimal[4096];
    int _optimumCurrentIndex;
    int _optimumEndIndex;
    final BitTreeEncoder _posAlignEncoder = new BitTreeEncoder(4);
    final short[] _posEncoders = new short[114];
    final BitTreeEncoder[] _posSlotEncoder = new BitTreeEncoder[4];
    final int[] _posSlotPrices = new int[256];
    int _posStateBits = 2;
    int _posStateMask = 3;
    byte _previousByte;
    final RangeEncoder _rangeEncoder = new RangeEncoder();
    final int[] _repDistances = new int[4];
    final LenPriceTableEncoder _repMatchLenEncoder = new LenPriceTableEncoder();
    Buffer _src;
    int _state = Base.stateInit();
    boolean _writeEndMark = false;
    int backRes;
    final boolean[] finished = new boolean[1];
    long nowPos64;
    final long[] processedInSize = new long[1];
    final long[] processedOutSize = new long[1];
    final int[] repLens = new int[4];
    final int[] reps = new int[4];
    final int[] tempPrices = new int[128];

    static {
        byte[] bArr = new byte[2048];
        g_FastPos = bArr;
        int c = 2;
        bArr[0] = 0;
        bArr[1] = 1;
        for (int slotFast = 2; slotFast < 22; slotFast++) {
            int k = 1 << ((slotFast >> 1) - 1);
            int j = 0;
            while (j < k) {
                g_FastPos[c] = (byte) slotFast;
                j++;
                c++;
            }
        }
    }

    static int getPosSlot(int pos) {
        if (pos < 2048) {
            return g_FastPos[pos];
        }
        if (pos < 2097152) {
            return g_FastPos[pos >> 10] + 20;
        }
        return g_FastPos[pos >> 20] + 40;
    }

    static int getPosSlot2(int pos) {
        if (pos < 131072) {
            return g_FastPos[pos >> 6] + 12;
        }
        if (pos < 134217728) {
            return g_FastPos[pos >> 16] + 32;
        }
        return g_FastPos[pos >> 26] + 52;
    }

    /* access modifiers changed from: package-private */
    public void baseInit() {
        this._state = Base.stateInit();
        this._previousByte = 0;
        for (int i = 0; i < 4; i++) {
            this._repDistances[i] = 0;
        }
    }

    public static class LiteralEncoder {
        Encoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        LiteralEncoder() {
        }

        public static class Encoder2 {
            final short[] m_Encoders = new short[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART];

            Encoder2() {
            }

            public void init() {
                RangeEncoder.initBitModels(this.m_Encoders);
            }

            public void encode(RangeEncoder rangeEncoder, byte symbol) {
                int context = 1;
                for (int i = 7; i >= 0; i--) {
                    int bit = (symbol >> i) & 1;
                    rangeEncoder.encode(this.m_Encoders, context, bit);
                    context = (context << 1) | bit;
                }
            }

            public void encodeMatched(RangeEncoder rangeEncoder, byte matchByte, byte symbol) {
                int context = 1;
                boolean same = true;
                for (int i = 7; i >= 0; i--) {
                    boolean z = true;
                    int bit = (symbol >> i) & 1;
                    int state = context;
                    if (same) {
                        int matchBit = (matchByte >> i) & 1;
                        state += (matchBit + 1) << 8;
                        if (matchBit != bit) {
                            z = false;
                        }
                        same = z;
                    }
                    rangeEncoder.encode(this.m_Encoders, state, bit);
                    context = (context << 1) | bit;
                }
            }

            public int getPrice(boolean matchMode, byte matchByte, byte symbol) {
                int price = 0;
                int context = 1;
                int i = 7;
                if (matchMode) {
                    while (true) {
                        if (i < 0) {
                            break;
                        }
                        int matchBit = (matchByte >> i) & 1;
                        int bit = (symbol >> i) & 1;
                        price += RangeEncoder.getPrice(this.m_Encoders[((matchBit + 1) << 8) + context], bit);
                        context = (context << 1) | bit;
                        if (matchBit != bit) {
                            i--;
                            break;
                        }
                        i--;
                    }
                }
                while (i >= 0) {
                    int bit2 = (symbol >> i) & 1;
                    price += RangeEncoder.getPrice(this.m_Encoders[context], bit2);
                    context = (context << 1) | bit2;
                    i--;
                }
                return price;
            }
        }

        public void create(int numPosBits, int numPrevBits) {
            if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
                this.m_NumPosBits = numPosBits;
                this.m_PosMask = (1 << numPosBits) - 1;
                this.m_NumPrevBits = numPrevBits;
                int numStates = 1 << (numPrevBits + numPosBits);
                this.m_Coders = new Encoder2[numStates];
                for (int i = 0; i < numStates; i++) {
                    this.m_Coders[i] = new Encoder2();
                }
            }
        }

        public void init() {
            int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i = 0; i < numStates; i++) {
                this.m_Coders[i].init();
            }
        }

        public Encoder2 getSubCoder(int pos, byte prevByte) {
            Encoder2[] encoder2Arr = this.m_Coders;
            int i = this.m_NumPrevBits;
            return encoder2Arr[((this.m_PosMask & pos) << i) + ((prevByte & 255) >>> (8 - i))];
        }
    }

    public static class LenEncoder {
        final short[] _choice = new short[2];
        final BitTreeEncoder _highCoder = new BitTreeEncoder(8);
        final BitTreeEncoder[] _lowCoder = new BitTreeEncoder[16];
        final BitTreeEncoder[] _midCoder = new BitTreeEncoder[16];

        public LenEncoder() {
            for (int posState = 0; posState < 16; posState++) {
                this._lowCoder[posState] = new BitTreeEncoder(3);
                this._midCoder[posState] = new BitTreeEncoder(3);
            }
        }

        public void init(int numPosStates) {
            RangeEncoder.initBitModels(this._choice);
            for (int posState = 0; posState < numPosStates; posState++) {
                this._lowCoder[posState].init();
                this._midCoder[posState].init();
            }
            this._highCoder.init();
        }

        public void encode(RangeEncoder rangeEncoder, int symbol, int posState) {
            if (symbol < 8) {
                rangeEncoder.encode(this._choice, 0, 0);
                this._lowCoder[posState].encode(rangeEncoder, symbol);
                return;
            }
            int symbol2 = symbol - 8;
            rangeEncoder.encode(this._choice, 0, 1);
            if (symbol2 < 8) {
                rangeEncoder.encode(this._choice, 1, 0);
                this._midCoder[posState].encode(rangeEncoder, symbol2);
                return;
            }
            rangeEncoder.encode(this._choice, 1, 1);
            this._highCoder.encode(rangeEncoder, symbol2 - 8);
        }

        public void setPrices(int posState, int numSymbols, int[] prices, int st) {
            int a0 = RangeEncoder.getPrice0(this._choice[0]);
            int a1 = RangeEncoder.getPrice1(this._choice[0]);
            int b0 = RangeEncoder.getPrice0(this._choice[1]) + a1;
            int b1 = RangeEncoder.getPrice1(this._choice[1]) + a1;
            int i = 0;
            while (i < 8) {
                if (i < numSymbols) {
                    prices[st + i] = this._lowCoder[posState].getPrice(i) + a0;
                    i++;
                } else {
                    return;
                }
            }
            while (i < 16) {
                if (i < numSymbols) {
                    prices[st + i] = this._midCoder[posState].getPrice(i - 8) + b0;
                    i++;
                } else {
                    return;
                }
            }
            while (i < numSymbols) {
                prices[st + i] = this._highCoder.getPrice((i - 8) - 8) + b1;
                i++;
            }
        }
    }

    public static class LenPriceTableEncoder extends LenEncoder {
        final int[] _counters = new int[16];
        final int[] _prices = new int[4352];
        int _tableSize;

        LenPriceTableEncoder() {
        }

        public void setTableSize(int tableSize) {
            this._tableSize = tableSize;
        }

        public int getPrice(int symbol, int posState) {
            return this._prices[(posState * Base.kNumLenSymbols) + symbol];
        }

        /* access modifiers changed from: package-private */
        public void updateTable(int posState) {
            setPrices(posState, this._tableSize, this._prices, posState * Base.kNumLenSymbols);
            this._counters[posState] = this._tableSize;
        }

        public void updateTables(int numPosStates) {
            for (int posState = 0; posState < numPosStates; posState++) {
                updateTable(posState);
            }
        }

        public void encode(RangeEncoder rangeEncoder, int symbol, int posState) {
            super.encode(rangeEncoder, symbol, posState);
            int[] iArr = this._counters;
            int i = iArr[posState] - 1;
            iArr[posState] = i;
            if (i == 0) {
                updateTable(posState);
            }
        }
    }

    public static class Optimal {
        public int BackPrev;
        public int BackPrev2;
        public int Backs0;
        public int Backs1;
        public int Backs2;
        public int Backs3;
        public int PosPrev;
        public int PosPrev2;
        public boolean Prev1IsChar;
        public boolean Prev2;
        public int Price;
        public int State;

        Optimal() {
        }

        public void makeAsChar() {
            this.BackPrev = -1;
            this.Prev1IsChar = false;
        }

        public void makeAsShortRep() {
            this.BackPrev = 0;
            this.Prev1IsChar = false;
        }

        public boolean isShortRep() {
            return this.BackPrev == 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void create() {
        if (this._matchFinder == null) {
            BinTree bt = new BinTree();
            int numHashBytes = 4;
            if (this._matchFinderType == 0) {
                numHashBytes = 2;
            }
            bt.setType(numHashBytes);
            this._matchFinder = bt;
        }
        this._literalEncoder.create(this._numLiteralPosStateBits, this._numLiteralContextBits);
        int i = this._dictionarySize;
        if (i != this._dictionarySizePrev || this._numFastBytesPrev != this._numFastBytes) {
            this._matchFinder.create(i, 4096, this._numFastBytes, 274);
            this._dictionarySizePrev = this._dictionarySize;
            this._numFastBytesPrev = this._numFastBytes;
        }
    }

    public Encoder() {
        for (int i = 0; i < 4096; i++) {
            this._optimum[i] = new Optimal();
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this._posSlotEncoder[i2] = new BitTreeEncoder(6);
        }
    }

    /* access modifiers changed from: package-private */
    public void setWriteEndMarkerMode(boolean writeEndMarker) {
        this._writeEndMark = writeEndMarker;
    }

    /* access modifiers changed from: package-private */
    public void init() {
        baseInit();
        this._rangeEncoder.init();
        RangeEncoder.initBitModels(this._isMatch);
        RangeEncoder.initBitModels(this._isRep0Long);
        RangeEncoder.initBitModels(this._isRep);
        RangeEncoder.initBitModels(this._isRepG0);
        RangeEncoder.initBitModels(this._isRepG1);
        RangeEncoder.initBitModels(this._isRepG2);
        RangeEncoder.initBitModels(this._posEncoders);
        this._literalEncoder.init();
        for (int i = 0; i < 4; i++) {
            this._posSlotEncoder[i].init();
        }
        this._lenEncoder.init(1 << this._posStateBits);
        this._repMatchLenEncoder.init(1 << this._posStateBits);
        this._posAlignEncoder.init();
        this._longestMatchWasFound = false;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        this._additionalOffset = 0;
    }

    /* access modifiers changed from: package-private */
    public int readMatchDistances() {
        int[] iArr;
        int lenRes = 0;
        int matches = this._matchFinder.getMatches(this._matchDistances);
        this._numDistancePairs = matches;
        if (matches > 0 && (lenRes = (iArr = this._matchDistances)[matches - 2]) == this._numFastBytes) {
            lenRes += this._matchFinder.getMatchLen(lenRes - 1, iArr[matches - 1], 273 - lenRes);
        }
        this._additionalOffset++;
        return lenRes;
    }

    /* access modifiers changed from: package-private */
    public void movePos(int num) {
        if (num > 0) {
            this._matchFinder.skip(num);
            this._additionalOffset += num;
        }
    }

    /* access modifiers changed from: package-private */
    public int getRepLen1Price(int state, int posState) {
        return RangeEncoder.getPrice0(this._isRepG0[state]) + RangeEncoder.getPrice0(this._isRep0Long[(state << 4) + posState]);
    }

    /* access modifiers changed from: package-private */
    public int getPureRepPrice(int repIndex, int state, int posState) {
        if (repIndex == 0) {
            return RangeEncoder.getPrice0(this._isRepG0[state]) + RangeEncoder.getPrice1(this._isRep0Long[(state << 4) + posState]);
        }
        int price = RangeEncoder.getPrice1(this._isRepG0[state]);
        if (repIndex == 1) {
            return price + RangeEncoder.getPrice0(this._isRepG1[state]);
        }
        return price + RangeEncoder.getPrice1(this._isRepG1[state]) + RangeEncoder.getPrice(this._isRepG2[state], repIndex - 2);
    }

    /* access modifiers changed from: package-private */
    public int getRepPrice(int repIndex, int len, int state, int posState) {
        return getPureRepPrice(repIndex, state, posState) + this._repMatchLenEncoder.getPrice(len - 2, posState);
    }

    /* access modifiers changed from: package-private */
    public int getPosLenPrice(int pos, int len, int posState) {
        int price;
        int lenToPosState = Base.getLenToPosState(len);
        if (pos < 128) {
            price = this._distancesPrices[(lenToPosState * 128) + pos];
        } else {
            price = this._posSlotPrices[(lenToPosState << 6) + getPosSlot2(pos)] + this._alignPrices[pos & 15];
        }
        return this._lenEncoder.getPrice(len - 2, posState) + price;
    }

    /* access modifiers changed from: package-private */
    public int backward(int cur) {
        Optimal[] optimalArr;
        this._optimumEndIndex = cur;
        Optimal[] optimalArr2 = this._optimum;
        int posMem = optimalArr2[cur].PosPrev;
        int backMem = optimalArr2[cur].BackPrev;
        do {
            Optimal[] optimalArr3 = this._optimum;
            if (optimalArr3[cur].Prev1IsChar) {
                optimalArr3[posMem].makeAsChar();
                Optimal[] optimalArr4 = this._optimum;
                optimalArr4[posMem].PosPrev = posMem - 1;
                if (optimalArr4[cur].Prev2) {
                    optimalArr4[posMem - 1].Prev1IsChar = false;
                    optimalArr4[posMem - 1].PosPrev = optimalArr4[cur].PosPrev2;
                    optimalArr4[posMem - 1].BackPrev = optimalArr4[cur].BackPrev2;
                }
            }
            int posPrev = posMem;
            int backCur = backMem;
            optimalArr = this._optimum;
            backMem = optimalArr[posPrev].BackPrev;
            posMem = optimalArr[posPrev].PosPrev;
            optimalArr[posPrev].BackPrev = backCur;
            optimalArr[posPrev].PosPrev = cur;
            cur = posPrev;
        } while (cur > 0);
        this.backRes = optimalArr[0].BackPrev;
        int i = optimalArr[0].PosPrev;
        this._optimumCurrentIndex = i;
        return i;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0424  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0433  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04d9  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0638  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0648  */
    /* JADX WARNING: Removed duplicated region for block: B:210:0x064c  */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x07be  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x079a A[EDGE_INSN: B:277:0x079a->B:242:0x079a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:279:0x07ac A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getOptimum(int r46) {
        /*
            r45 = this;
            r0 = r45
            r1 = r46
            int r2 = r0._optimumEndIndex
            int r3 = r0._optimumCurrentIndex
            if (r2 == r3) goto L_0x001e
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r2 = r0._optimum
            r4 = r2[r3]
            int r4 = r4.PosPrev
            int r4 = r4 - r3
            r5 = r2[r3]
            int r5 = r5.BackPrev
            r0.backRes = r5
            r2 = r2[r3]
            int r2 = r2.PosPrev
            r0._optimumCurrentIndex = r2
            return r4
        L_0x001e:
            r2 = 0
            r0._optimumEndIndex = r2
            r0._optimumCurrentIndex = r2
            boolean r3 = r0._longestMatchWasFound
            if (r3 != 0) goto L_0x002c
            int r3 = r45.readMatchDistances()
            goto L_0x0030
        L_0x002c:
            int r3 = r0._longestMatchLength
            r0._longestMatchWasFound = r2
        L_0x0030:
            int r4 = r0._numDistancePairs
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r5 = r0._matchFinder
            int r5 = r5.getNumAvailableBytes()
            r6 = 1
            int r5 = r5 + r6
            r7 = -1
            r8 = 2
            if (r5 >= r8) goto L_0x0041
            r0.backRes = r7
            return r6
        L_0x0041:
            r9 = 273(0x111, float:3.83E-43)
            if (r5 <= r9) goto L_0x0047
            r5 = 273(0x111, float:3.83E-43)
        L_0x0047:
            r10 = 0
            r11 = 0
        L_0x0049:
            r12 = 4
            if (r11 >= r12) goto L_0x006c
            int[] r12 = r0.reps
            int[] r13 = r0._repDistances
            r13 = r13[r11]
            r12[r11] = r13
            int[] r13 = r0.repLens
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r14 = r0._matchFinder
            r12 = r12[r11]
            int r12 = r14.getMatchLen(r7, r12, r9)
            r13[r11] = r12
            int[] r12 = r0.repLens
            r13 = r12[r11]
            r12 = r12[r10]
            if (r13 <= r12) goto L_0x0069
            r10 = r11
        L_0x0069:
            int r11 = r11 + 1
            goto L_0x0049
        L_0x006c:
            int[] r9 = r0.repLens
            r13 = r9[r10]
            int r14 = r0._numFastBytes
            if (r13 < r14) goto L_0x007e
            r0.backRes = r10
            r2 = r9[r10]
            int r6 = r2 + -1
            r0.movePos(r6)
            return r2
        L_0x007e:
            if (r3 < r14) goto L_0x008f
            int[] r2 = r0._matchDistances
            int r6 = r4 + -1
            r2 = r2[r6]
            int r2 = r2 + r12
            r0.backRes = r2
            int r2 = r3 + -1
            r0.movePos(r2)
            return r3
        L_0x008f:
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r9 = r0._matchFinder
            byte r9 = r9.getIndexByte(r7)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r13 = r0._matchFinder
            int[] r14 = r0._repDistances
            r14 = r14[r2]
            int r14 = 0 - r14
            int r14 = r14 - r6
            int r14 = r14 - r6
            byte r13 = r13.getIndexByte(r14)
            if (r3 >= r8) goto L_0x00b0
            if (r9 == r13) goto L_0x00b0
            int[] r14 = r0.repLens
            r14 = r14[r10]
            if (r14 >= r8) goto L_0x00b0
            r0.backRes = r7
            return r6
        L_0x00b0:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r14 = r0._optimum
            r15 = r14[r2]
            int r7 = r0._state
            r15.State = r7
            int r15 = r0._posStateMask
            r15 = r15 & r1
            r14 = r14[r6]
            short[] r2 = r0._isMatch
            int r7 = r7 << r12
            int r7 = r7 + r15
            short r2 = r2[r7]
            int r2 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r2)
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder r7 = r0._literalEncoder
            byte r8 = r0._previousByte
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder$Encoder2 r7 = r7.getSubCoder(r1, r8)
            int r8 = r0._state
            boolean r8 = org.glassfish.grizzly.compression.lzma.impl.Base.stateIsCharState(r8)
            r8 = r8 ^ r6
            int r7 = r7.getPrice(r8, r13, r9)
            int r2 = r2 + r7
            r14.Price = r2
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r2 = r0._optimum
            r2 = r2[r6]
            r2.makeAsChar()
            short[] r2 = r0._isMatch
            int r7 = r0._state
            int r7 = r7 << r12
            int r7 = r7 + r15
            short r2 = r2[r7]
            int r2 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r2)
            short[] r7 = r0._isRep
            int r8 = r0._state
            short r7 = r7[r8]
            int r7 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r7)
            int r7 = r7 + r2
            if (r13 != r9) goto L_0x0115
            int r8 = r0._state
            int r8 = r0.getRepLen1Price(r8, r15)
            int r8 = r8 + r7
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r14 = r0._optimum
            r12 = r14[r6]
            int r12 = r12.Price
            if (r8 >= r12) goto L_0x0115
            r12 = r14[r6]
            r12.Price = r8
            r12 = r14[r6]
            r12.makeAsShortRep()
        L_0x0115:
            int[] r8 = r0.repLens
            r12 = r8[r10]
            if (r3 < r12) goto L_0x011d
            r8 = r3
            goto L_0x011f
        L_0x011d:
            r8 = r8[r10]
        L_0x011f:
            r12 = 2
            if (r8 >= r12) goto L_0x012b
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r12 = r0._optimum
            r12 = r12[r6]
            int r12 = r12.BackPrev
            r0.backRes = r12
            return r6
        L_0x012b:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r12 = r0._optimum
            r14 = r12[r6]
            r6 = 0
            r14.PosPrev = r6
            r14 = r12[r6]
            int[] r1 = r0.reps
            r19 = r5
            r5 = r1[r6]
            r14.Backs0 = r5
            r5 = r12[r6]
            r14 = 1
            r6 = r1[r14]
            r5.Backs1 = r6
            r5 = 0
            r6 = r12[r5]
            r14 = 2
            r5 = r1[r14]
            r6.Backs2 = r5
            r5 = 0
            r6 = r12[r5]
            r5 = 3
            r1 = r1[r5]
            r6.Backs3 = r1
            r1 = r8
        L_0x0154:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r6 = r0._optimum
            int r12 = r1 + -1
            r1 = r6[r1]
            r6 = 268435455(0xfffffff, float:2.5243547E-29)
            r1.Price = r6
            r1 = 2
            if (r12 >= r1) goto L_0x07df
            r11 = 0
            r14 = r11
        L_0x0164:
            r11 = 4
            if (r14 >= r11) goto L_0x01a5
            int[] r11 = r0.repLens
            r11 = r11[r14]
            if (r11 >= r1) goto L_0x016e
            goto L_0x0196
        L_0x016e:
            int r1 = r0._state
            int r1 = r0.getPureRepPrice(r14, r1, r15)
            int r1 = r1 + r7
        L_0x0175:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LenPriceTableEncoder r6 = r0._repMatchLenEncoder
            int r5 = r11 + -2
            int r5 = r6.getPrice(r5, r15)
            int r5 = r5 + r1
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r6 = r0._optimum
            r6 = r6[r11]
            r22 = r1
            int r1 = r6.Price
            if (r5 >= r1) goto L_0x0191
            r6.Price = r5
            r1 = 0
            r6.PosPrev = r1
            r6.BackPrev = r14
            r6.Prev1IsChar = r1
        L_0x0191:
            int r11 = r11 + -1
            r1 = 2
            if (r11 >= r1) goto L_0x019e
        L_0x0196:
            int r14 = r14 + 1
            r1 = 2
            r5 = 3
            r6 = 268435455(0xfffffff, float:2.5243547E-29)
            goto L_0x0164
        L_0x019e:
            r1 = r22
            r5 = 3
            r6 = 268435455(0xfffffff, float:2.5243547E-29)
            goto L_0x0175
        L_0x01a5:
            short[] r1 = r0._isRep
            int r5 = r0._state
            short r1 = r1[r5]
            int r1 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r1)
            int r1 = r1 + r2
            int[] r5 = r0.repLens
            r6 = 0
            r11 = r5[r6]
            r6 = 2
            if (r11 < r6) goto L_0x01be
            r6 = 0
            r5 = r5[r6]
            r6 = 1
            int r5 = r5 + r6
            goto L_0x01bf
        L_0x01be:
            r5 = 2
        L_0x01bf:
            if (r5 > r3) goto L_0x0200
            r6 = 0
        L_0x01c2:
            int[] r11 = r0._matchDistances
            r11 = r11[r6]
            if (r5 <= r11) goto L_0x01cb
            int r6 = r6 + 2
            goto L_0x01c2
        L_0x01cb:
            int[] r11 = r0._matchDistances
            int r12 = r6 + 1
            r11 = r11[r12]
            int r12 = r0.getPosLenPrice(r11, r5, r15)
            int r12 = r12 + r1
            r22 = r1
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r1 = r0._optimum
            r1 = r1[r5]
            r23 = r2
            int r2 = r1.Price
            if (r12 >= r2) goto L_0x01ee
            r1.Price = r12
            r2 = 0
            r1.PosPrev = r2
            int r2 = r11 + 4
            r1.BackPrev = r2
            r2 = 0
            r1.Prev1IsChar = r2
        L_0x01ee:
            int[] r2 = r0._matchDistances
            r2 = r2[r6]
            if (r5 != r2) goto L_0x01f9
            int r6 = r6 + 2
            if (r6 != r4) goto L_0x01f9
            goto L_0x0204
        L_0x01f9:
            int r5 = r5 + 1
            r1 = r22
            r2 = r23
            goto L_0x01cb
        L_0x0200:
            r22 = r1
            r23 = r2
        L_0x0204:
            r1 = 0
            r2 = r1
            r1 = r46
        L_0x0208:
            r6 = 1
            int r2 = r2 + r6
            if (r2 != r8) goto L_0x0211
            int r6 = r0.backward(r2)
            return r6
        L_0x0211:
            int r6 = r45.readMatchDistances()
            int r4 = r0._numDistancePairs
            int r11 = r0._numFastBytes
            if (r6 < r11) goto L_0x0225
            r0._longestMatchLength = r6
            r11 = 1
            r0._longestMatchWasFound = r11
            int r11 = r0.backward(r2)
            return r11
        L_0x0225:
            int r1 = r1 + 1
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r11 = r0._optimum
            r12 = r11[r2]
            int r12 = r12.PosPrev
            r24 = r3
            r3 = r11[r2]
            boolean r3 = r3.Prev1IsChar
            if (r3 == 0) goto L_0x0263
            int r12 = r12 + -1
            r3 = r11[r2]
            boolean r3 = r3.Prev2
            if (r3 == 0) goto L_0x0258
            r3 = r11[r2]
            int r3 = r3.PosPrev2
            r3 = r11[r3]
            int r3 = r3.State
            r11 = r11[r2]
            int r11 = r11.BackPrev2
            r46 = r4
            r4 = 4
            if (r11 >= r4) goto L_0x0253
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateRep(r3)
            goto L_0x025e
        L_0x0253:
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateMatch(r3)
            goto L_0x025e
        L_0x0258:
            r46 = r4
            r3 = r11[r12]
            int r3 = r3.State
        L_0x025e:
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateChar(r3)
            goto L_0x0269
        L_0x0263:
            r46 = r4
            r3 = r11[r12]
            int r3 = r3.State
        L_0x0269:
            int r4 = r2 + -1
            if (r12 != r4) goto L_0x028b
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r4 = r0._optimum
            r4 = r4[r2]
            boolean r4 = r4.isShortRep()
            if (r4 == 0) goto L_0x0281
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateShortRep(r3)
            r26 = r5
            r27 = r7
            goto L_0x034d
        L_0x0281:
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateChar(r3)
            r26 = r5
            r27 = r7
            goto L_0x034d
        L_0x028b:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r4 = r0._optimum
            r11 = r4[r2]
            boolean r11 = r11.Prev1IsChar
            if (r11 == 0) goto L_0x02a7
            r11 = r4[r2]
            boolean r11 = r11.Prev2
            if (r11 == 0) goto L_0x02a7
            r11 = r4[r2]
            int r12 = r11.PosPrev2
            r4 = r4[r2]
            int r4 = r4.BackPrev2
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateRep(r3)
            r11 = 4
            goto L_0x02b7
        L_0x02a7:
            r4 = r4[r2]
            int r4 = r4.BackPrev
            r11 = 4
            if (r4 >= r11) goto L_0x02b3
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateRep(r3)
            goto L_0x02b7
        L_0x02b3:
            int r3 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateMatch(r3)
        L_0x02b7:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r11 = r0._optimum
            r11 = r11[r12]
            r25 = r3
            r3 = 4
            if (r4 >= r3) goto L_0x0331
            if (r4 != 0) goto L_0x02e0
            int[] r3 = r0.reps
            r26 = r5
            int r5 = r11.Backs0
            r16 = 0
            r3[r16] = r5
            int r5 = r11.Backs1
            r27 = r7
            r7 = 1
            r3[r7] = r5
            int r5 = r11.Backs2
            r17 = 2
            r3[r17] = r5
            int r5 = r11.Backs3
            r18 = 3
            r3[r18] = r5
            goto L_0x034b
        L_0x02e0:
            r26 = r5
            r27 = r7
            r7 = 1
            if (r4 != r7) goto L_0x02ff
            int[] r3 = r0.reps
            int r5 = r11.Backs1
            r16 = 0
            r3[r16] = r5
            int r5 = r11.Backs0
            r3[r7] = r5
            int r5 = r11.Backs2
            r7 = 2
            r3[r7] = r5
            int r5 = r11.Backs3
            r17 = 3
            r3[r17] = r5
            goto L_0x034b
        L_0x02ff:
            r7 = 2
            if (r4 != r7) goto L_0x031a
            int[] r3 = r0.reps
            int r5 = r11.Backs2
            r16 = 0
            r3[r16] = r5
            int r5 = r11.Backs0
            r17 = 1
            r3[r17] = r5
            int r5 = r11.Backs1
            r3[r7] = r5
            int r5 = r11.Backs3
            r7 = 3
            r3[r7] = r5
            goto L_0x034b
        L_0x031a:
            int[] r3 = r0.reps
            int r5 = r11.Backs3
            r7 = 0
            r3[r7] = r5
            int r5 = r11.Backs0
            r7 = 1
            r3[r7] = r5
            int r5 = r11.Backs1
            r7 = 2
            r3[r7] = r5
            int r5 = r11.Backs2
            r7 = 3
            r3[r7] = r5
            goto L_0x034b
        L_0x0331:
            r26 = r5
            r27 = r7
            int[] r3 = r0.reps
            int r5 = r4 + -4
            r7 = 0
            r3[r7] = r5
            int r5 = r11.Backs0
            r7 = 1
            r3[r7] = r5
            int r5 = r11.Backs1
            r7 = 2
            r3[r7] = r5
            int r5 = r11.Backs2
            r7 = 3
            r3[r7] = r5
        L_0x034b:
            r3 = r25
        L_0x034d:
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r4 = r0._optimum
            r5 = r4[r2]
            r5.State = r3
            r5 = r4[r2]
            int[] r7 = r0.reps
            r25 = r8
            r11 = 0
            r8 = r7[r11]
            r5.Backs0 = r8
            r5 = r4[r2]
            r8 = 1
            r11 = r7[r8]
            r5.Backs1 = r11
            r5 = r4[r2]
            r8 = 2
            r11 = r7[r8]
            r5.Backs2 = r11
            r5 = r4[r2]
            r21 = 3
            r7 = r7[r21]
            r5.Backs3 = r7
            r4 = r4[r2]
            int r4 = r4.Price
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r5 = r0._matchFinder
            r7 = -1
            byte r9 = r5.getIndexByte(r7)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r5 = r0._matchFinder
            int[] r7 = r0.reps
            r8 = 0
            r7 = r7[r8]
            int r7 = 0 - r7
            r8 = 1
            int r7 = r7 - r8
            int r7 = r7 - r8
            byte r13 = r5.getIndexByte(r7)
            int r5 = r0._posStateMask
            r15 = r1 & r5
            short[] r5 = r0._isMatch
            int r7 = r3 << 4
            int r7 = r7 + r15
            short r5 = r5[r7]
            int r5 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r5)
            int r5 = r5 + r4
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder r7 = r0._literalEncoder
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r8 = r0._matchFinder
            r11 = -2
            byte r8 = r8.getIndexByte(r11)
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder$Encoder2 r7 = r7.getSubCoder(r1, r8)
            boolean r8 = org.glassfish.grizzly.compression.lzma.impl.Base.stateIsCharState(r3)
            r11 = 1
            r8 = r8 ^ r11
            int r7 = r7.getPrice(r8, r13, r9)
            int r5 = r5 + r7
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r7 = r0._optimum
            int r8 = r2 + 1
            r7 = r7[r8]
            r8 = 0
            int r11 = r7.Price
            if (r5 >= r11) goto L_0x03ca
            r7.Price = r5
            r7.PosPrev = r2
            r7.makeAsChar()
            r8 = 1
        L_0x03ca:
            short[] r11 = r0._isMatch
            int r28 = r3 << 4
            int r28 = r28 + r15
            short r11 = r11[r28]
            int r11 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r11)
            int r23 = r4 + r11
            short[] r11 = r0._isRep
            short r11 = r11[r3]
            int r11 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r11)
            int r11 = r23 + r11
            if (r13 != r9) goto L_0x0406
            r27 = r4
            int r4 = r7.PosPrev
            if (r4 >= r2) goto L_0x03f2
            int r4 = r7.BackPrev
            if (r4 == 0) goto L_0x03ef
            goto L_0x03f2
        L_0x03ef:
            r28 = r8
            goto L_0x040a
        L_0x03f2:
            int r4 = r0.getRepLen1Price(r3, r15)
            int r4 = r4 + r11
            r28 = r8
            int r8 = r7.Price
            if (r4 > r8) goto L_0x040a
            r7.Price = r4
            r7.PosPrev = r2
            r7.makeAsShortRep()
            r8 = 1
            goto L_0x040c
        L_0x0406:
            r27 = r4
            r28 = r8
        L_0x040a:
            r8 = r28
        L_0x040c:
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r4 = r0._matchFinder
            int r4 = r4.getNumAvailableBytes()
            r18 = 1
            int r4 = r4 + 1
            r28 = r7
            int r7 = 4095 - r2
            int r4 = java.lang.Math.min(r7, r4)
            r7 = r4
            r29 = r10
            r10 = 2
            if (r7 >= r10) goto L_0x0433
            r4 = r46
            r19 = r7
            r7 = r11
            r3 = r24
            r8 = r25
            r5 = r26
            r10 = r29
            goto L_0x0208
        L_0x0433:
            int r10 = r0._numFastBytes
            if (r7 <= r10) goto L_0x0439
            int r7 = r0._numFastBytes
        L_0x0439:
            if (r8 != 0) goto L_0x04c6
            if (r13 == r9) goto L_0x04c6
            r19 = r8
            int r8 = r4 + -1
            int r8 = java.lang.Math.min(r8, r10)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r10 = r0._matchFinder
            r30 = r9
            int[] r9 = r0.reps
            r31 = r12
            r12 = 0
            r9 = r9[r12]
            int r9 = r10.getMatchLen(r12, r9, r8)
            r10 = 2
            if (r9 < r10) goto L_0x04bd
            int r10 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateChar(r3)
            int r12 = r1 + 1
            r32 = r8
            int r8 = r0._posStateMask
            r8 = r8 & r12
            short[] r12 = r0._isMatch
            int r33 = r10 << 4
            int r33 = r33 + r8
            short r12 = r12[r33]
            int r12 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r12)
            int r12 = r12 + r5
            r33 = r5
            short[] r5 = r0._isRep
            short r5 = r5[r10]
            int r5 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r5)
            int r12 = r12 + r5
            int r5 = r2 + 1
            int r5 = r5 + r9
            r34 = r13
            r13 = r25
        L_0x0481:
            if (r13 >= r5) goto L_0x0497
            r35 = r14
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r14 = r0._optimum
            int r13 = r13 + 1
            r14 = r14[r13]
            r25 = r13
            r13 = 268435455(0xfffffff, float:2.5243547E-29)
            r14.Price = r13
            r13 = r25
            r14 = r35
            goto L_0x0481
        L_0x0497:
            r35 = r14
            r14 = 0
            int r25 = r0.getRepPrice(r14, r9, r10, r8)
            int r14 = r12 + r25
            r36 = r8
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r8 = r0._optimum
            r8 = r8[r5]
            r37 = r5
            int r5 = r8.Price
            if (r14 >= r5) goto L_0x04bb
            r8.Price = r14
            int r5 = r2 + 1
            r8.PosPrev = r5
            r5 = 0
            r8.BackPrev = r5
            r5 = 1
            r8.Prev1IsChar = r5
            r5 = 0
            r8.Prev2 = r5
        L_0x04bb:
            r8 = r13
            goto L_0x04d4
        L_0x04bd:
            r33 = r5
            r32 = r8
            r34 = r13
            r35 = r14
            goto L_0x04d2
        L_0x04c6:
            r33 = r5
            r19 = r8
            r30 = r9
            r31 = r12
            r34 = r13
            r35 = r14
        L_0x04d2:
            r8 = r25
        L_0x04d4:
            r5 = 2
            r9 = 0
        L_0x04d6:
            r10 = 4
            if (r9 >= r10) goto L_0x0632
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r12 = r0._matchFinder
            int[] r13 = r0.reps
            r13 = r13[r9]
            r14 = -1
            int r12 = r12.getMatchLen(r14, r13, r7)
            r13 = 2
            if (r12 >= r13) goto L_0x04ed
            r42 = r4
            r39 = r11
            goto L_0x061e
        L_0x04ed:
            r13 = r12
        L_0x04ee:
            int r10 = r2 + r12
            if (r8 >= r10) goto L_0x0500
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r10 = r0._optimum
            int r8 = r8 + 1
            r10 = r10[r8]
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r10.Price = r14
            r10 = 4
            r14 = -1
            goto L_0x04ee
        L_0x0500:
            int r10 = r0.getRepPrice(r9, r12, r3, r15)
            int r10 = r10 + r11
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r14 = r0._optimum
            int r25 = r2 + r12
            r14 = r14[r25]
            r25 = r8
            int r8 = r14.Price
            if (r10 >= r8) goto L_0x051a
            r14.Price = r10
            r14.PosPrev = r2
            r14.BackPrev = r9
            r8 = 0
            r14.Prev1IsChar = r8
        L_0x051a:
            int r12 = r12 + -1
            r8 = 2
            if (r12 >= r8) goto L_0x0626
            r8 = r13
            if (r9 != 0) goto L_0x0524
            int r5 = r8 + 1
        L_0x0524:
            if (r8 >= r4) goto L_0x0610
            int r10 = r4 + -1
            int r10 = r10 - r8
            int r12 = r0._numFastBytes
            int r10 = java.lang.Math.min(r10, r12)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r12 = r0._matchFinder
            int[] r14 = r0.reps
            r14 = r14[r9]
            int r12 = r12.getMatchLen(r8, r14, r10)
            r14 = 2
            if (r12 < r14) goto L_0x0603
            int r14 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateRep(r3)
            int r32 = r1 + r8
            r36 = r5
            int r5 = r0._posStateMask
            r5 = r32 & r5
            int r32 = r0.getRepPrice(r9, r8, r3, r15)
            int r32 = r11 + r32
            r37 = r10
            short[] r10 = r0._isMatch
            int r38 = r14 << 4
            int r38 = r38 + r5
            short r10 = r10[r38]
            int r10 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r10)
            int r32 = r32 + r10
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder r10 = r0._literalEncoder
            r38 = r5
            int r5 = r1 + r8
            r39 = r11
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r11 = r0._matchFinder
            int r40 = r8 + -1
            r42 = r4
            r41 = r13
            r13 = 1
            int r4 = r40 + -1
            byte r4 = r11.getIndexByte(r4)
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder$Encoder2 r4 = r10.getSubCoder(r5, r4)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r5 = r0._matchFinder
            int r10 = r8 + -1
            int[] r11 = r0.reps
            r11 = r11[r9]
            int r11 = r11 + r13
            int r10 = r10 - r11
            byte r5 = r5.getIndexByte(r10)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r10 = r0._matchFinder
            int r11 = r8 + -1
            byte r10 = r10.getIndexByte(r11)
            int r4 = r4.getPrice(r13, r5, r10)
            int r32 = r32 + r4
            int r4 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateChar(r14)
            int r5 = r1 + r8
            int r5 = r5 + r13
            int r10 = r0._posStateMask
            r5 = r5 & r10
            short[] r10 = r0._isMatch
            int r11 = r4 << 4
            int r11 = r11 + r5
            short r10 = r10[r11]
            int r10 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r10)
            int r10 = r32 + r10
            short[] r11 = r0._isRep
            short r11 = r11[r4]
            int r11 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r11)
            int r11 = r11 + r10
            int r13 = r8 + 1
            int r13 = r13 + r12
            r14 = r25
        L_0x05ba:
            r38 = r10
            int r10 = r2 + r13
            if (r14 >= r10) goto L_0x05d2
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r10 = r0._optimum
            int r14 = r14 + 1
            r10 = r10[r14]
            r25 = r14
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r10.Price = r14
            r14 = r25
            r10 = r38
            goto L_0x05ba
        L_0x05d2:
            r10 = 0
            int r25 = r0.getRepPrice(r10, r12, r4, r5)
            int r10 = r11 + r25
            r40 = r4
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r4 = r0._optimum
            int r25 = r2 + r13
            r4 = r4[r25]
            r43 = r5
            int r5 = r4.Price
            if (r10 >= r5) goto L_0x05fd
            r4.Price = r10
            int r5 = r2 + r8
            r44 = r8
            r8 = 1
            int r5 = r5 + r8
            r4.PosPrev = r5
            r5 = 0
            r4.BackPrev = r5
            r4.Prev1IsChar = r8
            r4.Prev2 = r8
            r4.PosPrev2 = r2
            r4.BackPrev2 = r9
            goto L_0x05ff
        L_0x05fd:
            r44 = r8
        L_0x05ff:
            r8 = r14
            r5 = r36
            goto L_0x061e
        L_0x0603:
            r42 = r4
            r36 = r5
            r44 = r8
            r37 = r10
            r39 = r11
            r41 = r13
            goto L_0x061a
        L_0x0610:
            r42 = r4
            r36 = r5
            r44 = r8
            r39 = r11
            r41 = r13
        L_0x061a:
            r8 = r25
            r5 = r36
        L_0x061e:
            int r9 = r9 + 1
            r11 = r39
            r4 = r42
            goto L_0x04d6
        L_0x0626:
            r42 = r4
            r39 = r11
            r41 = r13
            r8 = r25
            r10 = 4
            r14 = -1
            goto L_0x04ee
        L_0x0632:
            r42 = r4
            r39 = r11
            if (r6 <= r7) goto L_0x0648
            r6 = r7
            r4 = 0
        L_0x063a:
            int[] r9 = r0._matchDistances
            r10 = r9[r4]
            if (r6 <= r10) goto L_0x0643
            int r4 = r4 + 2
            goto L_0x063a
        L_0x0643:
            r9[r4] = r6
            int r4 = r4 + 2
            goto L_0x064a
        L_0x0648:
            r4 = r46
        L_0x064a:
            if (r6 < r5) goto L_0x07be
            short[] r9 = r0._isRep
            short r9 = r9[r3]
            int r9 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r9)
            int r9 = r23 + r9
        L_0x0656:
            int r10 = r2 + r6
            if (r8 >= r10) goto L_0x0666
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r10 = r0._optimum
            int r8 = r8 + 1
            r10 = r10[r8]
            r11 = 268435455(0xfffffff, float:2.5243547E-29)
            r10.Price = r11
            goto L_0x0656
        L_0x0666:
            r10 = 0
        L_0x0667:
            int[] r11 = r0._matchDistances
            r11 = r11[r10]
            if (r5 <= r11) goto L_0x0670
            int r10 = r10 + 2
            goto L_0x0667
        L_0x0670:
            r11 = r5
        L_0x0671:
            int[] r12 = r0._matchDistances
            int r13 = r10 + 1
            r12 = r12[r13]
            int r13 = r0.getPosLenPrice(r12, r11, r15)
            int r13 = r13 + r9
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r14 = r0._optimum
            int r22 = r2 + r11
            r14 = r14[r22]
            r25 = r5
            int r5 = r14.Price
            if (r13 >= r5) goto L_0x0693
            r14.Price = r13
            r14.PosPrev = r2
            int r5 = r12 + 4
            r14.BackPrev = r5
            r5 = 0
            r14.Prev1IsChar = r5
        L_0x0693:
            int[] r5 = r0._matchDistances
            r5 = r5[r10]
            if (r11 != r5) goto L_0x079d
            r5 = r42
            if (r11 >= r5) goto L_0x0781
            int r22 = r5 + -1
            r42 = r5
            int r5 = r22 - r11
            r32 = r6
            int r6 = r0._numFastBytes
            int r5 = java.lang.Math.min(r5, r6)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r6 = r0._matchFinder
            int r6 = r6.getMatchLen(r11, r12, r5)
            r46 = r5
            r5 = 2
            if (r6 < r5) goto L_0x0773
            int r17 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateMatch(r3)
            int r22 = r1 + r11
            int r5 = r0._posStateMask
            r5 = r22 & r5
            r37 = r3
            short[] r3 = r0._isMatch
            int r22 = r17 << 4
            int r22 = r22 + r5
            short r3 = r3[r22]
            int r3 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice0(r3)
            int r3 = r3 + r13
            r22 = r5
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder r5 = r0._literalEncoder
            r38 = r7
            int r7 = r1 + r11
            r40 = r8
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r8 = r0._matchFinder
            int r41 = r11 + -1
            r43 = r9
            r44 = r13
            r9 = 1
            int r13 = r41 + -1
            byte r8 = r8.getIndexByte(r13)
            org.glassfish.grizzly.compression.lzma.impl.Encoder$LiteralEncoder$Encoder2 r5 = r5.getSubCoder(r7, r8)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r7 = r0._matchFinder
            int r8 = r12 + 1
            int r8 = r11 - r8
            int r8 = r8 - r9
            byte r7 = r7.getIndexByte(r8)
            org.glassfish.grizzly.compression.lzma.impl.lz.BinTree r8 = r0._matchFinder
            int r13 = r11 + -1
            byte r8 = r8.getIndexByte(r13)
            int r5 = r5.getPrice(r9, r7, r8)
            int r3 = r3 + r5
            int r5 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateChar(r17)
            int r7 = r1 + r11
            int r7 = r7 + r9
            int r8 = r0._posStateMask
            r7 = r7 & r8
            short[] r8 = r0._isMatch
            int r9 = r5 << 4
            int r9 = r9 + r7
            short r8 = r8[r9]
            int r8 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r8)
            int r8 = r8 + r3
            short[] r9 = r0._isRep
            short r9 = r9[r5]
            int r9 = org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder.getPrice1(r9)
            int r9 = r9 + r8
            int r13 = r11 + 1
            int r13 = r13 + r6
            r17 = r1
            r1 = r40
        L_0x072a:
            r22 = r3
            int r3 = r2 + r13
            if (r1 >= r3) goto L_0x0742
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r3 = r0._optimum
            int r1 = r1 + 1
            r3 = r3[r1]
            r40 = r1
            r1 = 268435455(0xfffffff, float:2.5243547E-29)
            r3.Price = r1
            r3 = r22
            r1 = r40
            goto L_0x072a
        L_0x0742:
            r40 = r1
            r1 = 268435455(0xfffffff, float:2.5243547E-29)
            r3 = 0
            int r20 = r0.getRepPrice(r3, r6, r5, r7)
            int r3 = r9 + r20
            org.glassfish.grizzly.compression.lzma.impl.Encoder$Optimal[] r1 = r0._optimum
            int r41 = r2 + r13
            r14 = r1[r41]
            int r1 = r14.Price
            if (r3 >= r1) goto L_0x076e
            r14.Price = r3
            int r1 = r2 + r11
            r0 = 1
            int r1 = r1 + r0
            r14.PosPrev = r1
            r1 = 0
            r14.BackPrev = r1
            r14.Prev1IsChar = r0
            r14.Prev2 = r0
            r14.PosPrev2 = r2
            int r0 = r12 + 4
            r14.BackPrev2 = r0
            goto L_0x076f
        L_0x076e:
            r1 = 0
        L_0x076f:
            r13 = r3
            r8 = r40
            goto L_0x0796
        L_0x0773:
            r17 = r1
            r37 = r3
            r38 = r7
            r40 = r8
            r43 = r9
            r44 = r13
            r1 = 0
            goto L_0x0792
        L_0x0781:
            r17 = r1
            r37 = r3
            r42 = r5
            r32 = r6
            r38 = r7
            r40 = r8
            r43 = r9
            r44 = r13
            r1 = 0
        L_0x0792:
            r8 = r40
            r13 = r44
        L_0x0796:
            int r10 = r10 + 2
            if (r10 != r4) goto L_0x07ac
            r22 = r43
            goto L_0x07c9
        L_0x079d:
            r17 = r1
            r37 = r3
            r32 = r6
            r38 = r7
            r40 = r8
            r43 = r9
            r44 = r13
            r1 = 0
        L_0x07ac:
            int r11 = r11 + 1
            r0 = r45
            r1 = r17
            r5 = r25
            r6 = r32
            r3 = r37
            r7 = r38
            r9 = r43
            goto L_0x0671
        L_0x07be:
            r17 = r1
            r37 = r3
            r25 = r5
            r32 = r6
            r38 = r7
            r1 = 0
        L_0x07c9:
            r0 = r45
            r1 = r17
            r3 = r24
            r5 = r26
            r10 = r29
            r9 = r30
            r13 = r34
            r14 = r35
            r19 = r38
            r7 = r39
            goto L_0x0208
        L_0x07df:
            r23 = r2
            r24 = r3
            r21 = r5
            r29 = r10
            r1 = 0
            r0 = r45
            r1 = r12
            goto L_0x0154
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.Encoder.getOptimum(int):int");
    }

    /* access modifiers changed from: package-private */
    public boolean changePair(int smallDist, int bigDist) {
        return smallDist < (1 << (32 - 7)) && bigDist >= (smallDist << 7);
    }

    /* access modifiers changed from: package-private */
    public void writeEndMarker(int posState) {
        if (this._writeEndMark) {
            this._rangeEncoder.encode(this._isMatch, (this._state << 4) + posState, 1);
            this._rangeEncoder.encode(this._isRep, this._state, 0);
            this._state = Base.stateUpdateMatch(this._state);
            this._lenEncoder.encode(this._rangeEncoder, 2 - 2, posState);
            this._posSlotEncoder[Base.getLenToPosState(2)].encode(this._rangeEncoder, 63);
            int posReduced = (1 << 30) - 1;
            this._rangeEncoder.encodeDirectBits(posReduced >> 4, 30 - 4);
            this._posAlignEncoder.reverseEncode(this._rangeEncoder, posReduced & 15);
        }
    }

    /* access modifiers changed from: package-private */
    public void flush(int nowPos) {
        releaseMFBuffer();
        writeEndMarker(this._posStateMask & nowPos);
        this._rangeEncoder.flushData();
    }

    public void codeOneBlock(long[] inSize, long[] outSize, boolean[] finished2) {
        int i = 0;
        inSize[0] = 0;
        outSize[0] = 0;
        finished2[0] = true;
        Buffer buffer = this._src;
        if (buffer != null) {
            this._matchFinder.setBuffer(buffer);
            this._matchFinder.init();
            this._needReleaseMFStream = true;
            this._src = null;
        }
        if (!this._finished) {
            this._finished = true;
            long progressPosValuePrev = this.nowPos64;
            int i2 = 4;
            if (this.nowPos64 == 0) {
                if (this._matchFinder.getNumAvailableBytes() == 0) {
                    flush((int) this.nowPos64);
                    return;
                }
                readMatchDistances();
                this._rangeEncoder.encode(this._isMatch, (this._state << 4) + (((int) this.nowPos64) & this._posStateMask), 0);
                this._state = Base.stateUpdateChar(this._state);
                byte curByte = this._matchFinder.getIndexByte(0 - this._additionalOffset);
                this._literalEncoder.getSubCoder((int) this.nowPos64, this._previousByte).encode(this._rangeEncoder, curByte);
                this._previousByte = curByte;
                this._additionalOffset--;
                this.nowPos64++;
            }
            if (this._matchFinder.getNumAvailableBytes() == 0) {
                flush((int) this.nowPos64);
                return;
            }
            while (true) {
                int len = getOptimum((int) this.nowPos64);
                int pos = this.backRes;
                int posState = ((int) this.nowPos64) & this._posStateMask;
                int complexState = (this._state << i2) + posState;
                if (len == 1 && pos == -1) {
                    this._rangeEncoder.encode(this._isMatch, complexState, i);
                    byte curByte2 = this._matchFinder.getIndexByte(0 - this._additionalOffset);
                    LiteralEncoder.Encoder2 subCoder = this._literalEncoder.getSubCoder((int) this.nowPos64, this._previousByte);
                    if (!Base.stateIsCharState(this._state)) {
                        subCoder.encodeMatched(this._rangeEncoder, this._matchFinder.getIndexByte(((0 - this._repDistances[i]) - 1) - this._additionalOffset), curByte2);
                    } else {
                        subCoder.encode(this._rangeEncoder, curByte2);
                    }
                    this._previousByte = curByte2;
                    this._state = Base.stateUpdateChar(this._state);
                } else {
                    this._rangeEncoder.encode(this._isMatch, complexState, 1);
                    if (pos < i2) {
                        this._rangeEncoder.encode(this._isRep, this._state, 1);
                        if (pos == 0) {
                            this._rangeEncoder.encode(this._isRepG0, this._state, i);
                            if (len == 1) {
                                this._rangeEncoder.encode(this._isRep0Long, complexState, i);
                            } else {
                                this._rangeEncoder.encode(this._isRep0Long, complexState, 1);
                            }
                        } else {
                            this._rangeEncoder.encode(this._isRepG0, this._state, 1);
                            if (pos == 1) {
                                this._rangeEncoder.encode(this._isRepG1, this._state, i);
                            } else {
                                this._rangeEncoder.encode(this._isRepG1, this._state, 1);
                                this._rangeEncoder.encode(this._isRepG2, this._state, pos - 2);
                            }
                        }
                        if (len == 1) {
                            this._state = Base.stateUpdateShortRep(this._state);
                        } else {
                            this._repMatchLenEncoder.encode(this._rangeEncoder, len - 2, posState);
                            this._state = Base.stateUpdateRep(this._state);
                        }
                        int[] iArr = this._repDistances;
                        int distance = iArr[pos];
                        if (pos != 0) {
                            System.arraycopy(iArr, i, iArr, 1, pos);
                            this._repDistances[i] = distance;
                        }
                    } else {
                        this._rangeEncoder.encode(this._isRep, this._state, i);
                        this._state = Base.stateUpdateMatch(this._state);
                        this._lenEncoder.encode(this._rangeEncoder, len - 2, posState);
                        int pos2 = pos - 4;
                        int posSlot = getPosSlot(pos2);
                        this._posSlotEncoder[Base.getLenToPosState(len)].encode(this._rangeEncoder, posSlot);
                        if (posSlot >= i2) {
                            int footerBits = (posSlot >> 1) - 1;
                            int baseVal = ((posSlot & 1) | 2) << footerBits;
                            int posReduced = pos2 - baseVal;
                            if (posSlot < 14) {
                                BitTreeEncoder.reverseEncode(this._posEncoders, (baseVal - posSlot) - 1, this._rangeEncoder, footerBits, posReduced);
                            } else {
                                this._rangeEncoder.encodeDirectBits(posReduced >> 4, footerBits - 4);
                                this._posAlignEncoder.reverseEncode(this._rangeEncoder, posReduced & 15);
                                this._alignPriceCount++;
                            }
                        }
                        int[] iArr2 = this._repDistances;
                        System.arraycopy(iArr2, 0, iArr2, 1, 3);
                        this._repDistances[0] = pos2;
                        this._matchPriceCount++;
                    }
                    this._previousByte = this._matchFinder.getIndexByte((len - 1) - this._additionalOffset);
                }
                int i3 = this._additionalOffset - len;
                this._additionalOffset = i3;
                this.nowPos64 += (long) len;
                if (i3 == 0) {
                    if (this._matchPriceCount >= 128) {
                        fillDistancesPrices();
                    }
                    if (this._alignPriceCount >= 16) {
                        fillAlignPrices();
                    }
                    inSize[0] = this.nowPos64;
                    outSize[0] = this._rangeEncoder.getProcessedSizeAdd();
                    if (this._matchFinder.getNumAvailableBytes() == 0) {
                        flush((int) this.nowPos64);
                        return;
                    } else if (this.nowPos64 - progressPosValuePrev >= 4096) {
                        this._finished = false;
                        finished2[0] = false;
                        return;
                    }
                }
                i = 0;
                i2 = 4;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void releaseMFBuffer() {
        BinTree binTree = this._matchFinder;
        if (binTree != null && this._needReleaseMFStream) {
            binTree.releaseBuffer();
            this._needReleaseMFStream = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void setDstBuffer(Buffer dst, MemoryManager mm) {
        this._rangeEncoder.setBuffer(dst, mm);
    }

    /* access modifiers changed from: package-private */
    public Buffer releaseDstBuffer() {
        return this._rangeEncoder.releaseBuffer();
    }

    /* access modifiers changed from: package-private */
    public void releaseBuffers(LZMAEncoder.LZMAOutputState state) {
        releaseMFBuffer();
        state.setDst(releaseDstBuffer());
    }

    /* access modifiers changed from: package-private */
    public void setStreams(Buffer src, Buffer dst, MemoryManager mm, long inSize, long outSize) {
        this._src = src;
        this._finished = false;
        create();
        setDstBuffer(dst, mm);
        init();
        fillDistancesPrices();
        fillAlignPrices();
        this._lenEncoder.setTableSize((this._numFastBytes + 1) - 2);
        this._lenEncoder.updateTables(1 << this._posStateBits);
        this._repMatchLenEncoder.setTableSize((this._numFastBytes + 1) - 2);
        this._repMatchLenEncoder.updateTables(1 << this._posStateBits);
        this.nowPos64 = 0;
    }

    public void code(LZMAEncoder.LZMAOutputState state, long inSize, long outSize) {
        this._needReleaseMFStream = false;
        try {
            setStreams(state.getSrc(), state.getDst(), state.getMemoryManager(), inSize, outSize);
            do {
                codeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
            } while (!this.finished[0]);
        } finally {
            releaseBuffers(state);
        }
    }

    public void writeCoderProperties(Buffer dst) {
        dst.put((byte) ((((this._posStateBits * 5) + this._numLiteralPosStateBits) * 9) + this._numLiteralContextBits));
        for (int i = 0; i < 4; i++) {
            dst.put((byte) (this._dictionarySize >> (i * 8)));
        }
    }

    /* access modifiers changed from: package-private */
    public void fillDistancesPrices() {
        for (int i = 4; i < 128; i++) {
            int posSlot = getPosSlot(i);
            int footerBits = (posSlot >> 1) - 1;
            int baseVal = ((posSlot & 1) | 2) << footerBits;
            this.tempPrices[i] = BitTreeEncoder.reverseGetPrice(this._posEncoders, (baseVal - posSlot) - 1, footerBits, i - baseVal);
        }
        for (int lenToPosState = 0; lenToPosState < 4; lenToPosState++) {
            BitTreeEncoder encoder = this._posSlotEncoder[lenToPosState];
            int st = lenToPosState << 6;
            for (int posSlot2 = 0; posSlot2 < this._distTableSize; posSlot2++) {
                this._posSlotPrices[st + posSlot2] = encoder.getPrice(posSlot2);
            }
            for (int posSlot3 = 14; posSlot3 < this._distTableSize; posSlot3++) {
                int[] iArr = this._posSlotPrices;
                int i2 = st + posSlot3;
                iArr[i2] = iArr[i2] + ((((posSlot3 >> 1) - 1) - 4) << 6);
            }
            int st2 = lenToPosState * 128;
            int i3 = 0;
            while (i3 < 4) {
                this._distancesPrices[st2 + i3] = this._posSlotPrices[st + i3];
                i3++;
            }
            while (i3 < 128) {
                this._distancesPrices[st2 + i3] = this._posSlotPrices[getPosSlot(i3) + st] + this.tempPrices[i3];
                i3++;
            }
        }
        this._matchPriceCount = 0;
    }

    /* access modifiers changed from: package-private */
    public void fillAlignPrices() {
        for (int i = 0; i < 16; i++) {
            this._alignPrices[i] = this._posAlignEncoder.reverseGetPrice(i);
        }
        this._alignPriceCount = 0;
    }

    public boolean setAlgorithm(int algorithm) {
        return true;
    }

    public boolean setDictionarySize(int dictionarySize) {
        if (dictionarySize < 1 || dictionarySize > (1 << 29)) {
            return false;
        }
        this._dictionarySize = dictionarySize;
        int dicLogSize = 0;
        while (dictionarySize > (1 << dicLogSize)) {
            dicLogSize++;
        }
        this._distTableSize = dicLogSize * 2;
        return true;
    }

    public boolean setNumFastBytes(int numFastBytes) {
        if (numFastBytes < 5 || numFastBytes > 273) {
            return false;
        }
        this._numFastBytes = numFastBytes;
        return true;
    }

    public boolean setMatchFinder(int matchFinderIndex) {
        if (matchFinderIndex < 0 || matchFinderIndex > 2) {
            return false;
        }
        int matchFinderIndexPrev = this._matchFinderType;
        this._matchFinderType = matchFinderIndex;
        if (this._matchFinder == null || matchFinderIndexPrev == matchFinderIndex) {
            return true;
        }
        this._dictionarySizePrev = -1;
        this._matchFinder = null;
        return true;
    }

    public boolean setLcLpPb(int lc, int lp, int pb) {
        if (lp < 0 || lp > 4 || lc < 0 || lc > 8 || pb < 0 || pb > 4) {
            return false;
        }
        this._numLiteralPosStateBits = lp;
        this._numLiteralContextBits = lc;
        this._posStateBits = pb;
        this._posStateMask = (1 << pb) - 1;
        return true;
    }

    public void setEndMarkerMode(boolean endMarkerMode) {
        this._writeEndMark = endMarkerMode;
    }
}
