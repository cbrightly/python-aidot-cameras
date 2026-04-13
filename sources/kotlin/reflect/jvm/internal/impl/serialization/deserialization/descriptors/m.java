package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeserializedAnnotations.kt */
public final class m extends a {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull j storageManager, @NotNull a<? extends List<? extends c>> compute) {
        super(storageManager, compute);
        k.f(storageManager, "storageManager");
        k.f(compute, "compute");
    }

    public boolean isEmpty() {
        return false;
    }
}
