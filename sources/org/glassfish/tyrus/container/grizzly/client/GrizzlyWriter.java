package org.glassfish.tyrus.container.grizzly.client;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.tyrus.container.grizzly.client.TaskProcessor;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.Writer;

public class GrizzlyWriter extends Writer {
    final Connection connection;
    /* access modifiers changed from: private */
    public final TaskProcessor taskProcessor = new TaskProcessor(new WriterCondition());

    public GrizzlyWriter(Connection connection2) {
        this.connection = connection2;
        connection2.configureBlocking(false);
    }

    public void write(final ByteBuffer buffer, final CompletionHandler<ByteBuffer> completionHandler) {
        if (!this.connection.isOpen()) {
            completionHandler.failed(new IllegalStateException("Connection is not open."));
            return;
        }
        this.taskProcessor.processTask(new WriteTask(this.connection, Buffers.wrap(this.connection.getTransport().getMemoryManager(), buffer), new EmptyCompletionHandler() {
            public void cancelled() {
                CompletionHandler completionHandler = completionHandler;
                if (completionHandler != null) {
                    completionHandler.cancelled();
                }
            }

            public void completed(Object result) {
                CompletionHandler completionHandler = completionHandler;
                if (completionHandler != null) {
                    completionHandler.completed(buffer);
                }
            }

            public void failed(Throwable throwable) {
                CompletionHandler completionHandler = completionHandler;
                if (completionHandler != null) {
                    completionHandler.failed(throwable);
                }
            }
        }));
    }

    public class WriterCondition implements TaskProcessor.Condition {
        /* access modifiers changed from: private */
        public final AtomicBoolean writeHandlerRegistered;

        private WriterCondition() {
            this.writeHandlerRegistered = new AtomicBoolean(false);
        }

        public boolean isValid() {
            if (GrizzlyWriter.this.connection.canWrite() || !this.writeHandlerRegistered.compareAndSet(false, true)) {
                return true;
            }
            GrizzlyWriter.this.connection.notifyCanWrite(new WriteHandler() {
                public void onWritePossible() {
                    WriterCondition.this.writeHandlerRegistered.set(false);
                    GrizzlyWriter.this.taskProcessor.processTask();
                }

                public void onError(Throwable t) {
                    WriterCondition.this.writeHandlerRegistered.set(false);
                    Logger.getLogger(GrizzlyWriter.class.getName()).log(Level.WARNING, t.getMessage(), t);
                }
            });
            return false;
        }
    }

    public void close() {
        this.taskProcessor.processTask(new CloseTask(this.connection));
    }

    public int hashCode() {
        return this.connection.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof GrizzlyWriter) && this.connection.equals(((GrizzlyWriter) obj).connection);
    }

    public String toString() {
        return getClass().getName() + " " + this.connection.toString() + " " + this.connection.hashCode();
    }

    public class WriteTask extends TaskProcessor.Task {
        private final EmptyCompletionHandler completionHandler;
        private final Connection connection;
        private final Buffer message;

        private WriteTask(Connection connection2, Buffer message2, EmptyCompletionHandler completionHandler2) {
            this.connection = connection2;
            this.message = message2;
            this.completionHandler = completionHandler2;
        }

        public void execute() {
            this.connection.write(this.message, this.completionHandler);
        }
    }

    public class CloseTask extends TaskProcessor.Task {
        private final Connection connection;

        private CloseTask(Connection connection2) {
            this.connection = connection2;
        }

        public void execute() {
            this.connection.closeSilently();
        }
    }
}
