package com.google.android.libraries.places.internal;

import android.location.Location;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.RectangularBounds;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfu {
    private static final zzjt zza;

    static {
        zzjs zzjs = new zzjs();
        zzjs.zza(zzda.NONE, "NONE");
        zzjs.zza(zzda.PSK, "WPA_PSK");
        zzjs.zza(zzda.EAP, "WPA_EAP");
        zzjs.zza(zzda.OTHER, "SECURED_NONE");
        zza = zzjs.zzb();
    }

    @Nullable
    public static String zza(@Nullable Location location) {
        if (location == null) {
            return null;
        }
        return zzf(location.getLatitude(), location.getLongitude());
    }

    @Nullable
    public static String zzb(@Nullable LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        return zzf(latLng.latitude, latLng.longitude);
    }

    @Nullable
    public static String zzc(@Nullable LocationBias locationBias) {
        if (locationBias == null) {
            return null;
        }
        if (locationBias instanceof RectangularBounds) {
            return zzg((RectangularBounds) locationBias);
        }
        throw new AssertionError("Unknown LocationBias type.");
    }

    @Nullable
    public static String zzd(@Nullable LocationRestriction locationRestriction) {
        if (locationRestriction == null) {
            return null;
        }
        if (locationRestriction instanceof RectangularBounds) {
            return zzg((RectangularBounds) locationRestriction);
        }
        throw new AssertionError("Unknown LocationRestriction type.");
    }

    public static String zze(zzjq zzjq, int i) {
        String str;
        StringBuilder sb = new StringBuilder();
        int size = zzjq.size();
        int i2 = 0;
        while (i2 < size) {
            zzdb zzdb = (zzdb) zzjq.get(i2);
            int length = sb.length();
            zzjs zzjs = new zzjs();
            zzjs.zza("mac", zzdb.zzd());
            zzjs.zza("strength_dbm", Integer.valueOf(zzdb.zzb()));
            zzjs.zza("wifi_auth_type", zza.get(zzdb.zzc()));
            zzjs.zza("is_connected", Boolean.valueOf(zzdb.zze()));
            zzjs.zza("frequency_mhz", Integer.valueOf(zzdb.zza()));
            zzjt zzb = zzjs.zzb();
            zzit zzb2 = zzit.zzb(",");
            Iterator it = zzb.entrySet().iterator();
            StringBuilder sb2 = new StringBuilder();
            try {
                zzir.zza(sb2, it, zzb2, "=");
                String sb3 = sb2.toString();
                int length2 = sb.length();
                if (length > 0) {
                    str = "|";
                } else {
                    str = "";
                }
                String concat = str.concat(sb3);
                if (length2 + concat.length() > 4000) {
                    break;
                }
                sb.append(concat);
                i2++;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
        return sb.toString();
    }

    private static String zzf(double d, double d2) {
        return String.format(Locale.US, "%.15f,%.15f", new Object[]{Double.valueOf(d), Double.valueOf(d2)});
    }

    private static String zzg(RectangularBounds rectangularBounds) {
        LatLng southwest = rectangularBounds.getSouthwest();
        double d = southwest.latitude;
        double d2 = southwest.longitude;
        LatLng northeast = rectangularBounds.getNortheast();
        double d3 = northeast.latitude;
        double d4 = northeast.longitude;
        return String.format(Locale.US, "rectangle:%.15f,%.15f|%.15f,%.15f", new Object[]{Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3), Double.valueOf(d4)});
    }
}
