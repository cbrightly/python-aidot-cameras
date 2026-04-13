package kotlin.reflect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KParameter.kt */
public interface j extends a {

    /* compiled from: KParameter.kt */
    public enum a {
        INSTANCE,
        EXTENSION_RECEIVER,
        VALUE
    }

    @Nullable
    String getName();

    @NotNull
    n getType();

    @NotNull
    a h();

    boolean m();
}
