package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Random;

/* compiled from: INettyClientHandler */
public class b extends SimpleChannelInboundHandler<Object> {
    private static final String c = b.class.getName();
    public static ChangeQuickRedirect changeQuickRedirect;
    private d d;
    private short f;
    private short q;
    private int x;
    private String y;

    public b(String sessionId, d listener, short cmd, short subCmd, int cmdParam) {
        this.y = sessionId;
        this.d = listener;
        this.f = cmd;
        this.q = subCmd;
        this.x = cmdParam;
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext}, this, changeQuickRedirect, false, 10779, new Class[]{ChannelHandlerContext.class}, Void.TYPE).isSupported) {
            this.d.b(this.y, 1, "messageChannelActive");
        }
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext}, this, changeQuickRedirect, false, 10780, new Class[]{ChannelHandlerContext.class}, Void.TYPE).isSupported) {
            this.d.b(this.y, 2, "channelInactive:");
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (!PatchProxy.proxy(new Object[]{ctx, evt}, this, changeQuickRedirect, false, 10781, new Class[]{ChannelHandlerContext.class, Object.class}, Void.TYPE).isSupported) {
            super.userEventTriggered(ctx, evt);
            if ((evt instanceof IdleStateEvent) && ((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE) {
                g head = new g();
                head.u(256);
                head.r(new Random().nextInt());
                head.k(this.f);
                head.s(this.q);
                head.l(this.x);
                head.t(System.currentTimeMillis());
                head.m(1005);
                head.n(0);
                head.q(4);
                head.p(2);
                ctx.channel().writeAndFlush(new h(head));
            }
        }
    }

    public void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        Class[] clsArr = {ChannelHandlerContext.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext, o}, this, changeQuickRedirect, false, 10782, clsArr, Void.TYPE).isSupported) {
            Channel channel = channelHandlerContext.channel();
            if (o instanceof h) {
                h msg = (h) o;
                if (msg.a().a() != 262) {
                    new String(msg.b(), "UTF-8");
                    this.d.a(this.y, msg);
                }
            }
        }
    }
}
