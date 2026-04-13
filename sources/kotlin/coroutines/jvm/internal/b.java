package kotlin.coroutines.jvm.internal;

import org.jetbrains.annotations.NotNull;

/* compiled from: boxing.kt */
public final class b {
    @NotNull
    public static final Boolean a(boolean primitive) {
        return Boolean.valueOf(primitive);
    }

    @NotNull
    public static final Integer c(int primitive) {
        return new Integer(primitive);
    }

    @NotNull
    public static final Long d(long primitive) {
        return new Long(primitive);
    }

    @NotNull
    public static final Character b(char primitive) {
        return new Character(primitive);
    }
}
