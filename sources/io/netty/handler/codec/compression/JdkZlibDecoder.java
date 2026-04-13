package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class JdkZlibDecoder extends ZlibDecoder {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final CRC32 crc;
    private boolean decideZlibOrNone;
    private final boolean decompressConcatenated;
    private final byte[] dictionary;
    private volatile boolean finished;
    private int flags;
    private GzipState gzipState;
    private Inflater inflater;
    private int xlen;

    public enum GzipState {
        HEADER_START,
        HEADER_END,
        FLG_READ,
        XLEN_READ,
        SKIP_FNAME,
        SKIP_COMMENT,
        PROCESS_FHCRC,
        FOOTER_START
    }

    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, (byte[]) null, false);
    }

    public JdkZlibDecoder(byte[] dictionary2) {
        this(ZlibWrapper.ZLIB, dictionary2, false);
    }

    public JdkZlibDecoder(ZlibWrapper wrapper) {
        this(wrapper, (byte[]) null, false);
    }

    public JdkZlibDecoder(ZlibWrapper wrapper, boolean decompressConcatenated2) {
        this(wrapper, (byte[]) null, decompressConcatenated2);
    }

    public JdkZlibDecoder(boolean decompressConcatenated2) {
        this(ZlibWrapper.GZIP, (byte[]) null, decompressConcatenated2);
    }

    private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary2, boolean decompressConcatenated2) {
        this.gzipState = GzipState.HEADER_START;
        this.flags = -1;
        this.xlen = -1;
        if (wrapper != null) {
            this.decompressConcatenated = decompressConcatenated2;
            switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[wrapper.ordinal()]) {
                case 1:
                    this.inflater = new Inflater(true);
                    this.crc = new CRC32();
                    break;
                case 2:
                    this.inflater = new Inflater(true);
                    this.crc = null;
                    break;
                case 3:
                    this.inflater = new Inflater();
                    this.crc = null;
                    break;
                case 4:
                    this.decideZlibOrNone = true;
                    this.crc = null;
                    break;
                default:
                    throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
            }
            this.dictionary = dictionary2;
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (this.finished) {
            in.skipBytes(in.readableBytes());
            return;
        }
        int readableBytes = in.readableBytes();
        if (readableBytes != 0) {
            boolean z = false;
            if (this.decideZlibOrNone) {
                if (readableBytes >= 2) {
                    this.inflater = new Inflater(!looksLikeZlib(in.getShort(in.readerIndex())));
                    this.decideZlibOrNone = false;
                } else {
                    return;
                }
            }
            if (this.crc != null) {
                switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[this.gzipState.ordinal()]) {
                    case 1:
                        if (readGZIPFooter(in)) {
                            this.finished = true;
                            return;
                        }
                        return;
                    default:
                        if (this.gzipState == GzipState.HEADER_END || readGZIPHeader(in)) {
                            readableBytes = in.readableBytes();
                            break;
                        } else {
                            return;
                        }
                        break;
                }
            }
            if (in.hasArray()) {
                this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), readableBytes);
            } else {
                byte[] array = new byte[readableBytes];
                in.getBytes(in.readerIndex(), array);
                this.inflater.setInput(array);
            }
            ByteBuf decompressed = ctx.alloc().heapBuffer(this.inflater.getRemaining() << 1);
            boolean readFooter = false;
            while (true) {
                try {
                    if (!this.inflater.needsInput()) {
                        byte[] outArray = decompressed.array();
                        int writerIndex = decompressed.writerIndex();
                        int outIndex = decompressed.arrayOffset() + writerIndex;
                        int outputLength = this.inflater.inflate(outArray, outIndex, decompressed.writableBytes());
                        if (outputLength > 0) {
                            decompressed.writerIndex(writerIndex + outputLength);
                            CRC32 crc32 = this.crc;
                            if (crc32 != null) {
                                crc32.update(outArray, outIndex, outputLength);
                            }
                        } else if (this.inflater.needsDictionary()) {
                            byte[] bArr = this.dictionary;
                            if (bArr != null) {
                                this.inflater.setDictionary(bArr);
                            } else {
                                throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                            }
                        }
                        if (!this.inflater.finished()) {
                            decompressed.ensureWritable(this.inflater.getRemaining() << 1);
                        } else if (this.crc == null) {
                            this.finished = true;
                        } else {
                            readFooter = true;
                        }
                    }
                } catch (DataFormatException e) {
                    throw new DecompressionException("decompression failure", e);
                } catch (Throwable th) {
                    if (decompressed.isReadable()) {
                        out.add(decompressed);
                    } else {
                        decompressed.release();
                    }
                    throw th;
                }
            }
            in.skipBytes(readableBytes - this.inflater.getRemaining());
            if (readFooter) {
                this.gzipState = GzipState.FOOTER_START;
                if (readGZIPFooter(in)) {
                    if (!this.decompressConcatenated) {
                        z = true;
                    }
                    this.finished = z;
                    if (!this.finished) {
                        this.inflater.reset();
                        this.crc.reset();
                        this.gzipState = GzipState.HEADER_START;
                    }
                }
            }
            if (decompressed.isReadable()) {
                out.add(decompressed);
            } else {
                decompressed.release();
            }
        }
    }

    /* renamed from: io.netty.handler.codec.compression.JdkZlibDecoder$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper;

        static {
            int[] iArr = new int[GzipState.values().length];
            $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState = iArr;
            try {
                iArr[GzipState.FOOTER_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.HEADER_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.FLG_READ.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.XLEN_READ.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.SKIP_FNAME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.SKIP_COMMENT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.PROCESS_FHCRC.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState[GzipState.HEADER_END.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            int[] iArr2 = new int[ZlibWrapper.values().length];
            $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = iArr2;
            try {
                iArr2[ZlibWrapper.GZIP.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB_OR_NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void handlerRemoved0(ChannelHandlerContext ctx) {
        super.handlerRemoved0(ctx);
        Inflater inflater2 = this.inflater;
        if (inflater2 != null) {
            inflater2.end();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0090, code lost:
        if ((r9.flags & 4) == 0) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0096, code lost:
        if (r10.readableBytes() >= 2) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0098, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0099, code lost:
        r0 = r10.readUnsignedByte();
        r5 = r10.readUnsignedByte();
        r9.crc.update(r0);
        r9.crc.update(r5);
        r9.xlen |= (r0 << 8) | r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00b3, code lost:
        r9.gzipState = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ba, code lost:
        if (r9.xlen == -1) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00bc, code lost:
        r0 = r10.readableBytes();
        r5 = r9.xlen;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c2, code lost:
        if (r0 >= r5) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c4, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00c5, code lost:
        r0 = new byte[r5];
        r10.readBytes(r0);
        r9.crc.update(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cf, code lost:
        r9.gzipState = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d6, code lost:
        if ((r9.flags & 8) == 0) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00dc, code lost:
        if (r10.isReadable() != false) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00de, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00df, code lost:
        r0 = r10.readUnsignedByte();
        r9.crc.update(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e8, code lost:
        if (r0 != 0) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ef, code lost:
        if (r10.isReadable() != 0) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00f1, code lost:
        r9.gzipState = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f9, code lost:
        if ((r9.flags & 16) == 0) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ff, code lost:
        if (r10.isReadable() != false) goto L_0x0102;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0101, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0102, code lost:
        r0 = r10.readUnsignedByte();
        r9.crc.update(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x010b, code lost:
        if (r0 != 0) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0112, code lost:
        if (r10.isReadable() != 0) goto L_0x0102;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0114, code lost:
        r9.gzipState = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x011b, code lost:
        if ((r9.flags & 2) == 0) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0121, code lost:
        if (r10.readableBytes() >= 4) goto L_0x0124;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0123, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0124, code lost:
        verifyCrc(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0127, code lost:
        r9.crc.reset();
        r9.gzipState = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean readGZIPHeader(io.netty.buffer.ByteBuf r10) {
        /*
            r9 = this;
            int[] r0 = io.netty.handler.codec.compression.JdkZlibDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r1 = r9.gzipState
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 4
            r3 = 8
            r4 = 0
            switch(r0) {
                case 2: goto L_0x0018;
                case 3: goto L_0x008d;
                case 4: goto L_0x00b7;
                case 5: goto L_0x00d3;
                case 6: goto L_0x00f5;
                case 7: goto L_0x0118;
                case 8: goto L_0x0130;
                default: goto L_0x0012;
            }
        L_0x0012:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x0018:
            int r0 = r10.readableBytes()
            r5 = 10
            if (r0 >= r5) goto L_0x0021
            return r4
        L_0x0021:
            byte r0 = r10.readByte()
            byte r5 = r10.readByte()
            r6 = 31
            if (r0 != r6) goto L_0x0156
            java.util.zip.CRC32 r6 = r9.crc
            r6.update(r0)
            java.util.zip.CRC32 r6 = r9.crc
            r6.update(r5)
            short r6 = r10.readUnsignedByte()
            if (r6 != r3) goto L_0x013a
            java.util.zip.CRC32 r7 = r9.crc
            r7.update(r6)
            short r7 = r10.readUnsignedByte()
            r9.flags = r7
            java.util.zip.CRC32 r8 = r9.crc
            r8.update(r7)
            int r7 = r9.flags
            r7 = r7 & 224(0xe0, float:3.14E-43)
            if (r7 != 0) goto L_0x0132
            java.util.zip.CRC32 r7 = r9.crc
            byte r8 = r10.readByte()
            r7.update(r8)
            java.util.zip.CRC32 r7 = r9.crc
            byte r8 = r10.readByte()
            r7.update(r8)
            java.util.zip.CRC32 r7 = r9.crc
            byte r8 = r10.readByte()
            r7.update(r8)
            java.util.zip.CRC32 r7 = r9.crc
            byte r8 = r10.readByte()
            r7.update(r8)
            java.util.zip.CRC32 r7 = r9.crc
            short r8 = r10.readUnsignedByte()
            r7.update(r8)
            java.util.zip.CRC32 r7 = r9.crc
            short r8 = r10.readUnsignedByte()
            r7.update(r8)
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.FLG_READ
            r9.gzipState = r7
        L_0x008d:
            int r0 = r9.flags
            r0 = r0 & r2
            if (r0 == 0) goto L_0x00b3
            int r0 = r10.readableBytes()
            if (r0 >= r1) goto L_0x0099
            return r4
        L_0x0099:
            short r0 = r10.readUnsignedByte()
            short r5 = r10.readUnsignedByte()
            java.util.zip.CRC32 r6 = r9.crc
            r6.update(r0)
            java.util.zip.CRC32 r6 = r9.crc
            r6.update(r5)
            int r6 = r9.xlen
            int r7 = r0 << 8
            r7 = r7 | r5
            r6 = r6 | r7
            r9.xlen = r6
        L_0x00b3:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ
            r9.gzipState = r0
        L_0x00b7:
            int r0 = r9.xlen
            r5 = -1
            if (r0 == r5) goto L_0x00cf
            int r0 = r10.readableBytes()
            int r5 = r9.xlen
            if (r0 >= r5) goto L_0x00c5
            return r4
        L_0x00c5:
            byte[] r0 = new byte[r5]
            r10.readBytes((byte[]) r0)
            java.util.zip.CRC32 r5 = r9.crc
            r5.update(r0)
        L_0x00cf:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME
            r9.gzipState = r0
        L_0x00d3:
            int r0 = r9.flags
            r0 = r0 & r3
            if (r0 == 0) goto L_0x00f1
            boolean r0 = r10.isReadable()
            if (r0 != 0) goto L_0x00df
            return r4
        L_0x00df:
            short r0 = r10.readUnsignedByte()
            java.util.zip.CRC32 r3 = r9.crc
            r3.update(r0)
            if (r0 != 0) goto L_0x00eb
            goto L_0x00f1
        L_0x00eb:
            boolean r0 = r10.isReadable()
            if (r0 != 0) goto L_0x00df
        L_0x00f1:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT
            r9.gzipState = r0
        L_0x00f5:
            int r0 = r9.flags
            r0 = r0 & 16
            if (r0 == 0) goto L_0x0114
            boolean r0 = r10.isReadable()
            if (r0 != 0) goto L_0x0102
            return r4
        L_0x0102:
            short r0 = r10.readUnsignedByte()
            java.util.zip.CRC32 r3 = r9.crc
            r3.update(r0)
            if (r0 != 0) goto L_0x010e
            goto L_0x0114
        L_0x010e:
            boolean r0 = r10.isReadable()
            if (r0 != 0) goto L_0x0102
        L_0x0114:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC
            r9.gzipState = r0
        L_0x0118:
            int r0 = r9.flags
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0127
            int r0 = r10.readableBytes()
            if (r0 >= r2) goto L_0x0124
            return r4
        L_0x0124:
            r9.verifyCrc(r10)
        L_0x0127:
            java.util.zip.CRC32 r0 = r9.crc
            r0.reset()
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END
            r9.gzipState = r0
        L_0x0130:
            r0 = 1
            return r0
        L_0x0132:
            io.netty.handler.codec.compression.DecompressionException r1 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "Reserved flags are set in the GZIP header"
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x013a:
            io.netty.handler.codec.compression.DecompressionException r1 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unsupported compression method "
            r2.append(r3)
            r2.append(r6)
            java.lang.String r3 = " in the GZIP header"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x0156:
            io.netty.handler.codec.compression.DecompressionException r1 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r2 = "Input is not in the GZIP format"
            r1.<init>((java.lang.String) r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.JdkZlibDecoder.readGZIPHeader(io.netty.buffer.ByteBuf):boolean");
    }

    private boolean readGZIPFooter(ByteBuf buf) {
        if (buf.readableBytes() < 8) {
            return false;
        }
        verifyCrc(buf);
        int dataLength = 0;
        for (int i = 0; i < 4; i++) {
            dataLength |= buf.readUnsignedByte() << (i * 8);
        }
        int readLength = this.inflater.getTotalOut();
        if (dataLength == readLength) {
            return true;
        }
        throw new DecompressionException("Number of bytes mismatch. Expected: " + dataLength + ", Got: " + readLength);
    }

    private void verifyCrc(ByteBuf in) {
        long crcValue = 0;
        for (int i = 0; i < 4; i++) {
            crcValue |= ((long) in.readUnsignedByte()) << (i * 8);
        }
        long readCrc = this.crc.getValue();
        if (crcValue != readCrc) {
            throw new DecompressionException("CRC value mismatch. Expected: " + crcValue + ", Got: " + readCrc);
        }
    }

    private static boolean looksLikeZlib(short cmf_flg) {
        return (cmf_flg & 30720) == 30720 && cmf_flg % 31 == 0;
    }
}
