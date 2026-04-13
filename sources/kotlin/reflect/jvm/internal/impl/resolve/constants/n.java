package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.a1;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IntegerLiteralTypeConstructor.kt */
public final class n implements u0 {
    public static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public final long b;
    /* access modifiers changed from: private */
    public final y c;
    @NotNull
    private final Set<b0> d;
    /* access modifiers changed from: private */
    public final i0 e;
    private final g f;

    private final List<b0> l() {
        return (List) this.f.getValue();
    }

    /* compiled from: IntegerLiteralTypeConstructor.kt */
    public static final class a {

        /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.constants.n$a$a  reason: collision with other inner class name */
        /* compiled from: IntegerLiteralTypeConstructor.kt */
        public enum C0407a {
            COMMON_SUPER_TYPE,
            INTERSECTION_TYPE
        }

        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final i0 b(@NotNull Collection<? extends i0> types) {
            k.f(types, "types");
            return a(types, C0407a.INTERSECTION_TYPE);
        }

        private final i0 a(Collection<? extends i0> types, C0407a mode) {
            if (types.isEmpty()) {
                return null;
            }
            Iterator iterator$iv = types.iterator();
            if (iterator$iv.hasNext()) {
                T $this$reduce$iv = iterator$iv.next();
                while (iterator$iv.hasNext()) {
                    a aVar = n.a;
                    $this$reduce$iv = aVar.e((i0) $this$reduce$iv, (i0) iterator$iv.next(), mode);
                }
                return (i0) $this$reduce$iv;
            }
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }

        private final i0 e(i0 left, i0 right, C0407a mode) {
            if (left == null || right == null) {
                return null;
            }
            u0 leftConstructor = left.I0();
            u0 rightConstructor = right.I0();
            if ((leftConstructor instanceof n) && (rightConstructor instanceof n)) {
                return c((n) leftConstructor, (n) rightConstructor, mode);
            }
            if (leftConstructor instanceof n) {
                return d((n) leftConstructor, right);
            }
            if (rightConstructor instanceof n) {
                return d((n) rightConstructor, left);
            }
            return null;
        }

        private final i0 c(n left, n right, C0407a mode) {
            Set possibleTypes;
            switch (o.a[mode.ordinal()]) {
                case 1:
                    possibleTypes = kotlin.collections.y.X(left.k(), right.k());
                    break;
                case 2:
                    possibleTypes = kotlin.collections.y.I0(left.k(), right.k());
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            return c0.e(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), new n(left.b, left.c, possibleTypes, (DefaultConstructorMarker) null), false);
        }

        private final i0 d(n left, i0 right) {
            if (left.k().contains(right)) {
                return right;
            }
            return null;
        }
    }

    public /* synthetic */ n(long value, y module, Set possibleTypes, DefaultConstructorMarker $constructor_marker) {
        this(value, module, possibleTypes);
    }

    @NotNull
    public final Set<b0> k() {
        return this.d;
    }

    private n(long value, y module, Set<? extends b0> possibleTypes) {
        this.e = c0.e(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), this, false);
        this.f = i.b(new b(this));
        this.b = value;
        this.c = module;
        this.d = possibleTypes;
    }

    /* access modifiers changed from: private */
    public final boolean m() {
        Collection<b0> a2 = u.a(this.c);
        if ((a2 instanceof Collection) && a2.isEmpty()) {
            return true;
        }
        for (b0 it : a2) {
            if (!(!this.d.contains(it))) {
                return false;
            }
        }
        return true;
    }

    /* compiled from: IntegerLiteralTypeConstructor.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<List<i0>> {
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(n nVar) {
            super(0);
            this.this$0 = nVar;
        }

        @NotNull
        public final List<i0> invoke() {
            e x = this.this$0.j().x();
            k.b(x, "builtIns.comparable");
            i0 m = x.m();
            k.b(m, "builtIns.comparable.defaultType");
            List result = q.m(a1.e(m, p.b(new y0(h1.IN_VARIANCE, this.this$0.e)), (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null, 2, (Object) null));
            if (!this.this$0.m()) {
                result.add(this.this$0.j().N());
            }
            return result;
        }
    }

    @NotNull
    public List<t0> getParameters() {
        return q.g();
    }

    @NotNull
    public Collection<b0> b() {
        return l();
    }

    public boolean d() {
        return false;
    }

    @Nullable
    public h c() {
        return null;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.builtins.g j() {
        return this.c.j();
    }

    @NotNull
    public u0 a(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @NotNull
    public String toString() {
        return "IntegerLiteralType" + n();
    }

    public final boolean i(@NotNull u0 constructor) {
        k.f(constructor, "constructor");
        Set<b0> set = this.d;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        for (b0 it : set) {
            if (k.a(it.I0(), constructor)) {
                return true;
            }
        }
        return false;
    }

    /* compiled from: IntegerLiteralTypeConstructor.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<b0, String> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull b0 it) {
            k.f(it, "it");
            return it.toString();
        }
    }

    private final String n() {
        return '[' + kotlin.collections.y.b0(this.d, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, c.INSTANCE, 30, (Object) null) + ']';
    }
}
