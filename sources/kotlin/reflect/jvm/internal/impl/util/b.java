package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public interface b {
    @Nullable
    String a(@NotNull u uVar);

    boolean b(@NotNull u uVar);

    @NotNull
    String getDescription();

    /* compiled from: modifierChecks.kt */
    public static final class a {
        @Nullable
        public static String a(b $this, @NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            if (!$this.b(functionDescriptor)) {
                return $this.getDescription();
            }
            return null;
        }
    }
}
