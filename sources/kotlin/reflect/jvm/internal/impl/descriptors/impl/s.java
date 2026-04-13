package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.b;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.j;
import kotlin.reflect.jvm.internal.impl.types.p;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazySubstitutingClassDescriptor */
public class s extends t {
    private final t d;
    private final TypeSubstitutor f;
    private TypeSubstitutor q;
    private List<t0> x;
    private List<t0> y;
    private u0 z;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 13:
            case 22:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 13:
            case 22:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 8:
                objArr[0] = "typeArguments";
                break;
            case 3:
            case 6:
            case 13:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 5:
            case 10:
                objArr[0] = "typeSubstitution";
                break;
            case 22:
                objArr[0] = "substitutor";
                break;
            default:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazySubstitutingClassDescriptor";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 13:
            case 22:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazySubstitutingClassDescriptor";
                break;
            case 4:
            case 7:
            case 9:
            case 11:
                objArr[1] = "getMemberScope";
                break;
            case 12:
            case 14:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 15:
                objArr[1] = "getStaticScope";
                break;
            case 16:
                objArr[1] = "getDefaultType";
                break;
            case 17:
                objArr[1] = "getConstructors";
                break;
            case 18:
                objArr[1] = "getAnnotations";
                break;
            case 19:
                objArr[1] = "getName";
                break;
            case 20:
                objArr[1] = "getOriginal";
                break;
            case 21:
                objArr[1] = "getContainingDeclaration";
                break;
            case 23:
                objArr[1] = "substitute";
                break;
            case 24:
                objArr[1] = "getKind";
                break;
            case 25:
                objArr[1] = "getModality";
                break;
            case 26:
                objArr[1] = "getVisibility";
                break;
            case 27:
                objArr[1] = "getUnsubstitutedInnerClassesScope";
                break;
            case 28:
                objArr[1] = "getSource";
                break;
            case 29:
                objArr[1] = "getDeclaredTypeParameters";
                break;
            case 30:
                objArr[1] = "getSealedSubclasses";
                break;
            default:
                objArr[1] = "getTypeConstructor";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
                objArr[2] = "getMemberScope";
                break;
            case 13:
                objArr[2] = "getUnsubstitutedMemberScope";
                break;
            case 22:
                objArr[2] = "substitute";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 13:
            case 22:
                th = new IllegalArgumentException(format);
                break;
            default:
                th = new IllegalStateException(format);
                break;
        }
        throw th;
    }

    public s(t descriptor, TypeSubstitutor substitutor) {
        this.d = descriptor;
        this.f = substitutor;
    }

    private TypeSubstitutor f0() {
        if (this.q == null) {
            if (this.f.k()) {
                this.q = this.f;
            } else {
                List<t0> parameters = this.d.i().getParameters();
                this.x = new ArrayList(parameters.size());
                this.q = p.b(parameters, this.f.j(), this, this.x);
                this.y = y.Q(this.x, new a());
            }
        }
        return this.q;
    }

    /* compiled from: LazySubstitutingClassDescriptor */
    public class a implements l<t0, Boolean> {
        a() {
        }

        /* renamed from: a */
        public Boolean invoke(t0 descriptor) {
            return Boolean.valueOf(!descriptor.N());
        }
    }

    @NotNull
    public u0 i() {
        u0 originalTypeConstructor = this.d.i();
        if (this.f.k()) {
            if (originalTypeConstructor == null) {
                c0(0);
            }
            return originalTypeConstructor;
        }
        if (this.z == null) {
            TypeSubstitutor substitutor = f0();
            Collection<b0> b = originalTypeConstructor.b();
            Collection<KotlinType> supertypes = new ArrayList<>(b.size());
            for (b0 supertype : b) {
                supertypes.add(substitutor.n(supertype, h1.INVARIANT));
            }
            this.z = new j(this, this.x, supertypes, b.b);
        }
        u0 u0Var = this.z;
        if (u0Var == null) {
            c0(1);
        }
        return u0Var;
    }

    @NotNull
    public h u(@NotNull z0 typeSubstitution, @NotNull i kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            c0(5);
        }
        if (kotlinTypeRefiner == null) {
            c0(6);
        }
        h memberScope = this.d.u(typeSubstitution, kotlinTypeRefiner);
        if (!this.f.k()) {
            return new kotlin.reflect.jvm.internal.impl.resolve.scopes.l(memberScope, f0());
        }
        if (memberScope == null) {
            c0(7);
        }
        return memberScope;
    }

    @NotNull
    public h m0(@NotNull z0 typeSubstitution) {
        if (typeSubstitution == null) {
            c0(10);
        }
        h u = u(typeSubstitution, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(c.g(this)));
        if (u == null) {
            c0(11);
        }
        return u;
    }

    @NotNull
    public h R() {
        h a0 = a0(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(c.g(this.d)));
        if (a0 == null) {
            c0(12);
        }
        return a0;
    }

    @NotNull
    public h a0(@NotNull i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            c0(13);
        }
        h memberScope = this.d.a0(kotlinTypeRefiner);
        if (!this.f.k()) {
            return new kotlin.reflect.jvm.internal.impl.resolve.scopes.l(memberScope, f0());
        }
        if (memberScope == null) {
            c0(14);
        }
        return memberScope;
    }

    @NotNull
    public h g0() {
        h g0 = this.d.g0();
        if (g0 == null) {
            c0(15);
        }
        return g0;
    }

    @NotNull
    public i0 m() {
        i0 j = c0.j(getAnnotations(), i(), c1.g(i().getParameters()), false, R());
        if (j == null) {
            c0(16);
        }
        return j;
    }

    @NotNull
    public l0 F0() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public Collection<d> f() {
        Collection<d> f2 = this.d.f();
        Collection<ClassConstructorDescriptor> result = new ArrayList<>(f2.size());
        for (d constructor : f2) {
            result.add(((d) constructor.r().m(constructor.a()).j(constructor.p()).c(constructor.getVisibility()).q(constructor.h()).n(false).build()).c(f0()));
        }
        return result;
    }

    @NotNull
    public g getAnnotations() {
        g annotations = this.d.getAnnotations();
        if (annotations == null) {
            c0(18);
        }
        return annotations;
    }

    @NotNull
    public f getName() {
        f name = this.d.getName();
        if (name == null) {
            c0(19);
        }
        return name;
    }

    @NotNull
    public e a() {
        e a2 = this.d.a();
        if (a2 == null) {
            c0(20);
        }
        return a2;
    }

    @NotNull
    public m b() {
        m b = this.d.b();
        if (b == null) {
            c0(21);
        }
        return b;
    }

    @NotNull
    /* renamed from: l0 */
    public e c(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            c0(22);
        }
        if (substitutor.k()) {
            return this;
        }
        return new s(this, TypeSubstitutor.h(substitutor.j(), f0().j()));
    }

    public e h0() {
        return this.d.h0();
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.f h() {
        kotlin.reflect.jvm.internal.impl.descriptors.f h = this.d.h();
        if (h == null) {
            c0(24);
        }
        return h;
    }

    @NotNull
    public w p() {
        w p = this.d.p();
        if (p == null) {
            c0(25);
        }
        return p;
    }

    @NotNull
    public a1 getVisibility() {
        a1 visibility = this.d.getVisibility();
        if (visibility == null) {
            c0(26);
        }
        return visibility;
    }

    public boolean x() {
        return this.d.x();
    }

    public boolean D0() {
        return this.d.D0();
    }

    public boolean isInline() {
        return this.d.isInline();
    }

    public boolean isExternal() {
        return this.d.isExternal();
    }

    public boolean V() {
        return this.d.V();
    }

    public boolean d0() {
        return this.d.d0();
    }

    public boolean S() {
        return this.d.S();
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.a(this, data);
    }

    @NotNull
    public h P() {
        h P = this.d.P();
        if (P == null) {
            c0(27);
        }
        return P;
    }

    @Nullable
    public d B() {
        return this.d.B();
    }

    @NotNull
    public o0 n() {
        o0 o0Var = o0.a;
        if (o0Var == null) {
            c0(28);
        }
        return o0Var;
    }

    @NotNull
    public List<t0> o() {
        f0();
        List<t0> list = this.y;
        if (list == null) {
            c0(29);
        }
        return list;
    }

    @NotNull
    public Collection<e> v() {
        Collection<e> v = this.d.v();
        if (v == null) {
            c0(30);
        }
        return v;
    }
}
