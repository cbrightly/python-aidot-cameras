package org.glassfish.grizzly.http;

import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.util.MimeHeaders;

public interface HttpPacketParsing {
    HttpCodecFilter.ContentParsingState getContentParsingState();

    HttpCodecFilter.HeaderParsingState getHeaderParsingState();

    MimeHeaders getHeaders();

    boolean isHeaderParsed();

    void setHeaderParsed(boolean z);
}
