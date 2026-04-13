package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: BuiltInSerializerProtocol.kt */
public final class a extends kotlin.reflect.jvm.internal.impl.serialization.a {
    public static final a n = new a();

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private a() {
        /*
            r14 = this;
            kotlin.reflect.jvm.internal.impl.protobuf.f r1 = kotlin.reflect.jvm.internal.impl.protobuf.f.d()
            r0 = r1
            r2 = 0
            kotlin.reflect.jvm.internal.impl.metadata.builtins.b.a(r0)
            java.lang.String r0 = "ExtensionRegistryLite.ne…f::registerAllExtensions)"
            kotlin.jvm.internal.k.b(r1, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.l, java.lang.Integer> r2 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.a
            java.lang.String r0 = "BuiltInsProtoBuf.packageFqName"
            kotlin.jvm.internal.k.b(r2, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.d, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r3 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.c
            java.lang.String r0 = "BuiltInsProtoBuf.constructorAnnotation"
            kotlin.jvm.internal.k.b(r3, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.c, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r4 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.b
            java.lang.String r0 = "BuiltInsProtoBuf.classAnnotation"
            kotlin.jvm.internal.k.b(r4, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.i, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r5 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.d
            java.lang.String r0 = "BuiltInsProtoBuf.functionAnnotation"
            kotlin.jvm.internal.k.b(r5, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.n, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r6 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.e
            java.lang.String r0 = "BuiltInsProtoBuf.propertyAnnotation"
            kotlin.jvm.internal.k.b(r6, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.n, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r7 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.f
            java.lang.String r0 = "BuiltInsProtoBuf.propertyGetterAnnotation"
            kotlin.jvm.internal.k.b(r7, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.n, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r8 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.g
            java.lang.String r0 = "BuiltInsProtoBuf.propertySetterAnnotation"
            kotlin.jvm.internal.k.b(r8, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.g, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r9 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.i
            java.lang.String r0 = "BuiltInsProtoBuf.enumEntryAnnotation"
            kotlin.jvm.internal.k.b(r9, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.n, kotlin.reflect.jvm.internal.impl.metadata.b$b$c> r10 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.h
            java.lang.String r0 = "BuiltInsProtoBuf.compileTimeValue"
            kotlin.jvm.internal.k.b(r10, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.u, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r11 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.j
            java.lang.String r0 = "BuiltInsProtoBuf.parameterAnnotation"
            kotlin.jvm.internal.k.b(r11, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.q, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r12 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.k
            java.lang.String r0 = "BuiltInsProtoBuf.typeAnnotation"
            kotlin.jvm.internal.k.b(r12, r0)
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.s, java.util.List<kotlin.reflect.jvm.internal.impl.metadata.b>> r13 = kotlin.reflect.jvm.internal.impl.metadata.builtins.b.l
            java.lang.String r0 = "BuiltInsProtoBuf.typeParameterAnnotation"
            kotlin.jvm.internal.k.b(r13, r0)
            r0 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.a.<init>():void");
    }

    @NotNull
    public final String n(@NotNull b fqName) {
        k.f(fqName, "fqName");
        StringBuilder sb = new StringBuilder();
        String b = fqName.b();
        k.b(b, "fqName.asString()");
        sb.append(w.G(b, '.', '/', false, 4, (Object) null));
        sb.append("/");
        sb.append(m(fqName));
        return sb.toString();
    }

    @NotNull
    public final String m(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return o(fqName) + "." + "kotlin_builtins";
    }

    private final String o(b fqName) {
        if (fqName.d()) {
            return "default-package";
        }
        String b = fqName.g().b();
        k.b(b, "fqName.shortName().asString()");
        return b;
    }
}
