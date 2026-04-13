package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmPackageScope.kt */
public final class d implements h {
    static final /* synthetic */ k[] b = {a0.h(new u(a0.b(d.class), "kotlinScopes", "getKotlinScopes()Ljava/util/List;"))};
    @NotNull
    private final j c;
    private final f d;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.h e;
    /* access modifiers changed from: private */
    public final i f;

    private final List<h> j() {
        return (List) i.a(this.d, this, b[0]);
    }

    public d(@NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2, @NotNull t jPackage, @NotNull i packageFragment) {
        kotlin.jvm.internal.k.f(c2, "c");
        kotlin.jvm.internal.k.f(jPackage, "jPackage");
        kotlin.jvm.internal.k.f(packageFragment, "packageFragment");
        this.e = c2;
        this.f = packageFragment;
        this.c = new j(c2, jPackage, packageFragment);
        this.d = c2.e().c(new a(this));
    }

    @NotNull
    public final j i() {
        return this.c;
    }

    /* compiled from: JvmPackageScope.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends h>> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final List<h> invoke() {
            Iterable<p> $this$mapNotNullTo$iv$iv = this.this$0.f.C0().values();
            Collection destination$iv$iv = new ArrayList();
            for (p partClass : $this$mapNotNullTo$iv$iv) {
                Object it$iv$iv = this.this$0.e.a().b().c(this.this$0.f, partClass);
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
            }
            return y.C0(destination$iv$iv);
        }
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        k(name, location);
        e javaClassifier = this.c.c(name, location);
        if (javaClassifier != null) {
            return javaClassifier;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.h result$iv = null;
        for (h it : j()) {
            kotlin.reflect.jvm.internal.impl.descriptors.h newResult$iv = it.c(name, location);
            if (newResult$iv != null) {
                if (!(newResult$iv instanceof kotlin.reflect.jvm.internal.impl.descriptors.i) || !((kotlin.reflect.jvm.internal.impl.descriptors.i) newResult$iv).d0()) {
                    return newResult$iv;
                }
                if (result$iv == null) {
                    result$iv = newResult$iv;
                }
            }
        }
        return result$iv;
    }

    @NotNull
    public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        k(name, location);
        h it = this.c;
        List<h> restScopes$iv = j();
        Collection result$iv = it.e(name, location);
        for (h it2 : restScopes$iv) {
            result$iv = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(result$iv, it2.e(name, location));
        }
        return result$iv != null ? result$iv : o0.d();
    }

    @NotNull
    public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        k(name, location);
        h it = this.c;
        List<h> restScopes$iv = j();
        Collection result$iv = it.b(name, location);
        for (h it2 : restScopes$iv) {
            result$iv = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(result$iv, it2.b(name, location));
        }
        return result$iv != null ? result$iv : o0.d();
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, java.lang.Boolean>, kotlin.jvm.functions.l, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.m> d(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d r9, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, java.lang.Boolean> r10) {
        /*
            r8 = this;
            java.lang.String r0 = "kindFilter"
            kotlin.jvm.internal.k.f(r9, r0)
            java.lang.String r0 = "nameFilter"
            kotlin.jvm.internal.k.f(r10, r0)
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.j r0 = r8.c
            java.util.List r1 = r8.j()
            r2 = 0
            r3 = r0
            r4 = 0
            java.util.Collection r3 = r3.d(r9, r10)
            java.util.Iterator r4 = r1.iterator()
        L_0x001b:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0032
            java.lang.Object r5 = r4.next()
            r6 = r5
            kotlin.reflect.jvm.internal.impl.resolve.scopes.h r6 = (kotlin.reflect.jvm.internal.impl.resolve.scopes.h) r6
            r7 = 0
            java.util.Collection r6 = r6.d(r9, r10)
            java.util.Collection r3 = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(r3, r6)
            goto L_0x001b
        L_0x0032:
            if (r3 == 0) goto L_0x0035
            goto L_0x003a
        L_0x0035:
            java.util.Set r4 = kotlin.collections.o0.d()
            r3 = r4
        L_0x003a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.d.d(kotlin.reflect.jvm.internal.impl.resolve.scopes.d, kotlin.jvm.functions.l):java.util.Collection");
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
        Iterable<h> $this$flatMapTo$iv = j();
        Set $this$apply = new LinkedHashSet();
        for (h it : $this$flatMapTo$iv) {
            v.x($this$apply, it.a());
        }
        $this$apply.addAll(this.c.a());
        return $this$apply;
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
        Iterable<h> $this$flatMapTo$iv = j();
        Set $this$apply = new LinkedHashSet();
        for (h it : $this$flatMapTo$iv) {
            v.x($this$apply, it.f());
        }
        $this$apply.addAll(this.c.f());
        return $this$apply;
    }

    public void k(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        kotlin.reflect.jvm.internal.impl.incremental.a.b(this.e.a().j(), location, this.f, name);
    }
}
