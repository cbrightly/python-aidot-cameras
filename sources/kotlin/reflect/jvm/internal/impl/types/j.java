package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassTypeConstructorImpl */
public class j extends b implements u0 {
    private final e c;
    private final List<t0> d;
    private final Collection<b0> e;

    private static /* synthetic */ void o(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
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
                objArr[0] = "parameters";
                break;
            case 2:
                objArr[0] = "supertypes";
                break;
            case 3:
                objArr[0] = "storageManager";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
                break;
            default:
                objArr[0] = "classDescriptor";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getParameters";
                break;
            case 5:
                objArr[1] = "getDeclarationDescriptor";
                break;
            case 6:
                objArr[1] = "computeSupertypes";
                break;
            case 7:
                objArr[1] = "getSupertypeLoopChecker";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
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
    public j(@NotNull e classDescriptor, @NotNull List<? extends t0> parameters, @NotNull Collection<b0> supertypes, @NotNull kotlin.reflect.jvm.internal.impl.storage.j storageManager) {
        super(storageManager);
        if (classDescriptor == null) {
            o(0);
        }
        if (parameters == null) {
            o(1);
        }
        if (supertypes == null) {
            o(2);
        }
        if (storageManager == null) {
            o(3);
        }
        this.c = classDescriptor;
        this.d = Collections.unmodifiableList(new ArrayList(parameters));
        this.e = Collections.unmodifiableCollection(supertypes);
    }

    @NotNull
    public List<t0> getParameters() {
        List<t0> list = this.d;
        if (list == null) {
            o(4);
        }
        return list;
    }

    public String toString() {
        return c.m(this.c).b();
    }

    public boolean d() {
        return true;
    }

    @NotNull
    /* renamed from: q */
    public e c() {
        e eVar = this.c;
        if (eVar == null) {
            o(5);
        }
        return eVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Collection<b0> g() {
        Collection<b0> collection = this.e;
        if (collection == null) {
            o(6);
        }
        return collection;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public r0 k() {
        r0.a aVar = r0.a.a;
        if (aVar == null) {
            o(7);
        }
        return aVar;
    }
}
