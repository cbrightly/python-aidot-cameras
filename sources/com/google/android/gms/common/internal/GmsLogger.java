package com.google.android.gms.common.internal;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class GmsLogger {
    private final String zza;
    @Nullable
    private final String zzb;

    @KeepForSdk
    public GmsLogger(@NonNull String logTag) {
        this(logTag, (String) null);
    }

    private final String zza(String str) {
        String str2 = this.zzb;
        return str2 == null ? str : str2.concat(str);
    }

    @FormatMethod
    private final String zzb(String str, Object... objArr) {
        String format = String.format(str, objArr);
        String str2 = this.zzb;
        if (str2 == null) {
            return format;
        }
        return str2.concat(format);
    }

    @KeepForSdk
    public boolean canLog(int level) {
        return Log.isLoggable(this.zza, level);
    }

    @KeepForSdk
    public boolean canLogPii() {
        return false;
    }

    @KeepForSdk
    public void d(@NonNull String tag, @NonNull String message) {
        if (canLog(3)) {
            Log.d(tag, zza(message));
        }
    }

    @KeepForSdk
    public void e(@NonNull String tag, @NonNull String message) {
        if (canLog(6)) {
            Log.e(tag, zza(message));
        }
    }

    @FormatMethod
    @KeepForSdk
    public void efmt(@NonNull String tag, @FormatString @NonNull String messageFormatString, @NonNull Object... messageParams) {
        if (canLog(6)) {
            Log.e(tag, zzb(messageFormatString, messageParams));
        }
    }

    @KeepForSdk
    public void i(@NonNull String tag, @NonNull String message) {
        if (canLog(4)) {
            Log.i(tag, zza(message));
        }
    }

    @KeepForSdk
    public void pii(@NonNull String str, @NonNull String str2) {
    }

    @KeepForSdk
    public void pii(@NonNull String str, @NonNull String str2, @NonNull Throwable th) {
    }

    @KeepForSdk
    public void v(@NonNull String tag, @NonNull String message) {
        if (canLog(2)) {
            Log.v(tag, zza(message));
        }
    }

    @KeepForSdk
    public void w(@NonNull String tag, @NonNull String message) {
        if (canLog(5)) {
            Log.w(tag, zza(message));
        }
    }

    @FormatMethod
    @KeepForSdk
    public void wfmt(@NonNull String str, @FormatString @NonNull String messageFormatString, @NonNull Object... messageParams) {
        if (canLog(5)) {
            Log.w(this.zza, zzb(messageFormatString, messageParams));
        }
    }

    @KeepForSdk
    public void wtf(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(7)) {
            Log.e(tag, zza(message), thr);
            Log.wtf(tag, zza(message), thr);
        }
    }

    @KeepForSdk
    public GmsLogger(@NonNull String logTag, @Nullable String messagePrefix) {
        Preconditions.checkNotNull(logTag, "log tag cannot be null");
        Preconditions.checkArgument(logTag.length() <= 23, "tag \"%s\" is longer than the %d character maximum", logTag, 23);
        this.zza = logTag;
        if (messagePrefix == null || messagePrefix.length() <= 0) {
            this.zzb = null;
        } else {
            this.zzb = messagePrefix;
        }
    }

    @KeepForSdk
    public void d(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(3)) {
            Log.d(tag, zza(message), thr);
        }
    }

    @KeepForSdk
    public void e(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(6)) {
            Log.e(tag, zza(message), thr);
        }
    }

    @KeepForSdk
    public void i(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(4)) {
            Log.i(tag, zza(message), thr);
        }
    }

    @KeepForSdk
    public void v(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(2)) {
            Log.v(tag, zza(message), thr);
        }
    }

    @KeepForSdk
    public void w(@NonNull String tag, @NonNull String message, @NonNull Throwable thr) {
        if (canLog(5)) {
            Log.w(tag, zza(message), thr);
        }
    }
}
