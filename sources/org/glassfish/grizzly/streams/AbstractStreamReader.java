package org.glassfish.grizzly.streams;

import java.io.EOFException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.ResultAware;
import org.glassfish.grizzly.utils.conditions.Condition;

public abstract class AbstractStreamReader implements StreamReader {
    private static final boolean DEBUG = false;
    private static final Logger LOGGER = Grizzly.logger(AbstractStreamReader.class);
    protected final Connection connection;
    protected final Input input;
    protected final AtomicBoolean isClosed = new AtomicBoolean(false);

    private static void msg(String msg) {
        LOGGER.log(Level.INFO, "READERSTREAM:DEBUG:{0}", msg);
    }

    private static void displayBuffer(String str, Buffer wrapper) {
        msg(str);
        msg("\tposition()     = " + wrapper.position());
        msg("\tlimit()        = " + wrapper.limit());
        msg("\tcapacity()     = " + wrapper.capacity());
    }

    protected AbstractStreamReader(Connection connection2, Input streamInput) {
        this.input = streamInput;
        this.connection = connection2;
    }

    public boolean readBoolean() {
        return readByte() == 1;
    }

    public byte readByte() {
        return this.input.read();
    }

    public char readChar() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 2) {
            return (char) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        char result = buffer.getChar();
        buffer.shrink();
        return result;
    }

    public short readShort() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        short result = buffer.getShort();
        buffer.shrink();
        return result;
    }

    public int readInt() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 4) {
            return ((readShort() & 65535) << 16) | (65535 & readShort());
        }
        int result = buffer.getInt();
        buffer.shrink();
        return result;
    }

    public long readLong() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (4294967295L & ((long) readInt()));
        }
        long result = buffer.getLong();
        buffer.shrink();
        return result;
    }

    public final float readFloat() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 4) {
            return Float.intBitsToFloat(readInt());
        }
        float result = buffer.getFloat();
        buffer.shrink();
        return result;
    }

    public final double readDouble() {
        Buffer buffer;
        if (!this.input.isBuffered() || (buffer = this.input.getBuffer()) == null || buffer.remaining() < 8) {
            return Double.longBitsToDouble(readLong());
        }
        double result = buffer.getDouble();
        buffer.shrink();
        return result;
    }

    private void arraySizeCheck(int sizeInBytes) {
        if (sizeInBytes > available()) {
            throw new BufferUnderflowException();
        }
    }

    public void readBooleanArray(boolean[] data) {
        arraySizeCheck(data.length);
        for (int ctr = 0; ctr < data.length; ctr++) {
            data[ctr] = readBoolean();
        }
    }

    public void readByteArray(byte[] data) {
        readByteArray(data, 0, data.length);
    }

    public void readByteArray(byte[] data, int offset, int length) {
        arraySizeCheck(length);
        if (this.input.isBuffered()) {
            Buffer buffer = this.input.getBuffer();
            buffer.get(data, offset, length);
            buffer.shrink();
            return;
        }
        for (int i = offset; i < length; i++) {
            data[i] = this.input.read();
        }
    }

    public void readBytes(Buffer buffer) {
        if (buffer.hasRemaining()) {
            arraySizeCheck(buffer.remaining());
            if (this.input.isBuffered()) {
                Buffer inputBuffer = this.input.getBuffer();
                int diff = buffer.remaining() - inputBuffer.remaining();
                if (diff >= 0) {
                    buffer.put(inputBuffer);
                } else {
                    int save = inputBuffer.limit();
                    inputBuffer.limit(save + diff);
                    buffer.put(inputBuffer);
                    inputBuffer.limit(save);
                }
                inputBuffer.shrink();
                return;
            }
            while (buffer.hasRemaining()) {
                buffer.put(this.input.read());
            }
        }
    }

    public void readCharArray(char[] data) {
        arraySizeCheck(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            data[i] = readChar();
        }
    }

    public void readShortArray(short[] data) {
        arraySizeCheck(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            data[i] = readShort();
        }
    }

    public void readIntArray(int[] data) {
        arraySizeCheck(data.length * 4);
        for (int i = 0; i < data.length; i++) {
            data[i] = readInt();
        }
    }

    public void readLongArray(long[] data) {
        arraySizeCheck(data.length * 8);
        for (int i = 0; i < data.length; i++) {
            data[i] = readLong();
        }
    }

    public void readFloatArray(float[] data) {
        arraySizeCheck(data.length * 4);
        for (int i = 0; i < data.length; i++) {
            data[i] = readFloat();
        }
    }

    public void readDoubleArray(double[] data) {
        arraySizeCheck(data.length * 8);
        for (int i = 0; i < data.length; i++) {
            data[i] = readDouble();
        }
    }

    public void skip(int length) {
        this.input.skip(length);
    }

    public <E> GrizzlyFuture<E> decode(Transformer<Stream, E> decoder) {
        return decode(decoder, (CompletionHandler) null);
    }

    public <E> GrizzlyFuture<E> decode(Transformer<Stream, E> decoder, CompletionHandler<E> completionHandler) {
        FutureImpl<E> future = SafeFutureImpl.create();
        DecodeCompletionHandler<E, Integer> completionHandlerWrapper = new DecodeCompletionHandler<>(future, completionHandler);
        notifyCondition(new StreamDecodeCondition(this, decoder, completionHandlerWrapper), completionHandlerWrapper);
        return future;
    }

    public GrizzlyFuture<Integer> notifyAvailable(int size) {
        return notifyAvailable(size, (CompletionHandler<Integer>) null);
    }

    public GrizzlyFuture<Integer> notifyAvailable(final int size, CompletionHandler<Integer> completionHandler) {
        return notifyCondition(new Condition() {
            public boolean check() {
                return AbstractStreamReader.this.available() >= size;
            }
        }, completionHandler);
    }

    public GrizzlyFuture<Integer> notifyCondition(Condition condition) {
        return notifyCondition(condition, (CompletionHandler<Integer>) null);
    }

    public synchronized GrizzlyFuture<Integer> notifyCondition(Condition condition, CompletionHandler<Integer> completionHandler) {
        if (isClosed()) {
            EOFException exception = new EOFException();
            if (completionHandler != null) {
                completionHandler.failed(exception);
            }
            return ReadyFutureImpl.create((Throwable) exception);
        }
        return this.input.notifyCondition(condition, completionHandler);
    }

    public void close() {
        Input input2;
        if (this.isClosed.compareAndSet(false, true) && (input2 = this.input) != null) {
            try {
                input2.close();
            } catch (IOException e) {
            }
        }
    }

    public boolean isClosed() {
        return this.isClosed.get();
    }

    public final boolean hasAvailable() {
        return available() > 0;
    }

    public int available() {
        return this.input.size();
    }

    public boolean isSupportBufferWindow() {
        return this.input.isBuffered();
    }

    public Buffer getBufferWindow() {
        return this.input.getBuffer();
    }

    public Buffer takeBufferWindow() {
        return this.input.takeBuffer();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static class DecodeCompletionHandler<A, B> extends CompletionHandlerAdapter<A, B> implements ResultAware<A> {
        private volatile A result;

        public DecodeCompletionHandler(FutureImpl<A> future, CompletionHandler<A> completionHandler) {
            super(future, completionHandler);
        }

        public void setResult(A result2) {
            this.result = result2;
        }

        /* access modifiers changed from: protected */
        public A adapt(B b) {
            return this.result;
        }
    }
}
