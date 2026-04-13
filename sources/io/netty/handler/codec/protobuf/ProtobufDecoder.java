package io.netty.handler.codec.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public class ProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final boolean HAS_PARSER;
    private final ExtensionRegistryLite extensionRegistry;
    private final MessageLite prototype;

    static {
        boolean hasParser = false;
        try {
            MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
            hasParser = true;
        } catch (Throwable th) {
        }
        HAS_PARSER = hasParser;
    }

    public ProtobufDecoder(MessageLite prototype2) {
        this(prototype2, (ExtensionRegistry) null);
    }

    public ProtobufDecoder(MessageLite prototype2, ExtensionRegistry extensionRegistry2) {
        this(prototype2, (ExtensionRegistryLite) extensionRegistry2);
    }

    public ProtobufDecoder(MessageLite prototype2, ExtensionRegistryLite extensionRegistry2) {
        if (prototype2 != null) {
            this.prototype = prototype2.getDefaultInstanceForType();
            this.extensionRegistry = extensionRegistry2;
            return;
        }
        throw new NullPointerException("prototype");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int offset;
        byte[] array;
        int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = new byte[length];
            msg.getBytes(msg.readerIndex(), array, 0, length);
            offset = 0;
        }
        if (this.extensionRegistry == null) {
            if (HAS_PARSER) {
                out.add(this.prototype.getParserForType().parseFrom(array, offset, length));
            } else {
                out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length).build());
            }
        } else if (HAS_PARSER) {
            out.add(this.prototype.getParserForType().parseFrom(array, offset, length, this.extensionRegistry));
        } else {
            out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length, this.extensionRegistry).build());
        }
    }
}
