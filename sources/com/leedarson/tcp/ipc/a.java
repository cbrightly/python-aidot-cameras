package com.leedarson.tcp.ipc;

import com.leedarson.tcp.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/* compiled from: INettyClient */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private EventLoopGroup a;
    private Bootstrap b;
    private Channel c;
    private boolean d = false;
    private d e;

    public void d(d listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 10773, new Class[]{d.class}, Void.TYPE).isSupported) {
            if (listener != null) {
                this.e = listener;
                return;
            }
            throw new IllegalArgumentException("listener == null ");
        }
    }

    public synchronized void a(String str, String str2, int i, int i2, short s, short s2, int i3, String str3, boolean z) {
        Class<String> cls = String.class;
        synchronized (this) {
            Object[] objArr = {str, str2, new Integer(i), new Integer(i2), new Short(s), new Short(s2), new Integer(i3), str3, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Integer.TYPE;
            Class cls3 = Short.TYPE;
            Class[] clsArr = {cls, cls, cls2, cls2, cls3, cls3, cls2, cls, Boolean.TYPE};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10774, clsArr, Void.TYPE).isSupported) {
                String inetHost = str2;
                short subCmd = s2;
                int heartbeat = i2;
                String avAESKey = str3;
                boolean isTls = z;
                String sessionId = str;
                int inetPort = i;
                int cmdParam = i3;
                short cmd = s;
                this.a = new NioEventLoopGroup();
                Bootstrap bootstrap = (Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) new Bootstrap().group(this.a)).option(ChannelOption.SO_KEEPALIVE, true)).option(ChannelOption.TCP_NODELAY, true)).channel(NioSocketChannel.class)).handler(new c(sessionId, this.e, heartbeat, cmd, subCmd, cmdParam, avAESKey, isTls));
                this.b = bootstrap;
                try {
                    ChannelFuture future = bootstrap.connect(inetHost, inetPort).sync();
                    if (future == null || !future.isSuccess()) {
                        this.d = false;
                    } else {
                        this.c = future.channel();
                        this.d = true;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    d dVar = this.e;
                    dVar.b(sessionId, 0, "exception:" + e2.toString());
                }
            } else {
                return;
            }
        }
        return;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10775, new Class[0], Void.TYPE).isSupported) {
            this.d = false;
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

    /* renamed from: com.leedarson.tcp.ipc.a$a  reason: collision with other inner class name */
    /* compiled from: INettyClient */
    public class C0185a extends c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ i c;

        C0185a(i iVar) {
            this.c = iVar;
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10777, new Class[0], Void.TYPE).isSupported) {
                this.c.a();
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10778, new Class[0], Void.TYPE).isSupported) {
                this.c.p();
            }
        }
    }

    public void c(h message, i listener) {
        Class[] clsArr = {h.class, i.class};
        if (!PatchProxy.proxy(new Object[]{message, listener}, this, changeQuickRedirect, false, 10776, clsArr, Void.TYPE).isSupported) {
            this.c.writeAndFlush(message).addListener(new C0185a(listener));
        }
    }
}
