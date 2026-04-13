package kotlin.reflect.jvm.internal.impl.descriptors;

import com.google.android.gms.common.internal.ImagesContract;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.s;
import kotlin.reflect.jvm.internal.impl.util.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Visibilities */
public class z0 {
    @NotNull
    public static final a1 a;
    @NotNull
    public static final a1 b;
    @NotNull
    public static final a1 c;
    @NotNull
    public static final a1 d;
    @NotNull
    public static final a1 e;
    @NotNull
    public static final a1 f;
    @NotNull
    public static final a1 g = new j("inherited", false);
    @NotNull
    public static final a1 h = new k("invisible_fake", false);
    @NotNull
    public static final a1 i = new l("unknown", false);
    public static final Set<a1> j;
    private static final Map<a1, Integer> k;
    public static final a1 l;
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d m = new a();
    public static final kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d n = new b();
    @Deprecated
    public static final kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d o = new c();
    /* access modifiers changed from: private */
    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.util.g p;

    private static /* synthetic */ void a(int i2) {
        Object[] objArr = new Object[3];
        switch (i2) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
                objArr[0] = "from";
                break;
            case 10:
            case 12:
                objArr[0] = "first";
                break;
            case 11:
            case 13:
                objArr[0] = "second";
                break;
            case 14:
                objArr[0] = "visibility";
                break;
            default:
                objArr[0] = "what";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities";
        switch (i2) {
            case 2:
            case 3:
                objArr[2] = "isVisibleIgnoringReceiver";
                break;
            case 4:
            case 5:
                objArr[2] = "isVisibleWithAnyReceiver";
                break;
            case 6:
            case 7:
                objArr[2] = "inSameFile";
                break;
            case 8:
            case 9:
                objArr[2] = "findInvisibleMember";
                break;
            case 10:
            case 11:
                objArr[2] = "compareLocal";
                break;
            case 12:
            case 13:
                objArr[2] = "compare";
                break;
            case 14:
                objArr[2] = "isPrivate";
                break;
            default:
                objArr[2] = "isVisible";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* compiled from: Visibilities */
    public static final class d extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "what";
                    break;
                case 2:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "descriptor";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$1";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "isVisible";
                    break;
                default:
                    objArr[2] = "hasContainingSourceFile";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        d(String x0, boolean x1) {
            super(x0, x1);
        }

        private boolean g(@NotNull m descriptor) {
            if (descriptor == null) {
                f(0);
            }
            return kotlin.reflect.jvm.internal.impl.resolve.c.j(descriptor) != p0.a;
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(1);
            }
            if (from == null) {
                f(2);
            }
            if (kotlin.reflect.jvm.internal.impl.resolve.c.J(what) && g(from)) {
                return z0.g(what, from);
            }
            if (what instanceof l) {
                i classDescriptor = ((l) what).b();
                if (kotlin.reflect.jvm.internal.impl.resolve.c.G(classDescriptor) && kotlin.reflect.jvm.internal.impl.resolve.c.J(classDescriptor) && (from instanceof l) && kotlin.reflect.jvm.internal.impl.resolve.c.J(from.b()) && z0.g(what, from)) {
                    return true;
                }
            }
            m parent = what;
            while (parent != null) {
                parent = parent.b();
                if (parent instanceof e) {
                    if (!kotlin.reflect.jvm.internal.impl.resolve.c.x(parent)) {
                        break;
                    }
                }
                if (parent instanceof b0) {
                    break;
                }
            }
            if (parent == null) {
                return false;
            }
            m fromParent = from;
            while (fromParent != null) {
                if (parent == fromParent) {
                    return true;
                }
                if (!(fromParent instanceof b0)) {
                    fromParent = fromParent.b();
                } else if (!(parent instanceof b0) || !((b0) parent).e().equals(((b0) fromParent).e()) || !kotlin.reflect.jvm.internal.impl.resolve.c.b(fromParent, parent)) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        Class<kotlin.reflect.jvm.internal.impl.util.g> cls = kotlin.reflect.jvm.internal.impl.util.g.class;
        d dVar = new d("private", false);
        a = dVar;
        e eVar = new e("private_to_this", false);
        b = eVar;
        f fVar = new f("protected", true);
        c = fVar;
        g gVar = new g("internal", false);
        d = gVar;
        h hVar = new h("public", true);
        e = hVar;
        i iVar = new i(ImagesContract.LOCAL, false);
        f = iVar;
        j = Collections.unmodifiableSet(o0.g(dVar, eVar, gVar, iVar));
        Map<Visibility, Integer> visibilities = kotlin.reflect.jvm.internal.impl.utils.a.e(4);
        visibilities.put(eVar, 0);
        visibilities.put(dVar, 0);
        visibilities.put(gVar, 1);
        visibilities.put(fVar, 1);
        visibilities.put(hVar, 2);
        k = Collections.unmodifiableMap(visibilities);
        l = hVar;
        Iterator<S> it = ServiceLoader.load(cls, cls.getClassLoader()).iterator();
        p = it.hasNext() ? (kotlin.reflect.jvm.internal.impl.util.g) it.next() : g.a.a;
    }

    /* compiled from: Visibilities */
    public static final class e extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$2";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        e(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d thisObject, @NotNull q what, @NotNull m from) {
            m classDescriptor;
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            if (z0.a.d(thisObject, what, from)) {
                if (thisObject == z0.n) {
                    return true;
                }
                if (!(thisObject == z0.m || (classDescriptor = kotlin.reflect.jvm.internal.impl.resolve.c.q(what, e.class)) == null || !(thisObject instanceof kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.f))) {
                    return ((kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.f) thisObject).q().a().equals(classDescriptor.a());
                }
            }
            return false;
        }

        @NotNull
        public String b() {
            return "private/*private to this*/";
        }
    }

    /* compiled from: Visibilities */
    public static final class f extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                case 2:
                    objArr[0] = "whatDeclaration";
                    break;
                case 3:
                    objArr[0] = "fromClass";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$3";
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "doesReceiverFitForProtectedVisibility";
                    break;
                default:
                    objArr[2] = "isVisible";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        f(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            e companionOwner;
            Class<e> cls = e.class;
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            e givenDescriptorContainingClass = (e) kotlin.reflect.jvm.internal.impl.resolve.c.q(what, cls);
            e fromClass = (e) kotlin.reflect.jvm.internal.impl.resolve.c.r(from, cls, false);
            if (fromClass == null) {
                return false;
            }
            if (givenDescriptorContainingClass != null && kotlin.reflect.jvm.internal.impl.resolve.c.x(givenDescriptorContainingClass) && (companionOwner = (e) kotlin.reflect.jvm.internal.impl.resolve.c.q(givenDescriptorContainingClass, cls)) != null && kotlin.reflect.jvm.internal.impl.resolve.c.H(fromClass, companionOwner)) {
                return true;
            }
            q whatDeclaration = kotlin.reflect.jvm.internal.impl.resolve.c.M(what);
            e classDescriptor = (e) kotlin.reflect.jvm.internal.impl.resolve.c.q(whatDeclaration, cls);
            if (classDescriptor == null) {
                return false;
            }
            if (!kotlin.reflect.jvm.internal.impl.resolve.c.H(fromClass, classDescriptor) || !g(receiver, whatDeclaration, fromClass)) {
                return d(receiver, what, fromClass.b());
            }
            return true;
        }

        private boolean g(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q whatDeclaration, @NotNull e fromClass) {
            if (whatDeclaration == null) {
                f(2);
            }
            if (fromClass == null) {
                f(3);
            }
            if (receiver == z0.o) {
                return false;
            }
            if (!(whatDeclaration instanceof b) || (whatDeclaration instanceof l) || receiver == z0.n) {
                return true;
            }
            if (receiver == z0.m || receiver == null) {
                return false;
            }
            b0 actualReceiverType = receiver instanceof kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.e ? ((kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.e) receiver).a() : receiver.getType();
            if (kotlin.reflect.jvm.internal.impl.resolve.c.I(actualReceiverType, fromClass) || s.a(actualReceiverType)) {
                return true;
            }
            return false;
        }
    }

    /* compiled from: Visibilities */
    public static final class g extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$4";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        g(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            if (!kotlin.reflect.jvm.internal.impl.resolve.c.g(from).H(kotlin.reflect.jvm.internal.impl.resolve.c.g(what))) {
                return false;
            }
            return z0.p.a(what, from);
        }
    }

    /* compiled from: Visibilities */
    public static final class h extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$5";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        h(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return true;
        }
    }

    /* compiled from: Visibilities */
    public static final class i extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$6";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        i(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            throw new IllegalStateException("This method shouldn't be invoked for LOCAL visibility");
        }
    }

    /* compiled from: Visibilities */
    public static final class j extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$7";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        j(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            throw new IllegalStateException("Visibility is unknown yet");
        }
    }

    /* compiled from: Visibilities */
    public static final class k extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$8";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        k(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return false;
        }
    }

    /* compiled from: Visibilities */
    public static final class l extends a1 {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$9";
            objArr[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        l(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d receiver, @NotNull q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return false;
        }
    }

    public static boolean i(@NotNull q what, @NotNull m from) {
        if (what == null) {
            a(2);
        }
        if (from == null) {
            a(3);
        }
        return f(n, what, from) == null;
    }

    public static boolean g(@NotNull m what, @NotNull m from) {
        if (what == null) {
            a(6);
        }
        if (from == null) {
            a(7);
        }
        p0 fromContainingFile = kotlin.reflect.jvm.internal.impl.resolve.c.j(from);
        if (fromContainingFile != p0.a) {
            return fromContainingFile.equals(kotlin.reflect.jvm.internal.impl.resolve.c.j(what));
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r1v11, types: [kotlin.reflect.jvm.internal.impl.descriptors.m] */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static kotlin.reflect.jvm.internal.impl.descriptors.q f(@org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d r3, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.q r4, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.m r5) {
        /*
            if (r4 != 0) goto L_0x0007
            r0 = 8
            a(r0)
        L_0x0007:
            if (r5 != 0) goto L_0x000e
            r0 = 9
            a(r0)
        L_0x000e:
            kotlin.reflect.jvm.internal.impl.descriptors.m r0 = r4.a()
            kotlin.reflect.jvm.internal.impl.descriptors.q r0 = (kotlin.reflect.jvm.internal.impl.descriptors.q) r0
        L_0x0014:
            if (r0 == 0) goto L_0x0033
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r1 = r0.getVisibility()
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r2 = f
            if (r1 == r2) goto L_0x0033
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r1 = r0.getVisibility()
            boolean r1 = r1.d(r3, r0, r5)
            if (r1 != 0) goto L_0x0029
            return r0
        L_0x0029:
            java.lang.Class<kotlin.reflect.jvm.internal.impl.descriptors.q> r1 = kotlin.reflect.jvm.internal.impl.descriptors.q.class
            kotlin.reflect.jvm.internal.impl.descriptors.m r1 = kotlin.reflect.jvm.internal.impl.resolve.c.q(r0, r1)
            r0 = r1
            kotlin.reflect.jvm.internal.impl.descriptors.q r0 = (kotlin.reflect.jvm.internal.impl.descriptors.q) r0
            goto L_0x0014
        L_0x0033:
            boolean r1 = r4 instanceof kotlin.reflect.jvm.internal.impl.descriptors.impl.h0
            if (r1 == 0) goto L_0x0045
            r1 = r4
            kotlin.reflect.jvm.internal.impl.descriptors.impl.h0 r1 = (kotlin.reflect.jvm.internal.impl.descriptors.impl.h0) r1
            kotlin.reflect.jvm.internal.impl.descriptors.d r1 = r1.O()
            kotlin.reflect.jvm.internal.impl.descriptors.q r1 = f(r3, r1, r5)
            if (r1 == 0) goto L_0x0045
            return r1
        L_0x0045:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.z0.f(kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d, kotlin.reflect.jvm.internal.impl.descriptors.q, kotlin.reflect.jvm.internal.impl.descriptors.m):kotlin.reflect.jvm.internal.impl.descriptors.q");
    }

    @Nullable
    static Integer e(@NotNull a1 first, @NotNull a1 second) {
        if (first == null) {
            a(10);
        }
        if (second == null) {
            a(11);
        }
        if (first == second) {
            return 0;
        }
        Map<a1, Integer> map = k;
        Integer firstIndex = map.get(first);
        Integer secondIndex = map.get(second);
        if (firstIndex == null || secondIndex == null || firstIndex.equals(secondIndex)) {
            return null;
        }
        return Integer.valueOf(firstIndex.intValue() - secondIndex.intValue());
    }

    @Nullable
    public static Integer d(@NotNull a1 first, @NotNull a1 second) {
        if (first == null) {
            a(12);
        }
        if (second == null) {
            a(13);
        }
        Integer result = first.a(second);
        if (result != null) {
            return result;
        }
        Integer oppositeResult = second.a(first);
        if (oppositeResult != null) {
            return Integer.valueOf(-oppositeResult.intValue());
        }
        return null;
    }

    /* compiled from: Visibilities */
    public static final class a implements kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d {
        a() {
        }

        @NotNull
        public b0 getType() {
            throw new IllegalStateException("This method should not be called");
        }
    }

    /* compiled from: Visibilities */
    public static final class b implements kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d {
        b() {
        }

        @NotNull
        public b0 getType() {
            throw new IllegalStateException("This method should not be called");
        }
    }

    /* compiled from: Visibilities */
    public static final class c implements kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d {
        c() {
        }

        @NotNull
        public b0 getType() {
            throw new IllegalStateException("This method should not be called");
        }
    }

    public static boolean h(@NotNull a1 visibility) {
        if (visibility == null) {
            a(14);
        }
        return visibility == a || visibility == b;
    }
}
