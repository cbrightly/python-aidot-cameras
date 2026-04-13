package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaPackage.kt */
public final class u extends n implements t {
    @NotNull
    private final b a;

    public u(@NotNull b fqName) {
        k.f(fqName, "fqName");
        this.a = fqName;
    }

    @NotNull
    public b e() {
        return this.a;
    }

    @NotNull
    public Collection<g> y(@NotNull l<? super f, Boolean> nameFilter) {
        k.f(nameFilter, "nameFilter");
        return q.g();
    }

    @NotNull
    public Collection<t> p() {
        return q.g();
    }

    @NotNull
    /* renamed from: k */
    public List<a> getAnnotations() {
        return q.g();
    }

    @Nullable
    public a c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return null;
    }

    public boolean w() {
        return false;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof u) && k.a(e(), ((u) other).e());
    }

    public int hashCode() {
        return e().hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + e();
    }
}
