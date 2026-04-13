package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ModuleClassResolver.kt */
public final class l implements j {
    @NotNull
    public b a;

    @Nullable
    public e a(@NotNull g javaClass) {
        k.f(javaClass, "javaClass");
        b bVar = this.a;
        if (bVar == null) {
            k.t("resolver");
        }
        return bVar.b(javaClass);
    }

    public final void b(@NotNull b bVar) {
        k.f(bVar, "<set-?>");
        this.a = bVar;
    }
}
