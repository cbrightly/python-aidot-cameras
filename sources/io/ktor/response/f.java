package io.ktor.response;

import io.ktor.http.UnsafeHeaderException;
import io.ktor.http.s;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ResponseHeaders.kt */
public abstract class f {
    /* access modifiers changed from: protected */
    public abstract void c(@NotNull String str, @NotNull String str2);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract List<String> e(@NotNull String str);

    @Nullable
    public String d(@NotNull String name) {
        k.f(name, "name");
        return (String) y.U(e(name));
    }

    public static /* synthetic */ void b(f fVar, String str, String str2, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                z = true;
            }
            fVar.a(str, str2, z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: append");
    }

    public final void a(@NotNull String name, @NotNull String value, boolean safeOnly) {
        k.f(name, "name");
        k.f(value, "value");
        if (!safeOnly || !s.V0.D(name)) {
            s sVar = s.V0;
            sVar.a(name);
            sVar.b(value);
            c(name, value);
            return;
        }
        throw new UnsafeHeaderException(name);
    }
}
