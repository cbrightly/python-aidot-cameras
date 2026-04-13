package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationsImpl.kt */
public final class h implements g {
    private final List<c> c;

    public h(@NotNull List<? extends c> annotations) {
        k.f(annotations, "annotations");
        this.c = annotations;
    }

    public boolean I(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return g.b.b(this, fqName);
    }

    @Nullable
    public c c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return g.b.a(this, fqName);
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @NotNull
    public Iterator<c> iterator() {
        return this.c.iterator();
    }

    @NotNull
    public String toString() {
        return this.c.toString();
    }
}
