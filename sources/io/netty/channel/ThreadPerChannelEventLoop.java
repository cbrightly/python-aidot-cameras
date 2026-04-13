package io.netty.channel;

public class ThreadPerChannelEventLoop extends SingleThreadEventLoop {
    /* access modifiers changed from: private */
    public Channel ch;
    private final ThreadPerChannelEventLoopGroup parent;

    public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup parent2) {
        super(parent2, parent2.threadFactory, true);
        this.parent = parent2;
    }

    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return super.register(channel, promise).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    Channel unused = ThreadPerChannelEventLoop.this.ch = future.channel();
                } else {
                    ThreadPerChannelEventLoop.this.deregister();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void run() {
        while (true) {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
                updateLastExecutionTime();
            }
            Channel ch2 = this.ch;
            if (isShuttingDown()) {
                if (ch2 != null) {
                    ch2.unsafe().close(ch2.unsafe().voidPromise());
                }
                if (confirmShutdown()) {
                    return;
                }
            } else if (ch2 != null && !ch2.isRegistered()) {
                runAllTasks();
                deregister();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void deregister() {
        this.ch = null;
        this.parent.activeChildren.remove(this);
        this.parent.idleChildren.add(this);
    }
}
