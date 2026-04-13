package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.model.f;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: KotlinType.kt */
public abstract class v extends g1 implements p0, f {
    @NotNull
    private final i0 d;
    @NotNull
    private final i0 f;

    @NotNull
    public abstract i0 P0();

    @NotNull
    public abstract String S0(@NotNull c cVar, @NotNull i iVar);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public v(@NotNull i0 lowerBound, @NotNull i0 upperBound) {
        super((DefaultConstructorMarker) null);
        k.f(lowerBound, "lowerBound");
        k.f(upperBound, "upperBound");
        this.d = lowerBound;
        this.f = upperBound;
    }

    @NotNull
    public final i0 Q0() {
        return this.d;
    }

    @NotNull
    public final i0 R0() {
        return this.f;
    }

    @NotNull
    public b0 C0() {
        return this.d;
    }

    @NotNull
    public b0 f0() {
        return this.f;
    }

    public boolean l0(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return false;
    }

    @NotNull
    public g getAnnotations() {
        return P0().getAnnotations();
    }

    @NotNull
    public u0 I0() {
        return P0().I0();
    }

    @NotNull
    public List<w0> H0() {
        return P0().H0();
    }

    public boolean J0() {
        return P0().J0();
    }

    @NotNull
    public h l() {
        return P0().l();
    }

    @NotNull
    public String toString() {
        return c.i.x(this);
    }
}
