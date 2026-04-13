package okhttp3;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/* compiled from: Callback.kt */
public interface f {
    void onFailure(@NotNull e eVar, @NotNull IOException iOException);

    void onResponse(@NotNull e eVar, @NotNull d0 d0Var);
}
