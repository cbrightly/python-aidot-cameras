package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.utils.Futures;

public class UDPNIOServerConnection extends UDPNIOConnection {
    private static final Logger LOGGER = Grizzly.logger(UDPNIOServerConnection.class);

    public UDPNIOServerConnection(UDPNIOTransport transport, DatagramChannel channel) {
        super(transport, channel);
    }

    public Processor getProcessor() {
        if (this.processor == null) {
            return this.transport.getProcessor();
        }
        return this.processor;
    }

    public ProcessorSelector getProcessorSelector() {
        if (this.processorSelector == null) {
            return this.transport.getProcessorSelector();
        }
        return this.processorSelector;
    }

    public void register() {
        FutureImpl<RegisterChannelResult> future = Futures.createSafeFuture();
        this.transport.getNIOChannelDistributor().registerServiceChannelAsync(this.channel, 1, this, Futures.toCompletionHandler(future, ((UDPNIOTransport) this.transport).registerChannelCompletionHandler));
        try {
            future.get(10, TimeUnit.SECONDS);
            notifyReady();
        } catch (Exception e) {
            throw new IOException("Error registering server channel key", e);
        }
    }

    /* access modifiers changed from: protected */
    public void closeGracefully0(CompletionHandler<Closeable> completionHandler, CloseReason closeReason) {
        terminate0(completionHandler, closeReason);
    }

    /* access modifiers changed from: protected */
    public void terminate0(CompletionHandler<Closeable> completionHandler, CloseReason closeReason) {
        Logger logger = LOGGER;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("UDPNIOServerConnection might be only closed by calling unbind().");
        }
        if (completionHandler != null) {
            completionHandler.completed(this);
        }
    }

    public void unbind(CompletionHandler<Closeable> completionHandler) {
        super.terminate0(completionHandler, CloseReason.LOCALLY_CLOSED_REASON);
    }

    /* access modifiers changed from: protected */
    public void preClose() {
        this.transport.unbind(this);
        super.preClose();
    }
}
