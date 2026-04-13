package io.ktor.http.content;

import io.ktor.http.c;
import io.ktor.http.content.j;
import io.ktor.util.cio.b;
import io.ktor.utils.io.i;
import io.ktor.utils.io.jvm.javaio.d;
import java.io.InputStream;
import java.net.URI;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: URIFileContent.kt */
public final class m extends j.d {
    @NotNull
    private final URI b;
    @NotNull
    private final c c;

    @NotNull
    public final URI h() {
        return this.b;
    }

    @NotNull
    public c b() {
        return this.c;
    }

    @NotNull
    public i g() {
        InputStream openStream = this.b.toURL().openStream();
        k.b(openStream, "uri.toURL().openStream()");
        return d.b(openStream, (g) null, b.a(), 1, (Object) null);
    }
}
