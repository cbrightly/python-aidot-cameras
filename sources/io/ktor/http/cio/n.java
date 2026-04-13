package io.ktor.http.cio;

import io.ktor.utils.io.core.f0;
import io.ktor.utils.io.core.g0;
import io.ktor.utils.io.core.q;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestResponseBuilder.kt */
public final class n {
    private final io.ktor.utils.io.core.n a = f0.b(0, 1, (Object) null);

    public final void e(@NotNull CharSequence version, int status, @NotNull CharSequence statusText) {
        k.f(version, ConfigUtil.VERSION_FILE);
        k.f(statusText, "statusText");
        g0.g(this.a, version, 0, 0, (Charset) null, 14, (Object) null);
        this.a.writeByte((byte) 32);
        g0.g(this.a, String.valueOf(status), 0, 0, (Charset) null, 14, (Object) null);
        this.a.writeByte((byte) 32);
        g0.g(this.a, statusText, 0, 0, (Charset) null, 14, (Object) null);
        this.a.writeByte((byte) 13);
        this.a.writeByte((byte) 10);
    }

    public final void c(@NotNull CharSequence name, @NotNull CharSequence value) {
        k.f(name, "name");
        k.f(value, "value");
        this.a.i(name);
        this.a.i(": ");
        this.a.i(value);
        this.a.writeByte((byte) 13);
        this.a.writeByte((byte) 10);
    }

    public final void b() {
        this.a.writeByte((byte) 13);
        this.a.writeByte((byte) 10);
    }

    @NotNull
    public final q a() {
        return this.a.e1();
    }

    public final void d() {
        this.a.release();
    }
}
