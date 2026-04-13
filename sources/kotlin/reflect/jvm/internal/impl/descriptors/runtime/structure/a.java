package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaMember.kt */
public final class a {
    @Nullable
    private static C0354a a;
    public static final a b = new a();

    /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.a$a  reason: collision with other inner class name */
    /* compiled from: ReflectJavaMember.kt */
    public static final class C0354a {
        @Nullable
        private final Method a;
        @Nullable
        private final Method b;

        public C0354a(@Nullable Method getParameters, @Nullable Method getName) {
            this.a = getParameters;
            this.b = getName;
        }

        @Nullable
        public final Method a() {
            return this.b;
        }

        @Nullable
        public final Method b() {
            return this.a;
        }
    }

    private a() {
    }

    @NotNull
    public final C0354a a(@NotNull Member member) {
        k.f(member, "member");
        Class methodOrConstructorClass = member.getClass();
        try {
            return new C0354a(methodOrConstructorClass.getMethod("getParameters", new Class[0]), b.g(methodOrConstructorClass).loadClass("java.lang.reflect.Parameter").getMethod("getName", new Class[0]));
        } catch (NoSuchMethodException e) {
            return new C0354a((Method) null, (Method) null);
        }
    }

    @Nullable
    public final List<String> b(@NotNull Member member) {
        Method a2;
        Member member2 = member;
        k.f(member2, "member");
        C0354a cache = a;
        if (cache == null) {
            cache = a(member);
            a = cache;
        }
        Method getParameters = cache.b();
        if (getParameters == null || (a2 = cache.a()) == null) {
            return null;
        }
        Method getName = a2;
        Object invoke = getParameters.invoke(member2, new Object[0]);
        if (invoke != null) {
            Object[] $this$map$iv = (Object[]) invoke;
            ArrayList arrayList = new ArrayList($this$map$iv.length);
            Object[] $this$mapTo$iv$iv = $this$map$iv;
            int length = $this$mapTo$iv$iv.length;
            int i = 0;
            while (i < length) {
                Object invoke2 = getName.invoke($this$mapTo$iv$iv[i], new Object[0]);
                if (invoke2 != null) {
                    arrayList.add((String) invoke2);
                    i++;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                }
            }
            return arrayList;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
    }
}
