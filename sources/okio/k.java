package okio;

import org.jetbrains.annotations.NotNull;

/* compiled from: ForwardingSource.kt */
public abstract class k implements e0 {
    @NotNull
    private final e0 delegate;

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public k(@NotNull e0 delegate2) {
        kotlin.jvm.internal.k.e(delegate2, "delegate");
        this.delegate = delegate2;
    }

    @NotNull
    public final e0 delegate() {
        return this.delegate;
    }

    public long read(@NotNull c sink, long byteCount) {
        kotlin.jvm.internal.k.e(sink, "sink");
        return this.delegate.read(sink, byteCount);
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
    public final e0 m30deprecated_delegate() {
        return this.delegate;
    }
}
