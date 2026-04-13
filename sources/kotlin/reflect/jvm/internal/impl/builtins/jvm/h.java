package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import com.didichuxing.doraemonkit.constant.SpInputType;
import com.leedarson.bean.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.z;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.v;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.e0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.utils.b;
import kotlin.reflect.jvm.internal.impl.utils.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmBuiltInsSettings.kt */
public class h implements kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a, kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c {
    static final /* synthetic */ kotlin.reflect.k[] a;
    /* access modifiers changed from: private */
    @NotNull
    public static final Set<String> b = p0.j(v.a.f("Collection", "toArray()[Ljava/lang/Object;", "toArray([Ljava/lang/Object;)[Ljava/lang/Object;"), "java/lang/annotation/Annotation.annotationType()Ljava/lang/Class;");
    /* access modifiers changed from: private */
    @NotNull
    public static final Set<String> c;
    /* access modifiers changed from: private */
    @NotNull
    public static final Set<String> d;
    @NotNull
    private static final Set<String> e;
    @NotNull
    private static final Set<String> f;
    @NotNull
    private static final Set<String> g;
    public static final a h;
    /* access modifiers changed from: private */
    public final c i = c.m;
    private final kotlin.g j;
    private final kotlin.g k;
    private final b0 l;
    private final kotlin.reflect.jvm.internal.impl.storage.f m;
    private final kotlin.reflect.jvm.internal.impl.storage.a<kotlin.reflect.jvm.internal.impl.name.b, kotlin.reflect.jvm.internal.impl.descriptors.e> n;
    private final kotlin.reflect.jvm.internal.impl.storage.f o;
    /* access modifiers changed from: private */
    public final y p;

    /* compiled from: JvmBuiltInsSettings.kt */
    public enum b {
        BLACK_LIST,
        WHITE_LIST,
        NOT_CONSIDERED,
        DROP
    }

    private final i0 p() {
        return (i0) kotlin.reflect.jvm.internal.impl.storage.i.a(this.m, this, a[0]);
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.g t() {
        return (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) kotlin.reflect.jvm.internal.impl.storage.i.a(this.o, this, a[1]);
    }

    /* access modifiers changed from: private */
    public final y u() {
        return (y) this.j.getValue();
    }

    private final boolean v() {
        return ((Boolean) this.k.getValue()).booleanValue();
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class g extends kotlin.jvm.internal.l implements p<kotlin.reflect.jvm.internal.impl.descriptors.l, kotlin.reflect.jvm.internal.impl.descriptors.l, Boolean> {
        final /* synthetic */ TypeSubstitutor $substitutor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(TypeSubstitutor typeSubstitutor) {
            super(2);
            this.$substitutor = typeSubstitutor;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.l) obj, (kotlin.reflect.jvm.internal.impl.descriptors.l) obj2));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.l $this$isEffectivelyTheSameAs, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.l javaConstructor) {
            kotlin.jvm.internal.k.f($this$isEffectivelyTheSameAs, "$this$isEffectivelyTheSameAs");
            kotlin.jvm.internal.k.f(javaConstructor, "javaConstructor");
            return kotlin.reflect.jvm.internal.impl.resolve.i.y($this$isEffectivelyTheSameAs, javaConstructor.c(this.$substitutor)) == i.j.a.OVERRIDABLE;
        }
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class l extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(h hVar) {
            super(1);
            this.this$0 = hVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(kotlin.reflect.jvm.internal.impl.descriptors.b overridden) {
            kotlin.jvm.internal.k.b(overridden, "overridden");
            if (overridden.h() == b.a.DECLARATION) {
                c h = this.this$0.i;
                kotlin.reflect.jvm.internal.impl.descriptors.m b = overridden.b();
                if (b == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                } else if (h.o((kotlin.reflect.jvm.internal.impl.descriptors.e) b)) {
                    return true;
                }
            }
            return false;
        }
    }

    public h(@NotNull y moduleDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.storage.j storageManager, @NotNull kotlin.jvm.functions.a<? extends y> deferredOwnerModuleDescriptor, @NotNull kotlin.jvm.functions.a<Boolean> isAdditionalBuiltInsFeatureSupported) {
        kotlin.jvm.internal.k.f(moduleDescriptor, "moduleDescriptor");
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(deferredOwnerModuleDescriptor, "deferredOwnerModuleDescriptor");
        kotlin.jvm.internal.k.f(isAdditionalBuiltInsFeatureSupported, "isAdditionalBuiltInsFeatureSupported");
        this.p = moduleDescriptor;
        this.j = kotlin.i.b(deferredOwnerModuleDescriptor);
        this.k = kotlin.i.b(isAdditionalBuiltInsFeatureSupported);
        this.l = n(storageManager);
        this.m = storageManager.c(new c(this, storageManager));
        this.n = storageManager.a();
        this.o = storageManager.c(new m(this));
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.storage.j $storageManager;
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(h hVar, kotlin.reflect.jvm.internal.impl.storage.j jVar) {
            super(0);
            this.this$0 = hVar;
            this.$storageManager = jVar;
        }

        @NotNull
        public final i0 invoke() {
            return t.c(this.this$0.u(), d.e.a(), new a0(this.$storageManager, this.this$0.u())).m();
        }
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class m extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.annotations.g> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(h hVar) {
            super(0);
            this.this$0 = hVar;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.g invoke() {
            return kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.a(kotlin.collections.p.b(kotlin.reflect.jvm.internal.impl.descriptors.annotations.f.b(this.this$0.p.j(), "This member is not fully supported by Kotlin compiler, so it may be absent or have different signature in next major version", (String) null, (String) null, 6, (Object) null)));
        }
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class d extends z {
        final /* synthetic */ h y;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(h $outer, y $super_call_param$1, kotlin.reflect.jvm.internal.impl.name.b $super_call_param$2) {
            super($super_call_param$1, $super_call_param$2);
            this.y = $outer;
        }

        @NotNull
        /* renamed from: f0 */
        public h.b l() {
            return h.b.b;
        }
    }

    private final b0 n(@NotNull kotlin.reflect.jvm.internal.impl.storage.j $this$createMockJavaIoSerializableType) {
        kotlin.reflect.jvm.internal.impl.descriptors.impl.h mockSerializableClass = new kotlin.reflect.jvm.internal.impl.descriptors.impl.h(new d(this, this.p, new kotlin.reflect.jvm.internal.impl.name.b("java.io")), kotlin.reflect.jvm.internal.impl.name.f.f("Serializable"), w.ABSTRACT, kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE, kotlin.collections.p.b(new e0($this$createMockJavaIoSerializableType, new e(this))), o0.a, false, $this$createMockJavaIoSerializableType);
        mockSerializableClass.l0(h.b.b, kotlin.collections.o0.d(), (kotlin.reflect.jvm.internal.impl.descriptors.d) null);
        i0 m2 = mockSerializableClass.m();
        kotlin.jvm.internal.k.b(m2, "mockSerializableClass.defaultType");
        return m2;
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(h hVar) {
            super(0);
            this.this$0 = hVar;
        }

        @NotNull
        public final i0 invoke() {
            i0 j = this.this$0.p.j().j();
            kotlin.jvm.internal.k.b(j, "moduleDescriptor.builtIns.anyType");
            return j;
        }
    }

    @NotNull
    public Collection<b0> d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor) {
        kotlin.jvm.internal.k.f(classDescriptor, "classDescriptor");
        kotlin.reflect.jvm.internal.impl.name.c fqName = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(classDescriptor);
        a aVar = h;
        if (aVar.i(fqName)) {
            i0 p2 = p();
            kotlin.jvm.internal.k.b(p2, "cloneableType");
            return q.j(p2, this.l);
        } else if (aVar.j(fqName)) {
            return kotlin.collections.p.b(this.l);
        } else {
            return q.g();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0157  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.n0> a(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.f r22, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.e r23) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            java.lang.String r3 = "name"
            kotlin.jvm.internal.k.f(r1, r3)
            java.lang.String r3 = "classDescriptor"
            kotlin.jvm.internal.k.f(r2, r3)
            kotlin.reflect.jvm.internal.impl.builtins.jvm.a$a r3 = kotlin.reflect.jvm.internal.impl.builtins.jvm.a.f
            kotlin.reflect.jvm.internal.impl.name.f r3 = r3.a()
            boolean r3 = kotlin.jvm.internal.k.a(r1, r3)
            if (r3 == 0) goto L_0x00a6
            boolean r3 = r2 instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d
            if (r3 == 0) goto L_0x00a6
            boolean r3 = kotlin.reflect.jvm.internal.impl.builtins.g.f0(r23)
            if (r3 == 0) goto L_0x00a6
            r3 = r2
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r3 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) r3
            kotlin.reflect.jvm.internal.impl.metadata.c r3 = r3.Q0()
            java.util.List r3 = r3.getFunctionList()
            java.lang.String r4 = "classDescriptor.classProto.functionList"
            kotlin.jvm.internal.k.b(r3, r4)
            r4 = 0
            boolean r5 = r3 instanceof java.util.Collection
            r6 = 0
            if (r5 == 0) goto L_0x0044
            boolean r5 = r3.isEmpty()
            if (r5 == 0) goto L_0x0044
            goto L_0x007d
        L_0x0044:
            java.util.Iterator r5 = r3.iterator()
        L_0x0048:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x007c
            java.lang.Object r7 = r5.next()
            r8 = r7
            kotlin.reflect.jvm.internal.impl.metadata.i r8 = (kotlin.reflect.jvm.internal.impl.metadata.i) r8
            r9 = 0
            r10 = r2
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r10 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) r10
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r10 = r10.P0()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r10 = r10.g()
            java.lang.String r11 = "functionProto"
            kotlin.jvm.internal.k.b(r8, r11)
            int r11 = r8.getName()
            kotlin.reflect.jvm.internal.impl.name.f r10 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.y.b(r10, r11)
            kotlin.reflect.jvm.internal.impl.builtins.jvm.a$a r11 = kotlin.reflect.jvm.internal.impl.builtins.jvm.a.f
            kotlin.reflect.jvm.internal.impl.name.f r11 = r11.a()
            boolean r8 = kotlin.jvm.internal.k.a(r10, r11)
            if (r8 == 0) goto L_0x0048
            r6 = 1
            goto L_0x007d
        L_0x007c:
        L_0x007d:
            if (r6 == 0) goto L_0x0084
            java.util.List r3 = kotlin.collections.q.g()
            return r3
        L_0x0084:
            r3 = r2
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r3 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) r3
            kotlin.reflect.jvm.internal.impl.types.i0 r4 = r21.p()
            kotlin.reflect.jvm.internal.impl.resolve.scopes.h r4 = r4.l()
            kotlin.reflect.jvm.internal.impl.incremental.components.d r5 = kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS
            java.util.Collection r4 = r4.b(r1, r5)
            java.lang.Object r4 = kotlin.collections.y.p0(r4)
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r4 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r4
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r3 = r0.m(r3, r4)
            java.util.List r3 = kotlin.collections.p.b(r3)
            return r3
        L_0x00a6:
            boolean r3 = r21.v()
            if (r3 != 0) goto L_0x00b1
            java.util.List r3 = kotlin.collections.q.g()
            return r3
        L_0x00b1:
            kotlin.reflect.jvm.internal.impl.builtins.jvm.h$h r3 = new kotlin.reflect.jvm.internal.impl.builtins.jvm.h$h
            r3.<init>(r1)
            java.util.Collection r3 = r0.o(r2, r3)
            r4 = 0
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r6 = r3
            r7 = 0
            r8 = r6
            r9 = 0
            java.util.Iterator r10 = r8.iterator()
        L_0x00ca:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0180
            java.lang.Object r11 = r10.next()
            r12 = r11
            r13 = 0
            r14 = r12
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r14 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r14
            r15 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.m r16 = r14.b()
            if (r16 == 0) goto L_0x0178
            r1 = r16
            kotlin.reflect.jvm.internal.impl.descriptors.e r1 = (kotlin.reflect.jvm.internal.impl.descriptors.e) r1
            kotlin.reflect.jvm.internal.impl.types.v0 r1 = kotlin.reflect.jvm.internal.impl.builtins.jvm.j.a(r1, r2)
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r1 = r1.c()
            kotlin.reflect.jvm.internal.impl.descriptors.u r1 = r14.c(r1)
            if (r1 == 0) goto L_0x0170
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r1 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r1
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r16 = r1.r()
            r17 = r16
            r18 = 0
            r19 = r1
            r1 = r17
            r1.p(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.l0 r2 = r23.F0()
            r1.d(r2)
            r1.k()
            kotlin.reflect.jvm.internal.impl.builtins.jvm.h$b r2 = r0.s(r14)
            int[] r17 = kotlin.reflect.jvm.internal.impl.builtins.jvm.i.a
            int r20 = r2.ordinal()
            r17 = r17[r20]
            r20 = 0
            switch(r17) {
                case 1: goto L_0x013c;
                case 2: goto L_0x012b;
                case 3: goto L_0x012a;
                case 4: goto L_0x0129;
                default: goto L_0x0126;
            }
        L_0x0126:
            r17 = r2
            goto L_0x014f
        L_0x0129:
            goto L_0x014f
        L_0x012a:
            goto L_0x015e
        L_0x012b:
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r0 = r21.t()
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r0 = r1.r(r0)
            r17 = r2
            java.lang.String r2 = "setAdditionalAnnotations(notConsideredDeprecation)"
            kotlin.jvm.internal.k.b(r0, r2)
            goto L_0x014f
        L_0x013c:
            r17 = r2
            boolean r0 = kotlin.reflect.jvm.internal.impl.descriptors.x.a(r23)
            if (r0 == 0) goto L_0x0145
            goto L_0x015e
        L_0x0145:
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r0 = r1.e()
            java.lang.String r2 = "setHiddenForResolutionEverywhereBesideSupercalls()"
            kotlin.jvm.internal.k.b(r0, r2)
        L_0x014f:
            kotlin.reflect.jvm.internal.impl.descriptors.u r0 = r16.build()
            if (r0 != 0) goto L_0x015a
            kotlin.jvm.internal.k.n()
        L_0x015a:
            r20 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r20 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r20
        L_0x015e:
            if (r20 == 0) goto L_0x0167
            r0 = r20
            r1 = 0
            r5.add(r0)
            goto L_0x0168
        L_0x0167:
        L_0x0168:
            r0 = r21
            r1 = r22
            r2 = r23
            goto L_0x00ca
        L_0x0170:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor"
            r0.<init>(r1)
            throw r0
        L_0x0178:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor"
            r0.<init>(r1)
            throw r0
        L_0x0180:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.jvm.h.a(kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.e):java.util.Collection");
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.jvm.h$h  reason: collision with other inner class name */
    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class C0347h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.resolve.scopes.h, Collection<? extends n0>> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.name.f $name;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0347h(kotlin.reflect.jvm.internal.impl.name.f fVar) {
            super(1);
            this.$name = fVar;
        }

        @NotNull
        public final Collection<? extends n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.h it) {
            kotlin.jvm.internal.k.f(it, "it");
            return it.b(this.$name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        r0 = (r0 = r0.J0()).a();
     */
    @org.jetbrains.annotations.NotNull
    /* renamed from: q */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Set<kotlin.reflect.jvm.internal.impl.name.f> e(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.e r2) {
        /*
            r1 = this;
            java.lang.String r0 = "classDescriptor"
            kotlin.jvm.internal.k.f(r2, r0)
            boolean r0 = r1.v()
            if (r0 != 0) goto L_0x0010
            java.util.Set r0 = kotlin.collections.o0.d()
            return r0
        L_0x0010:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r0 = r1.r(r2)
            if (r0 == 0) goto L_0x0023
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g r0 = r0.R()
            if (r0 == 0) goto L_0x0023
            java.util.Set r0 = r0.a()
            if (r0 == 0) goto L_0x0023
            goto L_0x0027
        L_0x0023:
            java.util.Set r0 = kotlin.collections.o0.d()
        L_0x0027:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.jvm.h.e(kotlin.reflect.jvm.internal.impl.descriptors.e):java.util.Set");
    }

    private final Collection<n0> o(kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.resolve.scopes.h, ? extends Collection<? extends n0>> functionsByScope) {
        Iterable $this$any$iv;
        kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f javaAnalogueDescriptor = r(classDescriptor);
        if (javaAnalogueDescriptor != null) {
            Collection kotlinClassDescriptors = this.i.y(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(javaAnalogueDescriptor), b.p.a());
            kotlin.reflect.jvm.internal.impl.descriptors.e kotlinMutableClassIfContainer = (kotlin.reflect.jvm.internal.impl.descriptors.e) kotlin.collections.y.e0(kotlinClassDescriptors);
            if (kotlinMutableClassIfContainer != null) {
                j.b bVar = kotlin.reflect.jvm.internal.impl.utils.j.c;
                Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> $this$map$iv = kotlinClassDescriptors;
                Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, 10));
                for (kotlin.reflect.jvm.internal.impl.descriptors.e it : $this$map$iv) {
                    destination$iv$iv.add(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(it));
                }
                kotlin.reflect.jvm.internal.impl.utils.j kotlinVersions = bVar.b(destination$iv$iv);
                boolean isMutable = this.i.o(classDescriptor);
                kotlin.reflect.jvm.internal.impl.resolve.scopes.h scope = this.n.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(javaAnalogueDescriptor), new f(javaAnalogueDescriptor, kotlinMutableClassIfContainer)).R();
                kotlin.jvm.internal.k.b(scope, "fakeJavaClassDescriptor.unsubstitutedMemberScope");
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : (Iterable) functionsByScope.invoke(scope)) {
                    kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f javaAnalogueDescriptor2 = javaAnalogueDescriptor;
                    Collection kotlinClassDescriptors2 = kotlinClassDescriptors;
                    n0 analogueMember = (n0) element$iv$iv;
                    kotlin.reflect.jvm.internal.impl.descriptors.e kotlinMutableClassIfContainer2 = kotlinMutableClassIfContainer;
                    boolean z = true;
                    if (analogueMember.h() != b.a.DECLARATION) {
                        z = false;
                    } else if (!analogueMember.getVisibility().c()) {
                        z = false;
                    } else if (kotlin.reflect.jvm.internal.impl.builtins.g.n0(analogueMember)) {
                        z = false;
                    } else {
                        Collection<? extends u> d2 = analogueMember.d();
                        kotlin.jvm.internal.k.b(d2, "analogueMember.overriddenDescriptors");
                        if ((d2 instanceof Collection) == 0 || !d2.isEmpty()) {
                            Iterator<T> it2 = d2.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    Iterable $this$any$iv2 = d2;
                                    $this$any$iv = null;
                                    break;
                                }
                                Collection<? extends u> collection = d2;
                                u it3 = (u) it2.next();
                                Iterator<T> it4 = it2;
                                kotlin.jvm.internal.k.b(it3, "it");
                                kotlin.reflect.jvm.internal.impl.descriptors.m b2 = it3.b();
                                u uVar = it3;
                                kotlin.jvm.internal.k.b(b2, "it.containingDeclaration");
                                if (kotlinVersions.contains(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(b2))) {
                                    $this$any$iv = 1;
                                    break;
                                }
                                d2 = collection;
                                it2 = it4;
                            }
                        } else {
                            $this$any$iv = null;
                        }
                        if ($this$any$iv != null) {
                            z = false;
                        } else if (w(analogueMember, isMutable)) {
                            z = false;
                        }
                    }
                    if (z) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                    kotlin.reflect.jvm.internal.impl.descriptors.e eVar = classDescriptor;
                    javaAnalogueDescriptor = javaAnalogueDescriptor2;
                    kotlinClassDescriptors = kotlinClassDescriptors2;
                    kotlinMutableClassIfContainer = kotlinMutableClassIfContainer2;
                }
                return destination$iv$iv2;
            }
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f fVar = javaAnalogueDescriptor;
            return q.g();
        }
        kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.resolve.scopes.h, ? extends Collection<? extends n0>> lVar = functionsByScope;
        return q.g();
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f $javaAnalogueDescriptor;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.e $kotlinMutableClassIfContainer;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f fVar, kotlin.reflect.jvm.internal.impl.descriptors.e eVar) {
            super(0);
            this.$javaAnalogueDescriptor = fVar;
            this.$kotlinMutableClassIfContainer = eVar;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f invoke() {
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f fVar = this.$javaAnalogueDescriptor;
            kotlin.reflect.jvm.internal.impl.load.java.components.g gVar = kotlin.reflect.jvm.internal.impl.load.java.components.g.a;
            kotlin.jvm.internal.k.b(gVar, "JavaResolverCache.EMPTY");
            return fVar.G0(gVar, this.$kotlinMutableClassIfContainer);
        }
    }

    private final n0 m(kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d arrayClassDescriptor, n0 cloneFromCloneable) {
        u.a r = cloneFromCloneable.r();
        u.a $this$apply = r;
        $this$apply.p(arrayClassDescriptor);
        $this$apply.c(z0.e);
        $this$apply.l(arrayClassDescriptor.m());
        $this$apply.d(arrayClassDescriptor.F0());
        Object build = r.build();
        if (build == null) {
            kotlin.jvm.internal.k.n();
        }
        return (n0) build;
    }

    private final boolean w(@NotNull n0 $this$isMutabilityViolation, boolean isMutable) {
        kotlin.reflect.jvm.internal.impl.descriptors.m b2 = $this$isMutabilityViolation.b();
        if (b2 != null) {
            String jvmDescriptor = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c($this$isMutabilityViolation, false, false, 3, (Object) null);
            if (e.contains(v.a.l((kotlin.reflect.jvm.internal.impl.descriptors.e) b2, jvmDescriptor)) ^ isMutable) {
                return true;
            }
            Boolean e2 = kotlin.reflect.jvm.internal.impl.utils.b.e(kotlin.collections.p.b($this$isMutabilityViolation), k.a, new l(this));
            kotlin.jvm.internal.k.b(e2, "DFS.ifAny<CallableMember…lassDescriptor)\n        }");
            return e2.booleanValue();
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class k<N> implements b.c<N> {
        public static final k a = new k();

        k() {
        }

        @NotNull
        /* renamed from: b */
        public final Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> a(kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            kotlin.jvm.internal.k.b(it, "it");
            kotlin.reflect.jvm.internal.impl.descriptors.b a2 = it.a();
            kotlin.jvm.internal.k.b(a2, "it.original");
            return a2.d();
        }
    }

    private final b s(@NotNull u $this$getJdkMethodStatus) {
        kotlin.reflect.jvm.internal.impl.descriptors.m b2 = $this$getJdkMethodStatus.b();
        if (b2 != null) {
            String jvmDescriptor = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c($this$getJdkMethodStatus, false, false, 3, (Object) null);
            kotlin.jvm.internal.z result = new kotlin.jvm.internal.z();
            result.element = null;
            Object b3 = kotlin.reflect.jvm.internal.impl.utils.b.b(kotlin.collections.p.b((kotlin.reflect.jvm.internal.impl.descriptors.e) b2), new i(this), new j(jvmDescriptor, result));
            kotlin.jvm.internal.k.b(b3, "DFS.dfs<ClassDescriptor,…CONSIDERED\n            })");
            return (b) b3;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class i<N> implements b.c<N> {
        final /* synthetic */ h a;

        i(h hVar) {
            this.a = hVar;
        }

        @NotNull
        /* renamed from: b */
        public final List<kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f> a(kotlin.reflect.jvm.internal.impl.descriptors.e it) {
            Iterable $this$mapNotNull$iv;
            Object it$iv$iv;
            kotlin.jvm.internal.k.b(it, "it");
            u0 i = it.i();
            kotlin.jvm.internal.k.b(i, "it.typeConstructor");
            Iterable<b0> $this$mapNotNull$iv2 = i.b();
            kotlin.jvm.internal.k.b($this$mapNotNull$iv2, "it.typeConstructor.supertypes");
            ArrayList arrayList = new ArrayList();
            for (b0 it2 : $this$mapNotNull$iv2) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = it2.I0().c();
                kotlin.reflect.jvm.internal.impl.descriptors.h a2 = c != null ? c.a() : null;
                if (!(a2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                    a2 = null;
                }
                kotlin.reflect.jvm.internal.impl.descriptors.e eVar = (kotlin.reflect.jvm.internal.impl.descriptors.e) a2;
                if (eVar != null) {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    it$iv$iv = this.a.r(eVar);
                } else {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    it$iv$iv = null;
                }
                if (it$iv$iv != null) {
                    arrayList.add(it$iv$iv);
                }
                $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            }
            return arrayList;
        }
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class j extends b.C0433b<kotlin.reflect.jvm.internal.impl.descriptors.e, b> {
        final /* synthetic */ String a;
        final /* synthetic */ kotlin.jvm.internal.z b;

        j(String $captured_local_variable$0, kotlin.jvm.internal.z $captured_local_variable$1) {
            this.a = $captured_local_variable$0;
            this.b = $captured_local_variable$1;
        }

        /* renamed from: d */
        public boolean c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e javaClassDescriptor) {
            kotlin.jvm.internal.k.f(javaClassDescriptor, "javaClassDescriptor");
            String signature = v.a.l(javaClassDescriptor, this.a);
            a aVar = h.h;
            if (aVar.f().contains(signature)) {
                this.b.element = b.BLACK_LIST;
            } else if (aVar.h().contains(signature)) {
                this.b.element = b.WHITE_LIST;
            } else if (aVar.g().contains(signature)) {
                this.b.element = b.DROP;
            }
            return ((b) this.b.element) == null;
        }

        @NotNull
        /* renamed from: e */
        public b a() {
            b bVar = (b) this.b.element;
            return bVar != null ? bVar : b.NOT_CONSIDERED;
        }
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e $this$getJavaAnalogue) {
        kotlin.reflect.jvm.internal.impl.name.a x;
        kotlin.reflect.jvm.internal.impl.name.b javaAnalogueFqName;
        kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f fVar = null;
        if (kotlin.reflect.jvm.internal.impl.builtins.g.c0($this$getJavaAnalogue) || !kotlin.reflect.jvm.internal.impl.builtins.g.I0($this$getJavaAnalogue)) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.name.c fqName = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k($this$getJavaAnalogue);
        if (!fqName.f() || (x = this.i.x(fqName)) == null || (javaAnalogueFqName = x.b()) == null) {
            return null;
        }
        kotlin.jvm.internal.k.b(javaAnalogueFqName, "j2kClassMap.mapKotlinToJ…leFqName() ?: return null");
        kotlin.reflect.jvm.internal.impl.descriptors.e a2 = kotlin.reflect.jvm.internal.impl.descriptors.r.a(u(), javaAnalogueFqName, kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS);
        if (a2 instanceof kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f) {
            fVar = a2;
        }
        return fVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f4 A[SYNTHETIC] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.d> c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.e r27) {
        /*
            r26 = this;
            r0 = r26
            r1 = r27
            java.lang.String r2 = "classDescriptor"
            kotlin.jvm.internal.k.f(r1, r2)
            kotlin.reflect.jvm.internal.impl.descriptors.f r2 = r27.h()
            kotlin.reflect.jvm.internal.impl.descriptors.f r3 = kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS
            if (r2 != r3) goto L_0x0196
            boolean r2 = r26.v()
            if (r2 != 0) goto L_0x0019
            goto L_0x0196
        L_0x0019:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r2 = r26.r(r27)
            if (r2 == 0) goto L_0x0191
            kotlin.reflect.jvm.internal.impl.builtins.jvm.c r3 = r0.i
            kotlin.reflect.jvm.internal.impl.name.b r4 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(r2)
            kotlin.reflect.jvm.internal.impl.builtins.jvm.b$a r5 = kotlin.reflect.jvm.internal.impl.builtins.jvm.b.p
            kotlin.reflect.jvm.internal.impl.builtins.g r5 = r5.a()
            r6 = 0
            r7 = 4
            r8 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.e r3 = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.w(r3, r4, r5, r6, r7, r8)
            if (r3 == 0) goto L_0x018c
            kotlin.reflect.jvm.internal.impl.types.v0 r4 = kotlin.reflect.jvm.internal.impl.builtins.jvm.j.a(r3, r2)
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r4 = r4.c()
            kotlin.reflect.jvm.internal.impl.builtins.jvm.h$g r5 = new kotlin.reflect.jvm.internal.impl.builtins.jvm.h$g
            r5.<init>(r4)
            java.util.List r6 = r2.f()
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r9 = r6
            r10 = 0
            java.util.Iterator r11 = r9.iterator()
        L_0x0055:
            boolean r12 = r11.hasNext()
            java.lang.String r15 = "javaConstructor"
            if (r12 == 0) goto L_0x00fc
            java.lang.Object r12 = r11.next()
            r13 = r12
            kotlin.reflect.jvm.internal.impl.descriptors.d r13 = (kotlin.reflect.jvm.internal.impl.descriptors.d) r13
            r18 = 0
            kotlin.jvm.internal.k.b(r13, r15)
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r15 = r13.getVisibility()
            boolean r15 = r15.c()
            r19 = 1
            if (r15 == 0) goto L_0x00ea
            java.util.Collection r15 = r3.f()
            java.lang.String r14 = "defaultKotlinVersion.constructors"
            kotlin.jvm.internal.k.b(r15, r14)
            r14 = r15
            r15 = 0
            r21 = r3
            boolean r3 = r14 instanceof java.util.Collection
            if (r3 == 0) goto L_0x0095
            boolean r3 = r14.isEmpty()
            if (r3 == 0) goto L_0x0095
            r25 = r6
            r3 = r19
            goto L_0x00c3
        L_0x0095:
            java.util.Iterator r3 = r14.iterator()
        L_0x0099:
            boolean r22 = r3.hasNext()
            if (r22 == 0) goto L_0x00bf
            java.lang.Object r22 = r3.next()
            r23 = r3
            r3 = r22
            kotlin.reflect.jvm.internal.impl.descriptors.d r3 = (kotlin.reflect.jvm.internal.impl.descriptors.d) r3
            r24 = 0
            r25 = r6
            java.lang.String r6 = "it"
            kotlin.jvm.internal.k.b(r3, r6)
            boolean r3 = r5.invoke((kotlin.reflect.jvm.internal.impl.descriptors.l) r3, (kotlin.reflect.jvm.internal.impl.descriptors.l) r13)
            if (r3 == 0) goto L_0x00ba
            r3 = 0
            goto L_0x00c3
        L_0x00ba:
            r3 = r23
            r6 = r25
            goto L_0x0099
        L_0x00bf:
            r25 = r6
            r3 = r19
        L_0x00c3:
            if (r3 == 0) goto L_0x00ee
            boolean r3 = r0.x(r13, r1)
            if (r3 != 0) goto L_0x00ee
            boolean r3 = kotlin.reflect.jvm.internal.impl.builtins.g.n0(r13)
            if (r3 != 0) goto L_0x00ee
            java.util.Set<java.lang.String> r3 = f
            kotlin.reflect.jvm.internal.impl.load.kotlin.v r6 = kotlin.reflect.jvm.internal.impl.load.kotlin.v.a
            r0 = 0
            r14 = 0
            r15 = 3
            java.lang.String r14 = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c(r13, r0, r0, r15, r14)
            java.lang.String r0 = r6.l(r2, r14)
            boolean r0 = r3.contains(r0)
            if (r0 != 0) goto L_0x00ee
            r13 = r19
            goto L_0x00ef
        L_0x00ea:
            r21 = r3
            r25 = r6
        L_0x00ee:
            r13 = 0
        L_0x00ef:
            if (r13 == 0) goto L_0x00f4
            r8.add(r12)
        L_0x00f4:
            r0 = r26
            r3 = r21
            r6 = r25
            goto L_0x0055
        L_0x00fc:
            r21 = r3
            r25 = r6
            r0 = r8
            r3 = 0
            java.util.ArrayList r6 = new java.util.ArrayList
            r7 = 10
            int r7 = kotlin.collections.r.r(r0, r7)
            r6.<init>(r7)
            r7 = r0
            r8 = 0
            java.util.Iterator r9 = r7.iterator()
        L_0x0113:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x018a
            java.lang.Object r10 = r9.next()
            r11 = r10
            kotlin.reflect.jvm.internal.impl.descriptors.d r11 = (kotlin.reflect.jvm.internal.impl.descriptors.d) r11
            r12 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r13 = r11.r()
            r14 = r13
            r18 = 0
            r14.p(r1)
            r19 = r0
            kotlin.reflect.jvm.internal.impl.types.i0 r0 = r27.m()
            r14.l(r0)
            r14.k()
            kotlin.reflect.jvm.internal.impl.types.z0 r0 = r4.j()
            r14.g(r0)
            java.util.Set<java.lang.String> r0 = g
            kotlin.reflect.jvm.internal.impl.load.kotlin.v r1 = kotlin.reflect.jvm.internal.impl.load.kotlin.v.a
            kotlin.jvm.internal.k.b(r11, r15)
            r22 = r3
            r16 = r4
            r17 = r5
            r20 = r7
            r3 = 0
            r4 = 3
            r5 = 0
            java.lang.String r7 = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c(r11, r5, r5, r4, r3)
            java.lang.String r1 = r1.l(r2, r7)
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0168
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r0 = r26.t()
            r14.r(r0)
        L_0x0168:
            kotlin.reflect.jvm.internal.impl.descriptors.u r0 = r13.build()
            if (r0 == 0) goto L_0x0182
            kotlin.reflect.jvm.internal.impl.descriptors.d r0 = (kotlin.reflect.jvm.internal.impl.descriptors.d) r0
            r6.add(r0)
            r1 = r27
            r4 = r16
            r5 = r17
            r0 = r19
            r7 = r20
            r3 = r22
            goto L_0x0113
        L_0x0182:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor"
            r0.<init>(r1)
            throw r0
        L_0x018a:
            return r6
        L_0x018c:
            java.util.List r0 = kotlin.collections.q.g()
            return r0
        L_0x0191:
            java.util.List r0 = kotlin.collections.q.g()
            return r0
        L_0x0196:
            java.util.List r0 = kotlin.collections.q.g()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.jvm.h.c(kotlin.reflect.jvm.internal.impl.descriptors.e):java.util.Collection");
    }

    public boolean b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor, @NotNull n0 functionDescriptor) {
        kotlin.jvm.internal.k.f(classDescriptor, "classDescriptor");
        kotlin.jvm.internal.k.f(functionDescriptor, "functionDescriptor");
        kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f javaAnalogueClassDescriptor = r(classDescriptor);
        if (javaAnalogueClassDescriptor == null || !functionDescriptor.getAnnotations().I(kotlin.reflect.jvm.internal.impl.descriptors.deserialization.d.a())) {
            return true;
        }
        if (!v()) {
            return false;
        }
        String jvmDescriptor = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c(functionDescriptor, false, false, 3, (Object) null);
        kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g J0 = javaAnalogueClassDescriptor.R();
        kotlin.reflect.jvm.internal.impl.name.f name = functionDescriptor.getName();
        kotlin.jvm.internal.k.b(name, "functionDescriptor.name");
        Collection<n0> b2 = J0.b(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS);
        if ((b2 instanceof Collection) && b2.isEmpty()) {
            return false;
        }
        for (n0 it : b2) {
            if (kotlin.jvm.internal.k.a(kotlin.reflect.jvm.internal.impl.load.kotlin.t.c(it, false, false, 3, (Object) null), jvmDescriptor)) {
                return true;
            }
        }
        return false;
    }

    private final boolean x(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.l $this$isTrivialCopyConstructorFor, kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor) {
        if ($this$isTrivialCopyConstructorFor.g().size() == 1) {
            List<w0> g2 = $this$isTrivialCopyConstructorFor.g();
            kotlin.jvm.internal.k.b(g2, "valueParameters");
            Object q0 = kotlin.collections.y.q0(g2);
            kotlin.jvm.internal.k.b(q0, "valueParameters.single()");
            kotlin.reflect.jvm.internal.impl.descriptors.h c2 = ((w0) q0).getType().I0().c();
            if (kotlin.jvm.internal.k.a(c2 != null ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(c2) : null, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(classDescriptor))) {
                return true;
            }
        }
        return false;
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean j(@NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
            kotlin.jvm.internal.k.f(fqName, "fqName");
            if (i(fqName)) {
                return true;
            }
            kotlin.reflect.jvm.internal.impl.name.a javaClassId = c.m.x(fqName);
            if (javaClassId == null) {
                return false;
            }
            try {
                return Serializable.class.isAssignableFrom(Class.forName(javaClassId.b().b()));
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        /* access modifiers changed from: private */
        public final boolean i(kotlin.reflect.jvm.internal.impl.name.c fqName) {
            return kotlin.jvm.internal.k.a(fqName, kotlin.reflect.jvm.internal.impl.builtins.g.h.h) || kotlin.reflect.jvm.internal.impl.builtins.g.z0(fqName);
        }

        @NotNull
        public final Set<String> g() {
            return h.b;
        }

        @NotNull
        public final Set<String> f() {
            return h.c;
        }

        /* access modifiers changed from: private */
        public final Set<String> e() {
            v $this$signatures = v.a;
            Iterable<kotlin.reflect.jvm.internal.impl.resolve.jvm.d> $this$flatMapTo$iv = q.j(kotlin.reflect.jvm.internal.impl.resolve.jvm.d.BOOLEAN, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.CHAR);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (kotlin.reflect.jvm.internal.impl.resolve.jvm.d it : $this$flatMapTo$iv) {
                String b = it.getWrapperFqName().g().b();
                kotlin.jvm.internal.k.b(b, "it.wrapperFqName.shortName().asString()");
                kotlin.collections.v.x(linkedHashSet, $this$signatures.e(b, it.getJavaKeywordName() + "Value()" + it.getDesc()));
            }
            return linkedHashSet;
        }

        @NotNull
        public final Set<String> h() {
            return h.d;
        }

        /* access modifiers changed from: private */
        public final Set<String> d() {
            v $this$signatures = v.a;
            kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.BYTE;
            Iterable<kotlin.reflect.jvm.internal.impl.resolve.jvm.d> $this$flatMapTo$iv = q.j(kotlin.reflect.jvm.internal.impl.resolve.jvm.d.BOOLEAN, dVar, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.DOUBLE, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.FLOAT, dVar, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.INT, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.LONG, kotlin.reflect.jvm.internal.impl.resolve.jvm.d.SHORT);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (kotlin.reflect.jvm.internal.impl.resolve.jvm.d it : $this$flatMapTo$iv) {
                String b = it.getWrapperFqName().g().b();
                kotlin.jvm.internal.k.b(b, "it.wrapperFqName.shortName().asString()");
                String[] b2 = $this$signatures.b("Ljava/lang/String;");
                kotlin.collections.v.x(linkedHashSet, $this$signatures.e(b, (String[]) Arrays.copyOf(b2, b2.length)));
            }
            return linkedHashSet;
        }
    }

    static {
        Class<h> cls = h.class;
        a = new kotlin.reflect.k[]{kotlin.jvm.internal.a0.h(new kotlin.jvm.internal.u(kotlin.jvm.internal.a0.b(cls), "cloneableType", "getCloneableType()Lorg/jetbrains/kotlin/types/SimpleType;")), kotlin.jvm.internal.a0.h(new kotlin.jvm.internal.u(kotlin.jvm.internal.a0.b(cls), "notConsideredDeprecation", "getNotConsideredDeprecation()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;"))};
        a aVar = new a((DefaultConstructorMarker) null);
        h = aVar;
        v $this$signatures = v.a;
        c = p0.i(p0.i(p0.i(p0.i(p0.i(aVar.e(), $this$signatures.f("List", "sort(Ljava/util/Comparator;)V")), $this$signatures.e(SpInputType.STRING, "codePointAt(I)I", "codePointBefore(I)I", "codePointCount(II)I", "compareToIgnoreCase(Ljava/lang/String;)I", "concat(Ljava/lang/String;)Ljava/lang/String;", "contains(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/StringBuffer;)Z", "endsWith(Ljava/lang/String;)Z", "equalsIgnoreCase(Ljava/lang/String;)Z", "getBytes()[B", "getBytes(II[BI)V", "getBytes(Ljava/lang/String;)[B", "getBytes(Ljava/nio/charset/Charset;)[B", "getChars(II[CI)V", "indexOf(I)I", "indexOf(II)I", "indexOf(Ljava/lang/String;)I", "indexOf(Ljava/lang/String;I)I", "intern()Ljava/lang/String;", "isEmpty()Z", "lastIndexOf(I)I", "lastIndexOf(II)I", "lastIndexOf(Ljava/lang/String;)I", "lastIndexOf(Ljava/lang/String;I)I", "matches(Ljava/lang/String;)Z", "offsetByCodePoints(II)I", "regionMatches(ILjava/lang/String;II)Z", "regionMatches(ZILjava/lang/String;II)Z", "replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(CC)Ljava/lang/String;", "replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", "split(Ljava/lang/String;I)[Ljava/lang/String;", "split(Ljava/lang/String;)[Ljava/lang/String;", "startsWith(Ljava/lang/String;I)Z", "startsWith(Ljava/lang/String;)Z", "substring(II)Ljava/lang/String;", "substring(I)Ljava/lang/String;", "toCharArray()[C", "toLowerCase()Ljava/lang/String;", "toLowerCase(Ljava/util/Locale;)Ljava/lang/String;", "toUpperCase()Ljava/lang/String;", "toUpperCase(Ljava/util/Locale;)Ljava/lang/String;", "trim()Ljava/lang/String;", "isBlank()Z", "lines()Ljava/util/stream/Stream;", "repeat(I)Ljava/lang/String;")), $this$signatures.e("Double", "isInfinite()Z", "isNaN()Z")), $this$signatures.e(SpInputType.FLOAT, "isInfinite()Z", "isNaN()Z")), $this$signatures.e("Enum", "getDeclaringClass()Ljava/lang/Class;", "finalize()V"));
        v $this$signatures2 = v.a;
        d = p0.i(p0.i(p0.i(p0.i(p0.i(p0.i($this$signatures2.e("CharSequence", "codePoints()Ljava/util/stream/IntStream;", "chars()Ljava/util/stream/IntStream;"), $this$signatures2.f("Iterator", "forEachRemaining(Ljava/util/function/Consumer;)V")), $this$signatures2.e("Iterable", "forEach(Ljava/util/function/Consumer;)V", "spliterator()Ljava/util/Spliterator;")), $this$signatures2.e("Throwable", "setStackTrace([Ljava/lang/StackTraceElement;)V", "fillInStackTrace()Ljava/lang/Throwable;", "getLocalizedMessage()Ljava/lang/String;", "printStackTrace()V", "printStackTrace(Ljava/io/PrintStream;)V", "printStackTrace(Ljava/io/PrintWriter;)V", "getStackTrace()[Ljava/lang/StackTraceElement;", "initCause(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "getSuppressed()[Ljava/lang/Throwable;", "addSuppressed(Ljava/lang/Throwable;)V")), $this$signatures2.f("Collection", "spliterator()Ljava/util/Spliterator;", "parallelStream()Ljava/util/stream/Stream;", "stream()Ljava/util/stream/Stream;", "removeIf(Ljava/util/function/Predicate;)Z")), $this$signatures2.f("List", "replaceAll(Ljava/util/function/UnaryOperator;)V")), $this$signatures2.f(Constants.SERVICE_MAP, "getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "forEach(Ljava/util/function/BiConsumer;)V", "replaceAll(Ljava/util/function/BiFunction;)V", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"));
        v $this$signatures3 = v.a;
        e = p0.i(p0.i($this$signatures3.f("Collection", "removeIf(Ljava/util/function/Predicate;)Z"), $this$signatures3.f("List", "replaceAll(Ljava/util/function/UnaryOperator;)V", "sort(Ljava/util/Comparator;)V")), $this$signatures3.f(Constants.SERVICE_MAP, "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "remove(Ljava/lang/Object;Ljava/lang/Object;)Z", "replaceAll(Ljava/util/function/BiFunction;)V", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z"));
        v $this$signatures4 = v.a;
        Set a2 = aVar.d();
        String[] b2 = $this$signatures4.b("D");
        Set<T> i2 = p0.i(a2, $this$signatures4.e(SpInputType.FLOAT, (String[]) Arrays.copyOf(b2, b2.length)));
        String[] b3 = $this$signatures4.b("[C", "[CII", "[III", "[BIILjava/lang/String;", "[BIILjava/nio/charset/Charset;", "[BLjava/lang/String;", "[BLjava/nio/charset/Charset;", "[BII", "[B", "Ljava/lang/StringBuffer;", "Ljava/lang/StringBuilder;");
        f = p0.i(i2, $this$signatures4.e(SpInputType.STRING, (String[]) Arrays.copyOf(b3, b3.length)));
        v $this$signatures5 = v.a;
        String[] b4 = $this$signatures5.b("Ljava/lang/String;Ljava/lang/Throwable;ZZ");
        g = $this$signatures5.e("Throwable", (String[]) Arrays.copyOf(b4, b4.length));
    }
}
