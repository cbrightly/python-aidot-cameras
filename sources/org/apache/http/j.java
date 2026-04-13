package org.apache.http;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: HttpEntity */
public interface j {
    InputStream getContent();

    d getContentEncoding();

    long getContentLength();

    d getContentType();

    boolean isChunked();

    boolean isRepeatable();

    boolean isStreaming();

    void writeTo(OutputStream outputStream);
}
