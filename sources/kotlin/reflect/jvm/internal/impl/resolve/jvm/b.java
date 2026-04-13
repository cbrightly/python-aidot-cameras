package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.incremental.components.d;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.i;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaDescriptorResolver.kt */
public final class b {
    @NotNull
    private final g a;
    private final kotlin.reflect.jvm.internal.impl.load.java.components.g b;

    public b(@NotNull g packageFragmentProvider, @NotNull kotlin.reflect.jvm.internal.impl.load.java.components.g javaResolverCache) {
        k.f(packageFragmentProvider, "packageFragmentProvider");
        k.f(javaResolverCache, "javaResolverCache");
        this.a = packageFragmentProvider;
        this.b = javaResolverCache;
    }

    @NotNull
    public final g a() {
        return this.a;
    }

    @Nullable
    public final e b(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass) {
        k.f(javaClass, "javaClass");
        kotlin.reflect.jvm.internal.impl.name.b fqName = javaClass.e();
        if (fqName != null && javaClass.E() == a0.SOURCE) {
            return this.b.d(fqName);
        }
        kotlin.reflect.jvm.internal.impl.load.java.structure.g outerClass = javaClass.j();
        m mVar = null;
        if (outerClass != null) {
            e b2 = b(outerClass);
            h outerClassScope = b2 != null ? b2.P() : null;
            m c = outerClassScope != null ? outerClassScope.c(javaClass.getName(), d.FROM_JAVA_LOADER) : null;
            if (c instanceof e) {
                mVar = c;
            }
            return (e) mVar;
        } else if (fqName == null) {
            return null;
        } else {
            g gVar = this.a;
            kotlin.reflect.jvm.internal.impl.name.b e = fqName.e();
            k.b(e, "fqName.parent()");
            i iVar = (i) y.U(gVar.a(e));
            if (iVar != null) {
                return iVar.A0(javaClass);
            }
            return null;
        }
    }
}
