package io.netty.channel.local;

import io.netty.channel.SingleThreadEventLoop;
import java.util.concurrent.ThreadFactory;

public final class LocalEventLoop extends SingleThreadEventLoop {
    LocalEventLoop(LocalEventLoopGroup parent, ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }

    /* access modifiers changed from: protected */
    public void run() {
        do {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
                updateLastExecutionTime();
            }
        } while (!confirmShutdown());
    }
}
