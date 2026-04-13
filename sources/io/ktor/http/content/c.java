package io.ktor.http.content;

import io.ktor.http.content.j;
import io.ktor.http.v;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpStatusCodeContent.kt */
public final class c extends j.b {
    private final v b;

    public c(@NotNull v value) {
        k.f(value, "value");
        this.b = value;
    }

    @NotNull
    public v e() {
        return this.b;
    }
}
