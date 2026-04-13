package androidx.work;

import androidx.annotation.RestrictTo;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: DirectExecutor.kt */
public enum DirectExecutor implements Executor {
    INSTANCE;

    public void execute(@NotNull Runnable command) {
        k.e(command, "command");
        command.run();
    }

    @NotNull
    public String toString() {
        return "DirectExecutor";
    }
}
