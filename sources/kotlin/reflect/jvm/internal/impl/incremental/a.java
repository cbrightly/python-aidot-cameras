package kotlin.reflect.jvm.internal.impl.incremental;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: utils.kt */
public final class a {
    public static final void a(@NotNull c $this$record, @NotNull b from, @NotNull e scopeOwner, @NotNull f name) {
        kotlin.reflect.jvm.internal.impl.incremental.components.a location;
        k.f($this$record, "$this$record");
        k.f(from, "from");
        k.f(scopeOwner, "scopeOwner");
        k.f(name, "name");
        if ($this$record != c.a.a && (location = from.getLocation()) != null) {
            kotlin.reflect.jvm.internal.impl.incremental.components.e position = $this$record.a() ? location.getPosition() : kotlin.reflect.jvm.internal.impl.incremental.components.e.Companion.a();
            String a = location.a();
            String b = kotlin.reflect.jvm.internal.impl.resolve.c.m(scopeOwner).b();
            k.b(b, "DescriptorUtils.getFqName(scopeOwner).asString()");
            kotlin.reflect.jvm.internal.impl.incremental.components.f fVar = kotlin.reflect.jvm.internal.impl.incremental.components.f.CLASSIFIER;
            String b2 = name.b();
            k.b(b2, "name.asString()");
            $this$record.b(a, position, b, fVar, b2);
        }
    }

    public static final void b(@NotNull c $this$record, @NotNull b from, @NotNull b0 scopeOwner, @NotNull f name) {
        k.f($this$record, "$this$record");
        k.f(from, "from");
        k.f(scopeOwner, "scopeOwner");
        k.f(name, "name");
        String b = scopeOwner.e().b();
        k.b(b, "scopeOwner.fqName.asString()");
        String b2 = name.b();
        k.b(b2, "name.asString()");
        c($this$record, from, b, b2);
    }

    public static final void c(@NotNull c $this$recordPackageLookup, @NotNull b from, @NotNull String packageFqName, @NotNull String name) {
        kotlin.reflect.jvm.internal.impl.incremental.components.a location;
        k.f($this$recordPackageLookup, "$this$recordPackageLookup");
        k.f(from, "from");
        k.f(packageFqName, "packageFqName");
        k.f(name, "name");
        if ($this$recordPackageLookup != c.a.a && (location = from.getLocation()) != null) {
            $this$recordPackageLookup.b(location.a(), $this$recordPackageLookup.a() ? location.getPosition() : kotlin.reflect.jvm.internal.impl.incremental.components.e.Companion.a(), packageFqName, kotlin.reflect.jvm.internal.impl.incremental.components.f.PACKAGE, name);
        }
    }
}
