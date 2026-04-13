package org.glassfish.grizzly.nio.tmpselectors;

import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.localization.LogMessages;

public class TemporarySelectorIO {
    private static final Logger LOGGER = Grizzly.logger(TemporarySelectorIO.class);
    private final Reader<SocketAddress> reader;
    protected TemporarySelectorPool selectorPool;
    private final Writer<SocketAddress> writer;

    public TemporarySelectorIO(Reader<SocketAddress> reader2, Writer<SocketAddress> writer2) {
        this(reader2, writer2, (TemporarySelectorPool) null);
    }

    public TemporarySelectorIO(Reader<SocketAddress> reader2, Writer<SocketAddress> writer2, TemporarySelectorPool selectorPool2) {
        this.reader = reader2;
        this.writer = writer2;
        this.selectorPool = selectorPool2;
    }

    public TemporarySelectorPool getSelectorPool() {
        return this.selectorPool;
    }

    public void setSelectorPool(TemporarySelectorPool selectorPool2) {
        this.selectorPool = selectorPool2;
    }

    public Reader<SocketAddress> getReader() {
        return this.reader;
    }

    public Writer<SocketAddress> getWriter() {
        return this.writer;
    }

    /* access modifiers changed from: protected */
    public void recycleTemporaryArtifacts(Selector selector, SelectionKey selectionKey) {
        if (selectionKey != null) {
            try {
                selectionKey.cancel();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_IO_CANCEL_KEY_EXCEPTION(selectionKey), e);
            }
        }
        if (selector != null) {
            this.selectorPool.offer(selector);
        }
    }
}
