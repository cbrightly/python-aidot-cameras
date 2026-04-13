package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public class StreamOutput implements Output {
    private final StreamWriter streamWriter;

    public StreamOutput(StreamWriter streamWriter2) {
        this.streamWriter = streamWriter2;
    }

    public void write(byte data) {
        this.streamWriter.writeByte(data);
    }

    public void write(Buffer buffer) {
        this.streamWriter.writeBuffer(buffer);
    }

    public boolean isBuffered() {
        return false;
    }

    public void ensureBufferCapacity(int size) {
    }

    public Buffer getBuffer() {
        throw new UnsupportedOperationException("Buffer is not available in StreamOutput");
    }

    public GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler) {
        return this.streamWriter.flush(completionHandler);
    }

    public GrizzlyFuture<Integer> close(CompletionHandler<Integer> completionHandler) {
        return this.streamWriter.close(completionHandler);
    }
}
