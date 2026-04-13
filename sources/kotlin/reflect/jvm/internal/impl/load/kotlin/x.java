package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.load.kotlin.w;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: methodSignatureMapping.kt */
public final class x implements w<k> {
    public static final x a = new x();

    private x() {
    }

    @Nullable
    public String b(@NotNull e classDescriptor) {
        k.f(classDescriptor, "classDescriptor");
        return w.a.a(this, classDescriptor);
    }

    @Nullable
    public b0 d(@NotNull b0 kotlinType) {
        k.f(kotlinType, "kotlinType");
        return w.a.b(this, kotlinType);
    }

    public boolean e() {
        return w.a.c(this);
    }

    @NotNull
    public b0 g(@NotNull Collection<? extends b0> types) {
        k.f(types, "types");
        throw new AssertionError("There should be no intersection type in existing descriptors, but found: " + y.b0(types, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 63, (Object) null));
    }

    @Nullable
    /* renamed from: h */
    public k a(@NotNull e classDescriptor) {
        k.f(classDescriptor, "classDescriptor");
        return null;
    }

    @Nullable
    public String c(@NotNull e classDescriptor) {
        k.f(classDescriptor, "classDescriptor");
        return null;
    }

    public void f(@NotNull b0 kotlinType, @NotNull e descriptor) {
        k.f(kotlinType, "kotlinType");
        k.f(descriptor, "descriptor");
    }
}
