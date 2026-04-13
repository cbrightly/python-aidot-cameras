package okio;

import java.io.OutputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmOkio.kt */
public final class t implements b0 {
    private final OutputStream c;
    private final f0 d;

    public t(@NotNull OutputStream out, @NotNull f0 timeout) {
        k.e(out, "out");
        k.e(timeout, "timeout");
        this.c = out;
        this.d = timeout;
    }

    public void write(@NotNull c source, long byteCount) {
        k.e(source, "source");
        i0.b(source.e1(), 0, byteCount);
        long remaining = byteCount;
        while (remaining > 0) {
            this.d.f();
            y head = source.c;
            k.c(head);
            int toCopy = (int) Math.min(remaining, (long) (head.d - head.c));
            this.c.write(head.b, head.c, toCopy);
            head.c += toCopy;
            remaining -= (long) toCopy;
            source.d1(source.e1() - ((long) toCopy));
            if (head.c == head.d) {
                source.c = head.b();
                z.b(head);
            }
        }
    }

    public void flush() {
        this.c.flush();
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
        return "sink(" + this.c + ')';
    }
}
