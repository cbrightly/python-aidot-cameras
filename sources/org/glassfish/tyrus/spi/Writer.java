package org.glassfish.tyrus.spi;

import java.io.Closeable;
import java.nio.ByteBuffer;

public abstract class Writer implements Closeable {
    public abstract void write(ByteBuffer byteBuffer, CompletionHandler<ByteBuffer> completionHandler);

    public void write(ByteBuffer buffer, CompletionHandler<ByteBuffer> completionHandler, WriterInfo writerInfo) {
        write(buffer, completionHandler);
    }
}
