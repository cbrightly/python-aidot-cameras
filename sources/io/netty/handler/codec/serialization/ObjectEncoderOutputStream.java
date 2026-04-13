package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import java.io.DataOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectEncoderOutputStream extends OutputStream implements ObjectOutput {
    private final int estimatedLength;
    private final DataOutputStream out;

    public ObjectEncoderOutputStream(OutputStream out2) {
        this(out2, 512);
    }

    public ObjectEncoderOutputStream(OutputStream out2, int estimatedLength2) {
        if (out2 == null) {
            throw new NullPointerException("out");
        } else if (estimatedLength2 >= 0) {
            if (out2 instanceof DataOutputStream) {
                this.out = (DataOutputStream) out2;
            } else {
                this.out = new DataOutputStream(out2);
            }
            this.estimatedLength = estimatedLength2;
        } else {
            throw new IllegalArgumentException("estimatedLength: " + estimatedLength2);
        }
    }

    public void writeObject(Object obj) {
        ObjectOutputStream oout;
        ByteBuf buf = Unpooled.buffer(this.estimatedLength);
        try {
            oout = new CompactObjectOutputStream(new ByteBufOutputStream(buf));
            oout.writeObject(obj);
            oout.flush();
            oout.close();
            int objectSize = buf.readableBytes();
            writeInt(objectSize);
            buf.getBytes(0, (OutputStream) this, objectSize);
            buf.release();
        } catch (Throwable th) {
            buf.release();
            throw th;
        }
    }

    public void write(int b) {
        this.out.write(b);
    }

    public void close() {
        this.out.close();
    }

    public void flush() {
        this.out.flush();
    }

    public final int size() {
        return this.out.size();
    }

    public void write(byte[] b, int off, int len) {
        this.out.write(b, off, len);
    }

    public void write(byte[] b) {
        this.out.write(b);
    }

    public final void writeBoolean(boolean v) {
        this.out.writeBoolean(v);
    }

    public final void writeByte(int v) {
        this.out.writeByte(v);
    }

    public final void writeBytes(String s) {
        this.out.writeBytes(s);
    }

    public final void writeChar(int v) {
        this.out.writeChar(v);
    }

    public final void writeChars(String s) {
        this.out.writeChars(s);
    }

    public final void writeDouble(double v) {
        this.out.writeDouble(v);
    }

    public final void writeFloat(float v) {
        this.out.writeFloat(v);
    }

    public final void writeInt(int v) {
        this.out.writeInt(v);
    }

    public final void writeLong(long v) {
        this.out.writeLong(v);
    }

    public final void writeShort(int v) {
        this.out.writeShort(v);
    }

    public final void writeUTF(String str) {
        this.out.writeUTF(str);
    }
}
