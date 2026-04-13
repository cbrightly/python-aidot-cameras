package io.ktor.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringValues.kt */
public class x implements v {
    @NotNull
    private final g d;
    private final boolean e;

    /* access modifiers changed from: protected */
    @NotNull
    public final Map<String, List<String>> d() {
        return (Map) this.d.getValue();
    }

    public x(boolean caseInsensitiveName, @NotNull Map<String, ? extends List<String>> values) {
        k.f(values, "values");
        this.e = caseInsensitiveName;
        this.d = i.b(new a(this, values));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ x(boolean z, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? l0.f() : map);
    }

    public boolean b() {
        return this.e;
    }

    /* compiled from: StringValues.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<String, ? extends List<? extends String>>> {
        final /* synthetic */ Map $values;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(x xVar, Map map) {
            super(0);
            this.this$0 = xVar;
            this.$values = map;
        }

        @NotNull
        public final Map<String, List<String>> invoke() {
            if (!this.this$0.b()) {
                return l0.q(this.$values);
            }
            Map $this$apply = j.a();
            $this$apply.putAll(this.$values);
            return $this$apply;
        }
    }

    @Nullable
    public String get(@NotNull String name) {
        k.f(name, "name");
        List<String> e2 = e(name);
        if (e2 != null) {
            return (String) y.U(e2);
        }
        return null;
    }

    @Nullable
    public List<String> c(@NotNull String name) {
        k.f(name, "name");
        return e(name);
    }

    public boolean isEmpty() {
        return d().isEmpty();
    }

    @NotNull
    public Set<Map.Entry<String, List<String>>> entries() {
        return i.a(d().entrySet());
    }

    public void a(@NotNull p<? super String, ? super List<String>, kotlin.x> body) {
        k.f(body, "body");
        for (Map.Entry next : d().entrySet()) {
            body.invoke((String) next.getKey(), (List) next.getValue());
        }
    }

    private final List<String> e(String name) {
        return d().get(name);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StringValues(case=");
        sb.append(!b());
        sb.append(") ");
        sb.append(entries());
        return sb.toString();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if ((other instanceof v) && b() == ((v) other).b()) {
            return y.c(entries(), ((v) other).entries());
        }
        return false;
    }

    public int hashCode() {
        return y.d(entries(), Boolean.valueOf(b()).hashCode() * 31);
    }
}
