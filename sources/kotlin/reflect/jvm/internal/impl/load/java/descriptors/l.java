package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: util.kt */
public final class l {
    @NotNull
    private final b0 a;
    private final boolean b;

    public l(@NotNull b0 type, boolean hasDefaultValue) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = type;
        this.b = hasDefaultValue;
    }

    public final boolean a() {
        return this.b;
    }

    @NotNull
    public final b0 b() {
        return this.a;
    }
}
