package androidx.work.impl;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.os.HandlerCompat;
import androidx.work.RunnableScheduler;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DefaultRunnableScheduler implements RunnableScheduler {
    private final Handler mHandler;

    public DefaultRunnableScheduler() {
        this.mHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    @VisibleForTesting
    public DefaultRunnableScheduler(@NonNull Handler handler) {
        this.mHandler = handler;
    }

    @NonNull
    public Handler getHandler() {
        return this.mHandler;
    }

    public void scheduleWithDelay(long delayInMillis, @NonNull Runnable runnable) {
        this.mHandler.postDelayed(runnable, delayInMillis);
    }

    public void cancel(@NonNull Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }
}
