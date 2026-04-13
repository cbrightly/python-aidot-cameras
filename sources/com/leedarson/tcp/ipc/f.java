package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* compiled from: RecordVideoEncoder */
public class f extends MessageToMessageEncoder<h> {
    public static final f c = new f();
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) {
        Class[] clsArr = {ChannelHandlerContext.class, Object.class, List.class};
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext, obj, list}, this, changeQuickRedirect, false, 10790, clsArr, Void.TYPE).isSupported) {
            b(channelHandlerContext, (h) obj, list);
        }
    }

    private f() {
    }

    public void b(ChannelHandlerContext ctx, h msg, List<Object> out) {
        Class[] clsArr = {ChannelHandlerContext.class, h.class, List.class};
        if (!PatchProxy.proxy(new Object[]{ctx, msg, out}, this, changeQuickRedirect, false, 10787, clsArr, Void.TYPE).isSupported) {
            out.add(a(ctx.alloc(), msg));
        }
    }

    private static ByteBuf a(ByteBufAllocator byteBufAllocator, h message) {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{byteBufAllocator, message}, (Object) null, changeQuickRedirect, true, 10788, new Class[]{ByteBufAllocator.class, h.class}, ByteBuf.class);
        if (proxy.isSupported) {
            return (ByteBuf) proxy.result;
        }
        if (message.b() != null) {
            i = message.b().length;
        }
        int payloadLen = i;
        ByteBuf buf = byteBufAllocator.buffer(payloadLen + 37);
        g header = message.a();
        buf.writeShort(header.j());
        buf.writeInt(header.g());
        buf.writeShort(header.a());
        buf.writeShort(header.h());
        buf.writeInt(header.b());
        buf.writeInt(payloadLen);
        buf.writeLong(header.i());
        buf.writeInt(header.c());
        buf.writeByte(header.d());
        buf.writeShort(header.f());
        buf.writeInt(header.e());
        if (payloadLen > 0) {
            buf.writeBytes(message.b());
        }
        return buf;
    }
}
