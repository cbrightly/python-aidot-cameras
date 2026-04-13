package com.google.android.gms.location;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.CommonStatusCodes;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class GeofenceStatusCodes extends CommonStatusCodes {
    public static final int GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION = 1004;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_REQUEST_TOO_FREQUENT = 1005;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;

    private GeofenceStatusCodes() {
    }

    @NonNull
    public static String getStatusCodeString(int statusCode) {
        switch (statusCode) {
            case 1000:
                return "GEOFENCE_NOT_AVAILABLE";
            case 1001:
                return "GEOFENCE_TOO_MANY_GEOFENCES";
            case 1002:
                return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
            case 1004:
                return "GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION";
            default:
                return CommonStatusCodes.getStatusCodeString(statusCode);
        }
    }

    public static int zza(int i) {
        if (i == 0) {
            return i;
        }
        if (i < 1000 || i >= 1006) {
            return 13;
        }
        return i;
    }
}
