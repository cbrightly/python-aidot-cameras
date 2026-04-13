package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ServerBootstrap.class);
    private final Map<AttributeKey<?>, Object> childAttrs;
    private volatile EventLoopGroup childGroup;
    private volatile ChannelHandler childHandler;
    private final Map<ChannelOption<?>, Object> childOptions;

    public ServerBootstrap() {
        this.childOptions = new LinkedHashMap();
        this.childAttrs = new LinkedHashMap();
    }

    private ServerBootstrap(ServerBootstrap bootstrap) {
        super(bootstrap);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.childOptions = linkedHashMap;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        this.childAttrs = linkedHashMap2;
        this.childGroup = bootstrap.childGroup;
        this.childHandler = bootstrap.childHandler;
        synchronized (bootstrap.childOptions) {
            linkedHashMap.putAll(bootstrap.childOptions);
        }
        synchronized (bootstrap.childAttrs) {
            linkedHashMap2.putAll(bootstrap.childAttrs);
        }
    }

    public ServerBootstrap group(EventLoopGroup group) {
        return group(group, group);
    }

    public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup2) {
        super.group(parentGroup);
        if (childGroup2 == null) {
            throw new NullPointerException("childGroup");
        } else if (this.childGroup == null) {
            this.childGroup = childGroup2;
            return this;
        } else {
            throw new IllegalStateException("childGroup set already");
        }
    }

    public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
        if (childOption != null) {
            if (value == null) {
                synchronized (this.childOptions) {
                    this.childOptions.remove(childOption);
                }
            } else {
                synchronized (this.childOptions) {
                    this.childOptions.put(childOption, value);
                }
            }
            return this;
        }
        throw new NullPointerException("childOption");
    }

    public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
        if (childKey != null) {
            if (value == null) {
                this.childAttrs.remove(childKey);
            } else {
                this.childAttrs.put(childKey, value);
            }
            return this;
        }
        throw new NullPointerException("childKey");
    }

    public ServerBootstrap childHandler(ChannelHandler childHandler2) {
        if (childHandler2 != null) {
            this.childHandler = childHandler2;
            return this;
        }
        throw new NullPointerException("childHandler");
    }

    public EventLoopGroup childGroup() {
        return this.childGroup;
    }

    /* access modifiers changed from: package-private */
    public void init(Channel channel) {
        final Map.Entry<ChannelOption<?>, Object>[] currentChildOptions;
        final Map.Entry<AttributeKey<?>, Object>[] currentChildAttrs;
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
        ChannelPipeline p = channel.pipeline();
        EventLoopGroup currentChildGroup = this.childGroup;
        ChannelHandler currentChildHandler = this.childHandler;
        synchronized (this.childOptions) {
            currentChildOptions = (Map.Entry[]) this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
        }
        synchronized (this.childAttrs) {
            currentChildAttrs = (Map.Entry[]) this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
        }
        final EventLoopGroup eventLoopGroup = currentChildGroup;
        final ChannelHandler channelHandler = currentChildHandler;
        p.addLast(new ChannelInitializer<Channel>() {
            public void initChannel(final Channel ch) {
                final ChannelPipeline pipeline = ch.pipeline();
                ChannelHandler handler = ServerBootstrap.this.handler();
                if (handler != null) {
                    pipeline.addLast(handler);
                }
                ch.eventLoop().execute(new Runnable() {
                    public void run() {
                        ChannelPipeline channelPipeline = pipeline;
                        Channel channel = ch;
                        AnonymousClass1 r2 = AnonymousClass1.this;
                        channelPipeline.addLast(new ServerBootstrapAcceptor(channel, eventLoopGroup, channelHandler, currentChildOptions, currentChildAttrs));
                    }
                });
            }
        });
    }

    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler != null) {
            if (this.childGroup == null) {
                logger.warn("childGroup is not set. Using parentGroup instead.");
                this.childGroup = group();
            }
            return this;
        }
        throw new IllegalStateException("childHandler not set");
    }

    private static Map.Entry<AttributeKey<?>, Object>[] newAttrArray(int size) {
        return new Map.Entry[size];
    }

    private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(int size) {
        return new Map.Entry[size];
    }

    public static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {
        private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
        private final Runnable enableAutoReadTask;

        ServerBootstrapAcceptor(final Channel channel, EventLoopGroup childGroup2, ChannelHandler childHandler2, Map.Entry<ChannelOption<?>, Object>[] childOptions2, Map.Entry<AttributeKey<?>, Object>[] childAttrs2) {
            this.childGroup = childGroup2;
            this.childHandler = childHandler2;
            this.childOptions = childOptions2;
            this.childAttrs = childAttrs2;
            this.enableAutoReadTask = new Runnable() {
                public void run() {
                    channel.config().setAutoRead(true);
                }
            };
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            final Channel child = (Channel) msg;
            child.pipeline().addLast(this.childHandler);
            AbstractBootstrap.setChannelOptions(child, this.childOptions, ServerBootstrap.logger);
            for (Map.Entry<AttributeKey<?>, Object> e : this.childAttrs) {
                child.attr(e.getKey()).set(e.getValue());
            }
            try {
                this.childGroup.register(child).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) {
                        if (!future.isSuccess()) {
                            ServerBootstrapAcceptor.forceClose(child, future.cause());
                        }
                    }
                });
            } catch (Throwable t) {
                forceClose(child, t);
            }
        }

        /* access modifiers changed from: private */
        public static void forceClose(Channel child, Throwable t) {
            child.unsafe().closeForcibly();
            InternalLogger access$000 = ServerBootstrap.logger;
            access$000.warn("Failed to register an accepted channel: " + child, t);
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ChannelConfig config = ctx.channel().config();
            if (config.isAutoRead()) {
                config.setAutoRead(false);
                ctx.channel().eventLoop().schedule(this.enableAutoReadTask, 1, TimeUnit.SECONDS);
            }
            ctx.fireExceptionCaught(cause);
        }
    }

    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(super.toString());
        buf.setLength(buf.length() - 1);
        buf.append(", ");
        if (this.childGroup != null) {
            buf.append("childGroup: ");
            buf.append(StringUtil.simpleClassName((Object) this.childGroup));
            buf.append(", ");
        }
        synchronized (this.childOptions) {
            if (!this.childOptions.isEmpty()) {
                buf.append("childOptions: ");
                buf.append(this.childOptions);
                buf.append(", ");
            }
        }
        synchronized (this.childAttrs) {
            if (!this.childAttrs.isEmpty()) {
                buf.append("childAttrs: ");
                buf.append(this.childAttrs);
                buf.append(", ");
            }
        }
        if (this.childHandler != null) {
            buf.append("childHandler: ");
            buf.append(this.childHandler);
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
}
