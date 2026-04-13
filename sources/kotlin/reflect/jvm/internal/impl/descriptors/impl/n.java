package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EnumEntrySyntheticClassDescriptor */
public class n extends g {
    private final u0 a1;
    /* access modifiers changed from: private */
    public final f<Set<kotlin.reflect.jvm.internal.impl.name.f>> a2;
    private final h p1;
    private final g p2;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "enumClass";
                break;
            case 2:
            case 9:
                objArr[0] = "name";
                break;
            case 3:
            case 10:
                objArr[0] = "enumMemberNames";
                break;
            case 4:
            case 11:
                objArr[0] = "annotations";
                break;
            case 5:
            case 12:
                objArr[0] = "source";
                break;
            case 7:
                objArr[0] = "containingClass";
                break;
            case 8:
                objArr[0] = "supertype";
                break;
            case 13:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 14:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 15:
                objArr[1] = "getStaticScope";
                break;
            case 16:
                objArr[1] = "getConstructors";
                break;
            case 17:
                objArr[1] = "getTypeConstructor";
                break;
            case 18:
                objArr[1] = "getKind";
                break;
            case 19:
                objArr[1] = "getModality";
                break;
            case 20:
                objArr[1] = "getVisibility";
                break;
            case 21:
                objArr[1] = "getAnnotations";
                break;
            case 22:
                objArr[1] = "getDeclaredTypeParameters";
                break;
            case 23:
                objArr[1] = "getSealedSubclasses";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "<init>";
                break;
            case 13:
                objArr[2] = "getUnsubstitutedMemberScope";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                break;
            default:
                objArr[2] = "create";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static n A0(@NotNull j storageManager, @NotNull e enumClass, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull f<Set<kotlin.reflect.jvm.internal.impl.name.f>> enumMemberNames, @NotNull g annotations, @NotNull o0 source) {
        if (storageManager == null) {
            c0(0);
        }
        if (enumClass == null) {
            c0(1);
        }
        if (name == null) {
            c0(2);
        }
        if (enumMemberNames == null) {
            c0(3);
        }
        if (annotations == null) {
            c0(4);
        }
        if (source == null) {
            c0(5);
        }
        return new n(storageManager, enumClass, enumClass.m(), name, enumMemberNames, annotations, source);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private n(@NotNull j storageManager, @NotNull e containingClass, @NotNull b0 supertype, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull f<Set<kotlin.reflect.jvm.internal.impl.name.f>> enumMemberNames, @NotNull g annotations, @NotNull o0 source) {
        super(storageManager, containingClass, name, source, false);
        if (storageManager == null) {
            c0(6);
        }
        if (containingClass == null) {
            c0(7);
        }
        if (supertype == null) {
            c0(8);
        }
        if (name == null) {
            c0(9);
        }
        if (enumMemberNames == null) {
            c0(10);
        }
        if (annotations == null) {
            c0(11);
        }
        if (source == null) {
            c0(12);
        }
        if (containingClass.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS) {
            this.p2 = annotations;
            this.a1 = new kotlin.reflect.jvm.internal.impl.types.j(this, Collections.emptyList(), Collections.singleton(supertype), storageManager);
            this.p1 = new a(this, storageManager);
            this.a2 = enumMemberNames;
            return;
        }
        throw new AssertionError();
    }

    @NotNull
    public h a0(@NotNull i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            c0(13);
        }
        h hVar = this.p1;
        if (hVar == null) {
            c0(14);
        }
        return hVar;
    }

    @NotNull
    public h g0() {
        h.b bVar = h.b.b;
        if (bVar == null) {
            c0(15);
        }
        return bVar;
    }

    @NotNull
    public Collection<d> f() {
        List emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(16);
        }
        return emptyList;
    }

    @NotNull
    public u0 i() {
        u0 u0Var = this.a1;
        if (u0Var == null) {
            c0(17);
        }
        return u0Var;
    }

    @Nullable
    public e h0() {
        return null;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.f h() {
        kotlin.reflect.jvm.internal.impl.descriptors.f fVar = kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_ENTRY;
        if (fVar == null) {
            c0(18);
        }
        return fVar;
    }

    @NotNull
    public w p() {
        w wVar = w.FINAL;
        if (wVar == null) {
            c0(19);
        }
        return wVar;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = z0.e;
        if (a1Var == null) {
            c0(20);
        }
        return a1Var;
    }

    public boolean x() {
        return false;
    }

    public boolean D0() {
        return false;
    }

    public boolean isInline() {
        return false;
    }

    public boolean V() {
        return false;
    }

    public boolean d0() {
        return false;
    }

    public boolean S() {
        return false;
    }

    @Nullable
    public d B() {
        return null;
    }

    @NotNull
    public g getAnnotations() {
        g gVar = this.p2;
        if (gVar == null) {
            c0(21);
        }
        return gVar;
    }

    public String toString() {
        return "enum entry " + getName();
    }

    @NotNull
    public List<t0> o() {
        List<t0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(22);
        }
        return emptyList;
    }

    @NotNull
    public Collection<e> v() {
        List emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(23);
        }
        return emptyList;
    }

    /* compiled from: EnumEntrySyntheticClassDescriptor */
    public class a extends kotlin.reflect.jvm.internal.impl.resolve.scopes.i {
        private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> b;
        private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends i0>> c;
        private final f<Collection<m>> d;
        final /* synthetic */ n e;

        private static /* synthetic */ void g(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                case 7:
                case 9:
                case 12:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                case 7:
                case 9:
                case 12:
                case 15:
                case 16:
                case 17:
                case 18:
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
                case 4:
                case 5:
                case 8:
                case 10:
                    objArr[0] = "name";
                    break;
                case 2:
                case 6:
                    objArr[0] = FirebaseAnalytics.Param.LOCATION;
                    break;
                case 3:
                case 7:
                case 9:
                case 12:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope";
                    break;
                case 11:
                    objArr[0] = "fromSupertypes";
                    break;
                case 13:
                    objArr[0] = "kindFilter";
                    break;
                case 14:
                    objArr[0] = "nameFilter";
                    break;
                case 20:
                    objArr[0] = "p";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "getContributedVariables";
                    break;
                case 7:
                    objArr[1] = "getContributedFunctions";
                    break;
                case 9:
                    objArr[1] = "getSupertypeScope";
                    break;
                case 12:
                    objArr[1] = "resolveFakeOverrides";
                    break;
                case 15:
                    objArr[1] = "getContributedDescriptors";
                    break;
                case 16:
                    objArr[1] = "computeAllDeclarations";
                    break;
                case 17:
                    objArr[1] = "getFunctionNames";
                    break;
                case 18:
                    objArr[1] = "getClassifierNames";
                    break;
                case 19:
                    objArr[1] = "getVariableNames";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "getContributedVariables";
                    break;
                case 3:
                case 7:
                case 9:
                case 12:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    break;
                case 4:
                    objArr[2] = "computeProperties";
                    break;
                case 5:
                case 6:
                    objArr[2] = "getContributedFunctions";
                    break;
                case 8:
                    objArr[2] = "computeFunctions";
                    break;
                case 10:
                case 11:
                    objArr[2] = "resolveFakeOverrides";
                    break;
                case 13:
                case 14:
                    objArr[2] = "getContributedDescriptors";
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
                case 3:
                case 7:
                case 9:
                case 12:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.impl.n$a$a  reason: collision with other inner class name */
        /* compiled from: EnumEntrySyntheticClassDescriptor */
        public class C0353a implements l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
            final /* synthetic */ n c;

            C0353a(n nVar) {
                this.c = nVar;
            }

            /* renamed from: a */
            public Collection<? extends n0> invoke(kotlin.reflect.jvm.internal.impl.name.f name) {
                return a.this.l(name);
            }
        }

        public a(@NotNull n nVar, j storageManager) {
            if (storageManager == null) {
                g(0);
            }
            this.e = nVar;
            this.b = storageManager.h(new C0353a(nVar));
            this.c = storageManager.h(new b(nVar));
            this.d = storageManager.c(new c(nVar));
        }

        /* compiled from: EnumEntrySyntheticClassDescriptor */
        public class b implements l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends i0>> {
            final /* synthetic */ n c;

            b(n nVar) {
                this.c = nVar;
            }

            /* renamed from: a */
            public Collection<? extends i0> invoke(kotlin.reflect.jvm.internal.impl.name.f name) {
                return a.this.m(name);
            }
        }

        /* compiled from: EnumEntrySyntheticClassDescriptor */
        public class c implements kotlin.jvm.functions.a<Collection<m>> {
            final /* synthetic */ n c;

            c(n nVar) {
                this.c = nVar;
            }

            /* renamed from: a */
            public Collection<m> invoke() {
                return a.this.k();
            }
        }

        @NotNull
        public Collection<? extends i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(1);
            }
            if (location == null) {
                g(2);
            }
            Collection<? extends i0> invoke = this.c.invoke(name);
            if (invoke == null) {
                g(3);
            }
            return invoke;
        }

        /* access modifiers changed from: private */
        @NotNull
        public Collection<? extends i0> m(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            if (name == null) {
                g(4);
            }
            return o(name, n().e(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_NON_TRACKED_SCOPE));
        }

        @NotNull
        public Collection<? extends n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            if (name == null) {
                g(5);
            }
            if (location == null) {
                g(6);
            }
            Collection<? extends n0> invoke = this.b.invoke(name);
            if (invoke == null) {
                g(7);
            }
            return invoke;
        }

        /* access modifiers changed from: private */
        @NotNull
        public Collection<? extends n0> l(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            if (name == null) {
                g(8);
            }
            return o(name, n().b(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_NON_TRACKED_SCOPE));
        }

        @NotNull
        private h n() {
            Collection<b0> b2 = this.e.i().b();
            if (b2.size() == 1) {
                h l = b2.iterator().next().l();
                if (l == null) {
                    g(9);
                }
                return l;
            }
            throw new AssertionError("Enum entry and its companion object both should have exactly one supertype: " + b2);
        }

        @NotNull
        private <D extends kotlin.reflect.jvm.internal.impl.descriptors.b> Collection<? extends D> o(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<? extends D> fromSupertypes) {
            if (name == null) {
                g(10);
            }
            if (fromSupertypes == null) {
                g(11);
            }
            Set<D> result = new LinkedHashSet<>();
            kotlin.reflect.jvm.internal.impl.resolve.i.b.w(name, fromSupertypes, Collections.emptySet(), this.e, new d(result));
            return result;
        }

        /* compiled from: EnumEntrySyntheticClassDescriptor */
        public class d extends kotlin.reflect.jvm.internal.impl.resolve.g {
            final /* synthetic */ Set a;

            private static /* synthetic */ void f(int i) {
                Object[] objArr = new Object[3];
                switch (i) {
                    case 1:
                        objArr[0] = "fromSuper";
                        break;
                    case 2:
                        objArr[0] = "fromCurrent";
                        break;
                    default:
                        objArr[0] = "fakeOverride";
                        break;
                }
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope$4";
                switch (i) {
                    case 1:
                    case 2:
                        objArr[2] = "conflict";
                        break;
                    default:
                        objArr[2] = "addFakeOverride";
                        break;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            d(Set set) {
                this.a = set;
            }

            public void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fakeOverride) {
                if (fakeOverride == null) {
                    f(0);
                }
                kotlin.reflect.jvm.internal.impl.resolve.i.L(fakeOverride, (l<kotlin.reflect.jvm.internal.impl.descriptors.b, x>) null);
                this.a.add(fakeOverride);
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromSuper, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromCurrent) {
                if (fromSuper == null) {
                    f(1);
                }
                if (fromCurrent == null) {
                    f(2);
                }
            }
        }

        @NotNull
        public Collection<m> d(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            if (kindFilter == null) {
                g(13);
            }
            if (nameFilter == null) {
                g(14);
            }
            Collection<m> collection = (Collection) this.d.invoke();
            if (collection == null) {
                g(15);
            }
            return collection;
        }

        /* access modifiers changed from: private */
        @NotNull
        public Collection<m> k() {
            Collection<DeclarationDescriptor> result = new HashSet<>();
            for (kotlin.reflect.jvm.internal.impl.name.f name : (Set) this.e.a2.invoke()) {
                kotlin.reflect.jvm.internal.impl.incremental.components.d dVar = kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_NON_TRACKED_SCOPE;
                result.addAll(b(name, dVar));
                result.addAll(e(name, dVar));
            }
            return result;
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
            Set<kotlin.reflect.jvm.internal.impl.name.f> set = (Set) this.e.a2.invoke();
            if (set == null) {
                g(17);
            }
            return set;
        }

        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
            Set<kotlin.reflect.jvm.internal.impl.name.f> set = (Set) this.e.a2.invoke();
            if (set == null) {
                g(19);
            }
            return set;
        }
    }
}
