package org.glassfish.grizzly.streams;

import androidx.work.WorkRequest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.memory.Buffers;

public abstract class AbstractStreamWriter implements StreamWriter {
    protected static final Integer ZERO = 0;
    protected static final GrizzlyFuture<Integer> ZERO_READY_FUTURE = ReadyFutureImpl.create(0);
    /* access modifiers changed from: protected */
    public static final Logger logger = Grizzly.logger(AbstractStreamWriter.class);
    private final Connection connection;
    private final AtomicBoolean isClosed = new AtomicBoolean();
    protected final boolean isOutputBuffered;
    protected final Output output;
    private long timeoutMillis = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;

    protected AbstractStreamWriter(Connection connection2, Output streamOutput) {
        this.connection = connection2;
        this.output = streamOutput;
        this.isOutputBuffered = streamOutput.isBuffered();
    }

    public GrizzlyFuture<Integer> flush() {
        return flush((CompletionHandler<Integer>) null);
    }

    public GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler) {
        return this.output.flush(completionHandler);
    }

    public boolean isClosed() {
        return this.isClosed.get();
    }

    public void close() {
        close((CompletionHandler<Integer>) null);
    }

    public GrizzlyFuture<Integer> close(CompletionHandler<Integer> completionHandler) {
        if (!this.isClosed.getAndSet(true)) {
            return this.output.close(completionHandler);
        }
        return ReadyFutureImpl.create(0);
    }

    public void writeBuffer(Buffer b) {
        this.output.write(b);
    }

    public void writeBoolean(boolean data) {
        writeByte((byte) data);
    }

    public void writeByte(byte data) {
        this.output.write(data);
    }

    public void writeChar(char data) {
        if (this.isOutputBuffered) {
            this.output.ensureBufferCapacity(2);
            this.output.getBuffer().putChar(data);
            return;
        }
        this.output.write((byte) ((data >>> 8) & 255));
        this.output.write((byte) (data & 255));
    }

    public void writeShort(short data) {
        if (this.isOutputBuffered) {
            this.output.ensureBufferCapacity(2);
            this.output.getBuffer().putShort(data);
            return;
        }
        this.output.write((byte) ((data >>> 8) & 255));
        this.output.write((byte) (data & 255));
    }

    public void writeInt(int data) {
        if (this.isOutputBuffered) {
            this.output.ensureBufferCapacity(4);
            this.output.getBuffer().putInt(data);
            return;
        }
        this.output.write((byte) ((data >>> 24) & 255));
        this.output.write((byte) ((data >>> 16) & 255));
        this.output.write((byte) ((data >>> 8) & 255));
        this.output.write((byte) (data & 255));
    }

    public void writeLong(long data) {
        if (this.isOutputBuffered) {
            this.output.ensureBufferCapacity(8);
            this.output.getBuffer().putLong(data);
            return;
        }
        this.output.write((byte) ((int) ((data >>> 56) & 255)));
        this.output.write((byte) ((int) ((data >>> 48) & 255)));
        this.output.write((byte) ((int) ((data >>> 40) & 255)));
        this.output.write((byte) ((int) ((data >>> 32) & 255)));
        this.output.write((byte) ((int) ((data >>> 24) & 255)));
        this.output.write((byte) ((int) ((data >>> 16) & 255)));
        this.output.write((byte) ((int) ((data >>> 8) & 255)));
        this.output.write((byte) ((int) (data & 255)));
    }

    public void writeFloat(float data) {
        writeInt(Float.floatToIntBits(data));
    }

    public void writeDouble(double data) {
        writeLong(Double.doubleToLongBits(data));
    }

    public void writeBooleanArray(boolean[] data) {
        for (boolean z : data) {
            this.output.write(z ? (byte) 1 : 0);
        }
    }

    public void writeByteArray(byte[] data) {
        writeByteArray(data, 0, data.length);
    }

    public void writeByteArray(byte[] data, int offset, int length) {
        this.output.write(Buffers.wrap(this.connection.getMemoryManager(), data, offset, length));
    }

    public void writeCharArray(char[] data) {
        for (char writeChar : data) {
            writeChar(writeChar);
        }
    }

    public void writeShortArray(short[] data) {
        for (short writeShort : data) {
            writeShort(writeShort);
        }
    }

    public void writeIntArray(int[] data) {
        for (int writeInt : data) {
            writeInt(writeInt);
        }
    }

    public void writeLongArray(long[] data) {
        for (long writeLong : data) {
            writeLong(writeLong);
        }
    }

    public void writeFloatArray(float[] data) {
        for (float writeFloat : data) {
            writeFloat(writeFloat);
        }
    }

    public void writeDoubleArray(double[] data) {
        for (double writeDouble : data) {
            writeDouble(writeDouble);
        }
    }

    public <E> GrizzlyFuture<Stream> encode(Transformer<E, Buffer> encoder, E object) {
        return encode(encoder, object, (CompletionHandler<Stream>) null);
    }

    public <E> GrizzlyFuture<Stream> encode(Transformer<E, Buffer> encoder, E object, CompletionHandler<Stream> completionHandler) {
        Exception exception = null;
        TransformationResult<E, Buffer> result = encoder.transform(this.connection, object);
        TransformationResult.Status status = result.getStatus();
        if (status == TransformationResult.Status.COMPLETE) {
            this.output.write(result.getMessage());
            if (completionHandler != null) {
                completionHandler.completed(this);
            }
            return ReadyFutureImpl.create(this);
        }
        if (status == TransformationResult.Status.INCOMPLETE) {
            exception = new IllegalStateException("Encoder returned INCOMPLETE state");
        }
        if (exception == null) {
            exception = new TransformationException(result.getErrorCode() + ": " + result.getErrorDescription());
        }
        return ReadyFutureImpl.create((Throwable) exception);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public long getTimeout(TimeUnit timeunit) {
        return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void setTimeout(long timeout, TimeUnit timeunit) {
        this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
    }

    public static class DisposeBufferCompletionHandler implements CompletionHandler {
        private final Buffer buffer;

        public DisposeBufferCompletionHandler(Buffer buffer2) {
            this.buffer = buffer2;
        }

        public void cancelled() {
            disposeBuffer();
        }

        public void failed(Throwable throwable) {
            disposeBuffer();
        }

        public void completed(Object result) {
            disposeBuffer();
        }

        public void updated(Object result) {
        }

        /* access modifiers changed from: protected */
        public void disposeBuffer() {
            this.buffer.dispose();
        }
    }
}
