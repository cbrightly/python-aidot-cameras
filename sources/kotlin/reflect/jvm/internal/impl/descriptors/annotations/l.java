package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Annotations.kt */
public final class l implements g {
    private final g c;
    private final boolean d;
    private final kotlin.jvm.functions.l<b, Boolean> f;

    public l(@NotNull g delegate, boolean isDefinitelyNewInference, @NotNull kotlin.jvm.functions.l<? super b, Boolean> fqNameFilter) {
        k.f(delegate, "delegate");
        k.f(fqNameFilter, "fqNameFilter");
        this.c = delegate;
        this.d = isDefinitelyNewInference;
        this.f = fqNameFilter;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public l(@NotNull g delegate, @NotNull kotlin.jvm.functions.l<? super b, Boolean> fqNameFilter) {
        this(delegate, false, fqNameFilter);
        k.f(delegate, "delegate");
        k.f(fqNameFilter, "fqNameFilter");
    }

    public boolean I(@NotNull b fqName) {
        k.f(fqName, "fqName");
        if (this.f.invoke(fqName).booleanValue()) {
            return this.c.I(fqName);
        }
        return false;
    }

    @Nullable
    public c c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        if (this.f.invoke(fqName).booleanValue()) {
            return this.c.c(fqName);
        }
        return null;
    }

    @NotNull
    public Iterator<c> iterator() {
        Iterable $this$filterTo$iv$iv = this.c;
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            if (a((c) element$iv$iv)) {
                arrayList.add(element$iv$iv);
            }
        }
        return arrayList.iterator();
    }

    public boolean isEmpty() {
        boolean condition;
        g gVar = this.c;
        if (!(gVar instanceof Collection) || !((Collection) gVar).isEmpty()) {
            Iterator it = gVar.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (a((c) it.next())) {
                        condition = true;
                        break;
                    }
                } else {
                    condition = false;
                    break;
                }
            }
        } else {
            condition = false;
        }
        if (!this.d) {
            return condition;
        }
        if (!condition) {
            return true;
        }
        return false;
    }

    private final boolean a(c annotation) {
        b fqName = annotation.e();
        return fqName != null && this.f.invoke(fqName).booleanValue();
    }
}
