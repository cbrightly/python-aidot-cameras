package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder {
    static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class.getName() + ".REPLAY");
    private int checkpoint;
    private final ReplayingDecoderByteBuf replayable;
    private S state;

    protected ReplayingDecoder() {
        this((Object) null);
    }

    protected ReplayingDecoder(S initialState) {
        this.replayable = new ReplayingDecoderByteBuf();
        this.checkpoint = -1;
        this.state = initialState;
    }

    /* access modifiers changed from: protected */
    public void checkpoint() {
        this.checkpoint = internalBuffer().readerIndex();
    }

    /* access modifiers changed from: protected */
    public void checkpoint(S state2) {
        checkpoint();
        state(state2);
    }

    /* access modifiers changed from: protected */
    public S state() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public S state(S newState) {
        S oldState = this.state;
        this.state = newState;
        return oldState;
    }

    /* access modifiers changed from: package-private */
    public final void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) {
        try {
            this.replayable.terminate();
            if (this.cumulation != null) {
                callDecode(ctx, internalBuffer(), out);
                decodeLast(ctx, this.replayable, out);
                return;
            }
            this.replayable.setCumulation(Unpooled.EMPTY_BUFFER);
            decodeLast(ctx, this.replayable, out);
        } catch (Signal replay) {
            replay.expect(REPLAY);
        }
    }

    /* access modifiers changed from: protected */
    public void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        this.replayable.setCumulation(in);
        while (in.isReadable()) {
            try {
                int oldReaderIndex = in.readerIndex();
                this.checkpoint = oldReaderIndex;
                int outSize = out.size();
                if (outSize > 0) {
                    ByteToMessageDecoder.fireChannelRead(ctx, out, outSize);
                    out.clear();
                    if (!ctx.isRemoved()) {
                        outSize = 0;
                    } else {
                        return;
                    }
                }
                S oldState = this.state;
                int oldInputLength = in.readableBytes();
                try {
                    decodeRemovalReentryProtection(ctx, this.replayable, out);
                    if (!ctx.isRemoved()) {
                        if (outSize != out.size()) {
                            if (oldReaderIndex == in.readerIndex()) {
                                if (oldState == this.state) {
                                    throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() method must consume the inbound data or change its state if it decoded something.");
                                }
                            }
                            if (isSingleDecode()) {
                                return;
                            }
                        } else if (oldInputLength != in.readableBytes()) {
                            continue;
                        } else if (oldState == this.state) {
                            throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() must consume the inbound data or change its state if it did not decode anything.");
                        }
                    } else {
                        return;
                    }
                } catch (Signal replay) {
                    replay.expect(REPLAY);
                    if (!ctx.isRemoved()) {
                        int checkpoint2 = this.checkpoint;
                        if (checkpoint2 >= 0) {
                            in.readerIndex(checkpoint2);
                            return;
                        }
                        return;
                    }
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Exception cause) {
                throw new DecoderException((Throwable) cause);
            }
        }
    }
}
