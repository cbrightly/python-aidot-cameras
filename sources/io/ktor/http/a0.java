package io.ktor.http;

import io.ktor.util.x;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Parameters.kt */
public final class a0 extends x implements y {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a0(@NotNull Map<String, ? extends List<String>> values) {
        super(true, values);
        k.f(values, "values");
    }

    @NotNull
    public String toString() {
        return "Parameters " + entries();
    }
}
