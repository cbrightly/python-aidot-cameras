package okio;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ForwardingTimeout.kt */
public class l extends f0 {
    @NotNull
    private f0 f;

    public l(@NotNull f0 delegate) {
        k.e(delegate, "delegate");
        this.f = delegate;
    }

    @NotNull
    public final f0 j() {
        return this.f;
    }

    @NotNull
    public final l k(@NotNull f0 delegate) {
        k.e(delegate, "delegate");
        this.f = delegate;
        return this;
    }

    @NotNull
    public f0 g(long timeout, @NotNull TimeUnit unit) {
        k.e(unit, "unit");
        return this.f.g(timeout, unit);
    }

    public long h() {
        return this.f.h();
    }

    public boolean e() {
        return this.f.e();
    }

    public long c() {
        return this.f.c();
    }

    @NotNull
    public f0 d(long deadlineNanoTime) {
        return this.f.d(deadlineNanoTime);
    }

    @NotNull
    public f0 b() {
        return this.f.b();
    }

    @NotNull
    public f0 a() {
        return this.f.a();
    }

    public void f() {
        this.f.f();
    }
}
