package io.ktor.http.cio;

import io.ktor.http.o;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.g0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.ranges.n;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOHeaders.kt */
public final class a implements o {
    private final g d = i.a(k.NONE, new c(this));
    /* access modifiers changed from: private */
    public final f e;

    public a(@NotNull f headers) {
        kotlin.jvm.internal.k.f(headers, "headers");
        this.e = headers;
    }

    public void a(@NotNull p<? super String, ? super List<String>, x> body) {
        kotlin.jvm.internal.k.f(body, "body");
        o.b.a(this, body);
    }

    /* compiled from: CIOHeaders.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<LinkedHashSet<String>> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar) {
            super(0);
            this.this$0 = aVar;
        }

        @NotNull
        public final LinkedHashSet<String> invoke() {
            LinkedHashSet linkedHashSet = new LinkedHashSet(this.this$0.e.g());
            LinkedHashSet $this$apply = linkedHashSet;
            int g = this.this$0.e.g();
            for (int i = 0; i < g; i++) {
                $this$apply.add(this.this$0.e.h(i).toString());
            }
            return linkedHashSet;
        }
    }

    public boolean b() {
        return true;
    }

    @Nullable
    public String get(@NotNull String name) {
        kotlin.jvm.internal.k.f(name, "name");
        CharSequence e2 = this.e.e(name);
        if (e2 != null) {
            return e2.toString();
        }
        return null;
    }

    /* compiled from: CIOHeaders.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<CharSequence, String> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull CharSequence it) {
            kotlin.jvm.internal.k.f(it, "it");
            return it.toString();
        }
    }

    @Nullable
    public List<String> c(@NotNull String name) {
        kotlin.jvm.internal.k.f(name, "name");
        List it = kotlin.sequences.o.C(kotlin.sequences.o.w(this.e.f(name), b.INSTANCE));
        if (!it.isEmpty()) {
            return it;
        }
        return null;
    }

    @NotNull
    public Set<Map.Entry<String, List<String>>> entries() {
        Iterable $this$mapTo$iv$iv = n.l(0, this.e.g());
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        Iterator it = $this$mapTo$iv$iv.iterator();
        while (it.hasNext()) {
            destination$iv$iv.add(new C0248a(((g0) it).nextInt()));
        }
        return y.H0(destination$iv$iv);
    }

    /* renamed from: io.ktor.http.cio.a$a  reason: collision with other inner class name */
    /* compiled from: CIOHeaders.kt */
    public final class C0248a implements Map.Entry<String, List<? extends String>>, kotlin.jvm.internal.markers.a {
        private final int c;

        public /* synthetic */ Object setValue(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public C0248a(int idx) {
            this.c = idx;
        }

        @NotNull
        /* renamed from: a */
        public String getKey() {
            return a.this.e.h(this.c).toString();
        }

        @NotNull
        /* renamed from: b */
        public List<String> getValue() {
            return kotlin.collections.p.b(a.this.e.k(this.c).toString());
        }
    }
}
