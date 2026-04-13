package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: GzipSource.kt */
public final class m implements e0 {
    private byte c;
    private final x d;
    private final Inflater f;
    private final n q;
    private final CRC32 x = new CRC32();

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public m(@NotNull e0 source) {
        k.e(source, "source");
        x xVar = new x(source);
        this.d = xVar;
        Inflater inflater = new Inflater(true);
        this.f = inflater;
        this.q = new n((e) xVar, inflater);
    }

    public long read(@NotNull c sink, long byteCount) {
        c cVar = sink;
        long j = byteCount;
        k.e(sink, "sink");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        } else if (j == 0) {
            return 0;
        } else {
            if (this.c == 0) {
                c();
                this.c = 1;
            }
            if (this.c == 1) {
                long offset = sink.e1();
                long result = this.q.read(sink, j);
                if (result != -1) {
                    i(sink, offset, result);
                    return result;
                }
                this.c = 2;
            }
            if (this.c == 2) {
                g();
                this.c = 3;
                if (!this.d.r0()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    private final void c() {
        this.d.k0(10);
        int flags = this.d.c.n(3);
        boolean z = true;
        int fhcrc = ((flags >> 1) & 1) == 1 ? 1 : 0;
        if (fhcrc != 0) {
            i(this.d.c, 0, 10);
        }
        a("ID1ID2", 8075, this.d.readShort());
        this.d.skip(8);
        if ((((flags >> 2) & 1) == 1 ? 1 : 0) != 0) {
            this.d.k0(2);
            if (fhcrc != 0) {
                i(this.d.c, 0, 2);
            }
            long xlen = (long) this.d.c.I();
            this.d.k0(xlen);
            if (fhcrc != 0) {
                i(this.d.c, 0, xlen);
            }
            this.d.skip(xlen);
        }
        if ((((flags >> 3) & 1) == 1 ? 1 : 0) != 0) {
            long index = this.d.a((byte) 0);
            if (index != -1) {
                if (fhcrc != 0) {
                    i(this.d.c, 0, index + 1);
                }
                this.d.skip(index + 1);
            } else {
                throw new EOFException();
            }
        }
        if (((flags >> 4) & 1) != 1) {
            z = false;
        }
        if (z) {
            long index2 = this.d.a((byte) 0);
            if (index2 != -1) {
                if (fhcrc != 0) {
                    i(this.d.c, 0, index2 + 1);
                }
                this.d.skip(1 + index2);
            } else {
                throw new EOFException();
            }
        }
        if (fhcrc != 0) {
            a("FHCRC", this.d.l(), (short) ((int) this.x.getValue()));
            this.x.reset();
        }
    }

    private final void g() {
        a("CRC", this.d.j(), (int) this.x.getValue());
        a("ISIZE", this.d.j(), (int) this.f.getBytesWritten());
    }

    @NotNull
    public f0 timeout() {
        return this.d.timeout();
    }

    public void close() {
        this.q.close();
    }

    private final void i(c buffer, long offset, long byteCount) {
        long offset2 = offset;
        long byteCount2 = byteCount;
        y s = buffer.c;
        k.c(s);
        while (true) {
            int i = s.d;
            int i2 = s.c;
            if (offset2 < ((long) (i - i2))) {
                break;
            }
            offset2 -= (long) (i - i2);
            y yVar = s.g;
            k.c(yVar);
            s = yVar;
        }
        while (byteCount2 > 0) {
            int pos = (int) (((long) s.c) + offset2);
            int a$iv = (int) Math.min((long) (s.d - pos), byteCount2);
            this.x.update(s.b, pos, a$iv);
            byteCount2 -= (long) a$iv;
            offset2 = 0;
            y yVar2 = s.g;
            k.c(yVar2);
            s = yVar2;
        }
    }

    private final void a(String name, int expected, int actual) {
        if (actual != expected) {
            String format = String.format("%s: actual 0x%08x != expected 0x%08x", Arrays.copyOf(new Object[]{name, Integer.valueOf(actual), Integer.valueOf(expected)}, 3));
            k.d(format, "java.lang.String.format(this, *args)");
            throw new IOException(format);
        }
    }
}
