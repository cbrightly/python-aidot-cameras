package org.glassfish.grizzly.http;

import java.util.concurrent.TimeUnit;
import org.apache.http.l;
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
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class HttpServerFilter extends HttpCodecFilter {
    public static final String HTTP_SERVER_REQUEST_ATTR_NAME = (HttpServerFilter.class.getName() + ".HttpRequest");
    public static final FilterChainEvent RESPONSE_COMPLETE_EVENT = new HttpEvents.ResponseCompleteEvent();
    private final boolean allowKeepAlive;
    private boolean allowPayloadForUndefinedHttpMethods;
    private String defaultResponseContentType;
    private byte[] defaultResponseContentTypeBytes;
    private byte[] defaultResponseContentTypeBytesNoCharset;
    private final Attribute<ServerHttpRequestImpl> httpRequestInProcessAttr;
    private final KeepAlive keepAlive;
    private final Attribute<KeepAliveContext> keepAliveContextAttr;
    private final DelayedExecutor.DelayQueue<KeepAliveContext> keepAliveQueue;
    private final int maxRequestHeaders;
    private final int maxResponseHeaders;

    @Deprecated
    public HttpServerFilter() {
        this(true, 8192, (KeepAlive) null, (DelayedExecutor) null);
    }

    @Deprecated
    public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, KeepAlive keepAlive2, DelayedExecutor executor) {
        this(chunkingEnabled, maxHeadersSize, Constants.DEFAULT_RESPONSE_TYPE, keepAlive2, executor);
    }

    @Deprecated
    public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, String defaultResponseContentType2, KeepAlive keepAlive2, DelayedExecutor executor) {
        this(chunkingEnabled, maxHeadersSize, defaultResponseContentType2, keepAlive2, executor, 100, 100);
    }

    @Deprecated
    public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, String defaultResponseContentType2, KeepAlive keepAlive2, DelayedExecutor executor, int maxRequestHeaders2, int maxResponseHeaders2) {
        super(chunkingEnabled, maxHeadersSize);
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.httpRequestInProcessAttr = attributeBuilder.createAttribute(HTTP_SERVER_REQUEST_ATTR_NAME);
        this.keepAliveContextAttr = attributeBuilder.createAttribute("HttpServerFilter.KeepAliveContext");
        KeepAlive keepAlive3 = null;
        this.keepAliveQueue = executor != null ? executor.createDelayQueue(new KeepAliveWorker(keepAlive2), new KeepAliveResolver()) : null;
        boolean z = keepAlive2 != null;
        this.allowKeepAlive = z;
        this.keepAlive = z ? new KeepAlive(keepAlive2) : keepAlive3;
        if (defaultResponseContentType2 != null && !defaultResponseContentType2.isEmpty()) {
            setDefaultResponseContentType(defaultResponseContentType2);
        }
        this.maxRequestHeaders = maxRequestHeaders2;
        this.maxResponseHeaders = maxResponseHeaders2;
    }

    public String getDefaultResponseContentType() {
        return this.defaultResponseContentType;
    }

    public final void setDefaultResponseContentType(String contentType) {
        this.defaultResponseContentType = contentType;
        if (contentType != null) {
            byte[] checkedByteArray = HttpCodecUtils.toCheckedByteArray(contentType);
            this.defaultResponseContentTypeBytes = checkedByteArray;
            this.defaultResponseContentTypeBytesNoCharset = ContentType.removeCharset(checkedByteArray);
            return;
        }
        this.defaultResponseContentTypeBytesNoCharset = null;
        this.defaultResponseContentTypeBytes = null;
    }

    public boolean isAllowPayloadForUndefinedHttpMethods() {
        return this.allowPayloadForUndefinedHttpMethods;
    }

    public void setAllowPayloadForUndefinedHttpMethods(boolean allowPayloadForUndefinedHttpMethods2) {
        this.allowPayloadForUndefinedHttpMethods = allowPayloadForUndefinedHttpMethods2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        Buffer input = (Buffer) ctx.getMessage();
        Connection connection = ctx.getConnection();
        ServerHttpRequestImpl httpRequest = this.httpRequestInProcessAttr.get((AttributeStorage) connection);
        if (httpRequest == null) {
            boolean isSecureLocal = HttpCodecFilter.isSecure(connection);
            ServerHttpRequestImpl httpRequest2 = ServerHttpRequestImpl.create();
            httpRequest2.initialize(connection, this, input.position(), this.maxHeadersSize, this.maxRequestHeaders);
            httpRequest2.setSecure(isSecureLocal);
            HttpResponsePacket response = httpRequest2.getResponse();
            response.setSecure(isSecureLocal);
            response.getHeaders().setMaxNumHeaders(this.maxResponseHeaders);
            httpRequest2.setResponse(response);
            response.setRequest(httpRequest2);
            HttpContext httpContext = HttpContext.newInstance(connection, connection, connection, httpRequest2).attach(ctx);
            httpRequest2.getProcessingState().setHttpContext(httpContext);
            if (this.allowKeepAlive) {
                KeepAliveContext keepAliveContext = this.keepAliveContextAttr.get((AttributeStorage) httpContext);
                if (keepAliveContext == null) {
                    keepAliveContext = new KeepAliveContext(connection);
                    this.keepAliveContextAttr.set((AttributeStorage) httpContext, keepAliveContext);
                } else {
                    DelayedExecutor.DelayQueue<KeepAliveContext> delayQueue = this.keepAliveQueue;
                    if (delayQueue != null) {
                        delayQueue.remove(keepAliveContext);
                    }
                }
                int requestsProcessed = keepAliveContext.requestsProcessed;
                if (requestsProcessed > 0) {
                    KeepAlive.notifyProbesHit(this.keepAlive, connection, requestsProcessed);
                }
            }
            this.httpRequestInProcessAttr.set((AttributeStorage) httpContext, httpRequest2);
            httpRequest = httpRequest2;
        } else if (httpRequest.isContentBroken()) {
            return ctx.getStopAction();
        } else {
            httpRequest.getProcessingState().getHttpContext().attach(ctx);
        }
        return handleRead(ctx, httpRequest);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0041, code lost:
        if (r7 >= 0) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0043, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0048, code lost:
        r13.start = r7;
        r13.offset = r7 + 1;
        r13.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0057, code lost:
        if (parseRequestURI(r0, r13, r14, r15) != 0) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0059, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005a, code lost:
        r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r13.offset + r1, r15, r2) - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0062, code lost:
        if (r7 >= 0) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0064, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0069, code lost:
        r13.start = r7;
        r13.offset = r7;
        r13.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
        if (org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r13, r14, r15) != 0) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0078, code lost:
        r13.offset = r15 - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007c, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0081, code lost:
        if (r13.checkpoint <= r13.start) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0083, code lost:
        r0.getProtocolDC().setBytes(r14, r13.start + r1, r13.checkpoint + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
        r0.getProtocolDC().setString("");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009a, code lost:
        r13.subState = 0;
        r13.start = -1;
        r13.checkpoint = -1;
        onInitialLineParsed(r0, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0039, code lost:
        r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r13.offset + r1, r15, r2) - r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean decodeInitialLineFromBytes(org.glassfish.grizzly.filterchain.FilterChainContext r11, org.glassfish.grizzly.http.HttpPacketParsing r12, org.glassfish.grizzly.http.HttpCodecFilter.HeaderParsingState r13, byte[] r14, int r15) {
        /*
            r10 = this;
            r0 = r12
            org.glassfish.grizzly.http.HttpServerFilter$ServerHttpRequestImpl r0 = (org.glassfish.grizzly.http.HttpServerFilter.ServerHttpRequestImpl) r0
            int r1 = r13.arrayOffset
            int r2 = r13.packetLimit
            int r2 = r2 + r1
            int r3 = r13.subState
            r4 = -1
            r5 = 1
            r6 = 0
            switch(r3) {
                case 0: goto L_0x0016;
                case 1: goto L_0x0039;
                case 2: goto L_0x0053;
                case 3: goto L_0x005a;
                case 4: goto L_0x0072;
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
            org.glassfish.grizzly.http.util.DataChunk r8 = r0.getMethodDC()
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
            boolean r7 = parseRequestURI(r0, r13, r14, r15)
            if (r7 != 0) goto L_0x005a
            return r6
        L_0x005a:
            int r7 = r13.offset
            int r7 = r7 + r1
            int r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r14, r7, r15, r2)
            int r7 = r7 - r1
            if (r7 >= 0) goto L_0x0069
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x0069:
            r13.start = r7
            r13.offset = r7
            int r8 = r13.subState
            int r8 = r8 + r5
            r13.subState = r8
        L_0x0072:
            boolean r7 = org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r13, r14, r15)
            if (r7 != 0) goto L_0x007d
            int r4 = r15 - r1
            r13.offset = r4
            return r6
        L_0x007d:
            int r7 = r13.checkpoint
            int r8 = r13.start
            if (r7 <= r8) goto L_0x0091
            org.glassfish.grizzly.http.util.DataChunk r7 = r0.getProtocolDC()
            int r8 = r13.start
            int r8 = r8 + r1
            int r9 = r13.checkpoint
            int r9 = r9 + r1
            r7.setBytes(r14, r8, r9)
            goto L_0x009a
        L_0x0091:
            org.glassfish.grizzly.http.util.DataChunk r7 = r0.getProtocolDC()
            java.lang.String r8 = ""
            r7.setString(r8)
        L_0x009a:
            r13.subState = r6
            r13.start = r4
            r13.checkpoint = r4
            r10.onInitialLineParsed(r0, r11)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpServerFilter.decodeInitialLineFromBytes(org.glassfish.grizzly.filterchain.FilterChainContext, org.glassfish.grizzly.http.HttpPacketParsing, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, byte[], int):boolean");
    }

    private static boolean parseRequestURI(ServerHttpRequestImpl httpRequest, HttpCodecFilter.HeaderParsingState state, byte[] input, int end) {
        int arrayOffs = state.arrayOffset;
        int limit = Math.min(end, state.packetLimit + arrayOffs);
        int offset = state.offset + arrayOffs;
        boolean found = false;
        while (true) {
            if (offset >= limit) {
                break;
            }
            byte b = input[offset];
            if (b == 32 || b == 9) {
                found = true;
            } else if (b == 13 || b == 10) {
                found = true;
            } else {
                if (b == 63 && state.checkpoint == -1) {
                    state.checkpoint = offset - arrayOffs;
                }
                offset++;
            }
        }
        found = true;
        if (found) {
            int requestURIEnd = offset;
            int i = state.checkpoint;
            if (i != -1) {
                requestURIEnd = arrayOffs + i;
                httpRequest.getQueryStringDC().setBytes(input, requestURIEnd + 1, offset);
            }
            httpRequest.getRequestURIRef().init(input, state.start + arrayOffs, requestURIEnd);
            state.start = -1;
            state.checkpoint = -1;
            state.subState++;
        }
        state.offset = offset - arrayOffs;
        return found;
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
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0052, code lost:
        if (parseRequestURI(r0, r12, r13) != 0) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0054, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r12.offset, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005b, code lost:
        if (r6 != -1) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
        r12.offset = r13.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0063, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0064, code lost:
        r12.start = r6;
        r12.offset = r6;
        r12.subState++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        if (org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r12, r13) != 0) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0073, code lost:
        r12.offset = r13.limit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0079, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007e, code lost:
        if (r12.checkpoint <= r12.start) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0080, code lost:
        r0.getProtocolDC().setBuffer(r13, r12.start, r12.checkpoint);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008c, code lost:
        r0.getProtocolDC().setString("");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0095, code lost:
        r12.subState = 0;
        r12.start = -1;
        r12.checkpoint = -1;
        onInitialLineParsed(r0, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
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
            org.glassfish.grizzly.http.HttpServerFilter$ServerHttpRequestImpl r0 = (org.glassfish.grizzly.http.HttpServerFilter.ServerHttpRequestImpl) r0
            int r1 = r12.packetLimit
            int r2 = r12.subState
            r3 = 1
            r4 = 0
            r5 = -1
            switch(r2) {
                case 0: goto L_0x0013;
                case 1: goto L_0x0034;
                case 2: goto L_0x004e;
                case 3: goto L_0x0055;
                case 4: goto L_0x006d;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            throw r3
        L_0x0013:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.findSpace(r13, r6, r1)
            if (r6 != r5) goto L_0x0022
            int r3 = r13.limit()
            r12.offset = r3
            return r4
        L_0x0022:
            org.glassfish.grizzly.http.util.DataChunk r7 = r0.getMethodDC()
            int r8 = r12.start
            r7.setBuffer(r13, r8, r6)
            r12.start = r5
            r12.offset = r6
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x0034:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r6, r1)
            if (r6 != r5) goto L_0x0043
            int r3 = r13.limit()
            r12.offset = r3
            return r4
        L_0x0043:
            r12.start = r6
            int r7 = r6 + 1
            r12.offset = r7
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x004e:
            boolean r6 = parseRequestURI(r0, r12, r13)
            if (r6 != 0) goto L_0x0055
            return r4
        L_0x0055:
            int r6 = r12.offset
            int r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r13, r6, r1)
            if (r6 != r5) goto L_0x0064
            int r3 = r13.limit()
            r12.offset = r3
            return r4
        L_0x0064:
            r12.start = r6
            r12.offset = r6
            int r7 = r12.subState
            int r7 = r7 + r3
            r12.subState = r7
        L_0x006d:
            boolean r6 = org.glassfish.grizzly.http.util.HttpCodecUtils.findEOL(r12, r13)
            if (r6 != 0) goto L_0x007a
            int r3 = r13.limit()
            r12.offset = r3
            return r4
        L_0x007a:
            int r6 = r12.checkpoint
            int r7 = r12.start
            if (r6 <= r7) goto L_0x008c
            org.glassfish.grizzly.http.util.DataChunk r6 = r0.getProtocolDC()
            int r7 = r12.start
            int r8 = r12.checkpoint
            r6.setBuffer(r13, r7, r8)
            goto L_0x0095
        L_0x008c:
            org.glassfish.grizzly.http.util.DataChunk r6 = r0.getProtocolDC()
            java.lang.String r7 = ""
            r6.setString(r7)
        L_0x0095:
            r12.subState = r4
            r12.start = r5
            r12.checkpoint = r5
            r9.onInitialLineParsed(r0, r10)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.HttpServerFilter.decodeInitialLineFromBuffer(org.glassfish.grizzly.filterchain.FilterChainContext, org.glassfish.grizzly.http.HttpPacketParsing, org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState, org.glassfish.grizzly.Buffer):boolean");
    }

    private static boolean parseRequestURI(ServerHttpRequestImpl httpRequest, HttpCodecFilter.HeaderParsingState state, Buffer input) {
        int limit = Math.min(input.limit(), state.packetLimit);
        int offset = state.offset;
        boolean found = false;
        while (true) {
            if (offset >= limit) {
                break;
            }
            byte b = input.get(offset);
            if (b == 32 || b == 9) {
                found = true;
            } else if (b == 13 || b == 10) {
                found = true;
            } else {
                if (b == 63 && state.checkpoint == -1) {
                    state.checkpoint = offset;
                }
                offset++;
            }
        }
        found = true;
        if (found) {
            int requestURIEnd = offset;
            if (state.checkpoint != -1) {
                requestURIEnd = state.checkpoint;
                httpRequest.getQueryStringDC().setBuffer(input, state.checkpoint + 1, offset);
            }
            httpRequest.getRequestURIRef().init(input, state.start, requestURIEnd);
            state.start = -1;
            state.checkpoint = -1;
            state.subState++;
        }
        state.offset = offset;
        return found;
    }

    /* access modifiers changed from: protected */
    public boolean onHttpHeaderParsed(HttpHeader httpHeader, Buffer buffer, FilterChainContext ctx) {
        ServerHttpRequestImpl request = (ServerHttpRequestImpl) httpHeader;
        prepareRequest(request, buffer.hasRemaining());
        return request.getProcessingState().error;
    }

    private void prepareRequest(ServerHttpRequestImpl request, boolean hasReadyContent) {
        ServerHttpRequestImpl serverHttpRequestImpl = request;
        ProcessingState state = request.getProcessingState();
        HttpResponsePacket response = request.getResponse();
        boolean z = true;
        try {
            Protocol protocol = request.getProtocol();
            request.getResponse().setChunkingAllowed(isChunkingEnabled());
            if (request.getHeaderParsingState().contentLengthsDiffer) {
                request.getProcessingState().error = true;
                return;
            }
            MimeHeaders headers = request.getHeaders();
            DataChunk hostDC = null;
            DataChunk uriBC = request.getRequestURIRef().getRequestURIBC();
            if (uriBC.startsWithIgnoreCase(l.DEFAULT_SCHEME_NAME, 0)) {
                int pos = uriBC.indexOf("://", 4);
                int uriBCStart = uriBC.getStart();
                if (pos != -1) {
                    int slashPos = uriBC.indexOf('/', pos + 3);
                    if (slashPos == -1) {
                        slashPos = uriBC.getLength();
                        uriBC.setStart(uriBCStart + pos + 1);
                        uriBC.setEnd(uriBCStart + pos + 2);
                    } else {
                        uriBC.setStart(uriBCStart + slashPos);
                        uriBC.setEnd(uriBC.getEnd());
                    }
                    hostDC = headers.setValue(Header.Host);
                    hostDC.set(uriBC, uriBCStart + pos + 3, uriBCStart + slashPos);
                }
            }
            if (hostDC == null) {
                hostDC = headers.getValue(Header.Host);
            }
            boolean isHttp11 = protocol == Protocol.HTTP_1_1;
            if (!isHttp11 || (hostDC != null && !hostDC.isNull())) {
                serverHttpRequestImpl.unparsedHostC = hostDC;
                if (!request.isIgnoreContentModifiers()) {
                    Method method = request.getMethod();
                    Method.PayloadExpectation payloadExpectation = method.getPayloadExpectation();
                    if (payloadExpectation != Method.PayloadExpectation.NOT_ALLOWED) {
                        boolean hasPayload = request.getContentLength() > 0 || request.isChunked();
                        if (!hasPayload || payloadExpectation != Method.PayloadExpectation.UNDEFINED || this.allowPayloadForUndefinedHttpMethods) {
                            serverHttpRequestImpl.setExpectContent(hasPayload);
                        } else {
                            state.error = true;
                            HttpStatus.BAD_REQUEST_400.setValues(response);
                            return;
                        }
                    } else {
                        serverHttpRequestImpl.setExpectContent(method == Method.CONNECT || method == Method.PRI);
                    }
                    if (method == Method.CONNECT) {
                        state.keepAlive = false;
                    } else {
                        DataChunk connectionValueDC = headers.getValue(Header.Connection);
                        if (!(connectionValueDC != null && connectionValueDC.equalsIgnoreCaseLowerCase(HttpCodecFilter.CLOSE_BYTES))) {
                            state.keepAlive = this.allowKeepAlive && (isHttp11 || (connectionValueDC != null && connectionValueDC.equalsIgnoreCaseLowerCase(HttpCodecFilter.KEEPALIVE_BYTES)));
                        }
                    }
                    if (request.requiresAcknowledgement()) {
                        if (!isHttp11 || hasReadyContent) {
                            z = false;
                        }
                        serverHttpRequestImpl.requiresAcknowledgement(z);
                        return;
                    }
                    return;
                }
                return;
            }
            state.error = true;
        } catch (IllegalStateException e) {
            IllegalStateException illegalStateException = e;
            state.error = true;
            HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505.setValues(response);
            serverHttpRequestImpl.setProtocol(Protocol.HTTP_1_1);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean onHttpPacketParsed(HttpHeader httpHeader, FilterChainContext ctx) {
        boolean error = ((ServerHttpRequestImpl) httpHeader).getProcessingState().error;
        if (!error) {
            this.httpRequestInProcessAttr.remove((AttributeStorage) ctx.getConnection());
        }
        return error;
    }

    /* access modifiers changed from: protected */
    public void onInitialLineParsed(HttpHeader httpHeader, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpHeadersParsed(HttpHeader httpHeader, MimeHeaders headers, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpContentParsed(HttpContent content, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpHeaderError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) {
        sendBadRequestResponse(ctx, ((ServerHttpRequestImpl) httpHeader).getResponse());
    }

    /* access modifiers changed from: protected */
    public void onHttpContentError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) {
        HttpResponsePacket response = ((ServerHttpRequestImpl) httpHeader).getResponse();
        if (!response.isCommitted()) {
            sendBadRequestResponse(ctx, response);
        }
        httpHeader.setContentBroken(true);
    }

    /* access modifiers changed from: protected */
    public Buffer encodeHttpPacket(FilterChainContext ctx, HttpPacket input) {
        HttpContent content;
        HttpHeader header;
        HttpContent encodedHttpContent;
        boolean isHeaderPacket = input.isHeader();
        if (isHeaderPacket) {
            header = (HttpHeader) input;
            content = null;
        } else {
            content = (HttpContent) input;
            header = content.getHttpHeader();
        }
        boolean wasContentAlreadyEncoded = false;
        HttpResponsePacket response = (HttpResponsePacket) header;
        if (!response.isCommitted() && (encodedHttpContent = prepareResponse(ctx, response.getRequest(), response, content)) != null) {
            content = encodedHttpContent;
            wasContentAlreadyEncoded = true;
        }
        Buffer encoded = super.encodeHttpPacket(ctx, header, content, wasContentAlreadyEncoded);
        if (!isHeaderPacket) {
            input.recycle();
        }
        return encoded;
    }

    private HttpContent prepareResponse(FilterChainContext ctx, HttpRequestPacket request, HttpResponsePacket response, HttpContent httpContent) {
        boolean z;
        HttpResponsePacket httpResponsePacket = response;
        HttpContent httpContent2 = httpContent;
        if (request.isIgnoreContentModifiers()) {
            FilterChainContext filterChainContext = ctx;
        } else if (response.isIgnoreContentModifiers()) {
            FilterChainContext filterChainContext2 = ctx;
        } else {
            Protocol requestProtocol = request.getProtocol();
            if (requestProtocol == Protocol.HTTP_0_9) {
                return null;
            }
            boolean entityBody = true;
            int statusCode = response.getStatus();
            boolean is204 = HttpStatus.NO_CONTENT_204.statusMatches(statusCode);
            if (is204 || HttpStatus.RESET_CONTENT_205.statusMatches(statusCode) || HttpStatus.NOT_MODIFIED_304.statusMatches(statusCode)) {
                entityBody = false;
                httpResponsePacket.setExpectContent(false);
                if (is204) {
                    httpResponsePacket.setTransferEncoding((TransferEncoding) null);
                    response.getHeaders().removeHeader(Header.TransferEncoding);
                }
            }
            Protocol protocol = Protocol.HTTP_1_1;
            boolean isHttp11OrHigher = requestProtocol.compareTo(protocol) >= 0;
            HttpContent encodedHttpContent = null;
            Method method = request.getMethod();
            if (!Method.CONNECT.equals(method)) {
                if (entityBody) {
                    setContentEncodingsOnSerializing(httpResponsePacket);
                    if (response.getContentLength() == -1 && !response.isChunked()) {
                        if (httpContent2 != null && httpContent.isLast()) {
                            if (!httpResponsePacket.getContentEncodings(true).isEmpty()) {
                                encodedHttpContent = encodeContent(ctx.getConnection(), httpContent2);
                            }
                            httpResponsePacket.setContentLength(httpContent.getContent().remaining());
                        } else if (this.chunkingEnabled && isHttp11OrHigher) {
                            httpResponsePacket.setChunked(true);
                        }
                    }
                }
                if (Method.HEAD.equals(method)) {
                    httpResponsePacket.setExpectContent(false);
                    setContentEncodingsOnSerializing(httpResponsePacket);
                    setTransferEncodingOnSerializing(ctx, httpResponsePacket, httpContent2);
                } else {
                    FilterChainContext filterChainContext3 = ctx;
                }
            } else {
                FilterChainContext filterChainContext4 = ctx;
                httpResponsePacket.setContentEncodingsSelected(true);
                httpResponsePacket.setContentLength(-1);
                httpResponsePacket.setChunked(false);
            }
            MimeHeaders headers = response.getHeaders();
            if (!entityBody) {
                httpResponsePacket.setContentLength(-1);
                int i = statusCode;
                boolean z2 = is204;
            } else {
                String contentLanguage = response.getContentLanguage();
                if (contentLanguage != null) {
                    headers.setValue(Header.ContentLanguage).setString(contentLanguage);
                }
                ContentType contentType = response.getContentTypeHolder();
                if (contentType.isMimeTypeSet()) {
                    DataChunk contentTypeValue = headers.setValue(Header.ContentType);
                    if (contentTypeValue.isNull()) {
                        contentType.serializeToDataChunk(contentTypeValue);
                    }
                    int i2 = statusCode;
                    boolean z3 = is204;
                } else if (this.defaultResponseContentType != null) {
                    DataChunk contenTypeValue = headers.setValue(Header.ContentType);
                    if (contenTypeValue.isNull()) {
                        int i3 = statusCode;
                        String ce = response.getCharacterEncoding();
                        if (ce == null) {
                            boolean z4 = is204;
                            contenTypeValue.setBytes(this.defaultResponseContentTypeBytes);
                        } else {
                            contenTypeValue.setBytes(ContentType.compose(this.defaultResponseContentTypeBytesNoCharset, ce));
                        }
                    } else {
                        boolean z5 = is204;
                    }
                } else {
                    boolean z6 = is204;
                }
            }
            Header header = Header.Date;
            if (!httpResponsePacket.containsHeader(header)) {
                response.getHeaders().addValue(header).setBytes(FastHttpDateFormat.getCurrentDateBytes());
            }
            ProcessingState state = response.getProcessingState();
            boolean isHttp11 = requestProtocol == protocol;
            if (state.keepAlive) {
                if (entityBody && !isHttp11 && response.getContentLength() == -1) {
                    state.keepAlive = false;
                    z = false;
                } else if (entityBody && !response.isChunked() && response.getContentLength() == -1) {
                    state.keepAlive = false;
                    z = false;
                } else if (!checkKeepAliveRequestsCount(state.getHttpContext())) {
                    state.keepAlive = false;
                    z = false;
                } else {
                    DataChunk dc = headers.getValue(Header.Connection);
                    if (dc == null || dc.isNull() || !dc.equalsIgnoreCase(HttpCodecFilter.CLOSE_BYTES)) {
                        z = false;
                    } else {
                        z = false;
                        state.keepAlive = false;
                    }
                }
                if (state.keepAlive && !HttpCodecFilter.statusDropsConnection(response.getStatus())) {
                    z = true;
                }
                state.keepAlive = z;
            }
            if (!state.keepAlive) {
                headers.setValue(Header.Connection).setBytes(HttpCodecFilter.CLOSE_BYTES);
            } else if (!isHttp11 && !state.error) {
                headers.setValue(Header.Connection).setBytes(HttpCodecFilter.KEEPALIVE_BYTES);
            }
            return encodedHttpContent;
        }
        return httpContent2;
    }

    /* access modifiers changed from: package-private */
    public Buffer encodeInitialLine(HttpPacket httpPacket, Buffer output, MemoryManager memoryManager) {
        DataChunk customReasonPhrase;
        HttpResponsePacket httpResponse = (HttpResponsePacket) httpPacket;
        Buffer output2 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, output, httpResponse.getProtocol().getProtocolBytes()), (byte) 32), httpResponse.getHttpStatus().getStatusBytes()), (byte) 32);
        if (!httpResponse.isCustomReasonPhraseSet()) {
            return HttpCodecUtils.put(memoryManager, output2, httpResponse.getHttpStatus().getReasonPhraseBytes());
        }
        if (httpResponse.isHtmlEncodingCustomReasonPhrase()) {
            customReasonPhrase = HttpUtils.filter(httpResponse.getReasonPhraseDC());
        } else {
            customReasonPhrase = HttpUtils.filterNonPrintableCharacters(httpResponse.getReasonPhraseDC());
        }
        return HttpCodecUtils.put(memoryManager, output2, httpResponse.getTempHeaderEncodingBuffer(), customReasonPhrase);
    }

    /* access modifiers changed from: protected */
    public void onInitialLineEncoded(HttpHeader header, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpHeadersEncoded(HttpHeader httpHeader, FilterChainContext ctx) {
    }

    /* access modifiers changed from: protected */
    public void onHttpContentEncoded(HttpContent content, FilterChainContext ctx) {
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        KeepAliveContext keepAliveContext;
        if (event.type() != HttpEvents.ResponseCompleteEvent.TYPE) {
            return ctx.getInvokeAction();
        }
        if (ctx.getConnection().isOpen()) {
            HttpContext context = HttpContext.get(ctx);
            HttpRequestPacket httpRequest = context.getRequest();
            if (this.allowKeepAlive) {
                if (!(this.keepAliveQueue == null || (keepAliveContext = this.keepAliveContextAttr.get((AttributeStorage) context)) == null)) {
                    this.keepAliveQueue.add(keepAliveContext, (long) this.keepAlive.getIdleTimeoutInSeconds(), TimeUnit.SECONDS);
                }
                processResponseComplete(ctx, httpRequest, httpRequest.getProcessingState().isKeepAlive());
            } else {
                processResponseComplete(ctx, httpRequest, false);
            }
        }
        return ctx.getStopAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        ServerHttpRequestImpl httpRequest = this.httpRequestInProcessAttr.get((AttributeStorage) ctx.getConnection());
        if (httpRequest != null && !httpRequest.isContentBroken() && httpRequest.isExpectContent() && httpRequest.getTransferEncoding() == null) {
            httpRequest.setExpectContent(false);
            onHttpPacketParsed(httpRequest, ctx);
        }
        return ctx.getInvokeAction();
    }

    private void processResponseComplete(FilterChainContext ctx, HttpRequestPacket httpRequest, boolean isStayAlive) {
        if (httpRequest.isUpgrade()) {
            httpRequest.getProcessingState().getHttpContext().close();
        } else if (httpRequest.isExpectContent()) {
            if (httpRequest.isContentBroken() || !checkContentLengthRemainder(httpRequest)) {
                httpRequest.setExpectContent(false);
                onHttpPacketParsed(httpRequest, ctx);
                httpRequest.getProcessingState().getHttpContext().close();
                return;
            }
            httpRequest.setSkipRemainder(true);
        } else if (!isStayAlive) {
            httpRequest.getProcessingState().getHttpContext().close();
        }
    }

    /* access modifiers changed from: protected */
    public HttpContent customizeErrorResponse(HttpResponsePacket response) {
        response.setContentLength(0);
        return HttpContent.builder(response).last(true).build();
    }

    private boolean checkKeepAliveRequestsCount(HttpContext httpContext) {
        boolean isKeepAlive = false;
        if (!this.allowKeepAlive) {
            return false;
        }
        KeepAliveContext keepAliveContext = this.keepAliveContextAttr.get((AttributeStorage) httpContext);
        int requestsProcessed = KeepAliveContext.access$108(keepAliveContext);
        int maxRequestCount = this.keepAlive.getMaxRequestsCount();
        if (maxRequestCount == -1 || keepAliveContext.requestsProcessed < maxRequestCount) {
            isKeepAlive = true;
        }
        if (requestsProcessed == 0) {
            if (isKeepAlive) {
                KeepAlive.notifyProbesConnectionAccepted(this.keepAlive, keepAliveContext.connection);
            } else {
                KeepAlive.notifyProbesRefused(this.keepAlive, keepAliveContext.connection);
            }
        }
        return isKeepAlive;
    }

    private void sendBadRequestResponse(FilterChainContext ctx, HttpResponsePacket response) {
        if (response.getHttpStatus().getStatusCode() < 400) {
            HttpStatus.BAD_REQUEST_400.setValues(response);
        }
        commitAndCloseAsError(ctx, response);
    }

    private void commitAndCloseAsError(FilterChainContext ctx, HttpResponsePacket response) {
        ctx.write(encodeHttpPacket(ctx, customizeErrorResponse(response)));
        response.getProcessingState().getHttpContext().close();
    }

    private boolean checkContentLengthRemainder(HttpRequestPacket httpRequest) {
        return this.maxPayloadRemainderToSkip < 0 || httpRequest.getContentLength() <= 0 || ((HttpPacketParsing) httpRequest).getContentParsingState().chunkRemainder <= this.maxPayloadRemainderToSkip;
    }

    public static class KeepAliveContext {
        /* access modifiers changed from: private */
        public final Connection connection;
        /* access modifiers changed from: private */
        public volatile long keepAliveTimeoutMillis = -1;
        /* access modifiers changed from: private */
        public int requestsProcessed;

        static /* synthetic */ int access$108(KeepAliveContext x0) {
            int i = x0.requestsProcessed;
            x0.requestsProcessed = i + 1;
            return i;
        }

        public KeepAliveContext(Connection connection2) {
            this.connection = connection2;
        }
    }

    public static class KeepAliveWorker implements DelayedExecutor.Worker<KeepAliveContext> {
        private final KeepAlive keepAlive;

        public KeepAliveWorker(KeepAlive keepAlive2) {
            this.keepAlive = keepAlive2;
        }

        public boolean doWork(KeepAliveContext context) {
            KeepAlive.notifyProbesTimeout(this.keepAlive, context.connection);
            context.connection.closeSilently();
            return true;
        }
    }

    public static class KeepAliveResolver implements DelayedExecutor.Resolver<KeepAliveContext> {
        private KeepAliveResolver() {
        }

        public boolean removeTimeout(KeepAliveContext context) {
            if (context.keepAliveTimeoutMillis == -1) {
                return false;
            }
            long unused = context.keepAliveTimeoutMillis = -1;
            return true;
        }

        public long getTimeoutMillis(KeepAliveContext element) {
            return element.keepAliveTimeoutMillis;
        }

        public void setTimeoutMillis(KeepAliveContext element, long timeoutMillis) {
            long unused = element.keepAliveTimeoutMillis = timeoutMillis;
        }
    }

    public static final class ServerHttpRequestImpl extends HttpRequestPacket implements HttpPacketParsing {
        private static final ThreadCache.CachedTypeIndex<ServerHttpRequestImpl> CACHE_IDX = ThreadCache.obtainIndex(ServerHttpRequestImpl.class, 16);
        private final HttpCodecFilter.ContentParsingState contentParsingState = new HttpCodecFilter.ContentParsingState();
        private boolean contentTypeParsed;
        private final HttpResponsePacket finalHttpResponse = new HttpResponsePacketImpl();
        private final HttpCodecFilter.HeaderParsingState headerParsingState = new HttpCodecFilter.HeaderParsingState();
        private boolean isHeaderParsed;
        private final ProcessingState processingState = new ProcessingState();

        public static ServerHttpRequestImpl create() {
            ServerHttpRequestImpl httpRequestImpl = (ServerHttpRequestImpl) ThreadCache.takeFromCache(CACHE_IDX);
            if (httpRequestImpl != null) {
                return httpRequestImpl;
            }
            return new ServerHttpRequestImpl();
        }

        private ServerHttpRequestImpl() {
            this.isExpectContent = true;
        }

        public void initialize(Connection connection, HttpCodecFilter filter, int initialOffset, int maxHeaderSize, int maxNumberOfHeaders) {
            this.headerParsingState.initialize(filter, initialOffset, maxHeaderSize);
            this.contentParsingState.trailerHeaders.setMaxNumHeaders(maxNumberOfHeaders);
            this.headers.setMaxNumHeaders(maxNumberOfHeaders);
            this.finalHttpResponse.setProtocol(Protocol.HTTP_1_1);
            setResponse(this.finalHttpResponse);
            setConnection(connection);
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

        public ProcessingState getProcessingState() {
            return this.processingState;
        }

        public HttpCodecFilter.HeaderParsingState getHeaderParsingState() {
            return this.headerParsingState;
        }

        public HttpCodecFilter.ContentParsingState getContentParsingState() {
            return this.contentParsingState;
        }

        public boolean isHeaderParsed() {
            return this.isHeaderParsed;
        }

        public void setHeaderParsed(boolean isHeaderParsed2) {
            if (isHeaderParsed2 && isExpectContent() && !this.isChunked) {
                this.contentParsingState.chunkRemainder = getContentLength();
            }
            this.isHeaderParsed = isHeaderParsed2;
        }

        /* access modifiers changed from: protected */
        public HttpPacketParsing getParsingState() {
            return this;
        }

        /* access modifiers changed from: protected */
        public void reset() {
            this.contentTypeParsed = false;
            this.isHeaderParsed = false;
            this.headerParsingState.recycle();
            this.contentParsingState.recycle();
            this.processingState.recycle();
            super.reset();
        }

        public void recycle() {
            if (!isExpectContent()) {
                reset();
                ThreadCache.putToCache(CACHE_IDX, this);
            }
        }
    }
}
