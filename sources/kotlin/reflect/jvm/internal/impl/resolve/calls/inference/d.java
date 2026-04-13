package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.e0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.m;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.reflect.jvm.internal.impl.types.z;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CapturedTypeConstructor.kt */
public final class d {
    @NotNull
    public static final b0 c(@NotNull w0 typeProjection) {
        k.f(typeProjection, "typeProjection");
        return new a(typeProjection, (b) null, false, (g) null, 14, (DefaultConstructorMarker) null);
    }

    public static final boolean d(@NotNull b0 $this$isCaptured) {
        k.f($this$isCaptured, "$this$isCaptured");
        return $this$isCaptured.I0() instanceof b;
    }

    public static /* synthetic */ z0 f(z0 z0Var, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return e(z0Var, z);
    }

    @NotNull
    public static final z0 e(@NotNull z0 $this$wrapWithCapturingSubstitution, boolean needApproximation) {
        k.f($this$wrapWithCapturingSubstitution, "$this$wrapWithCapturingSubstitution");
        if (!($this$wrapWithCapturingSubstitution instanceof z)) {
            return new b($this$wrapWithCapturingSubstitution, needApproximation, $this$wrapWithCapturingSubstitution);
        }
        t0[] i = ((z) $this$wrapWithCapturingSubstitution).i();
        Iterable<n> $this$mapTo$iv$iv = l.i0(((z) $this$wrapWithCapturingSubstitution).h(), ((z) $this$wrapWithCapturingSubstitution).i());
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (n it : $this$mapTo$iv$iv) {
            arrayList.add(b((w0) it.getFirst(), (t0) it.getSecond()));
        }
        Iterable $this$map$iv = arrayList;
        Object[] array = arrayList.toArray(new w0[0]);
        if (array != null) {
            return new z(i, (w0[]) array, needApproximation);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* compiled from: CapturedTypeConstructor.kt */
    public static final class b extends m {
        final /* synthetic */ z0 d;
        final /* synthetic */ boolean e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(z0 $receiver, boolean $captured_local_variable$1, z0 $super_call_param$2) {
            super($super_call_param$2);
            this.d = $receiver;
            this.e = $captured_local_variable$1;
        }

        public boolean b() {
            return this.e;
        }

        @Nullable
        public w0 e(@NotNull b0 key) {
            k.f(key, CacheEntity.KEY);
            w0 e2 = super.e(key);
            t0 t0Var = null;
            if (e2 == null) {
                return null;
            }
            h c = key.I0().c();
            if (c instanceof t0) {
                t0Var = c;
            }
            return d.b(e2, t0Var);
        }
    }

    /* access modifiers changed from: private */
    public static final w0 b(@NotNull w0 $this$createCapturedIfNeeded, t0 typeParameterDescriptor) {
        if (typeParameterDescriptor == null || $this$createCapturedIfNeeded.c() == h1.INVARIANT) {
            return $this$createCapturedIfNeeded;
        }
        if (typeParameterDescriptor.y() != $this$createCapturedIfNeeded.c()) {
            return new y0(c($this$createCapturedIfNeeded));
        }
        if (!$this$createCapturedIfNeeded.b()) {
            return new y0($this$createCapturedIfNeeded.getType());
        }
        j jVar = kotlin.reflect.jvm.internal.impl.storage.b.b;
        k.b(jVar, "LockBasedStorageManager.NO_LOCKS");
        return new y0(new e0(jVar, new a($this$createCapturedIfNeeded)));
    }

    /* compiled from: CapturedTypeConstructor.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<b0> {
        final /* synthetic */ w0 $this_createCapturedIfNeeded;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(w0 w0Var) {
            super(0);
            this.$this_createCapturedIfNeeded = w0Var;
        }

        @NotNull
        public final b0 invoke() {
            b0 type = this.$this_createCapturedIfNeeded.getType();
            k.b(type, "this@createCapturedIfNeeded.type");
            return type;
        }
    }
}
