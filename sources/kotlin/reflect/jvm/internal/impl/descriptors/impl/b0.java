package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.s;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.p;
import kotlin.reflect.jvm.internal.impl.types.z0;
import kotlin.reflect.jvm.internal.impl.utils.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PropertyDescriptorImpl */
public class b0 extends m0 implements i0 {
    private final boolean A4;
    private final boolean B4;
    private final boolean C4;
    /* access modifiers changed from: private */
    public l0 D4;
    private l0 E4;
    private List<t0> F4;
    private c0 G4;
    private k0 H4;
    private boolean I4;
    private s J4;
    private s K4;
    private a1 a1;
    private final i0 a2;
    private final w p0;
    private Collection<? extends i0> p1;
    private final b.a p2;
    private final boolean p3;
    private final boolean p4;
    private final boolean z4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 33:
            case 34:
            case 36:
            case 37:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 33:
            case 34:
            case 36:
            case 37:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 8:
                objArr[0] = "annotations";
                break;
            case 2:
            case 9:
                objArr[0] = "modality";
                break;
            case 3:
            case 10:
            case 16:
                objArr[0] = "visibility";
                break;
            case 4:
            case 11:
                objArr[0] = "name";
                break;
            case 5:
            case 12:
            case 30:
                objArr[0] = "kind";
                break;
            case 6:
            case 13:
            case 32:
                objArr[0] = "source";
                break;
            case 14:
                objArr[0] = "outType";
                break;
            case 15:
                objArr[0] = "typeParameters";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 33:
            case 34:
            case 36:
            case 37:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyDescriptorImpl";
                break;
            case 22:
                objArr[0] = "originalSubstitutor";
                break;
            case 24:
                objArr[0] = "copyConfiguration";
                break;
            case 25:
                objArr[0] = "substitutor";
                break;
            case 26:
                objArr[0] = "accessorDescriptor";
                break;
            case 27:
                objArr[0] = "newOwner";
                break;
            case 28:
                objArr[0] = "newModality";
                break;
            case 29:
                objArr[0] = "newVisibility";
                break;
            case 31:
                objArr[0] = "newName";
                break;
            case 35:
                objArr[0] = "overriddenDescriptors";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 17:
                objArr[1] = "getTypeParameters";
                break;
            case 18:
                objArr[1] = "getReturnType";
                break;
            case 19:
                objArr[1] = "getModality";
                break;
            case 20:
                objArr[1] = "getVisibility";
                break;
            case 21:
                objArr[1] = "getAccessors";
                break;
            case 23:
                objArr[1] = "getSourceToUseForCopy";
                break;
            case 33:
                objArr[1] = "getOriginal";
                break;
            case 34:
                objArr[1] = "getKind";
                break;
            case 36:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 37:
                objArr[1] = "copy";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyDescriptorImpl";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "create";
                break;
            case 14:
            case 15:
                objArr[2] = "setType";
                break;
            case 16:
                objArr[2] = "setVisibility";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 33:
            case 34:
            case 36:
            case 37:
                break;
            case 22:
                objArr[2] = "substitute";
                break;
            case 24:
                objArr[2] = "doSubstitute";
                break;
            case 25:
            case 26:
                objArr[2] = "getSubstitutedInitialSignatureDescriptor";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 35:
                objArr[2] = "setOverriddenDescriptors";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 33:
            case 34:
            case 36:
            case 37:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected b0(@NotNull m containingDeclaration, @Nullable i0 original, @NotNull g annotations, @NotNull w modality, @NotNull a1 visibility, boolean isVar, @NotNull f name, @NotNull b.a kind, @NotNull o0 source, boolean lateInit, boolean isConst, boolean isExpect, boolean isActual, boolean isExternal, boolean isDelegated) {
        super(containingDeclaration, annotations, name, (kotlin.reflect.jvm.internal.impl.types.b0) null, isVar, source);
        w wVar = modality;
        a1 a1Var = visibility;
        b.a aVar = kind;
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (wVar == null) {
            u(2);
        }
        if (a1Var == null) {
            u(3);
        }
        if (name == null) {
            u(4);
        }
        if (aVar == null) {
            u(5);
        }
        if (source == null) {
            u(6);
        }
        this.p1 = null;
        this.p0 = wVar;
        this.a1 = a1Var;
        this.a2 = original == null ? this : original;
        this.p2 = aVar;
        this.p3 = lateInit;
        this.p4 = isConst;
        this.z4 = isExpect;
        this.A4 = isActual;
        this.B4 = isExternal;
        this.C4 = isDelegated;
    }

    @NotNull
    public static b0 G0(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull w modality, @NotNull a1 visibility, boolean isVar, @NotNull f name, @NotNull b.a kind, @NotNull o0 source, boolean lateInit, boolean isConst, boolean isExpect, boolean isActual, boolean isExternal, boolean isDelegated) {
        if (containingDeclaration == null) {
            u(7);
        }
        if (annotations == null) {
            u(8);
        }
        if (modality == null) {
            u(9);
        }
        if (visibility == null) {
            u(10);
        }
        if (name == null) {
            u(11);
        }
        if (kind == null) {
            u(12);
        }
        if (source == null) {
            u(13);
        }
        return new b0(containingDeclaration, (i0) null, annotations, modality, visibility, isVar, name, kind, source, lateInit, isConst, isExpect, isActual, isExternal, isDelegated);
    }

    public void S0(@NotNull kotlin.reflect.jvm.internal.impl.types.b0 outType, @NotNull List<? extends t0> typeParameters, @Nullable l0 dispatchReceiverParameter, @Nullable l0 extensionReceiverParameter) {
        if (outType == null) {
            u(14);
        }
        if (typeParameters == null) {
            u(15);
        }
        f0(outType);
        this.F4 = new ArrayList(typeParameters);
        this.E4 = extensionReceiverParameter;
        this.D4 = dispatchReceiverParameter;
    }

    public void M0(@Nullable c0 getter, @Nullable k0 setter) {
        N0(getter, setter, (s) null, (s) null);
    }

    public void N0(@Nullable c0 getter, @Nullable k0 setter, @Nullable s backingField, @Nullable s delegateField) {
        this.G4 = getter;
        this.H4 = setter;
        this.J4 = backingField;
        this.K4 = delegateField;
    }

    public void R0(boolean setterProjectedOut) {
        this.I4 = setterProjectedOut;
    }

    public void T0(@NotNull a1 visibility) {
        if (visibility == null) {
            u(16);
        }
        this.a1 = visibility;
    }

    @NotNull
    public List<t0> getTypeParameters() {
        List<t0> list = this.F4;
        if (list == null) {
            u(17);
        }
        return list;
    }

    @Nullable
    public l0 L() {
        return this.E4;
    }

    @Nullable
    public l0 I() {
        return this.D4;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.b0 getReturnType() {
        kotlin.reflect.jvm.internal.impl.types.b0 type = getType();
        if (type == null) {
            u(18);
        }
        return type;
    }

    @NotNull
    public w p() {
        w wVar = this.p0;
        if (wVar == null) {
            u(19);
        }
        return wVar;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = this.a1;
        if (a1Var == null) {
            u(20);
        }
        return a1Var;
    }

    @Nullable
    /* renamed from: J0 */
    public c0 getGetter() {
        return this.G4;
    }

    @Nullable
    public k0 getSetter() {
        return this.H4;
    }

    public boolean O0() {
        return this.I4;
    }

    public boolean t0() {
        return this.p3;
    }

    public boolean isConst() {
        return this.p4;
    }

    public boolean isExternal() {
        return this.B4;
    }

    public boolean z() {
        return this.C4;
    }

    @NotNull
    public List<h0> s() {
        List<PropertyAccessorDescriptor> result = new ArrayList<>(2);
        c0 c0Var = this.G4;
        if (c0Var != null) {
            result.add(c0Var);
        }
        k0 k0Var = this.H4;
        if (k0Var != null) {
            result.add(k0Var);
        }
        return result;
    }

    public i0 c(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor == null) {
            u(22);
        }
        if (originalSubstitutor.k()) {
            return this;
        }
        return P0().u(originalSubstitutor.j()).s(c0()).m();
    }

    /* compiled from: PropertyDescriptorImpl */
    public class a {
        /* access modifiers changed from: private */
        public m a;
        /* access modifiers changed from: private */
        public w b;
        /* access modifiers changed from: private */
        public a1 c;
        /* access modifiers changed from: private */
        public i0 d = null;
        /* access modifiers changed from: private */
        public boolean e = false;
        /* access modifiers changed from: private */
        public b.a f;
        /* access modifiers changed from: private */
        public z0 g;
        /* access modifiers changed from: private */
        public boolean h;
        /* access modifiers changed from: private */
        public l0 i;
        /* access modifiers changed from: private */
        public List<t0> j;
        /* access modifiers changed from: private */
        public f k;

        private static /* synthetic */ void a(int i2) {
            String str;
            int i3;
            Throwable th;
            switch (i2) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 12:
                case 14:
                case 15:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i2) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 12:
                case 14:
                case 15:
                case 17:
                    i3 = 2;
                    break;
                default:
                    i3 = 3;
                    break;
            }
            Object[] objArr = new Object[i3];
            switch (i2) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 12:
                case 14:
                case 15:
                case 17:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyDescriptorImpl$CopyConfiguration";
                    break;
                case 4:
                    objArr[0] = "modality";
                    break;
                case 6:
                    objArr[0] = "visibility";
                    break;
                case 8:
                    objArr[0] = "kind";
                    break;
                case 10:
                    objArr[0] = "typeParameters";
                    break;
                case 13:
                    objArr[0] = "substitution";
                    break;
                case 16:
                    objArr[0] = "name";
                    break;
                default:
                    objArr[0] = "owner";
                    break;
            }
            switch (i2) {
                case 1:
                    objArr[1] = "setOwner";
                    break;
                case 2:
                    objArr[1] = "setOriginal";
                    break;
                case 3:
                    objArr[1] = "setPreserveSourceElement";
                    break;
                case 5:
                    objArr[1] = "setModality";
                    break;
                case 7:
                    objArr[1] = "setVisibility";
                    break;
                case 9:
                    objArr[1] = "setKind";
                    break;
                case 11:
                    objArr[1] = "setTypeParameters";
                    break;
                case 12:
                    objArr[1] = "setDispatchReceiverParameter";
                    break;
                case 14:
                    objArr[1] = "setSubstitution";
                    break;
                case 15:
                    objArr[1] = "setCopyOverrides";
                    break;
                case 17:
                    objArr[1] = "setName";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyDescriptorImpl$CopyConfiguration";
                    break;
            }
            switch (i2) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 12:
                case 14:
                case 15:
                case 17:
                    break;
                case 4:
                    objArr[2] = "setModality";
                    break;
                case 6:
                    objArr[2] = "setVisibility";
                    break;
                case 8:
                    objArr[2] = "setKind";
                    break;
                case 10:
                    objArr[2] = "setTypeParameters";
                    break;
                case 13:
                    objArr[2] = "setSubstitution";
                    break;
                case 16:
                    objArr[2] = "setName";
                    break;
                default:
                    objArr[2] = "setOwner";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i2) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                case 12:
                case 14:
                case 15:
                case 17:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public a() {
            this.a = b0.this.b();
            this.b = b0.this.p();
            this.c = b0.this.getVisibility();
            this.f = b0.this.h();
            this.g = z0.a;
            this.h = true;
            this.i = b0.this.D4;
            this.j = null;
            this.k = b0.this.getName();
        }

        @NotNull
        public a t(@NotNull m owner) {
            if (owner == null) {
                a(0);
            }
            this.a = owner;
            return this;
        }

        @NotNull
        public a s(@Nullable b original) {
            this.d = (i0) original;
            return this;
        }

        @NotNull
        public a r(@NotNull w modality) {
            if (modality == null) {
                a(4);
            }
            this.b = modality;
            return this;
        }

        @NotNull
        public a v(@NotNull a1 visibility) {
            if (visibility == null) {
                a(6);
            }
            this.c = visibility;
            return this;
        }

        @NotNull
        public a q(@NotNull b.a kind) {
            if (kind == null) {
                a(8);
            }
            this.f = kind;
            return this;
        }

        @NotNull
        public a u(@NotNull z0 substitution) {
            if (substitution == null) {
                a(13);
            }
            this.g = substitution;
            return this;
        }

        @NotNull
        public a p(boolean copyOverrides) {
            this.h = copyOverrides;
            return this;
        }

        @Nullable
        public i0 m() {
            return b0.this.I0(this);
        }

        /* access modifiers changed from: package-private */
        public j0 n() {
            i0 i0Var = this.d;
            if (i0Var == null) {
                return null;
            }
            return i0Var.getGetter();
        }

        /* access modifiers changed from: package-private */
        public k0 o() {
            i0 i0Var = this.d;
            if (i0Var == null) {
                return null;
            }
            return i0Var.getSetter();
        }
    }

    @NotNull
    public a P0() {
        return new a();
    }

    @NotNull
    private o0 K0(boolean preserveSource, @Nullable i0 original) {
        o0 o0Var;
        if (preserveSource) {
            o0Var = (original != null ? original : c0()).n();
        } else {
            o0Var = o0.a;
        }
        if (o0Var == null) {
            u(23);
        }
        return o0Var;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public i0 I0(@NotNull a copyConfiguration) {
        l0 substitutedDispatchReceiver;
        e0 e0Var;
        c0 c0Var;
        c0 newGetter;
        d0 newSetter;
        kotlin.reflect.jvm.internal.impl.storage.g<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> gVar;
        if (copyConfiguration == null) {
            u(24);
        }
        b0 substitutedDescriptor = H0(copyConfiguration.a, copyConfiguration.b, copyConfiguration.c, copyConfiguration.d, copyConfiguration.f, copyConfiguration.k, K0(copyConfiguration.e, copyConfiguration.d));
        List<t0> typeParameters = copyConfiguration.j == null ? getTypeParameters() : copyConfiguration.j;
        List<TypeParameterDescriptor> substitutedTypeParameters = new ArrayList<>(typeParameters.size());
        TypeSubstitutor substitutor = p.b(typeParameters, copyConfiguration.g, substitutedDescriptor, substitutedTypeParameters);
        kotlin.reflect.jvm.internal.impl.types.b0 originalOutType = getType();
        h1 h1Var = h1.OUT_VARIANCE;
        kotlin.reflect.jvm.internal.impl.types.b0 outType = substitutor.n(originalOutType, h1Var);
        if (outType == null) {
            return null;
        }
        l0 dispatchReceiver = copyConfiguration.i;
        if (dispatchReceiver != null) {
            l0 substitutedDispatchReceiver2 = dispatchReceiver.c(substitutor);
            if (substitutedDispatchReceiver2 == null) {
                return null;
            }
            substitutedDispatchReceiver = substitutedDispatchReceiver2;
        } else {
            substitutedDispatchReceiver = null;
        }
        l0 l0Var = this.E4;
        if (l0Var != null) {
            kotlin.reflect.jvm.internal.impl.types.b0 substitutedReceiverType = substitutor.n(l0Var.getType(), h1.IN_VARIANCE);
            if (substitutedReceiverType == null) {
                return null;
            }
            e0Var = new e0(substitutedDescriptor, new kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.b(substitutedDescriptor, substitutedReceiverType, this.E4.getValue()), this.E4.getAnnotations());
        } else {
            e0Var = null;
        }
        substitutedDescriptor.S0(outType, substitutedTypeParameters, substitutedDispatchReceiver, e0Var);
        if (this.G4 == null) {
            c0Var = null;
            e0 e0Var2 = e0Var;
            l0 l0Var2 = substitutedDispatchReceiver;
            l0 l0Var3 = dispatchReceiver;
        } else {
            e0 e0Var3 = e0Var;
            l0 l0Var4 = substitutedDispatchReceiver;
            l0 l0Var5 = dispatchReceiver;
            c0Var = new c0(substitutedDescriptor, this.G4.getAnnotations(), copyConfiguration.b, Q0(this.G4.getVisibility(), copyConfiguration.f), this.G4.D(), this.G4.isExternal(), this.G4.isInline(), copyConfiguration.f, copyConfiguration.n(), o0.a);
        }
        c0 newGetter2 = c0Var;
        if (newGetter2 != null) {
            kotlin.reflect.jvm.internal.impl.types.b0 returnType = this.G4.getReturnType();
            newGetter2.G0(L0(substitutor, this.G4));
            newGetter2.J0(returnType != null ? substitutor.n(returnType, h1Var) : null);
        }
        if (this.H4 == null) {
            newSetter = null;
            newGetter = newGetter2;
        } else {
            newGetter = newGetter2;
            newSetter = new d0(substitutedDescriptor, this.H4.getAnnotations(), copyConfiguration.b, Q0(this.H4.getVisibility(), copyConfiguration.f), this.H4.D(), this.H4.isExternal(), this.H4.isInline(), copyConfiguration.f, copyConfiguration.o(), o0.a);
        }
        if (newSetter != null) {
            List<w0> I0 = p.I0(newSetter, this.H4.g(), substitutor, false, false, (boolean[]) null);
            if (I0 == null) {
                substitutedDescriptor.R0(true);
                I0 = Collections.singletonList(d0.I0(newSetter, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(copyConfiguration.a).J(), this.H4.g().get(0).getAnnotations()));
            }
            if (I0.size() == 1) {
                newSetter.G0(L0(substitutor, this.H4));
                newSetter.K0(I0.get(0));
            } else {
                throw new IllegalStateException();
            }
        }
        s sVar = this.J4;
        o oVar = sVar == null ? null : new o(sVar.getAnnotations(), substitutedDescriptor);
        s sVar2 = this.K4;
        substitutedDescriptor.N0(newGetter, newSetter, oVar, sVar2 == null ? null : new o(sVar2.getAnnotations(), substitutedDescriptor));
        if (copyConfiguration.h) {
            Collection<CallableMemberDescriptor> overridden = j.a();
            for (i0 propertyDescriptor : d()) {
                overridden.add(propertyDescriptor.c(substitutor));
            }
            substitutedDescriptor.y0(overridden);
        }
        if (isConst() && (gVar = this.z) != null) {
            substitutedDescriptor.l0(gVar);
        }
        return substitutedDescriptor;
    }

    private static a1 Q0(a1 prev, b.a kind) {
        if (kind != b.a.FAKE_OVERRIDE || !kotlin.reflect.jvm.internal.impl.descriptors.z0.h(prev.e())) {
            return prev;
        }
        return kotlin.reflect.jvm.internal.impl.descriptors.z0.h;
    }

    private static u L0(@NotNull TypeSubstitutor substitutor, @NotNull h0 accessorDescriptor) {
        if (substitutor == null) {
            u(25);
        }
        if (accessorDescriptor == null) {
            u(26);
        }
        if (accessorDescriptor.o0() != null) {
            return accessorDescriptor.o0().c(substitutor);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public b0 H0(@NotNull m newOwner, @NotNull w newModality, @NotNull a1 newVisibility, @Nullable i0 original, @NotNull b.a kind, @NotNull f newName, @NotNull o0 source) {
        if (newOwner == null) {
            u(27);
        }
        if (newModality == null) {
            u(28);
        }
        if (newVisibility == null) {
            u(29);
        }
        if (kind == null) {
            u(30);
        }
        if (newName == null) {
            u(31);
        }
        if (source == null) {
            u(32);
        }
        return new b0(newOwner, original, getAnnotations(), newModality, newVisibility, K(), newName, kind, source, t0(), isConst(), d0(), S(), isExternal(), z());
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.c(this, data);
    }

    @NotNull
    public i0 a() {
        i0 i0Var = this.a2;
        i0 a3 = i0Var == this ? this : i0Var.a();
        if (a3 == null) {
            u(33);
        }
        return a3;
    }

    @NotNull
    public b.a h() {
        b.a aVar = this.p2;
        if (aVar == null) {
            u(34);
        }
        return aVar;
    }

    public boolean d0() {
        return this.z4;
    }

    public boolean S() {
        return this.A4;
    }

    @Nullable
    public s s0() {
        return this.J4;
    }

    @Nullable
    public s M() {
        return this.K4;
    }

    public void y0(@NotNull Collection<? extends b> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            u(35);
        }
        this.p1 = overriddenDescriptors;
    }

    @NotNull
    public Collection<? extends i0> d() {
        Collection<? extends i0> collection = this.p1;
        if (collection == null) {
            collection = Collections.emptyList();
        }
        if (collection == null) {
            u(36);
        }
        return collection;
    }

    @NotNull
    /* renamed from: C0 */
    public i0 B0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        i0 m = P0().t(newOwner).s((b) null).r(modality).v(visibility).q(kind).p(copyOverrides).m();
        if (m == null) {
            u(37);
        }
        return m;
    }
}
