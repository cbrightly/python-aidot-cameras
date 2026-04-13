package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Set;
import kotlin.collections.o0;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: DescriptorRenderer.kt */
public final class k {
    @NotNull
    private static final Set<b> a = o0.g(new b("kotlin.internal.NoInfer"), new b("kotlin.internal.Exact"));
    public static final k b = new k();

    private k() {
    }

    @NotNull
    public final Set<b> a() {
        return a;
    }
}
