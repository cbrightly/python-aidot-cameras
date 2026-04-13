package io.ktor.http;

import io.ktor.http.c;
import java.io.File;
import kotlin.io.j;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileContentTypeJvm.kt */
public final class i {
    @NotNull
    public static final c a(@NotNull c.b $this$defaultForFile, @NotNull File file) {
        k.f($this$defaultForFile, "$this$defaultForFile");
        k.f(file, "file");
        return j.d(j.a(c.e, j.d(file)));
    }
}
