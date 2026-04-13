package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
    public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            CompositeByteBuf composite;
            if (cumulation.refCnt() > 1) {
                ByteBuf buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
                buffer.writeBytes(in);
                in.release();
                return buffer;
            }
            if (cumulation instanceof CompositeByteBuf) {
                composite = (CompositeByteBuf) cumulation;
            } else {
                composite = alloc.compositeBuffer(Integer.MAX_VALUE);
                composite.addComponent(true, cumulation);
            }
            composite.addComponent(true, in);
            CompositeByteBuf compositeByteBuf = composite;
            return composite;
        }
    };
    public static final Cumulator MERGE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            ByteBuf buffer;
            if (cumulation.writerIndex() > cumulation.maxCapacity() - in.readableBytes() || cumulation.refCnt() > 1 || (cumulation instanceof ReadOnlyByteBuf)) {
                buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
            } else {
                buffer = cumulation;
            }
            buffer.writeBytes(in);
            in.release();
            return buffer;
        }
    };
    private static final byte STATE_CALLING_CHILD_DECODE = 1;
    private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
    private static final byte STATE_INIT = 0;
    ByteBuf cumulation;
    private Cumulator cumulator = MERGE_CUMULATOR;
    private byte decodeState = 0;
    private boolean decodeWasNull;
    private int discardAfterReads = 16;
    private boolean first;
    private int numReads;
    private boolean singleDecode;

    public interface Cumulator {
        ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2);
    }

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list);

    protected ByteToMessageDecoder() {
        ensureNotSharable();
    }

    public void setSingleDecode(boolean singleDecode2) {
        this.singleDecode = singleDecode2;
    }

    public boolean isSingleDecode() {
        return this.singleDecode;
    }

    public void setCumulator(Cumulator cumulator2) {
        if (cumulator2 != null) {
            this.cumulator = cumulator2;
            return;
        }
        throw new NullPointerException("cumulator");
    }

    public void setDiscardAfterReads(int discardAfterReads2) {
        if (discardAfterReads2 > 0) {
            this.discardAfterReads = discardAfterReads2;
            return;
        }
        throw new IllegalArgumentException("discardAfterReads must be > 0");
    }

    /* access modifiers changed from: protected */
    public int actualReadableBytes() {
        return internalBuffer().readableBytes();
    }

    /* access modifiers changed from: protected */
    public ByteBuf internalBuffer() {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            return byteBuf;
        }
        return Unpooled.EMPTY_BUFFER;
    }

    public final void handlerRemoved(ChannelHandlerContext ctx) {
        if (this.decodeState == 1) {
            this.decodeState = 2;
            return;
        }
        ByteBuf buf = this.cumulation;
        if (buf != null) {
            this.cumulation = null;
            int readable = buf.readableBytes();
            if (readable > 0) {
                ByteBuf bytes = buf.readBytes(readable);
                buf.release();
                ctx.fireChannelRead(bytes);
            } else {
                buf.release();
            }
            this.numReads = 0;
            ctx.fireChannelReadComplete();
        }
        handlerRemoved0(ctx);
    }

    /* access modifiers changed from: protected */
    public void handlerRemoved0(ChannelHandlerContext ctx) {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            CodecOutputList out = CodecOutputList.newInstance();
            try {
                ByteBuf data = (ByteBuf) msg;
                boolean z = this.cumulation == null;
                this.first = z;
                if (z) {
                    this.cumulation = data;
                } else {
                    this.cumulation = this.cumulator.cumulate(ctx.alloc(), this.cumulation, data);
                }
                callDecode(ctx, this.cumulation, out);
                ByteBuf data2 = this.cumulation;
                if (data2 == null || data2.isReadable()) {
                    int i = this.numReads + 1;
                    this.numReads = i;
                    if (i >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int size = out.size();
                this.decodeWasNull = !out.insertSinceRecycled();
                fireChannelRead(ctx, out, size);
                out.recycle();
            } catch (DecoderException e) {
                throw e;
            } catch (Exception e2) {
                throw new DecoderException((Throwable) e2);
            } catch (Throwable th) {
                ByteBuf byteBuf = this.cumulation;
                if (byteBuf == null || byteBuf.isReadable()) {
                    int i2 = this.numReads + 1;
                    this.numReads = i2;
                    if (i2 >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int size2 = out.size();
                this.decodeWasNull = !out.insertSinceRecycled();
                fireChannelRead(ctx, out, size2);
                out.recycle();
                throw th;
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    static void fireChannelRead(ChannelHandlerContext ctx, List<Object> msgs, int numElements) {
        if (msgs instanceof CodecOutputList) {
            fireChannelRead(ctx, (CodecOutputList) msgs, numElements);
            return;
        }
        for (int i = 0; i < numElements; i++) {
            ctx.fireChannelRead(msgs.get(i));
        }
    }

    static void fireChannelRead(ChannelHandlerContext ctx, CodecOutputList msgs, int numElements) {
        for (int i = 0; i < numElements; i++) {
            ctx.fireChannelRead(msgs.getUnsafe(i));
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        this.numReads = 0;
        discardSomeReadBytes();
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
            }
        }
        ctx.fireChannelReadComplete();
    }

    /* access modifiers changed from: protected */
    public final void discardSomeReadBytes() {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null && !this.first && byteBuf.refCnt() == 1) {
            this.cumulation.discardSomeReadBytes();
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        channelInputClosed(ctx, true);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof ChannelInputShutdownEvent) {
            channelInputClosed(ctx, false);
        }
        super.userEventTriggered(ctx, evt);
    }

    private void channelInputClosed(ChannelHandlerContext ctx, boolean callChannelInactive) {
        CodecOutputList out = CodecOutputList.newInstance();
        try {
            channelInputClosed(ctx, (List<Object>) out);
            try {
                ByteBuf byteBuf = this.cumulation;
                if (byteBuf != null) {
                    byteBuf.release();
                    this.cumulation = null;
                }
                int size = out.size();
                fireChannelRead(ctx, out, size);
                if (size > 0) {
                    ctx.fireChannelReadComplete();
                }
                if (callChannelInactive) {
                    ctx.fireChannelInactive();
                }
            } finally {
                out.recycle();
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Exception e2) {
            throw new DecoderException((Throwable) e2);
        } catch (Throwable th) {
            out.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            callDecode(ctx, byteBuf, out);
            decodeLast(ctx, this.cumulation, out);
            return;
        }
        decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
    }

    /* access modifiers changed from: protected */
    public void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (in.isReadable()) {
            try {
                int outSize = out.size();
                if (outSize > 0) {
                    fireChannelRead(ctx, out, outSize);
                    out.clear();
                    if (!ctx.isRemoved()) {
                        outSize = 0;
                    } else {
                        return;
                    }
                }
                int oldInputLength = in.readableBytes();
                decodeRemovalReentryProtection(ctx, in, out);
                if (!ctx.isRemoved()) {
                    if (outSize == out.size()) {
                        if (oldInputLength == in.readableBytes()) {
                            return;
                        }
                    } else if (oldInputLength == in.readableBytes()) {
                        throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
                    } else if (isSingleDecode()) {
                        return;
                    }
                } else {
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Exception cause) {
                throw new DecoderException((Throwable) cause);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void decodeRemovalReentryProtection(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        boolean removePending = true;
        this.decodeState = removePending;
        try {
            decode(ctx, in, out);
        } finally {
            if (this.decodeState != 2) {
                removePending = false;
            }
            this.decodeState = 0;
            if (removePending) {
                handlerRemoved(ctx);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.isReadable()) {
            decodeRemovalReentryProtection(ctx, in, out);
        }
    }

    static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation2, int readable) {
        ByteBuf oldCumulation = cumulation2;
        ByteBuf cumulation3 = alloc.buffer(oldCumulation.readableBytes() + readable);
        cumulation3.writeBytes(oldCumulation);
        oldCumulation.release();
        return cumulation3;
    }
}
