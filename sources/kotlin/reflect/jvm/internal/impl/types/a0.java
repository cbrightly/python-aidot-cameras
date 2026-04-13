package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.m;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IntersectionTypeConstructor.kt */
public final class a0 implements u0 {
    private final LinkedHashSet<b0> a;
    private final int b;

    public a0(@NotNull Collection<? extends b0> typesToIntersect) {
        k.f(typesToIntersect, "typesToIntersect");
        if (!typesToIntersect.isEmpty()) {
            LinkedHashSet<b0> linkedHashSet = new LinkedHashSet<>(typesToIntersect);
            this.a = linkedHashSet;
            this.b = linkedHashSet.hashCode();
            return;
        }
        throw new AssertionError("Attempt to create an empty intersection");
    }

    @NotNull
    public List<t0> getParameters() {
        return q.g();
    }

    @NotNull
    public Collection<b0> b() {
        return this.a;
    }

    @NotNull
    public final h e() {
        m.a aVar = m.b;
        return aVar.a("member scope for intersection type " + this, this.a);
    }

    public boolean d() {
        return false;
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.h c() {
        return null;
    }

    @NotNull
    public g j() {
        g j = ((b0) this.a.iterator().next()).I0().j();
        k.b(j, "intersectedTypes.iterato…xt().constructor.builtIns");
        return j;
    }

    @NotNull
    public String toString() {
        return g(this.a);
    }

    private final String g(Iterable<? extends b0> resultingTypes) {
        return y.b0(y.u0(resultingTypes, new b()), " & ", "{", "}", 0, (CharSequence) null, (l) null, 56, (Object) null);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof a0)) {
            return false;
        }
        return k.a(this.a, ((a0) other).a);
    }

    @NotNull
    public final i0 f() {
        return c0.k(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), this, q.g(), false, e(), new a(this));
    }

    /* compiled from: IntersectionTypeConstructor.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<i, i0> {
        final /* synthetic */ a0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(a0 a0Var) {
            super(1);
            this.this$0 = a0Var;
        }

        @NotNull
        public final i0 invoke(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            return this.this$0.a(kotlinTypeRefiner).f();
        }
    }

    public int hashCode() {
        return this.b;
    }

    @NotNull
    /* renamed from: h */
    public a0 a(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        Iterable<b0> $this$mapTo$iv$iv = this.a;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (b0 it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(it.K0(kotlinTypeRefiner));
        }
        return new a0(destination$iv$iv);
    }

    /* compiled from: Comparisons.kt */
    public static final class b<T> implements Comparator<T> {
        public final int compare(T a, T b) {
            return kotlin.comparisons.a.c(((b0) a).toString(), ((b0) b).toString());
        }
    }
}
