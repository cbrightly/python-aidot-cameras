package org.glassfish.grizzly.threadpool;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeStorage;

public interface WorkerThread extends Runnable, AttributeStorage {
    public static final long UNLIMITED_TRANSACTION_TIMEOUT = -1;

    String getName();

    Thread getThread();

    long getTransactionTimeout(TimeUnit timeUnit);

    void setTransactionTimeout(long j, TimeUnit timeUnit);

    void start();

    void stop();
}
