package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: utils.kt */
public final class t {
    @NotNull
    private final b0 a;
    @Nullable
    private final t b;

    public t(@NotNull b0 type, @Nullable t previous) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = type;
        this.b = previous;
    }

    @Nullable
    public final t a() {
        return this.b;
    }

    @NotNull
    public final b0 b() {
        return this.a;
    }
}
