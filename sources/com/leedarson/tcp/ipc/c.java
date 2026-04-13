package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/* compiled from: INettyClientInitializer */
public class c extends ChannelInitializer<SocketChannel> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private d c;
    private int d = 15;
    private short f;
    private boolean p0;
    private short q;
    private int x;
    private String y;
    private String z;

    public /* bridge */ /* synthetic */ void initChannel(Channel channel) {
        if (!PatchProxy.proxy(new Object[]{channel}, this, changeQuickRedirect, false, 10784, new Class[]{Channel.class}, Void.TYPE).isSupported) {
            a((SocketChannel) channel);
        }
    }

    public c(String sessionId, d listener, int heartbeat, short cmd, short subCmd, int cmdParam, String avAESKey, boolean isTls) {
        if (listener != null) {
            this.y = sessionId;
            this.c = listener;
            this.d = heartbeat;
            this.f = cmd;
            this.q = subCmd;
            this.x = cmdParam;
            this.z = avAESKey;
            this.p0 = isTls;
            return;
        }
        throw new IllegalArgumentException("listener == null ");
    }

    public void a(SocketChannel ch) {
        if (!PatchProxy.proxy(new Object[]{ch}, this, changeQuickRedirect, false, 10783, new Class[]{SocketChannel.class}, Void.TYPE).isSupported) {
            ChannelPipeline pipeline = ch.pipeline();
            if (this.p0) {
                SSLEngine sslEngine = SSLContext.getDefault().createSSLEngine();
                sslEngine.setUseClientMode(true);
                pipeline.addFirst("ssl", (ChannelHandler) new SslHandler(sslEngine));
            }
            pipeline.addLast("IdleStateHandler", (ChannelHandler) new IdleStateHandler(0, this.d, 0));
            pipeline.addLast("decoder", (ChannelHandler) new e(1048576, 14, 4, 19, 0));
            pipeline.addLast("encoder", (ChannelHandler) f.c);
            pipeline.addLast(new b(this.y, this.c, this.f, this.q, this.x));
        }
    }
}
