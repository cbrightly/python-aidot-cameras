package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;

@ChannelHandler.Sharable
public class LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
    private final ByteOrder byteOrder;
    private final int lengthAdjustment;
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;

    public LengthFieldPrepender(int lengthFieldLength2) {
        this(lengthFieldLength2, false);
    }

    public LengthFieldPrepender(int lengthFieldLength2, boolean lengthIncludesLengthFieldLength2) {
        this(lengthFieldLength2, 0, lengthIncludesLengthFieldLength2);
    }

    public LengthFieldPrepender(int lengthFieldLength2, int lengthAdjustment2) {
        this(lengthFieldLength2, lengthAdjustment2, false);
    }

    public LengthFieldPrepender(int lengthFieldLength2, int lengthAdjustment2, boolean lengthIncludesLengthFieldLength2) {
        this(ByteOrder.BIG_ENDIAN, lengthFieldLength2, lengthAdjustment2, lengthIncludesLengthFieldLength2);
    }

    public LengthFieldPrepender(ByteOrder byteOrder2, int lengthFieldLength2, int lengthAdjustment2, boolean lengthIncludesLengthFieldLength2) {
        if (lengthFieldLength2 == 1 || lengthFieldLength2 == 2 || lengthFieldLength2 == 3 || lengthFieldLength2 == 4 || lengthFieldLength2 == 8) {
            ObjectUtil.checkNotNull(byteOrder2, "byteOrder");
            this.byteOrder = byteOrder2;
            this.lengthFieldLength = lengthFieldLength2;
            this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength2;
            this.lengthAdjustment = lengthAdjustment2;
            return;
        }
        throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength2);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes() + this.lengthAdjustment;
        if (this.lengthIncludesLengthFieldLength) {
            length += this.lengthFieldLength;
        }
        if (length >= 0) {
            switch (this.lengthFieldLength) {
                case 1:
                    if (length < 256) {
                        out.writeByte((byte) length);
                        break;
                    } else {
                        throw new IllegalArgumentException("length does not fit into a byte: " + length);
                    }
                case 2:
                    if (length < 65536) {
                        out.writeShort((short) length);
                        break;
                    } else {
                        throw new IllegalArgumentException("length does not fit into a short integer: " + length);
                    }
                case 3:
                    if (length < 16777216) {
                        out.writeMedium(length);
                        break;
                    } else {
                        throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
                    }
                case 4:
                    out.writeInt(length);
                    break;
                case 8:
                    out.writeLong((long) length);
                    break;
                default:
                    throw new Error("should not reach here");
            }
            out.writeBytes(msg, msg.readerIndex(), msg.readableBytes());
            return;
        }
        throw new IllegalArgumentException("Adjusted frame length (" + length + ") is less than zero");
    }

    /* access modifiers changed from: protected */
    public ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) {
        return super.allocateBuffer(ctx, msg, preferDirect).order(this.byteOrder);
    }
}
