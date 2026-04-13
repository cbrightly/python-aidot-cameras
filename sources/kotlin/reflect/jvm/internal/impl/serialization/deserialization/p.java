package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.z;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.g;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeserializedPackageFragment.kt */
public abstract class p extends z {
    @NotNull
    private final j y;

    public abstract void A0(@NotNull l lVar);

    @NotNull
    public abstract i f0();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public p(@NotNull b fqName, @NotNull j storageManager, @NotNull y module) {
        super(module, fqName);
        k.f(fqName, "fqName");
        k.f(storageManager, "storageManager");
        k.f(module, "module");
        this.y = storageManager;
    }

    public boolean l0(@NotNull f name) {
        k.f(name, "name");
        h scope = l();
        return (scope instanceof g) && ((g) scope).x().contains(name);
    }
}
