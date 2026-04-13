package com.google.android.gms.internal.location;

import androidx.annotation.GuardedBy;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzdj {
    private static final SimpleDateFormat zza;
    private static final SimpleDateFormat zzb;
    @GuardedBy("sharedStringBuilder")
    private static final StringBuilder zzc = new StringBuilder(33);

    static {
        Locale locale = Locale.ROOT;
        zza = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", locale);
        zzb = new SimpleDateFormat("MM-dd HH:mm:ss", locale);
    }

    public static String zza(long j) {
        String sb;
        StringBuilder sb2 = zzc;
        synchronized (sb2) {
            sb2.setLength(0);
            zzb(j, sb2);
            sb = sb2.toString();
        }
        return sb;
    }

    public static void zzb(long j, StringBuilder sb) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i == 0) {
            sb.append("0s");
            return;
        }
        sb.ensureCapacity(sb.length() + 27);
        boolean z = false;
        if (i < 0) {
            sb.append("-");
            if (j != Long.MIN_VALUE) {
                j = -j;
            } else {
                j = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                z = true;
            }
        }
        if (j >= CostTimeUtil.DAY) {
            sb.append(j / CostTimeUtil.DAY);
            sb.append("d");
            j %= CostTimeUtil.DAY;
        }
        if (true == z) {
            j = 25975808;
        }
        if (j >= CostTimeUtil.HOUR) {
            sb.append(j / CostTimeUtil.HOUR);
            sb.append("h");
            j %= CostTimeUtil.HOUR;
        }
        if (j >= 60000) {
            sb.append(j / 60000);
            sb.append("m");
            j %= 60000;
        }
        if (j >= 1000) {
            sb.append(j / 1000);
            sb.append("s");
            j %= 1000;
        }
        if (j > 0) {
            sb.append(j);
            sb.append("ms");
        }
    }
}
