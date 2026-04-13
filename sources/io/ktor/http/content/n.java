package io.ktor.http.content;

import io.ktor.util.a;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Versions.kt */
public final class n {
    @NotNull
    private static final a<List<?>> a = new a<>("VersionList");

    @NotNull
    public static final List<?> a(@NotNull j $this$versions) {
        k.f($this$versions, "$this$versions");
        List<?> list = (List) $this$versions.d(a);
        return list != null ? list : q.g();
    }

    public static final void b(@NotNull j $this$versions, @NotNull List<?> value) {
        k.f($this$versions, "$this$versions");
        k.f(value, "value");
        $this$versions.f(a, value);
    }
}
