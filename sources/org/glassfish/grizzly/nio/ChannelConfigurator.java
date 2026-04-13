package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;

public interface ChannelConfigurator {
    void postConfigure(NIOTransport nIOTransport, SelectableChannel selectableChannel);

    void preConfigure(NIOTransport nIOTransport, SelectableChannel selectableChannel);
}
