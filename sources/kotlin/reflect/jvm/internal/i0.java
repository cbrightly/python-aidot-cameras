package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: moduleByClassLoader.kt */
public final class i0 {
    @NotNull
    private final WeakReference<ClassLoader> a;
    private final int b;
    @Nullable
    private ClassLoader c;

    public i0(@NotNull ClassLoader classLoader) {
        k.f(classLoader, "classLoader");
        this.a = new WeakReference<>(classLoader);
        this.b = System.identityHashCode(classLoader);
        this.c = classLoader;
    }

    public final void a(@Nullable ClassLoader classLoader) {
        this.c = classLoader;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof i0) && ((ClassLoader) this.a.get()) == ((ClassLoader) ((i0) other).a.get());
    }

    public int hashCode() {
        return this.b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r0 = r0.toString();
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r1 = this;
            java.lang.ref.WeakReference<java.lang.ClassLoader> r0 = r1.a
            java.lang.Object r0 = r0.get()
            java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0
            if (r0 == 0) goto L_0x0011
            java.lang.String r0 = r0.toString()
            if (r0 == 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            java.lang.String r0 = "<null>"
        L_0x0013:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.i0.toString():java.lang.String");
    }
}
