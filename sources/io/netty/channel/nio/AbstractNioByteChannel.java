package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')');
    private Runnable flushTask;

    /* access modifiers changed from: protected */
    public abstract int doReadBytes(ByteBuf byteBuf);

    /* access modifiers changed from: protected */
    public abstract int doWriteBytes(ByteBuf byteBuf);

    /* access modifiers changed from: protected */
    public abstract long doWriteFileRegion(FileRegion fileRegion);

    protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
        super(parent, ch, 1);
    }

    /* access modifiers changed from: protected */
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    public class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
        private RecvByteBufAllocator.Handle allocHandle;

        protected NioByteUnsafe() {
            super();
        }

        private void closeOnRead(ChannelPipeline pipeline) {
            SelectionKey key = AbstractNioByteChannel.this.selectionKey();
            AbstractNioByteChannel.this.setInputShutdown();
            if (!AbstractNioByteChannel.this.isOpen()) {
                return;
            }
            if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                key.interestOps(key.interestOps() & (~AbstractNioByteChannel.this.readInterestOp));
                pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                return;
            }
            close(voidPromise());
        }

        private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    AbstractNioByteChannel.this.setReadPending(false);
                    pipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            pipeline.fireChannelReadComplete();
            pipeline.fireExceptionCaught(cause);
            if (close || (cause instanceof IOException)) {
                closeOnRead(pipeline);
            }
        }

        public final void read() {
            ChannelConfig config = AbstractNioByteChannel.this.config();
            if (config.isAutoRead() || AbstractNioByteChannel.this.isReadPending()) {
                ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
                ByteBufAllocator allocator = config.getAllocator();
                int maxMessagesPerRead = config.getMaxMessagesPerRead();
                RecvByteBufAllocator.Handle allocHandle2 = this.allocHandle;
                if (allocHandle2 == null) {
                    RecvByteBufAllocator.Handle newHandle = config.getRecvByteBufAllocator().newHandle();
                    allocHandle2 = newHandle;
                    this.allocHandle = newHandle;
                }
                ByteBuf byteBuf = null;
                int messages = 0;
                boolean close = false;
                int totalReadAmount = 0;
                boolean readPendingReset = false;
                while (true) {
                    try {
                        ByteBuf byteBuf2 = allocHandle2.allocate(allocator);
                        int writable = byteBuf2.writableBytes();
                        int localReadAmount = AbstractNioByteChannel.this.doReadBytes(byteBuf2);
                        if (localReadAmount > 0) {
                            if (!readPendingReset) {
                                readPendingReset = true;
                                AbstractNioByteChannel.this.setReadPending(false);
                            }
                            pipeline.fireChannelRead(byteBuf2);
                            byteBuf = null;
                            if (totalReadAmount < Integer.MAX_VALUE - localReadAmount) {
                                totalReadAmount += localReadAmount;
                                if (config.isAutoRead()) {
                                    if (localReadAmount >= writable) {
                                        messages++;
                                        if (messages >= maxMessagesPerRead) {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            } else {
                                totalReadAmount = Integer.MAX_VALUE;
                                break;
                            }
                        } else {
                            byteBuf2.release();
                            close = localReadAmount < 0;
                            if (close) {
                                AbstractNioByteChannel.this.setReadPending(false);
                            }
                        }
                    } catch (Throwable th) {
                        if (!config.isAutoRead() && !AbstractNioByteChannel.this.isReadPending()) {
                            removeReadOp();
                        }
                        throw th;
                    }
                }
                pipeline.fireChannelReadComplete();
                allocHandle2.record(totalReadAmount);
                if (close) {
                    closeOnRead(pipeline);
                }
                if (config.isAutoRead() != 0 || AbstractNioByteChannel.this.isReadPending()) {
                    return;
                }
                removeReadOp();
                return;
            }
            removeReadOp();
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        int writeSpinCount = -1;
        boolean setOpWrite = false;
        while (true) {
            Object msg = in.current();
            if (msg == null) {
                clearOpWrite();
                return;
            } else if (msg instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf) msg;
                if (buf.readableBytes() != 0) {
                    boolean done = false;
                    long flushedAmount = 0;
                    if (writeSpinCount == -1) {
                        writeSpinCount = config().getWriteSpinCount();
                    }
                    int i = writeSpinCount - 1;
                    while (true) {
                        if (i < 0) {
                            break;
                        }
                        int localFlushedAmount = doWriteBytes(buf);
                        if (localFlushedAmount == 0) {
                            setOpWrite = true;
                            break;
                        }
                        flushedAmount += (long) localFlushedAmount;
                        if (!buf.isReadable()) {
                            done = true;
                            break;
                        }
                        i--;
                    }
                    in.progress(flushedAmount);
                    if (!done) {
                        break;
                    }
                    in.remove();
                } else {
                    in.remove();
                }
            } else if (msg instanceof FileRegion) {
                FileRegion region = (FileRegion) msg;
                boolean done2 = region.transfered() >= region.count();
                if (!done2) {
                    long flushedAmount2 = 0;
                    if (writeSpinCount == -1) {
                        writeSpinCount = config().getWriteSpinCount();
                    }
                    int i2 = writeSpinCount - 1;
                    while (true) {
                        if (i2 < 0) {
                            break;
                        }
                        long localFlushedAmount2 = doWriteFileRegion(region);
                        if (localFlushedAmount2 == 0) {
                            setOpWrite = true;
                            break;
                        }
                        flushedAmount2 += localFlushedAmount2;
                        if (region.transfered() >= region.count()) {
                            done2 = true;
                            break;
                        }
                        i2--;
                    }
                    in.progress(flushedAmount2);
                }
                if (!done2) {
                    break;
                }
                in.remove();
            } else {
                throw new Error();
            }
        }
        incompleteWrite(setOpWrite);
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.isDirect()) {
                return msg;
            }
            return newDirectBuffer(buf);
        } else if (msg instanceof FileRegion) {
            return msg;
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    /* access modifiers changed from: protected */
    public final void incompleteWrite(boolean setOpWrite) {
        if (setOpWrite) {
            setOpWrite();
            return;
        }
        Runnable flushTask2 = this.flushTask;
        if (flushTask2 == null) {
            AnonymousClass1 r1 = new Runnable() {
                public void run() {
                    AbstractNioByteChannel.this.flush();
                }
            };
            this.flushTask = r1;
            flushTask2 = r1;
        }
        eventLoop().execute(flushTask2);
    }

    /* access modifiers changed from: protected */
    public final void setOpWrite() {
        SelectionKey key = selectionKey();
        if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((interestOps & 4) == 0) {
                key.interestOps(interestOps | 4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void clearOpWrite() {
        SelectionKey key = selectionKey();
        if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((interestOps & 4) != 0) {
                key.interestOps(interestOps & -5);
            }
        }
    }
}
