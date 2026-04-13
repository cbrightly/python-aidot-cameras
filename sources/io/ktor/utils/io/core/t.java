package io.ktor.utils.io.core;

import java.lang.reflect.Method;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CloseableJVM.kt */
public final class t {
    private static final g a = i.b(a.INSTANCE);

    private static final Method b() {
        return (Method) a.getValue();
    }

    public static final void a(@NotNull Throwable $this$addSuppressedInternal, @NotNull Throwable other) {
        k.f($this$addSuppressedInternal, "$this$addSuppressedInternal");
        k.f(other, "other");
        Method b = b();
        if (b != null) {
            b.invoke($this$addSuppressedInternal, new Object[]{other});
        }
    }

    /* compiled from: CloseableJVM.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Method> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        @Nullable
        public final Method invoke() {
            try {
                return Throwable.class.getMethod("addSuppressed", new Class[]{Throwable.class});
            } catch (Throwable th) {
                return null;
            }
        }
    }
}
