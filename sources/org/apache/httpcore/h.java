package org.apache.httpcore;

import java.io.Closeable;

/* compiled from: HttpConnection */
public interface h extends Closeable {
    void close();

    boolean isOpen();

    void shutdown();
}
