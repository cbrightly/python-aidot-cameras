package com.leedarson.tcp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.nio.charset.Charset;

/* compiled from: NettyClientHandler */
public class e extends SimpleChannelInboundHandler<Object> {
    private static final String c = e.class.getName();
    public static ChangeQuickRedirect changeQuickRedirect;
    private g d;

    public e(g listener) {
        this.d = listener;
    }

    public void channelActive(ChannelHandlerContext ctx) {
        if (!PatchProxy.proxy(new Object[]{ctx}, this, changeQuickRedirect, false, 10762, new Class[]{ChannelHandlerContext.class}, Void.TYPE).isSupported) {
            this.d.a(ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0], 1);
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        if (!PatchProxy.proxy(new Object[]{ctx}, this, changeQuickRedirect, false, 10763, new Class[]{ChannelHandlerContext.class}, Void.TYPE).isSupported) {
            this.d.a(ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0], 2);
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        Class[] clsArr = {ChannelHandlerContext.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{ctx, evt}, this, changeQuickRedirect, false, 10764, clsArr, Void.TYPE).isSupported) {
            super.userEventTriggered(ctx, evt);
            if ((evt instanceof IdleStateEvent) && ((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE) {
                l msg = new l();
                k protocolHeader = new k();
                protocolHeader.d(7917);
                protocolHeader.e(2);
                byte[] bodyBytes = "{ \"service\": \"test\", \"method\": \"pingreq\", \"seq\": \"123456\", \"srcAddr\": \"x.xxxxxxx\", \"payload\": {} }".getBytes(Charset.forName("utf-8"));
                protocolHeader.c(bodyBytes.length);
                msg.d(protocolHeader);
                msg.c(bodyBytes);
                ctx.channel().writeAndFlush(msg);
            }
        }
    }

    public void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext, o}, this, changeQuickRedirect, false, 10765, new Class[]{ChannelHandlerContext.class, Object.class}, Void.TYPE).isSupported) {
            Channel incoming = channelHandlerContext.channel();
            if (o instanceof l) {
                l msg = (l) o;
                String result = incoming.remoteAddress().toString().replace("/", "").split(":")[0];
                new String(msg.a());
                this.d.b(result, new String(msg.a()));
            }
        }
    }
}
