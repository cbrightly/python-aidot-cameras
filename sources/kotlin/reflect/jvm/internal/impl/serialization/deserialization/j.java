package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.Set;
import kotlin.collections.n0;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassDeserializer.kt */
public final class j {
    /* access modifiers changed from: private */
    @NotNull
    public static final Set<kotlin.reflect.jvm.internal.impl.name.a> a = n0.c(kotlin.reflect.jvm.internal.impl.name.a.m(g.h.c.l()));
    public static final b b = new b((DefaultConstructorMarker) null);
    private final l<a, e> c;
    private final l d;

    public j(@NotNull l components) {
        k.f(components, "components");
        this.d = components;
        this.c = components.t().g(new c(this));
    }

    /* compiled from: ClassDeserializer.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<a, e> {
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar) {
            super(1);
            this.this$0 = jVar;
        }

        @Nullable
        public final e invoke(@NotNull a key) {
            k.f(key, CacheEntity.KEY);
            return this.this$0.c(key);
        }
    }

    public static /* synthetic */ e e(j jVar, kotlin.reflect.jvm.internal.impl.name.a aVar, h hVar, int i, Object obj) {
        if ((i & 2) != 0) {
            hVar = null;
        }
        return jVar.d(aVar, hVar);
    }

    @Nullable
    public final e d(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @Nullable h classData) {
        k.f(classId, "classId");
        return this.c.invoke(new a(classId, classData));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c5 A[LOOP:1: B:27:0x0098->B:37:0x00c5, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cc A[EDGE_INSN: B:48:0x00cc->B:39:0x00cc ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.reflect.jvm.internal.impl.descriptors.e c(kotlin.reflect.jvm.internal.impl.serialization.deserialization.j.a r18) {
        /*
            r17 = this;
            r0 = r17
            kotlin.reflect.jvm.internal.impl.name.a r1 = r18.b()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r2 = r0.d
            java.lang.Iterable r2 = r2.k()
            java.util.Iterator r2 = r2.iterator()
        L_0x0010:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0026
            java.lang.Object r3 = r2.next()
            kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b r3 = (kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b) r3
            kotlin.reflect.jvm.internal.impl.descriptors.e r4 = r3.c(r1)
            if (r4 == 0) goto L_0x0025
            r2 = r4
            r4 = 0
            return r2
        L_0x0025:
            goto L_0x0010
        L_0x0026:
            java.util.Set<kotlin.reflect.jvm.internal.impl.name.a> r2 = a
            boolean r2 = r2.contains(r1)
            r3 = 0
            if (r2 == 0) goto L_0x0030
            return r3
        L_0x0030:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.h r2 = r18.a()
            if (r2 == 0) goto L_0x0037
            goto L_0x0041
        L_0x0037:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r2 = r0.d
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.i r2 = r2.e()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.h r2 = r2.a(r1)
        L_0x0041:
            if (r2 == 0) goto L_0x0108
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r6 = r2.a()
            kotlin.reflect.jvm.internal.impl.metadata.c r13 = r2.b()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.a r14 = r2.c()
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r2 = r2.d()
            kotlin.reflect.jvm.internal.impl.name.a r15 = r1.g()
            java.lang.String r4 = "classId.shortClassName"
            if (r15 == 0) goto L_0x007f
            r5 = 2
            kotlin.reflect.jvm.internal.impl.descriptors.e r5 = e(r0, r15, r3, r5, r3)
            boolean r7 = r5 instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d
            if (r7 != 0) goto L_0x0065
            r5 = r3
        L_0x0065:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r5 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) r5
            if (r5 == 0) goto L_0x007e
            kotlin.reflect.jvm.internal.impl.name.f r7 = r1.j()
            kotlin.jvm.internal.k.b(r7, r4)
            boolean r4 = r5.V0(r7)
            if (r4 != 0) goto L_0x0077
            return r3
        L_0x0077:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r3 = r5.P0()
            r8 = r3
            goto L_0x00fa
        L_0x007e:
            return r3
        L_0x007f:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r5 = r0.d
            kotlin.reflect.jvm.internal.impl.descriptors.c0 r5 = r5.r()
            kotlin.reflect.jvm.internal.impl.name.b r7 = r1.h()
            java.lang.String r8 = "classId.packageFqName"
            kotlin.jvm.internal.k.b(r7, r8)
            java.util.List r11 = r5.a(r7)
            r5 = r11
            r7 = 0
            java.util.Iterator r8 = r5.iterator()
        L_0x0098:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00c9
            java.lang.Object r9 = r8.next()
            r10 = r9
            kotlin.reflect.jvm.internal.impl.descriptors.b0 r10 = (kotlin.reflect.jvm.internal.impl.descriptors.b0) r10
            r12 = 0
            boolean r3 = r10 instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.p
            if (r3 == 0) goto L_0x00bf
            r3 = r10
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.p r3 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.p) r3
            r16 = r5
            kotlin.reflect.jvm.internal.impl.name.f r5 = r1.j()
            kotlin.jvm.internal.k.b(r5, r4)
            boolean r3 = r3.l0(r5)
            if (r3 == 0) goto L_0x00bd
            goto L_0x00c1
        L_0x00bd:
            r3 = 0
            goto L_0x00c2
        L_0x00bf:
            r16 = r5
        L_0x00c1:
            r3 = 1
        L_0x00c2:
            if (r3 == 0) goto L_0x00c5
            goto L_0x00cc
        L_0x00c5:
            r5 = r16
            r3 = 0
            goto L_0x0098
        L_0x00c9:
            r16 = r5
            r9 = 0
        L_0x00cc:
            r5 = r9
            kotlin.reflect.jvm.internal.impl.descriptors.b0 r5 = (kotlin.reflect.jvm.internal.impl.descriptors.b0) r5
            if (r5 == 0) goto L_0x0106
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r4 = r0.d
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r7 = new kotlin.reflect.jvm.internal.impl.metadata.deserialization.h
            kotlin.reflect.jvm.internal.impl.metadata.t r3 = r13.getTypeTable()
            java.lang.String r8 = "classProto.typeTable"
            kotlin.jvm.internal.k.b(r3, r8)
            r7.<init>(r3)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.k$a r3 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.k.b
            kotlin.reflect.jvm.internal.impl.metadata.w r8 = r13.getVersionRequirementTable()
            java.lang.String r9 = "classProto.versionRequirementTable"
            kotlin.jvm.internal.k.b(r8, r9)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.k r8 = r3.a(r8)
            r10 = 0
            r9 = r14
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r3 = r4.a(r5, r6, r7, r8, r9, r10)
            r8 = r3
        L_0x00fa:
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r3 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d
            r7 = r3
            r9 = r13
            r10 = r6
            r11 = r14
            r12 = r2
            r7.<init>(r8, r9, r10, r11, r12)
            return r3
        L_0x0106:
            r3 = 0
            return r3
        L_0x0108:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.j.c(kotlin.reflect.jvm.internal.impl.serialization.deserialization.j$a):kotlin.reflect.jvm.internal.impl.descriptors.e");
    }

    /* compiled from: ClassDeserializer.kt */
    public static final class a {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a a;
        @Nullable
        private final h b;

        public a(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @Nullable h classData) {
            k.f(classId, "classId");
            this.a = classId;
            this.b = classData;
        }

        @Nullable
        public final h a() {
            return this.b;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a b() {
            return this.a;
        }

        public boolean equals(@Nullable Object other) {
            return (other instanceof a) && k.a(this.a, ((a) other).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    /* compiled from: ClassDeserializer.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.a> a() {
            return j.a;
        }
    }
}
