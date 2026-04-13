package io.netty.handler.codec;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN> extends ChannelDuplexHandler {
    private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder<Object>() {
        public boolean acceptInboundMessage(Object msg) {
            return MessageToMessageCodec.this.acceptInboundMessage(msg);
        }

        /* access modifiers changed from: protected */
        public void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
            MessageToMessageCodec.this.decode(ctx, msg, out);
        }
    };
    private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {
        public boolean acceptOutboundMessage(Object msg) {
            return MessageToMessageCodec.this.acceptOutboundMessage(msg);
        }

        /* access modifiers changed from: protected */
        public void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
            MessageToMessageCodec.this.encode(ctx, msg, out);
        }
    };
    private final TypeParameterMatcher inboundMsgMatcher;
    private final TypeParameterMatcher outboundMsgMatcher;

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, INBOUND_IN inbound_in, List<Object> list);

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, OUTBOUND_IN outbound_in, List<Object> list);

    protected MessageToMessageCodec() {
        Class<MessageToMessageCodec> cls = MessageToMessageCodec.class;
        this.inboundMsgMatcher = TypeParameterMatcher.find(this, cls, "INBOUND_IN");
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, cls, "OUTBOUND_IN");
    }

    protected MessageToMessageCodec(Class<? extends INBOUND_IN> inboundMessageType, Class<? extends OUTBOUND_IN> outboundMessageType) {
        this.inboundMsgMatcher = TypeParameterMatcher.get(inboundMessageType);
        this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        this.decoder.channelRead(ctx, msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        this.encoder.write(ctx, msg, promise);
    }

    public boolean acceptInboundMessage(Object msg) {
        return this.inboundMsgMatcher.match(msg);
    }

    public boolean acceptOutboundMessage(Object msg) {
        return this.outboundMsgMatcher.match(msg);
    }
}
