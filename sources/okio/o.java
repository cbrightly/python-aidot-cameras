package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmOkio.kt */
public class o implements e0 {
    private final InputStream c;
    private final f0 d;

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public o(@NotNull InputStream input, @NotNull f0 timeout) {
        k.e(input, "input");
        k.e(timeout, "timeout");
        this.c = input;
        this.d = timeout;
    }

    public long read(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        if (byteCount == 0) {
            return 0;
        }
        if (byteCount >= 0) {
            try {
                this.d.f();
                y tail = sink.h1(1);
                int bytesRead = this.c.read(tail.b, tail.d, (int) Math.min(byteCount, (long) (8192 - tail.d)));
                if (bytesRead != -1) {
                    tail.d += bytesRead;
                    sink.d1(sink.e1() + ((long) bytesRead));
                    return (long) bytesRead;
                } else if (tail.c != tail.d) {
                    return -1;
                } else {
                    sink.c = tail.b();
                    z.b(tail);
                    return -1;
                }
            } catch (AssertionError e) {
                if (p.e(e)) {
                    throw new IOException(e);
                }
                throw e;
            }
        } else {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
    }

    public void close() {
        this.c.close();
    }

    @NotNull
    public f0 timeout() {
        return this.d;
    }

    @NotNull
    public String toString() {
        return "source(" + this.c + ')';
    }
}
