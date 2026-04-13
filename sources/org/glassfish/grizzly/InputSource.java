package org.glassfish.grizzly;

public interface InputSource {
    boolean isFinished();

    boolean isReady();

    void notifyAvailable(ReadHandler readHandler);

    void notifyAvailable(ReadHandler readHandler, int i);

    int readyData();
}
