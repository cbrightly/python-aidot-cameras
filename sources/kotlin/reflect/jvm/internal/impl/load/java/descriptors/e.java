package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaForKotlinOverridePropertyDescriptor.kt */
public final class e extends g {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e ownerDescriptor, @NotNull n0 getterMethod, @Nullable n0 setterMethod, @NotNull i0 overriddenProperty) {
        super(ownerDescriptor, g.b.b(), getterMethod.p(), getterMethod.getVisibility(), setterMethod != null, overriddenProperty.getName(), getterMethod.n(), (i0) null, b.a.DECLARATION, false, (n<a.C0348a<?>, ?>) null);
        k.f(ownerDescriptor, "ownerDescriptor");
        k.f(getterMethod, "getterMethod");
        k.f(overriddenProperty, "overriddenProperty");
    }
}
