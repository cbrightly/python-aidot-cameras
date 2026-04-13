package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StarProjectionImpl.kt */
public final class o0 {
    @NotNull
    public static final b0 a(@NotNull t0 $this$starProjectionType) {
        k.f($this$starProjectionType, "$this$starProjectionType");
        m b = $this$starProjectionType.b();
        if (b != null) {
            u0 i = ((i) b).i();
            k.b(i, "classDescriptor.typeConstructor");
            Iterable<t0> $this$mapTo$iv$iv = i.getParameters();
            k.b($this$mapTo$iv$iv, "classDescriptor.typeConstructor.parameters");
            List typeParameters = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t0 it : $this$mapTo$iv$iv) {
                k.b(it, "it");
                typeParameters.add(it.i());
            }
            TypeSubstitutor g = TypeSubstitutor.g(new a(typeParameters));
            List<b0> upperBounds = $this$starProjectionType.getUpperBounds();
            k.b(upperBounds, "this.upperBounds");
            b0 n = g.n((b0) y.S(upperBounds), h1.OUT_VARIANCE);
            if (n != null) {
                return n;
            }
            i0 y = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h($this$starProjectionType).y();
            k.b(y, "builtIns.defaultBound");
            return y;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters");
    }

    /* compiled from: StarProjectionImpl.kt */
    public static final class a extends v0 {
        final /* synthetic */ List d;

        a(List $captured_local_variable$0) {
            this.d = $captured_local_variable$0;
        }

        @Nullable
        public w0 j(@NotNull u0 key) {
            k.f(key, CacheEntity.KEY);
            if (!this.d.contains(key)) {
                return null;
            }
            h c = key.c();
            if (c != null) {
                return c1.s((t0) c);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        }
    }
}
