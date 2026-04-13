package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteOrder;
import java.util.List;

public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteOrder byteOrder;
    private long bytesToDiscard;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final int initialBytesToStrip;
    private final int lengthAdjustment;
    private final int lengthFieldEndOffset;
    private final int lengthFieldLength;
    private final int lengthFieldOffset;
    private final int maxFrameLength;
    private long tooLongFrameLength;

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2) {
        this(maxFrameLength2, lengthFieldOffset2, lengthFieldLength2, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2, int lengthAdjustment2, int initialBytesToStrip2) {
        this(maxFrameLength2, lengthFieldOffset2, lengthFieldLength2, lengthAdjustment2, initialBytesToStrip2, true);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2, int lengthAdjustment2, int initialBytesToStrip2, boolean failFast2) {
        this(ByteOrder.BIG_ENDIAN, maxFrameLength2, lengthFieldOffset2, lengthFieldLength2, lengthAdjustment2, initialBytesToStrip2, failFast2);
    }

    public LengthFieldBasedFrameDecoder(ByteOrder byteOrder2, int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2, int lengthAdjustment2, int initialBytesToStrip2, boolean failFast2) {
        if (byteOrder2 == null) {
            throw new NullPointerException("byteOrder");
        } else if (maxFrameLength2 <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength2);
        } else if (lengthFieldOffset2 < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset2);
        } else if (initialBytesToStrip2 < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip2);
        } else if (lengthFieldOffset2 <= maxFrameLength2 - lengthFieldLength2) {
            this.byteOrder = byteOrder2;
            this.maxFrameLength = maxFrameLength2;
            this.lengthFieldOffset = lengthFieldOffset2;
            this.lengthFieldLength = lengthFieldLength2;
            this.lengthAdjustment = lengthAdjustment2;
            this.lengthFieldEndOffset = lengthFieldOffset2 + lengthFieldLength2;
            this.initialBytesToStrip = initialBytesToStrip2;
            this.failFast = failFast2;
        } else {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength2 + ") must be equal to or greater than lengthFieldOffset (" + lengthFieldOffset2 + ") + lengthFieldLength (" + lengthFieldLength2 + ").");
        }
    }

    /* access modifiers changed from: protected */
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    private void discardingTooLongFrame(ByteBuf in) {
        long bytesToDiscard2 = this.bytesToDiscard;
        int localBytesToDiscard = (int) Math.min(bytesToDiscard2, (long) in.readableBytes());
        in.skipBytes(localBytesToDiscard);
        this.bytesToDiscard = bytesToDiscard2 - ((long) localBytesToDiscard);
        failIfNecessary(false);
    }

    private static void failOnNegativeLengthField(ByteBuf in, long frameLength, int lengthFieldEndOffset2) {
        in.skipBytes(lengthFieldEndOffset2);
        throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
    }

    private static void failOnFrameLengthLessThanLengthFieldEndOffset(ByteBuf in, long frameLength, int lengthFieldEndOffset2) {
        in.skipBytes(lengthFieldEndOffset2);
        throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less than lengthFieldEndOffset: " + lengthFieldEndOffset2);
    }

    private void exceededFrameLength(ByteBuf in, long frameLength) {
        long discard = frameLength - ((long) in.readableBytes());
        this.tooLongFrameLength = frameLength;
        if (discard < 0) {
            in.skipBytes((int) frameLength);
        } else {
            this.discardingTooLongFrame = true;
            this.bytesToDiscard = discard;
            in.skipBytes(in.readableBytes());
        }
        failIfNecessary(true);
    }

    private static void failOnFrameLengthLessThanInitialBytesToStrip(ByteBuf in, long frameLength, int initialBytesToStrip2) {
        in.skipBytes((int) frameLength);
        throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less than initialBytesToStrip: " + initialBytesToStrip2);
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        if (this.discardingTooLongFrame) {
            discardingTooLongFrame(in);
        }
        if (in.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        long frameLength = getUnadjustedFrameLength(in, in.readerIndex() + this.lengthFieldOffset, this.lengthFieldLength, this.byteOrder);
        if (frameLength < 0) {
            failOnNegativeLengthField(in, frameLength, this.lengthFieldEndOffset);
        }
        int i = this.lengthAdjustment;
        int i2 = this.lengthFieldEndOffset;
        long frameLength2 = frameLength + ((long) (i + i2));
        if (frameLength2 < ((long) i2)) {
            failOnFrameLengthLessThanLengthFieldEndOffset(in, frameLength2, i2);
        }
        if (frameLength2 > ((long) this.maxFrameLength)) {
            exceededFrameLength(in, frameLength2);
            return null;
        }
        int frameLengthInt = (int) frameLength2;
        if (in.readableBytes() < frameLengthInt) {
            return null;
        }
        int i3 = this.initialBytesToStrip;
        if (i3 > frameLengthInt) {
            failOnFrameLengthLessThanInitialBytesToStrip(in, frameLength2, i3);
        }
        in.skipBytes(this.initialBytesToStrip);
        int readerIndex = in.readerIndex();
        int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
        ByteBuf frame = extractFrame(ctx, in, readerIndex, actualFrameLength);
        in.readerIndex(readerIndex + actualFrameLength);
        return frame;
    }

    /* access modifiers changed from: protected */
    public long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        ByteBuf buf2 = buf.order(order);
        switch (length) {
            case 1:
                return (long) buf2.getUnsignedByte(offset);
            case 2:
                return (long) buf2.getUnsignedShort(offset);
            case 3:
                return (long) buf2.getUnsignedMedium(offset);
            case 4:
                return buf2.getUnsignedInt(offset);
            case 8:
                return buf2.getLong(offset);
            default:
                throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
        }
    }

    private void failIfNecessary(boolean firstDetectionOfTooLongFrame) {
        if (this.bytesToDiscard == 0) {
            long tooLongFrameLength2 = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            this.discardingTooLongFrame = false;
            if (!this.failFast || firstDetectionOfTooLongFrame) {
                fail(tooLongFrameLength2);
            }
        } else if (this.failFast && firstDetectionOfTooLongFrame) {
            fail(this.tooLongFrameLength);
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.slice(index, length).retain();
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        }
        throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
    }
}
