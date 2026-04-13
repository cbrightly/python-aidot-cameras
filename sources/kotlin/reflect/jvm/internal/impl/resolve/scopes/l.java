package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.q0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.d;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SubstitutingScope.kt */
public final class l implements h {
    private final TypeSubstitutor b;
    private Map<m, m> c;
    private final g d = i.b(new a(this));
    /* access modifiers changed from: private */
    public final h e;

    private final Collection<m> i() {
        return (Collection) this.d.getValue();
    }

    public l(@NotNull h workerScope, @NotNull TypeSubstitutor givenSubstitutor) {
        k.f(workerScope, "workerScope");
        k.f(givenSubstitutor, "givenSubstitutor");
        this.e = workerScope;
        z0 j = givenSubstitutor.j();
        k.b(j, "givenSubstitutor.substitution");
        this.b = d.f(j, false, 1, (Object) null).c();
    }

    /* compiled from: SubstitutingScope.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends m>> {
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(l lVar) {
            super(0);
            this.this$0 = lVar;
        }

        @NotNull
        public final Collection<m> invoke() {
            l lVar = this.this$0;
            return lVar.j(j.a.a(lVar.e, (d) null, (kotlin.jvm.functions.l) null, 3, (Object) null));
        }
    }

    private final <D extends m> D k(D descriptor) {
        Object answer$iv;
        if (this.b.k()) {
            return descriptor;
        }
        if (this.c == null) {
            this.c = new HashMap();
        }
        Map $this$getOrPut$iv = this.c;
        if ($this$getOrPut$iv == null) {
            k.n();
        }
        Object value$iv = $this$getOrPut$iv.get(descriptor);
        if (value$iv != null) {
            answer$iv = value$iv;
        } else if (descriptor instanceof q0) {
            Object $this$sure$iv = ((q0) descriptor).c(this.b);
            if ($this$sure$iv != null) {
                answer$iv = $this$sure$iv;
                $this$getOrPut$iv.put(descriptor, answer$iv);
            } else {
                throw new AssertionError("We expect that no conflict should happen while substitution is guaranteed to generate invariant projection, " + "but " + descriptor + " substitution fails");
            }
        } else {
            throw new IllegalStateException(("Unknown descriptor in scope: " + descriptor).toString());
        }
        return (m) answer$iv;
    }

    /* access modifiers changed from: private */
    public final <D extends m> Collection<D> j(Collection<? extends D> descriptors) {
        if (this.b.k() || descriptors.isEmpty()) {
            return descriptors;
        }
        LinkedHashSet result = kotlin.reflect.jvm.internal.impl.utils.a.g(descriptors.size());
        Iterator<? extends D> it = descriptors.iterator();
        while (it.hasNext()) {
            result.add(k((m) it.next()));
        }
        return result;
    }

    @NotNull
    public Collection<? extends i0> e(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return j(this.e.e(name, location));
    }

    @Nullable
    public h c(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        h it = this.e.c(name, location);
        if (it != null) {
            return (h) k(it);
        }
        return null;
    }

    @NotNull
    public Collection<? extends n0> b(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return j(this.e.b(name, location));
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        return i();
    }

    @NotNull
    public Set<f> a() {
        return this.e.a();
    }

    @NotNull
    public Set<f> f() {
        return this.e.f();
    }
}
