package io.ktor.features;

import io.ktor.http.e0;
import io.ktor.request.d;
import io.ktor.util.a;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: OriginConnectionPoint.kt */
public final class n {
    @NotNull
    private static final a<m> a = new a<>("MutableOriginConnectionPointKey");

    @NotNull
    public static final e0 a(@NotNull d $this$origin) {
        k.f($this$origin, "$this$origin");
        m mVar = (m) $this$origin.d().getAttributes().e(a);
        return mVar != null ? mVar : $this$origin.b();
    }
}
