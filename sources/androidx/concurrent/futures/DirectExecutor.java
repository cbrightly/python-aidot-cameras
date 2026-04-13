package androidx.concurrent.futures;

import androidx.annotation.RestrictTo;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public enum DirectExecutor implements Executor {
    INSTANCE;

    public void execute(Runnable command) {
        command.run();
    }

    public String toString() {
        return "DirectExecutor";
    }
}
