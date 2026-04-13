package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.metadata.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.d;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.resolve.constants.h;
import kotlin.reflect.jvm.internal.impl.resolve.constants.k;
import kotlin.reflect.jvm.internal.impl.resolve.constants.m;
import kotlin.reflect.jvm.internal.impl.resolve.constants.r;
import kotlin.reflect.jvm.internal.impl.resolve.constants.s;
import kotlin.reflect.jvm.internal.impl.resolve.constants.v;
import kotlin.reflect.jvm.internal.impl.resolve.constants.x;
import kotlin.reflect.jvm.internal.impl.resolve.constants.z;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
public final class c extends a<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c, g<?>> {
    private final kotlin.reflect.jvm.internal.impl.serialization.deserialization.g e;
    private final y f;
    private final a0 g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull y module, @NotNull a0 notFoundClasses, @NotNull j storageManager, @NotNull n kotlinClassFinder) {
        super(storageManager, kotlinClassFinder);
        k.f(module, "module");
        k.f(notFoundClasses, "notFoundClasses");
        k.f(storageManager, "storageManager");
        k.f(kotlinClassFinder, "kotlinClassFinder");
        this.f = module;
        this.g = notFoundClasses;
        this.e = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.g(module, notFoundClasses);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: F */
    public kotlin.reflect.jvm.internal.impl.descriptors.annotations.c B(@NotNull b proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        return this.e.a(proto, nameResolver);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0063, code lost:
        throw new java.lang.AssertionError(r5);
     */
    @org.jetbrains.annotations.Nullable
    /* renamed from: E */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> z(@org.jetbrains.annotations.NotNull java.lang.String r5, @org.jetbrains.annotations.NotNull java.lang.Object r6) {
        /*
            r4 = this;
            java.lang.String r0 = "desc"
            kotlin.jvm.internal.k.f(r5, r0)
            java.lang.String r0 = "initializer"
            kotlin.jvm.internal.k.f(r6, r0)
            java.lang.String r0 = "ZBCS"
            r1 = 0
            r2 = 2
            r3 = 0
            boolean r0 = kotlin.text.x.S(r0, r5, r1, r2, r3)
            if (r0 == 0) goto L_0x0064
            r0 = r6
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r2 = r5.hashCode()
            switch(r2) {
                case 66: goto L_0x0050;
                case 67: goto L_0x0042;
                case 83: goto L_0x0034;
                case 90: goto L_0x0024;
                default: goto L_0x0023;
            }
        L_0x0023:
            goto L_0x005e
        L_0x0024:
            java.lang.String r2 = "Z"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x005e
            if (r0 == 0) goto L_0x002f
            r1 = 1
        L_0x002f:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            goto L_0x0065
        L_0x0034:
            java.lang.String r1 = "S"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x005e
            short r1 = (short) r0
            java.lang.Short r1 = java.lang.Short.valueOf(r1)
            goto L_0x0065
        L_0x0042:
            java.lang.String r1 = "C"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x005e
            char r1 = (char) r0
            java.lang.Character r1 = java.lang.Character.valueOf(r1)
            goto L_0x0065
        L_0x0050:
            java.lang.String r1 = "B"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x005e
            byte r1 = (byte) r0
            java.lang.Byte r1 = java.lang.Byte.valueOf(r1)
            goto L_0x0065
        L_0x005e:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>(r5)
            throw r1
        L_0x0064:
            r1 = r6
        L_0x0065:
            r0 = r1
            kotlin.reflect.jvm.internal.impl.resolve.constants.h r1 = kotlin.reflect.jvm.internal.impl.resolve.constants.h.a
            kotlin.reflect.jvm.internal.impl.resolve.constants.g r1 = r1.c(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.kotlin.c.z(java.lang.String, java.lang.Object):kotlin.reflect.jvm.internal.impl.resolve.constants.g");
    }

    /* access modifiers changed from: protected */
    @Nullable
    /* renamed from: H */
    public g<?> D(@NotNull g<?> constant) {
        k.f(constant, "constant");
        if (constant instanceof d) {
            return new x(((Number) ((d) constant).b()).byteValue());
        }
        if (constant instanceof v) {
            return new kotlin.reflect.jvm.internal.impl.resolve.constants.a0(((Number) ((v) constant).b()).shortValue());
        }
        if (constant instanceof m) {
            return new kotlin.reflect.jvm.internal.impl.resolve.constants.y(((Number) ((m) constant).b()).intValue());
        }
        if (constant instanceof s) {
            return new z(((Number) ((s) constant).b()).longValue());
        }
        return constant;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public p.a w(@NotNull kotlin.reflect.jvm.internal.impl.name.a annotationClassId, @NotNull o0 source, @NotNull List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> result) {
        k.f(annotationClassId, "annotationClassId");
        k.f(source, "source");
        k.f(result, "result");
        return new a(this, G(annotationClassId), result, source);
    }

    /* compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
    public static final class a implements p.a {
        /* access modifiers changed from: private */
        public final HashMap<f, g<?>> a = new HashMap<>();
        final /* synthetic */ c b;
        final /* synthetic */ e c;
        final /* synthetic */ List d;
        final /* synthetic */ o0 e;

        a(c $outer, e $captured_local_variable$1, List $captured_local_variable$2, o0 $captured_local_variable$3) {
            this.b = $outer;
            this.c = $captured_local_variable$1;
            this.d = $captured_local_variable$2;
            this.e = $captured_local_variable$3;
        }

        public void d(@Nullable f name, @Nullable Object value) {
            if (name != null) {
                this.a.put(name, h(name, value));
            }
        }

        public void c(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f value) {
            k.f(name, "name");
            k.f(value, "value");
            this.a.put(name, new r(value));
        }

        public void a(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a enumClassId, @NotNull f enumEntryName) {
            k.f(name, "name");
            k.f(enumClassId, "enumClassId");
            k.f(enumEntryName, "enumEntryName");
            this.a.put(name, new kotlin.reflect.jvm.internal.impl.resolve.constants.j(enumClassId, enumEntryName));
        }

        /* compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
        public static final class b implements p.b {
            private final ArrayList<g<?>> a = new ArrayList<>();
            final /* synthetic */ a b;
            final /* synthetic */ f c;

            b(a $outer, f $captured_local_variable$1) {
                this.b = $outer;
                this.c = $captured_local_variable$1;
            }

            public void a(@Nullable Object value) {
                this.a.add(this.b.h(this.c, value));
            }

            public void b(@NotNull kotlin.reflect.jvm.internal.impl.name.a enumClassId, @NotNull f enumEntryName) {
                k.f(enumClassId, "enumClassId");
                k.f(enumEntryName, "enumEntryName");
                this.a.add(new kotlin.reflect.jvm.internal.impl.resolve.constants.j(enumClassId, enumEntryName));
            }

            public void c(@NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f value) {
                k.f(value, "value");
                this.a.add(new r(value));
            }

            public void visitEnd() {
                w0 parameter = kotlin.reflect.jvm.internal.impl.load.java.components.a.b(this.c, this.b.c);
                if (parameter != null) {
                    HashMap g = this.b.a;
                    f fVar = this.c;
                    h hVar = h.a;
                    List<T> c2 = kotlin.reflect.jvm.internal.impl.utils.a.c(this.a);
                    b0 type = parameter.getType();
                    k.b(type, "parameter.type");
                    g.put(fVar, hVar.b(c2, type));
                }
            }
        }

        @Nullable
        public p.b e(@NotNull f name) {
            k.f(name, "name");
            return new b(this, name);
        }

        @Nullable
        public p.a b(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
            k.f(name, "name");
            k.f(classId, "classId");
            ArrayList list = new ArrayList();
            c cVar = this.b;
            o0 o0Var = o0.a;
            k.b(o0Var, "SourceElement.NO_SOURCE");
            p.a visitor = cVar.w(classId, o0Var, list);
            if (visitor == null) {
                k.n();
            }
            return new C0372a(this, visitor, name, list);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.c$a$a  reason: collision with other inner class name */
        /* compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
        public static final class C0372a implements p.a {
            private final /* synthetic */ p.a a;
            final /* synthetic */ a b;
            final /* synthetic */ p.a c;
            final /* synthetic */ f d;
            final /* synthetic */ ArrayList e;

            public void a(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull f fVar2) {
                k.f(fVar, "name");
                k.f(aVar, "enumClassId");
                k.f(fVar2, "enumEntryName");
                this.a.a(fVar, aVar, fVar2);
            }

            @Nullable
            public p.a b(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.name.a aVar) {
                k.f(fVar, "name");
                k.f(aVar, "classId");
                return this.a.b(fVar, aVar);
            }

            public void c(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f fVar2) {
                k.f(fVar, "name");
                k.f(fVar2, "value");
                this.a.c(fVar, fVar2);
            }

            public void d(@Nullable f fVar, @Nullable Object obj) {
                this.a.d(fVar, obj);
            }

            @Nullable
            public p.b e(@NotNull f fVar) {
                k.f(fVar, "name");
                return this.a.e(fVar);
            }

            C0372a(a $outer, p.a $captured_local_variable$1, f $captured_local_variable$2, ArrayList $captured_local_variable$3) {
                this.b = $outer;
                this.c = $captured_local_variable$1;
                this.d = $captured_local_variable$2;
                this.e = $captured_local_variable$3;
                this.a = $captured_local_variable$1;
            }

            public void visitEnd() {
                this.c.visitEnd();
                this.b.a.put(this.d, new kotlin.reflect.jvm.internal.impl.resolve.constants.a((kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) kotlin.collections.y.q0(this.e)));
            }
        }

        public void visitEnd() {
            this.d.add(new kotlin.reflect.jvm.internal.impl.descriptors.annotations.d(this.c.m(), this.a, this.e));
        }

        /* access modifiers changed from: private */
        public final g<?> h(f name, Object value) {
            g<?> c2 = h.a.c(value);
            if (c2 != null) {
                return c2;
            }
            k.a aVar = kotlin.reflect.jvm.internal.impl.resolve.constants.k.b;
            return aVar.a("Unsupported annotation argument: " + name);
        }
    }

    private final e G(kotlin.reflect.jvm.internal.impl.name.a classId) {
        return t.c(this.f, classId, this.g);
    }
}
