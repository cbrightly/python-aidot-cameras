package io.netty.util.concurrent;

public interface RejectedExecutionHandler {
    void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor);
}
