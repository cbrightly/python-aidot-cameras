package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.t;
import kotlin.reflect.jvm.internal.impl.load.java.structure.p;
import kotlin.reflect.jvm.internal.impl.load.java.structure.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaMember.kt */
public abstract class r extends n implements f, t, p {
    @NotNull
    public abstract Member J();

    @NotNull
    public a1 getVisibility() {
        return t.a.a(this);
    }

    public boolean i() {
        return t.a.d(this);
    }

    public boolean isAbstract() {
        return t.a.b(this);
    }

    public boolean isFinal() {
        return t.a.c(this);
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
    public AnnotatedElement n() {
        Member J = J();
        if (J != null) {
            return (AnnotatedElement) J;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.reflect.AnnotatedElement");
    }

    public int B() {
        return J().getModifiers();
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.f getName() {
        kotlin.reflect.jvm.internal.impl.name.f f;
        String it = J().getName();
        if (it != null && (f = kotlin.reflect.jvm.internal.impl.name.f.f(it)) != null) {
            return f;
        }
        kotlin.reflect.jvm.internal.impl.name.f fVar = h.a;
        k.b(fVar, "SpecialNames.NO_NAME_PROVIDED");
        return fVar;
    }

    @NotNull
    /* renamed from: F */
    public j I() {
        Class<?> declaringClass = J().getDeclaringClass();
        k.b(declaringClass, "member.declaringClass");
        return new j(declaringClass);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final List<y> K(@NotNull Type[] parameterTypes, @NotNull Annotation[][] parameterAnnotations, boolean isVararg) {
        String name;
        k.f(parameterTypes, "parameterTypes");
        k.f(parameterAnnotations, "parameterAnnotations");
        ArrayList result = new ArrayList(parameterTypes.length);
        List names = a.b.b(J());
        int shift = names != null ? names.size() - parameterTypes.length : 0;
        int length = parameterTypes.length;
        int i = 0;
        while (i < length) {
            w type = w.a.a(parameterTypes[i]);
            if (names != null) {
                List $this$run = names;
                name = (String) kotlin.collections.y.V($this$run, i + shift);
                if (name == null) {
                    throw new IllegalStateException(("No parameter with index " + i + '+' + shift + " (name=" + getName() + " type=" + type + ") in " + $this$run + "@ReflectJavaMember").toString());
                }
            } else {
                name = null;
            }
            result.add(new y(type, parameterAnnotations[i], name, isVararg && i == l.y(parameterTypes)));
            i++;
        }
        return result;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof r) && k.a(J(), ((r) other).J());
    }

    public int hashCode() {
        return J().hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + J();
    }
}
