package io.netty.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.TimeUnit;

public class ReadTimeoutHandler extends IdleStateHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean closed;

    public ReadTimeoutHandler(int timeoutSeconds) {
        this((long) timeoutSeconds, TimeUnit.SECONDS);
    }

    public ReadTimeoutHandler(long timeout, TimeUnit unit) {
        super(timeout, 0, 0, unit);
    }

    /* access modifiers changed from: protected */
    public final void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        if (evt.state() == IdleState.READER_IDLE) {
            readTimedOut(ctx);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public void readTimedOut(ChannelHandlerContext ctx) {
        if (!this.closed) {
            ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }
}
