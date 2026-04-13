package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.memory.Buffers;

public final class TCPNIOTransportFilter extends BaseFilter {
    private final TCPNIOTransport transport;

    TCPNIOTransportFilter(TCPNIOTransport transport2) {
        this.transport = transport2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        Buffer buffer;
        TCPNIOConnection connection = (TCPNIOConnection) ctx.getConnection();
        boolean isBlocking = ctx.getTransportContext().isBlocking();
        Buffer inBuffer = (Buffer) ctx.getMessage();
        if (!isBlocking) {
            buffer = this.transport.read(connection, inBuffer);
        } else {
            GrizzlyFuture<ReadResult<Buffer, SocketAddress>> future = this.transport.getTemporarySelectorIO().getReader().read(connection, inBuffer);
            try {
                Buffer buffer2 = ((ReadResult) future.get()).getMessage();
                future.recycle(true);
                buffer = buffer2;
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IOException) {
                    throw ((IOException) cause);
                }
                throw new IOException(cause);
            } catch (InterruptedException e2) {
                throw new IOException(e2);
            }
        }
        if (buffer == null || buffer.position() == 0) {
            return ctx.getStopAction();
        }
        buffer.trim();
        ctx.setMessage(buffer);
        ctx.setAddressHolder(connection.peerSocketAddressHolder);
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        WritableMessage message = (WritableMessage) ctx.getMessage();
        if (message != null) {
            ctx.setMessage((Object) null);
            Connection connection = ctx.getConnection();
            FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
            CompletionHandler completionHandler = transportContext.getCompletionHandler();
            MessageCloner cloner = transportContext.getMessageCloner();
            transportContext.setCompletionHandler((CompletionHandler) null);
            transportContext.setMessageCloner((MessageCloner) null);
            if (!transportContext.isBlocking()) {
                this.transport.getAsyncQueueIO().getWriter().write(connection, null, message, completionHandler, (MessageCloner<WritableMessage>) cloner);
            } else {
                this.transport.getTemporarySelectorIO().getWriter().write(connection, null, message, completionHandler);
            }
        }
        return ctx.getInvokeAction();
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        if (event.type() == TransportFilter.FlushEvent.TYPE) {
            Connection connection = ctx.getConnection();
            FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
            if (transportContext.getCompletionHandler() == null) {
                this.transport.getWriter(transportContext.isBlocking()).write(connection, (WritableMessage) Buffers.EMPTY_BUFFER, ((TransportFilter.FlushEvent) event).getCompletionHandler());
            } else {
                throw new IllegalStateException("TransportContext CompletionHandler must be null");
            }
        }
        return ctx.getInvokeAction();
    }

    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
        Connection connection = ctx.getConnection();
        if (connection != null) {
            connection.closeSilently();
        }
    }
}
