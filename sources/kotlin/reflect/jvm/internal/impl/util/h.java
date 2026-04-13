package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.util.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public final class h implements b {
    @NotNull
    private static final String a = a;
    public static final h b = new h();

    private h() {
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
        w0 it;
        k.f(functionDescriptor, "functionDescriptor");
        List<w0> g = functionDescriptor.g();
        k.b(g, "functionDescriptor.valueParameters");
        if ((g instanceof Collection) && g.isEmpty()) {
            return true;
        }
        for (w0 it2 : g) {
            k.b(it2, "it");
            if (a.b(it2) || it2.r0() != null) {
                it = null;
                continue;
            } else {
                it = 1;
                continue;
            }
            if (it == null) {
                return false;
            }
        }
        return true;
    }
}
