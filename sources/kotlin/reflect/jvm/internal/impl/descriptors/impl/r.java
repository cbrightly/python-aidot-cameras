package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyPackageViewDescriptorImpl.kt */
public final class r extends j implements e0 {
    static final /* synthetic */ k[] f = {a0.h(new u(a0.b(r.class), "fragments", "getFragments()Ljava/util/List;"))};
    @NotNull
    private final f q;
    @NotNull
    private final h x;
    @NotNull
    private final x y;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.name.b z;

    @NotNull
    public List<b0> b0() {
        return (List) i.a(this.q, this, f[0]);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r(@NotNull x module, @NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull j storageManager) {
        super(g.b.b(), fqName.h());
        kotlin.jvm.internal.k.f(module, "module");
        kotlin.jvm.internal.k.f(fqName, "fqName");
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        this.y = module;
        this.z = fqName;
        this.q = storageManager.c(new a(this));
        this.x = new kotlin.reflect.jvm.internal.impl.resolve.scopes.g(storageManager.c(new b(this)));
    }

    public boolean isEmpty() {
        return e0.a.a(this);
    }

    @NotNull
    /* renamed from: f0 */
    public x w0() {
        return this.y;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.b e() {
        return this.z;
    }

    /* compiled from: LazyPackageViewDescriptorImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends b0>> {
        final /* synthetic */ r this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(r rVar) {
            super(0);
            this.this$0 = rVar;
        }

        @NotNull
        public final List<b0> invoke() {
            return this.this$0.w0().I0().a(this.this$0.e());
        }
    }

    /* compiled from: LazyPackageViewDescriptorImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<h> {
        final /* synthetic */ r this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(r rVar) {
            super(0);
            this.this$0 = rVar;
        }

        @NotNull
        public final h invoke() {
            if (this.this$0.b0().isEmpty()) {
                return h.b.b;
            }
            Iterable<b0> $this$mapTo$iv$iv = this.this$0.b0();
            Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
            for (b0 it : $this$mapTo$iv$iv) {
                destination$iv$iv.add(it.l());
            }
            List scopes = y.o0(destination$iv$iv, new g0(this.this$0.w0(), this.this$0.e()));
            return new kotlin.reflect.jvm.internal.impl.resolve.scopes.b("package view scope for " + this.this$0.e() + " in " + this.this$0.w0().getName(), scopes);
        }
    }

    @NotNull
    public h l() {
        return this.x;
    }

    @Nullable
    /* renamed from: c0 */
    public e0 b() {
        if (e().d()) {
            return null;
        }
        x f0 = w0();
        kotlin.reflect.jvm.internal.impl.name.b e = e().e();
        kotlin.jvm.internal.k.b(e, "fqName.parent()");
        return f0.e0(e);
    }

    public boolean equals(@Nullable Object other) {
        e0 that = (e0) (!(other instanceof e0) ? null : other);
        if (that == null || !kotlin.jvm.internal.k.a(e(), that.e()) || !kotlin.jvm.internal.k.a(w0(), that.w0())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (w0().hashCode() * 31) + e().hashCode();
    }

    public <R, D> R w(@NotNull o<R, D> visitor, D data) {
        kotlin.jvm.internal.k.f(visitor, "visitor");
        return visitor.b(this, data);
    }
}
