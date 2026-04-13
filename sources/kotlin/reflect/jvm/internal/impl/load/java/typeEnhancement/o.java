package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: typeEnhancement.kt */
public final class o extends k {
    @NotNull
    private final i0 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public o(@NotNull i0 type, int subtreeSize, boolean wereChanges) {
        super(type, subtreeSize, wereChanges);
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.d = type;
    }

    @NotNull
    /* renamed from: e */
    public i0 b() {
        return this.d;
    }
}
