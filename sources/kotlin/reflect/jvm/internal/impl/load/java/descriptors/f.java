package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.util.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaMethodDescriptor */
public class f extends f0 implements b {
    public static final a.C0348a<w0> O4 = new a();
    private b P4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 12:
            case 17:
            case 20:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 12:
            case 17:
            case 20:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 6:
            case 15:
                objArr[0] = "annotations";
                break;
            case 2:
            case 7:
                objArr[0] = "name";
                break;
            case 3:
            case 14:
                objArr[0] = "kind";
                break;
            case 4:
            case 8:
            case 16:
                objArr[0] = "source";
                break;
            case 9:
                objArr[0] = "typeParameters";
                break;
            case 10:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 11:
                objArr[0] = "visibility";
                break;
            case 12:
            case 17:
            case 20:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaMethodDescriptor";
                break;
            case 13:
                objArr[0] = "newOwner";
                break;
            case 18:
                objArr[0] = "enhancedValueParametersData";
                break;
            case 19:
                objArr[0] = "enhancedReturnType";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 12:
                objArr[1] = "initialize";
                break;
            case 17:
                objArr[1] = "createSubstitutedCopy";
                break;
            case 20:
                objArr[1] = "enhance";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaMethodDescriptor";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "createJavaMethod";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "initialize";
                break;
            case 12:
            case 17:
            case 20:
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 18:
            case 19:
                objArr[2] = "enhance";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 12:
            case 17:
            case 20:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* compiled from: JavaMethodDescriptor */
    public static final class a implements a.C0348a<w0> {
        a() {
        }
    }

    /* compiled from: JavaMethodDescriptor */
    public enum b {
        NON_STABLE_DECLARED(false, false),
        STABLE_DECLARED(true, false),
        NON_STABLE_SYNTHESIZED(false, true),
        STABLE_SYNTHESIZED(true, true);
        
        public final boolean isStable;
        public final boolean isSynthesized;

        private b(boolean isStable2, boolean isSynthesized2) {
            this.isStable = isStable2;
            this.isSynthesized = isSynthesized2;
        }

        @NotNull
        public static b get(boolean stable, boolean synthesized) {
            b bVar = stable ? synthesized ? STABLE_SYNTHESIZED : STABLE_DECLARED : synthesized ? NON_STABLE_SYNTHESIZED : NON_STABLE_DECLARED;
            if (bVar == null) {
                a(0);
            }
            return bVar;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected f(@NotNull m containingDeclaration, @Nullable n0 original, @NotNull g annotations, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b.a kind, @NotNull o0 source) {
        super(containingDeclaration, original, annotations, name, kind, source);
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
        this.P4 = null;
    }

    @NotNull
    public static f h1(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull o0 source) {
        if (containingDeclaration == null) {
            u(5);
        }
        if (annotations == null) {
            u(6);
        }
        if (name == null) {
            u(7);
        }
        if (source == null) {
            u(8);
        }
        return new f(containingDeclaration, (n0) null, annotations, name, b.a.DECLARATION, source);
    }

    @NotNull
    public f0 g1(@Nullable l0 extensionReceiverParameter, @Nullable l0 dispatchReceiverParameter, @NotNull List<? extends t0> typeParameters, @NotNull List<w0> unsubstitutedValueParameters, @Nullable b0 unsubstitutedReturnType, @Nullable w modality, @NotNull a1 visibility, @Nullable Map<? extends a.C0348a<?>, ?> userData) {
        if (typeParameters == null) {
            u(9);
        }
        if (unsubstitutedValueParameters == null) {
            u(10);
        }
        if (visibility == null) {
            u(11);
        }
        f0 descriptor = super.g1(extensionReceiverParameter, dispatchReceiverParameter, typeParameters, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userData);
        X0(i.b.a(descriptor).a());
        if (descriptor == null) {
            u(12);
        }
        return descriptor;
    }

    public boolean k1() {
        b bVar = this.P4;
        if (bVar != null) {
            return bVar.isStable;
        }
        throw new AssertionError("Parameter names status was not set: " + this);
    }

    public boolean Z() {
        b bVar = this.P4;
        if (bVar != null) {
            return bVar.isSynthesized;
        }
        throw new AssertionError("Parameter names status was not set: " + this);
    }

    public void l1(boolean hasStableParameterNames, boolean hasSynthesizedParameterNames) {
        this.P4 = b.get(hasStableParameterNames, hasSynthesizedParameterNames);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: i1 */
    public f A0(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        if (newOwner == null) {
            u(13);
        }
        if (kind == null) {
            u(14);
        }
        if (annotations == null) {
            u(15);
        }
        if (source == null) {
            u(16);
        }
        f fVar = new f(newOwner, (n0) original, annotations, newName != null ? newName : getName(), kind, source);
        fVar.l1(k1(), Z());
        return fVar;
    }

    @NotNull
    /* renamed from: j1 */
    public f U(@Nullable b0 enhancedReceiverType, @NotNull List<l> enhancedValueParametersData, @NotNull b0 enhancedReturnType, @Nullable n<a.C0348a<?>, ?> additionalUserData) {
        if (enhancedValueParametersData == null) {
            u(18);
        }
        if (enhancedReturnType == null) {
            u(19);
        }
        f enhancedMethod = (f) r().b(k.a(enhancedValueParametersData, g(), this)).l(enhancedReturnType).f(enhancedReceiverType == null ? null : kotlin.reflect.jvm.internal.impl.resolve.b.f(this, enhancedReceiverType, g.b.b())).a().k().build();
        if (enhancedMethod != null) {
            if (additionalUserData != null) {
                enhancedMethod.M0(additionalUserData.getFirst(), additionalUserData.getSecond());
            }
            if (enhancedMethod == null) {
                u(20);
            }
            return enhancedMethod;
        }
        throw new AssertionError("null after substitution while enhancing " + toString());
    }
}
