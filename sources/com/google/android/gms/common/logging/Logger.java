package com.google.android.gms.common.logging;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class Logger {
    private final String zza;
    private final String zzb;
    private final GmsLogger zzc;
    private final int zzd;

    @KeepForSdk
    public Logger(@NonNull String tag, @NonNull String... categories) {
        String str;
        if (r0 == 0) {
            str = "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (String str2 : categories) {
                if (sb.length() > 1) {
                    sb.append(",");
                }
                sb.append(str2);
            }
            sb.append("] ");
            str = sb.toString();
        }
        this.zzb = str;
        this.zza = tag;
        this.zzc = new GmsLogger(tag);
        int i = 2;
        while (i <= 7 && !Log.isLoggable(this.zza, i)) {
            i++;
        }
        this.zzd = i;
    }

    @KeepForSdk
    public void d(@NonNull String msg, @NonNull Object... optionalFormatArgs) {
        if (isLoggable(3)) {
            Log.d(this.zza, format(msg, optionalFormatArgs));
        }
    }

    @KeepForSdk
    public void e(@NonNull String msg, @NonNull Throwable tr, @NonNull Object... optionalFormatArgs) {
        Log.e(this.zza, format(msg, optionalFormatArgs), tr);
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public String format(@NonNull String message, @NonNull Object... optionalFormatArgs) {
        if (optionalFormatArgs != null && optionalFormatArgs.length > 0) {
            message = String.format(Locale.US, message, optionalFormatArgs);
        }
        return this.zzb.concat(message);
    }

    @NonNull
    @KeepForSdk
    public String getTag() {
        return this.zza;
    }

    @KeepForSdk
    public void i(@NonNull String msg, @NonNull Object... optionalFormatArgs) {
        Log.i(this.zza, format(msg, optionalFormatArgs));
    }

    @KeepForSdk
    public boolean isLoggable(int i) {
        return this.zzd <= i;
    }

    @KeepForSdk
    public void v(@NonNull String msg, @NonNull Throwable tr, @NonNull Object... optionalFormatArgs) {
        if (isLoggable(2)) {
            Log.v(this.zza, format(msg, optionalFormatArgs), tr);
        }
    }

    @KeepForSdk
    public void w(@NonNull String msg, @NonNull Object... optionalFormatArgs) {
        Log.w(this.zza, format(msg, optionalFormatArgs));
    }

    @KeepForSdk
    public void wtf(@NonNull String msg, @NonNull Throwable tr, @NonNull Object... optionalFormatArgs) {
        Log.wtf(this.zza, format(msg, optionalFormatArgs), tr);
    }

    @KeepForSdk
    public void e(@NonNull String msg, @NonNull Object... optionalFormatArgs) {
        Log.e(this.zza, format(msg, optionalFormatArgs));
    }

    @KeepForSdk
    public void wtf(@NonNull Throwable tr) {
        Log.wtf(this.zza, tr);
    }

    @KeepForSdk
    public void v(@NonNull String msg, @NonNull Object... optionalFormatArgs) {
        if (isLoggable(2)) {
            Log.v(this.zza, format(msg, optionalFormatArgs));
        }
    }
}
