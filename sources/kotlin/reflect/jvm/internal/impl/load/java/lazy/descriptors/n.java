package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.b;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.load.java.components.l;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.e;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d;
import kotlin.reflect.jvm.internal.impl.load.java.structure.j;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LazyJavaTypeParameterDescriptor.kt */
public final class n extends b {
    @NotNull
    private final e a2;
    private final h p2;
    @NotNull
    private final w p3;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(@NotNull h c, @NotNull w javaTypeParameter, int index, @NotNull m containingDeclaration) {
        super(c.e(), containingDeclaration, javaTypeParameter.getName(), h1.INVARIANT, false, index, o0.a, c.a().t());
        k.f(c, "c");
        k.f(javaTypeParameter, "javaTypeParameter");
        k.f(containingDeclaration, "containingDeclaration");
        this.p2 = c;
        this.p3 = javaTypeParameter;
        this.a2 = new e(c, javaTypeParameter);
    }

    @NotNull
    /* renamed from: A0 */
    public e getAnnotations() {
        return this.a2;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<b0> l0() {
        Collection<j> upperBounds = this.p3.getUpperBounds();
        if (upperBounds.isEmpty()) {
            i0 j = this.p2.d().j().j();
            k.b(j, "c.module.builtIns.anyType");
            i0 K = this.p2.d().j().K();
            k.b(K, "c.module.builtIns.nullableAnyType");
            return p.b(c0.d(j, K));
        }
        Iterable<j> $this$mapTo$iv$iv = upperBounds;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (j it : $this$mapTo$iv$iv) {
            arrayList.add(this.p2.g().l(it, d.f(l.COMMON, false, this, 1, (Object) null)));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void f0(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
    }
}
