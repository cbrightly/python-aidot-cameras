package org.glassfish.grizzly.http.io;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.OutputSink;

public interface BinaryNIOOutputSink extends OutputSink {
    void write(Buffer buffer);
}
