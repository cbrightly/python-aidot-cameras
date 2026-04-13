package io.netty.buffer;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;

public class ByteBufOutputStream extends OutputStream implements DataOutput {
    private final ByteBuf buffer;
    private final int startIndex;
    private final DataOutputStream utf8out = new DataOutputStream(this);

    public ByteBufOutputStream(ByteBuf buffer2) {
        if (buffer2 != null) {
            this.buffer = buffer2;
            this.startIndex = buffer2.writerIndex();
            return;
        }
        throw new NullPointerException("buffer");
    }

    public int writtenBytes() {
        return this.buffer.writerIndex() - this.startIndex;
    }

    public void write(byte[] b, int off, int len) {
        if (len != 0) {
            this.buffer.writeBytes(b, off, len);
        }
    }

    public void write(byte[] b) {
        this.buffer.writeBytes(b);
    }

    public void write(int b) {
        this.buffer.writeByte(b);
    }

    public void writeBoolean(boolean v) {
        this.buffer.writeBoolean(v);
    }

    public void writeByte(int v) {
        this.buffer.writeByte(v);
    }

    public void writeBytes(String s) {
        ByteBufUtil.writeAscii(this.buffer, (CharSequence) s);
    }

    public void writeChar(int v) {
        this.buffer.writeChar(v);
    }

    public void writeChars(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            this.buffer.writeChar(s.charAt(i));
        }
    }

    public void writeDouble(double v) {
        this.buffer.writeDouble(v);
    }

    public void writeFloat(float v) {
        this.buffer.writeFloat(v);
    }

    public void writeInt(int v) {
        this.buffer.writeInt(v);
    }

    public void writeLong(long v) {
        this.buffer.writeLong(v);
    }

    public void writeShort(int v) {
        this.buffer.writeShort((short) v);
    }

    public void writeUTF(String s) {
        this.utf8out.writeUTF(s);
    }

    public ByteBuf buffer() {
        return this.buffer;
    }
}
