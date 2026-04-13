package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: utils.kt */
public final class h extends o {
    @NotNull
    private final e a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(@NotNull e descriptor) {
        super((DefaultConstructorMarker) null);
        k.f(descriptor, "descriptor");
        this.a = descriptor;
    }
}
