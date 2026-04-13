package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MutableClassDescriptor */
public class y extends g {
    private final j A4;
    private final f a1;
    private w a2;
    private final boolean p1;
    private a1 p2;
    private u0 p3;
    private List<t0> p4;
    private final Collection<b0> z4;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
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
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
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
                objArr[0] = "kind";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "source";
                break;
            case 4:
                objArr[0] = "storageManager";
                break;
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/MutableClassDescriptor";
                break;
            case 6:
                objArr[0] = "modality";
                break;
            case 9:
                objArr[0] = "visibility";
                break;
            case 12:
                objArr[0] = "supertype";
                break;
            case 14:
                objArr[0] = "typeParameters";
                break;
            case 16:
                objArr[0] = "kotlinTypeRefiner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "getAnnotations";
                break;
            case 7:
                objArr[1] = "getModality";
                break;
            case 8:
                objArr[1] = "getKind";
                break;
            case 10:
                objArr[1] = "getVisibility";
                break;
            case 11:
                objArr[1] = "getTypeConstructor";
                break;
            case 13:
                objArr[1] = "getConstructors";
                break;
            case 15:
                objArr[1] = "getDeclaredTypeParameters";
                break;
            case 17:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 18:
                objArr[1] = "getStaticScope";
                break;
            case 19:
                objArr[1] = "getSealedSubclasses";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/MutableClassDescriptor";
                break;
        }
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
                break;
            case 6:
                objArr[2] = "setModality";
                break;
            case 9:
                objArr[2] = "setVisibility";
                break;
            case 12:
                objArr[2] = "addSupertype";
                break;
            case 14:
                objArr[2] = "setTypeParameterDescriptors";
                break;
            case 16:
                objArr[2] = "getUnsubstitutedMemberScope";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public y(@NotNull m containingDeclaration, @NotNull f kind, boolean isInner, boolean isExternal, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull o0 source, @NotNull j storageManager) {
        super(storageManager, containingDeclaration, name, source, isExternal);
        if (containingDeclaration == null) {
            c0(0);
        }
        if (kind == null) {
            c0(1);
        }
        if (name == null) {
            c0(2);
        }
        if (source == null) {
            c0(3);
        }
        if (storageManager == null) {
            c0(4);
        }
        this.z4 = new ArrayList();
        this.A4 = storageManager;
        if (kind != f.OBJECT) {
            this.a1 = kind;
            this.p1 = isInner;
            return;
        }
        throw new AssertionError("Fix isCompanionObject()");
    }

    @Nullable
    public e h0() {
        return null;
    }

    @NotNull
    public g getAnnotations() {
        g b = g.b.b();
        if (b == null) {
            c0(5);
        }
        return b;
    }

    public void C0(@NotNull w modality) {
        if (modality == null) {
            c0(6);
        }
        if (modality != w.SEALED) {
            this.a2 = modality;
            return;
        }
        throw new AssertionError("Implement getSealedSubclasses() for this class: " + getClass());
    }

    @NotNull
    public w p() {
        w wVar = this.a2;
        if (wVar == null) {
            c0(7);
        }
        return wVar;
    }

    @NotNull
    public f h() {
        f fVar = this.a1;
        if (fVar == null) {
            c0(8);
        }
        return fVar;
    }

    public void H0(@NotNull a1 visibility) {
        if (visibility == null) {
            c0(9);
        }
        this.p2 = visibility;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = this.p2;
        if (a1Var == null) {
            c0(10);
        }
        return a1Var;
    }

    public boolean x() {
        return this.p1;
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

    @NotNull
    public u0 i() {
        u0 u0Var = this.p3;
        if (u0Var == null) {
            c0(11);
        }
        return u0Var;
    }

    @NotNull
    /* renamed from: A0 */
    public Set<d> f() {
        Set<d> emptySet = Collections.emptySet();
        if (emptySet == null) {
            c0(13);
        }
        return emptySet;
    }

    @Nullable
    public d B() {
        return null;
    }

    public void G0(@NotNull List<t0> typeParameters) {
        if (typeParameters == null) {
            c0(14);
        }
        if (this.p4 == null) {
            this.p4 = new ArrayList(typeParameters);
            return;
        }
        throw new IllegalStateException("Type parameters are already set for " + getName());
    }

    @NotNull
    public List<t0> o() {
        List<t0> list = this.p4;
        if (list == null) {
            c0(15);
        }
        return list;
    }

    public void l0() {
        if (this.p3 == null) {
            this.p3 = new kotlin.reflect.jvm.internal.impl.types.j(this, this.p4, this.z4, this.A4);
            Iterator i$ = f().iterator();
            while (i$.hasNext()) {
                ((f) i$.next()).Y0(m());
            }
            return;
        }
        throw new AssertionError(this.p3);
    }

    @NotNull
    public h a0(@NotNull i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            c0(16);
        }
        h.b bVar = h.b.b;
        if (bVar == null) {
            c0(17);
        }
        return bVar;
    }

    @NotNull
    public h g0() {
        h.b bVar = h.b.b;
        if (bVar == null) {
            c0(18);
        }
        return bVar;
    }

    @NotNull
    public Collection<e> v() {
        List emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(19);
        }
        return emptyList;
    }

    public String toString() {
        return j.a0(this);
    }
}
