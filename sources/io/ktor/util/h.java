package io.ktor.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Text.kt */
public final class h {
    private final int a;
    @NotNull
    private final String b;

    public h(@NotNull String content) {
        k.f(content, FirebaseAnalytics.Param.CONTENT);
        this.b = content;
        if (content != null) {
            String lowerCase = content.toLowerCase();
            k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
            this.a = lowerCase.hashCode();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    public boolean equals(@Nullable Object other) {
        String str;
        h hVar = (h) (!(other instanceof h) ? null : other);
        return (hVar == null || (str = hVar.b) == null || !w.y(str, this.b, true)) ? false : true;
    }

    public int hashCode() {
        return this.a;
    }

    @NotNull
    public String toString() {
        return this.b;
    }
}
