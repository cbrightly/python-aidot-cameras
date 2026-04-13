package com.didichuxing.doraemonkit.kit.weaknetwork;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import okio.b0;
import okio.c;
import okio.d;
import okio.e0;
import okio.f;
import okio.f0;
import okio.p;
import org.jetbrains.annotations.NotNull;

public class ByteCountBufferedSink implements d {
    private final long mByteCount;
    private final d mDelegate;
    private final b0 mOriginalSink;

    @NotNull
    public abstract /* synthetic */ c getBuffer();

    @NotNull
    public abstract /* synthetic */ d write(@NotNull f fVar, int i, int i2);

    public ByteCountBufferedSink(b0 sink, long byteCount) {
        this.mOriginalSink = sink;
        this.mDelegate = p.c(sink);
        this.mByteCount = byteCount;
    }

    public long writeAll(e0 source) {
        if (source != null) {
            long totalBytesRead = 0;
            while (true) {
                long read = source.read(buffer(), this.mByteCount);
                long readCount = read;
                if (read == -1) {
                    return totalBytesRead;
                }
                totalBytesRead += readCount;
                emitCompleteSegments();
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public d write(byte[] source, int offset, int byteCount) {
        if (isOpen()) {
            long count = (long) Math.ceil(((double) source.length) / ((double) this.mByteCount));
            for (int i = 0; ((long) i) < count; i++) {
                long j = this.mByteCount;
                long newOffset = ((long) i) * j;
                buffer().write(source, (int) newOffset, (int) Math.min(j, ((long) source.length) - newOffset));
                emitCompleteSegments();
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d emitCompleteSegments() {
        c buffer = buffer();
        this.mOriginalSink.write(buffer, buffer.e1());
        return this;
    }

    public c buffer() {
        return this.mDelegate.buffer();
    }

    public d write(f byteString) {
        return this.mDelegate.write(byteString);
    }

    public d write(byte[] source) {
        return this.mDelegate.write(source);
    }

    public d write(e0 source, long byteCount) {
        return this.mDelegate.write(source, byteCount);
    }

    public d writeUtf8(String string) {
        return this.mDelegate.writeUtf8(string);
    }

    public d writeUtf8(String string, int beginIndex, int endIndex) {
        return this.mDelegate.writeUtf8(string, beginIndex, endIndex);
    }

    public d writeUtf8CodePoint(int codePoint) {
        return this.mDelegate.writeUtf8CodePoint(codePoint);
    }

    public d writeString(String string, Charset charset) {
        return this.mDelegate.writeString(string, charset);
    }

    public d writeString(String string, int beginIndex, int endIndex, Charset charset) {
        return this.mDelegate.writeString(string, beginIndex, endIndex, charset);
    }

    public d writeByte(int b) {
        return this.mDelegate.writeByte(b);
    }

    public d writeShort(int s) {
        return this.mDelegate.writeShort(s);
    }

    public d writeShortLe(int s) {
        return this.mDelegate.writeShortLe(s);
    }

    public d writeInt(int i) {
        return this.mDelegate.writeInt(i);
    }

    public d writeIntLe(int i) {
        return this.mDelegate.writeIntLe(i);
    }

    public d writeLong(long v) {
        return this.mDelegate.writeLong(v);
    }

    public d writeLongLe(long v) {
        return this.mDelegate.writeLongLe(v);
    }

    public d writeDecimalLong(long v) {
        return this.mDelegate.writeDecimalLong(v);
    }

    public d writeHexadecimalUnsignedLong(long v) {
        return this.mDelegate.writeHexadecimalUnsignedLong(v);
    }

    public void flush() {
        this.mDelegate.flush();
    }

    public d emit() {
        return this.mDelegate.emit();
    }

    public OutputStream outputStream() {
        return this.mDelegate.outputStream();
    }

    public int write(ByteBuffer src) {
        return this.mDelegate.write(src);
    }

    public boolean isOpen() {
        return this.mDelegate.isOpen();
    }

    public void write(c source, long byteCount) {
        this.mDelegate.write(source, byteCount);
    }

    public f0 timeout() {
        return this.mDelegate.timeout();
    }

    public void close() {
        this.mDelegate.close();
    }
}
