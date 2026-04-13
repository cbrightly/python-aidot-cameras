package okio;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import okio.internal.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealBufferedSource.kt */
public final class x implements e {
    @NotNull
    public final c c;
    public boolean d;
    private final g f;
    @NotNull
    public final e0 q;

    public x(@NotNull e0 source) {
        okio.internal.a aVar;
        k.e(source, "source");
        this.q = source;
        c cVar = new c();
        this.c = cVar;
        g $this$run = source.cursor();
        if ($this$run != null) {
            aVar = new okio.internal.a(cVar, $this$run);
        } else {
            aVar = null;
        }
        this.f = aVar;
    }

    @NotNull
    public c getBuffer() {
        return this.c;
    }

    @NotNull
    public c buffer() {
        return this.c;
    }

    public long read(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        } else if (!(!this.d)) {
            throw new IllegalStateException("closed".toString());
        } else if (this.c.e1() == 0 && this.q.read(this.c, (long) 8192) == -1) {
            return -1;
        } else {
            return this.c.read(sink, Math.min(byteCount, this.c.e1()));
        }
    }

    public boolean r0() {
        if (!(!this.d)) {
            throw new IllegalStateException("closed".toString());
        } else if (!this.c.r0() || this.q.read(this.c, (long) 8192) != -1) {
            return false;
        } else {
            return true;
        }
    }

    public void k0(long byteCount) {
        if (!request(byteCount)) {
            throw new EOFException();
        }
    }

    public boolean request(long byteCount) {
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        } else if (!this.d) {
            while (this.c.e1() < byteCount) {
                if (this.q.read(this.c, (long) 8192) == -1) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public byte readByte() {
        k0(1);
        return this.c.readByte();
    }

    @NotNull
    public f D0() {
        this.c.writeAll(this.q);
        return this.c.D0();
    }

    @NotNull
    public f m0(long byteCount) {
        k0(byteCount);
        return this.c.m0(byteCount);
    }

    public int Z0(@NotNull s options) {
        k.e(options, "options");
        if (!this.d) {
            do {
                int index$iv = b.e(this.c, options, true);
                switch (index$iv) {
                    case -2:
                        break;
                    case -1:
                        return -1;
                    default:
                        this.c.skip((long) options.f()[index$iv].size());
                        return index$iv;
                }
            } while (this.q.read(this.c, (long) 8192) != -1);
            return -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public byte[] q0() {
        this.c.writeAll(this.q);
        return this.c.q0();
    }

    @NotNull
    public byte[] g0(long byteCount) {
        k0(byteCount);
        return this.c.g0(byteCount);
    }

    public void readFully(@NotNull byte[] sink) {
        k.e(sink, "sink");
        try {
            k0((long) sink.length);
            this.c.readFully(sink);
        } catch (EOFException e$iv) {
            int offset$iv = 0;
            while (this.c.e1() > 0) {
                int read$iv = this.c.read(sink, offset$iv, (int) this.c.e1());
                if (read$iv != -1) {
                    offset$iv += read$iv;
                } else {
                    throw new AssertionError();
                }
            }
            throw e$iv;
        }
    }

    public int read(@NotNull ByteBuffer sink) {
        k.e(sink, "sink");
        if (this.c.e1() == 0 && this.q.read(this.c, (long) 8192) == -1) {
            return -1;
        }
        return this.c.read(sink);
    }

    public void L(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        try {
            k0(byteCount);
            this.c.L(sink, byteCount);
        } catch (EOFException e$iv) {
            sink.writeAll(this.c);
            throw e$iv;
        }
    }

    public long M0(@NotNull b0 sink) {
        k.e(sink, "sink");
        long totalBytesWritten$iv = 0;
        while (this.q.read(this.c, (long) 8192) != -1) {
            long emitByteCount$iv = this.c.c();
            if (emitByteCount$iv > 0) {
                totalBytesWritten$iv += emitByteCount$iv;
                sink.write(this.c, emitByteCount$iv);
            }
        }
        if (this.c.e1() <= 0) {
            return totalBytesWritten$iv;
        }
        long totalBytesWritten$iv2 = totalBytesWritten$iv + this.c.e1();
        sink.write(this.c, this.c.e1());
        return totalBytesWritten$iv2;
    }

    @NotNull
    public String x0(@NotNull Charset charset) {
        k.e(charset, "charset");
        this.c.writeAll(this.q);
        return this.c.x0(charset);
    }

    @NotNull
    public String d0() {
        return Q(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    @NotNull
    public String Q(long limit) {
        long j = limit;
        if (j >= 0) {
            long scanLength$iv = j == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE ? Long.MAX_VALUE : j + 1;
            byte b = (byte) 10;
            byte b2 = b;
            long newline$iv = M(b, 0, scanLength$iv);
            if (newline$iv != -1) {
                return b.d(this.c, newline$iv);
            }
            if (scanLength$iv < DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE && request(scanLength$iv) && this.c.n(scanLength$iv - 1) == ((byte) 13) && request(1 + scanLength$iv) && this.c.n(scanLength$iv) == b2) {
                return b.d(this.c, scanLength$iv);
            }
            c data$iv = new c();
            this.c.j(data$iv, 0, Math.min((long) 32, this.c.e1()));
            throw new EOFException("\\n not found: limit=" + Math.min(this.c.e1(), j) + " content=" + data$iv.D0().hex() + "…");
        }
        throw new IllegalArgumentException(("limit < 0: " + j).toString());
    }

    public short readShort() {
        k0(2);
        return this.c.readShort();
    }

    public short l() {
        k0(2);
        return this.c.I();
    }

    public int readInt() {
        k0(4);
        return this.c.readInt();
    }

    public int j() {
        k0(4);
        return this.c.z();
    }

    public long readLong() {
        k0(8);
        return this.c.readLong();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        if (r4 == 0) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append("Expected leading [0-9] or '-' character but was 0x");
        r7 = java.lang.Integer.toString(r6, kotlin.text.a.a(kotlin.text.a.a(16)));
        kotlin.jvm.internal.k.d(r7, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        r3.append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005f, code lost:
        throw new java.lang.NumberFormatException(r3.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long s0() {
        /*
            r10 = this;
            r0 = r10
            r1 = 0
            r2 = 1
            r0.k0(r2)
            r4 = 0
        L_0x0009:
            long r6 = r4 + r2
            boolean r6 = r0.request(r6)
            if (r6 == 0) goto L_0x0060
            r6 = r0
            r7 = 0
            okio.c r6 = r6.c
            byte r6 = r6.n(r4)
            r7 = 48
            byte r7 = (byte) r7
            if (r6 < r7) goto L_0x0023
            r7 = 57
            byte r7 = (byte) r7
            if (r6 <= r7) goto L_0x002f
        L_0x0023:
            r7 = 0
            int r9 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x0031
            r9 = 45
            byte r9 = (byte) r9
            if (r6 == r9) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            long r4 = r4 + r2
            goto L_0x0009
        L_0x0031:
            int r2 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r2 == 0) goto L_0x0036
            goto L_0x0060
        L_0x0036:
            java.lang.NumberFormatException r2 = new java.lang.NumberFormatException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r7 = "Expected leading [0-9] or '-' character but was 0x"
            r3.append(r7)
            r7 = 16
            int r7 = kotlin.text.a.a(r7)
            int r7 = kotlin.text.a.a(r7)
            java.lang.String r7 = java.lang.Integer.toString(r6, r7)
            java.lang.String r8 = "java.lang.Integer.toStri…(this, checkRadix(radix))"
            kotlin.jvm.internal.k.d(r7, r8)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0060:
            r2 = r0
            r3 = 0
            okio.c r2 = r2.c
            long r0 = r2.s0()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.x.s0():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long X0() {
        /*
            r8 = this;
            r0 = r8
            r1 = 0
            r2 = 1
            r0.k0(r2)
            r2 = 0
        L_0x0008:
            int r3 = r2 + 1
            long r3 = (long) r3
            boolean r3 = r0.request(r3)
            if (r3 == 0) goto L_0x006d
            r3 = r0
            r4 = 0
            okio.c r3 = r3.c
            long r4 = (long) r2
            byte r3 = r3.n(r4)
            r4 = 48
            byte r4 = (byte) r4
            if (r3 < r4) goto L_0x0027
            r4 = 57
            byte r4 = (byte) r4
            if (r3 <= r4) goto L_0x003c
        L_0x0027:
            r4 = 97
            byte r4 = (byte) r4
            if (r3 < r4) goto L_0x0031
            r4 = 102(0x66, float:1.43E-43)
            byte r4 = (byte) r4
            if (r3 <= r4) goto L_0x003c
        L_0x0031:
            r4 = 65
            byte r4 = (byte) r4
            if (r3 < r4) goto L_0x0040
            r4 = 70
            byte r4 = (byte) r4
            if (r3 <= r4) goto L_0x003c
            goto L_0x0040
        L_0x003c:
            int r2 = r2 + 1
            goto L_0x0008
        L_0x0040:
            if (r2 == 0) goto L_0x0043
            goto L_0x006d
        L_0x0043:
            java.lang.NumberFormatException r4 = new java.lang.NumberFormatException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Expected leading [0-9a-fA-F] character but was 0x"
            r5.append(r6)
            r6 = 16
            int r6 = kotlin.text.a.a(r6)
            int r6 = kotlin.text.a.a(r6)
            java.lang.String r6 = java.lang.Integer.toString(r3, r6)
            java.lang.String r7 = "java.lang.Integer.toStri…(this, checkRadix(radix))"
            kotlin.jvm.internal.k.d(r6, r7)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x006d:
            r3 = r0
            r4 = 0
            okio.c r3 = r3.c
            long r0 = r3.X0()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.x.X0():long");
    }

    public void skip(long byteCount) {
        long byteCount$iv = byteCount;
        if (!this.d) {
            while (byteCount$iv > 0) {
                if (this.c.e1() == 0 && this.q.read(this.c, (long) 8192) == -1) {
                    throw new EOFException();
                }
                long toSkip$iv = Math.min(byteCount$iv, this.c.e1());
                this.c.skip(toSkip$iv);
                byteCount$iv -= toSkip$iv;
            }
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    public long a(byte b) {
        return M(b, 0, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    public long M(byte b, long fromIndex, long toIndex) {
        long j = toIndex;
        long fromIndex$iv = fromIndex;
        boolean z = true;
        if (!this.d) {
            if (0 > fromIndex$iv || j < fromIndex$iv) {
                z = false;
            }
            if (z) {
                long fromIndex$iv2 = fromIndex$iv;
                while (fromIndex$iv2 < j) {
                    long result$iv = this.c.M(b, fromIndex$iv2, toIndex);
                    if (result$iv != -1) {
                        return result$iv;
                    }
                    long lastBufferSize$iv = this.c.e1();
                    if (lastBufferSize$iv >= j || this.q.read(this.c, (long) 8192) == -1) {
                        return -1;
                    }
                    fromIndex$iv2 = Math.max(fromIndex$iv2, lastBufferSize$iv);
                }
                return -1;
            }
            throw new IllegalArgumentException(("fromIndex=" + fromIndex$iv + " toIndex=" + j).toString());
        }
        throw new IllegalStateException("closed".toString());
    }

    public long F(@NotNull f bytes) {
        k.e(bytes, "bytes");
        return c(bytes, 0);
    }

    public long c(@NotNull f bytes, long fromIndex) {
        f fVar = bytes;
        k.e(fVar, "bytes");
        long fromIndex$iv = fromIndex;
        if (!this.d) {
            while (true) {
                long result$iv = this.c.r(fVar, fromIndex$iv);
                if (result$iv != -1) {
                    return result$iv;
                }
                long lastBufferSize$iv = this.c.e1();
                if (this.q.read(this.c, (long) 8192) == -1) {
                    return -1;
                }
                fromIndex$iv = Math.max(fromIndex$iv, (lastBufferSize$iv - ((long) bytes.size())) + 1);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public long N(@NotNull f targetBytes) {
        k.e(targetBytes, "targetBytes");
        return g(targetBytes, 0);
    }

    public long g(@NotNull f targetBytes, long fromIndex) {
        f fVar = targetBytes;
        k.e(fVar, "targetBytes");
        long fromIndex$iv = fromIndex;
        if (!this.d) {
            while (true) {
                long result$iv = this.c.s(fVar, fromIndex$iv);
                if (result$iv != -1) {
                    return result$iv;
                }
                long lastBufferSize$iv = this.c.e1();
                if (this.q.read(this.c, (long) 8192) == -1) {
                    return -1;
                }
                fromIndex$iv = Math.max(fromIndex$iv, lastBufferSize$iv);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public boolean V(long offset, @NotNull f bytes) {
        k.e(bytes, "bytes");
        return i(offset, bytes, 0, bytes.size());
    }

    public boolean i(long offset, @NotNull f bytes, int bytesOffset, int byteCount) {
        k.e(bytes, "bytes");
        if (!(!this.d)) {
            throw new IllegalStateException("closed".toString());
        } else if (offset < 0 || bytesOffset < 0 || byteCount < 0 || bytes.size() - bytesOffset < byteCount) {
            return false;
        } else {
            for (int i$iv = 0; i$iv < byteCount; i$iv++) {
                long bufferOffset$iv = ((long) i$iv) + offset;
                if (!request(1 + bufferOffset$iv)) {
                    return false;
                }
                if (this.c.n(bufferOffset$iv) != bytes.getByte(bytesOffset + i$iv)) {
                    return false;
                }
            }
            return true;
        }
    }

    @NotNull
    public e peek() {
        return p.d(new u(this));
    }

    /* compiled from: RealBufferedSource.kt */
    public static final class a extends InputStream {
        final /* synthetic */ x c;

        a(x this$0) {
            this.c = this$0;
        }

        public int read() {
            if (this.c.d) {
                throw new IOException("closed");
            } else if (this.c.c.e1() == 0 && this.c.q.read(this.c.c, (long) 8192) == -1) {
                return -1;
            } else {
                return this.c.c.readByte() & 255;
            }
        }

        public int read(@NotNull byte[] data, int offset, int byteCount) {
            k.e(data, "data");
            if (!this.c.d) {
                i0.b((long) data.length, (long) offset, (long) byteCount);
                if (this.c.c.e1() == 0 && this.c.q.read(this.c.c, (long) 8192) == -1) {
                    return -1;
                }
                return this.c.c.read(data, offset, byteCount);
            }
            throw new IOException("closed");
        }

        public int available() {
            if (!this.c.d) {
                return (int) Math.min(this.c.c.e1(), (long) Integer.MAX_VALUE);
            }
            throw new IOException("closed");
        }

        public void close() {
            this.c.close();
        }

        @NotNull
        public String toString() {
            return this.c + ".inputStream()";
        }
    }

    @NotNull
    public InputStream Y0() {
        return new a(this);
    }

    public boolean isOpen() {
        return !this.d;
    }

    @Nullable
    public g cursor() {
        return this.f;
    }

    public void close() {
        if (!this.d) {
            this.d = true;
            this.q.close();
            this.c.clear();
        }
    }

    @NotNull
    public f0 timeout() {
        return this.q.timeout();
    }

    @NotNull
    public String toString() {
        return "buffer(" + this.q + ')';
    }
}
