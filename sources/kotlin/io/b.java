package kotlin.io;

import java.io.Closeable;
import org.jetbrains.annotations.Nullable;

/* compiled from: Closeable.kt */
public final class b {
    public static final void a(@Nullable Closeable $this$closeFinally, @Nullable Throwable cause) {
        if ($this$closeFinally != null) {
            if (cause == null) {
                $this$closeFinally.close();
                return;
            }
            try {
                $this$closeFinally.close();
            } catch (Throwable closeException) {
                kotlin.b.a(cause, closeException);
            }
        }
    }
}
