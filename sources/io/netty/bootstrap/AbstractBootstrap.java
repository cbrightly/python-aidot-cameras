package io.netty.bootstrap;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable {
    private final Map<AttributeKey<?>, Object> attrs;
    private volatile ChannelFactory<? extends C> channelFactory;
    volatile EventLoopGroup group;
    private volatile ChannelHandler handler;
    private volatile SocketAddress localAddress;
    private final Map<ChannelOption<?>, Object> options;

    public abstract B clone();

    /* access modifiers changed from: package-private */
    public abstract void init(Channel channel);

    AbstractBootstrap() {
        this.options = new LinkedHashMap();
        this.attrs = new LinkedHashMap();
    }

    AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.options = linkedHashMap;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        this.attrs = linkedHashMap2;
        this.group = bootstrap.group;
        this.channelFactory = bootstrap.channelFactory;
        this.handler = bootstrap.handler;
        this.localAddress = bootstrap.localAddress;
        synchronized (bootstrap.options) {
            linkedHashMap.putAll(bootstrap.options);
        }
        synchronized (bootstrap.attrs) {
            linkedHashMap2.putAll(bootstrap.attrs);
        }
    }

    public B group(EventLoopGroup group2) {
        if (group2 == null) {
            throw new NullPointerException("group");
        } else if (this.group == null) {
            this.group = group2;
            return self();
        } else {
            throw new IllegalStateException("group set already");
        }
    }

    private B self() {
        return this;
    }

    public B channel(Class<? extends C> channelClass) {
        if (channelClass != null) {
            return channelFactory(new BootstrapChannelFactory(channelClass));
        }
        throw new NullPointerException("channelClass");
    }

    public B channelFactory(ChannelFactory<? extends C> channelFactory2) {
        if (channelFactory2 == null) {
            throw new NullPointerException("channelFactory");
        } else if (this.channelFactory == null) {
            this.channelFactory = channelFactory2;
            return self();
        } else {
            throw new IllegalStateException("channelFactory set already");
        }
    }

    public B localAddress(SocketAddress localAddress2) {
        this.localAddress = localAddress2;
        return self();
    }

    public B localAddress(int inetPort) {
        return localAddress((SocketAddress) new InetSocketAddress(inetPort));
    }

    public B localAddress(String inetHost, int inetPort) {
        return localAddress((SocketAddress) SocketUtils.socketAddress(inetHost, inetPort));
    }

    public B localAddress(InetAddress inetHost, int inetPort) {
        return localAddress((SocketAddress) new InetSocketAddress(inetHost, inetPort));
    }

    public <T> B option(ChannelOption<T> option, T value) {
        if (option != null) {
            if (value == null) {
                synchronized (this.options) {
                    this.options.remove(option);
                }
            } else {
                synchronized (this.options) {
                    this.options.put(option, value);
                }
            }
            return self();
        }
        throw new NullPointerException("option");
    }

    public <T> B attr(AttributeKey<T> key, T value) {
        if (key != null) {
            if (value == null) {
                synchronized (this.attrs) {
                    this.attrs.remove(key);
                }
            } else {
                synchronized (this.attrs) {
                    this.attrs.put(key, value);
                }
            }
            return self();
        }
        throw new NullPointerException(CacheEntity.KEY);
    }

    public B validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        } else if (this.channelFactory != null) {
            return self();
        } else {
            throw new IllegalStateException("channel or channelFactory not set");
        }
    }

    public ChannelFuture register() {
        validate();
        return initAndRegister();
    }

    public ChannelFuture bind() {
        validate();
        SocketAddress localAddress2 = this.localAddress;
        if (localAddress2 != null) {
            return doBind(localAddress2);
        }
        throw new IllegalStateException("localAddress not set");
    }

    public ChannelFuture bind(int inetPort) {
        return bind((SocketAddress) new InetSocketAddress(inetPort));
    }

    public ChannelFuture bind(String inetHost, int inetPort) {
        return bind((SocketAddress) SocketUtils.socketAddress(inetHost, inetPort));
    }

    public ChannelFuture bind(InetAddress inetHost, int inetPort) {
        return bind((SocketAddress) new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture bind(SocketAddress localAddress2) {
        validate();
        if (localAddress2 != null) {
            return doBind(localAddress2);
        }
        throw new NullPointerException("localAddress");
    }

    private ChannelFuture doBind(SocketAddress localAddress2) {
        ChannelFuture regFuture = initAndRegister();
        Channel channel = regFuture.channel();
        if (regFuture.cause() != null) {
            return regFuture;
        }
        if (regFuture.isDone()) {
            ChannelPromise promise = channel.newPromise();
            doBind0(regFuture, channel, localAddress2, promise);
            return promise;
        }
        PendingRegistrationPromise promise2 = new PendingRegistrationPromise(channel);
        final PendingRegistrationPromise pendingRegistrationPromise = promise2;
        final Channel channel2 = channel;
        final ChannelFuture channelFuture = regFuture;
        final SocketAddress socketAddress = localAddress2;
        regFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                Throwable cause = future.cause();
                if (cause != null) {
                    pendingRegistrationPromise.setFailure(cause);
                    return;
                }
                EventExecutor unused = pendingRegistrationPromise.executor = channel2.eventLoop();
                AbstractBootstrap.doBind0(channelFuture, channel2, socketAddress, pendingRegistrationPromise);
            }
        });
        return promise2;
    }

    /* access modifiers changed from: package-private */
    public final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = channelFactory().newChannel();
            init(channel);
            ChannelFuture regFuture = group().register(channel);
            if (regFuture.cause() != null) {
                if (channel.isRegistered()) {
                    channel.close();
                } else {
                    channel.unsafe().closeForcibly();
                }
            }
            return regFuture;
        } catch (Throwable t) {
            if (channel == null) {
                return new DefaultChannelPromise(new FailedChannel(), GlobalEventExecutor.INSTANCE).setFailure(t);
            }
            channel.unsafe().closeForcibly();
            return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(t);
        }
    }

    /* access modifiers changed from: private */
    public static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress2, final ChannelPromise promise) {
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                if (regFuture.isSuccess()) {
                    channel.bind(localAddress2, promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    promise.setFailure(regFuture.cause());
                }
            }
        });
    }

    public B handler(ChannelHandler handler2) {
        if (handler2 != null) {
            this.handler = handler2;
            return self();
        }
        throw new NullPointerException("handler");
    }

    /* access modifiers changed from: package-private */
    public final SocketAddress localAddress() {
        return this.localAddress;
    }

    /* access modifiers changed from: package-private */
    public final ChannelFactory<? extends C> channelFactory() {
        return this.channelFactory;
    }

    /* access modifiers changed from: package-private */
    public final ChannelHandler handler() {
        return this.handler;
    }

    public EventLoopGroup group() {
        return this.group;
    }

    /* access modifiers changed from: package-private */
    public final Map<ChannelOption<?>, Object> options() {
        return this.options;
    }

    /* access modifiers changed from: package-private */
    public final Map<AttributeKey<?>, Object> attrs() {
        return this.attrs;
    }

    static void setChannelOptions(Channel channel, Map<ChannelOption<?>, Object> options2, InternalLogger logger) {
        for (Map.Entry<ChannelOption<?>, Object> e : options2.entrySet()) {
            setChannelOption(channel, e.getKey(), e.getValue(), logger);
        }
    }

    static void setChannelOptions(Channel channel, Map.Entry<ChannelOption<?>, Object>[] options2, InternalLogger logger) {
        for (Map.Entry<ChannelOption<?>, Object> e : options2) {
            setChannelOption(channel, e.getKey(), e.getValue(), logger);
        }
    }

    private static void setChannelOption(Channel channel, ChannelOption<?> option, Object value, InternalLogger logger) {
        try {
            if (!channel.config().setOption(option, value)) {
                logger.warn("Unknown channel option '{}' for channel '{}'", option, channel);
            }
        } catch (Throwable t) {
            logger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", option, value, channel, t);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        StringBuilder buf = sb.append('(');
        if (this.group != null) {
            buf.append("group: ");
            buf.append(StringUtil.simpleClassName((Object) this.group));
            buf.append(", ");
        }
        if (this.channelFactory != null) {
            buf.append("channelFactory: ");
            buf.append(this.channelFactory);
            buf.append(", ");
        }
        if (this.localAddress != null) {
            buf.append("localAddress: ");
            buf.append(this.localAddress);
            buf.append(", ");
        }
        synchronized (this.options) {
            if (!this.options.isEmpty()) {
                buf.append("options: ");
                buf.append(this.options);
                buf.append(", ");
            }
        }
        synchronized (this.attrs) {
            if (!this.attrs.isEmpty()) {
                buf.append("attrs: ");
                buf.append(this.attrs);
                buf.append(", ");
            }
        }
        if (this.handler != null) {
            buf.append("handler: ");
            buf.append(this.handler);
            buf.append(", ");
        }
        if (buf.charAt(buf.length() - 1) == '(') {
            buf.append(')');
        } else {
            buf.setCharAt(buf.length() - 2, ')');
            buf.setLength(buf.length() - 1);
        }
        return buf.toString();
    }

    public static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T> {
        private final Class<? extends T> clazz;

        BootstrapChannelFactory(Class<? extends T> clazz2) {
            this.clazz = clazz2;
        }

        public T newChannel() {
            try {
                return (Channel) this.clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable t) {
                throw new ChannelException("Unable to create Channel from class " + this.clazz, t);
            }
        }

        public String toString() {
            return StringUtil.simpleClassName((Class<?>) this.clazz) + ".class";
        }
    }

    public static final class PendingRegistrationPromise extends DefaultChannelPromise {
        /* access modifiers changed from: private */
        public volatile EventExecutor executor;

        private PendingRegistrationPromise(Channel channel) {
            super(channel);
        }

        /* access modifiers changed from: protected */
        public EventExecutor executor() {
            EventExecutor executor2 = this.executor;
            if (executor2 != null) {
                return executor2;
            }
            return GlobalEventExecutor.INSTANCE;
        }
    }
}
