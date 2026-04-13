package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class LineBasedFrameDecoder extends ByteToMessageDecoder {
    private int discardedBytes;
    private boolean discarding;
    private final boolean failFast;
    private final int maxLength;
    private int offset;
    private final boolean stripDelimiter;

    public LineBasedFrameDecoder(int maxLength2) {
        this(maxLength2, true, false);
    }

    public LineBasedFrameDecoder(int maxLength2, boolean stripDelimiter2, boolean failFast2) {
        this.maxLength = maxLength2;
        this.failFast = failFast2;
        this.stripDelimiter = stripDelimiter2;
    }

    /* access modifiers changed from: protected */
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteBuf frame;
        int eol = findEndOfLine(buffer);
        int delimLength = 2;
        if (this.discarding) {
            if (eol >= 0) {
                int length = (this.discardedBytes + eol) - buffer.readerIndex();
                if (buffer.getByte(eol) != 13) {
                    delimLength = 1;
                }
                buffer.readerIndex(eol + delimLength);
                this.discardedBytes = 0;
                this.discarding = false;
                if (!this.failFast) {
                    fail(ctx, length);
                }
            } else {
                this.discardedBytes += buffer.readableBytes();
                buffer.readerIndex(buffer.writerIndex());
            }
            return null;
        } else if (eol >= 0) {
            int length2 = eol - buffer.readerIndex();
            if (buffer.getByte(eol) != 13) {
                delimLength = 1;
            }
            if (length2 > this.maxLength) {
                buffer.readerIndex(eol + delimLength);
                fail(ctx, length2);
                return null;
            }
            if (this.stripDelimiter) {
                frame = buffer.readSlice(length2);
                buffer.skipBytes(delimLength);
            } else {
                frame = buffer.readSlice(length2 + delimLength);
            }
            return frame.retain();
        } else {
            int length3 = buffer.readableBytes();
            if (length3 > this.maxLength) {
                this.discardedBytes = length3;
                buffer.readerIndex(buffer.writerIndex());
                this.discarding = true;
                this.offset = 0;
                if (this.failFast) {
                    fail(ctx, "over " + this.discardedBytes);
                }
            }
            return null;
        }
    }

    private void fail(ChannelHandlerContext ctx, int length) {
        fail(ctx, String.valueOf(length));
    }

    private void fail(ChannelHandlerContext ctx, String length) {
        ctx.fireExceptionCaught(new TooLongFrameException("frame length (" + length + ") exceeds the allowed maximum (" + this.maxLength + ')'));
    }

    private int findEndOfLine(ByteBuf buffer) {
        int totalLength = buffer.readableBytes();
        int readerIndex = buffer.readerIndex();
        int i = this.offset;
        int i2 = buffer.forEachByte(readerIndex + i, totalLength - i, ByteBufProcessor.FIND_LF);
        if (i2 >= 0) {
            this.offset = 0;
            if (i2 <= 0 || buffer.getByte(i2 - 1) != 13) {
                return i2;
            }
            return i2 - 1;
        }
        this.offset = totalLength;
        return i2;
    }
}
