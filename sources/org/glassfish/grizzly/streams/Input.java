package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.utils.conditions.Condition;

public interface Input {
    void close();

    Buffer getBuffer();

    boolean isBuffered();

    GrizzlyFuture<Integer> notifyCondition(Condition condition, CompletionHandler<Integer> completionHandler);

    byte read();

    int size();

    void skip(int i);

    Buffer takeBuffer();
}
