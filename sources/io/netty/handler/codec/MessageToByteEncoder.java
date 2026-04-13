package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToByteEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;
    private final boolean preferDirect;

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf);

    protected MessageToByteEncoder() {
        this(true);
    }

    protected MessageToByteEncoder(Class<? extends I> outboundMessageType) {
        this(outboundMessageType, true);
    }

    protected MessageToByteEncoder(boolean preferDirect2) {
        this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
        this.preferDirect = preferDirect2;
    }

    protected MessageToByteEncoder(Class<? extends I> outboundMessageType, boolean preferDirect2) {
        this.matcher = TypeParameterMatcher.get(outboundMessageType);
        this.preferDirect = preferDirect2;
    }

    public boolean acceptOutboundMessage(Object msg) {
        return this.matcher.match(msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        Object obj;
        ByteBuf buf = null;
        try {
            if (acceptOutboundMessage(msg)) {
                obj = msg;
                buf = allocateBuffer(ctx, obj, this.preferDirect);
                encode(ctx, obj, buf);
                ReferenceCountUtil.release(obj);
                if (buf.isReadable()) {
                    ctx.write(buf, promise);
                } else {
                    buf.release();
                    ctx.write(Unpooled.EMPTY_BUFFER, promise);
                }
                buf = null;
            } else {
                ctx.write(msg, promise);
            }
            if (buf != null) {
                buf.release();
            }
        } catch (EncoderException e) {
            throw e;
        } catch (Throwable e2) {
            try {
                throw new EncoderException(e2);
            } catch (Throwable e3) {
                if (buf != null) {
                    buf.release();
                }
                throw e3;
            }
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuf allocateBuffer(ChannelHandlerContext ctx, I i, boolean preferDirect2) {
        if (preferDirect2) {
            return ctx.alloc().ioBuffer();
        }
        return ctx.alloc().heapBuffer();
    }
}
