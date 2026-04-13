package okhttp3;

import org.jetbrains.annotations.NotNull;

/* compiled from: Call.kt */
public interface e extends Cloneable {

    /* compiled from: Call.kt */
    public interface a {
        @NotNull
        e a(@NotNull b0 b0Var);
    }

    void cancel();

    @NotNull
    d0 execute();

    @NotNull
    b0 g();

    boolean isCanceled();

    void o0(@NotNull f fVar);
}
