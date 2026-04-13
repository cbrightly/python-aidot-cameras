package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeParameterDescriptor */
public abstract class e extends k implements t0 {
    private final f<i0> a1;
    private final f<u0> p0;
    private final j p1;
    private final h1 x;
    private final boolean y;
    private final int z;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "annotations";
                break;
            case 3:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "variance";
                break;
            case 5:
                objArr[0] = "source";
                break;
            case 6:
                objArr[0] = "supertypeLoopChecker";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "getVariance";
                break;
            case 8:
                objArr[1] = "getUpperBounds";
                break;
            case 9:
                objArr[1] = "getTypeConstructor";
                break;
            case 10:
                objArr[1] = "getDefaultType";
                break;
            case 11:
                objArr[1] = "getOriginal";
                break;
            case 12:
                objArr[1] = "getStorageManager";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* access modifiers changed from: protected */
    public abstract void f0(@NotNull b0 b0Var);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract List<b0> l0();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected e(@NotNull j storageManager, @NotNull m containingDeclaration, @NotNull g annotations, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull h1 variance, boolean isReified, int index, @NotNull o0 source, @NotNull r0 supertypeLoopChecker) {
        super(containingDeclaration, annotations, name, source);
        if (storageManager == null) {
            u(0);
        }
        if (containingDeclaration == null) {
            u(1);
        }
        if (annotations == null) {
            u(2);
        }
        if (name == null) {
            u(3);
        }
        if (variance == null) {
            u(4);
        }
        if (source == null) {
            u(5);
        }
        if (supertypeLoopChecker == null) {
            u(6);
        }
        this.x = variance;
        this.y = isReified;
        this.z = index;
        this.p0 = storageManager.c(new a(storageManager, supertypeLoopChecker));
        this.a1 = storageManager.c(new b(storageManager, name));
        this.p1 = storageManager;
    }

    /* compiled from: AbstractTypeParameterDescriptor */
    public class a implements kotlin.jvm.functions.a<u0> {
        final /* synthetic */ j c;
        final /* synthetic */ r0 d;

        a(j jVar, r0 r0Var) {
            this.c = jVar;
            this.d = r0Var;
        }

        /* renamed from: a */
        public u0 invoke() {
            return new c(e.this, this.c, this.d);
        }
    }

    /* compiled from: AbstractTypeParameterDescriptor */
    public class b implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ j c;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.name.f d;

        b(j jVar, kotlin.reflect.jvm.internal.impl.name.f fVar) {
            this.c = jVar;
            this.d = fVar;
        }

        /* renamed from: a */
        public i0 invoke() {
            return c0.j(g.b.b(), e.this.i(), Collections.emptyList(), false, new kotlin.reflect.jvm.internal.impl.resolve.scopes.g(this.c.c(new a())));
        }

        /* compiled from: AbstractTypeParameterDescriptor */
        public class a implements kotlin.jvm.functions.a<h> {
            a() {
            }

            /* renamed from: a */
            public h invoke() {
                return kotlin.reflect.jvm.internal.impl.resolve.scopes.m.h("Scope for type parameter " + b.this.d.b(), e.this.getUpperBounds());
            }
        }
    }

    @NotNull
    public h1 y() {
        h1 h1Var = this.x;
        if (h1Var == null) {
            u(7);
        }
        return h1Var;
    }

    public boolean t() {
        return this.y;
    }

    public int getIndex() {
        return this.z;
    }

    public boolean N() {
        return false;
    }

    @NotNull
    public List<b0> getUpperBounds() {
        List<b0> l = ((c) i()).b();
        if (l == null) {
            u(8);
        }
        return l;
    }

    @NotNull
    public final u0 i() {
        u0 u0Var = (u0) this.p0.invoke();
        if (u0Var == null) {
            u(9);
        }
        return u0Var;
    }

    @NotNull
    public i0 m() {
        i0 i0Var = (i0) this.a1.invoke();
        if (i0Var == null) {
            u(10);
        }
        return i0Var;
    }

    @NotNull
    public t0 a() {
        t0 t0Var = (t0) super.a();
        if (t0Var == null) {
            u(11);
        }
        return t0Var;
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.m(this, data);
    }

    @NotNull
    public j J() {
        j jVar = this.p1;
        if (jVar == null) {
            u(12);
        }
        return jVar;
    }

    /* compiled from: AbstractTypeParameterDescriptor */
    public class c extends kotlin.reflect.jvm.internal.impl.types.h {
        private final r0 b;
        final /* synthetic */ e c;

        private static /* synthetic */ void o(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
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
                case 5:
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
                case 5:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
                    break;
                case 6:
                    objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "computeSupertypes";
                    break;
                case 2:
                    objArr[1] = "getParameters";
                    break;
                case 3:
                    objArr[1] = "getDeclarationDescriptor";
                    break;
                case 4:
                    objArr[1] = "getBuiltIns";
                    break;
                case 5:
                    objArr[1] = "getSupertypeLoopChecker";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    break;
                case 6:
                    objArr[2] = "reportSupertypeLoopError";
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
                case 5:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull e eVar, j storageManager, r0 supertypeLoopChecker) {
            super(storageManager);
            if (storageManager == null) {
                o(0);
            }
            this.c = eVar;
            this.b = supertypeLoopChecker;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Collection<b0> g() {
            List<b0> l0 = this.c.l0();
            if (l0 == null) {
                o(1);
            }
            return l0;
        }

        @NotNull
        public List<t0> getParameters() {
            List<t0> emptyList = Collections.emptyList();
            if (emptyList == null) {
                o(2);
            }
            return emptyList;
        }

        public boolean d() {
            return true;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.descriptors.h c() {
            e eVar = this.c;
            if (eVar == null) {
                o(3);
            }
            return eVar;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            kotlin.reflect.jvm.internal.impl.builtins.g h = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(this.c);
            if (h == null) {
                o(4);
            }
            return h;
        }

        public String toString() {
            return this.c.getName().toString();
        }

        /* access modifiers changed from: protected */
        @NotNull
        public r0 k() {
            r0 r0Var = this.b;
            if (r0Var == null) {
                o(5);
            }
            return r0Var;
        }

        /* access modifiers changed from: protected */
        public void n(@NotNull b0 type) {
            if (type == null) {
                o(6);
            }
            this.c.f0(type);
        }

        /* access modifiers changed from: protected */
        @Nullable
        public b0 h() {
            return u.j("Cyclic upper bounds");
        }
    }
}
