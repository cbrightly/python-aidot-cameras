package io.ktor.http.content;

import io.ktor.http.c;
import io.ktor.http.v;
import io.ktor.util.pipeline.d;
import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultTransform.kt */
public final class b {
    @Nullable
    public static final j a(@NotNull d<Object, io.ktor.application.b> $this$transformDefaultContent, @NotNull Object value) {
        k.f($this$transformDefaultContent, "$this$transformDefaultContent");
        k.f(value, "value");
        if (value instanceof j) {
            return (j) value;
        }
        if (value instanceof String) {
            return new l((String) value, io.ktor.response.b.a($this$transformDefaultContent.getContext(), (c) null), (v) null);
        } else if (value instanceof byte[]) {
            return new a((byte[]) value, (c) null, (v) null, 6, (DefaultConstructorMarker) null);
        } else {
            if (value instanceof v) {
                return new c((v) value);
            }
            if (!(value instanceof m) || !k.a(((m) value).h().getScheme(), "file")) {
                return null;
            }
            return new f(new File(((m) value).h()), (c) null, 2, (DefaultConstructorMarker) null);
        }
    }
}
