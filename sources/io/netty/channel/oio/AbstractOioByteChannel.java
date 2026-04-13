package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

public abstract class AbstractOioByteChannel extends AbstractOioChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private RecvByteBufAllocator.Handle allocHandle;
    private volatile boolean inputShutdown;

    /* access modifiers changed from: protected */
    public abstract int available();

    /* access modifiers changed from: protected */
    public abstract int doReadBytes(ByteBuf byteBuf);

    /* access modifiers changed from: protected */
    public abstract void doWriteBytes(ByteBuf byteBuf);

    /* access modifiers changed from: protected */
    public abstract void doWriteFileRegion(FileRegion fileRegion);

    protected AbstractOioByteChannel(Channel parent) {
        super(parent);
    }

    /* access modifiers changed from: protected */
    public boolean isInputShutdown() {
        return this.inputShutdown;
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    /* access modifiers changed from: protected */
    public boolean checkInputShutdown() {
        if (!this.inputShutdown) {
            return false;
        }
        try {
            Thread.sleep(1000);
            return true;
        } catch (InterruptedException e) {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void doRead() {
        RecvByteBufAllocator.Handle allocHandle2;
        if (!checkInputShutdown()) {
            ChannelConfig config = config();
            ChannelPipeline pipeline = pipeline();
            RecvByteBufAllocator.Handle allocHandle3 = this.allocHandle;
            if (allocHandle3 == null) {
                allocHandle2 = config.getRecvByteBufAllocator().newHandle();
                this.allocHandle = allocHandle2;
            } else {
                allocHandle2 = allocHandle3;
            }
            ByteBuf byteBuf = allocHandle2.allocate(alloc());
            int totalReadAmount = 0;
            int localReadAmount = 0;
            boolean read = false;
            boolean closed = false;
            while (true) {
                try {
                    localReadAmount = doReadBytes(byteBuf);
                    if (localReadAmount > 0) {
                        read = true;
                    } else if (localReadAmount < 0) {
                        closed = true;
                    }
                    int available = available();
                    if (available > 0) {
                        if (!byteBuf.isWritable()) {
                            int capacity = byteBuf.capacity();
                            int maxCapacity = byteBuf.maxCapacity();
                            if (capacity == maxCapacity) {
                                if (read) {
                                    read = false;
                                    pipeline.fireChannelRead(byteBuf);
                                    byteBuf = alloc().buffer();
                                }
                            } else if (byteBuf.writerIndex() + available > maxCapacity) {
                                byteBuf.capacity(maxCapacity);
                            } else {
                                byteBuf.ensureWritable(available);
                            }
                        }
                        if (totalReadAmount < Integer.MAX_VALUE - localReadAmount) {
                            totalReadAmount += localReadAmount;
                            if (!config.isAutoRead()) {
                                break;
                            }
                        } else {
                            totalReadAmount = Integer.MAX_VALUE;
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (Throwable t) {
                    Throwable exception = t;
                    if (read) {
                        pipeline.fireChannelRead(byteBuf);
                    } else {
                        byteBuf.release();
                    }
                    pipeline.fireChannelReadComplete();
                    if (exception instanceof IOException) {
                        pipeline().fireExceptionCaught(exception);
                        closed = true;
                    } else {
                        pipeline.fireExceptionCaught(exception);
                        unsafe().close(voidPromise());
                    }
                    if (closed) {
                        setReadPending(false);
                        this.inputShutdown = true;
                        if (isOpen()) {
                            if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                                pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                            } else {
                                unsafe().close(unsafe().voidPromise());
                            }
                        }
                    }
                    if (localReadAmount != 0 || !isActive()) {
                        return;
                    }
                }
            }
            allocHandle2.record(totalReadAmount);
            if (read) {
                pipeline.fireChannelRead(byteBuf);
            } else {
                byteBuf.release();
            }
            pipeline.fireChannelReadComplete();
            if (0 != 0) {
                closed = true;
                pipeline().fireExceptionCaught((Throwable) null);
            }
            if (closed) {
                setReadPending(false);
                this.inputShutdown = true;
                if (isOpen()) {
                    if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                        pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                    } else {
                        unsafe().close(unsafe().voidPromise());
                    }
                }
            }
            if (localReadAmount != 0 || !isActive()) {
                return;
            }
            read();
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        while (true) {
            Object msg = in.current();
            if (msg != null) {
                if (msg instanceof ByteBuf) {
                    ByteBuf buf = (ByteBuf) msg;
                    int readableBytes = buf.readableBytes();
                    while (readableBytes > 0) {
                        doWriteBytes(buf);
                        int newReadableBytes = buf.readableBytes();
                        in.progress((long) (readableBytes - newReadableBytes));
                        readableBytes = newReadableBytes;
                    }
                    in.remove();
                } else if (msg instanceof FileRegion) {
                    FileRegion region = (FileRegion) msg;
                    long transfered = region.transfered();
                    doWriteFileRegion(region);
                    in.progress(region.transfered() - transfered);
                    in.remove();
                } else {
                    in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object msg) {
        if ((msg instanceof ByteBuf) || (msg instanceof FileRegion)) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }
}
