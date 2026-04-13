package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.k0;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.k;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KDeclarationContainerImpl.kt */
public abstract class j implements kotlin.jvm.internal.d {
    private static final Class<?> c = Class.forName("kotlin.jvm.internal.DefaultConstructorMarker");
    /* access modifiers changed from: private */
    @NotNull
    public static final kotlin.text.j d = new kotlin.text.j("<v#(\\d+)>");
    public static final a f = new a((DefaultConstructorMarker) null);

    @NotNull
    public abstract Collection<i0> A(@NotNull kotlin.reflect.jvm.internal.impl.name.f fVar);

    @NotNull
    public abstract Collection<l> v();

    @NotNull
    public abstract Collection<u> w(@NotNull kotlin.reflect.jvm.internal.impl.name.f fVar);

    @Nullable
    public abstract i0 x(int i);

    /* compiled from: KDeclarationContainerImpl.kt */
    public abstract class b {
        static final /* synthetic */ k[] a = {a0.h(new kotlin.jvm.internal.u(a0.b(b.class), "moduleData", "getModuleData()Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData;"))};
        @NotNull
        private final a0.a b = a0.d(new a(this));

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k a() {
            return (kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k) this.b.b(this, a[0]);
        }

        public b() {
        }

        /* compiled from: KDeclarationContainerImpl.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k invoke() {
                return z.a(j.this.b());
            }
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Class<?> z() {
        Class<?> h = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.h(b());
        return h != null ? h : b();
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public static final class g extends kotlin.reflect.jvm.internal.impl.descriptors.impl.l<e<?>, x> {
        final /* synthetic */ j a;

        g(j $outer) {
            this.a = $outer;
        }

        @NotNull
        /* renamed from: r */
        public e<?> c(@NotNull i0 descriptor, @NotNull x data) {
            kotlin.jvm.internal.k.f(descriptor, "descriptor");
            kotlin.jvm.internal.k.f(data, "data");
            return this.a.o(descriptor);
        }

        @NotNull
        /* renamed from: q */
        public e<?> i(@NotNull u descriptor, @NotNull x data) {
            kotlin.jvm.internal.k.f(descriptor, "descriptor");
            kotlin.jvm.internal.k.f(data, "data");
            return new k(this.a, descriptor);
        }

        @NotNull
        /* renamed from: p */
        public e<?> j(@NotNull l descriptor, @NotNull x data) {
            kotlin.jvm.internal.k.f(descriptor, "descriptor");
            kotlin.jvm.internal.k.f(data, "data");
            throw new IllegalStateException("No constructors should appear in this scope: " + descriptor);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final Collection<e<?>> y(@NotNull h scope, @NotNull c belonginess) {
        h hVar = scope;
        c cVar = belonginess;
        kotlin.jvm.internal.k.f(hVar, "scope");
        kotlin.jvm.internal.k.f(cVar, "belonginess");
        g visitor = new g(this);
        Iterable<m> $this$mapNotNullTo$iv$iv = j.a.a(hVar, (kotlin.reflect.jvm.internal.impl.resolve.scopes.d) null, (kotlin.jvm.functions.l) null, 3, (Object) null);
        Collection destination$iv$iv = new ArrayList();
        for (m descriptor : $this$mapNotNullTo$iv$iv) {
            Object it$iv$iv = (!(descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.b) || !(kotlin.jvm.internal.k.a(((kotlin.reflect.jvm.internal.impl.descriptors.b) descriptor).getVisibility(), z0.h) ^ true) || !cVar.accept((kotlin.reflect.jvm.internal.impl.descriptors.b) descriptor)) ? null : (e) descriptor.w(visitor, x.a);
            if (it$iv$iv != null) {
                destination$iv$iv.add(it$iv$iv);
            }
            h hVar2 = scope;
        }
        return y.C0(destination$iv$iv);
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public enum c {
        DECLARED,
        INHERITED;

        public final boolean accept(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b member) {
            kotlin.jvm.internal.k.f(member, "member");
            b.a h = member.h();
            kotlin.jvm.internal.k.b(h, "member.kind");
            return h.isReal() == (this == DECLARED);
        }
    }

    /* access modifiers changed from: private */
    public final t<?> o(i0 descriptor) {
        int i = 1;
        int i2 = descriptor.I() != null ? 1 : 0;
        l0 L = descriptor.L();
        if (L != null) {
            l0 l0Var = L;
        } else {
            i = 0;
        }
        int receiverCount = i2 + i;
        if (descriptor.K()) {
            switch (receiverCount) {
                case 0:
                    return new l(this, descriptor);
                case 1:
                    return new m(this, descriptor);
                case 2:
                    return new n(this, descriptor);
            }
        } else {
            switch (receiverCount) {
                case 0:
                    return new q(this, descriptor);
                case 1:
                    return new r(this, descriptor);
                case 2:
                    return new s(this, descriptor);
            }
        }
        throw new y("Unsupported property: " + descriptor);
    }

    @NotNull
    public final i0 u(@NotNull String name, @NotNull String signature) {
        String str;
        int $i$f$groupBy;
        String str2 = name;
        String str3 = signature;
        kotlin.jvm.internal.k.f(str2, "name");
        kotlin.jvm.internal.k.f(str3, "signature");
        kotlin.text.h match = d.matchEntire(str3);
        if (match != null) {
            String number = match.a().a().b().get(1);
            i0 x = x(Integer.parseInt(number));
            if (x != null) {
                return x;
            }
            throw new y("Local property #" + number + " not found in " + b());
        }
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f(name);
        kotlin.jvm.internal.k.b(f2, "Name.identifier(name)");
        Iterable $this$filterTo$iv$iv = A(f2);
        ArrayList arrayList = new ArrayList();
        for (T next : $this$filterTo$iv$iv) {
            if (kotlin.jvm.internal.k.a(e0.b.f((i0) next).a(), str3)) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            throw new y("Property '" + str2 + "' (JVM signature: " + str3 + ") not resolved in " + this);
        } else if (arrayList2.size() != 1) {
            Iterable $this$groupByTo$iv$iv = arrayList2;
            int $i$f$groupBy2 = false;
            Map $this$getOrPut$iv$iv$iv = new LinkedHashMap();
            for (Object element$iv$iv : $this$groupByTo$iv$iv) {
                kotlin.text.h match2 = match;
                Object key$iv$iv = ((i0) element$iv$iv).getVisibility();
                ArrayList arrayList3 = $this$groupByTo$iv$iv;
                Map $this$getOrPut$iv$iv$iv2 = $this$getOrPut$iv$iv$iv;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv2.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    $i$f$groupBy = $i$f$groupBy2;
                    Object answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv2.put(key$iv$iv, answer$iv$iv$iv);
                    value$iv$iv$iv = answer$iv$iv$iv;
                } else {
                    $i$f$groupBy = $i$f$groupBy2;
                }
                ((List) value$iv$iv$iv).add(element$iv$iv);
                match = match2;
                $this$groupByTo$iv$iv = arrayList3;
                $i$f$groupBy2 = $i$f$groupBy;
            }
            ArrayList arrayList4 = $this$groupByTo$iv$iv;
            int i = $i$f$groupBy2;
            Collection values = k0.e($this$getOrPut$iv$iv$iv, f.c).values();
            kotlin.jvm.internal.k.b(values, "properties\n             …                }).values");
            List mostVisibleProperties = (List) y.c0(values);
            boolean z = true;
            if (mostVisibleProperties.size() == 1) {
                kotlin.jvm.internal.k.b(mostVisibleProperties, "mostVisibleProperties");
                return (i0) y.S(mostVisibleProperties);
            }
            kotlin.reflect.jvm.internal.impl.name.f f3 = kotlin.reflect.jvm.internal.impl.name.f.f(name);
            kotlin.jvm.internal.k.b(f3, "Name.identifier(name)");
            String allMembers = y.b0(A(f3), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, e.INSTANCE, 30, (Object) null);
            StringBuilder sb = new StringBuilder();
            sb.append("Property '");
            sb.append(str2);
            sb.append("' (JVM signature: ");
            sb.append(str3);
            sb.append(") not resolved in ");
            sb.append(this);
            sb.append(':');
            if (allMembers.length() != 0) {
                z = false;
            }
            if (z) {
                str = " no members found";
            } else {
                str = 10 + allMembers;
            }
            sb.append(str);
            throw new y(sb.toString());
        } else {
            return (i0) y.q0(arrayList2);
        }
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public static final class f<T> implements Comparator<a1> {
        public static final f c = new f();

        f() {
        }

        /* renamed from: a */
        public final int compare(a1 first, a1 second) {
            Integer d = z0.d(first, second);
            if (d != null) {
                return d.intValue();
            }
            return 0;
        }
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i0, String> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull i0 descriptor) {
            kotlin.jvm.internal.k.f(descriptor, "descriptor");
            return kotlin.reflect.jvm.internal.impl.renderer.c.i.r(descriptor) + " | " + e0.b.f(descriptor).a();
        }
    }

    @NotNull
    public final u s(@NotNull String name, @NotNull String signature) {
        Iterable iterable;
        String str;
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(signature, "signature");
        if (kotlin.jvm.internal.k.a(name, "<init>")) {
            iterable = y.C0(v());
        } else {
            kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f(name);
            kotlin.jvm.internal.k.b(f2, "Name.identifier(name)");
            iterable = w(f2);
        }
        Iterable members = iterable;
        List arrayList = new ArrayList();
        for (Object element$iv$iv : members) {
            if (kotlin.jvm.internal.k.a(e0.b.g((u) element$iv$iv).a(), signature)) {
                arrayList.add(element$iv$iv);
            }
        }
        List functions = arrayList;
        boolean z = true;
        if (functions.size() == 1) {
            return (u) y.q0(functions);
        }
        String allMembers = y.b0(members, "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, d.INSTANCE, 30, (Object) null);
        StringBuilder sb = new StringBuilder();
        sb.append("Function '");
        sb.append(name);
        sb.append("' (JVM signature: ");
        sb.append(signature);
        sb.append(") not resolved in ");
        sb.append(this);
        sb.append(':');
        if (allMembers.length() != 0) {
            z = false;
        }
        if (z) {
            str = " no members found";
        } else {
            str = 10 + allMembers;
        }
        sb.append(str);
        throw new y(sb.toString());
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<u, String> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull u descriptor) {
            kotlin.jvm.internal.k.f(descriptor, "descriptor");
            return kotlin.reflect.jvm.internal.impl.renderer.c.i.r(descriptor) + " | " + e0.b.g(descriptor).a();
        }
    }

    private final Method D(@NotNull Class<?> $this$lookupMethod, String name, Class<?>[] parameterTypes, Class<?> returnType, boolean isStaticDefault) {
        Method it;
        Class<?>[] clsArr = parameterTypes;
        if (isStaticDefault) {
            clsArr[0] = $this$lookupMethod;
        }
        Method it2 = G($this$lookupMethod, name, parameterTypes, returnType);
        if (it2 != null) {
            return it2;
        }
        Class<? super Object> superclass = $this$lookupMethod.getSuperclass();
        if (superclass != null && (it = D(superclass, name, parameterTypes, returnType, isStaticDefault)) != null) {
            return it;
        }
        for (Class<?> cls : $this$lookupMethod.getInterfaces()) {
            kotlin.jvm.internal.k.b(cls, "superInterface");
            Method it3 = D(cls, name, parameterTypes, returnType, isStaticDefault);
            if (it3 != null) {
                return it3;
            }
            if (isStaticDefault) {
                ClassLoader classLoader = cls.getClassLoader();
                kotlin.jvm.internal.k.b(classLoader, "superInterface.classLoader");
                Class defaultImpls = kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.e.a(classLoader, cls.getName() + "$DefaultImpls");
                if (defaultImpls != null) {
                    clsArr[0] = cls;
                    String str = name;
                    Method it4 = G(defaultImpls, name, parameterTypes, returnType);
                    if (it4 != null) {
                        return it4;
                    }
                } else {
                    String str2 = name;
                    Class<?> cls2 = returnType;
                }
            } else {
                String str3 = name;
                Class<?> cls3 = returnType;
            }
        }
        String str4 = name;
        Class<?> cls4 = returnType;
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0063 A[LOOP:0: B:6:0x002c->B:20:0x0063, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0061 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.reflect.Method G(@org.jetbrains.annotations.NotNull java.lang.Class<?> r12, java.lang.String r13, java.lang.Class<?>[] r14, java.lang.Class<?> r15) {
        /*
            r11 = this;
            r0 = 0
            int r1 = r14.length     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r14, r1)     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.Class[] r1 = (java.lang.Class[]) r1     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.reflect.Method r1 = r12.getDeclaredMethod(r13, r1)     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.String r2 = "result"
            kotlin.jvm.internal.k.b(r1, r2)     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.Class r2 = r1.getReturnType()     // Catch:{ NoSuchMethodException -> 0x0068 }
            boolean r2 = kotlin.jvm.internal.k.a(r2, r15)     // Catch:{ NoSuchMethodException -> 0x0068 }
            if (r2 == 0) goto L_0x001f
            r0 = r1
            goto L_0x0067
        L_0x001f:
            java.lang.reflect.Method[] r2 = r12.getDeclaredMethods()     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.String r3 = "declaredMethods"
            kotlin.jvm.internal.k.b(r2, r3)     // Catch:{ NoSuchMethodException -> 0x0068 }
            r3 = 0
            int r4 = r2.length     // Catch:{ NoSuchMethodException -> 0x0068 }
            r5 = 0
            r6 = r5
        L_0x002c:
            if (r6 >= r4) goto L_0x0066
            r7 = r2[r6]     // Catch:{ NoSuchMethodException -> 0x0068 }
            r8 = r7
            r9 = 0
            java.lang.String r10 = "method"
            kotlin.jvm.internal.k.b(r8, r10)     // Catch:{ NoSuchMethodException -> 0x0068 }
            java.lang.String r10 = r8.getName()     // Catch:{ NoSuchMethodException -> 0x0068 }
            boolean r10 = kotlin.jvm.internal.k.a(r10, r13)     // Catch:{ NoSuchMethodException -> 0x0068 }
            if (r10 == 0) goto L_0x005e
            java.lang.Class r10 = r8.getReturnType()     // Catch:{ NoSuchMethodException -> 0x0068 }
            boolean r10 = kotlin.jvm.internal.k.a(r10, r15)     // Catch:{ NoSuchMethodException -> 0x0068 }
            if (r10 == 0) goto L_0x005e
            java.lang.Class[] r10 = r8.getParameterTypes()     // Catch:{ NoSuchMethodException -> 0x0068 }
            if (r10 != 0) goto L_0x0056
            kotlin.jvm.internal.k.n()     // Catch:{ NoSuchMethodException -> 0x0068 }
        L_0x0056:
            boolean r10 = java.util.Arrays.equals(r10, r14)     // Catch:{ NoSuchMethodException -> 0x0068 }
            if (r10 == 0) goto L_0x005e
            r10 = 1
            goto L_0x005f
        L_0x005e:
            r10 = r5
        L_0x005f:
            if (r10 == 0) goto L_0x0063
            r0 = r7
            goto L_0x0067
        L_0x0063:
            int r6 = r6 + 1
            goto L_0x002c
        L_0x0066:
        L_0x0067:
            goto L_0x006a
        L_0x0068:
            r1 = move-exception
        L_0x006a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.j.G(java.lang.Class, java.lang.String, java.lang.Class[], java.lang.Class):java.lang.reflect.Method");
    }

    private final Constructor<?> F(@NotNull Class<?> $this$tryGetConstructor, List<? extends Class<?>> parameterTypes) {
        try {
            Object[] array = parameterTypes.toArray(new Class[0]);
            if (array != null) {
                Class[] clsArr = (Class[]) array;
                return $this$tryGetConstructor.getDeclaredConstructor((Class[]) Arrays.copyOf(clsArr, clsArr.length));
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } catch (NoSuchMethodException e2) {
            return null;
        }
    }

    @Nullable
    public final Method t(@NotNull String name, @NotNull String desc) {
        Method it;
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(desc, "desc");
        if (kotlin.jvm.internal.k.a(name, "<init>")) {
            return null;
        }
        Object[] array = B(desc).toArray(new Class[0]);
        if (array != null) {
            Class[] parameterTypes = (Class[]) array;
            Class<?> C = C(desc);
            Method it2 = D(z(), name, parameterTypes, C, false);
            if (it2 != null) {
                return it2;
            }
            if (!z().isInterface() || (it = D(Object.class, name, parameterTypes, C, false)) == null) {
                return null;
            }
            return it;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Nullable
    public final Method r(@NotNull String name, @NotNull String desc, boolean isMember) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(desc, "desc");
        if (kotlin.jvm.internal.k.a(name, "<init>")) {
            return null;
        }
        ArrayList parameterTypes = new ArrayList();
        if (isMember) {
            parameterTypes.add(b());
        }
        n(parameterTypes, desc, false);
        Class<?> z = z();
        String str = name + "$default";
        Object[] array = parameterTypes.toArray(new Class[0]);
        if (array != null) {
            return D(z, str, (Class[]) array, C(desc), isMember);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Nullable
    public final Constructor<?> p(@NotNull String desc) {
        kotlin.jvm.internal.k.f(desc, "desc");
        return F(b(), B(desc));
    }

    @Nullable
    public final Constructor<?> q(@NotNull String desc) {
        kotlin.jvm.internal.k.f(desc, "desc");
        Class<?> b2 = b();
        ArrayList parameterTypes = new ArrayList();
        n(parameterTypes, desc, true);
        return F(b2, parameterTypes);
    }

    private final void n(List<Class<?>> result, String desc, boolean isConstructor) {
        List valueParameters = B(desc);
        result.addAll(valueParameters);
        int size = ((valueParameters.size() + 32) - 1) / 32;
        for (int i = 0; i < size; i++) {
            int i2 = i;
            Class cls = Integer.TYPE;
            kotlin.jvm.internal.k.b(cls, "Integer.TYPE");
            result.add(cls);
        }
        Class cls2 = isConstructor ? c : Object.class;
        kotlin.jvm.internal.k.b(cls2, "if (isConstructor) DEFAU…RKER else Any::class.java");
        result.add(cls2);
    }

    private final List<Class<?>> B(String desc) {
        int end;
        ArrayList result = new ArrayList();
        int begin = 1;
        while (desc.charAt(begin) != ')') {
            int end2 = begin;
            while (desc.charAt(end2) == '[') {
                end2++;
            }
            char charAt = desc.charAt(end2);
            if (kotlin.text.x.R("VZCBSIFJD", charAt, false, 2, (Object) null)) {
                end = end2 + 1;
            } else if (charAt == 'L') {
                end = kotlin.text.x.e0(desc, ';', begin, false, 4, (Object) null) + 1;
            } else {
                throw new y("Unknown type prefix in the method signature: " + desc);
            }
            result.add(E(desc, begin, end));
            begin = end;
        }
        return result;
    }

    private final Class<?> E(String desc, int begin, int end) {
        switch (desc.charAt(begin)) {
            case 'B':
                return Byte.TYPE;
            case 'C':
                return Character.TYPE;
            case 'D':
                return Double.TYPE;
            case 'F':
                return Float.TYPE;
            case 'I':
                return Integer.TYPE;
            case 'J':
                return Long.TYPE;
            case 'L':
                ClassLoader g2 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.g(b());
                String substring = desc.substring(begin + 1, end - 1);
                kotlin.jvm.internal.k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                Class<?> loadClass = g2.loadClass(w.G(substring, '/', '.', false, 4, (Object) null));
                kotlin.jvm.internal.k.b(loadClass, "jClass.safeClassLoader.l…d - 1).replace('/', '.'))");
                return loadClass;
            case 'S':
                return Short.TYPE;
            case 'V':
                Class<?> cls = Void.TYPE;
                kotlin.jvm.internal.k.b(cls, "Void.TYPE");
                return cls;
            case 'Z':
                return Boolean.TYPE;
            case '[':
                return kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.a(E(desc, begin + 1, end));
            default:
                throw new y("Unknown type prefix in the method signature: " + desc);
        }
    }

    private final Class<?> C(String desc) {
        return E(desc, kotlin.text.x.e0(desc, ')', 0, false, 6, (Object) null) + 1, desc.length());
    }

    /* compiled from: KDeclarationContainerImpl.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final kotlin.text.j a() {
            return j.d;
        }
    }
}
