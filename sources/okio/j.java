package okio;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ForwardingSink.kt */
public abstract class j implements b0 {
    @NotNull
    private final b0 delegate;

    public j(@NotNull b0 delegate2) {
        k.e(delegate2, "delegate");
        this.delegate = delegate2;
    }

    @NotNull
    public final b0 delegate() {
        return this.delegate;
    }

    public void write(@NotNull c source, long byteCount) {
        k.e(source, "source");
        this.delegate.write(source, byteCount);
    }

    public void flush() {
        this.delegate.flush();
    }

    @NotNull
    public f0 timeout() {
        return this.delegate.timeout();
    }

    public void close() {
        this.delegate.close();
    }

    @NotNull
    public String toString() {
        return getClass().getSimpleName() + '(' + this.delegate + ')';
    }

    @NotNull
    /* renamed from: -deprecated_delegate  reason: not valid java name */
    public final b0 m29deprecated_delegate() {
        return this.delegate;
    }
}
