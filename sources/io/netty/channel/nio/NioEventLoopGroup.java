package io.netty.channel.nio;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;

public class NioEventLoopGroup extends MultithreadEventLoopGroup {
    public NioEventLoopGroup() {
        this(0);
    }

    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (ThreadFactory) null);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }

    public void setIoRatio(int ioRatio) {
        Iterator<EventExecutor> it = children().iterator();
        while (it.hasNext()) {
            ((NioEventLoop) it.next()).setIoRatio(ioRatio);
        }
    }

    public void rebuildSelectors() {
        Iterator<EventExecutor> it = children().iterator();
        while (it.hasNext()) {
            ((NioEventLoop) it.next()).rebuildSelector();
        }
    }

    /* access modifiers changed from: protected */
    public EventExecutor newChild(ThreadFactory threadFactory, Object... args) {
        return new NioEventLoop(this, threadFactory, args[0], args[1].newSelectStrategy(), args[2]);
    }
}
