package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageDecoder<I> extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list);

    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    protected MessageToMessageDecoder(Class<? extends I> inboundMessageType) {
        this.matcher = TypeParameterMatcher.get(inboundMessageType);
    }

    public boolean acceptInboundMessage(Object msg) {
        return this.matcher.match(msg);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Object obj;
        CodecOutputList out = CodecOutputList.newInstance();
        try {
            if (acceptInboundMessage(msg)) {
                obj = msg;
                decode(ctx, obj, out);
                ReferenceCountUtil.release(obj);
            } else {
                out.add(msg);
            }
            int size = out.size();
            for (int i = 0; i < size; i++) {
                ctx.fireChannelRead(out.getUnsafe(i));
            }
            out.recycle();
        } catch (DecoderException e) {
            throw e;
        } catch (Exception e2) {
            try {
                throw new DecoderException((Throwable) e2);
            } catch (Throwable th) {
                int size2 = out.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ctx.fireChannelRead(out.getUnsafe(i2));
                }
                out.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            ReferenceCountUtil.release(obj);
            throw th2;
        }
    }
}
