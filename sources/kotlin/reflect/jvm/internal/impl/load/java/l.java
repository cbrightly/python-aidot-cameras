package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.c;
import kotlin.reflect.jvm.internal.impl.resolve.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FieldOverridabilityCondition.kt */
public final class l implements d {
    @NotNull
    public d.b b(@NotNull a superDescriptor, @NotNull a subDescriptor, @Nullable e subClassDescriptor) {
        k.f(superDescriptor, "superDescriptor");
        k.f(subDescriptor, "subDescriptor");
        if (!(subDescriptor instanceof i0) || !(superDescriptor instanceof i0)) {
            return d.b.UNKNOWN;
        }
        if (!k.a(((i0) subDescriptor).getName(), ((i0) superDescriptor).getName())) {
            return d.b.UNKNOWN;
        }
        if (c.a((i0) subDescriptor) && c.a((i0) superDescriptor)) {
            return d.b.OVERRIDABLE;
        }
        if (c.a((i0) subDescriptor) || c.a((i0) superDescriptor)) {
            return d.b.INCOMPATIBLE;
        }
        return d.b.UNKNOWN;
    }

    @NotNull
    public d.a a() {
        return d.a.BOTH;
    }
}
