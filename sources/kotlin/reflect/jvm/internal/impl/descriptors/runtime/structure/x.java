package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaTypeParameter.kt */
public final class x extends n implements f, w {
    @NotNull
    private final TypeVariable<?> a;

    public x(@NotNull TypeVariable<?> typeVariable) {
        k.f(typeVariable, "typeVariable");
        this.a = typeVariable;
    }

    @Nullable
    /* renamed from: k */
    public c c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return f.a.a(this, fqName);
    }

    @NotNull
    /* renamed from: r */
    public List<c> getAnnotations() {
        return f.a.b(this);
    }

    public boolean w() {
        return f.a.c(this);
    }

    @NotNull
    /* renamed from: F */
    public List<l> getUpperBounds() {
        Type[] bounds = this.a.getBounds();
        k.b(bounds, "typeVariable.bounds");
        List arrayList = new ArrayList(bounds.length);
        for (Type p1 : bounds) {
            arrayList.add(new l(p1));
        }
        List bounds2 = arrayList;
        l lVar = (l) y.s0(bounds2);
        if (k.a(lVar != null ? lVar.J() : null, Object.class)) {
            return q.g();
        }
        return bounds2;
    }

    @Nullable
    public AnnotatedElement n() {
        TypeVariable<?> typeVariable = this.a;
        if (!(typeVariable instanceof AnnotatedElement)) {
            typeVariable = null;
        }
        return (AnnotatedElement) typeVariable;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.f getName() {
        kotlin.reflect.jvm.internal.impl.name.f f = kotlin.reflect.jvm.internal.impl.name.f.f(this.a.getName());
        k.b(f, "Name.identifier(typeVariable.name)");
        return f;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof x) && k.a(this.a, ((x) other).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + this.a;
    }
}
