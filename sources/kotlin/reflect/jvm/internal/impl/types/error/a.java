package kotlin.reflect.jvm.internal.impl.types.error;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.p;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ErrorSimpleFunctionDescriptorImpl */
public class a extends f0 {
    private final u.d O4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 6:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "ownerScope";
                break;
            case 2:
                objArr[0] = "newOwner";
                break;
            case 3:
                objArr[0] = "kind";
                break;
            case 4:
                objArr[0] = "annotations";
                break;
            case 5:
                objArr[0] = "source";
                break;
            case 6:
            case 7:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl";
                break;
            case 8:
                objArr[0] = "overriddenDescriptors";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "createSubstitutedCopy";
                break;
            case 7:
                objArr[1] = "copy";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 6:
            case 7:
                break;
            case 8:
                objArr[2] = "setOverriddenDescriptors";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(@NotNull e containingDeclaration, @NotNull u.d ownerScope) {
        super(containingDeclaration, (n0) null, g.b.b(), f.k("<ERROR FUNCTION>"), b.a.DECLARATION, o0.a);
        if (containingDeclaration == null) {
            u(0);
        }
        if (ownerScope == null) {
            u(1);
        }
        this.O4 = ownerScope;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public p A0(@NotNull m newOwner, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.u original, @NotNull b.a kind, @Nullable f newName, @NotNull g annotations, @NotNull o0 source) {
        if (newOwner == null) {
            u(2);
        }
        if (kind == null) {
            u(3);
        }
        if (annotations == null) {
            u(4);
        }
        if (source == null) {
            u(5);
        }
        return this;
    }

    @NotNull
    /* renamed from: c1 */
    public n0 l0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        return this;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.types.error.a$a  reason: collision with other inner class name */
    /* compiled from: ErrorSimpleFunctionDescriptorImpl */
    public class C0425a implements u.a<n0> {
        private static /* synthetic */ void t(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl$1";
                    break;
                case 2:
                    objArr[0] = "modality";
                    break;
                case 4:
                    objArr[0] = "visibility";
                    break;
                case 6:
                    objArr[0] = "kind";
                    break;
                case 9:
                    objArr[0] = "name";
                    break;
                case 11:
                case 17:
                    objArr[0] = "parameters";
                    break;
                case 13:
                    objArr[0] = "substitution";
                    break;
                case 15:
                    objArr[0] = "userDataKey";
                    break;
                case 19:
                    objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                    break;
                case 29:
                    objArr[0] = "additionalAnnotations";
                    break;
                default:
                    objArr[0] = "owner";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "setOwner";
                    break;
                case 3:
                    objArr[1] = "setModality";
                    break;
                case 5:
                    objArr[1] = "setVisibility";
                    break;
                case 7:
                    objArr[1] = "setKind";
                    break;
                case 8:
                    objArr[1] = "setCopyOverrides";
                    break;
                case 10:
                    objArr[1] = "setName";
                    break;
                case 12:
                    objArr[1] = "setValueParameters";
                    break;
                case 14:
                    objArr[1] = "setSubstitution";
                    break;
                case 16:
                    objArr[1] = "putUserData";
                    break;
                case 18:
                    objArr[1] = "setTypeParameters";
                    break;
                case 20:
                    objArr[1] = "setReturnType";
                    break;
                case 21:
                    objArr[1] = "setExtensionReceiverParameter";
                    break;
                case 22:
                    objArr[1] = "setDispatchReceiverParameter";
                    break;
                case 23:
                    objArr[1] = "setOriginal";
                    break;
                case 24:
                    objArr[1] = "setSignatureChange";
                    break;
                case 25:
                    objArr[1] = "setPreserveSourceElement";
                    break;
                case 26:
                    objArr[1] = "setDropOriginalInContainingParts";
                    break;
                case 27:
                    objArr[1] = "setHiddenToOvercomeSignatureClash";
                    break;
                case 28:
                    objArr[1] = "setHiddenForResolutionEverywhereBesideSupercalls";
                    break;
                case 30:
                    objArr[1] = "setAdditionalAnnotations";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl$1";
                    break;
            }
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                    break;
                case 2:
                    objArr[2] = "setModality";
                    break;
                case 4:
                    objArr[2] = "setVisibility";
                    break;
                case 6:
                    objArr[2] = "setKind";
                    break;
                case 9:
                    objArr[2] = "setName";
                    break;
                case 11:
                    objArr[2] = "setValueParameters";
                    break;
                case 13:
                    objArr[2] = "setSubstitution";
                    break;
                case 15:
                    objArr[2] = "putUserData";
                    break;
                case 17:
                    objArr[2] = "setTypeParameters";
                    break;
                case 19:
                    objArr[2] = "setReturnType";
                    break;
                case 29:
                    objArr[2] = "setAdditionalAnnotations";
                    break;
                default:
                    objArr[2] = "setOwner";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        C0425a() {
        }

        @NotNull
        public u.a<n0> p(@NotNull m owner) {
            if (owner == null) {
                t(0);
            }
            return this;
        }

        @NotNull
        public u.a<n0> j(@NotNull w modality) {
            if (modality == null) {
                t(2);
            }
            return this;
        }

        @NotNull
        public u.a<n0> c(@NotNull a1 visibility) {
            if (visibility == null) {
                t(4);
            }
            return this;
        }

        @NotNull
        public u.a<n0> q(@NotNull b.a kind) {
            if (kind == null) {
                t(6);
            }
            return this;
        }

        @NotNull
        public u.a<n0> n(boolean copyOverrides) {
            return this;
        }

        @NotNull
        public u.a<n0> i(@NotNull f name) {
            if (name == null) {
                t(9);
            }
            return this;
        }

        @NotNull
        public u.a<n0> b(@NotNull List<w0> parameters) {
            if (parameters == null) {
                t(11);
            }
            return this;
        }

        @NotNull
        public u.a<n0> g(@NotNull z0 substitution) {
            if (substitution == null) {
                t(13);
            }
            return this;
        }

        @NotNull
        public u.a<n0> o(@NotNull List<t0> parameters) {
            if (parameters == null) {
                t(17);
            }
            return this;
        }

        @NotNull
        public u.a<n0> l(@NotNull b0 type) {
            if (type == null) {
                t(19);
            }
            return this;
        }

        @NotNull
        public u.a<n0> f(@Nullable l0 extensionReceiverParameter) {
            return this;
        }

        @NotNull
        public u.a<n0> d(@Nullable l0 dispatchReceiverParameter) {
            return this;
        }

        @NotNull
        public u.a<n0> m(@Nullable b original) {
            return this;
        }

        @NotNull
        public u.a<n0> s() {
            return this;
        }

        @NotNull
        public u.a<n0> k() {
            return this;
        }

        @NotNull
        public u.a<n0> a() {
            return this;
        }

        @NotNull
        public u.a<n0> h() {
            return this;
        }

        @NotNull
        public u.a<n0> e() {
            return this;
        }

        @NotNull
        public u.a<n0> r(@NotNull g additionalAnnotations) {
            if (additionalAnnotations == null) {
                t(29);
            }
            return this;
        }

        @Nullable
        /* renamed from: u */
        public n0 build() {
            return a.this;
        }
    }

    @NotNull
    public u.a<? extends n0> r() {
        return new C0425a();
    }

    public boolean isSuspend() {
        return false;
    }

    public <V> V q0(a.C0348a<V> aVar) {
        return null;
    }

    public void y0(@NotNull Collection<? extends b> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            u(8);
        }
    }
}
