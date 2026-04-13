package io.ktor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringValues.kt */
public class w {
    @NotNull
    private final Map<String, List<String>> a;
    private boolean b;
    private final boolean c;

    /* compiled from: StringValues.kt */
    public static final class a extends l implements p<String, List<? extends String>, x> {
        final /* synthetic */ w this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(w wVar) {
            super(2);
            this.this$0 = wVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((String) obj, (List<String>) (List) obj2);
            return x.a;
        }

        public final void invoke(@NotNull String name, @NotNull List<String> values) {
            k.f(name, "name");
            k.f(values, "values");
            this.this$0.c(name, values);
        }
    }

    /* compiled from: StringValues.kt */
    public static final class b extends l implements p<String, List<? extends String>, x> {
        final /* synthetic */ w this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(w wVar) {
            super(2);
            this.this$0 = wVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((String) obj, (List<String>) (List) obj2);
            return x.a;
        }

        public final void invoke(@NotNull String name, @NotNull List<String> values) {
            k.f(name, "name");
            k.f(values, "values");
            this.this$0.e(name, values);
        }
    }

    public w(boolean caseInsensitiveName, int size) {
        this.c = caseInsensitiveName;
        this.a = caseInsensitiveName ? j.a() : new LinkedHashMap<>(size);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final Map<String, List<String>> i() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final boolean h() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public final void j(boolean z) {
        this.b = z;
    }

    @Nullable
    public final List<String> g(@NotNull String name) {
        k.f(name, "name");
        return this.a.get(name);
    }

    public final void a(@NotNull String name, @NotNull String value) {
        k.f(name, "name");
        k.f(value, "value");
        l(value);
        f(name, 1).add(value);
    }

    public final void b(@NotNull v stringValues) {
        k.f(stringValues, "stringValues");
        stringValues.a(new a(this));
    }

    public final void d(@NotNull v stringValues) {
        k.f(stringValues, "stringValues");
        stringValues.a(new b(this));
    }

    public final void c(@NotNull String name, @NotNull Iterable<String> values) {
        k.f(name, "name");
        k.f(values, "values");
        Collection collection = (Collection) (!(values instanceof Collection) ? null : values);
        List list = f(name, collection != null ? collection.size() : 2);
        for (String value : values) {
            l(value);
            list.add(value);
        }
    }

    public final void e(@NotNull String name, @NotNull Iterable<String> values) {
        Set existing;
        k.f(name, "name");
        k.f(values, "values");
        List list = this.a.get(name);
        if (list == null || (existing = y.H0(list)) == null) {
            existing = o0.d();
        }
        Collection destination$iv$iv = new ArrayList();
        for (String it : values) {
            if (!existing.contains(it)) {
                destination$iv$iv.add(it);
            }
        }
        c(name, destination$iv$iv);
    }

    /* access modifiers changed from: protected */
    public void k(@NotNull String name) {
        k.f(name, "name");
    }

    /* access modifiers changed from: protected */
    public void l(@NotNull String value) {
        k.f(value, "value");
    }

    private final List<String> f(String name, int size) {
        if (!this.b) {
            List<String> list = this.a.get(name);
            if (list != null) {
                return list;
            }
            ArrayList it = new ArrayList(size);
            k(name);
            this.a.put(name, it);
            return it;
        }
        throw new IllegalStateException("Cannot modify a builder when final structure has already been built");
    }
}
