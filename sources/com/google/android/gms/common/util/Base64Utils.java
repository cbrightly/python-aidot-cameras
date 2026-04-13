package com.google.android.gms.common.util;

import android.util.Base64;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class Base64Utils {
    @NonNull
    @KeepForSdk
    public static byte[] decode(@NonNull String encodedData) {
        if (encodedData == null) {
            return null;
        }
        return Base64.decode(encodedData, 0);
    }

    @NonNull
    @KeepForSdk
    public static byte[] decodeUrlSafe(@NonNull String encodedData) {
        if (encodedData == null) {
            return null;
        }
        return Base64.decode(encodedData, 10);
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public static byte[] decodeUrlSafeNoPadding(@NonNull String encodedData) {
        if (encodedData == null) {
            return null;
        }
        return Base64.decode(encodedData, 11);
    }

    @NonNull
    @KeepForSdk
    public static String encode(@NonNull byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.encodeToString(data, 0);
    }

    @NonNull
    @KeepForSdk
    public static String encodeUrlSafe(@NonNull byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.encodeToString(data, 10);
    }

    @NonNull
    @KeepForSdk
    public static String encodeUrlSafeNoPadding(@NonNull byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.encodeToString(data, 11);
    }
}
