package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.slf4j.e;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: RawType.kt */
public final class g extends v implements h0 {

    /* compiled from: RawType.kt */
    public static final class a extends l implements p<String, String, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((String) obj, (String) obj2));
        }

        public final boolean invoke(@NotNull String first, @NotNull String second) {
            k.f(first, "first");
            k.f(second, "second");
            return k.a(first, x.w0(second, "out ")) || k.a(second, e.ANY_MARKER);
        }
    }

    private g(i0 lowerBound, i0 upperBound, boolean disableAssertion) {
        super(lowerBound, upperBound);
        if (!disableAssertion && !kotlin.reflect.jvm.internal.impl.types.checker.g.a.d(lowerBound, upperBound)) {
            throw new AssertionError("Lower bound " + lowerBound + " of a flexible type must be a subtype of the upper bound " + upperBound);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public g(@NotNull i0 lowerBound, @NotNull i0 upperBound) {
        this(lowerBound, upperBound, false);
        k.f(lowerBound, "lowerBound");
        k.f(upperBound, "upperBound");
    }

    @NotNull
    public i0 P0() {
        return Q0();
    }

    @NotNull
    public h l() {
        kotlin.reflect.jvm.internal.impl.descriptors.h c2 = I0().c();
        if (!(c2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
            c2 = null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = (kotlin.reflect.jvm.internal.impl.descriptors.e) c2;
        if (classDescriptor != null) {
            h m0 = classDescriptor.m0(f.e);
            k.b(m0, "classDescriptor.getMemberScope(RawSubstitution)");
            return m0;
        }
        throw new IllegalStateException(("Incorrect classifier: " + I0().c()).toString());
    }

    @NotNull
    /* renamed from: V0 */
    public g O0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return new g(Q0().Q0(newAnnotations), R0().Q0(newAnnotations));
    }

    @NotNull
    /* renamed from: T0 */
    public g M0(boolean newNullability) {
        return new g(Q0().P0(newNullability), R0().P0(newNullability));
    }

    @NotNull
    public String S0(@NotNull kotlin.reflect.jvm.internal.impl.renderer.c renderer, @NotNull i options) {
        boolean z;
        String newUpper;
        kotlin.reflect.jvm.internal.impl.renderer.c cVar = renderer;
        k.f(cVar, "renderer");
        k.f(options, "options");
        a $fun$onlyOutDiffers$1 = a.INSTANCE;
        b $fun$renderArguments$2 = new b(cVar);
        c $fun$replaceArgs$3 = c.INSTANCE;
        String lowerRendered = cVar.x(Q0());
        String upperRendered = cVar.x(R0());
        if (options.j()) {
            return "raw (" + lowerRendered + ".." + upperRendered + ')';
        } else if (R0().H0().isEmpty()) {
            return cVar.u(lowerRendered, upperRendered, kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(this));
        } else {
            List<String> invoke = $fun$renderArguments$2.invoke((b0) Q0());
            List upperArgs = $fun$renderArguments$2.invoke((b0) R0());
            String newArgs = y.b0(invoke, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, d.INSTANCE, 30, (Object) null);
            List<n<T, R>> K0 = y.K0(invoke, upperArgs);
            if (!(K0 instanceof Collection) || !K0.isEmpty()) {
                Iterator<T> it = K0.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    n it2 = (n) it.next();
                    a $fun$onlyOutDiffers$12 = $fun$onlyOutDiffers$1;
                    if (!a.INSTANCE.invoke((String) it2.getFirst(), (String) it2.getSecond())) {
                        z = false;
                        break;
                    }
                    i iVar = options;
                    $fun$onlyOutDiffers$1 = $fun$onlyOutDiffers$12;
                }
            } else {
                a aVar = $fun$onlyOutDiffers$1;
                z = true;
            }
            if (z) {
                newUpper = $fun$replaceArgs$3.invoke(upperRendered, newArgs);
            } else {
                newUpper = upperRendered;
            }
            String newLower = $fun$replaceArgs$3.invoke(lowerRendered, newArgs);
            if (k.a(newLower, newUpper)) {
                return newLower;
            }
            return cVar.u(newLower, newUpper, kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(this));
        }
    }

    /* compiled from: RawType.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<b0, List<? extends String>> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.renderer.c $renderer;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.reflect.jvm.internal.impl.renderer.c cVar) {
            super(1);
            this.$renderer = cVar;
        }

        @NotNull
        public final List<String> invoke(@NotNull b0 type) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            Iterable<w0> $this$mapTo$iv$iv = type.H0();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (w0 it : $this$mapTo$iv$iv) {
                arrayList.add(this.$renderer.y(it));
            }
            return arrayList;
        }
    }

    /* compiled from: RawType.kt */
    public static final class c extends l implements p<String, String, String> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        @NotNull
        public final String invoke(@NotNull String $this$replaceArgs, @NotNull String newArgs) {
            k.f($this$replaceArgs, "$this$replaceArgs");
            k.f(newArgs, "newArgs");
            if (!x.R($this$replaceArgs, '<', false, 2, (Object) null)) {
                return $this$replaceArgs;
            }
            return x.Y0($this$replaceArgs, '<', (String) null, 2, (Object) null) + '<' + newArgs + '>' + x.U0($this$replaceArgs, '>', (String) null, 2, (Object) null);
        }
    }

    /* compiled from: RawType.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<String, String> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull String it) {
            k.f(it, "it");
            return "(raw) " + it;
        }
    }

    @NotNull
    /* renamed from: U0 */
    public v N0(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(Q0());
        if (g != null) {
            i0 i0Var = (i0) g;
            b0 g2 = kotlinTypeRefiner.g(R0());
            if (g2 != null) {
                return new g(i0Var, (i0) g2, true);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
