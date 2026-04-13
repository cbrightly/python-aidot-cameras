package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public interface TransferEncoding {
    ParsingResult parsePacket(FilterChainContext filterChainContext, HttpHeader httpHeader, Buffer buffer);

    void prepareSerialize(FilterChainContext filterChainContext, HttpHeader httpHeader, HttpContent httpContent);

    Buffer serializePacket(FilterChainContext filterChainContext, HttpContent httpContent);

    boolean wantDecode(HttpHeader httpHeader);

    boolean wantEncode(HttpHeader httpHeader);
}
