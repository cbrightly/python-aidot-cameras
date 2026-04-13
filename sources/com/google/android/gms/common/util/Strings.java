package com.google.android.gms.common.util;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class Strings {
    private static final Pattern zza = Pattern.compile("\\$\\{(.*?)\\}");

    private Strings() {
    }

    @KeepForSdk
    @Nullable
    public static String emptyToNull(@Nullable String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    @KeepForSdk
    @EnsuresNonNullIf(expression = {"#1"}, result = false)
    public static boolean isEmptyOrWhitespace(@Nullable String string) {
        return string == null || string.trim().isEmpty();
    }
}
