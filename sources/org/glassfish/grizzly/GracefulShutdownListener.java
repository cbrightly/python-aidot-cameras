package org.glassfish.grizzly;

public interface GracefulShutdownListener {
    void shutdownForced();

    void shutdownRequested(ShutdownContext shutdownContext);
}
