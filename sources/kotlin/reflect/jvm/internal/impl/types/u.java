package kotlin.reflect.jvm.internal.impl.types;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ErrorUtils */
public class u {
    private static final y a = new a();
    private static final c b = new c(kotlin.reflect.jvm.internal.impl.name.f.k("<ERROR CLASS>"));
    public static final i0 c = j("<LOOP IN SUPERTYPES>");
    private static final b0 d = j("<ERROR PROPERTY TYPE>");
    private static final i0 e;
    /* access modifiers changed from: private */
    public static final Set<i0> f;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 6:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 6:
            case 19:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 7:
            case 11:
            case 15:
                objArr[0] = "debugMessage";
                break;
            case 4:
            case 6:
            case 19:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils";
                break;
            case 5:
                objArr[0] = "ownerScope";
                break;
            case 8:
            case 9:
            case 16:
            case 17:
                objArr[0] = "debugName";
                break;
            case 10:
                objArr[0] = "typeConstructor";
                break;
            case 12:
            case 14:
                objArr[0] = "arguments";
                break;
            case 13:
                objArr[0] = "presentableName";
                break;
            case 18:
                objArr[0] = "errorClass";
                break;
            case 20:
                objArr[0] = "typeParameterDescriptor";
                break;
            default:
                objArr[0] = "function";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "createErrorProperty";
                break;
            case 6:
                objArr[1] = "createErrorFunction";
                break;
            case 19:
                objArr[1] = "getErrorModule";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "createErrorClass";
                break;
            case 2:
            case 3:
                objArr[2] = "createErrorScope";
                break;
            case 4:
            case 6:
            case 19:
                break;
            case 5:
                objArr[2] = "createErrorFunction";
                break;
            case 7:
                objArr[2] = "createErrorType";
                break;
            case 8:
                objArr[2] = "createErrorTypeWithCustomDebugName";
                break;
            case 9:
            case 10:
                objArr[2] = "createErrorTypeWithCustomConstructor";
                break;
            case 11:
            case 12:
                objArr[2] = "createErrorTypeWithArguments";
                break;
            case 13:
            case 14:
                objArr[2] = "createUnresolvedType";
                break;
            case 15:
                objArr[2] = "createErrorTypeConstructor";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "createErrorTypeConstructorWithCustomDebugName";
                break;
            case 20:
                objArr[2] = "createUninferredParameterType";
                break;
            default:
                objArr[2] = "containsErrorTypeInParameters";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 6:
            case 19:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* compiled from: ErrorUtils */
    public static final class a implements y {
        private static /* synthetic */ void u(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$1";
                    break;
                case 2:
                case 7:
                    objArr[0] = "fqName";
                    break;
                case 3:
                    objArr[0] = "nameFilter";
                    break;
                case 10:
                    objArr[0] = "visitor";
                    break;
                case 11:
                    objArr[0] = "targetModule";
                    break;
                default:
                    objArr[0] = "capability";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "getAnnotations";
                    break;
                case 4:
                    objArr[1] = "getSubPackagesOf";
                    break;
                case 5:
                    objArr[1] = "getName";
                    break;
                case 6:
                    objArr[1] = "getStableName";
                    break;
                case 8:
                    objArr[1] = "getAllDependencyModules";
                    break;
                case 9:
                    objArr[1] = "getExpectedByModules";
                    break;
                case 12:
                    objArr[1] = "getOriginal";
                    break;
                case 13:
                    objArr[1] = "getBuiltIns";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$1";
                    break;
            }
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                    break;
                case 2:
                case 3:
                    objArr[2] = "getSubPackagesOf";
                    break;
                case 7:
                    objArr[2] = "getPackage";
                    break;
                case 10:
                    objArr[2] = "accept";
                    break;
                case 11:
                    objArr[2] = "shouldSeeInternalsOf";
                    break;
                default:
                    objArr[2] = "getCapability";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        a() {
        }

        @Nullable
        public <T> T i0(@NotNull y.a<T> capability) {
            if (capability != null) {
                return null;
            }
            u(0);
            return null;
        }

        @NotNull
        public g getAnnotations() {
            g b = g.b.b();
            if (b == null) {
                u(1);
            }
            return b;
        }

        @NotNull
        public Collection<kotlin.reflect.jvm.internal.impl.name.b> k(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            if (fqName == null) {
                u(2);
            }
            if (nameFilter == null) {
                u(3);
            }
            List g = q.g();
            if (g == null) {
                u(4);
            }
            return g;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.name.f getName() {
            kotlin.reflect.jvm.internal.impl.name.f k = kotlin.reflect.jvm.internal.impl.name.f.k("<ERROR MODULE>");
            if (k == null) {
                u(5);
            }
            return k;
        }

        @NotNull
        public e0 e0(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            if (fqName == null) {
                u(7);
            }
            throw new IllegalStateException("Should not be called!");
        }

        @NotNull
        public List<y> u0() {
            List<y> g = q.g();
            if (g == null) {
                u(9);
            }
            return g;
        }

        public <R, D> R w(@NotNull o<R, D> visitor, D d) {
            if (visitor != null) {
                return null;
            }
            u(10);
            return null;
        }

        public boolean H(@NotNull y targetModule) {
            if (targetModule != null) {
                return false;
            }
            u(11);
            return false;
        }

        @NotNull
        public m a() {
            return this;
        }

        @Nullable
        public m b() {
            return null;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            kotlin.reflect.jvm.internal.impl.builtins.d L0 = kotlin.reflect.jvm.internal.impl.builtins.d.L0();
            if (L0 == null) {
                u(13);
            }
            return L0;
        }
    }

    static {
        b0 g = g();
        e = g;
        f = Collections.singleton(g);
    }

    /* compiled from: ErrorUtils */
    public static class d implements h {
        private final String b;

        private static /* synthetic */ void g(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 18:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 18:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 8:
                case 14:
                case 19:
                    objArr[0] = "name";
                    break;
                case 2:
                case 4:
                case 6:
                case 9:
                case 15:
                    objArr[0] = FirebaseAnalytics.Param.LOCATION;
                    break;
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 18:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorScope";
                    break;
                case 16:
                    objArr[0] = "kindFilter";
                    break;
                case 17:
                    objArr[0] = "nameFilter";
                    break;
                case 20:
                    objArr[0] = "p";
                    break;
                default:
                    objArr[0] = "debugMessage";
                    break;
            }
            switch (i) {
                case 7:
                    objArr[1] = "getContributedVariables";
                    break;
                case 10:
                    objArr[1] = "getContributedFunctions";
                    break;
                case 11:
                    objArr[1] = "getFunctionNames";
                    break;
                case 12:
                    objArr[1] = "getVariableNames";
                    break;
                case 13:
                    objArr[1] = "getClassifierNames";
                    break;
                case 18:
                    objArr[1] = "getContributedDescriptors";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorScope";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "getContributedClassifier";
                    break;
                case 3:
                case 4:
                    objArr[2] = "getContributedClassifierIncludeDeprecated";
                    break;
                case 5:
                case 6:
                    objArr[2] = "getContributedVariables";
                    break;
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 18:
                    break;
                case 8:
                case 9:
                    objArr[2] = "getContributedFunctions";
                    break;
                case 14:
                case 15:
                    objArr[2] = "recordLookup";
                    break;
                case 16:
                case 17:
                    objArr[2] = "getContributedDescriptors";
                    break;
                case 19:
                    objArr[2] = "definitelyDoesNotContainName";
                    break;
                case 20:
                    objArr[2] = "printScopeStructure";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 18:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* synthetic */ d(String x0, a x1) {
            this(x0);
        }

        private d(@NotNull String debugMessage) {
            if (debugMessage == null) {
                g(0);
            }
            this.b = debugMessage;
        }

        @Nullable
        public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(1);
            }
            if (location == null) {
                g(2);
            }
            return u.e(name.b());
        }

        @NotNull
        /* renamed from: i */
        public Set<? extends i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(5);
            }
            if (location == null) {
                g(6);
            }
            Set<? extends i0> b2 = u.f;
            if (b2 == null) {
                g(7);
            }
            return b2;
        }

        @NotNull
        /* renamed from: h */
        public Set<? extends n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(8);
            }
            if (location == null) {
                g(9);
            }
            Set<? extends n0> singleton = Collections.singleton(u.f(this));
            if (singleton == null) {
                g(10);
            }
            return singleton;
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
            Set<kotlin.reflect.jvm.internal.impl.name.f> emptySet = Collections.emptySet();
            if (emptySet == null) {
                g(11);
            }
            return emptySet;
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
            Set<kotlin.reflect.jvm.internal.impl.name.f> emptySet = Collections.emptySet();
            if (emptySet == null) {
                g(12);
            }
            return emptySet;
        }

        @NotNull
        public Collection<m> d(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            if (kindFilter == null) {
                g(16);
            }
            if (nameFilter == null) {
                g(17);
            }
            List emptyList = Collections.emptyList();
            if (emptyList == null) {
                g(18);
            }
            return emptyList;
        }

        public String toString() {
            return "ErrorScope{" + this.b + '}';
        }
    }

    /* compiled from: ErrorUtils */
    public static class e implements h {
        private final String b;

        private static /* synthetic */ void g(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 11:
                case 13:
                    objArr[0] = "name";
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                case 12:
                    objArr[0] = FirebaseAnalytics.Param.LOCATION;
                    break;
                case 9:
                    objArr[0] = "kindFilter";
                    break;
                case 10:
                    objArr[0] = "nameFilter";
                    break;
                case 14:
                    objArr[0] = "p";
                    break;
                default:
                    objArr[0] = "message";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ThrowingScope";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "getContributedClassifier";
                    break;
                case 3:
                case 4:
                    objArr[2] = "getContributedClassifierIncludeDeprecated";
                    break;
                case 5:
                case 6:
                    objArr[2] = "getContributedVariables";
                    break;
                case 7:
                case 8:
                    objArr[2] = "getContributedFunctions";
                    break;
                case 9:
                case 10:
                    objArr[2] = "getContributedDescriptors";
                    break;
                case 11:
                case 12:
                    objArr[2] = "recordLookup";
                    break;
                case 13:
                    objArr[2] = "definitelyDoesNotContainName";
                    break;
                case 14:
                    objArr[2] = "printScopeStructure";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* synthetic */ e(String x0, a x1) {
            this(x0);
        }

        private e(@NotNull String message) {
            if (message == null) {
                g(0);
            }
            this.b = message;
        }

        @Nullable
        public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(1);
            }
            if (location == null) {
                g(2);
            }
            throw new IllegalStateException(this.b + ", required name: " + name);
        }

        @NotNull
        public Collection<? extends i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(5);
            }
            if (location == null) {
                g(6);
            }
            throw new IllegalStateException(this.b + ", required name: " + name);
        }

        @NotNull
        public Collection<? extends n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(7);
            }
            if (location == null) {
                g(8);
            }
            throw new IllegalStateException(this.b + ", required name: " + name);
        }

        @NotNull
        public Collection<m> d(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            if (kindFilter == null) {
                g(9);
            }
            if (nameFilter == null) {
                g(10);
            }
            throw new IllegalStateException(this.b);
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
            throw new IllegalStateException();
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
            throw new IllegalStateException();
        }

        public String toString() {
            return "ThrowingScope{" + this.b + '}';
        }
    }

    /* compiled from: ErrorUtils */
    public static class c extends kotlin.reflect.jvm.internal.impl.descriptors.impl.h {
        private static /* synthetic */ void c0(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                case 5:
                case 8:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 2:
                case 5:
                case 8:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "substitutor";
                    break;
                case 2:
                case 5:
                case 8:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorClassDescriptor";
                    break;
                case 3:
                    objArr[0] = "typeArguments";
                    break;
                case 4:
                case 7:
                    objArr[0] = "kotlinTypeRefiner";
                    break;
                case 6:
                    objArr[0] = "typeSubstitution";
                    break;
                default:
                    objArr[0] = "name";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "substitute";
                    break;
                case 5:
                case 8:
                    objArr[1] = "getMemberScope";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorClassDescriptor";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[2] = "substitute";
                    break;
                case 2:
                case 5:
                case 8:
                    break;
                case 3:
                case 4:
                case 6:
                case 7:
                    objArr[2] = "getMemberScope";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                case 5:
                case 8:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.f r11) {
            /*
                r10 = this;
                if (r11 != 0) goto L_0x0006
                r0 = 0
                c0(r0)
            L_0x0006:
                kotlin.reflect.jvm.internal.impl.descriptors.y r2 = kotlin.reflect.jvm.internal.impl.types.u.q()
                kotlin.reflect.jvm.internal.impl.descriptors.w r4 = kotlin.reflect.jvm.internal.impl.descriptors.w.OPEN
                kotlin.reflect.jvm.internal.impl.descriptors.f r5 = kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS
                java.util.List r6 = java.util.Collections.emptyList()
                kotlin.reflect.jvm.internal.impl.descriptors.o0 r0 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
                r8 = 0
                kotlin.reflect.jvm.internal.impl.storage.j r9 = kotlin.reflect.jvm.internal.impl.storage.b.b
                r1 = r10
                r3 = r11
                r7 = r0
                r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r1 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r1 = r1.b()
                r2 = 1
                kotlin.reflect.jvm.internal.impl.descriptors.impl.f r0 = kotlin.reflect.jvm.internal.impl.descriptors.impl.f.e1(r10, r1, r2, r0)
                java.util.List r1 = java.util.Collections.emptyList()
                kotlin.reflect.jvm.internal.impl.descriptors.a1 r2 = kotlin.reflect.jvm.internal.impl.descriptors.z0.d
                r0.h1(r1, r2)
                kotlin.reflect.jvm.internal.impl.name.f r1 = r10.getName()
                java.lang.String r1 = r1.b()
                kotlin.reflect.jvm.internal.impl.resolve.scopes.h r1 = kotlin.reflect.jvm.internal.impl.types.u.h(r1)
                kotlin.reflect.jvm.internal.impl.types.t r2 = new kotlin.reflect.jvm.internal.impl.types.t
                java.lang.String r3 = "<ERROR>"
                kotlin.reflect.jvm.internal.impl.types.u0 r3 = kotlin.reflect.jvm.internal.impl.types.u.m(r3, r10)
                r2.<init>(r3, r1)
                r0.Y0(r2)
                java.util.Set r2 = java.util.Collections.singleton(r0)
                r10.l0(r1, r2, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.u.c.<init>(kotlin.reflect.jvm.internal.impl.name.f):void");
        }

        @NotNull
        /* renamed from: f0 */
        public kotlin.reflect.jvm.internal.impl.descriptors.e c(@NotNull TypeSubstitutor substitutor) {
            if (substitutor == null) {
                c0(1);
            }
            return this;
        }

        public String toString() {
            return getName().b();
        }

        @NotNull
        public h u(@NotNull z0 typeSubstitution, @NotNull i kotlinTypeRefiner) {
            if (typeSubstitution == null) {
                c0(6);
            }
            if (kotlinTypeRefiner == null) {
                c0(7);
            }
            h h = u.h("Error scope for class " + getName() + " with arguments: " + typeSubstitution);
            if (h == null) {
                c0(8);
            }
            return h;
        }
    }

    @NotNull
    public static kotlin.reflect.jvm.internal.impl.descriptors.e e(@NotNull String debugMessage) {
        if (debugMessage == null) {
            a(1);
        }
        return new c(kotlin.reflect.jvm.internal.impl.name.f.k("<ERROR CLASS: " + debugMessage + ">"));
    }

    @NotNull
    public static h h(@NotNull String debugMessage) {
        if (debugMessage == null) {
            a(2);
        }
        return i(debugMessage, false);
    }

    @NotNull
    public static h i(@NotNull String debugMessage, boolean throwExceptions) {
        if (debugMessage == null) {
            a(3);
        }
        if (throwExceptions) {
            return new e(debugMessage, (a) null);
        }
        return new d(debugMessage, (a) null);
    }

    @NotNull
    private static b0 g() {
        b0 descriptor = b0.G0(b, g.b.b(), w.OPEN, z0.e, true, kotlin.reflect.jvm.internal.impl.name.f.k("<ERROR PROPERTY>"), b.a.DECLARATION, o0.a, false, false, false, false, false, false);
        descriptor.S0(d, Collections.emptyList(), (l0) null, (l0) null);
        return descriptor;
    }

    /* access modifiers changed from: private */
    @NotNull
    public static n0 f(@NotNull d ownerScope) {
        if (ownerScope == null) {
            a(5);
        }
        kotlin.reflect.jvm.internal.impl.types.error.a function = new kotlin.reflect.jvm.internal.impl.types.error.a(b, ownerScope);
        function.J0((l0) null, (l0) null, Collections.emptyList(), Collections.emptyList(), j("<ERROR FUNCTION RETURN TYPE>"), w.OPEN, z0.e);
        return function;
    }

    @NotNull
    public static i0 j(@NotNull String debugMessage) {
        if (debugMessage == null) {
            a(7);
        }
        return n(debugMessage, Collections.emptyList());
    }

    @NotNull
    public static i0 p(@NotNull String debugName) {
        if (debugName == null) {
            a(8);
        }
        return o(debugName, l(debugName));
    }

    @NotNull
    public static i0 o(@NotNull String debugName, @NotNull u0 typeConstructor) {
        if (debugName == null) {
            a(9);
        }
        if (typeConstructor == null) {
            a(10);
        }
        return new t(typeConstructor, h(debugName));
    }

    @NotNull
    public static i0 n(@NotNull String debugMessage, @NotNull List<w0> arguments) {
        if (debugMessage == null) {
            a(11);
        }
        if (arguments == null) {
            a(12);
        }
        return new t(k(debugMessage), h(debugMessage), arguments, false);
    }

    @NotNull
    public static u0 k(@NotNull String debugMessage) {
        if (debugMessage == null) {
            a(15);
        }
        return m("[ERROR : " + debugMessage + "]", b);
    }

    @NotNull
    public static u0 l(@NotNull String debugName) {
        if (debugName == null) {
            a(16);
        }
        return m(debugName, b);
    }

    /* compiled from: ErrorUtils */
    public static final class b implements u0 {
        final /* synthetic */ c a;
        final /* synthetic */ String b;

        private static /* synthetic */ void e(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
            switch (i) {
                case 3:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 3:
                    objArr[0] = "kotlinTypeRefiner";
                    break;
                default:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$2";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "getSupertypes";
                    break;
                case 2:
                    objArr[1] = "getBuiltIns";
                    break;
                case 3:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$2";
                    break;
                case 4:
                    objArr[1] = "refine";
                    break;
                default:
                    objArr[1] = "getParameters";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[2] = "refine";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                    th = new IllegalArgumentException(format);
                    break;
                default:
                    th = new IllegalStateException(format);
                    break;
            }
            throw th;
        }

        b(c cVar, String str) {
            this.a = cVar;
            this.b = str;
        }

        @NotNull
        public List<t0> getParameters() {
            List<t0> g = q.g();
            if (g == null) {
                e(0);
            }
            return g;
        }

        @NotNull
        public Collection<b0> b() {
            List g = q.g();
            if (g == null) {
                e(1);
            }
            return g;
        }

        public boolean d() {
            return false;
        }

        @Nullable
        public kotlin.reflect.jvm.internal.impl.descriptors.h c() {
            return this.a;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            kotlin.reflect.jvm.internal.impl.builtins.d L0 = kotlin.reflect.jvm.internal.impl.builtins.d.L0();
            if (L0 == null) {
                e(2);
            }
            return L0;
        }

        public String toString() {
            return this.b;
        }

        @NotNull
        public u0 a(@NotNull i kotlinTypeRefiner) {
            if (kotlinTypeRefiner == null) {
                e(3);
            }
            return this;
        }
    }

    /* access modifiers changed from: private */
    @NotNull
    public static u0 m(@NotNull String debugName, @NotNull c errorClass) {
        if (debugName == null) {
            a(17);
        }
        if (errorClass == null) {
            a(18);
        }
        return new b(errorClass, debugName);
    }

    public static boolean r(@Nullable m candidate) {
        if (candidate == null) {
            return false;
        }
        if (s(candidate) || s(candidate.b()) || candidate == a) {
            return true;
        }
        return false;
    }

    private static boolean s(@Nullable m candidate) {
        return candidate instanceof c;
    }

    @NotNull
    public static y q() {
        y yVar = a;
        if (yVar == null) {
            a(19);
        }
        return yVar;
    }

    public static boolean t(@Nullable b0 type) {
        return type != null && (type.I0() instanceof f);
    }

    /* compiled from: ErrorUtils */
    public static class f implements u0 {
        private final t0 a;
        private final u0 b;

        private static /* synthetic */ void e(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$UninferredParameterTypeConstructor";
                    break;
                case 5:
                    objArr[0] = "kotlinTypeRefiner";
                    break;
                default:
                    objArr[0] = "descriptor";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "getTypeParameterDescriptor";
                    break;
                case 2:
                    objArr[1] = "getParameters";
                    break;
                case 3:
                    objArr[1] = "getSupertypes";
                    break;
                case 4:
                    objArr[1] = "getBuiltIns";
                    break;
                case 6:
                    objArr[1] = "refine";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$UninferredParameterTypeConstructor";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    break;
                case 5:
                    objArr[2] = "refine";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        @NotNull
        public t0 f() {
            t0 t0Var = this.a;
            if (t0Var == null) {
                e(1);
            }
            return t0Var;
        }

        @NotNull
        public List<t0> getParameters() {
            List<t0> parameters = this.b.getParameters();
            if (parameters == null) {
                e(2);
            }
            return parameters;
        }

        @NotNull
        public Collection<b0> b() {
            Collection<b0> b2 = this.b.b();
            if (b2 == null) {
                e(3);
            }
            return b2;
        }

        public boolean d() {
            return this.b.d();
        }

        @Nullable
        public kotlin.reflect.jvm.internal.impl.descriptors.h c() {
            return this.b.c();
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            kotlin.reflect.jvm.internal.impl.builtins.g h = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(this.a);
            if (h == null) {
                e(4);
            }
            return h;
        }

        @NotNull
        public u0 a(@NotNull i kotlinTypeRefiner) {
            if (kotlinTypeRefiner == null) {
                e(5);
            }
            return this;
        }
    }
}
