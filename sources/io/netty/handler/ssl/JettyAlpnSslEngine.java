package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import org.eclipse.jetty.alpn.ALPN;

public abstract class JettyAlpnSslEngine extends JdkSslEngine {
    private static final boolean available = initAvailable();

    static boolean isAvailable() {
        return available;
    }

    private static boolean initAvailable() {
        if (PlatformDependent.javaVersion() > 8) {
            return false;
        }
        try {
            Class.forName("sun.security.ssl.ALPNExtension", true, (ClassLoader) null);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    static JettyAlpnSslEngine newClientEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ClientEngine(engine, applicationNegotiator);
    }

    static JettyAlpnSslEngine newServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ServerEngine(engine, applicationNegotiator);
    }

    private JettyAlpnSslEngine(SSLEngine engine) {
        super(engine);
    }

    public static final class ClientEngine extends JettyAlpnSslEngine {
        ClientEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine);
            ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
            final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener) ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
            ALPN.put(engine, new ALPN.ClientProvider() {
                public List<String> protocols() {
                    return applicationNegotiator.protocols();
                }

                public void selected(String protocol) {
                    try {
                        protocolListener.selected(protocol);
                    } catch (Throwable t) {
                        throw SslUtils.toSSLHandshakeException(t);
                    }
                }

                public void unsupported() {
                    protocolListener.unsupported();
                }
            });
        }

        public void closeInbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        public void closeOutbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }

    public static final class ServerEngine extends JettyAlpnSslEngine {
        ServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine);
            ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
            final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector) ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
            ALPN.put(engine, new ALPN.ServerProvider() {
                public String select(List<String> protocols) {
                    try {
                        return protocolSelector.select(protocols);
                    } catch (Throwable t) {
                        throw SslUtils.toSSLHandshakeException(t);
                    }
                }

                public void unsupported() {
                    protocolSelector.unsupported();
                }
            });
        }

        public void closeInbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        public void closeOutbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }
}
