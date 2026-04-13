package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zziy {
    public static int zza(int i, int i2, String str) {
        String str2;
        if (i >= 0 && i < i2) {
            return i;
        }
        if (i < 0) {
            str2 = zzjd.zza("%s (%s) must not be negative", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i));
        } else if (i2 < 0) {
            throw new IllegalArgumentException("negative size: " + i2);
        } else {
            str2 = zzjd.zza("%s (%s) must be less than size (%s)", FirebaseAnalytics.Param.INDEX, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IndexOutOfBoundsException(str2);
    }

    public static int zzb(int i, int i2, String str) {
        if (i >= 0 && i <= i2) {
            return i;
        }
        throw new IndexOutOfBoundsException(zzn(i, i2, FirebaseAnalytics.Param.INDEX));
    }

    public static Object zzc(@CheckForNull Object obj, @CheckForNull Object obj2) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException((String) obj2);
    }

    public static void zzd(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void zze(boolean z, @CheckForNull Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static void zzf(boolean z, String str, char c) {
        if (!z) {
            throw new IllegalArgumentException(zzjd.zza(str, Character.valueOf(c)));
        }
    }

    public static void zzg(boolean z, String str, int i) {
        if (!z) {
            throw new IllegalArgumentException(zzjd.zza(str, Integer.valueOf(i)));
        }
    }

    public static void zzh(boolean z, String str, @CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3) {
        if (!z) {
            throw new IllegalArgumentException(zzjd.zza(str, obj, obj2, obj3));
        }
    }

    public static void zzi(int i, int i2, int i3) {
        String str;
        if (i < 0 || i2 < i || i2 > i3) {
            if (i < 0 || i > i3) {
                str = zzn(i, i3, "start index");
            } else if (i2 < 0 || i2 > i3) {
                str = zzn(i2, i3, "end index");
            } else {
                str = zzjd.zza("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i));
            }
            throw new IndexOutOfBoundsException(str);
        }
    }

    public static void zzj(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void zzk(boolean z, @CheckForNull Object obj) {
        if (!z) {
            throw new IllegalStateException((String) obj);
        }
    }

    public static void zzl(boolean z, String str, int i) {
        if (!z) {
            throw new IllegalStateException(zzjd.zza(str, Integer.valueOf(i)));
        }
    }

    public static void zzm(boolean z, String str, @CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3) {
        if (!z) {
            throw new IllegalStateException(zzjd.zza(str, obj, obj2, obj3));
        }
    }

    private static String zzn(int i, int i2, String str) {
        if (i < 0) {
            return zzjd.zza("%s (%s) must not be negative", str, Integer.valueOf(i));
        } else if (i2 >= 0) {
            return zzjd.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        } else {
            throw new IllegalArgumentException("negative size: " + i2);
        }
    }
}
