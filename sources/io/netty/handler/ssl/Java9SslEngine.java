package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;

public final class Java9SslEngine extends JdkSslEngine {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AlpnSelector alpnSelector;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener selectionListener;

    static {
        Class<Java9SslEngine> cls = Java9SslEngine.class;
    }

    public final class AlpnSelector implements BiFunction<SSLEngine, List<String>, String> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean called;
        private final JdkApplicationProtocolNegotiator.ProtocolSelector selector;

        static {
            Class<Java9SslEngine> cls = Java9SslEngine.class;
        }

        AlpnSelector(JdkApplicationProtocolNegotiator.ProtocolSelector selector2) {
            this.selector = selector2;
        }

        public String apply(SSLEngine sslEngine, List<String> strings) {
            if (!this.called) {
                this.called = true;
                try {
                    String selected = this.selector.select(strings);
                    return selected == null ? "" : selected;
                } catch (Exception e) {
                    return null;
                }
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public void checkUnsupported() {
            if (!this.called) {
                String protocol = Java9SslEngine.this.getApplicationProtocol();
                if (protocol == null) {
                    throw new AssertionError();
                } else if (protocol.isEmpty()) {
                    this.selector.unsupported();
                }
            }
        }
    }

    Java9SslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
        super(engine);
        if (isServer) {
            this.selectionListener = null;
            AlpnSelector alpnSelector2 = new AlpnSelector(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())));
            this.alpnSelector = alpnSelector2;
            Java9SslUtils.setHandshakeApplicationProtocolSelector(engine, alpnSelector2);
            return;
        }
        this.selectionListener = applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols());
        this.alpnSelector = null;
        Java9SslUtils.setApplicationProtocols(engine, applicationNegotiator.protocols());
    }

    private SSLEngineResult verifyProtocolSelection(SSLEngineResult result) {
        if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
            AlpnSelector alpnSelector2 = this.alpnSelector;
            if (alpnSelector2 == null) {
                try {
                    String protocol = getApplicationProtocol();
                    if (protocol == null) {
                        throw new AssertionError();
                    } else if (protocol.isEmpty()) {
                        this.selectionListener.unsupported();
                    } else {
                        this.selectionListener.selected(protocol);
                    }
                } catch (Throwable e) {
                    throw SslUtils.toSSLHandshakeException(e);
                }
            } else if (this.selectionListener == null) {
                alpnSelector2.checkUnsupported();
            } else {
                throw new AssertionError();
            }
        }
        return result;
    }

    public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) {
        return verifyProtocolSelection(super.wrap(src, dst));
    }

    public SSLEngineResult wrap(ByteBuffer[] srcs, ByteBuffer dst) {
        return verifyProtocolSelection(super.wrap(srcs, dst));
    }

    public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int len, ByteBuffer dst) {
        return verifyProtocolSelection(super.wrap(srcs, offset, len, dst));
    }

    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) {
        return verifyProtocolSelection(super.unwrap(src, dst));
    }

    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) {
        return verifyProtocolSelection(super.unwrap(src, dsts));
    }

    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dst, int offset, int len) {
        return verifyProtocolSelection(super.unwrap(src, dst, offset, len));
    }

    /* access modifiers changed from: package-private */
    public void setNegotiatedApplicationProtocol(String applicationProtocol) {
    }

    public String getNegotiatedApplicationProtocol() {
        String protocol = getApplicationProtocol();
        if (protocol == null) {
            return protocol;
        }
        if (protocol.isEmpty()) {
            return null;
        }
        return protocol;
    }

    public String getApplicationProtocol() {
        return Java9SslUtils.getApplicationProtocol(getWrappedEngine());
    }

    public String getHandshakeApplicationProtocol() {
        return Java9SslUtils.getHandshakeApplicationProtocol(getWrappedEngine());
    }

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
        Java9SslUtils.setHandshakeApplicationProtocolSelector(getWrappedEngine(), selector);
    }

    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return Java9SslUtils.getHandshakeApplicationProtocolSelector(getWrappedEngine());
    }
}
