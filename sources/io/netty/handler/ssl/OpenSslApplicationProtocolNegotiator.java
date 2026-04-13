package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;

@Deprecated
public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
    ApplicationProtocolConfig.Protocol protocol();

    ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();

    ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();
}
