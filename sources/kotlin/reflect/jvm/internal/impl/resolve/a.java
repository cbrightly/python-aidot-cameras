package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.v;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.types.checker.g;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorEquivalenceForOverrides.kt */
public final class a {
    public static final a a = new a();

    /* compiled from: DescriptorEquivalenceForOverrides.kt */
    public static final class b implements g.a {
        final /* synthetic */ boolean a;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.a b;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.a c;

        b(boolean z, kotlin.reflect.jvm.internal.impl.descriptors.a aVar, kotlin.reflect.jvm.internal.impl.descriptors.a aVar2) {
            this.a = z;
            this.b = aVar;
            this.c = aVar2;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.a$b$a  reason: collision with other inner class name */
        /* compiled from: DescriptorEquivalenceForOverrides.kt */
        public static final class C0406a extends l implements p<m, m, Boolean> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0406a(b bVar) {
                super(2);
                this.this$0 = bVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(invoke((m) obj, (m) obj2));
            }

            public final boolean invoke(@Nullable m x, @Nullable m y) {
                return k.a(x, this.this$0.b) && k.a(y, this.this$0.c);
            }
        }

        /* renamed from: b */
        public final boolean a(@NotNull u0 c1, @NotNull u0 c2) {
            k.f(c1, "c1");
            k.f(c2, "c2");
            if (k.a(c1, c2)) {
                return true;
            }
            h d1 = c1.c();
            h d2 = c2.c();
            if (!(d1 instanceof t0) || !(d2 instanceof t0)) {
                return false;
            }
            return a.a.f((t0) d1, (t0) d2, this.a, new C0406a(this));
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.a$a  reason: collision with other inner class name */
    /* compiled from: DescriptorEquivalenceForOverrides.kt */
    public static final class C0405a extends l implements p<m, m, Boolean> {
        public static final C0405a INSTANCE = new C0405a();

        C0405a() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((m) obj, (m) obj2));
        }

        public final boolean invoke(@Nullable m $noName_0, @Nullable m $noName_1) {
            return false;
        }
    }

    /* compiled from: DescriptorEquivalenceForOverrides.kt */
    public static final class c extends l implements p<m, m, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((m) obj, (m) obj2));
        }

        public final boolean invoke(@Nullable m $noName_0, @Nullable m $noName_1) {
            return false;
        }
    }

    private a() {
    }

    public final boolean e(@Nullable m a2, @Nullable m b2, boolean allowCopiesFromTheSameDeclaration) {
        if ((a2 instanceof e) && (b2 instanceof e)) {
            return d((e) a2, (e) b2);
        }
        if ((a2 instanceof t0) && (b2 instanceof t0)) {
            return g(this, (t0) a2, (t0) b2, allowCopiesFromTheSameDeclaration, (p) null, 8, (Object) null);
        } else if ((a2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.a) && (b2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.a)) {
            return c(this, (kotlin.reflect.jvm.internal.impl.descriptors.a) a2, (kotlin.reflect.jvm.internal.impl.descriptors.a) b2, allowCopiesFromTheSameDeclaration, false, 8, (Object) null);
        } else if (!(a2 instanceof b0) || !(b2 instanceof b0)) {
            return k.a(a2, b2);
        } else {
            return k.a(((b0) a2).e(), ((b0) b2).e());
        }
    }

    private final boolean d(e a2, e b2) {
        return k.a(a2.i(), b2.i());
    }

    static /* synthetic */ boolean g(a aVar, t0 t0Var, t0 t0Var2, boolean z, p pVar, int i, Object obj) {
        if ((i & 8) != 0) {
            pVar = c.INSTANCE;
        }
        return aVar.f(t0Var, t0Var2, z, pVar);
    }

    /* access modifiers changed from: private */
    public final boolean f(t0 a2, t0 b2, boolean allowCopiesFromTheSameDeclaration, p<? super m, ? super m, Boolean> equivalentCallables) {
        if (k.a(a2, b2)) {
            return true;
        }
        if (k.a(a2.b(), b2.b()) || !h(a2, b2, equivalentCallables, allowCopiesFromTheSameDeclaration)) {
            return false;
        }
        if (a2.getIndex() == b2.getIndex()) {
            return true;
        }
        return false;
    }

    private final o0 i(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a $this$singleSource) {
        while (($this$singleSource instanceof kotlin.reflect.jvm.internal.impl.descriptors.b) && ((kotlin.reflect.jvm.internal.impl.descriptors.b) $this$singleSource).h() == b.a.FAKE_OVERRIDE) {
            Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> d = ((kotlin.reflect.jvm.internal.impl.descriptors.b) $this$singleSource).d();
            k.b(d, "overriddenDescriptors");
            kotlin.reflect.jvm.internal.impl.descriptors.b bVar = (kotlin.reflect.jvm.internal.impl.descriptors.b) y.r0(d);
            if (bVar == null) {
                return null;
            }
            $this$singleSource = bVar;
        }
        return $this$singleSource.n();
    }

    public static /* synthetic */ boolean c(a aVar, kotlin.reflect.jvm.internal.impl.descriptors.a aVar2, kotlin.reflect.jvm.internal.impl.descriptors.a aVar3, boolean z, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        return aVar.b(aVar2, aVar3, z, z2);
    }

    public final boolean b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a a2, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a b2, boolean allowCopiesFromTheSameDeclaration, boolean ignoreReturnType) {
        k.f(a2, "a");
        k.f(b2, "b");
        if (k.a(a2, b2)) {
            return true;
        }
        if (!k.a(a2.getName(), b2.getName())) {
            return false;
        }
        if (k.a(a2.b(), b2.b())) {
            if (!allowCopiesFromTheSameDeclaration || (!k.a(i(a2), i(b2)))) {
                return false;
            }
            if ((a2 instanceof v) && (b2 instanceof v) && ((v) a2).d0() != ((v) b2).d0()) {
                return false;
            }
        }
        if (c.E(a2) || c.E(b2) || !h(a2, b2, C0405a.INSTANCE, allowCopiesFromTheSameDeclaration)) {
            return false;
        }
        i overridingUtil = i.m(new b(allowCopiesFromTheSameDeclaration, a2, b2));
        k.b(overridingUtil, "OverridingUtil.createWit…= a && y == b }\n        }");
        i.j F = overridingUtil.F(a2, b2, (e) null, !ignoreReturnType);
        k.b(F, "overridingUtil.isOverrid… null, !ignoreReturnType)");
        i.j.a c2 = F.c();
        i.j.a aVar = i.j.a.OVERRIDABLE;
        if (c2 == aVar) {
            i.j F2 = overridingUtil.F(b2, a2, (e) null, !ignoreReturnType);
            k.b(F2, "overridingUtil.isOverrid… null, !ignoreReturnType)");
            if (F2.c() == aVar) {
                return true;
            }
        }
        return false;
    }

    private final boolean h(m a2, m b2, p<? super m, ? super m, Boolean> equivalentCallables, boolean allowCopiesFromTheSameDeclaration) {
        m aOwner = a2.b();
        m bOwner = b2.b();
        if ((aOwner instanceof kotlin.reflect.jvm.internal.impl.descriptors.b) || (bOwner instanceof kotlin.reflect.jvm.internal.impl.descriptors.b)) {
            return equivalentCallables.invoke(aOwner, bOwner).booleanValue();
        }
        return e(aOwner, bOwner, allowCopiesFromTheSameDeclaration);
    }
}
