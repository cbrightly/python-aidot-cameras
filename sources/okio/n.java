package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: InflaterSource.kt */
public final class n implements e0 {
    private int c;
    private boolean d;
    private final e f;
    private final Inflater q;

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public n(@NotNull e source, @NotNull Inflater inflater) {
        k.e(source, "source");
        k.e(inflater, "inflater");
        this.f = source;
        this.q = inflater;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public n(@NotNull e0 source, @NotNull Inflater inflater) {
        this(p.d(source), inflater);
        k.e(source, "source");
        k.e(inflater, "inflater");
    }

    public long read(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        do {
            long bytesInflated = a(sink, byteCount);
            if (bytesInflated > 0) {
                return bytesInflated;
            }
            if (this.q.finished() || this.q.needsDictionary()) {
                return -1;
            }
        } while (!this.f.r0());
        throw new EOFException("source exhausted prematurely");
    }

    public final long a(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        } else if (!(!this.d)) {
            throw new IllegalStateException("closed".toString());
        } else if (byteCount == 0) {
            return 0;
        } else {
            try {
                y tail = sink.h1(1);
                int toRead = (int) Math.min(byteCount, (long) (8192 - tail.d));
                c();
                int bytesInflated = this.q.inflate(tail.b, tail.d, toRead);
                g();
                if (bytesInflated > 0) {
                    tail.d += bytesInflated;
                    sink.d1(sink.e1() + ((long) bytesInflated));
                    return (long) bytesInflated;
                }
                if (tail.c == tail.d) {
                    sink.c = tail.b();
                    z.b(tail);
                }
                return 0;
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        }
    }

    public final boolean c() {
        if (!this.q.needsInput()) {
            return false;
        }
        if (this.f.r0()) {
            return true;
        }
        y head = this.f.getBuffer().c;
        k.c(head);
        int i = head.d;
        int i2 = head.c;
        int i3 = i - i2;
        this.c = i3;
        this.q.setInput(head.b, i2, i3);
        return false;
    }

    private final void g() {
        int i = this.c;
        if (i != 0) {
            int toRelease = i - this.q.getRemaining();
            this.c -= toRelease;
            this.f.skip((long) toRelease);
        }
    }

    @NotNull
    public f0 timeout() {
        return this.f.timeout();
    }

    public void close() {
        if (!this.d) {
            this.q.end();
            this.d = true;
            this.f.close();
        }
    }
}
