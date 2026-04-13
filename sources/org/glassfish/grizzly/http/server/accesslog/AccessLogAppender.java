package org.glassfish.grizzly.http.server.accesslog;

import java.io.Closeable;

public interface AccessLogAppender extends Closeable {
    void append(String str);

    void close();
}
