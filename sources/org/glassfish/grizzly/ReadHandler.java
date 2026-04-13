package org.glassfish.grizzly;

public interface ReadHandler {
    void onAllDataRead();

    void onDataAvailable();

    void onError(Throwable th);
}
