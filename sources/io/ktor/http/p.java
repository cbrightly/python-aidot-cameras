package io.ktor.http;

import io.ktor.util.w;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Headers.kt */
public final class p extends w {
    public p(int size) {
        super(true, size);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ p(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }

    @NotNull
    public o m() {
        if (!h()) {
            j(true);
            return new q(i());
        }
        throw new IllegalArgumentException("HeadersBuilder can only build a single Headers instance".toString());
    }

    /* access modifiers changed from: protected */
    public void k(@NotNull String name) {
        k.f(name, "name");
        super.k(name);
        s.V0.a(name);
    }

    /* access modifiers changed from: protected */
    public void l(@NotNull String value) {
        k.f(value, "value");
        super.l(value);
        s.V0.b(value);
    }
}
