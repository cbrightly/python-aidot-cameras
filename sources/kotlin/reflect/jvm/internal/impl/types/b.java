package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.utils.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractClassTypeConstructor */
public abstract class b extends h implements u0 {
    private int b;

    private static /* synthetic */ void o(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 3:
            case 4:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
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
            case 4:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/AbstractClassTypeConstructor";
                break;
            case 2:
                objArr[0] = "descriptor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getBuiltIns";
                break;
            case 3:
            case 4:
                objArr[1] = "getAdditionalNeighboursInSupertypeGraph";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/AbstractClassTypeConstructor";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
                break;
            case 2:
                objArr[2] = "hasMeaningfulFqName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 4:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public abstract e q();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull j storageManager) {
        super(storageManager);
        if (storageManager == null) {
            o(0);
        }
        this.b = 0;
    }

    public final int hashCode() {
        int currentHashCode;
        int currentHashCode2 = this.b;
        if (currentHashCode2 != 0) {
            return currentHashCode2;
        }
        h descriptor = q();
        if (r(descriptor)) {
            currentHashCode = c.m(descriptor).hashCode();
        } else {
            currentHashCode = System.identityHashCode(this);
        }
        this.b = currentHashCode;
        return currentHashCode;
    }

    @NotNull
    public g j() {
        g h = a.h(q());
        if (h == null) {
            o(1);
        }
        return h;
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof u0) || other.hashCode() != hashCode() || ((u0) other).getParameters().size() != getParameters().size()) {
            return false;
        }
        e q = q();
        h otherDescriptor = ((u0) other).c();
        if (!r(q) || ((otherDescriptor != null && !r(otherDescriptor)) || !(otherDescriptor instanceof e))) {
            return false;
        }
        return p(q, (e) otherDescriptor);
    }

    private static boolean p(e first, e second) {
        if (!first.getName().equals(second.getName())) {
            return false;
        }
        m a = first.b();
        m b2 = second.b();
        while (a != null && b2 != null) {
            if (a instanceof y) {
                return b2 instanceof y;
            }
            if (b2 instanceof y) {
                return false;
            }
            if (a instanceof b0) {
                if (!(b2 instanceof b0) || !((b0) a).e().equals(((b0) b2).e())) {
                    return false;
                }
                return true;
            } else if ((b2 instanceof b0) || !a.getName().equals(b2.getName())) {
                return false;
            } else {
                a = a.b();
                b2 = b2.b();
            }
        }
        return true;
    }

    private static boolean r(@NotNull h descriptor) {
        if (descriptor == null) {
            o(2);
        }
        return !u.r(descriptor) && !c.E(descriptor);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Collection<b0> i(boolean useCompanions) {
        m containingDeclaration = q().b();
        if (!(containingDeclaration instanceof e)) {
            List emptyList = Collections.emptyList();
            if (emptyList == null) {
                o(3);
            }
            return emptyList;
        }
        Collection<KotlinType> additionalNeighbours = new i<>();
        e containingClassDescriptor = (e) containingDeclaration;
        additionalNeighbours.add(containingClassDescriptor.m());
        e companion = containingClassDescriptor.h0();
        if (useCompanions && companion != null) {
            additionalNeighbours.add(companion.m());
        }
        return additionalNeighbours;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public b0 h() {
        if (g.F0(q())) {
            return null;
        }
        return j().j();
    }
}
