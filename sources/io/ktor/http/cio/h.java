package io.ktor.http.cio;

import io.ktor.http.cio.internals.b;
import java.io.Closeable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestResponse.kt */
public abstract class h implements Closeable {
    @NotNull
    private final f c;
    private final b d;

    public h(@NotNull f headers, @NotNull b builder) {
        k.f(headers, "headers");
        k.f(builder, "builder");
        this.c = headers;
        this.d = builder;
    }

    @NotNull
    public final f a() {
        return this.c;
    }

    public final void release() {
        this.d.o();
        this.c.j();
    }

    public void close() {
        release();
    }
}
