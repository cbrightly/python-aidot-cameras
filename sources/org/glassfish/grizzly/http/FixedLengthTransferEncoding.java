package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpCodecFilter;

public final class FixedLengthTransferEncoding implements TransferEncoding {
    public boolean wantDecode(HttpHeader httpPacket) {
        return httpPacket.getContentLength() != -1;
    }

    public boolean wantEncode(HttpHeader httpPacket) {
        return httpPacket.getContentLength() != -1;
    }

    public void prepareSerialize(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent) {
        httpHeader.makeContentLengthHeader((long) (httpContent != null ? httpContent.getContent().remaining() : -1));
    }

    public ParsingResult parsePacket(FilterChainContext ctx, HttpHeader httpPacket, Buffer input) {
        HttpCodecFilter.ContentParsingState contentParsingState = ((HttpPacketParsing) httpPacket).getContentParsingState();
        if (contentParsingState.chunkRemainder == -1) {
            contentParsingState.chunkRemainder = httpPacket.getContentLength();
        }
        Buffer remainder = null;
        long thisPacketRemaining = contentParsingState.chunkRemainder;
        if (((long) input.remaining()) > thisPacketRemaining) {
            remainder = input.slice((int) (((long) input.position()) + thisPacketRemaining), input.limit());
            input.limit((int) (((long) input.position()) + thisPacketRemaining));
        }
        long remaining = contentParsingState.chunkRemainder - ((long) input.remaining());
        contentParsingState.chunkRemainder = remaining;
        return ParsingResult.create(httpPacket.httpContentBuilder().content(input).last(remaining == 0).build(), remainder);
    }

    public Buffer serializePacket(FilterChainContext ctx, HttpContent httpContent) {
        return httpContent.getContent();
    }
}
