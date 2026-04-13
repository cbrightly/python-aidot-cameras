package io.netty.handler.codec.serialization;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;

public class ObjectDecoderInputStream extends InputStream implements ObjectInput {
    private final ClassResolver classResolver;
    private final DataInputStream in;
    private final int maxObjectSize;

    public ObjectDecoderInputStream(InputStream in2) {
        this(in2, (ClassLoader) null);
    }

    public ObjectDecoderInputStream(InputStream in2, ClassLoader classLoader) {
        this(in2, classLoader, 1048576);
    }

    public ObjectDecoderInputStream(InputStream in2, int maxObjectSize2) {
        this(in2, (ClassLoader) null, maxObjectSize2);
    }

    public ObjectDecoderInputStream(InputStream in2, ClassLoader classLoader, int maxObjectSize2) {
        if (in2 == null) {
            throw new NullPointerException("in");
        } else if (maxObjectSize2 > 0) {
            if (in2 instanceof DataInputStream) {
                this.in = (DataInputStream) in2;
            } else {
                this.in = new DataInputStream(in2);
            }
            this.classResolver = ClassResolvers.weakCachingResolver(classLoader);
            this.maxObjectSize = maxObjectSize2;
        } else {
            throw new IllegalArgumentException("maxObjectSize: " + maxObjectSize2);
        }
    }

    public Object readObject() {
        int dataLen = readInt();
        if (dataLen <= 0) {
            throw new StreamCorruptedException("invalid data length: " + dataLen);
        } else if (dataLen <= this.maxObjectSize) {
            return new CompactObjectInputStream(this.in, this.classResolver).readObject();
        } else {
            throw new StreamCorruptedException("data length too big: " + dataLen + " (max: " + this.maxObjectSize + ')');
        }
    }

    public int available() {
        return this.in.available();
    }

    public void close() {
        this.in.close();
    }

    public void mark(int readlimit) {
        this.in.mark(readlimit);
    }

    public boolean markSupported() {
        return this.in.markSupported();
    }

    public int read() {
        return this.in.read();
    }

    public final int read(byte[] b, int off, int len) {
        return this.in.read(b, off, len);
    }

    public final int read(byte[] b) {
        return this.in.read(b);
    }

    public final boolean readBoolean() {
        return this.in.readBoolean();
    }

    public final byte readByte() {
        return this.in.readByte();
    }

    public final char readChar() {
        return this.in.readChar();
    }

    public final double readDouble() {
        return this.in.readDouble();
    }

    public final float readFloat() {
        return this.in.readFloat();
    }

    public final void readFully(byte[] b, int off, int len) {
        this.in.readFully(b, off, len);
    }

    public final void readFully(byte[] b) {
        this.in.readFully(b);
    }

    public final int readInt() {
        return this.in.readInt();
    }

    @Deprecated
    public final String readLine() {
        return this.in.readLine();
    }

    public final long readLong() {
        return this.in.readLong();
    }

    public final short readShort() {
        return this.in.readShort();
    }

    public final int readUnsignedByte() {
        return this.in.readUnsignedByte();
    }

    public final int readUnsignedShort() {
        return this.in.readUnsignedShort();
    }

    public final String readUTF() {
        return this.in.readUTF();
    }

    public void reset() {
        this.in.reset();
    }

    public long skip(long n) {
        return this.in.skip(n);
    }

    public final int skipBytes(int n) {
        return this.in.skipBytes(n);
    }
}
