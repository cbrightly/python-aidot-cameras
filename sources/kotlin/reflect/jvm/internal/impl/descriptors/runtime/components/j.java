package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.r;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeErrorReporter.kt */
public final class j implements r {
    public static final j b = new j();

    private j() {
    }

    public void b(@NotNull e descriptor, @NotNull List<String> unresolvedSuperClasses) {
        k.f(descriptor, "descriptor");
        k.f(unresolvedSuperClasses, "unresolvedSuperClasses");
        throw new IllegalStateException("Incomplete hierarchy for class " + descriptor.getName() + ", unresolved classes " + unresolvedSuperClasses);
    }

    public void a(@NotNull b descriptor) {
        k.f(descriptor, "descriptor");
        throw new IllegalStateException("Cannot infer visibility for " + descriptor);
    }
}
