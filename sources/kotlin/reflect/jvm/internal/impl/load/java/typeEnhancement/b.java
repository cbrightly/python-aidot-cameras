package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Iterator;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeEnhancement.kt */
public final class b implements g {
    private final kotlin.reflect.jvm.internal.impl.name.b c;

    public b(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqNameToMatch) {
        k.f(fqNameToMatch, "fqNameToMatch");
        this.c = fqNameToMatch;
    }

    public boolean I(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        return g.b.b(this, fqName);
    }

    public boolean isEmpty() {
        return false;
    }

    @Nullable
    /* renamed from: a */
    public a c(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        if (k.a(fqName, this.c)) {
            return a.a;
        }
        return null;
    }

    @NotNull
    public Iterator<c> iterator() {
        return q.g().iterator();
    }
}
