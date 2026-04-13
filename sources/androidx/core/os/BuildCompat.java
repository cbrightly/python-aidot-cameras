package androidx.core.os;

import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresOptIn;
import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;

public class BuildCompat {

    @RequiresOptIn
    public @interface PrereleaseSdkCheck {
    }

    private BuildCompat() {
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    protected static boolean isAtLeastPreReleaseCodename(@NonNull String codename, @NonNull String buildCodename) {
        if (!"REL".equals(buildCodename) && buildCodename.compareTo(codename) >= 0) {
            return true;
        }
        return false;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 24)
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 25)
    public static boolean isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 26)
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 27)
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 28)
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 29)
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Deprecated
    @ChecksSdkIntAtLeast(api = 30)
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @ChecksSdkIntAtLeast(api = 31, codename = "S")
    public static boolean isAtLeastS() {
        return Build.VERSION.SDK_INT >= 31 || isAtLeastPreReleaseCodename(ExifInterface.LATITUDE_SOUTH, Build.VERSION.CODENAME);
    }

    @ChecksSdkIntAtLeast(codename = "T")
    @PrereleaseSdkCheck
    public static boolean isAtLeastT() {
        return isAtLeastPreReleaseCodename(ExifInterface.GPS_DIRECTION_TRUE, Build.VERSION.CODENAME);
    }
}
