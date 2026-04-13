package io.ktor.http.cio;

import kotlinx.coroutines.n0;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pipeline.kt */
public final class l {
    @NotNull
    private static final n0 a = new n0("http-pipeline");
    @NotNull
    private static final n0 b = new n0("http-pipeline-writer");
    @NotNull
    private static final n0 c = new n0("request-handler");

    @NotNull
    public static final n0 a() {
        return a;
    }

    @NotNull
    public static final n0 b() {
        return b;
    }

    @NotNull
    public static final n0 c() {
        return c;
    }
}
