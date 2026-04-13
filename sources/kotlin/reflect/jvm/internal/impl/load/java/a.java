package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g;
import kotlin.reflect.jvm.internal.impl.storage.d;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationTypeQualifierResolver.kt */
public final class a {
    private final d<e, kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> a;
    private final boolean b;
    private final kotlin.reflect.jvm.internal.impl.utils.e c;

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.a$a  reason: collision with other inner class name */
    /* compiled from: AnnotationTypeQualifierResolver.kt */
    public enum C0356a {
        METHOD_RETURN_TYPE,
        VALUE_PARAMETER,
        FIELD,
        TYPE_USE
    }

    public a(@NotNull j storageManager, @NotNull kotlin.reflect.jvm.internal.impl.utils.e jsr305State) {
        k.f(storageManager, "storageManager");
        k.f(jsr305State, "jsr305State");
        this.c = jsr305State;
        this.a = storageManager.g(new c(this));
        this.b = jsr305State.a();
    }

    /* compiled from: AnnotationTypeQualifierResolver.kt */
    public static final class b {
        private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c a;
        private final int b;

        public b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c typeQualifier, int applicability) {
            k.f(typeQualifier, "typeQualifier");
            this.a = typeQualifier;
            this.b = applicability;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c a() {
            return this.a;
        }

        @NotNull
        public final List<C0356a> b() {
            C0356a[] values = C0356a.values();
            ArrayList arrayList = new ArrayList();
            for (C0356a p1 : values) {
                if (d(p1)) {
                    arrayList.add(p1);
                }
            }
            return arrayList;
        }

        private final boolean d(C0356a elementType) {
            return c(C0356a.TYPE_USE) || c(elementType);
        }

        private final boolean c(C0356a elementType) {
            return (this.b & (1 << elementType.ordinal())) != 0;
        }
    }

    /* compiled from: AnnotationTypeQualifierResolver.kt */
    public static final /* synthetic */ class c extends h implements l<e, kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> {
        c(a aVar) {
            super(1, aVar);
        }

        public final String getName() {
            return "computeTypeQualifierNickname";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(a.class);
        }

        public final String getSignature() {
            return "computeTypeQualifierNickname(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;";
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c invoke(@NotNull e p1) {
            k.f(p1, "p1");
            return ((a) this.receiver).b(p1);
        }
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c b(e classDescriptor) {
        if (!classDescriptor.getAnnotations().I(b.e())) {
            return null;
        }
        for (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c p1 : classDescriptor.getAnnotations()) {
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.c p12 = i(p1);
            if (p12 != null) {
                return p12;
            }
        }
        return null;
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c k(e classDescriptor) {
        if (classDescriptor.h() != f.ANNOTATION_CLASS) {
            return null;
        }
        return this.a.invoke(classDescriptor);
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c i(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        e annotationClass;
        k.f(annotationDescriptor, "annotationDescriptor");
        if (this.c.a() || (annotationClass = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(annotationDescriptor)) == null) {
            return null;
        }
        if (b.f(annotationClass)) {
            return annotationDescriptor;
        }
        return k(annotationClass);
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.k h(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        kotlin.reflect.jvm.internal.impl.load.java.lazy.k $dstr$qualifier$applicability;
        k.f(annotationDescriptor, "annotationDescriptor");
        if (this.c.a() || ($dstr$qualifier$applicability = b.b().get(annotationDescriptor.e())) == null) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h qualifier = $dstr$qualifier$applicability.a();
        Collection applicability = $dstr$qualifier$applicability.b();
        kotlin.reflect.jvm.internal.impl.utils.h state = f(annotationDescriptor);
        if (!(state != kotlin.reflect.jvm.internal.impl.utils.h.IGNORE)) {
            state = null;
        }
        if (state != null) {
            return new kotlin.reflect.jvm.internal.impl.load.java.lazy.k(kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h.b(qualifier, (g) null, state.isWarning(), 1, (Object) null), applicability);
        }
        return null;
    }

    @Nullable
    public final b j(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        e typeQualifierDefaultAnnotatedClass;
        Object element$iv;
        boolean z;
        Iterable list$iv$iv;
        k.f(annotationDescriptor, "annotationDescriptor");
        if (!this.c.a() && (typeQualifierDefaultAnnotatedClass = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(annotationDescriptor)) != null) {
            if (!typeQualifierDefaultAnnotatedClass.getAnnotations().I(b.d())) {
                typeQualifierDefaultAnnotatedClass = null;
            }
            if (typeQualifierDefaultAnnotatedClass != null) {
                e g = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(annotationDescriptor);
                if (g == null) {
                    k.n();
                }
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.c c2 = g.getAnnotations().c(b.d());
                if (c2 == null) {
                    k.n();
                }
                Map $this$flatMapTo$iv$iv = c2.a();
                ArrayList<C0356a> $this$fold$iv = new ArrayList<>();
                for (Map.Entry $dstr$parameter$argument : $this$flatMapTo$iv$iv.entrySet()) {
                    kotlin.reflect.jvm.internal.impl.resolve.constants.g argument = (kotlin.reflect.jvm.internal.impl.resolve.constants.g) $dstr$parameter$argument.getValue();
                    if (k.a((kotlin.reflect.jvm.internal.impl.name.f) $dstr$parameter$argument.getKey(), s.c)) {
                        list$iv$iv = d(argument);
                    } else {
                        list$iv$iv = q.g();
                    }
                    v.x($this$fold$iv, list$iv$iv);
                }
                int accumulator$iv = 0;
                for (C0356a applicabilityType : $this$fold$iv) {
                    accumulator$iv |= 1 << applicabilityType.ordinal();
                }
                int elementTypesMask = accumulator$iv;
                Iterator it = typeQualifierDefaultAnnotatedClass.getAnnotations().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        element$iv = null;
                        break;
                    }
                    element$iv = it.next();
                    if (i((kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) element$iv) != null) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (z) {
                        break;
                    }
                }
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.c typeQualifier = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) element$iv;
                if (typeQualifier != null) {
                    return new b(typeQualifier, elementTypesMask);
                }
                return null;
            }
        }
        return null;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.utils.h f(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        k.f(annotationDescriptor, "annotationDescriptor");
        kotlin.reflect.jvm.internal.impl.utils.h it = g(annotationDescriptor);
        if (it != null) {
            return it;
        }
        return this.c.c();
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.utils.h g(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        k.f(annotationDescriptor, "annotationDescriptor");
        Map<String, kotlin.reflect.jvm.internal.impl.utils.h> e = this.c.e();
        kotlin.reflect.jvm.internal.impl.name.b e2 = annotationDescriptor.e();
        kotlin.reflect.jvm.internal.impl.utils.h it = e.get(e2 != null ? e2.b() : null);
        if (it != null) {
            return it;
        }
        e g = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(annotationDescriptor);
        if (g != null) {
            return e(g);
        }
        return null;
    }

    private final kotlin.reflect.jvm.internal.impl.utils.h e(@NotNull e $this$migrationAnnotationStatus) {
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.c c2 = $this$migrationAnnotationStatus.getAnnotations().c(b.c());
        kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> c3 = c2 != null ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.c(c2) : null;
        if (!(c3 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.j)) {
            c3 = null;
        }
        kotlin.reflect.jvm.internal.impl.resolve.constants.j enumValue = (kotlin.reflect.jvm.internal.impl.resolve.constants.j) c3;
        if (enumValue == null) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.utils.h it = this.c.d();
        if (it != null) {
            return it;
        }
        String b2 = enumValue.c().b();
        switch (b2.hashCode()) {
            case -2137067054:
                if (b2.equals("IGNORE")) {
                    return kotlin.reflect.jvm.internal.impl.utils.h.IGNORE;
                }
                return null;
            case -1838656823:
                if (b2.equals("STRICT")) {
                    return kotlin.reflect.jvm.internal.impl.utils.h.STRICT;
                }
                return null;
            case 2656902:
                if (b2.equals("WARN")) {
                    return kotlin.reflect.jvm.internal.impl.utils.h.WARN;
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<kotlin.reflect.jvm.internal.impl.load.java.a.C0356a> d(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.b
            if (r0 == 0) goto L_0x0032
            r0 = r10
            kotlin.reflect.jvm.internal.impl.resolve.constants.b r0 = (kotlin.reflect.jvm.internal.impl.resolve.constants.b) r0
            java.lang.Object r0 = r0.b()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = r0
            r4 = 0
            java.util.Iterator r5 = r3.iterator()
        L_0x001a:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0030
            java.lang.Object r6 = r5.next()
            r7 = r6
            kotlin.reflect.jvm.internal.impl.resolve.constants.g r7 = (kotlin.reflect.jvm.internal.impl.resolve.constants.g) r7
            r8 = 0
            java.util.List r7 = r9.d(r7)
            kotlin.collections.v.x(r2, r7)
            goto L_0x001a
        L_0x0030:
            goto L_0x0080
        L_0x0032:
            boolean r0 = r10 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.j
            if (r0 == 0) goto L_0x007c
            r0 = r10
            kotlin.reflect.jvm.internal.impl.resolve.constants.j r0 = (kotlin.reflect.jvm.internal.impl.resolve.constants.j) r0
            kotlin.reflect.jvm.internal.impl.name.f r0 = r0.c()
            java.lang.String r0 = r0.d()
            int r1 = r0.hashCode()
            switch(r1) {
                case -2024225567: goto L_0x006a;
                case 66889946: goto L_0x005f;
                case 107598562: goto L_0x0054;
                case 446088073: goto L_0x0049;
                default: goto L_0x0048;
            }
        L_0x0048:
            goto L_0x0075
        L_0x0049:
            java.lang.String r1 = "PARAMETER"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0075
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.VALUE_PARAMETER
            goto L_0x0076
        L_0x0054:
            java.lang.String r1 = "TYPE_USE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0075
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.TYPE_USE
            goto L_0x0076
        L_0x005f:
            java.lang.String r1 = "FIELD"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0075
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.FIELD
            goto L_0x0076
        L_0x006a:
            java.lang.String r1 = "METHOD"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0075
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.METHOD_RETURN_TYPE
            goto L_0x0076
        L_0x0075:
            r0 = 0
        L_0x0076:
            java.util.List r2 = kotlin.collections.q.k(r0)
            goto L_0x0080
        L_0x007c:
            java.util.List r2 = kotlin.collections.q.g()
        L_0x0080:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.a.d(kotlin.reflect.jvm.internal.impl.resolve.constants.g):java.util.List");
    }

    public final boolean c() {
        return this.b;
    }
}
