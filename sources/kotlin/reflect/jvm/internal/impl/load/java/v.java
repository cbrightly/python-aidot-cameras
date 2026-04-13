package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.a;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: propertiesConventionUtil.kt */
public final class v {
    @Nullable
    public static final f b(@NotNull f methodName) {
        k.f(methodName, "methodName");
        f e = e(methodName, "get", false, (String) null, 12, (Object) null);
        return e != null ? e : e(methodName, "is", false, (String) null, 8, (Object) null);
    }

    @Nullable
    public static final f c(@NotNull f methodName, boolean withIsPrefix) {
        k.f(methodName, "methodName");
        return e(methodName, "set", false, withIsPrefix ? "is" : null, 4, (Object) null);
    }

    @NotNull
    public static final List<f> f(@NotNull f methodName) {
        k.f(methodName, "methodName");
        return q.l(c(methodName, false), c(methodName, true));
    }

    static /* synthetic */ f e(f fVar, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            str2 = null;
        }
        return d(fVar, str, z, str2);
    }

    private static final f d(f methodName, String prefix, boolean removePrefix, String addPrefix) {
        if (methodName.h()) {
            return null;
        }
        String identifier = methodName.d();
        k.b(identifier, "methodName.identifier");
        if (!w.N(identifier, prefix, false, 2, (Object) null) || identifier.length() == prefix.length()) {
            return null;
        }
        char charAt = identifier.charAt(prefix.length());
        if ('a' <= charAt && 'z' >= charAt) {
            return null;
        }
        if (addPrefix != null) {
            if (removePrefix) {
                return f.f(addPrefix + x.w0(identifier, prefix));
            }
            throw new AssertionError("Assertion failed");
        } else if (!removePrefix) {
            return methodName;
        } else {
            String name = a.c(x.w0(identifier, prefix), true);
            if (!f.j(name)) {
                return null;
            }
            return f.f(name);
        }
    }

    @NotNull
    public static final List<f> a(@NotNull f name) {
        k.f(name, "name");
        String nameAsString = name.b();
        k.b(nameAsString, "name.asString()");
        if (r.e(nameAsString)) {
            return q.k(b(name));
        }
        if (r.h(nameAsString)) {
            return f(name);
        }
        return e.e.b(name);
    }
}
