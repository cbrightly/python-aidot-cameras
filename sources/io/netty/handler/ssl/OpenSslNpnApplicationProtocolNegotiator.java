package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Deprecated
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final List<String> protocols;

    public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> protocols2) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols2), "protocols");
    }

    public OpenSslNpnApplicationProtocolNegotiator(String... protocols2) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols2), "protocols");
    }

    public ApplicationProtocolConfig.Protocol protocol() {
        return ApplicationProtocolConfig.Protocol.NPN;
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
    }
}
