package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFuture extends Future<Void> {
    ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelFuture await();

    ChannelFuture awaitUninterruptibly();

    Channel channel();

    ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelFuture sync();

    ChannelFuture syncUninterruptibly();
}
