package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.AbstractNioChannel;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
    /* access modifiers changed from: protected */
    public abstract int doReadMessages(List<Object> list);

    /* access modifiers changed from: protected */
    public abstract boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer);

    protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
        super(parent, ch, readInterestOp);
    }

    /* access modifiers changed from: protected */
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe();
    }

    public final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<Object> readBuf;

        static {
            Class<AbstractNioMessageChannel> cls = AbstractNioMessageChannel.class;
        }

        private NioMessageUnsafe() {
            super();
            this.readBuf = new ArrayList();
        }

        public void read() {
            if (AbstractNioMessageChannel.this.eventLoop().inEventLoop()) {
                ChannelConfig config = AbstractNioMessageChannel.this.config();
                if (config.isAutoRead() || AbstractNioMessageChannel.this.isReadPending()) {
                    int maxMessagesPerRead = config.getMaxMessagesPerRead();
                    ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
                    boolean closed = false;
                    Throwable exception = null;
                    while (true) {
                        try {
                            int localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
                            if (localRead == 0) {
                                break;
                            } else if (localRead < 0) {
                                closed = true;
                                break;
                            } else if (!config.isAutoRead()) {
                                break;
                            }
                            

                            /* access modifiers changed from: protected */
                            public void doWrite(ChannelOutboundBuffer in) {
                                SelectionKey key = selectionKey();
                                int interestOps = key.interestOps();
                                while (true) {
                                    Object msg = in.current();
                                    if (msg != null) {
                                        boolean done = false;
                                        try {
                                            int i = config().getWriteSpinCount() - 1;
                                            while (true) {
                                                if (i < 0) {
                                                    break;
                                                } else if (doWriteMessage(msg, in)) {
                                                    done = true;
                                                    break;
                                                } else {
                                                    i--;
                                                }
                                            }
                                            if (done) {
                                                in.remove();
                                            } else if ((interestOps & 4) == 0) {
                                                key.interestOps(interestOps | 4);
                                                return;
                                            } else {
                                                return;
                                            }
                                        } catch (Exception e) {
                                            if (continueOnWriteError()) {
                                                in.remove(e);
                                            } else {
                                                throw e;
                                            }
                                        }
                                    } else if ((interestOps & 4) != 0) {
                                        key.interestOps(interestOps & -5);
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                            }

                            /* access modifiers changed from: protected */
                            public boolean continueOnWriteError() {
                                return false;
                            }

                            /* access modifiers changed from: protected */
                            public boolean closeOnReadError(Throwable cause) {
                                return (cause instanceof IOException) && !(cause instanceof PortUnreachableException) && !(this instanceof ServerChannel);
                            }
                        }
