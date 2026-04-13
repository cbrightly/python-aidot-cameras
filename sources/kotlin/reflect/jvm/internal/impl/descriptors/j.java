package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ConstUtil.kt */
public final class j {
    public static final j a = new j();

    private j() {
    }

    public static final boolean a(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return k.a(type);
    }
}
