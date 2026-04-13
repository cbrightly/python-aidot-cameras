package org.glassfish.grizzly;

public interface OutputSink {
    boolean canWrite();

    @Deprecated
    boolean canWrite(int i);

    void notifyCanWrite(WriteHandler writeHandler);

    @Deprecated
    void notifyCanWrite(WriteHandler writeHandler, int i);
}
