package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: signatureEnhancement.kt */
public final class p {
    @NotNull
    private final b0 a;
    @Nullable
    private final d b;

    @NotNull
    public final b0 a() {
        return this.a;
    }

    @Nullable
    public final d b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        return k.a(this.a, pVar.a) && k.a(this.b, pVar.b);
    }

    public int hashCode() {
        b0 b0Var = this.a;
        int i = 0;
        int hashCode = (b0Var != null ? b0Var.hashCode() : 0) * 31;
        d dVar = this.b;
        if (dVar != null) {
            i = dVar.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.a + ", defaultQualifiers=" + this.b + ")";
    }

    public p(@NotNull b0 type, @Nullable d defaultQualifiers) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = type;
        this.b = defaultQualifiers;
    }

    @NotNull
    public final b0 c() {
        return this.a;
    }
}
