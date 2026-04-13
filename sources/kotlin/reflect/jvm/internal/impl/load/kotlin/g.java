package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.h;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaClassDataFinder.kt */
public final class g implements i {
    @NotNull
    private final n a;
    private final e b;

    public g(@NotNull n kotlinClassFinder, @NotNull e deserializedDescriptorResolver) {
        k.f(kotlinClassFinder, "kotlinClassFinder");
        k.f(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        this.a = kotlinClassFinder;
        this.b = deserializedDescriptorResolver;
    }

    @Nullable
    public h a(@NotNull a classId) {
        k.f(classId, "classId");
        p kotlinClass = o.b(this.a, classId);
        if (kotlinClass == null) {
            return null;
        }
        if (k.a(kotlinClass.d(), classId)) {
            return this.b.i(kotlinClass);
        }
        throw new AssertionError("Class with incorrect id found: expected " + classId + ", actual " + kotlinClass.d());
    }
}
