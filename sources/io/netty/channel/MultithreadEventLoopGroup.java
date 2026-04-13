package io.netty.channel;

import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ThreadFactory;

public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
    private static final int DEFAULT_EVENT_LOOP_THREADS;
    private static final InternalLogger logger;

    static {
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) MultithreadEventLoopGroup.class);
        logger = instance;
        int max = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        DEFAULT_EVENT_LOOP_THREADS = max;
        if (instance.isDebugEnabled()) {
            instance.debug("-Dio.netty.eventLoopThreads: {}", (Object) Integer.valueOf(max));
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
    }

    /* access modifiers changed from: protected */
    public ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass(), 10);
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public ChannelFuture register(Channel channel) {
        return next().register(channel);
    }

    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return next().register(channel, promise);
    }
}
