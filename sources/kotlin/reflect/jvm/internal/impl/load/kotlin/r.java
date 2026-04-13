package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinJvmBinarySourceElement.kt */
public final class r implements e {
    @NotNull
    private final p b;
    @Nullable
    private final t<f> c;
    private final boolean d;

    public r(@NotNull p binaryClass, @Nullable t<f> incompatibility, boolean isPreReleaseInvisible) {
        k.f(binaryClass, "binaryClass");
        this.b = binaryClass;
        this.c = incompatibility;
        this.d = isPreReleaseInvisible;
    }

    @NotNull
    public final p d() {
        return this.b;
    }

    @NotNull
    public String a() {
        return "Class '" + this.b.d().b().b() + '\'';
    }

    @NotNull
    public p0 b() {
        p0 p0Var = p0.a;
        k.b(p0Var, "SourceFile.NO_SOURCE_FILE");
        return p0Var;
    }

    @NotNull
    public String toString() {
        return getClass().getSimpleName() + ": " + this.b;
    }
}
