package io.ktor.http;

import io.ktor.util.x;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Headers.kt */
public final class q extends x implements o {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull Map<String, ? extends List<String>> values) {
        super(true, values);
        k.f(values, "values");
    }

    @NotNull
    public String toString() {
        return "Headers " + entries();
    }
}
