package com.google.android.gms.vision;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class L {
    @RecentlyNonNull
    @KeepForSdk
    public static final String TAG = "Vision";

    @KeepForSdk
    public static int v(@RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 2)) {
            return Log.v(TAG, String.format(str, objArr));
        }
        return 0;
    }

    @KeepForSdk
    public static int d(@RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 3)) {
            return Log.d(TAG, String.format(str, objArr));
        }
        return 0;
    }

    @KeepForSdk
    public static int d(@RecentlyNonNull Throwable th, @RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 3)) {
            return Log.d(TAG, String.format(str, objArr), th);
        }
        return 0;
    }

    @KeepForSdk
    public static int i(@RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 4)) {
            return Log.i(TAG, String.format(str, objArr));
        }
        return 0;
    }

    @KeepForSdk
    public static int e(@RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 6)) {
            return Log.e(TAG, String.format(str, objArr));
        }
        return 0;
    }

    @SuppressLint({"LogTagMismatch"})
    @KeepForSdk
    public static int e(@RecentlyNonNull Throwable th, @RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (!Log.isLoggable(TAG, 6)) {
            return 0;
        }
        if (Log.isLoggable(TAG, 3)) {
            return Log.e(TAG, String.format(str, objArr), th);
        }
        String format = String.format(str, objArr);
        String valueOf = String.valueOf(th);
        StringBuilder sb = new StringBuilder(String.valueOf(format).length() + 2 + String.valueOf(valueOf).length());
        sb.append(format);
        sb.append(": ");
        sb.append(valueOf);
        return Log.e(TAG, sb.toString());
    }

    @KeepForSdk
    public static int w(@RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (Log.isLoggable(TAG, 5)) {
            return Log.w(TAG, String.format(str, objArr));
        }
        return 0;
    }

    @SuppressLint({"LogTagMismatch"})
    @KeepForSdk
    public static int w(@RecentlyNonNull Throwable th, @RecentlyNonNull String str, @RecentlyNonNull Object... objArr) {
        if (!Log.isLoggable(TAG, 5)) {
            return 0;
        }
        if (Log.isLoggable(TAG, 3)) {
            return Log.w(TAG, String.format(str, objArr), th);
        }
        String format = String.format(str, objArr);
        String valueOf = String.valueOf(th);
        StringBuilder sb = new StringBuilder(String.valueOf(format).length() + 2 + String.valueOf(valueOf).length());
        sb.append(format);
        sb.append(": ");
        sb.append(valueOf);
        return Log.w(TAG, sb.toString());
    }
}
