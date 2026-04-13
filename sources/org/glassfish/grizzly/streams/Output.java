package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public interface Output {
    GrizzlyFuture<Integer> close(CompletionHandler<Integer> completionHandler);

    void ensureBufferCapacity(int i);

    GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler);

    Buffer getBuffer();

    boolean isBuffered();

    void write(byte b);

    void write(Buffer buffer);
}
