package org.apache.http;

import java.io.Closeable;

/* compiled from: HttpConnection */
public interface i extends Closeable {
    void close();

    boolean isOpen();

    boolean l0();

    void shutdown();

    void y(int i);
}
