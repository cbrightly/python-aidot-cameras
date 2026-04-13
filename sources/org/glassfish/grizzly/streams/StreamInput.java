package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.utils.conditions.Condition;

public class StreamInput implements Input {
    private final StreamReader streamReader;

    public StreamInput(StreamReader streamReader2) {
        this.streamReader = streamReader2;
    }

    public GrizzlyFuture<Integer> notifyCondition(Condition condition, CompletionHandler<Integer> completionHandler) {
        return this.streamReader.notifyCondition(condition, completionHandler);
    }

    public byte read() {
        return this.streamReader.readByte();
    }

    public void skip(int length) {
        this.streamReader.skip(length);
    }

    public boolean isBuffered() {
        return this.streamReader.isSupportBufferWindow();
    }

    public Buffer getBuffer() {
        return this.streamReader.getBufferWindow();
    }

    public Buffer takeBuffer() {
        return this.streamReader.takeBufferWindow();
    }

    public int size() {
        return this.streamReader.available();
    }

    public void close() {
        this.streamReader.close();
    }
}
