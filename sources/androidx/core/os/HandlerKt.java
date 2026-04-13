package androidx.core.os;

import android.os.Handler;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Handler.kt */
public final class HandlerKt {
    public static /* synthetic */ Runnable postDelayed$default(Handler $this$postDelayed_u24default, long delayInMillis, Object token, a action, int i, Object obj) {
        if ((i & 2) != 0) {
            token = null;
        }
        k.e($this$postDelayed_u24default, "<this>");
        k.e(action, "action");
        Runnable runnable = new HandlerKt$postDelayed$runnable$1(action);
        if (token == null) {
            $this$postDelayed_u24default.postDelayed(runnable, delayInMillis);
        } else {
            HandlerCompat.postDelayed($this$postDelayed_u24default, runnable, token, delayInMillis);
        }
        return runnable;
    }

    @NotNull
    public static final Runnable postDelayed(@NotNull Handler $this$postDelayed, long delayInMillis, @Nullable Object token, @NotNull a<x> action) {
        k.e($this$postDelayed, "<this>");
        k.e(action, "action");
        Runnable runnable = new HandlerKt$postDelayed$runnable$1(action);
        if (token == null) {
            $this$postDelayed.postDelayed(runnable, delayInMillis);
        } else {
            HandlerCompat.postDelayed($this$postDelayed, runnable, token, delayInMillis);
        }
        return runnable;
    }

    public static /* synthetic */ Runnable postAtTime$default(Handler $this$postAtTime_u24default, long uptimeMillis, Object token, a action, int i, Object obj) {
        if ((i & 2) != 0) {
            token = null;
        }
        k.e($this$postAtTime_u24default, "<this>");
        k.e(action, "action");
        Runnable runnable = new HandlerKt$postAtTime$runnable$1(action);
        $this$postAtTime_u24default.postAtTime(runnable, token, uptimeMillis);
        return runnable;
    }

    @NotNull
    public static final Runnable postAtTime(@NotNull Handler $this$postAtTime, long uptimeMillis, @Nullable Object token, @NotNull a<x> action) {
        k.e($this$postAtTime, "<this>");
        k.e(action, "action");
        Runnable runnable = new HandlerKt$postAtTime$runnable$1(action);
        $this$postAtTime.postAtTime(runnable, token, uptimeMillis);
        return runnable;
    }
}
