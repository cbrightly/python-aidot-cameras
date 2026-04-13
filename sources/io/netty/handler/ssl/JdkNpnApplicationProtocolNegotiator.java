package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import java.util.List;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory NPN_WRAPPER = new JdkApplicationProtocolNegotiator.SslEngineWrapperFactory() {
        {
            if (!JettyNpnSslEngine.isAvailable()) {
                throw new RuntimeException("NPN unsupported. Is your classpath configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
            }
        }

        public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            return new JettyNpnSslEngine(engine, applicationNegotiator, isServer);
        }
    };

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

    public JdkNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this(false, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(String... protocols) {
        this(false, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
        this(clientFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTOR_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTION_LISTENER_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
        this(clientFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTOR_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? JdkBaseApplicationProtocolNegotiator.FAIL_SELECTION_LISTENER_FACTORY : JdkBaseApplicationProtocolNegotiator.NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
        super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }
}
