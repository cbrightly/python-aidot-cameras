package org.glassfish.grizzly.http;

public interface EncodingFilter {
    boolean applyDecoding(HttpHeader httpHeader);

    boolean applyEncoding(HttpHeader httpHeader);
}
