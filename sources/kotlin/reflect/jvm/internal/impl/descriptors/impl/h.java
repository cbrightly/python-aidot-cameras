package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
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
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassDescriptorImpl */
public class h extends g {
    private final w a1;
    private final u0 a2;
    private final f p1;
    private kotlin.reflect.jvm.internal.impl.resolve.scopes.h p2;
    private Set<d> p3;
    private d p4;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
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
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
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
                objArr[0] = "name";
                break;
            case 2:
                objArr[0] = "modality";
                break;
            case 3:
                objArr[0] = "kind";
                break;
            case 4:
                objArr[0] = "supertypes";
                break;
            case 5:
                objArr[0] = "source";
                break;
            case 6:
                objArr[0] = "storageManager";
                break;
            case 7:
                objArr[0] = "unsubstitutedMemberScope";
                break;
            case 8:
                objArr[0] = "constructors";
                break;
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorImpl";
                break;
            case 12:
                objArr[0] = "kotlinTypeRefiner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 9:
                objArr[1] = "getAnnotations";
                break;
            case 10:
                objArr[1] = "getTypeConstructor";
                break;
            case 11:
                objArr[1] = "getConstructors";
                break;
            case 13:
                objArr[1] = "getUnsubstitutedMemberScope";
                break;
            case 14:
                objArr[1] = "getStaticScope";
                break;
            case 15:
                objArr[1] = "getKind";
                break;
            case 16:
                objArr[1] = "getModality";
                break;
            case 17:
                objArr[1] = "getVisibility";
                break;
            case 18:
                objArr[1] = "getDeclaredTypeParameters";
                break;
            case 19:
                objArr[1] = "getSealedSubclasses";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorImpl";
                break;
        }
        switch (i) {
            case 7:
            case 8:
                objArr[2] = "initialize";
                break;
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                break;
            case 12:
                objArr[2] = "getUnsubstitutedMemberScope";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(@NotNull m containingDeclaration, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull w modality, @NotNull f kind, @NotNull Collection<b0> supertypes, @NotNull o0 source, boolean isExternal, @NotNull j storageManager) {
        super(storageManager, containingDeclaration, name, source, isExternal);
        if (containingDeclaration == null) {
            c0(0);
        }
        if (name == null) {
            c0(1);
        }
        if (modality == null) {
            c0(2);
        }
        if (kind == null) {
            c0(3);
        }
        if (supertypes == null) {
            c0(4);
        }
        if (source == null) {
            c0(5);
        }
        if (storageManager == null) {
            c0(6);
        }
        if (modality != w.SEALED) {
            this.a1 = modality;
            this.p1 = kind;
            this.a2 = new kotlin.reflect.jvm.internal.impl.types.j(this, Collections.emptyList(), supertypes, storageManager);
            return;
        }
        throw new AssertionError("Implement getSealedSubclasses() for this class: " + getClass());
    }

    public final void l0(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.h unsubstitutedMemberScope, @NotNull Set<d> constructors, @Nullable d primaryConstructor) {
        if (unsubstitutedMemberScope == null) {
            c0(7);
        }
        if (constructors == null) {
            c0(8);
        }
        this.p2 = unsubstitutedMemberScope;
        this.p3 = constructors;
        this.p4 = primaryConstructor;
    }

    @NotNull
    public g getAnnotations() {
        g b = g.b.b();
        if (b == null) {
            c0(9);
        }
        return b;
    }

    @NotNull
    public u0 i() {
        u0 u0Var = this.a2;
        if (u0Var == null) {
            c0(10);
        }
        return u0Var;
    }

    @NotNull
    public Collection<d> f() {
        Set<d> set = this.p3;
        if (set == null) {
            c0(11);
        }
        return set;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h a0(@NotNull i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            c0(12);
        }
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h hVar = this.p2;
        if (hVar == null) {
            c0(13);
        }
        return hVar;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h g0() {
        h.b bVar = h.b.b;
        if (bVar == null) {
            c0(14);
        }
        return bVar;
    }

    @Nullable
    public e h0() {
        return null;
    }

    @NotNull
    public f h() {
        f fVar = this.p1;
        if (fVar == null) {
            c0(15);
        }
        return fVar;
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

    public d B() {
        return this.p4;
    }

    @NotNull
    public w p() {
        w wVar = this.a1;
        if (wVar == null) {
            c0(16);
        }
        return wVar;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = z0.e;
        if (a1Var == null) {
            c0(17);
        }
        return a1Var;
    }

    public boolean D0() {
        return false;
    }

    public boolean isInline() {
        return false;
    }

    public boolean x() {
        return false;
    }

    public String toString() {
        return "class " + getName();
    }

    @NotNull
    public List<t0> o() {
        List<t0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(18);
        }
        return emptyList;
    }

    @NotNull
    public Collection<e> v() {
        List emptyList = Collections.emptyList();
        if (emptyList == null) {
            c0(19);
        }
        return emptyList;
    }
}
