package com.leedarson.tcp;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import timber.log.a;

/* compiled from: NettyClient */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private EventLoopGroup a;
    private Bootstrap b;
    private Channel c;
    private boolean d = false;
    private g e;

    public void e(g listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 10758, new Class[]{g.class}, Void.TYPE).isSupported) {
            if (listener != null) {
                this.e = listener;
                return;
            }
            throw new IllegalArgumentException("listener == null ");
        }
    }

    public synchronized void a(String str, int i, Context context, int i2, String str2, int i3) {
        Class<String> cls = String.class;
        synchronized (this) {
            Object[] objArr = {str, new Integer(i), context, new Integer(i2), str2, new Integer(i3)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Integer.TYPE;
            Class[] clsArr = {cls, cls2, Context.class, cls2, cls, cls2};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10759, clsArr, Void.TYPE).isSupported) {
                int inetPort = i;
                int heartBeatTick = i3;
                int tls = i2;
                String inetHost = str;
                Context context2 = context;
                String aesKey = str2;
                if (!this.d) {
                    this.a = new NioEventLoopGroup();
                    Bootstrap bootstrap = (Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) new Bootstrap().group(this.a)).option(ChannelOption.SO_KEEPALIVE, true)).option(ChannelOption.SO_BACKLOG, 128)).option(ChannelOption.TCP_NODELAY, true)).channel(NioSocketChannel.class)).handler(new f(this.e, context2, tls, aesKey, heartBeatTick));
                    this.b = bootstrap;
                    try {
                        ChannelFuture future = bootstrap.connect(inetHost, inetPort).sync().addListener(new a());
                        Throwable throwable = future.cause();
                        if (throwable != null) {
                            throwable.printStackTrace();
                        }
                        if (future.isSuccess()) {
                            this.c = future.channel();
                            this.d = true;
                        } else {
                            this.d = false;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        this.e.a(inetHost, 0);
                    }
                }
            } else {
                return;
            }
        }
        return;
    }

    /* compiled from: NettyClient */
    public class a implements GenericFutureListener<Future<? super Void>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void operationComplete(Future<? super Void> future) {
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10760, new Class[0], Void.TYPE).isSupported) {
            try {
                Channel channel = this.c;
                if (channel != null) {
                    channel.close().sync();
                }
                EventLoopGroup eventLoopGroup = this.a;
                if (eventLoopGroup != null) {
                    eventLoopGroup.shutdownGracefully().sync();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean c() {
        return this.d;
    }

    public boolean d(byte[] bodyBytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bodyBytes}, this, changeQuickRedirect, false, 10761, new Class[]{byte[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.c == null || !this.d) {
        }
        l msg = new l();
        k protocolHeader = new k();
        protocolHeader.d(7917);
        protocolHeader.e(1);
        int bodySize = bodyBytes.length;
        a.b g = timber.log.a.g("NettyClient");
        g.h("sendMessage: " + bodySize, new Object[0]);
        protocolHeader.c(bodySize);
        msg.d(protocolHeader);
        msg.c(bodyBytes);
        this.c.writeAndFlush(msg).addListener(new b());
        return true;
    }

    /* compiled from: NettyClient */
    public class b extends c {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void c() {
        }

        public void a() {
        }
    }
}
