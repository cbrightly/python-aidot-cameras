package com.google.android.gms.maps.internal;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zza {
    public static byte zza(@Nullable Boolean bool) {
        if (bool != null) {
            return !bool.booleanValue() ? (byte) 0 : 1;
        }
        return -1;
    }

    @Nullable
    public static Boolean zzb(byte b) {
        switch (b) {
            case 0:
                return Boolean.FALSE;
            case 1:
                return Boolean.TRUE;
            default:
                return null;
        }
    }
}
