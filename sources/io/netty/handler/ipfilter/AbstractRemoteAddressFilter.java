package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.SocketAddress;

public abstract class AbstractRemoteAddressFilter<T extends SocketAddress> extends ChannelInboundHandlerAdapter {
    /* access modifiers changed from: protected */
    public abstract boolean accept(ChannelHandlerContext channelHandlerContext, T t);

    public void channelRegistered(ChannelHandlerContext ctx) {
        handleNewChannel(ctx);
        ctx.fireChannelRegistered();
    }

    public void channelActive(ChannelHandlerContext ctx) {
        if (handleNewChannel(ctx)) {
            ctx.fireChannelActive();
            return;
        }
        throw new IllegalStateException("cannot determine to accept or reject a channel: " + ctx.channel());
    }

    private boolean handleNewChannel(ChannelHandlerContext ctx) {
        T remoteAddress = ctx.channel().remoteAddress();
        if (remoteAddress == null) {
            return false;
        }
        ctx.pipeline().remove((ChannelHandler) this);
        if (accept(ctx, remoteAddress)) {
            channelAccepted(ctx, remoteAddress);
            return true;
        }
        ChannelFuture rejectedFuture = channelRejected(ctx, remoteAddress);
        if (rejectedFuture != null) {
            rejectedFuture.addListener(ChannelFutureListener.CLOSE);
            return true;
        }
        ctx.close();
        return true;
    }

    /* access modifiers changed from: protected */
    public void channelAccepted(ChannelHandlerContext ctx, T t) {
    }

    /* access modifiers changed from: protected */
    public ChannelFuture channelRejected(ChannelHandlerContext ctx, T t) {
        return null;
    }
}
