package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.HexUtils;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Charsets;

public final class ChunkedTransferEncoding implements TransferEncoding {
    private static final int CHUNK_LENGTH_PARSED_STATE = 3;
    private static final long CHUNK_SIZE_OVERFLOW = 576460752303423487L;
    private static final int[] DEC = HexUtils.getDecBytes();
    private static final byte[] LAST_CHUNK_CRLF_BYTES = "0\r\n".getBytes(Charsets.ASCII_CHARSET);
    private static final int MAX_HTTP_CHUNK_SIZE_LENGTH = 16;
    private final int maxHeadersSize;

    public ChunkedTransferEncoding(int maxHeadersSize2) {
        this.maxHeadersSize = maxHeadersSize2;
    }

    public boolean wantDecode(HttpHeader httpPacket) {
        return httpPacket.isChunked();
    }

    public boolean wantEncode(HttpHeader httpPacket) {
        return httpPacket.isChunked();
    }

    public void prepareSerialize(FilterChainContext ctx, HttpHeader httpHeader, HttpContent content) {
        httpHeader.makeTransferEncodingHeader("chunked");
    }

    public ParsingResult parsePacket(FilterChainContext ctx, HttpHeader httpPacket, Buffer buffer) {
        HttpPacketParsing httpPacketParsing = (HttpPacketParsing) httpPacket;
        HttpCodecFilter.ContentParsingState contentParsingState = httpPacketParsing.getContentParsingState();
        boolean isLastChunk = contentParsingState.isLastChunk;
        if (isLastChunk || contentParsingState.chunkRemainder > 0) {
            contentParsingState.chunkContentStart = 0;
        } else {
            buffer = parseTrailerCRLF(httpPacketParsing, buffer);
            if (buffer == null) {
                return ParsingResult.create((HttpContent) null, (Buffer) null);
            }
            if (!parseHttpChunkLength(httpPacketParsing, buffer)) {
                if (isHeadRequest(httpPacket)) {
                    return ParsingResult.create(httpPacket.httpTrailerBuilder().headers(contentParsingState.trailerHeaders).build(), (Buffer) null);
                }
                return ParsingResult.create((HttpContent) null, buffer, false);
            }
        }
        int chunkContentStart = contentParsingState.chunkContentStart;
        if (contentParsingState.chunkLength == 0) {
            if (!isLastChunk) {
                contentParsingState.isLastChunk = true;
                isLastChunk = true;
                initTrailerParsing(httpPacketParsing);
            }
            if (!parseLastChunkTrailer(ctx, httpPacket, httpPacketParsing, buffer)) {
                return ParsingResult.create((HttpContent) null, buffer);
            }
            chunkContentStart = httpPacketParsing.getHeaderParsingState().offset;
        }
        long thisPacketRemaining = contentParsingState.chunkRemainder;
        Buffer remainder = null;
        if (((long) (buffer.limit() - chunkContentStart)) > thisPacketRemaining) {
            remainder = buffer.split((int) (((long) chunkContentStart) + thisPacketRemaining));
            buffer.position(chunkContentStart);
        } else if (chunkContentStart > 0) {
            buffer.position(chunkContentStart);
        }
        if (isLastChunk) {
            return ParsingResult.create(httpPacket.httpTrailerBuilder().headers(contentParsingState.trailerHeaders).build(), remainder);
        }
        buffer.shrink();
        if (buffer.hasRemaining()) {
            contentParsingState.chunkRemainder -= (long) buffer.remaining();
        } else {
            buffer.tryDispose();
            buffer = Buffers.EMPTY_BUFFER;
        }
        return ParsingResult.create(httpPacket.httpContentBuilder().content(buffer).build(), remainder);
    }

    public Buffer serializePacket(FilterChainContext ctx, HttpContent httpContent) {
        return encodeHttpChunk(ctx.getMemoryManager(), httpContent, httpContent.isLast());
    }

    private void initTrailerParsing(HttpPacketParsing httpPacket) {
        HttpCodecFilter.HeaderParsingState headerParsingState = httpPacket.getHeaderParsingState();
        HttpCodecFilter.ContentParsingState contentParsingState = httpPacket.getContentParsingState();
        headerParsingState.subState = 0;
        int start = contentParsingState.chunkContentStart;
        headerParsingState.start = start;
        headerParsingState.offset = start;
        headerParsingState.packetLimit = this.maxHeadersSize + start;
    }

    private boolean parseLastChunkTrailer(FilterChainContext ctx, HttpHeader httpHeader, HttpPacketParsing httpPacket, Buffer input) {
        HttpCodecFilter.HeaderParsingState headerParsingState = httpPacket.getHeaderParsingState();
        HttpCodecFilter.ContentParsingState contentParsingState = httpPacket.getContentParsingState();
        HttpCodecFilter filter = headerParsingState.codecFilter;
        boolean result = filter.parseHeadersFromBuffer(httpHeader, contentParsingState.trailerHeaders, headerParsingState, input);
        if (!result) {
            headerParsingState.checkOverflow(input.limit(), "The chunked encoding trailer header is too large");
        } else if (contentParsingState.trailerHeaders.size() > 0) {
            filter.onHttpHeadersParsed(httpHeader, contentParsingState.trailerHeaders, ctx);
        }
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001d, code lost:
        r2 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r0, r1.offset, r1.packetLimit);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0025, code lost:
        if (r2 != -1) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0027, code lost:
        r1.offset = r17.limit();
        r1.state = 1;
        r1.checkOverflow(r17.limit(), "The chunked encoding length prefix is too large");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0036, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0037, code lost:
        r1.offset = r2;
        r1.state = 2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0006 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean parseHttpChunkLength(org.glassfish.grizzly.http.HttpPacketParsing r16, org.glassfish.grizzly.Buffer r17) {
        /*
            r0 = r17
            org.glassfish.grizzly.http.HttpCodecFilter$HeaderParsingState r1 = r16.getHeaderParsingState()
        L_0x0006:
            int r2 = r1.state
            r3 = 0
            java.lang.String r4 = "The chunked encoding length prefix is too large"
            r5 = -1
            r6 = 1
            switch(r2) {
                case 0: goto L_0x0011;
                case 1: goto L_0x001d;
                case 2: goto L_0x003c;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x0006
        L_0x0011:
            int r2 = r17.position()
            r1.start = r2
            r1.offset = r2
            int r7 = r2 + 16
            r1.packetLimit = r7
        L_0x001d:
            int r2 = r1.offset
            int r7 = r1.packetLimit
            int r2 = org.glassfish.grizzly.http.util.HttpCodecUtils.skipSpaces(r0, r2, r7)
            if (r2 != r5) goto L_0x0037
            int r5 = r17.limit()
            r1.offset = r5
            r1.state = r6
            int r5 = r17.limit()
            r1.checkOverflow(r5, r4)
            return r3
        L_0x0037:
            r1.offset = r2
            r7 = 2
            r1.state = r7
        L_0x003c:
            int r2 = r1.offset
            int r7 = r1.packetLimit
            int r8 = r17.limit()
            int r7 = java.lang.Math.min(r7, r8)
            long r8 = r1.parsingNumericValue
        L_0x004a:
            if (r2 >= r7) goto L_0x00b5
            byte r10 = r0.get((int) r2)
            boolean r11 = org.glassfish.grizzly.http.util.HttpCodecUtils.isSpaceOrTab(r10)
            if (r11 != 0) goto L_0x00af
            r11 = 13
            if (r10 == r11) goto L_0x00af
            r11 = 59
            if (r10 != r11) goto L_0x005f
            goto L_0x00af
        L_0x005f:
            r11 = 10
            if (r10 != r11) goto L_0x0073
            org.glassfish.grizzly.http.HttpCodecFilter$ContentParsingState r3 = r16.getContentParsingState()
            int r4 = r2 + 1
            r3.chunkContentStart = r4
            r3.chunkLength = r8
            r3.chunkRemainder = r8
            r4 = 3
            r1.state = r4
            return r6
        L_0x0073:
            int r11 = r1.checkpoint
            if (r11 != r5) goto L_0x00a7
            int[] r11 = DEC
            r12 = r10 & 255(0xff, float:3.57E-43)
            r12 = r11[r12]
            if (r12 == r5) goto L_0x0090
            boolean r12 = checkOverflow(r8)
            if (r12 == 0) goto L_0x0090
            r12 = 4
            long r12 = r8 << r12
            r14 = r10 & 255(0xff, float:3.57E-43)
            r11 = r11[r14]
            long r14 = (long) r11
            long r12 = r12 + r14
            r8 = r12
            goto L_0x00b1
        L_0x0090:
            org.glassfish.grizzly.http.HttpBrokenContentException r3 = new org.glassfish.grizzly.http.HttpBrokenContentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Invalid byte representing a hex value within a chunk length encountered : "
            r4.append(r5)
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            r3.<init>((java.lang.String) r4)
            throw r3
        L_0x00a7:
            org.glassfish.grizzly.http.HttpBrokenContentException r3 = new org.glassfish.grizzly.http.HttpBrokenContentException
            java.lang.String r4 = "Unexpected HTTP chunk header"
            r3.<init>((java.lang.String) r4)
            throw r3
        L_0x00af:
            r1.checkpoint = r2
        L_0x00b1:
            int r2 = r2 + 1
            goto L_0x004a
        L_0x00b5:
            r1.parsingNumericValue = r8
            r1.offset = r7
            r1.checkOverflow(r7, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.ChunkedTransferEncoding.parseHttpChunkLength(org.glassfish.grizzly.http.HttpPacketParsing, org.glassfish.grizzly.Buffer):boolean");
    }

    private static boolean checkOverflow(long value) {
        return value <= CHUNK_SIZE_OVERFLOW;
    }

    private static boolean isHeadRequest(HttpHeader header) {
        return (header.isRequest() ? (HttpRequestPacket) header : ((HttpResponsePacket) header).getRequest()).isHeadRequest();
    }

    private static Buffer parseTrailerCRLF(HttpPacketParsing httpPacket, Buffer input) {
        HttpCodecFilter.HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
        if (parsingState.state != 3) {
            return input;
        }
        while (input.hasRemaining()) {
            if (input.get() == 10) {
                parsingState.recycle();
                if (input.hasRemaining()) {
                    return input.slice();
                }
                return null;
            }
        }
        return null;
    }

    private static Buffer encodeHttpChunk(MemoryManager memoryManager, HttpContent httpContent, boolean isLastChunk) {
        Buffer httpChunkTrailer;
        Buffer httpChunkTrailer2;
        Buffer content = httpContent.getContent();
        Buffer httpChunkBuffer = memoryManager.allocate(16);
        int chunkSize = content.remaining();
        Ascii.intToHexString(httpChunkBuffer, chunkSize);
        byte[] bArr = HttpCodecFilter.CRLF_BYTES;
        Buffer httpChunkBuffer2 = HttpCodecUtils.put(memoryManager, httpChunkBuffer, bArr);
        httpChunkBuffer2.trim();
        httpChunkBuffer2.allowBufferDispose(true);
        boolean isTrailer = false;
        boolean hasContent = chunkSize > 0;
        if (hasContent) {
            httpChunkBuffer2 = Buffers.appendBuffers(memoryManager, httpChunkBuffer2, content);
            if (httpChunkBuffer2.isComposite()) {
                httpChunkBuffer2.allowBufferDispose(true);
                ((CompositeBuffer) httpChunkBuffer2).allowInternalBuffersDispose(true);
                ((CompositeBuffer) httpChunkBuffer2).disposeOrder(CompositeBuffer.DisposeOrder.FIRST_TO_LAST);
            }
        }
        if (!isLastChunk) {
            httpChunkTrailer = memoryManager.allocate(2);
        } else {
            if (HttpTrailer.isTrailer(httpContent) && ((HttpTrailer) httpContent).getHeaders().size() > 0) {
                isTrailer = true;
            }
            if (!isTrailer) {
                httpChunkTrailer2 = memoryManager.allocate(8);
            } else {
                httpChunkTrailer2 = memoryManager.allocate(256);
            }
            if (hasContent) {
                httpChunkTrailer2 = HttpCodecUtils.put(memoryManager, HttpCodecUtils.put(memoryManager, httpChunkTrailer2, bArr), LAST_CHUNK_CRLF_BYTES);
            }
            if (isTrailer) {
                httpChunkTrailer = HttpCodecFilter.encodeMimeHeaders(memoryManager, httpChunkTrailer2, ((HttpTrailer) httpContent).getHeaders(), httpContent.getHttpHeader().getTempHeaderEncodingBuffer());
            } else {
                httpChunkTrailer = httpChunkTrailer2;
            }
        }
        Buffer httpChunkTrailer3 = HttpCodecUtils.put(memoryManager, httpChunkTrailer, bArr);
        httpChunkTrailer3.trim();
        httpChunkTrailer3.allowBufferDispose(true);
        return Buffers.appendBuffers(memoryManager, httpChunkBuffer2, httpChunkTrailer3);
    }
}
