package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: util.kt */
public final class j extends a {
    @NotNull
    private final String a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull String value) {
        super((DefaultConstructorMarker) null);
        k.f(value, "value");
        this.a = value;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
