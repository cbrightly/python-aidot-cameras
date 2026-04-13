package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;

public class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() {
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new FailProtocolSelectionListener((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() {
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new FailProtocolSelector((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() {
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new NoFailProtocolSelectionListener((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() {
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new NoFailProtocolSelector((JdkSslEngine) engine, supportedProtocols);
        }
    };
    private final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory;
    private final List<String> protocols;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory;
    private final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory;

    JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory2, Iterable<String> protocols2) {
        this(wrapperFactory2, selectorFactory2, listenerFactory2, ApplicationProtocolUtil.toList(protocols2));
    }

    JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory2, String... protocols2) {
        this(wrapperFactory2, selectorFactory2, listenerFactory2, ApplicationProtocolUtil.toList(protocols2));
    }

    private JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory2, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory2, List<String> protocols2) {
        this.wrapperFactory = (JdkApplicationProtocolNegotiator.SslEngineWrapperFactory) ObjectUtil.checkNotNull(wrapperFactory2, "wrapperFactory");
        this.selectorFactory = (JdkApplicationProtocolNegotiator.ProtocolSelectorFactory) ObjectUtil.checkNotNull(selectorFactory2, "selectorFactory");
        this.listenerFactory = (JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory) ObjectUtil.checkNotNull(listenerFactory2, "listenerFactory");
        this.protocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(protocols2, "protocols"));
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return this.selectorFactory;
    }

    public JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return this.listenerFactory;
    }

    public JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return this.wrapperFactory;
    }

    public static class NoFailProtocolSelector implements JdkApplicationProtocolNegotiator.ProtocolSelector {
        private final JdkSslEngine engineWrapper;
        private final Set<String> supportedProtocols;

        NoFailProtocolSelector(JdkSslEngine engineWrapper2, Set<String> supportedProtocols2) {
            this.engineWrapper = engineWrapper2;
            this.supportedProtocols = supportedProtocols2;
        }

        public void unsupported() {
            this.engineWrapper.setNegotiatedApplicationProtocol((String) null);
        }

        public String select(List<String> protocols) {
            for (String p : this.supportedProtocols) {
                if (protocols.contains(p)) {
                    this.engineWrapper.setNegotiatedApplicationProtocol(p);
                    return p;
                }
            }
            return noSelectMatchFound();
        }

        public String noSelectMatchFound() {
            this.engineWrapper.setNegotiatedApplicationProtocol((String) null);
            return null;
        }
    }

    public static final class FailProtocolSelector extends NoFailProtocolSelector {
        FailProtocolSelector(JdkSslEngine engineWrapper, Set<String> supportedProtocols) {
            super(engineWrapper, supportedProtocols);
        }

        public String noSelectMatchFound() {
            throw new SSLHandshakeException("Selected protocol is not supported");
        }
    }

    public static class NoFailProtocolSelectionListener implements JdkApplicationProtocolNegotiator.ProtocolSelectionListener {
        private final JdkSslEngine engineWrapper;
        private final List<String> supportedProtocols;

        NoFailProtocolSelectionListener(JdkSslEngine engineWrapper2, List<String> supportedProtocols2) {
            this.engineWrapper = engineWrapper2;
            this.supportedProtocols = supportedProtocols2;
        }

        public void unsupported() {
            this.engineWrapper.setNegotiatedApplicationProtocol((String) null);
        }

        public void selected(String protocol) {
            if (this.supportedProtocols.contains(protocol)) {
                this.engineWrapper.setNegotiatedApplicationProtocol(protocol);
            } else {
                noSelectedMatchFound(protocol);
            }
        }

        /* access modifiers changed from: protected */
        public void noSelectedMatchFound(String protocol) {
        }
    }

    public static final class FailProtocolSelectionListener extends NoFailProtocolSelectionListener {
        FailProtocolSelectionListener(JdkSslEngine engineWrapper, List<String> supportedProtocols) {
            super(engineWrapper, supportedProtocols);
        }

        /* access modifiers changed from: protected */
        public void noSelectedMatchFound(String protocol) {
            throw new SSLHandshakeException("No compatible protocols found");
        }
    }
}
