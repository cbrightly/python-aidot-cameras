package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedAnnotations.kt */
public class a implements g {
    static final /* synthetic */ k[] c = {a0.h(new u(a0.b(a.class), "annotations", "getAnnotations()Ljava/util/List;"))};
    private final f d;

    private final List<c> a() {
        return (List) i.a(this.d, this, c[0]);
    }

    public a(@NotNull j storageManager, @NotNull kotlin.jvm.functions.a<? extends List<? extends c>> compute) {
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(compute, "compute");
        this.d = storageManager.c(compute);
    }

    public boolean I(@NotNull b fqName) {
        kotlin.jvm.internal.k.f(fqName, "fqName");
        return g.b.b(this, fqName);
    }

    @Nullable
    public c c(@NotNull b fqName) {
        kotlin.jvm.internal.k.f(fqName, "fqName");
        return g.b.a(this, fqName);
    }

    public boolean isEmpty() {
        return a().isEmpty();
    }

    @NotNull
    public Iterator<c> iterator() {
        return a().iterator();
    }
}
