package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.i;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.util.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public final class e implements b {
    @NotNull
    private static final String a = a;
    public static final e b = new e();

    private e() {
    }

    @Nullable
    public String a(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        return b.a.a(this, functionDescriptor);
    }

    @NotNull
    public String getDescription() {
        return a;
    }

    public boolean b(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        w0 secondParameter = functionDescriptor.g().get(1);
        i.b bVar = i.b;
        k.b(secondParameter, "secondParameter");
        b0 a2 = bVar.a(a.m(secondParameter));
        if (a2 == null) {
            return false;
        }
        b0 type = secondParameter.getType();
        k.b(type, "secondParameter.type");
        return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.h(a2, kotlin.reflect.jvm.internal.impl.types.typeUtil.a.k(type));
    }
}
