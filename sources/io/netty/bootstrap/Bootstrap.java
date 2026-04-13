package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

public class Bootstrap extends AbstractBootstrap<Bootstrap, Channel> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) Bootstrap.class);
    private volatile SocketAddress remoteAddress;

    public Bootstrap() {
    }

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.remoteAddress = bootstrap.remoteAddress;
    }

    public Bootstrap remoteAddress(SocketAddress remoteAddress2) {
        this.remoteAddress = remoteAddress2;
        return this;
    }

    public Bootstrap remoteAddress(String inetHost, int inetPort) {
        this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
        this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
        return this;
    }

    public ChannelFuture connect() {
        validate();
        SocketAddress remoteAddress2 = this.remoteAddress;
        if (remoteAddress2 != null) {
            return doConnect(remoteAddress2, localAddress());
        }
        throw new IllegalStateException("remoteAddress not set");
    }

    public ChannelFuture connect(String inetHost, int inetPort) {
        return connect(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture connect(InetAddress inetHost, int inetPort) {
        return connect(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture connect(SocketAddress remoteAddress2) {
        if (remoteAddress2 != null) {
            validate();
            return doConnect(remoteAddress2, localAddress());
        }
        throw new NullPointerException("remoteAddress");
    }

    public ChannelFuture connect(SocketAddress remoteAddress2, SocketAddress localAddress) {
        if (remoteAddress2 != null) {
            validate();
            return doConnect(remoteAddress2, localAddress);
        }
        throw new NullPointerException("remoteAddress");
    }

    private ChannelFuture doConnect(SocketAddress remoteAddress2, SocketAddress localAddress) {
        ChannelFuture regFuture = initAndRegister();
        Channel channel = regFuture.channel();
        if (regFuture.cause() != null) {
            return regFuture;
        }
        ChannelPromise promise = channel.newPromise();
        if (regFuture.isDone()) {
            doConnect0(regFuture, channel, remoteAddress2, localAddress, promise);
        } else {
            final ChannelFuture channelFuture = regFuture;
            final Channel channel2 = channel;
            final SocketAddress socketAddress = remoteAddress2;
            final SocketAddress socketAddress2 = localAddress;
            final ChannelPromise channelPromise = promise;
            regFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) {
                    Bootstrap.doConnect0(channelFuture, channel2, socketAddress, socketAddress2, channelPromise);
                }
            });
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public static void doConnect0(ChannelFuture regFuture, Channel channel, SocketAddress remoteAddress2, SocketAddress localAddress, ChannelPromise promise) {
        final ChannelFuture channelFuture = regFuture;
        final SocketAddress socketAddress = localAddress;
        final Channel channel2 = channel;
        final SocketAddress socketAddress2 = remoteAddress2;
        final ChannelPromise channelPromise = promise;
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                if (channelFuture.isSuccess()) {
                    SocketAddress socketAddress = socketAddress;
                    if (socketAddress == null) {
                        channel2.connect(socketAddress2, channelPromise);
                    } else {
                        channel2.connect(socketAddress2, socketAddress, channelPromise);
                    }
                    channelPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    return;
                }
                channelPromise.setFailure(channelFuture.cause());
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void init(Channel channel) {
        channel.pipeline().addLast(handler());
        Map<ChannelOption<?>, Object> options = options();
        synchronized (options) {
            AbstractBootstrap.setChannelOptions(channel, options, logger);
        }
        Map<AttributeKey<?>, Object> attrs = attrs();
        synchronized (attrs) {
            for (Map.Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
                channel.attr(e.getKey()).set(e.getValue());
            }
        }
    }

    public Bootstrap validate() {
        super.validate();
        if (handler() != null) {
            return this;
        }
        throw new IllegalStateException("handler not set");
    }

    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup group) {
        Bootstrap bs = new Bootstrap(this);
        bs.group = group;
        return bs;
    }

    public String toString() {
        if (this.remoteAddress == null) {
            return super.toString();
        }
        StringBuilder buf = new StringBuilder(super.toString());
        buf.setLength(buf.length() - 1);
        buf.append(", remoteAddress: ");
        buf.append(this.remoteAddress);
        buf.append(')');
        return buf.toString();
    }
}
