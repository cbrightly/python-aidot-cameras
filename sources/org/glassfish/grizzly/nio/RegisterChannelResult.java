package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public final class RegisterChannelResult {
    private final SelectableChannel channel;
    private final SelectionKey selectionKey;
    private final SelectorRunner selectorRunner;

    public RegisterChannelResult(SelectorRunner selectorRunner2, SelectionKey selectionKey2, SelectableChannel channel2) {
        this.selectorRunner = selectorRunner2;
        this.selectionKey = selectionKey2;
        this.channel = channel2;
    }

    public SelectorRunner getSelectorRunner() {
        return this.selectorRunner;
    }

    public SelectionKey getSelectionKey() {
        return this.selectionKey;
    }

    public SelectableChannel getChannel() {
        return this.channel;
    }
}
