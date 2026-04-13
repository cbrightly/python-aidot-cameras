package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import org.conscrypt.AllocatedBuffer;
import org.conscrypt.BufferAllocator;
import org.conscrypt.Conscrypt;
import org.conscrypt.HandshakeListener;

public abstract class ConscryptAlpnSslEngine extends JdkSslEngine {
    private static final boolean USE_BUFFER_ALLOCATOR = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.conscrypt.useBufferAllocator", true);

    static ConscryptAlpnSslEngine newClientEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ClientEngine(engine, alloc, applicationNegotiator);
    }

    static ConscryptAlpnSslEngine newServerEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ServerEngine(engine, alloc, applicationNegotiator);
    }

    private ConscryptAlpnSslEngine(SSLEngine engine, ByteBufAllocator alloc, List<String> protocols) {
        super(engine);
        if (USE_BUFFER_ALLOCATOR) {
            Conscrypt.setBufferAllocator(engine, new BufferAllocatorAdapter(alloc));
        }
        Conscrypt.setApplicationProtocols(engine, (String[]) protocols.toArray(new String[protocols.size()]));
    }

    /* access modifiers changed from: package-private */
    public final int calculateOutNetBufSize(int plaintextBytes, int numBuffers) {
        return (int) Math.min(2147483647L, ((long) plaintextBytes) + (((long) Conscrypt.maxSealOverhead(getWrappedEngine())) * ((long) numBuffers)));
    }

    /* access modifiers changed from: package-private */
    public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dests) {
        return Conscrypt.unwrap(getWrappedEngine(), srcs, dests);
    }

    public static final class ClientEngine extends ConscryptAlpnSslEngine {
        private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener;

        ClientEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine, alloc, applicationNegotiator.protocols());
            Conscrypt.setHandshakeListener(engine, new HandshakeListener() {
                public void onHandshakeFinished() {
                    ClientEngine.this.selectProtocol();
                }
            });
            this.protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener) ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
        }

        /* access modifiers changed from: private */
        public void selectProtocol() {
            try {
                this.protocolListener.selected(Conscrypt.getApplicationProtocol(getWrappedEngine()));
            } catch (Throwable e) {
                throw SslUtils.toSSLHandshakeException(e);
            }
        }
    }

    public static final class ServerEngine extends ConscryptAlpnSslEngine {
        private final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector;

        ServerEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine, alloc, applicationNegotiator.protocols());
            Conscrypt.setHandshakeListener(engine, new HandshakeListener() {
                public void onHandshakeFinished() {
                    ServerEngine.this.selectProtocol();
                }
            });
            this.protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector) ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
        }

        /* access modifiers changed from: private */
        public void selectProtocol() {
            List list;
            try {
                String protocol = Conscrypt.getApplicationProtocol(getWrappedEngine());
                JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector2 = this.protocolSelector;
                if (protocol != null) {
                    list = Collections.singletonList(protocol);
                } else {
                    list = Collections.emptyList();
                }
                protocolSelector2.select(list);
            } catch (Throwable e) {
                throw SslUtils.toSSLHandshakeException(e);
            }
        }
    }

    public static final class BufferAllocatorAdapter extends BufferAllocator {
        private final ByteBufAllocator alloc;

        BufferAllocatorAdapter(ByteBufAllocator alloc2) {
            this.alloc = alloc2;
        }

        public AllocatedBuffer allocateDirectBuffer(int capacity) {
            return new BufferAdapter(this.alloc.directBuffer(capacity));
        }
    }

    public static final class BufferAdapter extends AllocatedBuffer {
        private final ByteBuffer buffer;
        private final ByteBuf nettyBuffer;

        BufferAdapter(ByteBuf nettyBuffer2) {
            this.nettyBuffer = nettyBuffer2;
            this.buffer = nettyBuffer2.nioBuffer(0, nettyBuffer2.capacity());
        }

        public ByteBuffer nioBuffer() {
            return this.buffer;
        }

        public AllocatedBuffer retain() {
            this.nettyBuffer.retain();
            return this;
        }

        public AllocatedBuffer release() {
            this.nettyBuffer.release();
            return this;
        }
    }
}
