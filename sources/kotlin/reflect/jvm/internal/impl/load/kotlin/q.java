package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinJvmBinaryPackageSourceElement.kt */
public final class q implements o0 {
    private final i b;

    public q(@NotNull i packageFragment) {
        k.f(packageFragment, "packageFragment");
        this.b = packageFragment;
    }

    @NotNull
    public String toString() {
        return this.b + ": " + this.b.C0().keySet();
    }

    @NotNull
    public p0 b() {
        p0 p0Var = p0.a;
        k.b(p0Var, "SourceFile.NO_SOURCE_FILE");
        return p0Var;
    }
}
