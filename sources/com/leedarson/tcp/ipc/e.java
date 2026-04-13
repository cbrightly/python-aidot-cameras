package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/* compiled from: RecordVideoDecoder */
public class e extends LengthFieldBasedFrameDecoder {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{channelHandlerContext, byteBuf}, this, changeQuickRedirect, false, 10786, new Class[]{ChannelHandlerContext.class, ByteBuf.class}, Object.class);
        return proxy.isSupported ? proxy.result : a(channelHandlerContext, byteBuf);
    }

    public e(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public h a(ChannelHandlerContext ctx, ByteBuf in2) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ctx, in2}, this, changeQuickRedirect2, false, 10785, new Class[]{ChannelHandlerContext.class, ByteBuf.class}, h.class);
        if (proxy.isSupported) {
            return (h) proxy.result;
        }
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null || in.readableBytes() < 18) {
            return null;
        }
        g head = new g();
        head.u(in.readShort());
        head.r(in.readInt());
        head.k(in.readShort());
        head.s(in.readShort());
        head.l(in.readInt());
        int payloadLen = in.readInt();
        if (in.readableBytes() < payloadLen + 19) {
            return null;
        }
        head.t(in.readLong());
        head.m(in.readInt());
        head.n(in.readByte());
        head.q(in.readShort());
        head.p(in.readInt());
        ByteBuf buf = in.readBytes(payloadLen);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        return new h(head, req);
    }
}
