package io.ktor.http;

import io.ktor.util.w;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Parameters.kt */
public final class z extends w {
    public z() {
        this(0, 1, (DefaultConstructorMarker) null);
    }

    public z(int size) {
        super(true, size);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ z(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }

    @NotNull
    public y m() {
        if (!h()) {
            j(true);
            return new a0(i());
        }
        throw new IllegalArgumentException("ParametersBuilder can only build a single Parameters instance".toString());
    }
}
