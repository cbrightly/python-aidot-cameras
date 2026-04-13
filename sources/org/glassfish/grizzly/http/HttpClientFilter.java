package org.glassfish.grizzly.http;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.HttpEvents;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpClientFilter extends HttpCodecFilter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Attribute<Queue<HttpRequestPacket>> httpRequestQueueAttr;
    private final Attribute<HttpResponsePacket> httpResponseInProcessAttr;

    public HttpClientFilter() {
        this(8192);
    }

    public HttpClientFilter(int maxHeadersSize) {
        super(true, maxHeadersSize);
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.httpResponseInProcessAttr = attributeBuilder.createAttribute("HttpClientFilter.httpResponse");
        this.httpRequestQueueAttr = attributeBuilder.createAttribute("HttpClientFilter.httpRequest");
        this.contentEncodings.add(new GZipContentEncoding());
        this.contentEncodings.add(new LZMAContentEncoding());
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        Connection c = ctx.getConnection();
        Object message = ctx.getMessage();
        if (HttpPacket.isHttp(message)) {
            if (message instanceof HttpPacket) {
                HttpHeader header = ((HttpPacket) message).getHttpHeader();
                if (!header.isCommitted() && header.isRequest()) {
                    if (header instanceof HttpRequestPacket) {
                        getRequestQueue(c).offer((HttpRequestPacket) header);
                    } else {
                        throw new AssertionError();
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
        return super.handleWrite(ctx);
    }

    public NextAction handleRead(FilterChainContext ctx) {
        HttpContext httpCtx;
        Connection connection = ctx.getConnection();
        HttpResponsePacket httpResponse = this.httpResponseInProcessAttr.get((AttributeStorage) connection);
        if (httpResponse == null) {
            httpResponse = createHttpResponse(ctx);
            this.httpResponseInProcessAttr.set((AttributeStorage) connection, httpResponse);
        }
        HttpRequestPacket request = httpResponse.getRequest();
        if (request != null) {
            httpCtx = request.getProcessingState().getHttpContext();
            if (httpCtx == null) {
                httpCtx = HttpContext.newInstance(connection, connection, connection, request);
                request.getProcessingState().setHttpContext(httpCtx);
            }
        } else {
            httpCtx = HttpContext.newInstance(connection, connection, connection, (HttpRequestPacket) null);
        }
        httpCtx.attach(ctx);
        return handleRead(ctx, httpResponse);
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        if (event.type() != HttpEvents.ChangePacketInProgressEvent.TYPE) {
            return super.handleEvent(ctx, event);
        }
        HttpResponsePacket responsePacket = (HttpResponsePacket) ((HttpEvents.ChangePacketInProgressEvent) event).getPacket();
        this.httpResponseInProcessAttr.set((AttributeStorage) responsePacket.getProcessingState().getHttpContext(), responsePacket);
        return ctx.getStopAction();
    }

    private ClientHttpResponseImpl createHttpResponse(FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        ClientHttpResponseImpl httpResponse = ClientHttpResponseImpl.create();
        HttpRequestPacket httpRequest = getRequestQueue(connection).poll();
        httpResponse.setRequest(httpRequest);
        httpResponse.initialize(this, ((Buffer) ctx.getMessage()).position(), this.maxHeadersSize, -1);
        httpResponse.setSecure(HttpCodecFilter.isSecure(connection));
        if (httpRequest != null) {
            try {
                if (Protocol.HTTP_2_0.equals(httpRequest.getProtocol())) {
                    httpResponse.setProtocol(httpRequest.getProtocol());
                    httpResponse.setStatus(HttpStatus.OK_200);
                    httpResponse.setExpectContent(true);
                    httpResponse.setHeaderParsed(true);
                }
            } catch (IllegalStateException e) {
            }
        }
        return httpResponse;
    }

    /* access modifiers changed from: protected */
    public boolean onHttpPacketParsed(HttpHeader httpHeader, FilterChainContext ctx) {
        clearResponse(ctx.getConnection());
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onHttpHeaderParsed(HttpHeader httpHeader, Buffer buffer, FilterChainContext ctx) {
        ClientHttpResponseImpl response = (ClientHttpResponseImpl) httpHeader;
        HttpRequestPacket request = response.getRequest();
        int statusCode = response.getStatus();
        boolean z = true;
        if (statusCode == 204 || statusCode == 205 || statusCode == 304 || (request != null && request.isHeadRequest())) {
            z = false;
        }
        response.setExpectContent(z);
        if (request != null) {
            response.getProcessingState().setKeepAlive(checkKeepAlive(response));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onHttpHeaderError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) {
        throw new IllegalStateException(t);
    }

    /* access modifiers changed from: protected */
    public void onHttpContentError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) {
        httpHeader.setContentBroken(true);
        throw new IllegalStateException(t);
    }

    /* access modifiers changed from: protected */
    public void onInitialLineParsed(HttpHeader httpHeader, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onInitialLineEncoded(HttpHeader header, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpHeadersParsed(HttpHeader httpHeader, MimeHeaders headers, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpHeadersEncoded(HttpHeader httpHeader, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpContentParsed(HttpContent content, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpContentEncoded(HttpContent content, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public final void clearResponse(Connection connection) {
        this.httpResponseInProcessAttr.remove((AttributeStorage) connection);
    }

    /* access modifiers changed from: protected */
    public Buffer encodeHttpPacket(FilterChainContext ctx, HttpPacket input) {
        HttpContent content;
        HttpHeader header;
        if (input.isHeader()) {
            header = (HttpHeader) input;
            content = null;
        } else {
            content = (HttpContent) input;
            header = content.getHttpHeader();
        }
        HttpRequestPacket request = (HttpRequestPacket) header;
        if (!request.isCommitted()) {
            prepareRequest(request);
        }
        return super.encodeHttpPacket(ctx, header, content, false);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0039, code lost:
        r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r13.offset + r1, r15, r2) - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0041, code lost:
        if (r7 >= 0) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0043, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0047, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0048, code lost:
        r13.start = r7;
        r13.offset = r7 + 1;
        r13.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0059, code lost:
        if ((r13.offset + 3) <= (r15 - r1)) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005b, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005c, code lost:
        r0.setStatus(org.glassfish.grizzly.http.util.Ascii.parseInt(r14, r13.start + r1, 3));
        r13.start = -1;
        r13.offset += 3;
        r13.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0072, code lost:
        r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r13.offset + r1, r15, r2) - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007a, code lost:
        if (r7 >= 0) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007c, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0080, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0081, code lost:
        r13.start = r7;
        r13.offset = r7;
        r13.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008e, code lost:
        if (org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r13, r14, r15) != 0) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0090, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0094, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        r0.getReasonPhraseRawDC().setBytes(r14, r13.start + r1, r13.checkpoint + r1);
        r13.subState = 0;
        r13.start = -1;
        r13.checkpoint = -1;
        onInitialLineParsed(r0, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b1, code lost:
        if (r0.getStatus() != 100) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b3, code lost:
        r4 = r13.offset + 2;
        r13.offset = r4;
        r13.start = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00bb, code lost:
        if (r4 >= r15) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c1, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c2, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean decodeInitialLineFromBytes(org.glassfish.grizzly.filterchain.FilterChainContext r11, org.glassfish.grizzly.http.HttpPacketParsing r12, org.glassfish.grizzly.http.HttpCodecFilter.HeaderParsingState r13, byte[] r14, int r15) {
        /*
            r10 = this;
            r0 = r12
            org.glassfish.grizzly.http.HttpResponsePacket r0 = (org.glassfish.grizzly.http.HttpResponsePacket) r0
            int r1 = r13.arrayOffset
            int r2 = r13.packetLimit
            int r2 = r2 + r1
        L_0x0008:
            int r3 = r13.subState
            r4 = -1
            r5 = 1
            r6 = 0
            switch(r3) {
                case 0: goto L_0x0016;
                case 1: goto L_0x0039;
                case 2: goto L_0x0053;
                case 3: goto L_0x0072;
                case 4: goto L_0x008a;
                default: goto L_0x0010;
            }
        L_0x0010:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>()
            throw r4
        L_0x0016:
            int r7 = r13.offset
            int r7 = r7 + r1
            int r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.findSpace(r14, r7, r15, r2)
            if (r7 != r4) goto L_0x0024
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x0024:
            org.glassfish.grizzly.http.util.DataChunk r8 = r0.getProtocolDC()
            int r9 = r13.start
            int r9 = r9 + r1
            r8.setBytes(r14, r9, r7)
            r13.start = r4
            int r8 = r7 - r1
            r13.offset = r8
            int r8 = r13.subState
            int r8 = r8 + r5
            r13.subState = r8
        L_0x0039:
            int r7 = r13.offset
            int r7 = r7 + r1
            int r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r7, r15, r2)
            int r7 = r7 - r1
            if (r7 >= 0) goto L_0x0048
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x0048:
            r13.start = r7
            int r8 = r7 + 1
            r13.offset = r8
            int r8 = r13.subState
            int r8 = r8 + r5
            r13.subState = r8
        L_0x0053:
            int r7 = r13.offset
            r8 = 3
            int r7 = r7 + r8
            int r9 = r15 - r1
            if (r7 <= r9) goto L_0x005c
            return r6
        L_0x005c:
            int r7 = r13.start
            int r7 = r7 + r1
            int r7 = org.glassfish.grizzly.http.util.Ascii.parseInt((byte[]) r14, (int) r7, (int) r8)
            r0.setStatus((int) r7)
            r13.start = r4
            int r7 = r13.offset
            int r7 = r7 + r8
            r13.offset = r7
            int r7 = r13.subState
            int r7 = r7 + r5
            r13.subState = r7
        L_0x0072:
            int r7 = r13.offset
            int r7 = r7 + r1
            int r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r7, r15, r2)
            int r7 = r7 - r1
            if (r7 >= 0) goto L_0x0081
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x0081:
            r13.start = r7
            r13.offset = r7
            int r8 = r13.subState
            int r8 = r8 + r5
            r13.subState = r8
        L_0x008a:
            boolean r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r13, r14, r15)
            if (r7 != 0) goto L_0x0095
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x0095:
            org.glassfish.grizzly.http.util.DataChunk r7 = r0.getReasonPhraseRawDC()
            int r8 = r13.start
            int r8 = r8 + r1
            int r9 = r13.checkpoint
            int r9 = r9 + r1
            r7.setBytes(r14, r8, r9)
            r13.subState = r6
            r13.start = r4
            r13.checkpoint = r4
            r10.onInitialLineParsed(r0, r11)
            int r4 = r0.getStatus()
            r7 = 100
            if (r4 != r7) goto L_0x00c2
            int r4 = r13.offset
            int r4 = r4 + 2
            r13.offset = r4
            r13.start = r4
            if (r4 >= r15) goto L_0x00c1
            r13.subState = r6
            goto L_0x0008
        L_0x00c1:
            return r6
        L_0x00c2:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpClientFilter.decodeInitialLineFromBytes(org.glassfish.grizzly.filterchain.FilterChainContext, org.glassfish.grizzly.http.HttpPacketParsing, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, byte[], int):boolean");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003a, code lost:
        if (r6 != -1) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003c, code lost:
        r12.offset = r13.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0042, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0043, code lost:
        r12.start = r6;
        r12.offset = r6 + 1;
        r12.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0056, code lost:
        if ((r12.offset + 3) <= r13.limit()) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0058, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0059, code lost:
        r0.setStatus(org.glassfish.grizzly.http.util.Ascii.parseInt(r13, r12.start, 3));
        r12.start = -1;
        r12.offset += 3;
        r12.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006e, code lost:
        r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r12.offset, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0074, code lost:
        if (r6 != -1) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0076, code lost:
        r12.offset = r13.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007c, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007d, code lost:
        r12.start = r6;
        r12.offset = r6;
        r12.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008a, code lost:
        if (org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r12, r13) != 0) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008c, code lost:
        r12.offset = r13.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0092, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0093, code lost:
        r0.getReasonPhraseRawDC().setBuffer(r13, r12.start, r12.checkpoint);
        r12.subState = 0;
        r12.start = -1;
        r12.checkpoint = -1;
        onInitialLineParsed(r0, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ad, code lost:
        if (r0.getStatus() != 100) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00af, code lost:
        r3 = r12.offset + 2;
        r12.offset = r3;
        r12.start = 0;
        r13.position(r3);
        r13.shrink();
        r12.offset = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00bf, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c0, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0034, code lost:
        r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r12.offset, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean decodeInitialLineFromBuffer(org.glassfish.grizzly.filterchain.FilterChainContext r10, org.glassfish.grizzly.http.HttpPacketParsing r11, org.glassfish.grizzly.http.HttpCodecFilter.HeaderParsingState r12, org.glassfish.grizzly.Buffer r13) {
        /*
            r9 = this;
            r0 = r11
            org.glassfish.grizzly.http.HttpResponsePacket r0 = (org.glassfish.grizzly.http.HttpResponsePacket) r0
            int r1 = r12.packetLimit
            int r2 = r12.subState
            r3 = 1
            r4 = -1
            r5 = 0
            switch(r2) {
                case 0: goto L_0x0013;
                case 1: goto L_0x0034;
                case 2: goto L_0x004e;
                case 3: goto L_0x006e;
                case 4: goto L_0x0086;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            throw r3
        L_0x0013:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.findSpace(r13, r6, r1)
            if (r6 != r4) goto L_0x0022
            int r3 = r13.limit()
            r12.offset = r3
            return r5
        L_0x0022:
            org.glassfish.grizzly.http.util.DataChunk r7 = r0.getProtocolDC()
            int r8 = r12.start
            r7.setBuffer(r13, r8, r6)
            r12.start = r4
            r12.offset = r6
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x0034:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r6, r1)
            if (r6 != r4) goto L_0x0043
            int r3 = r13.limit()
            r12.offset = r3
            return r5
        L_0x0043:
            r12.start = r6
            int r7 = r6 + 1
            r12.offset = r7
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x004e:
            int r6 = r12.offset
            r7 = 3
            int r6 = r6 + r7
            int r8 = r13.limit()
            if (r6 <= r8) goto L_0x0059
            return r5
        L_0x0059:
            int r6 = r12.start
            int r6 = org.glassfish.grizzly.http.util.Ascii.parseInt((org.glassfish.grizzly.Buffer) r13, (int) r6, (int) r7)
            r0.setStatus((int) r6)
            r12.start = r4
            int r6 = r12.offset
            int r6 = r6 + r7
            r12.offset = r6
            int r6 = r12.subState
            int r6 = r6 + r3
            r12.subState = r6
        L_0x006e:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r6, r1)
            if (r6 != r4) goto L_0x007d
            int r3 = r13.limit()
            r12.offset = r3
            return r5
        L_0x007d:
            r12.start = r6
            r12.offset = r6
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x0086:
            boolean r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r12, r13)
            if (r6 != 0) goto L_0x0093
            int r3 = r13.limit()
            r12.offset = r3
            return r5
        L_0x0093:
            org.glassfish.grizzly.http.util.DataChunk r6 = r0.getReasonPhraseRawDC()
            int r7 = r12.start
            int r8 = r12.checkpoint
            r6.setBuffer(r13, r7, r8)
            r12.subState = r5
            r12.start = r4
            r12.checkpoint = r4
            r9.onInitialLineParsed(r0, r10)
            int r4 = r0.getStatus()
            r6 = 100
            if (r4 != r6) goto L_0x00c0
            int r3 = r12.offset
            int r3 = r3 + 2
            r12.offset = r3
            r12.start = r5
            r13.position(r3)
            r13.shrink()
            r12.offset = r5
            return r5
        L_0x00c0:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpClientFilter.decodeInitialLineFromBuffer(org.glassfish.grizzly.filterchain.FilterChainContext, org.glassfish.grizzly.http.HttpPacketParsing, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, org.glassfish.grizzly.Buffer):boolean");
    }

    /* access modifiers changed from: package-private */
    public Buffer encodeInitialLine(HttpPacket httpPacket, Buffer output, MemoryManager memoryManager) {
        HttpRequestPacket httpRequest = (HttpRequestPacket) httpPacket;
        byte[] tempEncodingBuffer = httpRequest.getTempHeaderEncodingBuffer();
        Buffer output2 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, output, tempEncodingBuffer, httpRequest.getMethodDC()), (byte) 32), tempEncodingBuffer, httpRequest.getRequestURIRef().getRequestURIBC());
        if (!httpRequest.getQueryStringDC().isNull()) {
            output2 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, output2, (byte) 63), tempEncodingBuffer, httpRequest.getQueryStringDC());
        }
        return HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, output2, (byte) 32), tempEncodingBuffer, httpRequest.getProtocolString());
    }

    private Queue<HttpRequestPacket> getRequestQueue(Connection c) {
        Queue<HttpRequestPacket> q = this.httpRequestQueueAttr.get((AttributeStorage) c);
        if (q != null) {
            return q;
        }
        Queue<HttpRequestPacket> q2 = new ConcurrentLinkedQueue<>();
        this.httpRequestQueueAttr.set((AttributeStorage) c, q2);
        return q2;
    }

    private static void prepareRequest(HttpRequestPacket request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            request.getHeaders().setValue(Header.ContentType).setString(contentType);
        }
    }

    private static boolean checkKeepAlive(HttpResponsePacket response) {
        boolean keepAlive = false;
        boolean keepAlive2 = !HttpCodecFilter.statusDropsConnection(response.getStatus()) || !response.isExpectContent() || !response.isChunked() || response.getContentLength() == -1;
        if (!keepAlive2) {
            return keepAlive2;
        }
        DataChunk cVal = response.getHeaders().getValue(Header.Connection);
        if (response.getProtocol().compareTo(Protocol.HTTP_1_1) < 0) {
            if (cVal != null && cVal.equalsIgnoreCase(HttpCodecFilter.KEEPALIVE_BYTES)) {
                keepAlive = true;
            }
            return keepAlive;
        }
        if (cVal == null || !cVal.equalsIgnoreCase(HttpCodecFilter.CLOSE_BYTES)) {
            keepAlive = true;
        }
        return keepAlive;
    }

    public static final class ClientHttpResponseImpl extends HttpResponsePacket implements HttpPacketParsing {
        private static final ThreadCache.CachedTypeIndex<ClientHttpResponseImpl> CACHE_IDX = ThreadCache.obtainIndex(ClientHttpResponseImpl.class, 16);
        private final HttpCodecFilter.ContentParsingState contentParsingState = new HttpCodecFilter.ContentParsingState();
        private boolean contentTypeParsed;
        private final HttpCodecFilter.HeaderParsingState headerParsingState = new HttpCodecFilter.HeaderParsingState();
        private boolean isHeaderParsed;

        public static ClientHttpResponseImpl create() {
            ClientHttpResponseImpl httpResponseImpl = (ClientHttpResponseImpl) ThreadCache.takeFromCache(CACHE_IDX);
            if (httpResponseImpl != null) {
                return httpResponseImpl;
            }
            return new ClientHttpResponseImpl();
        }

        private ClientHttpResponseImpl() {
        }

        public void initialize(HttpCodecFilter filter, int initialOffset, int maxHeaderSize, int maxNumberOfHeaders) {
            this.headerParsingState.initialize(filter, initialOffset, maxHeaderSize);
            this.headers.setMaxNumHeaders(maxNumberOfHeaders);
            this.contentParsingState.trailerHeaders.setMaxNumHeaders(maxNumberOfHeaders);
        }

        public String getCharacterEncoding() {
            if (!this.contentTypeParsed) {
                parseContentTypeHeader();
            }
            return super.getCharacterEncoding();
        }

        public void setCharacterEncoding(String charset) {
            if (!this.contentTypeParsed) {
                parseContentTypeHeader();
            }
            super.setCharacterEncoding(charset);
        }

        public String getContentType() {
            if (!this.contentTypeParsed) {
                parseContentTypeHeader();
            }
            return super.getContentType();
        }

        private void parseContentTypeHeader() {
            DataChunk dc;
            this.contentTypeParsed = true;
            if (!this.contentType.isSet() && (dc = this.headers.getValue(Header.ContentType)) != null && !dc.isNull()) {
                setContentType(dc.toString());
            }
        }

        /* access modifiers changed from: protected */
        public HttpPacketParsing getParsingState() {
            return this;
        }

        public HttpCodecFilter.HeaderParsingState getHeaderParsingState() {
            return this.headerParsingState;
        }

        public HttpCodecFilter.ContentParsingState getContentParsingState() {
            return this.contentParsingState;
        }

        public ProcessingState getProcessingState() {
            return getRequest().getProcessingState();
        }

        public boolean isHeaderParsed() {
            return this.isHeaderParsed;
        }

        public void setHeaderParsed(boolean isHeaderParsed2) {
            this.isHeaderParsed = isHeaderParsed2;
        }

        /* access modifiers changed from: protected */
        public void reset() {
            this.contentTypeParsed = false;
            this.isHeaderParsed = false;
            this.headerParsingState.recycle();
            this.contentParsingState.recycle();
            super.reset();
        }

        public void recycle() {
            if (!getRequest().isExpectContent()) {
                reset();
                ThreadCache.putToCache(CACHE_IDX, this);
            }
        }
    }
}
