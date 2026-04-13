package okio;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Okio.kt */
public final class b implements b0 {
    public void write(@NotNull c source, long byteCount) {
        k.e(source, "source");
        source.skip(byteCount);
    }

    public void flush() {
    }

    @NotNull
    public f0 timeout() {
        return f0.a;
    }

    public void close() {
    }
}
