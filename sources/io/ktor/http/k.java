package io.ktor.http;

import java.util.Iterator;
import java.util.List;
import kotlin.text.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpHeaderValueParser.kt */
public final class k {
    private final double a;
    @NotNull
    private final String b;
    @NotNull
    private final List<l> c;

    @NotNull
    public final String a() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        return kotlin.jvm.internal.k.a(this.b, kVar.b) && kotlin.jvm.internal.k.a(this.c, kVar.c);
    }

    public int hashCode() {
        String str = this.b;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        List<l> list = this.c;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "HeaderValue(value=" + this.b + ", params=" + this.c + ")";
    }

    public k(@NotNull String value, @NotNull List<l> params) {
        Double d;
        Object element$iv;
        String d2;
        Double l;
        kotlin.jvm.internal.k.f(value, "value");
        kotlin.jvm.internal.k.f(params, "params");
        this.b = value;
        this.c = params;
        Iterator<T> it = params.iterator();
        while (true) {
            d = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (kotlin.jvm.internal.k.a(((l) element$iv).c(), "q")) {
                break;
            }
        }
        l lVar = (l) element$iv;
        double d3 = 1.0d;
        if (!(lVar == null || (d2 = lVar.d()) == null || (l = u.l(d2)) == null)) {
            double it2 = l.doubleValue();
            d = (it2 > 0.0d ? 1 : (it2 == 0.0d ? 0 : -1)) >= 0 && (it2 > 1.0d ? 1 : (it2 == 1.0d ? 0 : -1)) <= 0 ? l : d;
            if (d != null) {
                d3 = d.doubleValue();
            }
        }
        this.a = d3;
    }

    @NotNull
    public final List<l> b() {
        return this.c;
    }

    @NotNull
    public final String d() {
        return this.b;
    }

    public final double c() {
        return this.a;
    }
}
