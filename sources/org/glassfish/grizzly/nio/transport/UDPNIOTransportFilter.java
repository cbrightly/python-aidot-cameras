package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.utils.Holder;

public final class UDPNIOTransportFilter extends BaseFilter {
    private final UDPNIOTransport transport;

    UDPNIOTransportFilter(UDPNIOTransport transport2) {
        this.transport = transport2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        ReadResult<Buffer, SocketAddress> readResult;
        UDPNIOConnection connection = (UDPNIOConnection) ctx.getConnection();
        boolean isBlocking = ctx.getTransportContext().isBlocking();
        Buffer inBuffer = (Buffer) ctx.getMessage();
        if (!isBlocking) {
            readResult = ReadResult.create(connection);
            this.transport.read(connection, inBuffer, readResult);
        } else {
            GrizzlyFuture<ReadResult<Buffer, SocketAddress>> future = this.transport.getTemporarySelectorIO().getReader().read(connection, inBuffer);
            try {
                ReadResult<Buffer, SocketAddress> readResult2 = (ReadResult) future.get();
                future.recycle(false);
                readResult = readResult2;
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
        if (readResult.getReadSize() > 0) {
            Buffer buffer = readResult.getMessage();
            buffer.trim();
            Holder<SocketAddress> addressHolder = readResult.getSrcAddressHolder();
            readResult.recycle();
            ctx.setMessage(buffer);
            ctx.setAddressHolder(addressHolder);
            return ctx.getInvokeAction();
        }
        readResult.recycle();
        return ctx.getStopAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        WritableMessage message = (WritableMessage) ctx.getMessage();
        if (message != null) {
            ctx.setMessage((Object) null);
            Connection connection = ctx.getConnection();
            FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
            CompletionHandler completionHandler = transportContext.getCompletionHandler();
            Object address = ctx.getAddress();
            transportContext.setCompletionHandler((CompletionHandler) null);
            this.transport.getWriter(transportContext.isBlocking()).write(connection, address, message, completionHandler);
        }
        return ctx.getInvokeAction();
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        if (event.type() == TransportFilter.FlushEvent.TYPE) {
            Connection connection = ctx.getConnection();
            FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
            if (transportContext.getCompletionHandler() == null) {
                this.transport.getWriter(transportContext.isBlocking()).write(connection, (WritableMessage) Buffers.EMPTY_BUFFER, ((TransportFilter.FlushEvent) event).getCompletionHandler());
                transportContext.setCompletionHandler((CompletionHandler) null);
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
