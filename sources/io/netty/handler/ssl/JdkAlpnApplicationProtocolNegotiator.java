package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.PlatformDependent;
import java.util.List;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory ALPN_WRAPPER;
    private static final boolean AVAILABLE;

    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return super.protocolListenerFactory();
    }

    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return super.protocolSelectorFactory();
    }

    public /* bridge */ /* synthetic */ List protocols() {
        return super.protocols();
    }

    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return super.wrapperFactory();
    }

    static {
        boolean z = Conscrypt.isAvailable() || jdkAlpnSupported() || JettyAlpnSslEngine.isAvailable();
        AVAILABLE = z;
        ALPN_WRAPPER = z ? new AlpnWrapper() : new FailureWrapper();
    }

    public JdkAlpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this(false, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(String... protocols) {
        this(false, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
        this(serverFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTOR_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTION_LISTENER_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
        this(serverFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTOR_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTION_LISTENER_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
        super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }

    public static final class FailureWrapper extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
        private FailureWrapper() {
        }

        public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            throw new RuntimeException("ALPN unsupported. Is your classpath configured correctly? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider. For Jetty-ALPN, see http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
        }
    }

    public static final class AlpnWrapper extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
        private AlpnWrapper() {
        }

        public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            if (Conscrypt.isEngineSupported(engine)) {
                if (isServer) {
                    return ConscryptAlpnSslEngine.newServerEngine(engine, alloc, applicationNegotiator);
                }
                return ConscryptAlpnSslEngine.newClientEngine(engine, alloc, applicationNegotiator);
            } else if (JdkAlpnApplicationProtocolNegotiator.jdkAlpnSupported()) {
                return new Java9SslEngine(engine, applicationNegotiator, isServer);
            } else {
                if (!JettyAlpnSslEngine.isAvailable()) {
                    throw new RuntimeException("Unable to wrap SSLEngine of type " + engine.getClass().getName());
                } else if (isServer) {
                    return JettyAlpnSslEngine.newServerEngine(engine, applicationNegotiator);
                } else {
                    return JettyAlpnSslEngine.newClientEngine(engine, applicationNegotiator);
                }
            }
        }
    }

    static boolean jdkAlpnSupported() {
        return PlatformDependent.javaVersion() >= 9 && Java9SslUtils.supportsAlpn();
    }
}
