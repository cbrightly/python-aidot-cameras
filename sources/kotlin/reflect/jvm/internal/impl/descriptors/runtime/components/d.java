package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.j;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.u;
import kotlin.reflect.jvm.internal.impl.load.java.m;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaClassFinder.kt */
public final class d implements m {
    private final ClassLoader a;

    public d(@NotNull ClassLoader classLoader) {
        k.f(classLoader, "classLoader");
        this.a = classLoader;
    }

    @Nullable
    public g a(@NotNull m.a request) {
        String name;
        k.f(request, Progress.REQUEST);
        a classId = request.a();
        b packageFqName = classId.h();
        k.b(packageFqName, "classId.packageFqName");
        String b = classId.i().b();
        k.b(b, "classId.relativeClassName.asString()");
        String relativeClassName = w.G(b, '.', '$', false, 4, (Object) null);
        if (packageFqName.d()) {
            name = relativeClassName;
        } else {
            name = packageFqName.b() + "." + relativeClassName;
        }
        Class klass = e.a(this.a, name);
        if (klass != null) {
            return new j(klass);
        }
        return null;
    }

    @Nullable
    public t b(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return new u(fqName);
    }

    @Nullable
    public Set<String> c(@NotNull b packageFqName) {
        k.f(packageFqName, "packageFqName");
        return null;
    }
}
