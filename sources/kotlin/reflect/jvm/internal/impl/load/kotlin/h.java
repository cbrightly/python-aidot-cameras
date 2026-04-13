package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.g;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.s;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaFlexibleTypeDeserializer.kt */
public final class h implements s {
    public static final h a = new h();

    private h() {
    }

    @NotNull
    public b0 a(@NotNull q proto, @NotNull String flexibleId, @NotNull i0 lowerBound, @NotNull i0 upperBound) {
        k.f(proto, "proto");
        k.f(flexibleId, "flexibleId");
        k.f(lowerBound, "lowerBound");
        k.f(upperBound, "upperBound");
        if (!k.a(flexibleId, "kotlin.jvm.PlatformType")) {
            i0 j = u.j("Error java flexible type with id: " + flexibleId + ". (" + lowerBound + ".." + upperBound + ')');
            k.b(j, "ErrorUtils.createErrorTy…owerBound..$upperBound)\")");
            return j;
        } else if (proto.hasExtension(a.g)) {
            return new g(lowerBound, upperBound);
        } else {
            return c0.d(lowerBound, upperBound);
        }
    }
}
