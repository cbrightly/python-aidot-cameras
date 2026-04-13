package com.leedarson.tcp;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/* compiled from: ProtocolDecoder */
public class i extends LengthFieldBasedFrameDecoder {
    public static ChangeQuickRedirect changeQuickRedirect;
    private short c;
    private short d;
    private int f;
    private String q;

    public /* bridge */ /* synthetic */ Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{channelHandlerContext, byteBuf}, this, changeQuickRedirect, false, 10770, new Class[]{ChannelHandlerContext.class, ByteBuf.class}, Object.class);
        return proxy.isSupported ? proxy.result : a(channelHandlerContext, byteBuf);
    }

    public i(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, String aesKey) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.q = aesKey;
    }

    public l a(ChannelHandlerContext ctx, ByteBuf in2) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ctx, in2}, this, changeQuickRedirect2, false, 10769, new Class[]{ChannelHandlerContext.class, ByteBuf.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null || in.readableBytes() < 8) {
            return null;
        }
        this.c = in.readShort();
        this.d = in.readShort();
        this.f = in.readInt();
        int readableBytes = in.readableBytes();
        int i = this.f;
        if (readableBytes < i) {
            return null;
        }
        ByteBuf buf = in.readBytes(i);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        l msg = new l();
        if (!TextUtils.isEmpty(this.q) && this.d != 3) {
            req = a.a(this.q, req);
            this.f = req.length;
        }
        k protocolHeader = new k(this.c, this.d, this.f);
        msg.c(req);
        msg.d(protocolHeader);
        if (this.d == 3) {
            return null;
        }
        return msg;
    }
}
