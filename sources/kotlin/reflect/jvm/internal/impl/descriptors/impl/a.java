package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractClassDescriptor */
public abstract class a extends t {
    private final f d;
    protected final kotlin.reflect.jvm.internal.impl.storage.f<i0> f;
    private final kotlin.reflect.jvm.internal.impl.storage.f<h> q;
    private final kotlin.reflect.jvm.internal.impl.storage.f<l0> x;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 16:
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
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
                break;
            case 6:
            case 12:
                objArr[0] = "typeArguments";
                break;
            case 7:
            case 10:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 9:
            case 14:
                objArr[0] = "typeSubstitution";
                break;
            case 17:
                objArr[0] = "substitutor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getName";
                break;
            case 3:
                objArr[1] = "getOriginal";
                break;
            case 4:
                objArr[1] = "getUnsubstitutedInnerClassesScope";
                break;
            case 5:
                objArr[1] = "getThisAsReceiverParameter";
                break;
            case 8:
            case 11:
            case 13:
            case 15:
                objArr[1] = "getMemberScope";
                break;
            case 16:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 18:
                objArr[1] = "substitute";
                break;
            case 19:
                objArr[1] = "getDefaultType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
                break;
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 14:
                objArr[2] = "getMemberScope";
                break;
            case 17:
                objArr[2] = "substitute";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 16:
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

    public a(@NotNull j storageManager, @NotNull f name) {
        if (storageManager == null) {
            c0(0);
        }
        if (name == null) {
            c0(1);
        }
        this.d = name;
        this.f = storageManager.c(new C0351a());
        this.q = storageManager.c(new b());
        this.x = storageManager.c(new c());
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.impl.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractClassDescriptor */
    public class C0351a implements kotlin.jvm.functions.a<i0> {
        C0351a() {
        }

        /* renamed from: a */
        public i0 invoke() {
            a aVar = a.this;
            return c1.t(aVar, aVar.R(), new C0352a());
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.impl.a$a$a  reason: collision with other inner class name */
        /* compiled from: AbstractClassDescriptor */
        public class C0352a implements l<i, i0> {
            C0352a() {
            }

            /* renamed from: a */
            public i0 invoke(i kotlinTypeRefiner) {
                kotlin.reflect.jvm.internal.impl.descriptors.h descriptor = kotlinTypeRefiner.e(a.this);
                if (descriptor == null) {
                    return (i0) a.this.f.invoke();
                }
                if (descriptor instanceof s0) {
                    return c0.b((s0) descriptor, c1.g(descriptor.i().getParameters()));
                }
                if (descriptor instanceof t) {
                    return c1.u(descriptor.i().a(kotlinTypeRefiner), ((t) descriptor).a0(kotlinTypeRefiner), this);
                }
                return descriptor.m();
            }
        }
    }

    /* compiled from: AbstractClassDescriptor */
    public class b implements kotlin.jvm.functions.a<h> {
        b() {
        }

        /* renamed from: a */
        public h invoke() {
            return new kotlin.reflect.jvm.internal.impl.resolve.scopes.f(a.this.R());
        }
    }

    /* compiled from: AbstractClassDescriptor */
    public class c implements kotlin.jvm.functions.a<l0> {
        c() {
        }

        /* renamed from: a */
        public l0 invoke() {
            return new q(a.this);
        }
    }

    @NotNull
    public f getName() {
        f fVar = this.d;
        if (fVar == null) {
            c0(2);
        }
        return fVar;
    }

    @NotNull
    public e a() {
        return this;
    }

    @NotNull
    public h P() {
        h hVar = (h) this.q.invoke();
        if (hVar == null) {
            c0(4);
        }
        return hVar;
    }

    @NotNull
    public l0 F0() {
        l0 l0Var = (l0) this.x.invoke();
        if (l0Var == null) {
            c0(5);
        }
        return l0Var;
    }

    @NotNull
    public h u(@NotNull z0 typeSubstitution, @NotNull i kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            c0(9);
        }
        if (kotlinTypeRefiner == null) {
            c0(10);
        }
        if (typeSubstitution.f()) {
            h a0 = a0(kotlinTypeRefiner);
            if (a0 == null) {
                c0(11);
            }
            return a0;
        }
        return new kotlin.reflect.jvm.internal.impl.resolve.scopes.l(a0(kotlinTypeRefiner), TypeSubstitutor.g(typeSubstitution));
    }

    @NotNull
    public h m0(@NotNull z0 typeSubstitution) {
        if (typeSubstitution == null) {
            c0(14);
        }
        h u = u(typeSubstitution, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(kotlin.reflect.jvm.internal.impl.resolve.c.g(this)));
        if (u == null) {
            c0(15);
        }
        return u;
    }

    @NotNull
    public h R() {
        h a0 = a0(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(kotlin.reflect.jvm.internal.impl.resolve.c.g(this)));
        if (a0 == null) {
            c0(16);
        }
        return a0;
    }

    @NotNull
    /* renamed from: f0 */
    public e c(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            c0(17);
        }
        if (substitutor.k()) {
            return this;
        }
        return new s(this, substitutor);
    }

    @NotNull
    public i0 m() {
        i0 i0Var = (i0) this.f.invoke();
        if (i0Var == null) {
            c0(19);
        }
        return i0Var;
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.a(this, data);
    }
}
