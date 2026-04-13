package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: utils.kt */
public final class f extends o {
    @NotNull
    private final Object a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f(@NotNull Object value) {
        super((DefaultConstructorMarker) null);
        k.f(value, "value");
        this.a = value;
    }
}
