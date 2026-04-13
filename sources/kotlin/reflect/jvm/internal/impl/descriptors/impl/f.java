package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassConstructorDescriptorImpl */
public class f extends p implements d {
    private static final kotlin.reflect.jvm.internal.impl.name.f O4 = kotlin.reflect.jvm.internal.impl.name.f.k("<init>");
    protected final boolean P4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 15:
            case 16:
            case 17:
            case 19:
            case 25:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 15:
            case 16:
            case 17:
            case 19:
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
            case 5:
            case 8:
            case 23:
                objArr[0] = "annotations";
                break;
            case 2:
            case 22:
                objArr[0] = "kind";
                break;
            case 3:
            case 6:
            case 9:
            case 24:
                objArr[0] = "source";
                break;
            case 10:
            case 13:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 11:
            case 14:
                objArr[0] = "visibility";
                break;
            case 12:
                objArr[0] = "typeParameterDescriptors";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
            case 25:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassConstructorDescriptorImpl";
                break;
            case 18:
                objArr[0] = "originalSubstitutor";
                break;
            case 20:
                objArr[0] = "overriddenDescriptors";
                break;
            case 21:
                objArr[0] = "newOwner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 15:
                objArr[1] = "getContainingDeclaration";
                break;
            case 16:
                objArr[1] = "getConstructedClass";
                break;
            case 17:
                objArr[1] = "getOriginal";
                break;
            case 19:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 25:
                objArr[1] = "copy";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassConstructorDescriptorImpl";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[2] = "create";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createSynthesized";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[2] = "initialize";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
            case 25:
                break;
            case 18:
                objArr[2] = "substitute";
                break;
            case 20:
                objArr[2] = "setOverriddenDescriptors";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "createSubstitutedCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 15:
            case 16:
            case 17:
            case 19:
            case 25:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected f(@NotNull e containingDeclaration, @Nullable l original, @NotNull g annotations, boolean isPrimary, @NotNull b.a kind, @NotNull o0 source) {
        super(containingDeclaration, original, annotations, O4, kind, source);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (kind == null) {
            u(2);
        }
        if (source == null) {
            u(3);
        }
        this.P4 = isPrimary;
    }

    @NotNull
    public static f e1(@NotNull e containingDeclaration, @NotNull g annotations, boolean isPrimary, @NotNull o0 source) {
        if (containingDeclaration == null) {
            u(4);
        }
        if (annotations == null) {
            u(5);
        }
        if (source == null) {
            u(6);
        }
        return new f(containingDeclaration, (l) null, annotations, isPrimary, b.a.DECLARATION, source);
    }

    public f i1(@NotNull List<w0> unsubstitutedValueParameters, @NotNull a1 visibility, @NotNull List<t0> typeParameterDescriptors) {
        if (unsubstitutedValueParameters == null) {
            u(10);
        }
        if (visibility == null) {
            u(11);
        }
        if (typeParameterDescriptors == null) {
            u(12);
        }
        super.J0((l0) null, c1(), typeParameterDescriptors, unsubstitutedValueParameters, (b0) null, w.FINAL, visibility);
        return this;
    }

    public f h1(@NotNull List<w0> unsubstitutedValueParameters, @NotNull a1 visibility) {
        if (unsubstitutedValueParameters == null) {
            u(13);
        }
        if (visibility == null) {
            u(14);
        }
        i1(unsubstitutedValueParameters, visibility, b().o());
        return this;
    }

    @Nullable
    public l0 c1() {
        e classDescriptor = b();
        if (!classDescriptor.x()) {
            return null;
        }
        m classContainer = classDescriptor.b();
        if (classContainer instanceof e) {
            return ((e) classContainer).F0();
        }
        return null;
    }

    @NotNull
    /* renamed from: g1 */
    public e b() {
        e eVar = (e) super.b();
        if (eVar == null) {
            u(15);
        }
        return eVar;
    }

    @NotNull
    public e X() {
        e g1 = b();
        if (g1 == null) {
            u(16);
        }
        return g1;
    }

    @NotNull
    public d a() {
        d dVar = (d) super.c0();
        if (dVar == null) {
            u(17);
        }
        return dVar;
    }

    @Nullable
    public d c(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor == null) {
            u(18);
        }
        return (d) super.c(originalSubstitutor);
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.j(this, data);
    }

    public boolean W() {
        return this.P4;
    }

    @NotNull
    public Collection<? extends u> d() {
        Set emptySet = Collections.emptySet();
        if (emptySet == null) {
            u(19);
        }
        return emptySet;
    }

    public void y0(@NotNull Collection<? extends b> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            u(20);
        }
        if (!overriddenDescriptors.isEmpty()) {
            throw new AssertionError("Constructors cannot override anything");
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: f1 */
    public f A0(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        if (newOwner == null) {
            u(21);
        }
        if (kind == null) {
            u(22);
        }
        if (annotations == null) {
            u(23);
        }
        if (source == null) {
            u(24);
        }
        b.a aVar = b.a.DECLARATION;
        if (kind != aVar && kind != b.a.SYNTHESIZED) {
            throw new IllegalStateException("Attempt at creating a constructor that is not a declaration: \ncopy from: " + this + "\n" + "newOwner: " + newOwner + "\n" + "kind: " + kind);
        } else if (newName == null) {
            return new f((e) newOwner, this, annotations, this.P4, aVar, source);
        } else {
            throw new AssertionError("Attempt to rename constructor: " + this);
        }
    }

    @NotNull
    /* renamed from: d1 */
    public d l0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        d dVar = (d) super.B0(newOwner, modality, visibility, kind, copyOverrides);
        if (dVar == null) {
            u(25);
        }
        return dVar;
    }
}
