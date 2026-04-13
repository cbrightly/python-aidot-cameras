package io.ktor.http;

import io.ktor.util.z;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Parameters.kt */
public final class c0 extends z implements y {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c0(@NotNull String name, @NotNull List<String> values) {
        super(true, name, values);
        k.f(name, "name");
        k.f(values, "values");
    }

    @NotNull
    public String toString() {
        return "Parameters " + entries();
    }
}
