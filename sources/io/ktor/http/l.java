package io.ktor.http;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpHeaderValueParser.kt */
public final class l {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    @NotNull
    public String toString() {
        return "HeaderValueParam(name=" + this.a + ", value=" + this.b + ")";
    }

    public l(@NotNull String name, @NotNull String value) {
        k.f(name, "name");
        k.f(value, "value");
        this.a = name;
        this.b = value;
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String d() {
        return this.b;
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof l) || !w.y(((l) other).a, this.a, true) || !w.y(((l) other).b, this.b, true)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.a;
        if (str != null) {
            String lowerCase = str.toLowerCase();
            k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
            int result = lowerCase.hashCode();
            int i = result * 31;
            String str2 = this.b;
            if (str2 != null) {
                String lowerCase2 = str2.toLowerCase();
                k.b(lowerCase2, "(this as java.lang.String).toLowerCase()");
                return result + i + lowerCase2.hashCode();
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }
}
