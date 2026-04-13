package io.ktor.util.pipeline;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PipelinePhase.kt */
public final class g {
    @NotNull
    private final String a;

    public g(@NotNull String name) {
        k.f(name, "name");
        this.a = name;
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public String toString() {
        return "Phase('" + this.a + "')";
    }
}
