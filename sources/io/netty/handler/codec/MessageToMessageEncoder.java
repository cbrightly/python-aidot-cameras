package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list);

    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> outboundMessageType) {
        this.matcher = TypeParameterMatcher.get(outboundMessageType);
    }

    public boolean acceptOutboundMessage(Object msg) {
        return this.matcher.match(msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ChannelPromise p;
        ChannelPromise p2;
        Object obj;
        CodecOutputList out = null;
        boolean isVoidPromise = false;
        try {
            if (acceptOutboundMessage(msg)) {
                out = CodecOutputList.newInstance();
                obj = msg;
                encode(ctx, obj, out);
                ReferenceCountUtil.release(obj);
                if (out.isEmpty()) {
                    out.recycle();
                    throw new EncoderException(StringUtil.simpleClassName((Object) this) + " must produce at least one message.");
                }
            } else {
                ctx.write(msg, promise);
            }
            if (out != null) {
                int sizeMinusOne = out.size() - 1;
                if (sizeMinusOne == 0) {
                    ctx.write(out.get(0), promise);
                } else if (sizeMinusOne > 0) {
                    ChannelPromise voidPromise = ctx.voidPromise();
                    if (promise == voidPromise) {
                        isVoidPromise = true;
                    }
                    for (int i = 0; i < sizeMinusOne; i++) {
                        if (isVoidPromise) {
                            p2 = voidPromise;
                        } else {
                            p2 = ctx.newPromise();
                        }
                        ctx.write(out.getUnsafe(i), p2);
                    }
                    ctx.write(out.getUnsafe(sizeMinusOne), promise);
                }
                out.recycle();
            }
        } catch (EncoderException e) {
            throw e;
        } catch (Throwable t) {
            try {
                throw new EncoderException(t);
            } catch (Throwable e2) {
                if (out != null) {
                    int sizeMinusOne2 = out.size() - 1;
                    if (sizeMinusOne2 == 0) {
                        ctx.write(out.get(0), promise);
                    } else if (sizeMinusOne2 > 0) {
                        ChannelPromise voidPromise2 = ctx.voidPromise();
                        if (promise == voidPromise2) {
                            isVoidPromise = true;
                        }
                        for (int i2 = 0; i2 < sizeMinusOne2; i2++) {
                            if (isVoidPromise) {
                                p = voidPromise2;
                            } else {
                                p = ctx.newPromise();
                            }
                            ctx.write(out.getUnsafe(i2), p);
                        }
                        ctx.write(out.getUnsafe(sizeMinusOne2), promise);
                    }
                    out.recycle();
                }
                throw e2;
            }
        }
    }
}
