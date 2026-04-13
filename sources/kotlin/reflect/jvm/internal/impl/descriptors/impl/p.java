package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.i;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: FunctionDescriptorImpl */
public abstract class p extends k implements u {
    private boolean A4;
    private boolean B4;
    private boolean C4;
    private boolean D4;
    private boolean E4;
    private boolean F4;
    private boolean G4;
    private boolean H4;
    private Collection<? extends u> I4;
    private volatile kotlin.jvm.functions.a<Collection<u>> J4;
    private final u K4;
    private final b.a L4;
    @Nullable
    private u M4;
    protected Map<a.C0348a<?>, Object> N4;
    /* access modifiers changed from: private */
    public l0 a1;
    private a1 a2;
    private l0 p0;
    private w p1;
    private boolean p2;
    private boolean p3;
    private boolean p4;
    private List<t0> x;
    private List<w0> y;
    private b0 z;
    private boolean z4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 8:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 8:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "kind";
                break;
            case 4:
                objArr[0] = "source";
                break;
            case 5:
                objArr[0] = "typeParameters";
                break;
            case 6:
            case 26:
            case 28:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 7:
            case 9:
                objArr[0] = "visibility";
                break;
            case 8:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl";
                break;
            case 10:
                objArr[0] = "unsubstitutedReturnType";
                break;
            case 11:
                objArr[0] = "extensionReceiverParameter";
                break;
            case 15:
                objArr[0] = "overriddenDescriptors";
                break;
            case 20:
                objArr[0] = "originalSubstitutor";
                break;
            case 22:
            case 27:
            case 29:
                objArr[0] = "substitutor";
                break;
            case 23:
                objArr[0] = "configuration";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 8:
                objArr[1] = "initialize";
                break;
            case 12:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 13:
                objArr[1] = "getModality";
                break;
            case 14:
                objArr[1] = "getVisibility";
                break;
            case 16:
                objArr[1] = "getTypeParameters";
                break;
            case 17:
                objArr[1] = "getValueParameters";
                break;
            case 18:
                objArr[1] = "getOriginal";
                break;
            case 19:
                objArr[1] = "getKind";
                break;
            case 21:
                objArr[1] = "newCopyBuilder";
                break;
            case 24:
                objArr[1] = "copy";
                break;
            case 25:
                objArr[1] = "getSourceToUseForCopy";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
                objArr[2] = "initialize";
                break;
            case 8:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
                break;
            case 9:
                objArr[2] = "setVisibility";
                break;
            case 10:
                objArr[2] = "setReturnType";
                break;
            case 11:
                objArr[2] = "setExtensionReceiverParameter";
                break;
            case 15:
                objArr[2] = "setOverriddenDescriptors";
                break;
            case 20:
                objArr[2] = "substitute";
                break;
            case 22:
                objArr[2] = "newCopyBuilder";
                break;
            case 23:
                objArr[2] = "doSubstitute";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                objArr[2] = "getSubstitutedValueParameters";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 8:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract p A0(@NotNull m mVar, @Nullable u uVar, @NotNull b.a aVar, @Nullable f fVar, @NotNull g gVar, @NotNull o0 o0Var);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected p(@NotNull m containingDeclaration, @Nullable u original, @NotNull g annotations, @NotNull f name, @NotNull b.a kind, @NotNull o0 source) {
        super(containingDeclaration, annotations, name, source);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (name == null) {
            u(2);
        }
        if (kind == null) {
            u(3);
        }
        if (source == null) {
            u(4);
        }
        this.a2 = z0.i;
        this.p2 = false;
        this.p3 = false;
        this.p4 = false;
        this.z4 = false;
        this.A4 = false;
        this.B4 = false;
        this.C4 = false;
        this.D4 = false;
        this.E4 = false;
        this.F4 = false;
        this.G4 = true;
        this.H4 = false;
        this.I4 = null;
        this.J4 = null;
        this.M4 = null;
        this.N4 = null;
        this.K4 = original == null ? this : original;
        this.L4 = kind;
    }

    @NotNull
    public p J0(@Nullable l0 extensionReceiverParameter, @Nullable l0 dispatchReceiverParameter, @NotNull List<? extends t0> typeParameters, @NotNull List<w0> unsubstitutedValueParameters, @Nullable b0 unsubstitutedReturnType, @Nullable w modality, @NotNull a1 visibility) {
        if (typeParameters == null) {
            u(5);
        }
        if (unsubstitutedValueParameters == null) {
            u(6);
        }
        if (visibility == null) {
            u(7);
        }
        this.x = y.C0(typeParameters);
        this.y = y.C0(unsubstitutedValueParameters);
        this.z = unsubstitutedReturnType;
        this.p1 = modality;
        this.a2 = visibility;
        this.p0 = extensionReceiverParameter;
        this.a1 = dispatchReceiverParameter;
        int i = 0;
        while (i < typeParameters.size()) {
            t0 typeParameterDescriptor = (t0) typeParameters.get(i);
            if (typeParameterDescriptor.getIndex() == i) {
                i++;
            } else {
                throw new IllegalStateException(typeParameterDescriptor + " index is " + typeParameterDescriptor.getIndex() + " but position is " + i);
            }
        }
        int i2 = 0;
        while (i2 < unsubstitutedValueParameters.size()) {
            w0 valueParameterDescriptor = unsubstitutedValueParameters.get(i2);
            if (valueParameterDescriptor.getIndex() == i2 + 0) {
                i2++;
            } else {
                throw new IllegalStateException(valueParameterDescriptor + "index is " + valueParameterDescriptor.getIndex() + " but position is " + i2);
            }
        }
        return this;
    }

    public void b1(@NotNull a1 visibility) {
        if (visibility == null) {
            u(9);
        }
        this.a2 = visibility;
    }

    public void X0(boolean isOperator) {
        this.p2 = isOperator;
    }

    public void U0(boolean isInfix) {
        this.p3 = isInfix;
    }

    public void P0(boolean isExternal) {
        this.p4 = isExternal;
    }

    public void W0(boolean isInline) {
        this.z4 = isInline;
    }

    public void a1(boolean isTailrec) {
        this.A4 = isTailrec;
    }

    public void O0(boolean isExpect) {
        this.B4 = isExpect;
    }

    public void N0(boolean isActual) {
        this.C4 = isActual;
    }

    private void T0(boolean hiddenToOvercomeSignatureClash) {
        this.D4 = hiddenToOvercomeSignatureClash;
    }

    private void S0(boolean hiddenForResolutionEverywhereBesideSupercalls) {
        this.E4 = hiddenForResolutionEverywhereBesideSupercalls;
    }

    public void Z0(boolean suspend) {
        this.F4 = suspend;
    }

    public void Y0(@NotNull b0 unsubstitutedReturnType) {
        if (unsubstitutedReturnType == null) {
            u(10);
        }
        this.z = unsubstitutedReturnType;
    }

    public void Q0(boolean hasStableParameterNames) {
        this.G4 = hasStableParameterNames;
    }

    public void R0(boolean hasSynthesizedParameterNames) {
        this.H4 = hasSynthesizedParameterNames;
    }

    @Nullable
    public l0 L() {
        return this.p0;
    }

    @Nullable
    public l0 I() {
        return this.a1;
    }

    @NotNull
    public Collection<? extends u> d() {
        L0();
        Collection<? extends u> collection = this.I4;
        if (collection == null) {
            collection = Collections.emptyList();
        }
        if (collection == null) {
            u(12);
        }
        return collection;
    }

    private void L0() {
        Function0<Collection<FunctionDescriptor>> overriddenTask = this.J4;
        if (overriddenTask != null) {
            this.I4 = overriddenTask.invoke();
            this.J4 = null;
        }
    }

    @NotNull
    public w p() {
        w wVar = this.p1;
        if (wVar == null) {
            u(13);
        }
        return wVar;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = this.a2;
        if (a1Var == null) {
            u(14);
        }
        return a1Var;
    }

    public boolean isOperator() {
        if (this.p2) {
            return true;
        }
        for (u descriptor : c0().d()) {
            if (descriptor.isOperator()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInfix() {
        if (this.p3) {
            return true;
        }
        for (u descriptor : c0().d()) {
            if (descriptor.isInfix()) {
                return true;
            }
        }
        return false;
    }

    public boolean isExternal() {
        return this.p4;
    }

    public boolean isInline() {
        return this.z4;
    }

    public boolean A() {
        return this.A4;
    }

    public boolean isSuspend() {
        return this.F4;
    }

    public boolean d0() {
        return this.B4;
    }

    public boolean S() {
        return this.C4;
    }

    public <V> V q0(a.C0348a<V> key) {
        Map map = this.N4;
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    public boolean x0() {
        return this.D4;
    }

    public void y0(@NotNull Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            u(15);
        }
        this.I4 = overriddenDescriptors;
        Iterator i$ = overriddenDescriptors.iterator();
        while (i$.hasNext()) {
            if (((u) i$.next()).z0()) {
                this.E4 = true;
                return;
            }
        }
    }

    @NotNull
    public List<t0> getTypeParameters() {
        List<t0> list = this.x;
        if (list == null) {
            u(16);
        }
        return list;
    }

    @NotNull
    public List<w0> g() {
        List<w0> list = this.y;
        if (list == null) {
            u(17);
        }
        return list;
    }

    public boolean Z() {
        return this.H4;
    }

    public b0 getReturnType() {
        return this.z;
    }

    @NotNull
    public u a() {
        u uVar = this.K4;
        u a3 = uVar == this ? this : uVar.a();
        if (a3 == null) {
            u(18);
        }
        return a3;
    }

    @NotNull
    public b.a h() {
        b.a aVar = this.L4;
        if (aVar == null) {
            u(19);
        }
        return aVar;
    }

    public u c(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor == null) {
            u(20);
        }
        if (originalSubstitutor.k()) {
            return this;
        }
        return K0(originalSubstitutor).m(c0()).k().I(true).build();
    }

    public boolean z0() {
        return this.E4;
    }

    /* compiled from: FunctionDescriptorImpl */
    public class c implements u.a<u> {
        @NotNull
        protected kotlin.reflect.jvm.internal.impl.types.z0 a;
        @NotNull
        protected m b;
        @NotNull
        protected w c;
        @NotNull
        protected a1 d;
        @Nullable
        protected u e;
        @NotNull
        protected b.a f;
        @NotNull
        protected List<w0> g;
        @Nullable
        protected l0 h;
        @Nullable
        protected l0 i;
        @NotNull
        protected b0 j;
        @Nullable
        protected f k;
        protected boolean l;
        protected boolean m;
        protected boolean n;
        protected boolean o;
        /* access modifiers changed from: private */
        public boolean p;
        /* access modifiers changed from: private */
        public List<t0> q;
        /* access modifiers changed from: private */
        public g r;
        /* access modifiers changed from: private */
        public boolean s;
        /* access modifiers changed from: private */
        public Map<a.C0348a<?>, Object> t;
        /* access modifiers changed from: private */
        public Boolean u;
        protected boolean v;
        final /* synthetic */ p w;

        private static /* synthetic */ void t(int i2) {
            String str;
            int i3;
            Throwable th;
            switch (i2) {
                case 8:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 19:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 33:
                case 35:
                case 37:
                case 38:
                case 39:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i2) {
                case 8:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 19:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 33:
                case 35:
                case 37:
                case 38:
                case 39:
                    i3 = 2;
                    break;
                default:
                    i3 = 3;
                    break;
            }
            Object[] objArr = new Object[i3];
            switch (i2) {
                case 1:
                    objArr[0] = "newOwner";
                    break;
                case 2:
                    objArr[0] = "newModality";
                    break;
                case 3:
                    objArr[0] = "newVisibility";
                    break;
                case 4:
                case 13:
                    objArr[0] = "kind";
                    break;
                case 5:
                    objArr[0] = "newValueParameterDescriptors";
                    break;
                case 6:
                    objArr[0] = "newReturnType";
                    break;
                case 7:
                    objArr[0] = "owner";
                    break;
                case 8:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 19:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 33:
                case 35:
                case 37:
                case 38:
                case 39:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
                case 9:
                    objArr[0] = "modality";
                    break;
                case 11:
                    objArr[0] = "visibility";
                    break;
                case 16:
                    objArr[0] = "name";
                    break;
                case 18:
                case 20:
                    objArr[0] = "parameters";
                    break;
                case 22:
                    objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                    break;
                case 32:
                    objArr[0] = "additionalAnnotations";
                    break;
                case 36:
                    objArr[0] = "userDataKey";
                    break;
                default:
                    objArr[0] = "substitution";
                    break;
            }
            switch (i2) {
                case 8:
                    objArr[1] = "setOwner";
                    break;
                case 10:
                    objArr[1] = "setModality";
                    break;
                case 12:
                    objArr[1] = "setVisibility";
                    break;
                case 14:
                    objArr[1] = "setKind";
                    break;
                case 15:
                    objArr[1] = "setCopyOverrides";
                    break;
                case 17:
                    objArr[1] = "setName";
                    break;
                case 19:
                    objArr[1] = "setValueParameters";
                    break;
                case 21:
                    objArr[1] = "setTypeParameters";
                    break;
                case 23:
                    objArr[1] = "setReturnType";
                    break;
                case 24:
                    objArr[1] = "setExtensionReceiverParameter";
                    break;
                case 25:
                    objArr[1] = "setDispatchReceiverParameter";
                    break;
                case 26:
                    objArr[1] = "setOriginal";
                    break;
                case 27:
                    objArr[1] = "setSignatureChange";
                    break;
                case 28:
                    objArr[1] = "setPreserveSourceElement";
                    break;
                case 29:
                    objArr[1] = "setDropOriginalInContainingParts";
                    break;
                case 30:
                    objArr[1] = "setHiddenToOvercomeSignatureClash";
                    break;
                case 31:
                    objArr[1] = "setHiddenForResolutionEverywhereBesideSupercalls";
                    break;
                case 33:
                    objArr[1] = "setAdditionalAnnotations";
                    break;
                case 35:
                    objArr[1] = "setSubstitution";
                    break;
                case 37:
                    objArr[1] = "putUserData";
                    break;
                case 38:
                    objArr[1] = "getSubstitution";
                    break;
                case 39:
                    objArr[1] = "setJustForTypeSubstitution";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
            }
            switch (i2) {
                case 7:
                    objArr[2] = "setOwner";
                    break;
                case 8:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 19:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 33:
                case 35:
                case 37:
                case 38:
                case 39:
                    break;
                case 9:
                    objArr[2] = "setModality";
                    break;
                case 11:
                    objArr[2] = "setVisibility";
                    break;
                case 13:
                    objArr[2] = "setKind";
                    break;
                case 16:
                    objArr[2] = "setName";
                    break;
                case 18:
                    objArr[2] = "setValueParameters";
                    break;
                case 20:
                    objArr[2] = "setTypeParameters";
                    break;
                case 22:
                    objArr[2] = "setReturnType";
                    break;
                case 32:
                    objArr[2] = "setAdditionalAnnotations";
                    break;
                case 34:
                    objArr[2] = "setSubstitution";
                    break;
                case 36:
                    objArr[2] = "putUserData";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i2) {
                case 8:
                case 10:
                case 12:
                case 14:
                case 15:
                case 17:
                case 19:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 33:
                case 35:
                case 37:
                case 38:
                case 39:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public c(@NotNull p pVar, @NotNull kotlin.reflect.jvm.internal.impl.types.z0 substitution, @NotNull m newOwner, @NotNull w newModality, @NotNull a1 newVisibility, @NotNull b.a kind, @Nullable List<w0> newValueParameterDescriptors, @NotNull l0 newExtensionReceiverParameter, @Nullable b0 newReturnType, f name) {
            if (substitution == null) {
                t(0);
            }
            if (newOwner == null) {
                t(1);
            }
            if (newModality == null) {
                t(2);
            }
            if (newVisibility == null) {
                t(3);
            }
            if (kind == null) {
                t(4);
            }
            if (newValueParameterDescriptors == null) {
                t(5);
            }
            if (newReturnType == null) {
                t(6);
            }
            this.w = pVar;
            this.e = null;
            this.i = pVar.a1;
            this.l = true;
            this.m = false;
            this.n = false;
            this.o = false;
            this.p = pVar.x0();
            this.q = null;
            this.r = null;
            this.s = pVar.z0();
            this.t = new LinkedHashMap();
            this.u = null;
            this.v = false;
            this.a = substitution;
            this.b = newOwner;
            this.c = newModality;
            this.d = newVisibility;
            this.f = kind;
            this.g = newValueParameterDescriptors;
            this.h = newExtensionReceiverParameter;
            this.j = newReturnType;
            this.k = name;
        }

        @NotNull
        /* renamed from: N */
        public c p(@NotNull m owner) {
            if (owner == null) {
                t(7);
            }
            this.b = owner;
            return this;
        }

        @NotNull
        /* renamed from: K */
        public c j(@NotNull w modality) {
            if (modality == null) {
                t(9);
            }
            this.c = modality;
            return this;
        }

        @NotNull
        /* renamed from: U */
        public c c(@NotNull a1 visibility) {
            if (visibility == null) {
                t(11);
            }
            this.d = visibility;
            return this;
        }

        @NotNull
        /* renamed from: J */
        public c q(@NotNull b.a kind) {
            if (kind == null) {
                t(13);
            }
            this.f = kind;
            return this;
        }

        @NotNull
        /* renamed from: B */
        public c n(boolean copyOverrides) {
            this.l = copyOverrides;
            return this;
        }

        @NotNull
        /* renamed from: L */
        public c i(@NotNull f name) {
            if (name == null) {
                t(16);
            }
            this.k = name;
            return this;
        }

        @NotNull
        /* renamed from: T */
        public c b(@NotNull List<w0> parameters) {
            if (parameters == null) {
                t(18);
            }
            this.g = parameters;
            return this;
        }

        @NotNull
        /* renamed from: S */
        public c o(@NotNull List<t0> parameters) {
            if (parameters == null) {
                t(20);
            }
            this.q = parameters;
            return this;
        }

        @NotNull
        /* renamed from: P */
        public c l(@NotNull b0 type) {
            if (type == null) {
                t(22);
            }
            this.j = type;
            return this;
        }

        @NotNull
        /* renamed from: E */
        public c f(@Nullable l0 extensionReceiverParameter) {
            this.h = extensionReceiverParameter;
            return this;
        }

        @NotNull
        /* renamed from: C */
        public c d(@Nullable l0 dispatchReceiverParameter) {
            this.i = dispatchReceiverParameter;
            return this;
        }

        @NotNull
        /* renamed from: M */
        public c m(@Nullable kotlin.reflect.jvm.internal.impl.descriptors.b original) {
            this.e = (u) original;
            return this;
        }

        @NotNull
        /* renamed from: Q */
        public c s() {
            this.m = true;
            return this;
        }

        @NotNull
        /* renamed from: O */
        public c k() {
            this.n = true;
            return this;
        }

        @NotNull
        /* renamed from: D */
        public c a() {
            this.o = true;
            return this;
        }

        @NotNull
        /* renamed from: H */
        public c h() {
            this.p = true;
            return this;
        }

        @NotNull
        /* renamed from: G */
        public c e() {
            this.s = true;
            return this;
        }

        @NotNull
        /* renamed from: A */
        public c r(@NotNull g additionalAnnotations) {
            if (additionalAnnotations == null) {
                t(32);
            }
            this.r = additionalAnnotations;
            return this;
        }

        public c F(boolean value) {
            this.u = Boolean.valueOf(value);
            return this;
        }

        @NotNull
        /* renamed from: R */
        public c g(@NotNull kotlin.reflect.jvm.internal.impl.types.z0 substitution) {
            if (substitution == null) {
                t(34);
            }
            this.a = substitution;
            return this;
        }

        @Nullable
        public u build() {
            return this.w.C0(this);
        }

        @NotNull
        public c I(boolean value) {
            this.v = value;
            return this;
        }
    }

    @NotNull
    public u.a<? extends u> r() {
        c K0 = K0(TypeSubstitutor.a);
        if (K0 == null) {
            u(21);
        }
        return K0;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public c K0(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            u(22);
        }
        return new c(this, substitutor.j(), b(), p(), getVisibility(), h(), g(), L(), getReturnType(), (f) null);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public u C0(@NotNull c configuration) {
        l0 substitutedReceiverParameter;
        l0 substitutedExpectedThis;
        b0 substitutedReturnType;
        c cVar = configuration;
        if (cVar == null) {
            u(23);
        }
        boolean[] wereChanges = new boolean[1];
        g resultAnnotations = configuration.r != null ? i.a(getAnnotations(), configuration.r) : getAnnotations();
        m mVar = cVar.b;
        u uVar = cVar.e;
        p substitutedDescriptor = A0(mVar, uVar, cVar.f, cVar.k, resultAnnotations, G0(cVar.n, uVar));
        List<t0> typeParameters = configuration.q == null ? getTypeParameters() : configuration.q;
        wereChanges[0] = wereChanges[0] | (!typeParameters.isEmpty());
        ArrayList arrayList = new ArrayList(typeParameters.size());
        TypeSubstitutor substitutor = kotlin.reflect.jvm.internal.impl.types.p.c(typeParameters, cVar.a, substitutedDescriptor, arrayList, wereChanges);
        if (substitutor == null) {
            return null;
        }
        l0 l0Var = cVar.h;
        if (l0Var != null) {
            b0 substitutedExtensionReceiverType = substitutor.n(l0Var.getType(), h1.IN_VARIANCE);
            if (substitutedExtensionReceiverType == null) {
                return null;
            }
            l0 substitutedReceiverParameter2 = new e0(substitutedDescriptor, new kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.b(substitutedDescriptor, substitutedExtensionReceiverType, cVar.h.getValue()), cVar.h.getAnnotations());
            wereChanges[0] = wereChanges[0] | (substitutedExtensionReceiverType != cVar.h.getType());
            substitutedReceiverParameter = substitutedReceiverParameter2;
        } else {
            substitutedReceiverParameter = null;
        }
        l0 l0Var2 = cVar.i;
        if (l0Var2 != null) {
            l0 substitutedExpectedThis2 = l0Var2.c(substitutor);
            if (substitutedExpectedThis2 == null) {
                return null;
            }
            wereChanges[0] = wereChanges[0] | (substitutedExpectedThis2 != cVar.i);
            substitutedExpectedThis = substitutedExpectedThis2;
        } else {
            substitutedExpectedThis = null;
        }
        boolean[] wereChanges2 = wereChanges;
        List<w0> I0 = I0(substitutedDescriptor, cVar.g, substitutor, cVar.o, cVar.n, wereChanges);
        if (I0 == null || (substitutedReturnType = substitutor.n(cVar.j, h1.OUT_VARIANCE)) == null) {
            return null;
        }
        wereChanges2[0] = wereChanges2[0] | (substitutedReturnType != cVar.j);
        if (!wereChanges2[0] && cVar.v) {
            return this;
        }
        substitutedDescriptor.J0(substitutedReceiverParameter, substitutedExpectedThis, arrayList, I0, substitutedReturnType, cVar.c, cVar.d);
        substitutedDescriptor.X0(this.p2);
        substitutedDescriptor.U0(this.p3);
        substitutedDescriptor.P0(this.p4);
        substitutedDescriptor.W0(this.z4);
        substitutedDescriptor.a1(this.A4);
        substitutedDescriptor.Z0(this.F4);
        substitutedDescriptor.O0(this.B4);
        substitutedDescriptor.N0(this.C4);
        substitutedDescriptor.Q0(this.G4);
        substitutedDescriptor.T0(configuration.p);
        substitutedDescriptor.S0(configuration.s);
        substitutedDescriptor.R0(configuration.u != null ? configuration.u.booleanValue() : this.H4);
        if (!configuration.t.isEmpty() || this.N4 != null) {
            Map<CallableDescriptor.UserDataKey<?>, Object> newMap = configuration.t;
            Map<a.C0348a<?>, Object> map = this.N4;
            if (map != null) {
                for (Map.Entry<CallableDescriptor.UserDataKey<?>, Object> entry : map.entrySet()) {
                    if (!newMap.containsKey(entry.getKey())) {
                        newMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (newMap.size() == 1) {
                substitutedDescriptor.N4 = Collections.singletonMap(newMap.keySet().iterator().next(), newMap.values().iterator().next());
            } else {
                substitutedDescriptor.N4 = newMap;
            }
        }
        if (cVar.m || o0() != null) {
            substitutedDescriptor.V0((o0() != null ? o0() : this).c(substitutor));
        }
        if (cVar.l && !c0().d().isEmpty()) {
            if (cVar.a.f()) {
                Function0<Collection<FunctionDescriptor>> overriddenFunctionsTask = this.J4;
                if (overriddenFunctionsTask != null) {
                    substitutedDescriptor.J4 = overriddenFunctionsTask;
                } else {
                    substitutedDescriptor.y0(d());
                }
            } else {
                substitutedDescriptor.J4 = new a(substitutor);
            }
        }
        return substitutedDescriptor;
    }

    /* compiled from: FunctionDescriptorImpl */
    public class a implements kotlin.jvm.functions.a<Collection<u>> {
        final /* synthetic */ TypeSubstitutor c;

        a(TypeSubstitutor typeSubstitutor) {
            this.c = typeSubstitutor;
        }

        /* renamed from: a */
        public Collection<u> invoke() {
            Collection<FunctionDescriptor> result = new kotlin.reflect.jvm.internal.impl.utils.i<>();
            for (u overriddenFunction : p.this.d()) {
                result.add(overriddenFunction.c(this.c));
            }
            return result;
        }
    }

    @NotNull
    /* renamed from: l0 */
    public u B0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        u build = r().p(newOwner).j(modality).c(visibility).q(kind).n(copyOverrides).build();
        if (build == null) {
            u(24);
        }
        return build;
    }

    @NotNull
    private o0 G0(boolean preserveSource, @Nullable u original) {
        o0 o0Var;
        if (preserveSource) {
            o0Var = (original != null ? original : c0()).n();
        } else {
            o0Var = o0.a;
        }
        if (o0Var == null) {
            u(25);
        }
        return o0Var;
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.i(this, data);
    }

    @Nullable
    public static List<w0> H0(u substitutedDescriptor, @NotNull List<w0> unsubstitutedValueParameters, @NotNull TypeSubstitutor substitutor) {
        if (unsubstitutedValueParameters == null) {
            u(26);
        }
        if (substitutor == null) {
            u(27);
        }
        return I0(substitutedDescriptor, unsubstitutedValueParameters, substitutor, false, false, (boolean[]) null);
    }

    @Nullable
    public static List<w0> I0(u substitutedDescriptor, @NotNull List<w0> unsubstitutedValueParameters, @NotNull TypeSubstitutor substitutor, boolean dropOriginal, boolean preserveSourceElement, @Nullable boolean[] wereChanges) {
        b bVar;
        TypeSubstitutor typeSubstitutor = substitutor;
        if (unsubstitutedValueParameters == null) {
            u(28);
        }
        if (typeSubstitutor == null) {
            u(29);
        }
        List<ValueParameterDescriptor> result = new ArrayList<>(unsubstitutedValueParameters.size());
        for (w0 unsubstitutedValueParameter : unsubstitutedValueParameters) {
            b0 type = unsubstitutedValueParameter.getType();
            h1 h1Var = h1.IN_VARIANCE;
            b0 substitutedType = typeSubstitutor.n(type, h1Var);
            b0 varargElementType = unsubstitutedValueParameter.r0();
            b0 substituteVarargElementType = varargElementType == null ? null : typeSubstitutor.n(varargElementType, h1Var);
            if (substitutedType == null) {
                return null;
            }
            if (!((substitutedType == unsubstitutedValueParameter.getType() && varargElementType == substituteVarargElementType) || wereChanges == null)) {
                wereChanges[0] = true;
            }
            if (unsubstitutedValueParameter instanceof k0.b) {
                bVar = new b(((k0.b) unsubstitutedValueParameter).G0());
            } else {
                bVar = null;
            }
            b0 b0Var = varargElementType;
            result.add(k0.l0(substitutedDescriptor, dropOriginal ? null : unsubstitutedValueParameter, unsubstitutedValueParameter.getIndex(), unsubstitutedValueParameter.getAnnotations(), unsubstitutedValueParameter.getName(), substitutedType, unsubstitutedValueParameter.v0(), unsubstitutedValueParameter.n0(), unsubstitutedValueParameter.k0(), substituteVarargElementType, preserveSourceElement ? unsubstitutedValueParameter.n() : o0.a, bVar));
        }
        return result;
    }

    /* compiled from: FunctionDescriptorImpl */
    public static final class b implements kotlin.jvm.functions.a<List<x0>> {
        final /* synthetic */ List c;

        b(List list) {
            this.c = list;
        }

        /* renamed from: a */
        public List<x0> invoke() {
            return this.c;
        }
    }

    @Nullable
    public u o0() {
        return this.M4;
    }

    private void V0(@Nullable u initialSignatureDescriptor) {
        this.M4 = initialSignatureDescriptor;
    }

    public <V> void M0(a.C0348a<V> key, Object value) {
        if (this.N4 == null) {
            this.N4 = new LinkedHashMap();
        }
        this.N4.put(key, value);
    }
}
