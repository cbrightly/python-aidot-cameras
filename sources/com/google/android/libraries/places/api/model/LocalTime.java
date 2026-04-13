package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzkc;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class LocalTime implements Parcelable, Comparable<LocalTime> {
    @RecentlyNonNull
    public static LocalTime newInstance(@IntRange(from = 0, to = 23) int hours, @IntRange(from = 0, to = 59) int minutes) {
        try {
            zzk zzk = new zzk();
            zzk.zza(hours);
            zzk.zzb(minutes);
            LocalTime zzc = zzk.zzc();
            int hours2 = zzc.getHours();
            zziy.zzl(zzkc.zzb(0, 23).zzd(Integer.valueOf(hours2)), "Hours must not be out-of-range: 0 to 23, but was: %s.", hours2);
            int minutes2 = zzc.getMinutes();
            zziy.zzl(zzkc.zzb(0, 59).zzd(Integer.valueOf(minutes2)), "Minutes must not be out-of-range: 0 to 59, but was: %s.", minutes2);
            return zzc;
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public int compareTo(@RecentlyNonNull LocalTime compare) {
        zziy.zzc(compare, "compare must not be null.");
        if (this == compare) {
            return 0;
        }
        if (getHours() == compare.getHours()) {
            return getMinutes() - compare.getMinutes();
        }
        return getHours() - compare.getHours();
    }

    @IntRange(from = 0, to = 23)
    public abstract int getHours();

    @IntRange(from = 0, to = 59)
    public abstract int getMinutes();
}
