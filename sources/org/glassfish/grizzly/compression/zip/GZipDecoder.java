package org.glassfish.grizzly.compression.zip;

import java.util.zip.CRC32;
import java.util.zip.Inflater;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;

public class GZipDecoder extends AbstractTransformer<Buffer, Buffer> {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FTEXT = 1;
    private static final int GZIP_MAGIC = 35615;
    private final int bufferSize;

    public enum DecodeStatus {
        INITIAL,
        FEXTRA1,
        FEXTRA2,
        FNAME,
        FCOMMENT,
        FHCRC,
        PAYLOAD,
        TRAILER,
        DONE
    }

    public GZipDecoder() {
        this(512);
    }

    public GZipDecoder(int bufferSize2) {
        this.bufferSize = bufferSize2;
    }

    public String getName() {
        return "gzip-decoder";
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public GZipInputState createStateObject() {
        return new GZipInputState();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage storage, Buffer input) {
        MemoryManager memoryManager = obtainMemoryManager(storage);
        GZipInputState state = (GZipInputState) obtainStateObject(storage);
        if (!state.isInitialized() && !initializeInput(input, state)) {
            return TransformationResult.createIncompletedResult(input);
        }
        Buffer decodedBuffer = null;
        if (state.getDecodeStatus() == DecodeStatus.PAYLOAD && input.hasRemaining()) {
            decodedBuffer = decodeBuffer(memoryManager, input, state);
        }
        if (state.getDecodeStatus() == DecodeStatus.TRAILER && input.hasRemaining() && decodeTrailer(input, state)) {
            state.setDecodeStatus(DecodeStatus.DONE);
            state.setInitialized(false);
        }
        boolean hasRemainder = input.hasRemaining();
        Buffer buffer = null;
        if (decodedBuffer == null || !decodedBuffer.hasRemaining()) {
            if (hasRemainder) {
                buffer = input;
            }
            return TransformationResult.createIncompletedResult(buffer);
        }
        if (hasRemainder) {
            buffer = input;
        }
        return TransformationResult.createCompletedResult(decodedBuffer, buffer);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0092, code lost:
        r7 = r10.remaining();
        r6.restore();
        r6.recycle();
        r3.position(r25.position() + r7);
        r9 = r9 + 1;
        r0 = r14;
        r7 = r17;
        r8 = r19;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.glassfish.grizzly.Buffer decodeBuffer(org.glassfish.grizzly.memory.MemoryManager r24, org.glassfish.grizzly.Buffer r25, org.glassfish.grizzly.compression.zip.GZipDecoder.GZipInputState r26) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            java.util.zip.Inflater r4 = r26.getInflater()
            java.util.zip.CRC32 r5 = r26.getCrc32()
            org.glassfish.grizzly.memory.ByteBufferArray r6 = r25.toByteBufferArray()
            java.lang.Object[] r0 = r6.getArray()
            r7 = r0
            java.nio.ByteBuffer[] r7 = (java.nio.ByteBuffer[]) r7
            int r8 = r6.size()
            r0 = 0
            r9 = 0
        L_0x001f:
            if (r9 >= r8) goto L_0x00f0
            r10 = r7[r9]
            int r11 = r10.remaining()
            boolean r12 = r10.hasArray()
            if (r12 == 0) goto L_0x003b
            byte[] r12 = r10.array()
            int r13 = r10.arrayOffset()
            int r14 = r10.position()
            int r13 = r13 + r14
            goto L_0x0049
        L_0x003b:
            byte[] r12 = new byte[r11]
            r13 = 0
            r10.get(r12)
            int r14 = r10.position()
            int r14 = r14 - r11
            r10.position(r14)
        L_0x0049:
            r4.setInput(r12, r13, r11)
            r14 = r0
        L_0x004d:
            int r0 = r1.bufferSize
            org.glassfish.grizzly.Buffer r15 = r2.allocate(r0)
            java.nio.ByteBuffer r16 = r15.toByteBuffer()
            r17 = r7
            byte[] r7 = r16.array()
            int r0 = r16.arrayOffset()
            int r18 = r16.position()
            r19 = r8
            int r8 = r0 + r18
            int r0 = r1.bufferSize     // Catch:{ DataFormatException -> 0x00d5 }
            int r0 = r4.inflate(r7, r8, r0)     // Catch:{ DataFormatException -> 0x00d5 }
            if (r0 <= 0) goto L_0x0080
            r5.update(r7, r8, r0)
            r15.position(r0)
            r15.trim()
            org.glassfish.grizzly.Buffer r14 = org.glassfish.grizzly.memory.Buffers.appendBuffers(r2, r14, r15)
            goto L_0x0090
        L_0x0080:
            r15.dispose()
            boolean r18 = r4.finished()
            if (r18 != 0) goto L_0x00b2
            boolean r18 = r4.needsDictionary()
            if (r18 == 0) goto L_0x0090
            goto L_0x00b2
        L_0x0090:
            if (r0 > 0) goto L_0x00ad
            int r7 = r10.remaining()
            r6.restore()
            r6.recycle()
            int r8 = r25.position()
            int r8 = r8 + r7
            r3.position(r8)
            int r9 = r9 + 1
            r0 = r14
            r7 = r17
            r8 = r19
            goto L_0x001f
        L_0x00ad:
            r7 = r17
            r8 = r19
            goto L_0x004d
        L_0x00b2:
            int r18 = r4.getRemaining()
            int r20 = r10.remaining()
            r6.restore()
            r6.recycle()
            int r21 = r25.position()
            int r21 = r21 + r20
            r22 = r0
            int r0 = r21 - r18
            r3.position(r0)
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r0 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.TRAILER
            r1 = r26
            r1.setDecodeStatus(r0)
            return r14
        L_0x00d5:
            r0 = move-exception
            r1 = r26
            r15.dispose()
            java.lang.String r18 = r0.getMessage()
            r20 = r0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            if (r18 == 0) goto L_0x00e8
            r1 = r18
            goto L_0x00ec
        L_0x00e8:
            java.lang.String r21 = "Invalid ZLIB data format"
            r1 = r21
        L_0x00ec:
            r0.<init>(r1)
            throw r0
        L_0x00f0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.zip.GZipDecoder.decodeBuffer(org.glassfish.grizzly.memory.MemoryManager, org.glassfish.grizzly.Buffer, org.glassfish.grizzly.compression.zip.GZipDecoder$GZipInputState):org.glassfish.grizzly.Buffer");
    }

    private boolean initializeInput(Buffer buffer, GZipInputState state) {
        Inflater inflater = state.getInflater();
        if (inflater == null) {
            Inflater inflater2 = new Inflater(true);
            CRC32 crc32 = new CRC32();
            crc32.reset();
            state.setInflater(inflater2);
            state.setCrc32(crc32);
        } else if (state.getDecodeStatus() == DecodeStatus.DONE) {
            state.setDecodeStatus(DecodeStatus.INITIAL);
            inflater.reset();
            state.getCrc32().reset();
        }
        if (!parseHeader(buffer, state)) {
            return false;
        }
        state.getCrc32().reset();
        state.setInitialized(true);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004c, code lost:
        if ((r9.getHeaderFlag() & 4) == 4) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004e, code lost:
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FNAME);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0059, code lost:
        if (r8.remaining() >= 2) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005b, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005c, code lost:
        r9.setHeaderParseStateValue(getUShort(r8, r0));
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FEXTRA2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        r1 = r9.getHeaderParseStateValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0070, code lost:
        if (r8.remaining() >= r1) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0072, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0073, code lost:
        skipBytes(r8, r1, r0);
        r9.setHeaderParseStateValue(0);
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FNAME);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
        if ((r9.getHeaderFlag() & 8) != 8) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0085, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008a, code lost:
        if (r8.hasRemaining() == false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0090, code lost:
        if (getUByte(r8, r0) != 0) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0092, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0093, code lost:
        if (r1 != false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0095, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0096, code lost:
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FCOMMENT);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a2, code lost:
        if ((r9.getHeaderFlag() & 16) != 16) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a4, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a9, code lost:
        if (r8.hasRemaining() == false) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00af, code lost:
        if (getUByte(r8, r0) != 0) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b1, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b2, code lost:
        if (r1 != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b4, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b5, code lost:
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FHCRC);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bf, code lost:
        if ((r9.getHeaderFlag() & 2) != 2) goto L_0x00e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c5, code lost:
        if (r8.remaining() >= 2) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c7, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d9, code lost:
        if ((((int) r9.getCrc32().getValue()) & 65535) != getUShort(r8, r0)) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e3, code lost:
        throw new java.lang.IllegalStateException("Corrupt GZIP header");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e4, code lost:
        r9.setDecodeStatus(org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.PAYLOAD);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean parseHeader(org.glassfish.grizzly.Buffer r8, org.glassfish.grizzly.compression.zip.GZipDecoder.GZipInputState r9) {
        /*
            r7 = this;
            java.util.zip.CRC32 r0 = r9.getCrc32()
        L_0x0004:
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = r9.getDecodeStatus()
            r2 = r1
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r3 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.PAYLOAD
            if (r1 == r3) goto L_0x00fc
            int[] r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.AnonymousClass1.$SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus
            int r3 = r2.ordinal()
            r1 = r1[r3]
            r3 = 8
            r4 = 2
            r5 = 0
            switch(r1) {
                case 1: goto L_0x001e;
                case 2: goto L_0x0046;
                case 3: goto L_0x0068;
                case 4: goto L_0x007e;
                case 5: goto L_0x009b;
                case 6: goto L_0x00ba;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x00fa
        L_0x001e:
            int r1 = r8.remaining()
            r6 = 10
            if (r1 >= r6) goto L_0x0027
            return r5
        L_0x0027:
            int r1 = getUShort(r8, r0)
            r6 = 35615(0x8b1f, float:4.9907E-41)
            if (r1 != r6) goto L_0x00f2
            int r1 = getUByte(r8, r0)
            if (r1 != r3) goto L_0x00ea
            int r1 = getUByte(r8, r0)
            r9.setHeaderFlag(r1)
            r6 = 6
            skipBytes(r8, r6, r0)
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r6 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FEXTRA1
            r9.setDecodeStatus(r6)
        L_0x0046:
            int r1 = r9.getHeaderFlag()
            r6 = 4
            r1 = r1 & r6
            if (r1 == r6) goto L_0x0055
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FNAME
            r9.setDecodeStatus(r1)
            goto L_0x00fa
        L_0x0055:
            int r1 = r8.remaining()
            if (r1 >= r4) goto L_0x005c
            return r5
        L_0x005c:
            int r1 = getUShort(r8, r0)
            r9.setHeaderParseStateValue(r1)
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FEXTRA2
            r9.setDecodeStatus(r1)
        L_0x0068:
            int r1 = r9.getHeaderParseStateValue()
            int r6 = r8.remaining()
            if (r6 >= r1) goto L_0x0073
            return r5
        L_0x0073:
            skipBytes(r8, r1, r0)
            r9.setHeaderParseStateValue(r5)
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r6 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FNAME
            r9.setDecodeStatus(r6)
        L_0x007e:
            int r1 = r9.getHeaderFlag()
            r1 = r1 & r3
            if (r1 != r3) goto L_0x0096
            r1 = 0
        L_0x0086:
            boolean r3 = r8.hasRemaining()
            if (r3 == 0) goto L_0x0093
            int r3 = getUByte(r8, r0)
            if (r3 != 0) goto L_0x0086
            r1 = 1
        L_0x0093:
            if (r1 != 0) goto L_0x0096
            return r5
        L_0x0096:
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FCOMMENT
            r9.setDecodeStatus(r1)
        L_0x009b:
            int r1 = r9.getHeaderFlag()
            r3 = 16
            r1 = r1 & r3
            if (r1 != r3) goto L_0x00b5
            r1 = 0
        L_0x00a5:
            boolean r3 = r8.hasRemaining()
            if (r3 == 0) goto L_0x00b2
            int r3 = getUByte(r8, r0)
            if (r3 != 0) goto L_0x00a5
            r1 = 1
        L_0x00b2:
            if (r1 != 0) goto L_0x00b5
            return r5
        L_0x00b5:
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.FHCRC
            r9.setDecodeStatus(r1)
        L_0x00ba:
            int r1 = r9.getHeaderFlag()
            r1 = r1 & r4
            if (r1 != r4) goto L_0x00e4
            int r1 = r8.remaining()
            if (r1 >= r4) goto L_0x00c8
            return r5
        L_0x00c8:
            java.util.zip.CRC32 r1 = r9.getCrc32()
            long r3 = r1.getValue()
            int r1 = (int) r3
            r3 = 65535(0xffff, float:9.1834E-41)
            r1 = r1 & r3
            int r3 = getUShort(r8, r0)
            if (r1 != r3) goto L_0x00dc
            goto L_0x00e4
        L_0x00dc:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Corrupt GZIP header"
            r4.<init>(r5)
            throw r4
        L_0x00e4:
            org.glassfish.grizzly.compression.zip.GZipDecoder$DecodeStatus r1 = org.glassfish.grizzly.compression.zip.GZipDecoder.DecodeStatus.PAYLOAD
            r9.setDecodeStatus(r1)
            goto L_0x00fa
        L_0x00ea:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r3 = "Unsupported compression method"
            r1.<init>(r3)
            throw r1
        L_0x00f2:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r3 = "Not in GZIP format"
            r1.<init>(r3)
            throw r1
        L_0x00fa:
            goto L_0x0004
        L_0x00fc:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.compression.zip.GZipDecoder.parseHeader(org.glassfish.grizzly.Buffer, org.glassfish.grizzly.compression.zip.GZipDecoder$GZipInputState):boolean");
    }

    /* renamed from: org.glassfish.grizzly.compression.zip.GZipDecoder$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus;

        static {
            int[] iArr = new int[DecodeStatus.values().length];
            $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus = iArr;
            try {
                iArr[DecodeStatus.INITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus[DecodeStatus.FEXTRA1.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus[DecodeStatus.FEXTRA2.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus[DecodeStatus.FNAME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus[DecodeStatus.FCOMMENT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$compression$zip$GZipDecoder$DecodeStatus[DecodeStatus.FHCRC.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private boolean decodeTrailer(Buffer buffer, GZipInputState state) {
        if (buffer.remaining() < 8) {
            return false;
        }
        Inflater inflater = state.getInflater();
        CRC32 crc32 = state.getCrc32();
        if (getUInt(buffer, crc32) == crc32.getValue() && getUInt(buffer, crc32) == (inflater.getBytesWritten() & 4294967295L)) {
            return true;
        }
        throw new TransformationException("Corrupt GZIP trailer");
    }

    private static long getUInt(Buffer buffer, CRC32 crc32) {
        return (((long) getUShort(buffer, crc32)) << 16) | ((long) getUShort(buffer, crc32));
    }

    private static int getUShort(Buffer buffer, CRC32 crc32) {
        return (getUByte(buffer, crc32) << 8) | getUByte(buffer, crc32);
    }

    private static int getUByte(Buffer buffer, CRC32 crc32) {
        byte b = buffer.get();
        crc32.update(b);
        return b & 255;
    }

    private static void skipBytes(Buffer buffer, int num, CRC32 crc32) {
        for (int i = 0; i < num; i++) {
            getUByte(buffer, crc32);
        }
    }

    public static final class GZipInputState extends AbstractTransformer.LastResultAwareState<Buffer, Buffer> {
        private CRC32 crc32;
        private DecodeStatus decodeStatus = DecodeStatus.INITIAL;
        private int headerFlag;
        private int headerParseStateValue;
        private Inflater inflater;
        private boolean isInitialized;

        protected GZipInputState() {
        }

        public boolean isInitialized() {
            return this.isInitialized;
        }

        public void setInitialized(boolean isInitialized2) {
            this.isInitialized = isInitialized2;
        }

        public Inflater getInflater() {
            return this.inflater;
        }

        public void setInflater(Inflater inflater2) {
            this.inflater = inflater2;
        }

        public CRC32 getCrc32() {
            return this.crc32;
        }

        public void setCrc32(CRC32 crc322) {
            this.crc32 = crc322;
        }

        public DecodeStatus getDecodeStatus() {
            return this.decodeStatus;
        }

        public void setDecodeStatus(DecodeStatus decodeStatus2) {
            this.decodeStatus = decodeStatus2;
        }

        public int getHeaderFlag() {
            return this.headerFlag;
        }

        public void setHeaderFlag(int headerFlag2) {
            this.headerFlag = headerFlag2;
        }

        public int getHeaderParseStateValue() {
            return this.headerParseStateValue;
        }

        public void setHeaderParseStateValue(int headerParseStateValue2) {
            this.headerParseStateValue = headerParseStateValue2;
        }
    }
}
