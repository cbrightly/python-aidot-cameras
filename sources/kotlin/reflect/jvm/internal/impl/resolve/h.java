package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: OverridingStrategy.kt */
public abstract class h {
    public abstract void a(@NotNull b bVar);

    public abstract void b(@NotNull b bVar, @NotNull b bVar2);

    public abstract void c(@NotNull b bVar, @NotNull b bVar2);

    public void d(@NotNull b member, @NotNull Collection<? extends b> overridden) {
        k.f(member, "member");
        k.f(overridden, "overridden");
        member.y0(overridden);
    }
}
