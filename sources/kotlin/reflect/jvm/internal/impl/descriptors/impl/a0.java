package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PropertyAccessorDescriptorImpl */
public abstract class a0 extends k implements h0 {
    private final boolean a1;
    private a1 a2;
    private final i0 p0;
    private final b.a p1;
    @Nullable
    private u p2;
    private boolean x;
    private final boolean y;
    private final w z;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
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
                objArr[0] = "visibility";
                break;
            case 2:
                objArr[0] = "correspondingProperty";
                break;
            case 3:
                objArr[0] = "annotations";
                break;
            case 4:
                objArr[0] = "name";
                break;
            case 5:
                objArr[0] = "source";
                break;
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyAccessorDescriptorImpl";
                break;
            case 7:
                objArr[0] = "substitutor";
                break;
            case 14:
                objArr[0] = "overriddenDescriptors";
                break;
            default:
                objArr[0] = "modality";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getKind";
                break;
            case 8:
                objArr[1] = "getTypeParameters";
                break;
            case 9:
                objArr[1] = "getModality";
                break;
            case 10:
                objArr[1] = "getVisibility";
                break;
            case 11:
                objArr[1] = "getCorrespondingVariable";
                break;
            case 12:
                objArr[1] = "getCorrespondingProperty";
                break;
            case 13:
                objArr[1] = "getOverriddenDescriptors";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyAccessorDescriptorImpl";
                break;
        }
        switch (i) {
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 7:
                objArr[2] = "substitute";
                break;
            case 14:
                objArr[2] = "setOverriddenDescriptors";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
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

    @NotNull
    /* renamed from: l0 */
    public abstract h0 c0();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a0(@NotNull w modality, @NotNull a1 visibility, @NotNull i0 correspondingProperty, @NotNull g annotations, @NotNull f name, boolean isDefault, boolean isExternal, boolean isInline, b.a kind, @NotNull o0 source) {
        super(correspondingProperty.b(), annotations, name, source);
        if (modality == null) {
            u(0);
        }
        if (visibility == null) {
            u(1);
        }
        if (correspondingProperty == null) {
            u(2);
        }
        if (annotations == null) {
            u(3);
        }
        if (name == null) {
            u(4);
        }
        if (source == null) {
            u(5);
        }
        this.p2 = null;
        this.z = modality;
        this.a2 = visibility;
        this.p0 = correspondingProperty;
        this.x = isDefault;
        this.y = isExternal;
        this.a1 = isInline;
        this.p1 = kind;
    }

    public boolean D() {
        return this.x;
    }

    public void C0(boolean aDefault) {
        this.x = aDefault;
    }

    @NotNull
    public b.a h() {
        b.a aVar = this.p1;
        if (aVar == null) {
            u(6);
        }
        return aVar;
    }

    public boolean isOperator() {
        return false;
    }

    public boolean isInfix() {
        return false;
    }

    public boolean isExternal() {
        return this.y;
    }

    public boolean isInline() {
        return this.a1;
    }

    public boolean A() {
        return false;
    }

    public boolean isSuspend() {
        return false;
    }

    public boolean d0() {
        return false;
    }

    public boolean S() {
        return false;
    }

    @NotNull
    public u c(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            u(7);
        }
        throw new UnsupportedOperationException();
    }

    @NotNull
    public List<t0> getTypeParameters() {
        List<t0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(8);
        }
        return emptyList;
    }

    public boolean Z() {
        return false;
    }

    @NotNull
    public w p() {
        w wVar = this.z;
        if (wVar == null) {
            u(9);
        }
        return wVar;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = this.a2;
        if (a1Var == null) {
            u(10);
        }
        return a1Var;
    }

    public void H0(a1 visibility) {
        this.a2 = visibility;
    }

    @NotNull
    public i0 Q() {
        i0 i0Var = this.p0;
        if (i0Var == null) {
            u(12);
        }
        return i0Var;
    }

    @Nullable
    public l0 L() {
        return Q().L();
    }

    @Nullable
    public l0 I() {
        return Q().I();
    }

    @NotNull
    /* renamed from: f0 */
    public h0 B0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        throw new UnsupportedOperationException("Accessors must be copied by the corresponding property");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Collection<h0> A0(boolean isGetter) {
        Collection<PropertyAccessorDescriptor> result = new ArrayList<>(0);
        for (i0 overriddenProperty : Q().d()) {
            h0 accessorDescriptor = isGetter ? overriddenProperty.getGetter() : overriddenProperty.getSetter();
            if (accessorDescriptor != null) {
                result.add(accessorDescriptor);
            }
        }
        return result;
    }

    public void y0(@NotNull Collection<? extends b> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            u(14);
        }
        if (!overriddenDescriptors.isEmpty()) {
            throw new AssertionError("Overridden accessors should be empty");
        }
    }

    @Nullable
    public u o0() {
        return this.p2;
    }

    public void G0(@Nullable u initialSignatureDescriptor) {
        this.p2 = initialSignatureDescriptor;
    }

    public boolean x0() {
        return false;
    }

    public boolean z0() {
        return false;
    }

    @Nullable
    public <V> V q0(a.C0348a<V> aVar) {
        return null;
    }
}
