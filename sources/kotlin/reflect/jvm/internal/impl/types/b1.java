package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.c;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.k;
import kotlin.reflect.jvm.internal.impl.types.model.l;
import kotlin.reflect.jvm.internal.impl.types.model.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSystemCommonBackendContext.kt */
public interface b1 extends m {
    @Nullable
    h C(@NotNull k kVar);

    @NotNull
    g D(@NotNull l lVar);

    @Nullable
    c J(@NotNull k kVar);

    @Nullable
    g Q(@NotNull g gVar);

    boolean V(@NotNull g gVar, @NotNull b bVar);

    boolean W(@NotNull g gVar);

    boolean c(@NotNull k kVar);

    @NotNull
    g c0(@NotNull g gVar);

    @Nullable
    l h(@NotNull k kVar);

    boolean p(@NotNull k kVar);

    @Nullable
    h s(@NotNull k kVar);

    /* compiled from: TypeSystemCommonBackendContext.kt */
    public static final class a {
        public static boolean a(b1 $this, @NotNull g $this$isMarkedNullable) {
            kotlin.jvm.internal.k.f($this$isMarkedNullable, "$this$isMarkedNullable");
            return ($this$isMarkedNullable instanceof kotlin.reflect.jvm.internal.impl.types.model.h) && $this.n((kotlin.reflect.jvm.internal.impl.types.model.h) $this$isMarkedNullable);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
            r0 = r2.O(r0, true);
         */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static kotlin.reflect.jvm.internal.impl.types.model.g b(kotlin.reflect.jvm.internal.impl.types.b1 r2, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.types.model.g r3) {
            /*
                java.lang.String r0 = "$this$makeNullable"
                kotlin.jvm.internal.k.f(r3, r0)
                kotlin.reflect.jvm.internal.impl.types.model.h r0 = r2.a(r3)
                if (r0 == 0) goto L_0x0013
                r1 = 1
                kotlin.reflect.jvm.internal.impl.types.model.h r0 = r2.O(r0, r1)
                if (r0 == 0) goto L_0x0013
                goto L_0x0014
            L_0x0013:
                r0 = r3
            L_0x0014:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.b1.a.b(kotlin.reflect.jvm.internal.impl.types.b1, kotlin.reflect.jvm.internal.impl.types.model.g):kotlin.reflect.jvm.internal.impl.types.model.g");
        }
    }
}
