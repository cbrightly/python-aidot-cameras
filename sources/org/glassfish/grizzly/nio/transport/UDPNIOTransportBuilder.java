package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.nio.NIOTransport;

public class UDPNIOTransportBuilder extends NIOTransportBuilder<UDPNIOTransportBuilder> {
    protected UDPNIOTransportBuilder(Class<? extends UDPNIOTransport> transportClass) {
        super(transportClass);
    }

    public static UDPNIOTransportBuilder newInstance() {
        return new UDPNIOTransportBuilder(UDPNIOTransport.class);
    }

    public UDPNIOTransport build() {
        return (UDPNIOTransport) super.build();
    }

    /* access modifiers changed from: protected */
    public UDPNIOTransportBuilder getThis() {
        return this;
    }

    /* access modifiers changed from: protected */
    public NIOTransport create(String name) {
        return new UDPNIOTransport(name);
    }
}
