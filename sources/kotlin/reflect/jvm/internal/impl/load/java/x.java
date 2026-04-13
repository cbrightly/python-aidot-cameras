package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.incremental.components.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.utils.g;
import kotlin.text.u;
import kotlin.text.v;
import kotlin.text.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: utils.kt */
public final class x {
    @Nullable
    public static final o a(@NotNull b0 $this$lexicalCastFrom, @NotNull String value) {
        Object result;
        k.f($this$lexicalCastFrom, "$this$lexicalCastFrom");
        k.f(value, "value");
        h typeDescriptor = $this$lexicalCastFrom.I0().c();
        if (!(typeDescriptor instanceof e) || ((e) typeDescriptor).h() != f.ENUM_CLASS) {
            b0 type = a.k($this$lexicalCastFrom);
            kotlin.reflect.jvm.internal.impl.utils.f a = g.a(value);
            String number = a.a();
            int radix = a.b();
            try {
                if (kotlin.reflect.jvm.internal.impl.builtins.g.g0(type)) {
                    result = Boolean.valueOf(Boolean.parseBoolean(value));
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.j0(type)) {
                    result = z.i1(value);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.i0(type)) {
                    result = v.n(number, radix);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.E0(type)) {
                    result = v.s(number, radix);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.s0(type)) {
                    result = v.p(number, radix);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.u0(type)) {
                    result = v.r(number, radix);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.q0(type)) {
                    result = u.m(value);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.o0(type)) {
                    result = u.l(value);
                } else if (kotlin.reflect.jvm.internal.impl.builtins.g.G0(type)) {
                    result = value;
                } else {
                    result = null;
                }
            } catch (IllegalArgumentException e) {
                result = null;
            }
            if (result != null) {
                return new f(result);
            }
            return null;
        }
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h P = ((e) typeDescriptor).P();
        kotlin.reflect.jvm.internal.impl.name.f f = kotlin.reflect.jvm.internal.impl.name.f.f(value);
        k.b(f, "Name.identifier(value)");
        h descriptor = P.c(f, d.FROM_BACKEND);
        if (!(descriptor instanceof e) || ((e) descriptor).h() != f.ENUM_ENTRY) {
            return null;
        }
        return new h((e) descriptor);
    }
}
