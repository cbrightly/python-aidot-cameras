package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: typeEnhancement.kt */
public class k {
    @NotNull
    private final b0 a;
    private final int b;
    private final boolean c;

    public k(@NotNull b0 type, int subtreeSize, boolean wereChanges) {
        kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = type;
        this.b = subtreeSize;
        this.c = wereChanges;
    }

    public final int a() {
        return this.b;
    }

    @NotNull
    public b0 b() {
        return this.a;
    }

    public final boolean d() {
        return this.c;
    }

    @Nullable
    public final b0 c() {
        b0 b2 = b();
        b0 b0Var = b2;
        if (this.c) {
            return b2;
        }
        return null;
    }
}
