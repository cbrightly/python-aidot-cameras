package org.glassfish.grizzly.http;

import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeHeaders;

public interface MimeHeadersPacket {
    void addHeader(String str, String str2);

    void addHeader(String str, HeaderValue headerValue);

    void addHeader(Header header, String str);

    void addHeader(Header header, HeaderValue headerValue);

    boolean containsHeader(String str);

    boolean containsHeader(Header header);

    String getHeader(String str);

    String getHeader(Header header);

    MimeHeaders getHeaders();

    void setHeader(String str, String str2);

    void setHeader(String str, HeaderValue headerValue);

    void setHeader(Header header, String str);

    void setHeader(Header header, HeaderValue headerValue);
}
