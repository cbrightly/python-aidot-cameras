package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ReflectJavaValueParameter.kt */
public final class y extends n implements kotlin.reflect.jvm.internal.impl.load.java.structure.y {
    @NotNull
    private final w a;
    private final Annotation[] b;
    private final String c;
    private final boolean d;

    public y(@NotNull w type, @NotNull Annotation[] reflectAnnotations, @Nullable String reflectName, boolean isVararg) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(reflectAnnotations, "reflectAnnotations");
        this.a = type;
        this.b = reflectAnnotations;
        this.c = reflectName;
        this.d = isVararg;
    }

    @NotNull
    /* renamed from: F */
    public w getType() {
        return this.a;
    }

    public boolean z() {
        return this.d;
    }

    @NotNull
    /* renamed from: r */
    public List<c> getAnnotations() {
        return g.b(this.b);
    }

    @Nullable
    /* renamed from: k */
    public c c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return g.a(this.b, fqName);
    }

    public boolean w() {
        return false;
    }

    @Nullable
    public f getName() {
        String p1 = this.c;
        if (p1 != null) {
            return f.e(p1);
        }
        return null;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ");
        sb.append(z() ? "vararg " : "");
        sb.append(getName());
        sb.append(": ");
        sb.append(getType());
        return sb.toString();
    }
}
