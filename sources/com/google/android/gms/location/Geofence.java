package com.google.android.gms.location;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.location.zzdh;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public static final class Builder {
        private String zza = null;
        @TransitionTypes
        private int zzb = 0;
        private long zzc = Long.MIN_VALUE;
        private short zzd = -1;
        private double zze;
        private double zzf;
        private float zzg;
        private int zzh = 0;
        private int zzi = -1;

        @NonNull
        public Geofence build() {
            if (this.zza != null) {
                int i = this.zzb;
                if (i == 0) {
                    throw new IllegalArgumentException("Transitions types not set.");
                } else if ((i & 4) != 0 && this.zzi < 0) {
                    throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELL.");
                } else if (this.zzc == Long.MIN_VALUE) {
                    throw new IllegalArgumentException("Expiration not set.");
                } else if (this.zzd == -1) {
                    throw new IllegalArgumentException("Geofence region not set.");
                } else if (this.zzh >= 0) {
                    return new zzdh(this.zza, this.zzb, 1, this.zze, this.zzf, this.zzg, this.zzc, this.zzh, this.zzi);
                } else {
                    throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
                }
            } else {
                throw new IllegalArgumentException("Request ID not set.");
            }
        }

        @NonNull
        public Builder setCircularRegion(@FloatRange(from = -90.0d, to = 90.0d) double latitude, @FloatRange(from = -180.0d, to = 180.0d) double longitude, @FloatRange(from = 0.0d, fromInclusive = false) float radius) {
            boolean z;
            boolean z2;
            boolean z3 = false;
            if (latitude < -90.0d || latitude > 90.0d) {
                z = false;
            } else {
                z = true;
            }
            Preconditions.checkArgument(z, "Invalid latitude: " + latitude);
            if (longitude < -180.0d || longitude > 180.0d) {
                z2 = false;
            } else {
                z2 = true;
            }
            Preconditions.checkArgument(z2, "Invalid longitude: " + longitude);
            if (radius > 0.0f) {
                z3 = true;
            }
            Preconditions.checkArgument(z3, "Invalid radius: " + radius);
            this.zzd = 1;
            this.zze = latitude;
            this.zzf = longitude;
            this.zzg = radius;
            return this;
        }

        @NonNull
        public Builder setExpirationDuration(long durationMillis) {
            if (durationMillis < 0) {
                this.zzc = -1;
            } else {
                this.zzc = DefaultClock.getInstance().elapsedRealtime() + durationMillis;
            }
            return this;
        }

        @NonNull
        public Builder setLoiteringDelay(int i) {
            this.zzi = i;
            return this;
        }

        @NonNull
        public Builder setNotificationResponsiveness(@IntRange(from = 0) int i) {
            this.zzh = i;
            return this;
        }

        @NonNull
        public Builder setRequestId(@NonNull String requestId) {
            this.zza = (String) Preconditions.checkNotNull(requestId, "Request ID can't be set to null");
            return this;
        }

        @NonNull
        public Builder setTransitionTypes(@TransitionTypes int i) {
            this.zzb = i;
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public @interface GeofenceTransition {
    }

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public @interface TransitionTypes {
    }

    long getExpirationTime();

    double getLatitude();

    int getLoiteringDelay();

    double getLongitude();

    int getNotificationResponsiveness();

    float getRadius();

    @NonNull
    String getRequestId();

    @TransitionTypes
    int getTransitionTypes();
}
