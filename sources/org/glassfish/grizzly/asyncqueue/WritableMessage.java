package org.glassfish.grizzly.asyncqueue;

public interface WritableMessage {
    boolean hasRemaining();

    boolean isExternal();

    boolean release();

    int remaining();
}
