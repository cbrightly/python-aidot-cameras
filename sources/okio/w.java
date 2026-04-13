package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RealBufferedSink.kt */
public final class w implements d {
    @NotNull
    public final c c = new c();
    public boolean d;
    @NotNull
    public final b0 f;

    public w(@NotNull b0 sink) {
        k.e(sink, "sink");
        this.f = sink;
    }

    @NotNull
    public c getBuffer() {
        return this.c;
    }

    @NotNull
    public c buffer() {
        return this.c;
    }

    public void write(@NotNull c source, long byteCount) {
        k.e(source, "source");
        if (!this.d) {
            this.c.write(source, byteCount);
            emitCompleteSegments();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d write(@NotNull f byteString) {
        k.e(byteString, "byteString");
        if (!this.d) {
            this.c.write(byteString);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeUtf8(@NotNull String string) {
        k.e(string, TypedValues.Custom.S_STRING);
        if (!this.d) {
            this.c.writeUtf8(string);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeUtf8(@NotNull String string, int beginIndex, int endIndex) {
        k.e(string, TypedValues.Custom.S_STRING);
        if (!this.d) {
            this.c.writeUtf8(string, beginIndex, endIndex);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeUtf8CodePoint(int codePoint) {
        if (!this.d) {
            this.c.writeUtf8CodePoint(codePoint);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeString(@NotNull String string, @NotNull Charset charset) {
        k.e(string, TypedValues.Custom.S_STRING);
        k.e(charset, "charset");
        if (!this.d) {
            this.c.writeString(string, charset);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeString(@NotNull String string, int beginIndex, int endIndex, @NotNull Charset charset) {
        k.e(string, TypedValues.Custom.S_STRING);
        k.e(charset, "charset");
        if (!this.d) {
            this.c.writeString(string, beginIndex, endIndex, charset);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d write(@NotNull byte[] source) {
        k.e(source, "source");
        if (!this.d) {
            this.c.write(source);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d write(@NotNull byte[] source, int offset, int byteCount) {
        k.e(source, "source");
        if (!this.d) {
            this.c.write(source, offset, byteCount);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    public int write(@NotNull ByteBuffer source) {
        k.e(source, "source");
        if (!this.d) {
            int result = this.c.write(source);
            emitCompleteSegments();
            return result;
        }
        throw new IllegalStateException("closed".toString());
    }

    public long writeAll(@NotNull e0 source) {
        k.e(source, "source");
        long totalBytesRead$iv = 0;
        while (true) {
            long readCount$iv = source.read(this.c, (long) 8192);
            if (readCount$iv == -1) {
                return totalBytesRead$iv;
            }
            totalBytesRead$iv += readCount$iv;
            emitCompleteSegments();
        }
    }

    @NotNull
    public d write(@NotNull e0 source, long byteCount) {
        k.e(source, "source");
        long byteCount$iv = byteCount;
        while (byteCount$iv > 0) {
            long read$iv = source.read(this.c, byteCount$iv);
            if (read$iv != -1) {
                byteCount$iv -= read$iv;
                emitCompleteSegments();
            } else {
                throw new EOFException();
            }
        }
        return this;
    }

    @NotNull
    public d writeByte(int b) {
        if (!this.d) {
            this.c.writeByte(b);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeShort(int s) {
        if (!this.d) {
            this.c.writeShort(s);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeShortLe(int s) {
        if (!this.d) {
            this.c.writeShortLe(s);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeInt(int i) {
        if (!this.d) {
            this.c.writeInt(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeIntLe(int i) {
        if (!this.d) {
            this.c.writeIntLe(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeLong(long v) {
        if (!this.d) {
            this.c.writeLong(v);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeLongLe(long v) {
        if (!this.d) {
            this.c.writeLongLe(v);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeDecimalLong(long v) {
        if (!this.d) {
            this.c.writeDecimalLong(v);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d writeHexadecimalUnsignedLong(long v) {
        if (!this.d) {
            this.c.writeHexadecimalUnsignedLong(v);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d emitCompleteSegments() {
        if (!this.d) {
            long byteCount$iv = this.c.c();
            if (byteCount$iv > 0) {
                this.f.write(this.c, byteCount$iv);
            }
            return this;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public d emit() {
        if (!this.d) {
            long byteCount$iv = this.c.e1();
            if (byteCount$iv > 0) {
                this.f.write(this.c, byteCount$iv);
            }
            return this;
        }
        throw new IllegalStateException("closed".toString());
    }

    /* compiled from: RealBufferedSink.kt */
    public static final class a extends OutputStream {
        final /* synthetic */ w c;

        a(w this$0) {
            this.c = this$0;
        }

        public void write(int b) {
            if (!this.c.d) {
                this.c.c.writeByte((byte) b);
                this.c.emitCompleteSegments();
                return;
            }
            throw new IOException("closed");
        }

        public void write(@NotNull byte[] data, int offset, int byteCount) {
            k.e(data, "data");
            if (!this.c.d) {
                this.c.c.write(data, offset, byteCount);
                this.c.emitCompleteSegments();
                return;
            }
            throw new IOException("closed");
        }

        public void flush() {
            w wVar = this.c;
            if (!wVar.d) {
                wVar.flush();
            }
        }

        public void close() {
            this.c.close();
        }

        @NotNull
        public String toString() {
            return this.c + ".outputStream()";
        }
    }

    @NotNull
    public OutputStream outputStream() {
        return new a(this);
    }

    public void flush() {
        if (!this.d) {
            if (this.c.e1() > 0) {
                this.f.write(this.c, this.c.e1());
            }
            this.f.flush();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    public boolean isOpen() {
        return !this.d;
    }

    public void close() {
        if (!this.d) {
            Throwable thrown$iv = null;
            try {
                if (this.c.e1() > 0) {
                    this.f.write(this.c, this.c.e1());
                }
            } catch (Throwable e$iv) {
                thrown$iv = e$iv;
            }
            try {
                this.f.close();
            } catch (Throwable e$iv2) {
                if (thrown$iv == null) {
                    thrown$iv = e$iv2;
                }
            }
            this.d = true;
            if (thrown$iv != null) {
                throw thrown$iv;
            }
        }
    }

    @NotNull
    public f0 timeout() {
        return this.f.timeout();
    }

    @NotNull
    public String toString() {
        return "buffer(" + this.f + ')';
    }
}
