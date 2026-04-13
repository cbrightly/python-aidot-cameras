package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: OverridingStrategy.kt */
public abstract class g extends h {
    /* access modifiers changed from: protected */
    public abstract void e(@NotNull b bVar, @NotNull b bVar2);

    public void c(@NotNull b fromSuper, @NotNull b fromCurrent) {
        k.f(fromSuper, "fromSuper");
        k.f(fromCurrent, "fromCurrent");
        e(fromSuper, fromCurrent);
    }

    public void b(@NotNull b first, @NotNull b second) {
        k.f(first, "first");
        k.f(second, "second");
        e(first, second);
    }
}
