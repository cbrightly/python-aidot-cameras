package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class ByteToMessageCodec<I> extends ChannelDuplexHandler {
    private final ByteToMessageDecoder decoder;
    private final MessageToByteEncoder<I> encoder;
    private final TypeParameterMatcher outboundMsgMatcher;

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list);

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf);

    protected ByteToMessageCodec() {
        this(true);
    }

    protected ByteToMessageCodec(Class<? extends I> outboundMessageType) {
        this(outboundMessageType, true);
    }

    protected ByteToMessageCodec(boolean preferDirect) {
        this.decoder = new ByteToMessageDecoder() {
            public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                ByteToMessageCodec.this.decode(ctx, in, out);
            }

            /* access modifiers changed from: protected */
            public void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                ByteToMessageCodec.this.decodeLast(ctx, in, out);
            }
        };
        ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.encoder = new Encoder(preferDirect);
    }

    protected ByteToMessageCodec(Class<? extends I> outboundMessageType, boolean preferDirect) {
        this.decoder = new ByteToMessageDecoder() {
            public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                ByteToMessageCodec.this.decode(ctx, in, out);
            }

            /* access modifiers changed from: protected */
            public void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
                ByteToMessageCodec.this.decodeLast(ctx, in, out);
            }
        };
        ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
        this.encoder = new Encoder(preferDirect);
    }

    public boolean acceptOutboundMessage(Object msg) {
        return this.outboundMsgMatcher.match(msg);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        this.decoder.channelRead(ctx, msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        this.encoder.write(ctx, msg, promise);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        this.decoder.channelReadComplete(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        this.decoder.channelInactive(ctx);
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        try {
            this.decoder.handlerAdded(ctx);
        } finally {
            this.encoder.handlerAdded(ctx);
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        try {
            this.decoder.handlerRemoved(ctx);
        } finally {
            this.encoder.handlerRemoved(ctx);
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.isReadable()) {
            decode(ctx, in, out);
        }
    }

    public final class Encoder extends MessageToByteEncoder<I> {
        Encoder(boolean preferDirect) {
            super(preferDirect);
        }

        public boolean acceptOutboundMessage(Object msg) {
            return ByteToMessageCodec.this.acceptOutboundMessage(msg);
        }

        /* access modifiers changed from: protected */
        public void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) {
            ByteToMessageCodec.this.encode(ctx, msg, out);
        }
    }
}
