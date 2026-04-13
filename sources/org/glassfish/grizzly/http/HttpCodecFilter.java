package org.glassfish.grizzly.http;

import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.CacheableDataChunk;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.ssl.SSLUtils;
import org.glassfish.grizzly.utils.ArraySet;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpCodecFilter extends HttpBaseFilter implements MonitoringAware<HttpProbe> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] CHUNKED_ENCODING_BYTES = "chunked".getBytes(Charsets.ASCII_CHARSET);
    protected static final byte[] CLOSE_BYTES = {99, 108, 111, 115, 101};
    static final byte[] COLON_BYTES = {58, 32};
    static final byte[] CRLF_BYTES = {13, 10};
    public static final int DEFAULT_MAX_HTTP_PACKET_HEADER_SIZE = 8192;
    protected static final byte[] KEEPALIVE_BYTES = {107, 101, 101, 112, 45, 97, 108, 105, 118, 101};
    private static final Logger LOGGER = Grizzly.logger(HttpCodecFilter.class);
    protected final boolean chunkingEnabled;
    protected final ArraySet<ContentEncoding> contentEncodings = new ArraySet<>(ContentEncoding.class);
    protected final int maxHeadersSize;
    protected long maxPayloadRemainderToSkip = -1;
    protected final DefaultMonitoringConfig<HttpProbe> monitoringConfig = new DefaultMonitoringConfig<HttpProbe>(HttpProbe.class) {
        public Object createManagementObject() {
            return HttpCodecFilter.this.createJmxManagementObject();
        }
    };
    protected boolean preserveHeaderCase = Boolean.parseBoolean(System.getProperty("org.glassfish.grizzly.http.PRESERVE_HEADER_CASE", Bugly.SDK_IS_DEV));
    private boolean removeHandledContentEncodingHeaders = false;
    private final ArraySet<TransferEncoding> transferEncodings;

    /* access modifiers changed from: package-private */
    public abstract boolean decodeInitialLineFromBuffer(FilterChainContext filterChainContext, HttpPacketParsing httpPacketParsing, HeaderParsingState headerParsingState, Buffer buffer);

    /* access modifiers changed from: package-private */
    public abstract boolean decodeInitialLineFromBytes(FilterChainContext filterChainContext, HttpPacketParsing httpPacketParsing, HeaderParsingState headerParsingState, byte[] bArr, int i);

    /* access modifiers changed from: package-private */
    public abstract Buffer encodeInitialLine(HttpPacket httpPacket, Buffer buffer, MemoryManager memoryManager);

    /* access modifiers changed from: protected */
    public abstract void onHttpContentEncoded(HttpContent httpContent, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onHttpContentError(HttpHeader httpHeader, FilterChainContext filterChainContext, Throwable th);

    /* access modifiers changed from: protected */
    public abstract void onHttpContentParsed(HttpContent httpContent, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onHttpHeaderError(HttpHeader httpHeader, FilterChainContext filterChainContext, Throwable th);

    /* access modifiers changed from: protected */
    public abstract boolean onHttpHeaderParsed(HttpHeader httpHeader, Buffer buffer, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onHttpHeadersEncoded(HttpHeader httpHeader, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onHttpHeadersParsed(HttpHeader httpHeader, MimeHeaders mimeHeaders, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract boolean onHttpPacketParsed(HttpHeader httpHeader, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onInitialLineEncoded(HttpHeader httpHeader, FilterChainContext filterChainContext);

    /* access modifiers changed from: protected */
    public abstract void onInitialLineParsed(HttpHeader httpHeader, FilterChainContext filterChainContext);

    public HttpCodecFilter(boolean chunkingEnabled2, int maxHeadersSize2) {
        ArraySet<TransferEncoding> arraySet = new ArraySet<>(TransferEncoding.class);
        this.transferEncodings = arraySet;
        this.maxHeadersSize = maxHeadersSize2;
        this.chunkingEnabled = chunkingEnabled2;
        arraySet.addAll((T[]) new TransferEncoding[]{new FixedLengthTransferEncoding(), new ChunkedTransferEncoding(maxHeadersSize2)});
    }

    public long getMaxPayloadRemainderToSkip() {
        return this.maxPayloadRemainderToSkip;
    }

    public void setMaxPayloadRemainderToSkip(long maxPayloadRemainderToSkip2) {
        this.maxPayloadRemainderToSkip = maxPayloadRemainderToSkip2;
    }

    public boolean isPreserveHeaderCase() {
        return this.preserveHeaderCase;
    }

    public void setPreserveHeaderCase(boolean preserveHeaderCase2) {
        this.preserveHeaderCase = preserveHeaderCase2;
    }

    public TransferEncoding[] getTransferEncodings() {
        return (TransferEncoding[]) this.transferEncodings.obtainArrayCopy();
    }

    public void addTransferEncoding(TransferEncoding transferEncoding) {
        this.transferEncodings.add(transferEncoding);
    }

    public boolean removeTransferEncoding(TransferEncoding transferEncoding) {
        return this.transferEncodings.remove(transferEncoding);
    }

    public ContentEncoding[] getContentEncodings() {
        return (ContentEncoding[]) this.contentEncodings.obtainArrayCopy();
    }

    public void addContentEncoding(ContentEncoding contentEncoding) {
        this.contentEncodings.add(contentEncoding);
    }

    public boolean removeContentEncoding(ContentEncoding contentEncoding) {
        return this.contentEncodings.remove(contentEncoding);
    }

    /* access modifiers changed from: protected */
    public boolean isChunkingEnabled() {
        return this.chunkingEnabled;
    }

    public boolean isRemoveHandledContentEncodingHeaders() {
        return this.removeHandledContentEncodingHeaders;
    }

    public void setRemoveHandledContentEncodingHeaders(boolean removeHandledContentEncodingHeaders2) {
        this.removeHandledContentEncodingHeaders = removeHandledContentEncodingHeaders2;
    }

    public final NextAction handleRead(FilterChainContext ctx, HttpHeader httpHeader) {
        Buffer input = (Buffer) ctx.getMessage();
        Connection connection = ctx.getConnection();
        HttpProbeNotifier.notifyDataReceived(this, connection, input);
        HttpPacketParsing parsingState = httpHeader.getParsingState();
        boolean wasHeaderParsed = parsingState == null || parsingState.isHeaderParsed();
        if (!wasHeaderParsed) {
            if (parsingState != null) {
                try {
                    if (!decodeHttpPacket(ctx, parsingState, input)) {
                        return ctx.getStopAction(input);
                    }
                    int headerSizeInBytes = input.position();
                    if (httpHeader.isUpgrade()) {
                        onIncomingUpgrade(ctx, httpHeader);
                    }
                    if (!onHttpHeaderParsed(httpHeader, input, ctx)) {
                        parsingState.setHeaderParsed(true);
                        parsingState.getHeaderParsingState().recycle();
                        Buffer remainder = input.hasRemaining() ? input.split(input.position()) : Buffers.EMPTY_BUFFER;
                        httpHeader.setHeaderBuffer(input);
                        input = remainder;
                        if (httpHeader.isExpectContent()) {
                            setTransferEncodingOnParsing(httpHeader);
                            setContentEncodingsOnParsing(httpHeader);
                        }
                        HttpProbeNotifier.notifyHeaderParse(this, connection, httpHeader, headerSizeInBytes);
                    } else {
                        throw new IllegalStateException("Bad HTTP headers");
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.FINE, "Error parsing HTTP header", e);
                    HttpProbeNotifier.notifyProbesError(this, connection, httpHeader, e);
                    onHttpHeaderError(httpHeader, ctx, e);
                    NextAction suspendAction = ctx.getSuspendAction();
                    ctx.completeAndRecycle();
                    return suspendAction;
                }
            } else {
                throw new AssertionError();
            }
        }
        if (!httpHeader.isExpectContent()) {
            onHttpPacketParsed(httpHeader, ctx);
            HttpContent emptyContent = HttpContent.create(httpHeader, true);
            HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent);
            ctx.setMessage(emptyContent);
            if (input.remaining() > 0) {
                return ctx.getInvokeAction(input);
            }
            return ctx.getInvokeAction();
        } else if (httpHeader.isIgnoreContentModifiers()) {
            HttpContent message = HttpContent.create(httpHeader);
            message.setContent(input);
            ctx.setMessage(message);
            return ctx.getInvokeAction();
        } else {
            try {
                if (httpHeader.getTransferEncoding() != null) {
                    return decodeWithTransferEncoding(ctx, httpHeader, input, wasHeaderParsed);
                }
                if (input.hasRemaining()) {
                    HttpContent message2 = HttpContent.create(httpHeader);
                    message2.setContent(input);
                    HttpContent decodedContent = decodeContent(ctx, message2);
                    if (decodedContent != null) {
                        if (httpHeader.isSkipRemainder()) {
                            if (!checkRemainderOverflow(httpHeader, decodedContent.getContent().remaining())) {
                                httpHeader.getProcessingState().getHttpContext().close();
                            }
                            return ctx.getStopAction();
                        }
                        HttpProbeNotifier.notifyContentChunkParse(this, connection, decodedContent);
                        ctx.setMessage(decodedContent);
                        return ctx.getInvokeAction();
                    }
                }
                if (wasHeaderParsed) {
                    return ctx.getStopAction();
                }
                HttpContent emptyContent2 = HttpContent.create(httpHeader);
                HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent2);
                ctx.setMessage(emptyContent2);
                return ctx.getInvokeAction();
            } catch (Exception e2) {
                LOGGER.log(Level.FINE, "Error parsing HTTP payload", e2);
                httpHeader.getProcessingState().setError(true);
                HttpProbeNotifier.notifyProbesError(this, connection, httpHeader, e2);
                onHttpContentError(httpHeader, ctx, e2);
                onHttpPacketParsed(httpHeader, ctx);
                ctx.setMessage(HttpBrokenContent.builder(httpHeader).error(e2).build());
                return ctx.getInvokeAction();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean decodeHttpPacket(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer input) {
        if (input.hasArray()) {
            return decodeHttpPacketFromBytes(ctx, httpPacket, input);
        }
        return decodeHttpPacketFromBuffer(ctx, httpPacket, input);
    }

    /* access modifiers changed from: protected */
    public boolean decodeHttpPacketFromBytes(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer inputBuffer) {
        HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
        int arrayOffset = inputBuffer.arrayOffset();
        parsingState.arrayOffset = arrayOffset;
        int end = arrayOffset + inputBuffer.limit();
        byte[] input = inputBuffer.array();
        switch (parsingState.state) {
            case 0:
                if (decodeInitialLineFromBytes(ctx, httpPacket, parsingState, input, end)) {
                    parsingState.state++;
                    break;
                } else {
                    parsingState.checkOverflow(inputBuffer.limit(), "HTTP packet intial line is too large");
                    return false;
                }
            case 1:
                break;
            case 2:
                break;
            default:
                throw new IllegalStateException();
        }
        if (!parseHeadersFromBytes((HttpHeader) httpPacket, httpPacket.getHeaders(), parsingState, input, end)) {
            parsingState.checkOverflow(inputBuffer.limit(), "HTTP packet header is too large");
            return false;
        }
        parsingState.state++;
        onHttpHeadersParsed((HttpHeader) httpPacket, httpPacket.getHeaders(), ctx);
        if (httpPacket.getHeaders().size() == 0) {
            ((HttpHeader) httpPacket).setExpectContent(false);
        }
        inputBuffer.position(parsingState.offset);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean parseHeadersFromBytes(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, byte[] input, int end) {
        do {
            if (parsingState.subState == 0) {
                int eol = HttpCodecUtils.checkEOL(parsingState, input, end);
                if (eol == 0) {
                    return true;
                }
                if (eol == -2) {
                    return false;
                }
            }
        } while (parseHeaderFromBytes(httpHeader, mimeHeaders, parsingState, input, end) != 0);
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r12, r11.offset + r0, r13, r1) - r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        if (r6 >= 0) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0034, code lost:
        r11.offset = r13 - r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0039, code lost:
        r11.subState++;
        r11.offset = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        if (r11.start != -1) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0044, code lost:
        r11.start = r6;
        r11.checkpoint = r6;
        r11.checkpoint2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        r6 = parseHeaderValue(r9, r11, r12, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        if (r6 != -1) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0052, code lost:
        if (r6 != -2) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0059, code lost:
        r11.subState = 0;
        r11.start = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0020, code lost:
        if (parseHeaderName(r9, r10, r11, r12, r13) != false) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r11.subState++;
        r11.start = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parseHeaderFromBytes(org.glassfish.grizzly.http.HttpHeader r9, org.glassfish.grizzly.http.util.MimeHeaders r10, org.glassfish.grizzly.http.HttpCodecFilter.HeaderParsingState r11, byte[] r12, int r13) {
        /*
            r8 = this;
            int r0 = r11.arrayOffset
            int r1 = r11.packetLimit
            int r1 = r1 + r0
        L_0x0005:
            int r2 = r11.subState
            r3 = 0
            r4 = 1
            r5 = -1
            switch(r2) {
                case 0: goto L_0x0013;
                case 1: goto L_0x001c;
                case 2: goto L_0x002a;
                case 3: goto L_0x004a;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            throw r3
        L_0x0013:
            int r6 = r11.offset
            r11.start = r6
            int r6 = r11.subState
            int r6 = r6 + r4
            r11.subState = r6
        L_0x001c:
            boolean r6 = r8.parseHeaderName(r9, r10, r11, r12, r13)
            if (r6 != 0) goto L_0x0023
            return r3
        L_0x0023:
            int r6 = r11.subState
            int r6 = r6 + r4
            r11.subState = r6
            r11.start = r5
        L_0x002a:
            int r6 = r11.offset
            int r6 = r6 + r0
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r12, r6, r13, r1)
            int r6 = r6 - r0
            if (r6 >= 0) goto L_0x0039
            int r4 = r13 - r0
            r11.offset = r4
            return r3
        L_0x0039:
            int r7 = r11.subState
            int r7 = r7 + r4
            r11.subState = r7
            r11.offset = r6
            int r7 = r11.start
            if (r7 != r5) goto L_0x004a
            r11.start = r6
            r11.checkpoint = r6
            r11.checkpoint2 = r6
        L_0x004a:
            int r6 = parseHeaderValue(r9, r11, r12, r13)
            if (r6 != r5) goto L_0x0051
            return r3
        L_0x0051:
            r7 = -2
            if (r6 != r7) goto L_0x0059
            r3 = 2
            r11.subState = r3
            goto L_0x0005
        L_0x0059:
            r11.subState = r3
            r11.start = r5
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpCodecFilter.parseHeaderFromBytes(org.glassfish.grizzly.http.HttpHeader, org.glassfish.grizzly.http.util.MimeHeaders, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, byte[], int):boolean");
    }

    /* access modifiers changed from: protected */
    public boolean parseHeaderName(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, byte[] input, int end) {
        int arrayOffs = parsingState.arrayOffset;
        int limit = Math.min(end, parsingState.packetLimit + arrayOffs);
        int start = parsingState.start + arrayOffs;
        int offset = parsingState.offset + arrayOffs;
        while (offset < limit) {
            byte b = input[offset];
            if (b == 58) {
                parsingState.headerValueStorage = mimeHeaders.addValue(input, start, offset - start);
                parsingState.offset = (offset + 1) - arrayOffs;
                finalizeKnownHeaderNames(httpHeader, parsingState, input, start, offset);
                return true;
            }
            if (b >= 65 && b <= 90) {
                if (!this.preserveHeaderCase) {
                    b = (byte) (b + 32);
                }
                input[offset] = b;
            }
            offset++;
        }
        parsingState.offset = offset - arrayOffs;
        return false;
    }

    protected static int parseHeaderValue(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int end) {
        int arrayOffs = parsingState.arrayOffset;
        int limit = Math.min(end, parsingState.packetLimit + arrayOffs);
        int offset = parsingState.offset + arrayOffs;
        boolean hasShift = offset != parsingState.checkpoint + arrayOffs;
        while (offset < limit) {
            byte b = input[offset];
            if (b != 13) {
                if (b == 10) {
                    if (offset + 1 < limit) {
                        byte b2 = input[offset + 1];
                        if (b2 == 32 || b2 == 9) {
                            int i = parsingState.checkpoint;
                            parsingState.checkpoint = i + 1;
                            input[i + arrayOffs] = b2;
                            parsingState.offset = (offset + 2) - arrayOffs;
                            return -2;
                        }
                        parsingState.offset = (offset + 1) - arrayOffs;
                        finalizeKnownHeaderValues(httpHeader, parsingState, input, parsingState.start + arrayOffs, parsingState.checkpoint2 + arrayOffs);
                        parsingState.headerValueStorage.setBytes(input, parsingState.start + arrayOffs, parsingState.checkpoint2 + arrayOffs);
                        return 0;
                    }
                    parsingState.offset = offset - arrayOffs;
                    return -1;
                } else if (b != 32) {
                    if (hasShift) {
                        int i2 = parsingState.checkpoint;
                        parsingState.checkpoint = i2 + 1;
                        input[i2 + arrayOffs] = b;
                    } else {
                        parsingState.checkpoint++;
                    }
                    parsingState.checkpoint2 = parsingState.checkpoint;
                } else if (hasShift) {
                    int i3 = parsingState.checkpoint;
                    parsingState.checkpoint = i3 + 1;
                    input[i3 + arrayOffs] = b;
                } else {
                    parsingState.checkpoint++;
                }
            }
            offset++;
        }
        parsingState.offset = offset - arrayOffs;
        return -1;
    }

    private static void finalizeKnownHeaderNames(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int start, int end) {
        int size = end - start;
        Header header = Header.ContentLength;
        if (size != header.getLowerCaseBytes().length) {
            Header header2 = Header.TransferEncoding;
            if (size != header2.getLowerCaseBytes().length) {
                Header header3 = Header.Upgrade;
                if (size != header3.getLowerCaseBytes().length) {
                    Header header4 = Header.Expect;
                    if (size == header4.getLowerCaseBytes().length && ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, header4.getLowerCaseBytes())) {
                        ((HttpRequestPacket) httpHeader).requiresAcknowledgement(true);
                    }
                } else if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, header3.getLowerCaseBytes())) {
                    parsingState.isUpgradeHeader = true;
                }
            } else if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, header2.getLowerCaseBytes())) {
                parsingState.isTransferEncodingHeader = true;
            }
        } else if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, header.getLowerCaseBytes())) {
            parsingState.isContentLengthHeader = true;
        }
    }

    private static void finalizeKnownHeaderValues(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int start, int end) {
        if (parsingState.isContentLengthHeader) {
            if (httpHeader.isChunked()) {
                parsingState.isContentLengthHeader = false;
                return;
            }
            long contentLengthLong = Ascii.parseLong(input, start, end - start);
            int i = parsingState.contentLengthHeadersCount;
            parsingState.contentLengthHeadersCount = i + 1;
            if (i == 0) {
                httpHeader.setContentLengthLong(contentLengthLong);
            } else if (httpHeader.getContentLength() != contentLengthLong) {
                parsingState.contentLengthsDiffer = true;
            }
            parsingState.isContentLengthHeader = false;
        } else if (parsingState.isTransferEncodingHeader) {
            int i2 = end - start;
            byte[] bArr = CHUNKED_ENCODING_BYTES;
            if (i2 >= bArr.length && ByteChunk.equalsIgnoreCaseLowerCase(input, start, bArr.length + start, bArr)) {
                httpHeader.setContentLengthLong(-1);
                httpHeader.setChunked(true);
            }
            parsingState.isTransferEncodingHeader = false;
        } else if (parsingState.isUpgradeHeader) {
            httpHeader.getUpgradeDC().setBytes(input, start, end);
            parsingState.isUpgradeHeader = false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean decodeHttpPacketFromBuffer(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer input) {
        HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
        switch (parsingState.state) {
            case 0:
                if (decodeInitialLineFromBuffer(ctx, httpPacket, parsingState, input)) {
                    parsingState.state++;
                    break;
                } else {
                    parsingState.checkOverflow(input.limit(), "HTTP packet intial line is too large");
                    return false;
                }
            case 1:
                break;
            case 2:
                break;
            default:
                throw new IllegalStateException();
        }
        if (!parseHeadersFromBuffer((HttpHeader) httpPacket, httpPacket.getHeaders(), parsingState, input)) {
            parsingState.checkOverflow(input.limit(), "HTTP packet header is too large");
            return false;
        }
        parsingState.state++;
        onHttpHeadersParsed((HttpHeader) httpPacket, httpPacket.getHeaders(), ctx);
        if (httpPacket.getHeaders().size() == 0) {
            ((HttpHeader) httpPacket).setExpectContent(false);
        }
        input.position(parsingState.offset);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean parseHeadersFromBuffer(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, Buffer input) {
        do {
            if (parsingState.subState == 0) {
                int eol = HttpCodecUtils.checkEOL(parsingState, input);
                if (eol == 0) {
                    return true;
                }
                if (eol == -2) {
                    return false;
                }
            }
        } while (parseHeaderFromBuffer(httpHeader, mimeHeaders, parsingState, input) != 0);
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002d, code lost:
        if (r4 != -1) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        r9.offset = r10.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r9.subState++;
        r9.offset = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        if (r9.start != -1) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r9.start = r4;
        r9.checkpoint = r4;
        r9.checkpoint2 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
        r4 = parseHeaderValue(r7, r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004b, code lost:
        if (r4 != -1) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004d, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
        if (r4 != -2) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        r9.subState = 0;
        r9.start = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
        if (parseHeaderName(r7, r8, r9, r10) != false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001d, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        r9.subState++;
        r9.start = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0025, code lost:
        r4 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r10, r9.offset, r9.packetLimit);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parseHeaderFromBuffer(org.glassfish.grizzly.http.HttpHeader r7, org.glassfish.grizzly.http.util.MimeHeaders r8, org.glassfish.grizzly.http.HttpCodecFilter.HeaderParsingState r9, org.glassfish.grizzly.Buffer r10) {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r9.subState
            r1 = 0
            r2 = 1
            r3 = -1
            switch(r0) {
                case 0: goto L_0x000e;
                case 1: goto L_0x0017;
                case 2: goto L_0x0025;
                case 3: goto L_0x0047;
                default: goto L_0x0008;
            }
        L_0x0008:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x000e:
            int r4 = r9.offset
            r9.start = r4
            int r4 = r9.subState
            int r4 = r4 + r2
            r9.subState = r4
        L_0x0017:
            boolean r4 = r6.parseHeaderName(r7, r8, r9, r10)
            if (r4 != 0) goto L_0x001e
            return r1
        L_0x001e:
            int r4 = r9.subState
            int r4 = r4 + r2
            r9.subState = r4
            r9.start = r3
        L_0x0025:
            int r4 = r9.offset
            int r5 = r9.packetLimit
            int r4 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r10, r4, r5)
            if (r4 != r3) goto L_0x0036
            int r2 = r10.limit()
            r9.offset = r2
            return r1
        L_0x0036:
            int r5 = r9.subState
            int r5 = r5 + r2
            r9.subState = r5
            r9.offset = r4
            int r5 = r9.start
            if (r5 != r3) goto L_0x0047
            r9.start = r4
            r9.checkpoint = r4
            r9.checkpoint2 = r4
        L_0x0047:
            int r4 = parseHeaderValue(r7, r9, r10)
            if (r4 != r3) goto L_0x004e
            return r1
        L_0x004e:
            r5 = -2
            if (r4 != r5) goto L_0x0056
            r1 = 2
            r9.subState = r1
            goto L_0x0000
        L_0x0056:
            r9.subState = r1
            r9.start = r3
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpCodecFilter.parseHeaderFromBuffer(org.glassfish.grizzly.http.HttpHeader, org.glassfish.grizzly.http.util.MimeHeaders, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, org.glassfish.grizzly.Buffer):boolean");
    }

    /* access modifiers changed from: protected */
    public boolean parseHeaderName(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, Buffer input) {
        int limit = Math.min(input.limit(), parsingState.packetLimit);
        int start = parsingState.start;
        int offset = parsingState.offset;
        while (offset < limit) {
            byte b = input.get(offset);
            if (b == 58) {
                parsingState.headerValueStorage = mimeHeaders.addValue(input, start, offset - start);
                parsingState.offset = offset + 1;
                finalizeKnownHeaderNames(httpHeader, parsingState, input, start, offset);
                return true;
            }
            if (b >= 65 && b <= 90) {
                if (!this.preserveHeaderCase) {
                    b = (byte) (b + 32);
                }
                input.put(offset, b);
            }
            offset++;
        }
        parsingState.offset = offset;
        return false;
    }

    protected static int parseHeaderValue(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input) {
        int limit = Math.min(input.limit(), parsingState.packetLimit);
        int offset = parsingState.offset;
        boolean hasShift = offset != parsingState.checkpoint;
        while (offset < limit) {
            byte b = input.get(offset);
            if (b != 13) {
                if (b == 10) {
                    if (offset + 1 < limit) {
                        byte b2 = input.get(offset + 1);
                        if (b2 == 32 || b2 == 9) {
                            int i = parsingState.checkpoint;
                            parsingState.checkpoint = i + 1;
                            input.put(i, b2);
                            parsingState.offset = offset + 2;
                            return -2;
                        }
                        parsingState.offset = offset + 1;
                        finalizeKnownHeaderValues(httpHeader, parsingState, input, parsingState.start, parsingState.checkpoint2);
                        parsingState.headerValueStorage.setBuffer(input, parsingState.start, parsingState.checkpoint2);
                        return 0;
                    }
                    parsingState.offset = offset;
                    return -1;
                } else if (b != 32) {
                    if (hasShift) {
                        int i2 = parsingState.checkpoint;
                        parsingState.checkpoint = i2 + 1;
                        input.put(i2, b);
                    } else {
                        parsingState.checkpoint++;
                    }
                    parsingState.checkpoint2 = parsingState.checkpoint;
                } else if (hasShift) {
                    int i3 = parsingState.checkpoint;
                    parsingState.checkpoint = i3 + 1;
                    input.put(i3, b);
                } else {
                    parsingState.checkpoint++;
                }
            }
            offset++;
        }
        parsingState.offset = offset;
        return -1;
    }

    private static void finalizeKnownHeaderNames(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input, int start, int end) {
        int size = end - start;
        Header header = Header.ContentLength;
        if (size != header.getLowerCaseBytes().length) {
            Header header2 = Header.TransferEncoding;
            if (size != header2.getLowerCaseBytes().length) {
                Header header3 = Header.Upgrade;
                if (size != header3.getLowerCaseBytes().length) {
                    Header header4 = Header.Expect;
                    if (size == header4.getLowerCaseBytes().length && BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, header4.getLowerCaseBytes())) {
                        ((HttpRequestPacket) httpHeader).requiresAcknowledgement(true);
                    }
                } else if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, header3.getLowerCaseBytes())) {
                    parsingState.isUpgradeHeader = true;
                }
            } else if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, header2.getLowerCaseBytes())) {
                parsingState.isTransferEncodingHeader = true;
            }
        } else if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, header.getLowerCaseBytes())) {
            parsingState.isContentLengthHeader = true;
        }
    }

    private static void finalizeKnownHeaderValues(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input, int start, int end) {
        if (parsingState.isContentLengthHeader) {
            if (httpHeader.isChunked()) {
                parsingState.isContentLengthHeader = false;
                return;
            }
            long contentLengthLong = Ascii.parseLong(input, start, end - start);
            int i = parsingState.contentLengthHeadersCount;
            parsingState.contentLengthHeadersCount = i + 1;
            if (i == 0) {
                httpHeader.setContentLengthLong(contentLengthLong);
            } else if (httpHeader.getContentLength() != contentLengthLong) {
                parsingState.contentLengthsDiffer = true;
            }
            parsingState.isContentLengthHeader = false;
        } else if (parsingState.isTransferEncodingHeader) {
            if (BufferChunk.startsWith(input, start, end, CHUNKED_ENCODING_BYTES)) {
                httpHeader.setContentLengthLong(-1);
                httpHeader.setChunked(true);
            }
            parsingState.isTransferEncodingHeader = false;
        } else if (parsingState.isUpgradeHeader) {
            httpHeader.getUpgradeDC().setBuffer(input, start, end);
            parsingState.isUpgradeHeader = false;
        }
    }

    private NextAction decodeWithTransferEncoding(FilterChainContext ctx, HttpHeader httpHeader, Buffer input, boolean wasHeaderParsed) {
        Connection connection = ctx.getConnection();
        ParsingResult result = parseWithTransferEncoding(ctx, httpHeader, input);
        HttpContent httpContent = result.getHttpContent();
        Buffer remainderBuffer = result.getRemainderBuffer();
        boolean sendHeaderUpstream = result.isSendHeaderUpstream();
        boolean hasRemainder = remainderBuffer != null && remainderBuffer.hasRemaining();
        result.recycle();
        boolean isLast = true ^ httpHeader.isExpectContent();
        Buffer buffer = null;
        if (httpContent != null) {
            if (httpContent.isLast()) {
                isLast = true;
                httpHeader.setExpectContent(false);
            }
            if (!httpHeader.isSkipRemainder()) {
                HttpContent decodedContent = decodeContent(ctx, httpContent);
                if (isLast) {
                    onHttpPacketParsed(httpHeader, ctx);
                }
                if (decodedContent != null) {
                    HttpProbeNotifier.notifyContentChunkParse(this, connection, decodedContent);
                    ctx.setMessage(decodedContent);
                    if (hasRemainder) {
                        buffer = remainderBuffer;
                    }
                    return ctx.getInvokeAction(buffer);
                } else if (hasRemainder) {
                    HttpContent emptyContent = HttpContent.create(httpHeader, isLast);
                    HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent);
                    ctx.setMessage(emptyContent);
                    return ctx.getInvokeAction(remainderBuffer);
                }
            } else if (isLast) {
                onHttpPacketParsed(httpHeader, ctx);
                if (!httpHeader.getProcessingState().isStayAlive()) {
                    httpHeader.getProcessingState().getHttpContext().close();
                    return ctx.getStopAction();
                } else if (remainderBuffer == null) {
                    return ctx.getStopAction();
                } else {
                    ctx.setMessage(remainderBuffer);
                    return ctx.getRerunFilterAction();
                }
            } else {
                if (!checkRemainderOverflow(httpHeader, httpContent.getContent().remaining())) {
                    httpHeader.getProcessingState().getHttpContext().close();
                } else if (remainderBuffer != null) {
                    ctx.setMessage(remainderBuffer);
                    return ctx.getRerunFilterAction();
                }
                return ctx.getStopAction();
            }
        }
        if (wasHeaderParsed && !isLast) {
            if (hasRemainder) {
                buffer = remainderBuffer;
            }
            return ctx.getStopAction(buffer);
        } else if (sendHeaderUpstream) {
            HttpContent emptyContent2 = HttpContent.create(httpHeader, isLast);
            HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent2);
            ctx.setMessage(emptyContent2);
            if (hasRemainder) {
                buffer = remainderBuffer;
            }
            return ctx.getInvokeAction(buffer);
        } else {
            if (hasRemainder) {
                buffer = remainderBuffer;
            }
            return ctx.getStopAction(buffer);
        }
    }

    /* access modifiers changed from: package-private */
    public final HttpContent decodeContent(FilterChainContext ctx, HttpContent httpContent) {
        if (!httpContent.getContent().hasRemaining()) {
            FilterChainContext filterChainContext = ctx;
        } else if (isResponseToHeadRequest(httpContent.getHttpHeader())) {
            FilterChainContext filterChainContext2 = ctx;
        } else {
            Connection connection = ctx.getConnection();
            MemoryManager memoryManager = connection.getMemoryManager();
            HttpHeader httpHeader = httpContent.getHttpHeader();
            ContentParsingState parsingState = ((HttpPacketParsing) httpHeader).getContentParsingState();
            List<ContentEncoding> encodings = httpHeader.getContentEncodings(true);
            int encodingsNum = encodings.size();
            HttpContent httpContent2 = httpContent;
            for (int i = 0; i < encodingsNum; i++) {
                ContentEncoding encoding = encodings.get(i);
                HttpProbeNotifier.notifyContentEncodingParse(this, connection, httpHeader, httpContent2.getContent(), encoding);
                Buffer oldRemainder = parsingState.removeContentDecodingRemainder(i);
                if (oldRemainder != null) {
                    httpContent2.setContent(Buffers.appendBuffers(memoryManager, oldRemainder, httpContent2.getContent()));
                }
                ParsingResult result = encoding.decode(connection, httpContent2);
                Buffer newRemainder = result.getRemainderBuffer();
                if (newRemainder != null) {
                    parsingState.setContentDecodingRemainder(i, newRemainder);
                }
                HttpContent decodedContent = result.getHttpContent();
                result.recycle();
                if (decodedContent == null) {
                    httpContent2.recycle();
                    return null;
                }
                HttpProbeNotifier.notifyContentEncodingParseResult(this, connection, httpHeader, decodedContent.getContent(), encoding);
                httpContent2 = decodedContent;
            }
            onHttpContentParsed(httpContent2, ctx);
            return httpContent2;
        }
        if (httpContent.isLast()) {
            return httpContent;
        }
        httpContent.recycle();
        return null;
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        if (!HttpPacket.isHttp(ctx.getMessage())) {
            return ctx.getInvokeAction();
        }
        HttpPacket input = (HttpPacket) ctx.getMessage();
        Connection connection = ctx.getConnection();
        try {
            Buffer output = encodeHttpPacket(ctx, input);
            if (output == null) {
                return ctx.getStopAction();
            }
            HttpProbeNotifier.notifyDataSent(this, connection, output);
            ctx.setMessage(output);
            return ctx.getInvokeAction();
        } catch (RuntimeException re) {
            HttpProbeNotifier.notifyProbesError(this, connection, input, re);
            throw re;
        }
    }

    /* access modifiers changed from: protected */
    public void onIncomingUpgrade(FilterChainContext ctx, HttpHeader httpHeader) {
        httpHeader.setIgnoreContentModifiers(true);
        ctx.notifyUpstream(HttpEvents.createIncomingUpgradeEvent(httpHeader));
    }

    /* access modifiers changed from: protected */
    public void onOutgoingUpgrade(FilterChainContext ctx, HttpHeader httpHeader) {
        httpHeader.setIgnoreContentModifiers(true);
        ctx.notifyUpstream(HttpEvents.createOutgoingUpgradeEvent(httpHeader));
    }

    /* access modifiers changed from: protected */
    public Buffer encodeHttpPacket(FilterChainContext ctx, HttpPacket input) {
        HttpHeader httpHeader;
        HttpContent httpContent;
        if (input.isHeader()) {
            httpContent = null;
            httpHeader = (HttpHeader) input;
        } else {
            httpContent = (HttpContent) input;
            httpHeader = httpContent.getHttpHeader();
        }
        return encodeHttpPacket(ctx, httpHeader, httpContent, false);
    }

    /* access modifiers changed from: protected */
    public final Buffer encodeHttpPacket(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent, boolean isContentAlreadyEncoded) {
        Connection connection = ctx.getConnection();
        MemoryManager memoryManager = ctx.getMemoryManager();
        Buffer encodedBuffer = null;
        if (!httpHeader.isCommitted()) {
            if (httpHeader.isUpgrade()) {
                onOutgoingUpgrade(ctx, httpHeader);
            }
            if (!httpHeader.isRequest()) {
                HttpResponsePacket response = (HttpResponsePacket) httpHeader;
                if (response.isAcknowledgement()) {
                    Buffer encodedBuffer2 = encodeInitialLine(httpHeader, memoryManager.allocate(128), memoryManager);
                    byte[] bArr = CRLF_BYTES;
                    Buffer encodedBuffer3 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, encodedBuffer2, bArr), bArr);
                    onInitialLineEncoded(httpHeader, ctx);
                    encodedBuffer3.trim();
                    encodedBuffer3.allowBufferDispose(true);
                    HttpProbeNotifier.notifyHeaderSerialize(this, connection, httpHeader, encodedBuffer3);
                    response.acknowledged();
                    return encodedBuffer3;
                }
            }
            if (httpHeader.isExpectContent()) {
                setContentEncodingsOnSerializing(httpHeader);
                setTransferEncodingOnSerializing(ctx, httpHeader, httpContent);
            }
            Buffer encodedBuffer4 = encodeInitialLine(httpHeader, memoryManager.allocateAtLeast(2048), memoryManager);
            byte[] bArr2 = CRLF_BYTES;
            Buffer encodedBuffer5 = HttpCodecUtils.put(memoryManager, encodedBuffer4, bArr2);
            onInitialLineEncoded(httpHeader, ctx);
            Buffer encodedBuffer6 = encodeMimeHeaders(memoryManager, encodeKnownHeaders(memoryManager, encodedBuffer5, httpHeader), httpHeader.getHeaders(), httpHeader.getTempHeaderEncodingBuffer());
            onHttpHeadersEncoded(httpHeader, ctx);
            encodedBuffer = HttpCodecUtils.put(memoryManager, encodedBuffer6, bArr2);
            encodedBuffer.trim();
            encodedBuffer.allowBufferDispose(true);
            httpHeader.setCommitted(true);
            HttpProbeNotifier.notifyHeaderSerialize(this, connection, httpHeader, encodedBuffer);
        }
        if (httpContent != null && httpHeader.isExpectContent()) {
            HttpProbeNotifier.notifyContentChunkSerialize(this, connection, httpContent);
            HttpContent encodedHttpContent = isContentAlreadyEncoded ? httpContent : encodeContent(connection, httpContent);
            if (encodedHttpContent == null) {
                return encodedBuffer;
            }
            Buffer content = serializeWithTransferEncoding(ctx, encodedHttpContent, httpHeader.getTransferEncoding());
            onHttpContentEncoded(encodedHttpContent, ctx);
            if (content != null && content.hasRemaining()) {
                encodedBuffer = Buffers.appendBuffers(memoryManager, encodedBuffer, content);
            }
            if (encodedBuffer != null && encodedBuffer.isComposite()) {
                encodedBuffer.allowBufferDispose(true);
                ((CompositeBuffer) encodedBuffer).disposeOrder(CompositeBuffer.DisposeOrder.FIRST_TO_LAST);
            }
        }
        return encodedBuffer;
    }

    protected static Buffer encodeKnownHeaders(MemoryManager memoryManager, Buffer buffer, HttpHeader httpHeader) {
        CacheableDataChunk name = CacheableDataChunk.create();
        CacheableDataChunk value = CacheableDataChunk.create();
        if (true ^ httpHeader.getContentEncodings(true).isEmpty()) {
            buffer = encodeContentEncodingHeader(memoryManager, buffer, httpHeader, name, value);
        }
        name.recycle();
        value.recycle();
        httpHeader.makeUpgradeHeader();
        return buffer;
    }

    private static Buffer encodeContentEncodingHeader(MemoryManager memoryManager, Buffer buffer, HttpHeader httpHeader, CacheableDataChunk name, CacheableDataChunk value) {
        List<ContentEncoding> packetContentEncodings = httpHeader.getContentEncodings(true);
        name.setBytes(Header.ContentEncoding.toByteArray());
        value.reset();
        httpHeader.extractContentEncoding(value);
        boolean needComma = true ^ value.isNull();
        byte[] tempBuffer = httpHeader.getTempHeaderEncodingBuffer();
        Buffer buffer2 = encodeMimeHeader(memoryManager, buffer, name, value, tempBuffer, false);
        for (ContentEncoding encoding : packetContentEncodings) {
            if (needComma) {
                buffer2 = HttpCodecUtils.put(memoryManager, buffer2, (byte) Constants.COMMA);
            }
            buffer2 = HttpCodecUtils.put(memoryManager, buffer2, tempBuffer, encoding.getName());
            needComma = true;
        }
        return HttpCodecUtils.put(memoryManager, buffer2, CRLF_BYTES);
    }

    protected static Buffer encodeMimeHeaders(MemoryManager memoryManager, Buffer buffer, MimeHeaders mimeHeaders, byte[] tempEncodingBuffer) {
        int mimeHeadersNum = mimeHeaders.size();
        for (int i = 0; i < mimeHeadersNum; i++) {
            if (!mimeHeaders.setSerialized(i, true)) {
                DataChunk value = mimeHeaders.getValue(i);
                if (!value.isNull()) {
                    buffer = encodeMimeHeader(memoryManager, buffer, mimeHeaders.getName(i), value, tempEncodingBuffer, true);
                }
            }
        }
        return buffer;
    }

    protected static Buffer encodeMimeHeader(MemoryManager memoryManager, Buffer buffer, DataChunk name, DataChunk value, byte[] tempBuffer, boolean encodeLastCRLF) {
        Buffer buffer2 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, buffer, tempBuffer, name), COLON_BYTES), tempBuffer, value);
        if (encodeLastCRLF) {
            return HttpCodecUtils.put(memoryManager, buffer2, CRLF_BYTES);
        }
        return buffer2;
    }

    /* access modifiers changed from: package-private */
    public final void setTransferEncodingOnParsing(HttpHeader httpHeader) {
        TransferEncoding[] encodings;
        if (!httpHeader.isIgnoreContentModifiers() && (encodings = (TransferEncoding[]) this.transferEncodings.getArray()) != null) {
            for (TransferEncoding encoding : encodings) {
                if (encoding.wantDecode(httpHeader)) {
                    httpHeader.setTransferEncoding(encoding);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void setTransferEncodingOnSerializing(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent) {
        TransferEncoding[] encodings;
        if (!httpHeader.isIgnoreContentModifiers() && (encodings = (TransferEncoding[]) this.transferEncodings.getArray()) != null) {
            for (TransferEncoding encoding : encodings) {
                if (encoding.wantEncode(httpHeader)) {
                    encoding.prepareSerialize(ctx, httpHeader, httpContent);
                    httpHeader.setTransferEncoding(encoding);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final HttpContent encodeContent(Connection connection, HttpContent httpContent) {
        HttpHeader httpHeader = httpContent.getHttpHeader();
        List<ContentEncoding> encodings = httpHeader.getContentEncodings(true);
        int len = encodings.size();
        for (int i = 0; i < len; i++) {
            ContentEncoding encoding = encodings.get(i);
            HttpProbeNotifier.notifyContentEncodingSerialize(this, connection, httpHeader, httpContent.getContent(), encoding);
            HttpContent encodedContent = encoding.encode(connection, httpContent);
            if (encodedContent == null) {
                httpContent.recycle();
                return null;
            }
            HttpProbeNotifier.notifyContentEncodingSerializeResult(this, connection, httpHeader, encodedContent.getContent(), encoding);
            httpContent = encodedContent;
        }
        return httpContent;
    }

    /* access modifiers changed from: package-private */
    public final void setContentEncodingsOnParsing(HttpHeader httpHeader) {
        DataChunk bc;
        int commaIdx;
        if (!httpHeader.isIgnoreContentModifiers() && (bc = httpHeader.getHeaders().getValue(Header.ContentEncoding)) != null) {
            List<ContentEncoding> encodings = httpHeader.getContentEncodings(true);
            int currentIdx = 0;
            int endMatchDecoded = -1;
            do {
                commaIdx = bc.indexOf((char) StringUtil.COMMA, currentIdx);
                int endMatch = commaIdx >= 0 ? commaIdx : bc.getLength();
                ContentEncoding ce = lookupContentEncoding(bc, currentIdx);
                if (ce != null && ce.wantDecode(httpHeader)) {
                    endMatchDecoded = endMatch;
                    encodings.add(0, ce);
                    currentIdx = commaIdx + 1;
                }
            } while (commaIdx >= 0);
            if (this.removeHandledContentEncodingHeaders) {
                if (endMatchDecoded < bc.getLength()) {
                    endMatchDecoded++;
                }
                bc.setStart(bc.getStart() + endMatchDecoded);
                if (bc.getLength() != 0) {
                    bc.trimLeft();
                }
                if (bc.getLength() == 0) {
                    httpHeader.getHeaders().removeHeader(Header.ContentEncoding);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void setContentEncodingsOnSerializing(HttpHeader httpHeader) {
        if (!httpHeader.isIgnoreContentModifiers() && !httpHeader.isContentEncodingsSelected()) {
            httpHeader.setContentEncodingsSelected(true);
            ContentEncoding[] encodingsLibrary = (ContentEncoding[]) this.contentEncodings.getArray();
            if (encodingsLibrary != null) {
                MimeHeaders headers = httpHeader.getHeaders();
                Header header = Header.ContentEncoding;
                DataChunk bc = headers.getValue(header);
                boolean isSomeEncodingApplied = bc != null && bc.getLength() > 0;
                if (!isSomeEncodingApplied || !bc.equals("identity")) {
                    List<ContentEncoding> httpPacketEncoders = httpHeader.getContentEncodings(true);
                    for (ContentEncoding encoding : encodingsLibrary) {
                        if ((!isSomeEncodingApplied || !lookupAlias(encoding, bc, 0)) && encoding.wantEncode(httpHeader)) {
                            httpPacketEncoders.add(encoding);
                        }
                    }
                    return;
                }
                httpHeader.getHeaders().removeHeader(header);
            }
        }
    }

    private ContentEncoding lookupContentEncoding(DataChunk bc, int startIdx) {
        ContentEncoding[] encodings = (ContentEncoding[]) this.contentEncodings.getArray();
        if (encodings == null) {
            return null;
        }
        for (ContentEncoding encoding : encodings) {
            if (lookupAlias(encoding, bc, startIdx)) {
                return encoding;
            }
        }
        return null;
    }

    private ParsingResult parseWithTransferEncoding(FilterChainContext ctx, HttpHeader httpHeader, Buffer input) {
        TransferEncoding encoding = httpHeader.getTransferEncoding();
        HttpProbeNotifier.notifyTransferEncodingParse(this, ctx.getConnection(), httpHeader, input, encoding);
        return encoding.parsePacket(ctx, httpHeader, input);
    }

    private Buffer serializeWithTransferEncoding(FilterChainContext ctx, HttpContent httpContent, TransferEncoding encoding) {
        if (encoding == null) {
            return httpContent.getContent();
        }
        HttpProbeNotifier.notifyTransferEncodingSerialize(this, ctx.getConnection(), httpContent.getHttpHeader(), httpContent.getContent(), encoding);
        return encoding.serializePacket(ctx, httpContent);
    }

    private static boolean lookupAlias(ContentEncoding encoding, DataChunk aliasBuffer, int startIdx) {
        for (String alias : encoding.getAliases()) {
            int aliasLen = alias.length();
            for (int i = 0; i < aliasLen; i++) {
                if (aliasBuffer.startsWithIgnoreCase(alias, startIdx)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean isSecure(Connection connection) {
        return SSLUtils.getSSLEngine(connection) != null;
    }

    protected static boolean statusDropsConnection(int status) {
        return status == 400 || status == 408 || status == 411 || status == 413 || status == 414 || status == 417 || status == 500 || status == 503 || status == 501 || status == 505;
    }

    private boolean checkRemainderOverflow(HttpHeader httpHeader, int payloadChunkSize) {
        if (this.maxPayloadRemainderToSkip < 0) {
            return true;
        }
        ContentParsingState parsingState = ((HttpPacketParsing) httpHeader).getContentParsingState();
        long newSize = parsingState.remainderBytesRead + ((long) payloadChunkSize);
        parsingState.remainderBytesRead = newSize;
        if (newSize <= this.maxPayloadRemainderToSkip) {
            return true;
        }
        return false;
    }

    public static final class HeaderParsingState {
        public int arrayOffset;
        public int checkpoint = -1;
        public int checkpoint2 = -1;
        public HttpCodecFilter codecFilter;
        public int contentLengthHeadersCount;
        public boolean contentLengthsDiffer;
        public DataChunk headerValueStorage;
        public boolean isContentLengthHeader;
        public boolean isTransferEncodingHeader;
        public boolean isUpgradeHeader;
        public int offset;
        public int packetLimit;
        public long parsingNumericValue;
        public int start;
        public int state;
        public int subState;

        public void initialize(HttpCodecFilter codecFilter2, int initialOffset, int maxHeaderSize) {
            this.codecFilter = codecFilter2;
            this.offset = initialOffset;
            this.packetLimit = initialOffset + maxHeaderSize;
        }

        public void set(int state2, int subState2, int start2, int offset2) {
            this.state = state2;
            this.subState = subState2;
            this.start = start2;
            this.offset = offset2;
        }

        public void recycle() {
            this.state = 0;
            this.subState = 0;
            this.start = 0;
            this.offset = 0;
            this.checkpoint = -1;
            this.checkpoint2 = -1;
            this.headerValueStorage = null;
            this.parsingNumericValue = 0;
            this.contentLengthHeadersCount = 0;
            this.contentLengthsDiffer = false;
        }

        public void checkOverflow(int pos, String errorDescriptionIfOverflow) {
            if (pos >= this.packetLimit) {
                throw new IllegalStateException(errorDescriptionIfOverflow);
            }
        }
    }

    public MonitoringConfig<HttpProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.jmx.HttpCodecFilter", this, HttpCodecFilter.class);
    }

    private boolean isResponseToHeadRequest(HttpHeader header) {
        if (header.isRequest()) {
            return false;
        }
        return ((HttpResponsePacket) header).getRequest().isHeadRequest();
    }

    public static final class ContentParsingState {
        public int chunkContentStart = -1;
        public long chunkLength = -1;
        public long chunkRemainder = -1;
        private Buffer[] contentDecodingRemainders;
        public boolean isLastChunk;
        public long remainderBytesRead;
        public final MimeHeaders trailerHeaders;

        public ContentParsingState() {
            MimeHeaders mimeHeaders = new MimeHeaders();
            this.trailerHeaders = mimeHeaders;
            this.contentDecodingRemainders = new Buffer[1];
            mimeHeaders.mark();
        }

        public void recycle() {
            this.isLastChunk = false;
            this.chunkContentStart = -1;
            this.chunkLength = -1;
            this.chunkRemainder = -1;
            this.remainderBytesRead = 0;
            this.trailerHeaders.recycle();
            this.trailerHeaders.mark();
            this.contentDecodingRemainders = null;
        }

        /* access modifiers changed from: private */
        public Buffer removeContentDecodingRemainder(int i) {
            Buffer[] bufferArr = this.contentDecodingRemainders;
            if (bufferArr == null || i >= bufferArr.length) {
                return null;
            }
            Buffer remainder = bufferArr[i];
            bufferArr[i] = null;
            return remainder;
        }

        /* access modifiers changed from: private */
        public void setContentDecodingRemainder(int i, Buffer remainder) {
            Buffer[] bufferArr = this.contentDecodingRemainders;
            if (bufferArr == null) {
                this.contentDecodingRemainders = new Buffer[(i + 1)];
            } else if (i >= bufferArr.length) {
                this.contentDecodingRemainders = (Buffer[]) Arrays.copyOf(bufferArr, i + 1);
            }
            this.contentDecodingRemainders[i] = remainder;
        }
    }
}
