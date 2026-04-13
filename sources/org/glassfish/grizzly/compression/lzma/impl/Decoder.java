package org.glassfish.grizzly.compression.lzma.impl;

import com.alibaba.fastjson.asm.Opcodes;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.compression.lzma.impl.lz.OutWindow;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeDecoder;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder;

public class Decoder {
    int m_DictionarySize = -1;
    int m_DictionarySizeCheck = -1;
    final short[] m_IsMatchDecoders = new short[Opcodes.CHECKCAST];
    final short[] m_IsRep0LongDecoders = new short[Opcodes.CHECKCAST];
    final short[] m_IsRepDecoders = new short[12];
    final short[] m_IsRepG0Decoders = new short[12];
    final short[] m_IsRepG1Decoders = new short[12];
    final short[] m_IsRepG2Decoders = new short[12];
    final LenDecoder m_LenDecoder = new LenDecoder();
    final LiteralDecoder m_LiteralDecoder = new LiteralDecoder();
    final OutWindow m_OutWindow = new OutWindow();
    final BitTreeDecoder m_PosAlignDecoder = new BitTreeDecoder(4);
    final short[] m_PosDecoders = new short[114];
    final BitTreeDecoder[] m_PosSlotDecoder = new BitTreeDecoder[4];
    int m_PosStateMask;
    final RangeDecoder m_RangeDecoder = new RangeDecoder();
    final LenDecoder m_RepLenDecoder = new LenDecoder();

    public enum State {
        ERR,
        NEED_MORE_DATA,
        DONE,
        CONTINUE
    }

    public static class LenDecoder {
        private int decodeMethodState;
        final short[] m_Choice = new short[2];
        final BitTreeDecoder m_HighCoder = new BitTreeDecoder(8);
        final BitTreeDecoder[] m_LowCoder = new BitTreeDecoder[16];
        final BitTreeDecoder[] m_MidCoder = new BitTreeDecoder[16];
        int m_NumPosStates = 0;

        LenDecoder() {
        }

        public void create(int numPosStates) {
            while (true) {
                int i = this.m_NumPosStates;
                if (i < numPosStates) {
                    this.m_LowCoder[i] = new BitTreeDecoder(3);
                    this.m_MidCoder[this.m_NumPosStates] = new BitTreeDecoder(3);
                    this.m_NumPosStates++;
                } else {
                    return;
                }
            }
        }

        public void init() {
            this.decodeMethodState = 0;
            RangeDecoder.initBitModels(this.m_Choice);
            for (int posState = 0; posState < this.m_NumPosStates; posState++) {
                this.m_LowCoder[posState].init();
                this.m_MidCoder[posState].init();
            }
            this.m_HighCoder.init();
        }

        public boolean decode(LZMADecoder.LZMAInputState decoderState, RangeDecoder rangeDecoder, int posState) {
            while (true) {
                int i = 1;
                switch (this.decodeMethodState) {
                    case 0:
                        if (rangeDecoder.decodeBit(decoderState, this.m_Choice, 0)) {
                            if (decoderState.lastMethodResult != 0) {
                                i = 2;
                            }
                            this.decodeMethodState = i;
                            break;
                        } else {
                            return false;
                        }
                    case 1:
                        if (this.m_LowCoder[posState].decode(decoderState, rangeDecoder)) {
                            this.decodeMethodState = 5;
                            break;
                        } else {
                            return false;
                        }
                    case 2:
                        if (rangeDecoder.decodeBit(decoderState, this.m_Choice, 1)) {
                            this.decodeMethodState = decoderState.lastMethodResult == 0 ? 3 : 4;
                            break;
                        } else {
                            return false;
                        }
                    case 3:
                        if (this.m_MidCoder[posState].decode(decoderState, rangeDecoder)) {
                            decoderState.lastMethodResult += 8;
                            this.decodeMethodState = 5;
                            break;
                        } else {
                            return false;
                        }
                    case 4:
                        if (this.m_HighCoder.decode(decoderState, rangeDecoder)) {
                            decoderState.lastMethodResult += 16;
                            this.decodeMethodState = 5;
                            break;
                        } else {
                            return false;
                        }
                    case 5:
                        this.decodeMethodState = 0;
                        return true;
                }
            }
        }
    }

    public static class LiteralDecoder {
        Decoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        public static class Decoder2 {
            int decodeNormalMethodState;
            int decodeWithMatchByteMethodState;
            final short[] m_Decoders = new short[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART];
            int matchBit;
            byte matchByte;
            int symbol;

            public void init() {
                this.decodeNormalMethodState = 0;
                this.decodeWithMatchByteMethodState = 0;
                RangeDecoder.initBitModels(this.m_Decoders);
            }

            public boolean decodeNormal(LZMADecoder.LZMAInputState decoderState, RangeDecoder rangeDecoder) {
                while (true) {
                    switch (this.decodeNormalMethodState) {
                        case 0:
                            this.symbol = 1;
                            this.decodeNormalMethodState = 1;
                            break;
                        case 1:
                            break;
                    }
                    if (!rangeDecoder.decodeBit(decoderState, this.m_Decoders, this.symbol)) {
                        return false;
                    }
                    int i = (this.symbol << 1) | decoderState.lastMethodResult;
                    this.symbol = i;
                    if (i >= 256) {
                        this.decodeNormalMethodState = 0;
                        decoderState.lastMethodResult = i;
                        return true;
                    }
                }
            }

            /* JADX WARNING: Removed duplicated region for block: B:17:0x0050  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0053  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean decodeWithMatchByte(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r8, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r9, byte r10) {
                /*
                    r7 = this;
                L_0x0000:
                    int r0 = r7.decodeWithMatchByteMethodState
                    r1 = 4
                    r2 = 256(0x100, float:3.59E-43)
                    r3 = 0
                    r4 = 1
                    switch(r0) {
                        case 0: goto L_0x0012;
                        case 1: goto L_0x0016;
                        case 2: goto L_0x0024;
                        case 3: goto L_0x004c;
                        case 4: goto L_0x000b;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    goto L_0x0000
                L_0x000b:
                    r7.decodeWithMatchByteMethodState = r3
                    int r0 = r7.symbol
                    r8.lastMethodResult = r0
                    return r4
                L_0x0012:
                    r7.symbol = r4
                    r7.matchByte = r10
                L_0x0016:
                    byte r0 = r7.matchByte
                    int r5 = r0 >> 7
                    r5 = r5 & r4
                    r7.matchBit = r5
                    int r0 = r0 << r4
                    byte r0 = (byte) r0
                    r7.matchByte = r0
                    r0 = 2
                    r7.decodeWithMatchByteMethodState = r0
                L_0x0024:
                    short[] r0 = r7.m_Decoders
                    int r5 = r7.matchBit
                    int r5 = r5 + r4
                    int r5 = r5 << 8
                    int r6 = r7.symbol
                    int r5 = r5 + r6
                    boolean r0 = r9.decodeBit(r8, r0, r5)
                    if (r0 != 0) goto L_0x0035
                    return r3
                L_0x0035:
                    int r0 = r8.lastMethodResult
                    int r5 = r7.symbol
                    int r5 = r5 << r4
                    r5 = r5 | r0
                    r7.symbol = r5
                    int r6 = r7.matchBit
                    if (r6 != r0) goto L_0x0049
                    if (r5 < r2) goto L_0x0046
                    r7.decodeWithMatchByteMethodState = r1
                    goto L_0x0000
                L_0x0046:
                    r7.decodeWithMatchByteMethodState = r4
                    goto L_0x0000
                L_0x0049:
                    r5 = 3
                    r7.decodeWithMatchByteMethodState = r5
                L_0x004c:
                    int r0 = r7.symbol
                    if (r0 < r2) goto L_0x0053
                    r7.decodeWithMatchByteMethodState = r1
                    goto L_0x0000
                L_0x0053:
                    short[] r1 = r7.m_Decoders
                    boolean r0 = r9.decodeBit(r8, r1, r0)
                    if (r0 != 0) goto L_0x005c
                    return r3
                L_0x005c:
                    int r0 = r7.symbol
                    int r0 = r0 << r4
                    int r1 = r8.lastMethodResult
                    r0 = r0 | r1
                    r7.symbol = r0
                    goto L_0x0000
                */
                throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.Decoder.LiteralDecoder.Decoder2.decodeWithMatchByte(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder, byte):boolean");
            }
        }

        public void create(int numPosBits, int numPrevBits) {
            if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
                this.m_NumPosBits = numPosBits;
                this.m_PosMask = (1 << numPosBits) - 1;
                this.m_NumPrevBits = numPrevBits;
                int numStates = 1 << (numPrevBits + numPosBits);
                this.m_Coders = new Decoder2[numStates];
                for (int i = 0; i < numStates; i++) {
                    this.m_Coders[i] = new Decoder2();
                }
            }
        }

        public void init() {
            int numStates = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i = 0; i < numStates; i++) {
                this.m_Coders[i].init();
            }
        }

        /* access modifiers changed from: package-private */
        public Decoder2 getDecoder(int pos, byte prevByte) {
            Decoder2[] decoder2Arr = this.m_Coders;
            int i = this.m_NumPrevBits;
            return decoder2Arr[((this.m_PosMask & pos) << i) + ((prevByte & 255) >>> (8 - i))];
        }
    }

    public Decoder() {
        for (int i = 0; i < 4; i++) {
            this.m_PosSlotDecoder[i] = new BitTreeDecoder(6);
        }
    }

    public boolean setDecoderProperties(byte[] properties) {
        if (properties.length < 5) {
            return false;
        }
        int val = properties[0] & 255;
        int lc = val % 9;
        int remainder = val / 9;
        int lp = remainder % 5;
        int pb = remainder / 5;
        int dictionarySize = 0;
        for (int i = 0; i < 4; i++) {
            dictionarySize += (properties[i + 1] & 255) << (i * 8);
        }
        if (setLcLpPb(lc, lp, pb) == 0 || !setDictionarySize(dictionarySize)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean setDictionarySize(int dictionarySize) {
        if (dictionarySize < 0) {
            return false;
        }
        if (this.m_DictionarySize != dictionarySize) {
            this.m_DictionarySize = dictionarySize;
            int max = Math.max(dictionarySize, 1);
            this.m_DictionarySizeCheck = max;
            this.m_OutWindow.create(Math.max(max, 4096));
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean setLcLpPb(int lc, int lp, int pb) {
        if (lc > 8 || lp > 4 || pb > 4) {
            return false;
        }
        this.m_LiteralDecoder.create(lp, lc);
        int numPosStates = 1 << pb;
        this.m_LenDecoder.create(numPosStates);
        this.m_RepLenDecoder.create(numPosStates);
        this.m_PosStateMask = numPosStates - 1;
        return true;
    }

    /* access modifiers changed from: package-private */
    public void init() {
        this.m_OutWindow.init(false);
        RangeDecoder.initBitModels(this.m_IsMatchDecoders);
        RangeDecoder.initBitModels(this.m_IsRep0LongDecoders);
        RangeDecoder.initBitModels(this.m_IsRepDecoders);
        RangeDecoder.initBitModels(this.m_IsRepG0Decoders);
        RangeDecoder.initBitModels(this.m_IsRepG1Decoders);
        RangeDecoder.initBitModels(this.m_IsRepG2Decoders);
        RangeDecoder.initBitModels(this.m_PosDecoders);
        this.m_LiteralDecoder.init();
        for (int i = 0; i < 4; i++) {
            this.m_PosSlotDecoder[i].init();
        }
        this.m_LenDecoder.init();
        this.m_RepLenDecoder.init();
        this.m_PosAlignDecoder.init();
        this.m_RangeDecoder.init();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006f, code lost:
        r1 = ((int) r6.nowPos64) & r5.m_PosStateMask;
        r6.posState = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0084, code lost:
        if (r5.m_RangeDecoder.decodeBit(r6, r5.m_IsMatchDecoders, (r6.state << 4) + r1) != false) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0088, code lost:
        return org.glassfish.grizzly.compression.lzma.impl.Decoder.State.NEED_MORE_DATA;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008b, code lost:
        if (r6.lastMethodResult != 0) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008d, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008f, code lost:
        r2 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0090, code lost:
        r6.inner1State = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.compression.lzma.impl.Decoder.State code(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r6, long r7) {
        /*
            r5 = this;
            org.glassfish.grizzly.Buffer r0 = r6.getSrc()
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r1 = r5.m_RangeDecoder
            r1.initFromState(r6)
            org.glassfish.grizzly.compression.lzma.impl.lz.OutWindow r1 = r5.m_OutWindow
            r1.initFromState(r6)
            boolean r1 = r6.isInitialized()
            if (r1 != 0) goto L_0x0025
            int r1 = r0.remaining()
            r2 = 13
            if (r1 >= r2) goto L_0x001f
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r1 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.NEED_MORE_DATA
            return r1
        L_0x001f:
            r6.initialize(r0)
            r5.init()
        L_0x0025:
            int r1 = r6.inner1State
            r2 = 0
            switch(r1) {
                case 0: goto L_0x004d;
                case 1: goto L_0x006f;
                case 2: goto L_0x0041;
                case 3: goto L_0x002c;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x0093
        L_0x002c:
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r1 = r5.processState3(r6)
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r3 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.NEED_MORE_DATA
            if (r1 == r3) goto L_0x0040
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r3 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.ERR
            if (r1 != r3) goto L_0x0039
            goto L_0x0040
        L_0x0039:
            r6.inner1State = r2
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r2 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.DONE
            if (r1 != r2) goto L_0x0093
            goto L_0x005a
        L_0x0040:
            return r1
        L_0x0041:
            boolean r1 = r5.processState2(r6)
            if (r1 != 0) goto L_0x004a
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r1 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.NEED_MORE_DATA
            return r1
        L_0x004a:
            r6.inner1State = r2
            goto L_0x0093
        L_0x004d:
            r1 = 0
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r1 < 0) goto L_0x006c
            long r1 = r6.nowPos64
            int r1 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r1 < 0) goto L_0x006c
        L_0x005a:
            org.glassfish.grizzly.compression.lzma.impl.lz.OutWindow r1 = r5.m_OutWindow
            r1.flush()
            org.glassfish.grizzly.compression.lzma.impl.lz.OutWindow r1 = r5.m_OutWindow
            r1.releaseBuffer()
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r1 = r5.m_RangeDecoder
            r1.releaseBuffer()
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r1 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.DONE
            return r1
        L_0x006c:
            r1 = 1
            r6.inner1State = r1
        L_0x006f:
            long r1 = r6.nowPos64
            int r1 = (int) r1
            int r2 = r5.m_PosStateMask
            r1 = r1 & r2
            r6.posState = r1
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r2 = r5.m_RangeDecoder
            short[] r3 = r5.m_IsMatchDecoders
            int r4 = r6.state
            int r4 = r4 << 4
            int r4 = r4 + r1
            boolean r1 = r2.decodeBit(r6, r3, r4)
            if (r1 != 0) goto L_0x0089
            org.glassfish.grizzly.compression.lzma.impl.Decoder$State r1 = org.glassfish.grizzly.compression.lzma.impl.Decoder.State.NEED_MORE_DATA
            return r1
        L_0x0089:
            int r1 = r6.lastMethodResult
            if (r1 != 0) goto L_0x008f
            r2 = 2
            goto L_0x0090
        L_0x008f:
            r2 = 3
        L_0x0090:
            r6.inner1State = r2
        L_0x0093:
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.Decoder.code(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState, long):org.glassfish.grizzly.compression.lzma.impl.Decoder$State");
    }

    private boolean processState2(LZMADecoder.LZMAInputState decoderState) {
        while (true) {
            int i = 1;
            switch (decoderState.inner2State) {
                case 0:
                    decoderState.decoder2 = this.m_LiteralDecoder.getDecoder((int) decoderState.nowPos64, decoderState.prevByte);
                    if (Base.stateIsCharState(decoderState.state)) {
                        i = 2;
                    }
                    decoderState.inner2State = i;
                    continue;
                case 1:
                    if (!decoderState.decoder2.decodeWithMatchByte(decoderState, this.m_RangeDecoder, this.m_OutWindow.getByte(decoderState.rep0))) {
                        return false;
                    }
                    decoderState.prevByte = (byte) decoderState.lastMethodResult;
                    decoderState.inner2State = 3;
                    continue;
                case 2:
                    if (decoderState.decoder2.decodeNormal(decoderState, this.m_RangeDecoder)) {
                        decoderState.prevByte = (byte) decoderState.lastMethodResult;
                        decoderState.inner2State = 3;
                        break;
                    } else {
                        return false;
                    }
                case 3:
                    break;
            }
        }
        this.m_OutWindow.putByte(decoderState.prevByte);
        decoderState.state = Base.stateUpdateChar(decoderState.state);
        decoderState.nowPos64++;
        decoderState.inner2State = 0;
        return true;
    }

    private State processState3(LZMADecoder.LZMAInputState decoderState) {
        while (true) {
            switch (decoderState.inner2State) {
                case 0:
                    if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepDecoders, decoderState.state)) {
                        return State.NEED_MORE_DATA;
                    }
                    int i = 1;
                    if (decoderState.lastMethodResult != 1) {
                        i = 2;
                    }
                    decoderState.inner2State = i;
                    continue;
                case 1:
                    if (!processState31(decoderState)) {
                        return State.NEED_MORE_DATA;
                    }
                    decoderState.inner2State = 3;
                    continue;
                case 2:
                    State internalResult = processState32(decoderState);
                    if (internalResult == State.CONTINUE) {
                        decoderState.inner2State = 3;
                        break;
                    } else {
                        return internalResult;
                    }
                case 3:
                    break;
            }
        }
        int i2 = decoderState.rep0;
        if (((long) i2) >= decoderState.nowPos64 || i2 >= this.m_DictionarySizeCheck) {
            return State.ERR;
        }
        this.m_OutWindow.copyBlock(i2, decoderState.state3Len);
        decoderState.nowPos64 += (long) decoderState.state3Len;
        decoderState.prevByte = this.m_OutWindow.getByte(0);
        decoderState.inner2State = 0;
        return State.CONTINUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        r8.state31 = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
        if (r8.state3Len == 0) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        r8.state31 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean processState31(org.glassfish.grizzly.compression.lzma.LZMADecoder.LZMAInputState r8) {
        /*
            r7 = this;
        L_0x0000:
            int r0 = r8.state31
            r1 = 3
            r2 = 2
            r3 = 4
            r4 = 1
            r5 = 0
            switch(r0) {
                case 0: goto L_0x005d;
                case 1: goto L_0x003a;
                case 2: goto L_0x000b;
                case 3: goto L_0x0014;
                case 4: goto L_0x001d;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0000
        L_0x000b:
            boolean r0 = r7.processState311(r8)
            if (r0 != 0) goto L_0x0012
            return r5
        L_0x0012:
            r8.state31 = r1
        L_0x0014:
            int r0 = r8.state3Len
            if (r0 == 0) goto L_0x001b
            r8.state31 = r5
            return r4
        L_0x001b:
            r8.state31 = r3
        L_0x001d:
            org.glassfish.grizzly.compression.lzma.impl.Decoder$LenDecoder r0 = r7.m_RepLenDecoder
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r1 = r7.m_RangeDecoder
            int r3 = r8.posState
            boolean r0 = r0.decode(r8, r1, r3)
            if (r0 != 0) goto L_0x002a
            return r5
        L_0x002a:
            int r0 = r8.lastMethodResult
            int r0 = r0 + r2
            r8.state3Len = r0
            int r0 = r8.state
            int r0 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateRep(r0)
            r8.state = r0
            r8.state31 = r5
            return r4
        L_0x003a:
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r0 = r7.m_RangeDecoder
            short[] r2 = r7.m_IsRep0LongDecoders
            int r6 = r8.state
            int r3 = r6 << 4
            int r6 = r8.posState
            int r3 = r3 + r6
            boolean r0 = r0.decodeBit(r8, r2, r3)
            if (r0 != 0) goto L_0x004c
            return r5
        L_0x004c:
            int r0 = r8.lastMethodResult
            if (r0 != 0) goto L_0x005a
            int r0 = r8.state
            int r0 = org.glassfish.grizzly.compression.lzma.impl.Base.stateUpdateShortRep(r0)
            r8.state = r0
            r8.state3Len = r4
        L_0x005a:
            r8.state31 = r1
            goto L_0x0000
        L_0x005d:
            r8.state3Len = r5
            org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder r0 = r7.m_RangeDecoder
            short[] r1 = r7.m_IsRepG0Decoders
            int r3 = r8.state
            boolean r0 = r0.decodeBit(r8, r1, r3)
            if (r0 != 0) goto L_0x006c
            return r5
        L_0x006c:
            int r0 = r8.lastMethodResult
            if (r0 != 0) goto L_0x0071
            r2 = r4
        L_0x0071:
            r8.state31 = r2
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.lzma.impl.Decoder.processState31(org.glassfish.grizzly.compression.lzma.LZMADecoder$LZMAInputState):boolean");
    }

    private boolean processState311(LZMADecoder.LZMAInputState decoderState) {
        while (true) {
            int i = 1;
            switch (decoderState.state311) {
                case 0:
                    if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepG1Decoders, decoderState.state)) {
                        return false;
                    }
                    if (decoderState.lastMethodResult != 0) {
                        i = 2;
                    }
                    decoderState.state311 = i;
                    continue;
                case 1:
                    decoderState.state311Distance = decoderState.rep1;
                    decoderState.state311 = 3;
                    continue;
                case 2:
                    if (this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepG2Decoders, decoderState.state)) {
                        if (decoderState.lastMethodResult == 0) {
                            decoderState.state311Distance = decoderState.rep2;
                        } else {
                            decoderState.state311Distance = decoderState.rep3;
                            decoderState.rep3 = decoderState.rep2;
                        }
                        decoderState.rep2 = decoderState.rep1;
                        break;
                    } else {
                        return false;
                    }
                case 3:
                    break;
            }
        }
        decoderState.rep1 = decoderState.rep0;
        decoderState.rep0 = decoderState.state311Distance;
        decoderState.state311 = 0;
        return true;
    }

    private State processState32(LZMADecoder.LZMAInputState decoderState) {
        while (true) {
            switch (decoderState.state32) {
                case 0:
                    decoderState.rep3 = decoderState.rep2;
                    decoderState.rep2 = decoderState.rep1;
                    decoderState.rep1 = decoderState.rep0;
                    decoderState.state32 = 1;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    State localState = processState321(decoderState);
                    if (localState == State.CONTINUE) {
                        decoderState.state32 = 0;
                    }
                    return localState;
                case 4:
                    decoderState.rep0 = decoderState.state32PosSlot;
                    decoderState.state32 = 0;
                    return State.CONTINUE;
            }
            if (!this.m_LenDecoder.decode(decoderState, this.m_RangeDecoder, decoderState.posState)) {
                return State.NEED_MORE_DATA;
            }
            decoderState.state3Len = decoderState.lastMethodResult + 2;
            decoderState.state = Base.stateUpdateMatch(decoderState.state);
            decoderState.state32 = 2;
            if (!this.m_PosSlotDecoder[Base.getLenToPosState(decoderState.state3Len)].decode(decoderState, this.m_RangeDecoder)) {
                return State.NEED_MORE_DATA;
            }
            int i = decoderState.lastMethodResult;
            decoderState.state32PosSlot = i;
            int i2 = 4;
            if (i >= 4) {
                i2 = 3;
            }
            decoderState.state32 = i2;
            continue;
        }
    }

    private State processState321(LZMADecoder.LZMAInputState decoderState) {
        while (true) {
            int i = 1;
            switch (decoderState.state321) {
                case 0:
                    int i2 = decoderState.state32PosSlot;
                    int i3 = (i2 >> 1) - 1;
                    decoderState.state321NumDirectBits = i3;
                    decoderState.rep0 = ((i2 & 1) | 2) << i3;
                    if (i2 >= 14) {
                        i = 2;
                    }
                    decoderState.state321 = i;
                    break;
                case 1:
                    if (!BitTreeDecoder.reverseDecode(decoderState, this.m_PosDecoders, (decoderState.rep0 - decoderState.state32PosSlot) - 1, this.m_RangeDecoder, decoderState.state321NumDirectBits)) {
                        return State.NEED_MORE_DATA;
                    }
                    decoderState.rep0 += decoderState.lastMethodResult;
                    decoderState.state321 = 0;
                    return State.CONTINUE;
                case 2:
                    if (this.m_RangeDecoder.decodeDirectBits(decoderState, decoderState.state321NumDirectBits - 4)) {
                        decoderState.rep0 += decoderState.lastMethodResult << 4;
                        decoderState.state321 = 3;
                        break;
                    } else {
                        return State.NEED_MORE_DATA;
                    }
                case 3:
                    if (!this.m_PosAlignDecoder.reverseDecode(decoderState, this.m_RangeDecoder)) {
                        return State.NEED_MORE_DATA;
                    }
                    int i4 = decoderState.rep0 + decoderState.lastMethodResult;
                    decoderState.rep0 = i4;
                    decoderState.state321 = 0;
                    if (i4 >= 0) {
                        return State.CONTINUE;
                    }
                    if (i4 == -1) {
                        return State.DONE;
                    }
                    return State.ERR;
            }
        }
    }
}
