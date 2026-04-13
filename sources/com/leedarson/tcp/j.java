package com.leedarson.tcp;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.ByteOrder;

/* compiled from: ProtocolEncoder */
public class j extends MessageToByteEncoder<l> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String c;

    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) {
        Class[] clsArr = {ChannelHandlerContext.class, Object.class, ByteBuf.class};
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext, obj, byteBuf}, this, changeQuickRedirect, false, 10772, clsArr, Void.TYPE).isSupported) {
            a(channelHandlerContext, (l) obj, byteBuf);
        }
    }

    public j(String aesKey) {
        this.c = aesKey;
    }

    public void a(ChannelHandlerContext channelHandlerContext, l msg, ByteBuf out) {
        Class[] clsArr = {ChannelHandlerContext.class, l.class, ByteBuf.class};
        if (!PatchProxy.proxy(new Object[]{channelHandlerContext, msg, out}, this, changeQuickRedirect, false, 10771, clsArr, Void.TYPE).isSupported) {
            if (msg == null || msg.b() == null) {
                throw new Exception("The encode message is null");
            }
            k header = msg.b();
            byte[] bodyBytes = msg.a();
            if (!TextUtils.isEmpty(this.c)) {
                bodyBytes = a.b(this.c, bodyBytes);
            }
            int bodySize = bodyBytes.length;
            out.order(ByteOrder.BIG_ENDIAN);
            out.writeShort(header.a());
            out.writeShort(header.b());
            out.writeInt(bodySize);
            out.writeBytes(bodyBytes);
        }
    }
}
