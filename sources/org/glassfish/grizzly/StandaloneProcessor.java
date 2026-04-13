package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.transport.DefaultStreamReader;
import org.glassfish.grizzly.nio.transport.DefaultStreamWriter;
import org.glassfish.grizzly.streams.StreamReader;
import org.glassfish.grizzly.streams.StreamWriter;

public class StandaloneProcessor implements Processor {
    public static final StandaloneProcessor INSTANCE = new StandaloneProcessor();

    public ProcessorResult process(Context context) {
        IOEvent iOEvent = context.getIoEvent();
        if (iOEvent == IOEvent.READ) {
            return ((AsyncQueueEnabledTransport) context.getConnection().getTransport()).getAsyncQueueIO().getReader().processAsync(context).toProcessorResult();
        }
        if (iOEvent == IOEvent.WRITE) {
            return ((AsyncQueueEnabledTransport) context.getConnection().getTransport()).getAsyncQueueIO().getWriter().processAsync(context).toProcessorResult();
        }
        throw new IllegalStateException("Unexpected IOEvent=" + iOEvent);
    }

    public boolean isInterested(IOEvent ioEvent) {
        return ioEvent == IOEvent.READ || ioEvent == IOEvent.WRITE;
    }

    public void setInterested(IOEvent ioEvent, boolean isInterested) {
    }

    public Context obtainContext(Connection connection) {
        Context context = Context.create(connection);
        context.setProcessor(this);
        return context;
    }

    public StreamReader getStreamReader(Connection connection) {
        return new DefaultStreamReader(connection);
    }

    public StreamWriter getStreamWriter(Connection connection) {
        return new DefaultStreamWriter(connection);
    }

    public void read(Connection connection, CompletionHandler completionHandler) {
        connection.getTransport().getReader(connection).read(connection, (Buffer) null, completionHandler);
    }

    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler) {
        write(connection, dstAddress, message, completionHandler, (MessageCloner) null);
    }

    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, MessageCloner messageCloner) {
        connection.getTransport().getWriter(connection).write(connection, dstAddress, (WritableMessage) (Buffer) message, completionHandler, (MessageCloner<WritableMessage>) messageCloner);
    }

    @Deprecated
    public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
        connection.getTransport().getWriter(connection).write(connection, dstAddress, (WritableMessage) (Buffer) message, completionHandler, pushBackHandler);
    }
}
