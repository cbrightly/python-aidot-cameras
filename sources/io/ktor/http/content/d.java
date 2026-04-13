package io.ktor.http.content;

import io.ktor.util.date.a;
import java.nio.file.attribute.FileTime;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LastModifiedJavaTime.kt */
public final class d {
    @NotNull
    public static final e a(@NotNull FileTime lastModified) {
        k.f(lastModified, "lastModified");
        return new e(a.b(Long.valueOf(lastModified.toMillis())));
    }
}
