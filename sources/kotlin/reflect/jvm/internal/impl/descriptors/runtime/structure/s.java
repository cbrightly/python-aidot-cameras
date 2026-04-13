package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.b;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.load.java.structure.y;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaMethod.kt */
public final class s extends r implements q {
    @NotNull
    private final Method a;

    public s(@NotNull Method member) {
        k.f(member, "member");
        this.a = member;
    }

    public boolean G() {
        return q.a.a(this);
    }

    @NotNull
    /* renamed from: L */
    public Method J() {
        return this.a;
    }

    @NotNull
    public List<y> g() {
        Type[] genericParameterTypes = J().getGenericParameterTypes();
        k.b(genericParameterTypes, "member.genericParameterTypes");
        Annotation[][] parameterAnnotations = J().getParameterAnnotations();
        k.b(parameterAnnotations, "member.parameterAnnotations");
        return K(genericParameterTypes, parameterAnnotations, J().isVarArgs());
    }

    @NotNull
    /* renamed from: M */
    public w getReturnType() {
        w.a aVar = w.a;
        Type genericReturnType = J().getGenericReturnType();
        k.b(genericReturnType, "member.genericReturnType");
        return aVar.a(genericReturnType);
    }

    @Nullable
    public b m() {
        Object it = J().getDefaultValue();
        if (it != null) {
            return d.a.a(it, (f) null);
        }
        return null;
    }

    @NotNull
    public List<x> getTypeParameters() {
        TypeVariable[] typeParameters = J().getTypeParameters();
        k.b(typeParameters, "member.typeParameters");
        ArrayList arrayList = new ArrayList(typeParameters.length);
        for (TypeVariable p1 : typeParameters) {
            arrayList.add(new x(p1));
        }
        return arrayList;
    }
}
