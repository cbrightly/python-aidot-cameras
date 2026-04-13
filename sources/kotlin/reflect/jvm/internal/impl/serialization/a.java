package kotlin.reflect.jvm.internal.impl.serialization;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.b;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.g;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: SerializerExtensionProtocol.kt */
public class a {
    @NotNull
    private final f a;
    @NotNull
    private final h.f<l, Integer> b;
    @NotNull
    private final h.f<d, List<b>> c;
    @NotNull
    private final h.f<c, List<b>> d;
    @NotNull
    private final h.f<i, List<b>> e;
    @NotNull
    private final h.f<n, List<b>> f;
    @NotNull
    private final h.f<n, List<b>> g;
    @NotNull
    private final h.f<n, List<b>> h;
    @NotNull
    private final h.f<g, List<b>> i;
    @NotNull
    private final h.f<n, b.C0379b.c> j;
    @NotNull
    private final h.f<u, List<b>> k;
    @NotNull
    private final h.f<q, List<b>> l;
    @NotNull
    private final h.f<s, List<b>> m;

    public a(@NotNull f extensionRegistry, @NotNull h.f<l, Integer> packageFqName, @NotNull h.f<d, List<b>> constructorAnnotation, @NotNull h.f<c, List<b>> classAnnotation, @NotNull h.f<i, List<b>> functionAnnotation, @NotNull h.f<n, List<b>> propertyAnnotation, @NotNull h.f<n, List<b>> propertyGetterAnnotation, @NotNull h.f<n, List<b>> propertySetterAnnotation, @NotNull h.f<g, List<b>> enumEntryAnnotation, @NotNull h.f<n, b.C0379b.c> compileTimeValue, @NotNull h.f<u, List<b>> parameterAnnotation, @NotNull h.f<q, List<b>> typeAnnotation, @NotNull h.f<s, List<b>> typeParameterAnnotation) {
        k.f(extensionRegistry, "extensionRegistry");
        k.f(packageFqName, "packageFqName");
        k.f(constructorAnnotation, "constructorAnnotation");
        k.f(classAnnotation, "classAnnotation");
        k.f(functionAnnotation, "functionAnnotation");
        k.f(propertyAnnotation, "propertyAnnotation");
        k.f(propertyGetterAnnotation, "propertyGetterAnnotation");
        k.f(propertySetterAnnotation, "propertySetterAnnotation");
        k.f(enumEntryAnnotation, "enumEntryAnnotation");
        k.f(compileTimeValue, "compileTimeValue");
        k.f(parameterAnnotation, "parameterAnnotation");
        k.f(typeAnnotation, "typeAnnotation");
        k.f(typeParameterAnnotation, "typeParameterAnnotation");
        this.a = extensionRegistry;
        this.b = packageFqName;
        this.c = constructorAnnotation;
        this.d = classAnnotation;
        this.e = functionAnnotation;
        this.f = propertyAnnotation;
        this.g = propertyGetterAnnotation;
        this.h = propertySetterAnnotation;
        this.i = enumEntryAnnotation;
        this.j = compileTimeValue;
        this.k = parameterAnnotation;
        this.l = typeAnnotation;
        this.m = typeParameterAnnotation;
    }

    @NotNull
    public final f e() {
        return this.a;
    }

    @NotNull
    public final h.f<d, List<b>> c() {
        return this.c;
    }

    @NotNull
    public final h.f<c, List<b>> a() {
        return this.d;
    }

    @NotNull
    public final h.f<i, List<b>> f() {
        return this.e;
    }

    @NotNull
    public final h.f<n, List<b>> h() {
        return this.f;
    }

    @NotNull
    public final h.f<n, List<b>> i() {
        return this.g;
    }

    @NotNull
    public final h.f<n, List<b>> j() {
        return this.h;
    }

    @NotNull
    public final h.f<g, List<b>> d() {
        return this.i;
    }

    @NotNull
    public final h.f<n, b.C0379b.c> b() {
        return this.j;
    }

    @NotNull
    public final h.f<u, List<b>> g() {
        return this.k;
    }

    @NotNull
    public final h.f<q, List<b>> k() {
        return this.l;
    }

    @NotNull
    public final h.f<s, List<b>> l() {
        return this.m;
    }
}
