package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleDescriptorImpl.kt */
public final class w implements v {
    @NotNull
    private final List<x> a;
    @NotNull
    private final Set<x> b;
    @NotNull
    private final List<x> c;

    public w(@NotNull List<x> allDependencies, @NotNull Set<x> modulesWhoseInternalsAreVisible, @NotNull List<x> expectedByDependencies) {
        k.f(allDependencies, "allDependencies");
        k.f(modulesWhoseInternalsAreVisible, "modulesWhoseInternalsAreVisible");
        k.f(expectedByDependencies, "expectedByDependencies");
        this.a = allDependencies;
        this.b = modulesWhoseInternalsAreVisible;
        this.c = expectedByDependencies;
    }

    @NotNull
    public List<x> a() {
        return this.a;
    }

    @NotNull
    public Set<x> c() {
        return this.b;
    }

    @NotNull
    public List<x> b() {
        return this.c;
    }
}
