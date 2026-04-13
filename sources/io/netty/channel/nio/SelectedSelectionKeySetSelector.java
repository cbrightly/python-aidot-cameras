package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public final class SelectedSelectionKeySetSelector extends Selector {
    private final Selector delegate;
    private final SelectedSelectionKeySet selectionKeys;

    SelectedSelectionKeySetSelector(Selector delegate2, SelectedSelectionKeySet selectionKeys2) {
        this.delegate = delegate2;
        this.selectionKeys = selectionKeys2;
    }

    public boolean isOpen() {
        return this.delegate.isOpen();
    }

    public SelectorProvider provider() {
        return this.delegate.provider();
    }

    public Set<SelectionKey> keys() {
        return this.delegate.keys();
    }

    public Set<SelectionKey> selectedKeys() {
        return this.delegate.selectedKeys();
    }

    public int selectNow() {
        this.selectionKeys.reset();
        return this.delegate.selectNow();
    }

    public int select(long timeout) {
        this.selectionKeys.reset();
        return this.delegate.select(timeout);
    }

    public int select() {
        this.selectionKeys.reset();
        return this.delegate.select();
    }

    public Selector wakeup() {
        return this.delegate.wakeup();
    }

    public void close() {
        this.delegate.close();
    }
}
