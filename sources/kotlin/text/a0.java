package kotlin.text;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: StringBuilderJVM.kt */
public final class a0 {
    @NotNull
    public static final String a;
    @NotNull
    public static final a0 b = new a0();

    static {
        String property = System.getProperty("line.separator");
        k.c(property);
        a = property;
    }

    private a0() {
    }
}
