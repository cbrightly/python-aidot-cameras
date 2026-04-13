package io.ktor.routing;

import io.ktor.http.b0;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RouteSelector.kt */
public final class c extends j {
    @NotNull
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.b, cVar.b) && k.a(this.c, cVar.c) && k.a(this.d, cVar.d);
    }

    public int hashCode() {
        String str = this.b;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.c;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.d;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode2 + i;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull String name, @Nullable String prefix, @Nullable String suffix) {
        super(0.8d);
        k.f(name, "name");
        this.b = name;
        this.c = prefix;
        this.d = suffix;
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        String prefixChecked;
        String suffixChecked;
        k.f(context, "context");
        if (segmentIndex >= context.b().size()) {
            return k.f.d();
        }
        String part = context.b().get(segmentIndex);
        String str = this.c;
        if (str == null) {
            prefixChecked = part;
        } else if (!w.N(part, str, false, 2, (Object) null)) {
            return k.f.d();
        } else {
            prefixChecked = z.f1(part, this.c.length());
        }
        String str2 = this.d;
        if (str2 == null) {
            suffixChecked = prefixChecked;
        } else if (!w.x(prefixChecked, str2, false, 2, (Object) null)) {
            return k.f.d();
        } else {
            suffixChecked = z.g1(prefixChecked, this.d.length());
        }
        return new k(true, 0.8d, b0.b(this.b, suffixChecked), 1);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = this.c;
        String str2 = "";
        if (str == null) {
            str = str2;
        }
        sb.append(str);
        sb.append('{');
        sb.append(this.b);
        sb.append("?}");
        String str3 = this.d;
        if (str3 != null) {
            str2 = str3;
        }
        sb.append(str2);
        return sb.toString();
    }
}
