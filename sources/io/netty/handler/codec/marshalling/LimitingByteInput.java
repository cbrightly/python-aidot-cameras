package io.netty.handler.codec.marshalling;

import java.io.IOException;
import org.jboss.marshalling.ByteInput;

public class LimitingByteInput implements ByteInput {
    private static final TooBigObjectException EXCEPTION = new TooBigObjectException();
    private final ByteInput input;
    private final long limit;
    private long read;

    LimitingByteInput(ByteInput input2, long limit2) {
        if (limit2 > 0) {
            this.input = input2;
            this.limit = limit2;
            return;
        }
        throw new IllegalArgumentException("The limit MUST be > 0");
    }

    public void close() {
    }

    public int available() {
        return readable(this.input.available());
    }

    public int read() {
        if (readable(1) > 0) {
            int b = this.input.read();
            this.read++;
            return b;
        }
        throw EXCEPTION;
    }

    public int read(byte[] array) {
        return read(array, 0, array.length);
    }

    public int read(byte[] array, int offset, int length) {
        int readable = readable(length);
        if (readable > 0) {
            int i = this.input.read(array, offset, readable);
            this.read += (long) i;
            return i;
        }
        throw EXCEPTION;
    }

    public long skip(long bytes) {
        int readable = readable((int) bytes);
        if (readable > 0) {
            long i = this.input.skip((long) readable);
            this.read += i;
            return i;
        }
        throw EXCEPTION;
    }

    private int readable(int length) {
        return (int) Math.min((long) length, this.limit - this.read);
    }

    public static final class TooBigObjectException extends IOException {
        private static final long serialVersionUID = 1;

        TooBigObjectException() {
        }
    }
}
