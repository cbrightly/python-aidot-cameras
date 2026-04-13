package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.c;
import kotlin.reflect.jvm.internal.impl.types.g;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.y;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: NewKotlinTypeChecker.kt */
public final class p {
    public static final p a = new p();

    private p() {
    }

    public final boolean a(@NotNull g1 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return c.a.a(r.a.d0(false, true), y.c(type), g.b.C0426b.a);
    }
}
