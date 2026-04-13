package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ChunkedWriteHandler extends ChannelDuplexHandler {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ChunkedWriteHandler.class);
    private volatile ChannelHandlerContext ctx;
    private PendingWrite currentWrite;
    private final Queue<PendingWrite> queue = new ArrayDeque();

    public ChunkedWriteHandler() {
    }

    @Deprecated
    public ChunkedWriteHandler(int maxPendingWrites) {
        if (maxPendingWrites <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx2) {
        this.ctx = ctx2;
    }

    public void resumeTransfer() {
        final ChannelHandlerContext ctx2 = this.ctx;
        if (ctx2 != null) {
            if (ctx2.executor().inEventLoop()) {
                try {
                    doFlush(ctx2);
                } catch (Exception e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Unexpected exception while sending chunks.", (Throwable) e);
                    }
                }
            } else {
                ctx2.executor().execute(new Runnable() {
                    public void run() {
                        try {
                            ChunkedWriteHandler.this.doFlush(ctx2);
                        } catch (Exception e) {
                            if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                                ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", (Throwable) e);
                            }
                        }
                    }
                });
            }
        }
    }

    public void write(ChannelHandlerContext ctx2, Object msg, ChannelPromise promise) {
        this.queue.add(new PendingWrite(msg, promise));
    }

    public void flush(ChannelHandlerContext ctx2) {
        doFlush(ctx2);
    }

    public void channelInactive(ChannelHandlerContext ctx2) {
        doFlush(ctx2);
        ctx2.fireChannelInactive();
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx2) {
        if (ctx2.channel().isWritable()) {
            doFlush(ctx2);
        }
        ctx2.fireChannelWritabilityChanged();
    }

    private void discard(Throwable cause) {
        while (true) {
            PendingWrite currentWrite2 = this.currentWrite;
            if (this.currentWrite == null) {
                currentWrite2 = this.queue.poll();
            } else {
                this.currentWrite = null;
            }
            if (currentWrite2 != null) {
                Object message = currentWrite2.msg;
                if (message instanceof ChunkedInput) {
                    ChunkedInput<?> in = (ChunkedInput) message;
                    try {
                        if (!in.isEndOfInput()) {
                            if (cause == null) {
                                cause = new ClosedChannelException();
                            }
                            currentWrite2.fail(cause);
                        } else {
                            currentWrite2.success();
                        }
                        closeInput(in);
                    } catch (Exception e) {
                        currentWrite2.fail(e);
                        InternalLogger internalLogger = logger;
                        internalLogger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", (Throwable) e);
                        closeInput(in);
                    }
                } else {
                    if (cause == null) {
                        cause = new ClosedChannelException();
                    }
                    currentWrite2.fail(cause);
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void doFlush(ChannelHandlerContext ctx2) {
        PendingWrite pendingWrite;
        boolean suspend;
        Object obj;
        ChannelHandlerContext channelHandlerContext = ctx2;
        Channel channel = ctx2.channel();
        PendingWrite pendingWrite2 = null;
        if (!channel.isActive()) {
            discard((Throwable) null);
            return;
        }
        boolean requiresFlush = true;
        while (true) {
            if (!channel.isWritable()) {
                break;
            }
            if (this.currentWrite == null) {
                this.currentWrite = this.queue.poll();
            }
            if (this.currentWrite == null) {
                break;
            }
            final PendingWrite currentWrite2 = this.currentWrite;
            final Object pendingMessage = currentWrite2.msg;
            if (pendingMessage instanceof ChunkedInput) {
                final ChunkedInput<?> chunks = (ChunkedInput) pendingMessage;
                try {
                    Object readChunk = chunks.readChunk(channelHandlerContext);
                    boolean endOfInput = chunks.isEndOfInput();
                    if (readChunk == null) {
                        suspend = !endOfInput;
                    } else {
                        suspend = false;
                    }
                    if (suspend) {
                        break;
                    }
                    if (readChunk == null) {
                        obj = Unpooled.EMPTY_BUFFER;
                    } else {
                        obj = readChunk;
                    }
                    final int amount = amount(obj);
                    ChannelFuture f = channelHandlerContext.write(obj);
                    if (endOfInput) {
                        this.currentWrite = pendingWrite2;
                        f.addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture future) {
                                currentWrite2.progress(amount);
                                currentWrite2.success();
                                ChunkedWriteHandler.closeInput(chunks);
                            }
                        });
                        boolean z = endOfInput;
                        ChannelFuture channelFuture = f;
                        int i = amount;
                        Object obj2 = obj;
                    } else if (channel.isWritable()) {
                        f.addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture future) {
                                if (!future.isSuccess()) {
                                    ChunkedWriteHandler.closeInput((ChunkedInput) pendingMessage);
                                    currentWrite2.fail(future.cause());
                                    return;
                                }
                                currentWrite2.progress(amount);
                            }
                        });
                        boolean z2 = endOfInput;
                        ChannelFuture channelFuture2 = f;
                        int i2 = amount;
                        Object obj3 = obj;
                    } else {
                        AnonymousClass4 r10 = r1;
                        final Object obj4 = pendingMessage;
                        boolean z3 = endOfInput;
                        ChannelFuture f2 = f;
                        final PendingWrite pendingWrite3 = currentWrite2;
                        int i3 = amount;
                        Object obj5 = obj;
                        final Channel channel2 = channel;
                        AnonymousClass4 r1 = new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture future) {
                                if (!future.isSuccess()) {
                                    ChunkedWriteHandler.closeInput((ChunkedInput) obj4);
                                    pendingWrite3.fail(future.cause());
                                    return;
                                }
                                pendingWrite3.progress(amount);
                                if (channel2.isWritable()) {
                                    ChunkedWriteHandler.this.resumeTransfer();
                                }
                            }
                        };
                        f2.addListener(r10);
                    }
                    ctx2.flush();
                    requiresFlush = false;
                    pendingWrite = null;
                } catch (Throwable t) {
                    this.currentWrite = null;
                    if (0 != 0) {
                        ReferenceCountUtil.release((Object) null);
                    }
                    currentWrite2.fail(t);
                    closeInput(chunks);
                }
            } else {
                channelHandlerContext.write(pendingMessage, currentWrite2.promise);
                pendingWrite = null;
                this.currentWrite = null;
                requiresFlush = true;
            }
            if (!channel.isActive()) {
                discard(new ClosedChannelException());
                break;
            }
            pendingWrite2 = pendingWrite;
        }
        if (requiresFlush) {
            ctx2.flush();
        }
    }

    static void closeInput(ChunkedInput<?> chunks) {
        try {
            chunks.close();
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close a chunked input.", t);
            }
        }
    }

    public static final class PendingWrite {
        final Object msg;
        private long progress;
        final ChannelPromise promise;

        PendingWrite(Object msg2, ChannelPromise promise2) {
            this.msg = msg2;
            this.promise = promise2;
        }

        /* access modifiers changed from: package-private */
        public void fail(Throwable cause) {
            ReferenceCountUtil.release(this.msg);
            this.promise.tryFailure(cause);
        }

        /* access modifiers changed from: package-private */
        public void success() {
            if (!this.promise.isDone()) {
                ChannelPromise channelPromise = this.promise;
                if (channelPromise instanceof ChannelProgressivePromise) {
                    long j = this.progress;
                    ((ChannelProgressivePromise) channelPromise).tryProgress(j, j);
                }
                this.promise.trySuccess();
            }
        }

        /* access modifiers changed from: package-private */
        public void progress(int amount) {
            long j = this.progress + ((long) amount);
            this.progress = j;
            ChannelPromise channelPromise = this.promise;
            if (channelPromise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise) channelPromise).tryProgress(j, -1);
            }
        }
    }

    private static int amount(Object msg) {
        if (msg instanceof ByteBuf) {
            return ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof ByteBufHolder) {
            return ((ByteBufHolder) msg).content().readableBytes();
        }
        return 1;
    }
}
