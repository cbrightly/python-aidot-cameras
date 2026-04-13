package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractReceiverParameterDescriptor */
public abstract class c extends j implements l0 {
    private static final f f = f.k("<this>");

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
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
            case 6:
            case 7:
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractReceiverParameterDescriptor";
                break;
            default:
                objArr[0] = "annotations";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getTypeParameters";
                break;
            case 3:
                objArr[1] = "getType";
                break;
            case 4:
                objArr[1] = "getValueParameters";
                break;
            case 5:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 6:
                objArr[1] = "getVisibility";
                break;
            case 7:
                objArr[1] = "getOriginal";
                break;
            case 8:
                objArr[1] = "getSource";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractReceiverParameterDescriptor";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "substitute";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
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
            case 6:
            case 7:
            case 8:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull g annotations) {
        super(annotations, f);
        if (annotations == null) {
            u(0);
        }
    }

    @Nullable
    public l0 c(@NotNull TypeSubstitutor substitutor) {
        b0 substitutedType;
        if (substitutor == null) {
            u(1);
        }
        if (substitutor.k()) {
            return this;
        }
        if (b() instanceof e) {
            substitutedType = substitutor.n(getType(), h1.OUT_VARIANCE);
        } else {
            substitutedType = substitutor.n(getType(), h1.INVARIANT);
        }
        if (substitutedType == null) {
            return null;
        }
        if (substitutedType == getType()) {
            return this;
        }
        return new e0(b(), new kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.g(substitutedType), getAnnotations());
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.l(this, data);
    }

    @Nullable
    public l0 L() {
        return null;
    }

    @Nullable
    public l0 I() {
        return null;
    }

    @NotNull
    public List<t0> getTypeParameters() {
        List<t0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(2);
        }
        return emptyList;
    }

    @Nullable
    public b0 getReturnType() {
        return getType();
    }

    @NotNull
    public b0 getType() {
        b0 type = getValue().getType();
        if (type == null) {
            u(3);
        }
        return type;
    }

    @NotNull
    public List<w0> g() {
        List<w0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(4);
        }
        return emptyList;
    }

    public boolean Z() {
        return false;
    }

    @NotNull
    public Collection<? extends a> d() {
        Set emptySet = Collections.emptySet();
        if (emptySet == null) {
            u(5);
        }
        return emptySet;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = z0.f;
        if (a1Var == null) {
            u(6);
        }
        return a1Var;
    }

    @NotNull
    /* renamed from: c0 */
    public f0 a() {
        return this;
    }

    @NotNull
    public o0 n() {
        o0 o0Var = o0.a;
        if (o0Var == null) {
            u(8);
        }
        return o0Var;
    }
}
