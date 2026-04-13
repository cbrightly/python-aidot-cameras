package org.apache.httpcore;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: HttpEntity */
public interface j {
    InputStream getContent();

    e getContentEncoding();

    long getContentLength();

    e getContentType();

    boolean isChunked();

    boolean isStreaming();

    void writeTo(OutputStream outputStream);
}
