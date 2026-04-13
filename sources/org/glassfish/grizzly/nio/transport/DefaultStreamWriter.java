package org.glassfish.grizzly.nio.transport;

import java.net.SocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.streams.AbstractStreamWriter;
import org.glassfish.grizzly.streams.BufferedOutput;

public final class DefaultStreamWriter extends AbstractStreamWriter {
    public DefaultStreamWriter(Connection connection) {
        super(connection, new Output(connection));
    }

    public GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler) {
        return super.flush(new ResetCounterCompletionHandler((Output) this.output, completionHandler));
    }

    public static final class Output extends BufferedOutput {
        private final Connection connection;
        /* access modifiers changed from: private */
        public int sentBytesCounter;

        public Output(Connection connection2) {
            super(connection2.getWriteBufferSize());
            this.connection = connection2;
        }

        /* access modifiers changed from: protected */
        public GrizzlyFuture<Integer> flush0(Buffer buffer, CompletionHandler<Integer> completionHandler) {
            FutureImpl<Integer> future = SafeFutureImpl.create();
            if (buffer == null) {
                buffer = Buffers.EMPTY_BUFFER;
            }
            this.connection.write(buffer, new CompletionHandlerAdapter(this, future, completionHandler));
            return future;
        }

        /* access modifiers changed from: protected */
        public Buffer newBuffer(int size) {
            return this.connection.getMemoryManager().allocate(size);
        }

        /* access modifiers changed from: protected */
        public Buffer reallocateBuffer(Buffer oldBuffer, int size) {
            return this.connection.getMemoryManager().reallocate(oldBuffer, size);
        }

        /* access modifiers changed from: protected */
        public void onClosed() {
            this.connection.closeSilently();
        }
    }

    public static final class CompletionHandlerAdapter implements CompletionHandler<WriteResult<Buffer, SocketAddress>> {
        private final CompletionHandler<Integer> completionHandler;
        private final FutureImpl<Integer> future;
        private final Output output;

        public CompletionHandlerAdapter(Output output2, FutureImpl<Integer> future2, CompletionHandler<Integer> completionHandler2) {
            this.output = output2;
            this.future = future2;
            this.completionHandler = completionHandler2;
        }

        public void cancelled() {
            CompletionHandler<Integer> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.cancelled();
            }
            FutureImpl<Integer> futureImpl = this.future;
            if (futureImpl != null) {
                futureImpl.cancel(false);
            }
        }

        public void failed(Throwable throwable) {
            CompletionHandler<Integer> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.failed(throwable);
            }
            FutureImpl<Integer> futureImpl = this.future;
            if (futureImpl != null) {
                futureImpl.failure(throwable);
            }
        }

        public void completed(WriteResult result) {
            Output output2 = this.output;
            int unused = output2.sentBytesCounter = (int) (((long) output2.sentBytesCounter) + result.getWrittenSize());
            int totalSentBytes = this.output.sentBytesCounter;
            CompletionHandler<Integer> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.completed(Integer.valueOf(totalSentBytes));
            }
            FutureImpl<Integer> futureImpl = this.future;
            if (futureImpl != null) {
                futureImpl.result(Integer.valueOf(totalSentBytes));
            }
        }

        public void updated(WriteResult result) {
            CompletionHandler<Integer> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.updated(Integer.valueOf(this.output.sentBytesCounter + ((int) result.getWrittenSize())));
            }
        }
    }

    public static final class ResetCounterCompletionHandler implements CompletionHandler<Integer> {
        private final Output output;
        private final CompletionHandler<Integer> parentCompletionHandler;

        public ResetCounterCompletionHandler(Output output2, CompletionHandler<Integer> parentCompletionHandler2) {
            this.output = output2;
            this.parentCompletionHandler = parentCompletionHandler2;
        }

        public void cancelled() {
            CompletionHandler<Integer> completionHandler = this.parentCompletionHandler;
            if (completionHandler != null) {
                completionHandler.cancelled();
            }
        }

        public void failed(Throwable throwable) {
            CompletionHandler<Integer> completionHandler = this.parentCompletionHandler;
            if (completionHandler != null) {
                completionHandler.failed(throwable);
            }
        }

        public void completed(Integer result) {
            int unused = this.output.sentBytesCounter = 0;
            CompletionHandler<Integer> completionHandler = this.parentCompletionHandler;
            if (completionHandler != null) {
                completionHandler.completed(result);
            }
        }

        public void updated(Integer result) {
            CompletionHandler<Integer> completionHandler = this.parentCompletionHandler;
            if (completionHandler != null) {
                completionHandler.updated(result);
            }
        }
    }
}
