package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.d;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotation.kt */
public final class c extends n implements a {
    @NotNull
    private final Annotation a;

    public c(@NotNull Annotation annotation) {
        k.f(annotation, "annotation");
        this.a = annotation;
    }

    public boolean h() {
        return a.C0366a.a(this);
    }

    @NotNull
    public final Annotation k() {
        return this.a;
    }

    @NotNull
    public Collection<b> getArguments() {
        Method[] declaredMethods = kotlin.jvm.a.b(kotlin.jvm.a.a(this.a)).getDeclaredMethods();
        k.b(declaredMethods, "annotation.annotationClass.java.declaredMethods");
        Collection destination$iv$iv = new ArrayList(declaredMethods.length);
        for (Method method : declaredMethods) {
            d.a aVar = d.a;
            Object invoke = method.invoke(this.a, new Object[0]);
            k.b(invoke, "method.invoke(annotation)");
            k.b(method, FirebaseAnalytics.Param.METHOD);
            destination$iv$iv.add(aVar.a(invoke, f.f(method.getName())));
        }
        return destination$iv$iv;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.a d() {
        return b.b(kotlin.jvm.a.b(kotlin.jvm.a.a(this.a)));
    }

    @NotNull
    /* renamed from: r */
    public j resolve() {
        return new j(kotlin.jvm.a.b(kotlin.jvm.a.a(this.a)));
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof c) && k.a(this.a, ((c) other).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + this.a;
    }
}
