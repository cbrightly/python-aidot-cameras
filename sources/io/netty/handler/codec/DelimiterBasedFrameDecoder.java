package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteBuf[] delimiters;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final LineBasedFrameDecoder lineBasedDecoder;
    private final int maxFrameLength;
    private final boolean stripDelimiter;
    private int tooLongFrameLength;

    public DelimiterBasedFrameDecoder(int maxFrameLength2, ByteBuf delimiter) {
        this(maxFrameLength2, true, delimiter);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength2, boolean stripDelimiter2, ByteBuf delimiter) {
        this(maxFrameLength2, stripDelimiter2, true, delimiter);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength2, boolean stripDelimiter2, boolean failFast2, ByteBuf delimiter) {
        this(maxFrameLength2, stripDelimiter2, failFast2, delimiter.slice(delimiter.readerIndex(), delimiter.readableBytes()));
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength2, ByteBuf... delimiters2) {
        this(maxFrameLength2, true, delimiters2);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength2, boolean stripDelimiter2, ByteBuf... delimiters2) {
        this(maxFrameLength2, stripDelimiter2, true, delimiters2);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength2, boolean stripDelimiter2, boolean failFast2, ByteBuf... delimiters2) {
        validateMaxFrameLength(maxFrameLength2);
        if (delimiters2 == null) {
            throw new NullPointerException("delimiters");
        } else if (delimiters2.length != 0) {
            if (!isLineBased(delimiters2) || isSubclass()) {
                this.delimiters = new ByteBuf[delimiters2.length];
                for (int i = 0; i < delimiters2.length; i++) {
                    ByteBuf d = delimiters2[i];
                    validateDelimiter(d);
                    this.delimiters[i] = d.slice(d.readerIndex(), d.readableBytes());
                }
                this.lineBasedDecoder = null;
            } else {
                this.lineBasedDecoder = new LineBasedFrameDecoder(maxFrameLength2, stripDelimiter2, failFast2);
                this.delimiters = null;
            }
            this.maxFrameLength = maxFrameLength2;
            this.stripDelimiter = stripDelimiter2;
            this.failFast = failFast2;
        } else {
            throw new IllegalArgumentException("empty delimiters");
        }
    }

    private static boolean isLineBased(ByteBuf[] delimiters2) {
        if (delimiters2.length != 2) {
            return false;
        }
        ByteBuf a = delimiters2[0];
        ByteBuf b = delimiters2[1];
        if (a.capacity() < b.capacity()) {
            a = delimiters2[1];
            b = delimiters2[0];
        }
        if (a.capacity() == 2 && b.capacity() == 1 && a.getByte(0) == 13 && a.getByte(1) == 10 && b.getByte(0) == 10) {
            return true;
        }
        return false;
    }

    private boolean isSubclass() {
        return getClass() != DelimiterBasedFrameDecoder.class;
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
        LineBasedFrameDecoder lineBasedFrameDecoder = this.lineBasedDecoder;
        if (lineBasedFrameDecoder != null) {
            return lineBasedFrameDecoder.decode(ctx, buffer);
        }
        int minFrameLength = Integer.MAX_VALUE;
        ByteBuf minDelim = null;
        for (ByteBuf delim : this.delimiters) {
            int frameLength = indexOf(buffer, delim);
            if (frameLength >= 0 && frameLength < minFrameLength) {
                minFrameLength = frameLength;
                minDelim = delim;
            }
        }
        if (minDelim != null) {
            int minDelimLength = minDelim.capacity();
            if (this.discardingTooLongFrame) {
                this.discardingTooLongFrame = false;
                buffer.skipBytes(minFrameLength + minDelimLength);
                int tooLongFrameLength2 = this.tooLongFrameLength;
                this.tooLongFrameLength = 0;
                if (!this.failFast) {
                    fail((long) tooLongFrameLength2);
                }
                return null;
            } else if (minFrameLength > this.maxFrameLength) {
                buffer.skipBytes(minFrameLength + minDelimLength);
                fail((long) minFrameLength);
                return null;
            } else {
                if (this.stripDelimiter) {
                    frame = buffer.readSlice(minFrameLength);
                    buffer.skipBytes(minDelimLength);
                } else {
                    frame = buffer.readSlice(minFrameLength + minDelimLength);
                }
                return frame.retain();
            }
        } else {
            if (this.discardingTooLongFrame != 0) {
                this.tooLongFrameLength += buffer.readableBytes();
                buffer.skipBytes(buffer.readableBytes());
            } else if (buffer.readableBytes() > this.maxFrameLength) {
                this.tooLongFrameLength = buffer.readableBytes();
                buffer.skipBytes(buffer.readableBytes());
                this.discardingTooLongFrame = true;
                if (this.failFast) {
                    fail((long) this.tooLongFrameLength);
                }
            }
            return null;
        }
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }

    private static int indexOf(ByteBuf haystack, ByteBuf needle) {
        for (int i = haystack.readerIndex(); i < haystack.writerIndex(); i++) {
            int haystackIndex = i;
            int needleIndex = 0;
            while (needleIndex < needle.capacity() && haystack.getByte(haystackIndex) == needle.getByte(needleIndex)) {
                haystackIndex++;
                if (haystackIndex == haystack.writerIndex() && needleIndex != needle.capacity() - 1) {
                    return -1;
                }
                needleIndex++;
            }
            if (needleIndex == needle.capacity()) {
                return i - haystack.readerIndex();
            }
        }
        return -1;
    }

    private static void validateDelimiter(ByteBuf delimiter) {
        if (delimiter == null) {
            throw new NullPointerException("delimiter");
        } else if (!delimiter.isReadable()) {
            throw new IllegalArgumentException("empty delimiter");
        }
    }

    private static void validateMaxFrameLength(int maxFrameLength2) {
        if (maxFrameLength2 <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength2);
        }
    }
}
