package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.p;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.m;
import kotlin.reflect.jvm.internal.impl.load.kotlin.e;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.a;
import kotlin.reflect.jvm.internal.impl.load.kotlin.o;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.c;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.b;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackagePartScopeCache.kt */
public final class a {
    private final ConcurrentHashMap<kotlin.reflect.jvm.internal.impl.name.a, h> a = new ConcurrentHashMap<>();
    private final e b;
    private final g c;

    public a(@NotNull e resolver, @NotNull g kotlinClassFinder) {
        k.f(resolver, "resolver");
        k.f(kotlinClassFinder, "kotlinClassFinder");
        this.b = resolver;
        this.c = kotlinClassFinder;
    }

    @NotNull
    public final h a(@NotNull f fileClass) {
        Collection destination$iv$iv;
        f fVar = fileClass;
        k.f(fVar, "fileClass");
        ConcurrentMap $this$getOrPut$iv = this.a;
        Object key$iv = fileClass.d();
        int $i$f$getOrPut = 0;
        Object default$iv = $this$getOrPut$iv.get(key$iv);
        if (default$iv == null) {
            int i = false;
            b fqName = fileClass.d().h();
            k.b(fqName, "fileClass.classId.packageFqName");
            if (fileClass.b().c() == a.C0373a.MULTIFILE_CLASS) {
                Iterable<String> $this$mapNotNullTo$iv$iv = fileClass.b().f();
                destination$iv$iv = new ArrayList();
                for (String partName : $this$mapNotNullTo$iv$iv) {
                    int $i$f$getOrPut2 = $i$f$getOrPut;
                    c d = c.d(partName);
                    int i2 = i;
                    k.b(d, "JvmClassName.byInternalName(partName)");
                    kotlin.reflect.jvm.internal.impl.name.a classId = kotlin.reflect.jvm.internal.impl.name.a.m(d.e());
                    k.b(classId, "ClassId.topLevel(JvmClas…velClassMaybeWithDollars)");
                    Object it$iv$iv = o.b(this.c, classId);
                    if (it$iv$iv != null) {
                        destination$iv$iv.add(it$iv$iv);
                    }
                    $i$f$getOrPut = $i$f$getOrPut2;
                    i = i2;
                }
                int i3 = i;
            } else {
                destination$iv$iv = p.b(fileClass);
            }
            Collection parts = destination$iv$iv;
            m packageFragment = new m(this.b.d().p(), fqName);
            Iterable<kotlin.reflect.jvm.internal.impl.load.kotlin.p> $this$mapNotNull$iv = parts;
            Collection destination$iv$iv2 = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.load.kotlin.p part : $this$mapNotNull$iv) {
                Collection parts2 = parts;
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                Object it$iv$iv2 = this.b.c(packageFragment, part);
                if (it$iv$iv2 != null) {
                    destination$iv$iv2.add(it$iv$iv2);
                }
                parts = parts2;
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
            }
            Iterable iterable = $this$mapNotNull$iv;
            List scopes = y.C0(destination$iv$iv2);
            b.a aVar = kotlin.reflect.jvm.internal.impl.resolve.scopes.b.b;
            default$iv = aVar.a("package " + fqName + " (" + fVar + ')', scopes);
            Object putIfAbsent = $this$getOrPut$iv.putIfAbsent(key$iv, default$iv);
            if (putIfAbsent != null) {
                default$iv = putIfAbsent;
            }
        }
        k.b(default$iv, "cache.getOrPut(fileClass…ileClass)\", scopes)\n    }");
        return (h) default$iv;
    }
}
