package coil.util;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Logs.kt */
public final class g {
    public static final void a(@NotNull m $this$log, @NotNull String tag, @NotNull Throwable throwable) {
        k.e($this$log, "<this>");
        k.e(tag, Progress.TAG);
        k.e(throwable, "throwable");
        if ($this$log.b() <= 6) {
            $this$log.a(tag, 6, (String) null, throwable);
        }
    }
}
