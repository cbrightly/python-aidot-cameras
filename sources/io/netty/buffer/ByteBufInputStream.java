package io.netty.buffer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;

public class ByteBufInputStream extends InputStream implements DataInput {
    private final ByteBuf buffer;
    private boolean closed;
    private final int endIndex;
    private final StringBuilder lineBuf;
    private boolean releaseOnClose;
    private final int startIndex;

    public ByteBufInputStream(ByteBuf buffer2) {
        this(buffer2, buffer2.readableBytes());
    }

    public ByteBufInputStream(ByteBuf buffer2, int length) {
        this(buffer2, length, false);
    }

    public ByteBufInputStream(ByteBuf buffer2, boolean releaseOnClose2) {
        this(buffer2, buffer2.readableBytes(), releaseOnClose2);
    }

    public ByteBufInputStream(ByteBuf buffer2, int length, boolean releaseOnClose2) {
        this.lineBuf = new StringBuilder();
        if (buffer2 == null) {
            throw new NullPointerException("buffer");
        } else if (length < 0) {
            if (releaseOnClose2) {
                buffer2.release();
            }
            throw new IllegalArgumentException("length: " + length);
        } else if (length > buffer2.readableBytes()) {
            if (releaseOnClose2) {
                buffer2.release();
            }
            throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + length + ", maximum is " + buffer2.readableBytes());
        } else {
            this.releaseOnClose = releaseOnClose2;
            this.buffer = buffer2;
            int readerIndex = buffer2.readerIndex();
            this.startIndex = readerIndex;
            this.endIndex = readerIndex + length;
            buffer2.markReaderIndex();
        }
    }

    public int readBytes() {
        return this.buffer.readerIndex() - this.startIndex;
    }

    public void close() {
        try {
            super.close();
        } finally {
            if (this.releaseOnClose && !this.closed) {
                this.closed = true;
                this.buffer.release();
            }
        }
    }

    public int available() {
        return this.endIndex - this.buffer.readerIndex();
    }

    public void mark(int readlimit) {
        this.buffer.markReaderIndex();
    }

    public boolean markSupported() {
        return true;
    }

    public int read() {
        if (!this.buffer.isReadable()) {
            return -1;
        }
        return this.buffer.readByte() & 255;
    }

    public int read(byte[] b, int off, int len) {
        int available = available();
        if (available == 0) {
            return -1;
        }
        int len2 = Math.min(available, len);
        this.buffer.readBytes(b, off, len2);
        return len2;
    }

    public void reset() {
        this.buffer.resetReaderIndex();
    }

    public long skip(long n) {
        if (n > 2147483647L) {
            return (long) skipBytes(Integer.MAX_VALUE);
        }
        return (long) skipBytes((int) n);
    }

    public boolean readBoolean() {
        checkAvailable(1);
        if (read() != 0) {
            return true;
        }
        return false;
    }

    public byte readByte() {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte();
        }
        throw new EOFException();
    }

    public char readChar() {
        return (char) readShort();
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public void readFully(byte[] b) {
        readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) {
        checkAvailable(len);
        this.buffer.readBytes(b, off, len);
    }

    public int readInt() {
        checkAvailable(4);
        return this.buffer.readInt();
    }

    public String readLine() {
        this.lineBuf.setLength(0);
        while (this.buffer.isReadable()) {
            int c = this.buffer.readUnsignedByte();
            switch (c) {
                case 10:
                    break;
                case 13:
                    if (this.buffer.isReadable()) {
                        ByteBuf byteBuf = this.buffer;
                        if (((char) byteBuf.getUnsignedByte(byteBuf.readerIndex())) == 10) {
                            this.buffer.skipBytes(1);
                            break;
                        }
                    }
                    break;
                default:
                    this.lineBuf.append((char) c);
            }
            return this.lineBuf.toString();
        }
        if (this.lineBuf.length() > 0) {
            return this.lineBuf.toString();
        }
        return null;
    }

    public long readLong() {
        checkAvailable(8);
        return this.buffer.readLong();
    }

    public short readShort() {
        checkAvailable(2);
        return this.buffer.readShort();
    }

    public String readUTF() {
        return DataInputStream.readUTF(this);
    }

    public int readUnsignedByte() {
        return readByte() & 255;
    }

    public int readUnsignedShort() {
        return readShort() & 65535;
    }

    public int skipBytes(int n) {
        int nBytes = Math.min(available(), n);
        this.buffer.skipBytes(nBytes);
        return nBytes;
    }

    private void checkAvailable(int fieldSize) {
        if (fieldSize < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
        } else if (fieldSize > available()) {
            throw new EOFException("fieldSize is too long! Length is " + fieldSize + ", but maximum is " + available());
        }
    }
}
