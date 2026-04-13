package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TypeParameterDescriptorImpl */
public class j0 extends e {
    @Nullable
    private final l<b0, Void> a2;
    private final List<b0> p2;
    private boolean p3;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 5:
            case 28:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 28:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 7:
            case 13:
            case 20:
                objArr[0] = "annotations";
                break;
            case 2:
            case 8:
            case 14:
            case 21:
                objArr[0] = "variance";
                break;
            case 3:
            case 9:
            case 15:
            case 22:
                objArr[0] = "name";
                break;
            case 4:
            case 11:
            case 18:
            case 25:
                objArr[0] = "storageManager";
                break;
            case 5:
            case 28:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
                break;
            case 10:
            case 16:
            case 23:
                objArr[0] = "source";
                break;
            case 17:
                objArr[0] = "supertypeLoopsResolver";
                break;
            case 24:
                objArr[0] = "supertypeLoopsChecker";
                break;
            case 26:
                objArr[0] = "bound";
                break;
            case 27:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "createWithDefaultBound";
                break;
            case 28:
                objArr[1] = "resolveUpperBounds";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
                break;
        }
        switch (i) {
            case 5:
            case 28:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "createForFurtherModification";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "<init>";
                break;
            case 26:
                objArr[2] = "addUpperBound";
                break;
            case 27:
                objArr[2] = "reportSupertypeLoopError";
                break;
            default:
                objArr[2] = "createWithDefaultBound";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 5:
            case 28:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static t0 J0(@NotNull m containingDeclaration, @NotNull g annotations, boolean reified, @NotNull h1 variance, @NotNull f name, int index, @NotNull j storageManager) {
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (variance == null) {
            u(2);
        }
        if (name == null) {
            u(3);
        }
        if (storageManager == null) {
            u(4);
        }
        j0 typeParameterDescriptor = I0(containingDeclaration, annotations, reified, variance, name, index, o0.a, storageManager);
        typeParameterDescriptor.A0(a.h(containingDeclaration).y());
        typeParameterDescriptor.M0();
        return typeParameterDescriptor;
    }

    public static j0 I0(@NotNull m containingDeclaration, @NotNull g annotations, boolean reified, @NotNull h1 variance, @NotNull f name, int index, @NotNull o0 source, @NotNull j storageManager) {
        if (containingDeclaration == null) {
            u(6);
        }
        if (annotations == null) {
            u(7);
        }
        if (variance == null) {
            u(8);
        }
        if (name == null) {
            u(9);
        }
        if (source == null) {
            u(10);
        }
        if (storageManager == null) {
            u(11);
        }
        return H0(containingDeclaration, annotations, reified, variance, name, index, source, (l<b0, Void>) null, r0.a.a, storageManager);
    }

    public static j0 H0(@NotNull m containingDeclaration, @NotNull g annotations, boolean reified, @NotNull h1 variance, @NotNull f name, int index, @NotNull o0 source, @Nullable l<b0, Void> reportCycleError, @NotNull r0 supertypeLoopsResolver, @NotNull j storageManager) {
        if (containingDeclaration == null) {
            u(12);
        }
        if (annotations == null) {
            u(13);
        }
        if (variance == null) {
            u(14);
        }
        if (name == null) {
            u(15);
        }
        if (source == null) {
            u(16);
        }
        if (supertypeLoopsResolver == null) {
            u(17);
        }
        if (storageManager == null) {
            u(18);
        }
        return new j0(containingDeclaration, annotations, reified, variance, name, index, source, reportCycleError, supertypeLoopsResolver, storageManager);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private j0(@NotNull m containingDeclaration, @NotNull g annotations, boolean reified, @NotNull h1 variance, @NotNull f name, int index, @NotNull o0 source, @Nullable l<b0, Void> reportCycleError, @NotNull r0 supertypeLoopsChecker, @NotNull j storageManager) {
        super(storageManager, containingDeclaration, annotations, name, variance, reified, index, source, supertypeLoopsChecker);
        if (containingDeclaration == null) {
            u(19);
        }
        if (annotations == null) {
            u(20);
        }
        if (variance == null) {
            u(21);
        }
        if (name == null) {
            u(22);
        }
        if (source == null) {
            u(23);
        }
        if (supertypeLoopsChecker == null) {
            u(24);
        }
        if (storageManager == null) {
            u(25);
        }
        this.p2 = new ArrayList(1);
        this.p3 = false;
        this.a2 = reportCycleError;
    }

    private void C0() {
        if (!this.p3) {
            throw new IllegalStateException("Type parameter descriptor is not initialized: " + L0());
        }
    }

    private void G0() {
        if (this.p3) {
            throw new IllegalStateException("Type parameter descriptor is already initialized: " + L0());
        }
    }

    private String L0() {
        return getName() + " declared in " + c.m(b());
    }

    public void M0() {
        G0();
        this.p3 = true;
    }

    public void A0(@NotNull b0 bound) {
        if (bound == null) {
            u(26);
        }
        G0();
        K0(bound);
    }

    private void K0(b0 bound) {
        if (!d0.a(bound)) {
            this.p2.add(bound);
        }
    }

    /* access modifiers changed from: protected */
    public void f0(@NotNull b0 type) {
        if (type == null) {
            u(27);
        }
        l<b0, Void> lVar = this.a2;
        if (lVar != null) {
            lVar.invoke(type);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<b0> l0() {
        C0();
        List<b0> list = this.p2;
        if (list == null) {
            u(28);
        }
        return list;
    }
}
