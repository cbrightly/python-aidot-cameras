package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.io.InputStream;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectKotlinClassFinder.kt */
public final class g implements n {
    private final ClassLoader a;

    public g(@NotNull ClassLoader classLoader) {
        k.f(classLoader, "classLoader");
        this.a = classLoader;
    }

    private final n.a d(String fqName) {
        p p1;
        Class it = e.a(this.a, fqName);
        if (it == null || (p1 = f.a.a(it)) == null) {
            return null;
        }
        return new n.a.b(p1);
    }

    @Nullable
    public n.a c(@NotNull a classId) {
        k.f(classId, "classId");
        return d(h.b(classId));
    }

    @Nullable
    public n.a a(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass) {
        String b;
        k.f(javaClass, "javaClass");
        b e = javaClass.e();
        if (e == null || (b = e.b()) == null) {
            return null;
        }
        return d(b);
    }

    @Nullable
    public InputStream b(@NotNull b packageFqName) {
        k.f(packageFqName, "packageFqName");
        if (!packageFqName.i(kotlin.reflect.jvm.internal.impl.builtins.g.a)) {
            return null;
        }
        return this.a.getResourceAsStream(kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.a.n.n(packageFqName));
    }
}
