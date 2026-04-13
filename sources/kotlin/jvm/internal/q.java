package kotlin.jvm.internal;

import java.util.Collection;
import kotlin.reflect.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PackageReference.kt */
public final class q implements d {
    @NotNull
    private final Class<?> c;
    private final String d;

    public q(@NotNull Class<?> jClass, @NotNull String moduleName) {
        k.e(jClass, "jClass");
        k.e(moduleName, "moduleName");
        this.c = jClass;
        this.d = moduleName;
    }

    @NotNull
    public Class<?> b() {
        return this.c;
    }

    @NotNull
    public Collection<b<?>> g() {
        throw new kotlin.jvm.b();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof q) && k.a(b(), ((q) other).b());
    }

    public int hashCode() {
        return b().hashCode();
    }

    @NotNull
    public String toString() {
        return b().toString() + " (Kotlin reflection is not available)";
    }
}
