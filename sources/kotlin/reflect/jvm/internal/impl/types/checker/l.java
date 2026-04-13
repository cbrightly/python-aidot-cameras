package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NewCapturedType.kt */
public final class l implements kotlin.reflect.jvm.internal.impl.resolve.calls.inference.b {
    private final g a;
    @NotNull
    private final w0 b;
    /* access modifiers changed from: private */
    public kotlin.jvm.functions.a<? extends List<? extends g1>> c;
    private final l d;
    @Nullable
    private final t0 e;

    private final List<g1> g() {
        return (List) this.a.getValue();
    }

    public l(@NotNull w0 projection, @Nullable kotlin.jvm.functions.a<? extends List<? extends g1>> supertypesComputation, @Nullable l original, @Nullable t0 typeParameter) {
        k.f(projection, "projection");
        this.b = projection;
        this.c = supertypesComputation;
        this.d = original;
        this.e = typeParameter;
        this.a = i.a(kotlin.k.PUBLICATION, new b(this));
    }

    @NotNull
    public w0 getProjection() {
        return this.b;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(w0 w0Var, kotlin.jvm.functions.a aVar, l lVar, t0 t0Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(w0Var, (i & 2) != 0 ? null : aVar, (i & 4) != 0 ? null : lVar, (i & 8) != 0 ? null : t0Var);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public l(@NotNull w0 projection, @NotNull List<? extends g1> supertypes, @Nullable l original) {
        this(projection, new a(supertypes), original, (t0) null, 8, (DefaultConstructorMarker) null);
        k.f(projection, "projection");
        k.f(supertypes, "supertypes");
    }

    /* compiled from: NewCapturedType.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g1>> {
        final /* synthetic */ List $supertypes;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(List list) {
            super(0);
            this.$supertypes = list;
        }

        @NotNull
        public final List<g1> invoke() {
            return this.$supertypes;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(w0 w0Var, List list, l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(w0Var, list, (i & 4) != 0 ? null : lVar);
    }

    /* compiled from: NewCapturedType.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g1>> {
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(l lVar) {
            super(0);
            this.this$0 = lVar;
        }

        @Nullable
        public final List<g1> invoke() {
            kotlin.jvm.functions.a e = this.this$0.c;
            if (e != null) {
                return (List) e.invoke();
            }
            return null;
        }
    }

    public final void h(@NotNull List<? extends g1> supertypes) {
        k.f(supertypes, "supertypes");
        if (this.c == null) {
            this.c = new c(supertypes);
            return;
        }
        throw new AssertionError("Already initialized! oldValue = " + this.c + ", newValue = " + supertypes);
    }

    /* compiled from: NewCapturedType.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g1>> {
        final /* synthetic */ List $supertypes;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(List list) {
            super(0);
            this.$supertypes = list;
        }

        @NotNull
        public final List<g1> invoke() {
            return this.$supertypes;
        }
    }

    @NotNull
    /* renamed from: f */
    public List<g1> b() {
        List<g1> g = g();
        return g != null ? g : q.g();
    }

    @NotNull
    public List<t0> getParameters() {
        return q.g();
    }

    public boolean d() {
        return false;
    }

    @Nullable
    public h c() {
        return null;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.builtins.g j() {
        b0 type = getProjection().getType();
        k.b(type, "projection.type");
        return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(type);
    }

    @NotNull
    /* renamed from: i */
    public l a(@NotNull i kotlinTypeRefiner) {
        d dVar;
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        w0 a2 = getProjection().a(kotlinTypeRefiner);
        k.b(a2, "projection.refine(kotlinTypeRefiner)");
        if (this.c != null) {
            dVar = new d(this, kotlinTypeRefiner);
        } else {
            dVar = null;
        }
        l lVar = this.d;
        if (lVar == null) {
            lVar = this;
        }
        return new l(a2, dVar, lVar, this.e);
    }

    /* compiled from: NewCapturedType.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g1>> {
        final /* synthetic */ i $kotlinTypeRefiner$inlined;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, i iVar) {
            super(0);
            this.this$0 = lVar;
            this.$kotlinTypeRefiner$inlined = iVar;
        }

        @NotNull
        public final List<g1> invoke() {
            Iterable<g1> $this$mapTo$iv$iv = this.this$0.b();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (g1 it : $this$mapTo$iv$iv) {
                arrayList.add(it.N0(this.$kotlinTypeRefiner$inlined));
            }
            return arrayList;
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!k.a(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        if (other != null) {
            l lVar = (l) other;
            l lVar2 = this.d;
            if (lVar2 == null) {
                lVar2 = this;
            }
            l lVar3 = ((l) other).d;
            if (lVar3 == null) {
                lVar3 = (l) other;
            }
            if (lVar2 == lVar3) {
                return true;
            }
            return false;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor");
    }

    public int hashCode() {
        l lVar = this.d;
        return lVar != null ? lVar.hashCode() : super.hashCode();
    }

    @NotNull
    public String toString() {
        return "CapturedType(" + getProjection() + ')';
    }
}
