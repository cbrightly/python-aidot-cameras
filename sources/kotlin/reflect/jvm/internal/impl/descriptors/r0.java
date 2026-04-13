package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SupertypeLoopChecker.kt */
public interface r0 {
    @NotNull
    Collection<b0> a(@NotNull u0 u0Var, @NotNull Collection<? extends b0> collection, @NotNull l<? super u0, ? extends Iterable<? extends b0>> lVar, @NotNull l<? super b0, x> lVar2);

    /* compiled from: SupertypeLoopChecker.kt */
    public static final class a implements r0 {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        public Collection<b0> a(@NotNull u0 currentTypeConstructor, @NotNull Collection<? extends b0> superTypes, @NotNull l<? super u0, ? extends Iterable<? extends b0>> neighbors, @NotNull l<? super b0, x> reportLoop) {
            k.f(currentTypeConstructor, "currentTypeConstructor");
            k.f(superTypes, "superTypes");
            k.f(neighbors, "neighbors");
            k.f(reportLoop, "reportLoop");
            return superTypes;
        }
    }
}
