package io.ktor.http;

import io.ktor.utils.io.charsets.a;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentTypes.kt */
public final class d {
    @NotNull
    public static final c b(@NotNull c $this$withCharset, @NotNull Charset charset) {
        k.f($this$withCharset, "$this$withCharset");
        k.f(charset, "charset");
        return $this$withCharset.i("charset", a.h(charset));
    }

    @Nullable
    public static final Charset a(@NotNull m $this$charset) {
        k.f($this$charset, "$this$charset");
        String it = $this$charset.c("charset");
        if (it != null) {
            return Charset.forName(it);
        }
        return null;
    }
}
