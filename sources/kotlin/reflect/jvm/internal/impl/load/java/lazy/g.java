package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.List;
import kotlin.collections.q;
import kotlin.j;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.i;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.m;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyJavaPackageFragmentProvider.kt */
public final class g implements c0 {
    /* access modifiers changed from: private */
    public final h a;
    private final kotlin.reflect.jvm.internal.impl.storage.a<b, i> b;

    public g(@NotNull b components) {
        k.f(components, "components");
        h hVar = new h(components, m.a.a, j.c(null));
        this.a = hVar;
        this.b = hVar.e().a();
    }

    private final i c(b fqName) {
        t jPackage = this.a.a().d().b(fqName);
        if (jPackage != null) {
            return this.b.a(fqName, new a(this, jPackage));
        }
        return null;
    }

    /* compiled from: LazyJavaPackageFragmentProvider.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<i> {
        final /* synthetic */ t $jPackage;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar, t tVar) {
            super(0);
            this.this$0 = gVar;
            this.$jPackage = tVar;
        }

        @NotNull
        public final i invoke() {
            return new i(this.this$0.a, this.$jPackage);
        }
    }

    @NotNull
    public List<i> a(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return q.k(c(fqName));
    }

    @NotNull
    /* renamed from: d */
    public List<b> k(@NotNull b fqName, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(fqName, "fqName");
        k.f(nameFilter, "nameFilter");
        i c = c(fqName);
        List<b> H0 = c != null ? c.H0() : null;
        return H0 != null ? H0 : q.g();
    }
}
