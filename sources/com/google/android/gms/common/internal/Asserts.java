package com.google.android.gms.common.internal;

import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import javax.annotation.Nullable;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class Asserts {
    private Asserts() {
        throw new AssertionError("Uninstantiable");
    }

    @KeepForSdk
    public static void checkMainThread(@NonNull String errorMessage) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            String valueOf = String.valueOf(Thread.currentThread());
            String valueOf2 = String.valueOf(Looper.getMainLooper().getThread());
            Log.e("Asserts", "checkMainThread: current thread " + valueOf + " IS NOT the main thread " + valueOf2 + "!");
            throw new IllegalStateException(errorMessage);
        }
    }

    @KeepForSdk
    public static void checkNotMainThread(@NonNull String errorMessage) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            String valueOf = String.valueOf(Thread.currentThread());
            String valueOf2 = String.valueOf(Looper.getMainLooper().getThread());
            Log.e("Asserts", "checkNotMainThread: current thread " + valueOf + " IS the main thread " + valueOf2 + "!");
            throw new IllegalStateException(errorMessage);
        }
    }

    @KeepForSdk
    @EnsuresNonNull({"#1"})
    public static void checkNotNull(@Nullable Object reference) {
        if (reference == null) {
            throw new IllegalArgumentException("null reference");
        }
    }

    @KeepForSdk
    public static void checkNull(@Nullable Object reference) {
        if (reference != null) {
            throw new IllegalArgumentException("non-null reference");
        }
    }

    @KeepForSdk
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    @KeepForSdk
    @EnsuresNonNull({"#1"})
    public static void checkNotNull(@Nullable Object reference, @NonNull Object errorMessage) {
        if (reference == null) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    @KeepForSdk
    public static void checkNull(@Nullable Object reference, @NonNull Object errorMessage) {
        if (reference != null) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    @KeepForSdk
    public static void checkState(boolean expression, @NonNull Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }
}
