package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzjd;
import com.google.android.libraries.places.internal.zzkc;
import com.google.android.material.timepicker.TimeModel;
import com.google.auto.value.AutoValue;
import java.util.Arrays;
import java.util.Locale;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class LocalDate implements Parcelable, Comparable<LocalDate> {
    @RecentlyNonNull
    public static LocalDate newInstance(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day) {
        int i;
        zzi zzi = new zzi();
        zzi.zzc(year);
        zzi.zzb(month);
        zzi.zza(day);
        LocalDate zzd = zzi.zzd();
        int month2 = zzd.getMonth();
        zzkc zzb = zzkc.zzb(1, 12);
        Integer valueOf = Integer.valueOf(month2);
        zziy.zzg(zzb.zzd(valueOf), "Month must not be out of range of 1 to 12, but was: %s.", month2);
        int day2 = zzd.getDay();
        zzkc zzb2 = zzkc.zzb(1, 31);
        Integer valueOf2 = Integer.valueOf(day2);
        zziy.zzg(zzb2.zzd(valueOf2), "Day must not be out of range of 1 to 31, but was: %s.", day2);
        if (!Arrays.asList(new Integer[]{4, 6, 9, 11}).contains(valueOf) || zzkc.zzb(1, 30).zzd(valueOf2)) {
            if (month2 == 2) {
                int year2 = zzd.getYear();
                if (year2 % 4 == 0) {
                    i = 29;
                } else {
                    i = 28;
                }
                zziy.zzh(zzkc.zzb(1, Integer.valueOf(i)).zzd(valueOf2), "%s is not a valid day for month %s in year %s.", valueOf2, 2, Integer.valueOf(year2));
            }
            return zzd;
        }
        throw new IllegalArgumentException(zzjd.zza("%s is not a valid day for month %s.", valueOf2, valueOf));
    }

    public int compareTo(@RecentlyNonNull LocalDate dateToCompare) {
        zziy.zzc(dateToCompare, "dateToCompare must not be null.");
        if (this == dateToCompare) {
            return 0;
        }
        if (getYear() != dateToCompare.getYear()) {
            return getYear() - dateToCompare.getYear();
        }
        if (getMonth() != dateToCompare.getMonth()) {
            return getMonth() - dateToCompare.getMonth();
        }
        return getDay() - dateToCompare.getDay();
    }

    @IntRange(from = 1, to = 31)
    public abstract int getDay();

    @IntRange(from = 1, to = 12)
    public abstract int getMonth();

    public abstract int getYear();

    @RecentlyNonNull
    public final String toString() {
        String format = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(getMonth())});
        String format2 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(getDay())});
        return String.format(Locale.getDefault(), "%s-%s-%s", new Object[]{Integer.valueOf(getYear()), format, format2});
    }
}
