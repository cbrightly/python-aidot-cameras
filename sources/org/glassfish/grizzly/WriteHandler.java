package org.glassfish.grizzly;

public interface WriteHandler {
    void onError(Throwable th);

    void onWritePossible();
}
