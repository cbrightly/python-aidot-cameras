package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.g;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.f;
import kotlin.reflect.j;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.b;
import kotlin.reflect.jvm.internal.c;
import kotlin.reflect.jvm.internal.calls.a;
import kotlin.reflect.jvm.internal.calls.d;
import kotlin.reflect.jvm.internal.calls.e;
import kotlin.reflect.jvm.internal.calls.h;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KFunctionImpl.kt */
public final class k extends e<Object> implements g<Object>, f<Object>, b {
    static final /* synthetic */ kotlin.reflect.k[] x;
    @NotNull
    private final j a1;
    private final Object a2;
    @Nullable
    private final a0.b p0;
    /* access modifiers changed from: private */
    public final String p1;
    @NotNull
    private final a0.a y;
    @NotNull
    private final a0.b z;

    static {
        Class<k> cls = k.class;
        x = new kotlin.reflect.k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "defaultCaller", "getDefaultCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
    }

    @NotNull
    /* renamed from: F */
    public kotlin.reflect.jvm.internal.impl.descriptors.u s() {
        return (kotlin.reflect.jvm.internal.impl.descriptors.u) this.y.b(this, x[0]);
    }

    @NotNull
    public d<?> p() {
        return (d) this.z.b(this, x[1]);
    }

    @Nullable
    public d<?> r() {
        return (d) this.p0.b(this, x[2]);
    }

    private k(j container, String name, String signature, kotlin.reflect.jvm.internal.impl.descriptors.u descriptorInitialValue, Object rawBoundReceiver) {
        this.a1 = container;
        this.p1 = signature;
        this.a2 = rawBoundReceiver;
        this.y = a0.c(descriptorInitialValue, new c(this, name));
        this.z = a0.b(new a(this));
        this.p0 = a0.b(new b(this));
    }

    @Nullable
    public Object invoke() {
        return b.a.a(this);
    }

    @Nullable
    public Object invoke(@Nullable Object p12) {
        return b.a.b(this, p12);
    }

    @Nullable
    public Object invoke(@Nullable Object p12, @Nullable Object p2) {
        return b.a.c(this, p12, p2);
    }

    @Nullable
    public Object invoke(@Nullable Object p12, @Nullable Object p2, @Nullable Object p3) {
        return b.a.d(this, p12, p2, p3);
    }

    @Nullable
    public Object invoke(@Nullable Object p12, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4) {
        return b.a.e(this, p12, p2, p3, p4);
    }

    @NotNull
    public j q() {
        return this.a1;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    /* synthetic */ k(j jVar, String str, String str2, kotlin.reflect.jvm.internal.impl.descriptors.u uVar, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jVar, str, str2, uVar, (i & 16) != 0 ? kotlin.jvm.internal.c.NO_RECEIVER : obj);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public k(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        this(container, name, signature, (kotlin.reflect.jvm.internal.impl.descriptors.u) null, boundReceiver);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(signature, "signature");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public k(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.j r10, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.u r11) {
        /*
            r9 = this;
            java.lang.String r0 = "container"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.String r0 = "descriptor"
            kotlin.jvm.internal.k.f(r11, r0)
            kotlin.reflect.jvm.internal.impl.name.f r0 = r11.getName()
            java.lang.String r3 = r0.b()
            java.lang.String r0 = "descriptor.name.asString()"
            kotlin.jvm.internal.k.b(r3, r0)
            kotlin.reflect.jvm.internal.e0 r0 = kotlin.reflect.jvm.internal.e0.b
            kotlin.reflect.jvm.internal.c r0 = r0.g(r11)
            java.lang.String r4 = r0.a()
            r6 = 0
            r7 = 16
            r8 = 0
            r1 = r9
            r2 = r10
            r5 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.k.<init>(kotlin.reflect.jvm.internal.j, kotlin.reflect.jvm.internal.impl.descriptors.u):void");
    }

    public boolean u() {
        return !kotlin.jvm.internal.k.a(this.a2, kotlin.jvm.internal.c.NO_RECEIVER);
    }

    /* compiled from: KFunctionImpl.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.u> {
        final /* synthetic */ String $name;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar, String str) {
            super(0);
            this.this$0 = kVar;
            this.$name = str;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.u invoke() {
            return this.this$0.q().s(this.$name, this.this$0.p1);
        }
    }

    @NotNull
    public String getName() {
        String b2 = s().getName().b();
        kotlin.jvm.internal.k.b(b2, "descriptor.name.asString()");
        return b2;
    }

    /* compiled from: KFunctionImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<d<? extends Member>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final d<Member> invoke() {
            Member member;
            d dVar;
            c jvmSignature = e0.b.g(this.this$0.s());
            if (jvmSignature instanceof c.d) {
                if (this.this$0.t()) {
                    Class<?> b = this.this$0.q().b();
                    Iterable<j> $this$mapTo$iv$iv = this.this$0.getParameters();
                    ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (j it : $this$mapTo$iv$iv) {
                        String name = it.getName();
                        if (name == null) {
                            kotlin.jvm.internal.k.n();
                        }
                        arrayList.add(name);
                    }
                    return new kotlin.reflect.jvm.internal.calls.a(b, arrayList, a.C0330a.POSITIONAL_CALL, a.b.KOTLIN, (List) null, 16, (DefaultConstructorMarker) null);
                }
                member = this.this$0.q().p(((c.d) jvmSignature).b());
            } else if (jvmSignature instanceof c.e) {
                member = this.this$0.q().t(((c.e) jvmSignature).c(), ((c.e) jvmSignature).b());
            } else if (jvmSignature instanceof c.C0329c) {
                member = ((c.C0329c) jvmSignature).b();
            } else if (jvmSignature instanceof c.b) {
                member = ((c.b) jvmSignature).b();
            } else if (jvmSignature instanceof c.a) {
                Iterable b2 = ((c.a) jvmSignature).b();
                Class<?> b3 = this.this$0.q().b();
                Iterable<Method> $this$map$iv = b2;
                ArrayList arrayList2 = new ArrayList(r.r($this$map$iv, 10));
                for (Method it2 : $this$map$iv) {
                    kotlin.jvm.internal.k.b(it2, "it");
                    arrayList2.add(it2.getName());
                }
                return new kotlin.reflect.jvm.internal.calls.a(b3, arrayList2, a.C0330a.POSITIONAL_CALL, a.b.JAVA, b2);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            if (member instanceof Constructor) {
                k kVar = this.this$0;
                dVar = kVar.A((Constructor) member, kVar.s());
            } else if (!(member instanceof Method)) {
                throw new y("Could not compute caller for function: " + this.this$0.s() + " (member = " + member + ')');
            } else if (!Modifier.isStatic(((Method) member).getModifiers())) {
                dVar = this.this$0.B((Method) member);
            } else if (this.this$0.s().getAnnotations().c(h0.g()) != null) {
                dVar = this.this$0.C((Method) member);
            } else {
                dVar = this.this$0.D((Method) member);
            }
            return h.c(dVar, this.this$0.s(), false, 2, (Object) null);
        }
    }

    /* compiled from: KFunctionImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<d<? extends Member>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @Nullable
        public final d<Member> invoke() {
            GenericDeclaration genericDeclaration;
            d dVar;
            c jvmSignature = e0.b.g(this.this$0.s());
            if (jvmSignature instanceof c.e) {
                j q = this.this$0.q();
                String c = ((c.e) jvmSignature).c();
                String b = ((c.e) jvmSignature).b();
                Member b2 = this.this$0.p().b();
                if (b2 == null) {
                    kotlin.jvm.internal.k.n();
                }
                genericDeclaration = q.r(c, b, !Modifier.isStatic(b2.getModifiers()));
            } else if (jvmSignature instanceof c.d) {
                if (this.this$0.t()) {
                    Class<?> b3 = this.this$0.q().b();
                    Iterable<j> $this$mapTo$iv$iv = this.this$0.getParameters();
                    ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (j it : $this$mapTo$iv$iv) {
                        String name = it.getName();
                        if (name == null) {
                            kotlin.jvm.internal.k.n();
                        }
                        arrayList.add(name);
                    }
                    return new kotlin.reflect.jvm.internal.calls.a(b3, arrayList, a.C0330a.CALL_BY_NAME, a.b.KOTLIN, (List) null, 16, (DefaultConstructorMarker) null);
                }
                genericDeclaration = this.this$0.q().q(((c.d) jvmSignature).b());
            } else if (jvmSignature instanceof c.a) {
                Iterable b4 = ((c.a) jvmSignature).b();
                Class<?> b5 = this.this$0.q().b();
                Iterable $this$map$iv = b4;
                ArrayList arrayList2 = new ArrayList(r.r($this$map$iv, 10));
                for (Method it2 : $this$map$iv) {
                    kotlin.jvm.internal.k.b(it2, "it");
                    arrayList2.add(it2.getName());
                }
                return new kotlin.reflect.jvm.internal.calls.a(b5, arrayList2, a.C0330a.CALL_BY_NAME, a.b.JAVA, b4);
            } else {
                genericDeclaration = null;
            }
            if (genericDeclaration instanceof Constructor) {
                k kVar = this.this$0;
                dVar = kVar.A((Constructor) genericDeclaration, kVar.s());
            } else if (genericDeclaration instanceof Method) {
                if (this.this$0.s().getAnnotations().c(h0.g()) != null) {
                    m b6 = this.this$0.s().b();
                    if (b6 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    } else if (!((e) b6).V()) {
                        dVar = this.this$0.C((Method) genericDeclaration);
                    }
                }
                dVar = this.this$0.D((Method) genericDeclaration);
            } else {
                dVar = null;
            }
            if (dVar != null) {
                return h.b(dVar, this.this$0.s(), true);
            }
            return null;
        }
    }

    private final Object E() {
        return h.a(this.a2, s());
    }

    /* access modifiers changed from: private */
    public final e.h D(Method member) {
        return u() ? new e.h.c(member, E()) : new e.h.f(member);
    }

    /* access modifiers changed from: private */
    public final e.h C(Method member) {
        return u() ? new e.h.b(member) : new e.h.C0335e(member);
    }

    /* access modifiers changed from: private */
    public final e.h B(Method member) {
        return u() ? new e.h.a(member, E()) : new e.h.d(member);
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.calls.e<Constructor<?>> A(Constructor<?> member, kotlin.reflect.jvm.internal.impl.descriptors.u descriptor) {
        if (kotlin.reflect.jvm.internal.impl.resolve.jvm.a.f(descriptor)) {
            if (u()) {
                return new e.a(member, E());
            }
            return new e.b(member);
        } else if (u()) {
            return new e.c(member, E());
        } else {
            return new e.C0332e(member);
        }
    }

    public int getArity() {
        return kotlin.reflect.jvm.internal.calls.f.a(p());
    }

    public boolean isInline() {
        return s().isInline();
    }

    public boolean isExternal() {
        return s().isExternal();
    }

    public boolean isOperator() {
        return s().isOperator();
    }

    public boolean isInfix() {
        return s().isInfix();
    }

    public boolean isSuspend() {
        return s().isSuspend();
    }

    public boolean equals(@Nullable Object other) {
        k that = h0.b(other);
        if (that == null || !kotlin.jvm.internal.k.a(q(), that.q()) || !kotlin.jvm.internal.k.a(getName(), that.getName()) || !kotlin.jvm.internal.k.a(this.p1, that.p1) || !kotlin.jvm.internal.k.a(this.a2, that.a2)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((q().hashCode() * 31) + getName().hashCode()) * 31) + this.p1.hashCode();
    }

    @NotNull
    public String toString() {
        return d0.b.d(s());
    }
}
