package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.zzb;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("Uninstantiable");
    }

    @KeepForSdk
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    @KeepForSdk
    public static double checkArgumentInRange(double value, double lower, double upper, @NonNull String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(zza("%s is out of range of [%f, %f] (too low)", valueName, Double.valueOf(lower), Double.valueOf(upper)));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(zza("%s is out of range of [%f, %f] (too high)", valueName, Double.valueOf(lower), Double.valueOf(upper)));
        }
    }

    @KeepForSdk
    public static void checkHandlerThread(@NonNull Handler handler) {
        String str;
        Looper myLooper = Looper.myLooper();
        if (myLooper != handler.getLooper()) {
            if (myLooper != null) {
                str = myLooper.getThread().getName();
            } else {
                str = "null current looper";
            }
            String name = handler.getLooper().getThread().getName();
            throw new IllegalStateException("Must be called on " + name + " thread, but got " + str + ".");
        }
    }

    @KeepForSdk
    public static void checkMainThread() {
        checkMainThread("Must be called on the main application thread");
    }

    @CanIgnoreReturnValue
    @EnsuresNonNull({"#1"})
    @NonNull
    @KeepForSdk
    public static String checkNotEmpty(@Nullable String string) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException("Given String is empty or null");
    }

    @KeepForSdk
    public static void checkNotMainThread() {
        checkNotMainThread("Must not be called on the main application thread");
    }

    @CanIgnoreReturnValue
    @EnsuresNonNull({"#1"})
    @NonNull
    @KeepForSdk
    public static <T> T checkNotNull(@Nullable T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException("null reference");
    }

    @KeepForSdk
    @CanIgnoreReturnValue
    public static int checkNotZero(int value) {
        if (value != 0) {
            return value;
        }
        throw new IllegalArgumentException("Given Integer is zero");
    }

    @KeepForSdk
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    static String zza(String str, Object... objArr) {
        int indexOf;
        StringBuilder sb = new StringBuilder(str.length() + 48);
        int i = 0;
        int i2 = 0;
        while (i < 3 && (indexOf = str.indexOf("%s", i2)) != -1) {
            sb.append(str.substring(i2, indexOf));
            sb.append(objArr[i]);
            i2 = indexOf + 2;
            i++;
        }
        sb.append(str.substring(i2));
        if (i < 3) {
            sb.append(" [");
            sb.append(objArr[i]);
            for (int i3 = i + 1; i3 < 3; i3++) {
                sb.append(", ");
                sb.append(objArr[i3]);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @KeepForSdk
    public static void checkArgument(boolean expression, @NonNull Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    @KeepForSdk
    public static void checkMainThread(@NonNull String errorMessage) {
        if (!zzb.zza()) {
            throw new IllegalStateException(errorMessage);
        }
    }

    @KeepForSdk
    public static void checkNotMainThread(@NonNull String errorMessage) {
        if (zzb.zza()) {
            throw new IllegalStateException(errorMessage);
        }
    }

    @CanIgnoreReturnValue
    @EnsuresNonNull({"#1"})
    @NonNull
    @KeepForSdk
    public static <T> T checkNotNull(@NonNull T reference, @NonNull Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    @KeepForSdk
    @CanIgnoreReturnValue
    public static int checkNotZero(int value, @NonNull Object errorMessage) {
        if (value != 0) {
            return value;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    @KeepForSdk
    public static void checkState(boolean expression, @NonNull Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    @KeepForSdk
    public static void checkArgument(boolean expression, @NonNull String errorMessage, @NonNull Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(errorMessage, errorMessageArgs));
        }
    }

    @KeepForSdk
    public static float checkArgumentInRange(float value, float lower, float upper, @NonNull String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(zza("%s is out of range of [%f, %f] (too low)", valueName, Float.valueOf(lower), Float.valueOf(upper)));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(zza("%s is out of range of [%f, %f] (too high)", valueName, Float.valueOf(lower), Float.valueOf(upper)));
        }
    }

    @CanIgnoreReturnValue
    @EnsuresNonNull({"#1"})
    @NonNull
    @KeepForSdk
    public static String checkNotEmpty(@Nullable String string, @NonNull Object errorMessage) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    @KeepForSdk
    @CanIgnoreReturnValue
    public static long checkNotZero(long value) {
        if (value != 0) {
            return value;
        }
        throw new IllegalArgumentException("Given Long is zero");
    }

    @KeepForSdk
    public static void checkState(boolean expression, @NonNull String errorMessage, @NonNull Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(String.format(errorMessage, errorMessageArgs));
        }
    }

    @KeepForSdk
    @CanIgnoreReturnValue
    public static long checkNotZero(long value, @NonNull Object errorMessage) {
        if (value != 0) {
            return value;
        }
        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    @KeepForSdk
    @CanIgnoreReturnValue
    public static int checkArgumentInRange(int value, int lower, int upper, @NonNull String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(zza("%s is out of range of [%d, %d] (too low)", valueName, Integer.valueOf(lower), Integer.valueOf(upper)));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(zza("%s is out of range of [%d, %d] (too high)", valueName, Integer.valueOf(lower), Integer.valueOf(upper)));
        }
    }

    @KeepForSdk
    public static void checkHandlerThread(@NonNull Handler handler, @NonNull String errorMessage) {
        if (Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException(errorMessage);
        }
    }

    @KeepForSdk
    public static long checkArgumentInRange(long value, long lower, long upper, @NonNull String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(zza("%s is out of range of [%d, %d] (too low)", valueName, Long.valueOf(lower), Long.valueOf(upper)));
        } else if (value <= upper) {
            return value;
        } else {
            throw new IllegalArgumentException(zza("%s is out of range of [%d, %d] (too high)", valueName, Long.valueOf(lower), Long.valueOf(upper)));
        }
    }
}
