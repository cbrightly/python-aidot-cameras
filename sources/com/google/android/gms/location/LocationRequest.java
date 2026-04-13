package com.google.android.gms.location;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.WorkSource;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.WorkSourceUtil;
import com.google.android.gms.internal.location.zzd;
import com.google.android.gms.internal.location.zzdj;
import org.checkerframework.dataflow.qual.Pure;

@SafeParcelable.Class(creator = "LocationRequestCreator")
@SafeParcelable.Reserved({4, 5, 1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class LocationRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<LocationRequest> CREATOR = new zzx();
    @Deprecated
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    @Deprecated
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    @Deprecated
    public static final int PRIORITY_LOW_POWER = 104;
    @Deprecated
    public static final int PRIORITY_NO_POWER = 105;
    @SafeParcelable.Field(defaultValueUnchecked = "Priority.PRIORITY_BALANCED_POWER_ACCURACY", getter = "getPriority", id = 1)
    private int zza;
    @SafeParcelable.Field(defaultValue = "3600000", getter = "getIntervalMillis", id = 2)
    private long zzb;
    @SafeParcelable.Field(defaultValue = "600000", getter = "getMinUpdateIntervalMillis", id = 3)
    private long zzc;
    @SafeParcelable.Field(defaultValue = "0", getter = "getMaxUpdateDelayMillis", id = 8)
    private long zzd;
    @SafeParcelable.Field(defaultValueUnchecked = "Long.MAX_VALUE", getter = "getDurationMillis", id = 10)
    private long zze;
    @SafeParcelable.Field(defaultValueUnchecked = "Integer.MAX_VALUE", getter = "getMaxUpdates", id = 6)
    private int zzf;
    @SafeParcelable.Field(defaultValue = "0", getter = "getMinUpdateDistanceMeters", id = 7)
    private float zzg;
    @SafeParcelable.Field(defaultValue = "false", getter = "isWaitForAccurateLocation", id = 9)
    private boolean zzh;
    @SafeParcelable.Field(defaultValueUnchecked = "-1", getter = "getMaxUpdateAgeMillis", id = 11)
    private long zzi;
    @SafeParcelable.Field(defaultValueUnchecked = "Granularity.GRANULARITY_PERMISSION_LEVEL", getter = "getGranularity", id = 12)
    private final int zzj;
    @SafeParcelable.Field(defaultValueUnchecked = "ThrottleBehavior.THROTTLE_BACKGROUND", getter = "getThrottleBehavior", id = 13)
    private final int zzk;
    @SafeParcelable.Field(getter = "getModuleId", id = 14)
    @Nullable
    private final String zzl;
    @SafeParcelable.Field(defaultValue = "false", getter = "isBypass", id = 15)
    private final boolean zzm;
    @SafeParcelable.Field(defaultValueUnchecked = "new android.os.WorkSource()", getter = "getWorkSource", id = 16)
    private final WorkSource zzn;
    @SafeParcelable.Field(getter = "getImpersonation", id = 17)
    @Nullable
    private final zzd zzo;

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LocationRequest() {
        /*
            r23 = this;
            r0 = r23
            android.os.WorkSource r1 = new android.os.WorkSource
            r21 = r1
            r1.<init>()
            r1 = 102(0x66, float:1.43E-43)
            r2 = 3600000(0x36ee80, double:1.7786363E-317)
            r4 = 600000(0x927c0, double:2.964394E-318)
            r6 = 0
            r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r12 = 2147483647(0x7fffffff, float:NaN)
            r13 = 0
            r14 = 1
            r15 = 3600000(0x36ee80, double:1.7786363E-317)
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r22 = 0
            r0.<init>(r1, r2, r4, r6, r8, r10, r12, r13, r14, r15, r17, r18, r19, r20, r21, r22)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.LocationRequest.<init>():void");
    }

    @NonNull
    @Deprecated
    public static LocationRequest create() {
        WorkSource workSource = r1;
        WorkSource workSource2 = new WorkSource();
        return new LocationRequest(102, CostTimeUtil.HOUR, 600000, 0, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, Integer.MAX_VALUE, 0.0f, true, CostTimeUtil.HOUR, 0, 0, (String) null, false, workSource, (zzd) null);
    }

    private static String zzf(long j) {
        return j == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE ? "∞" : zzdj.zza(j);
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof LocationRequest) {
            LocationRequest locationRequest = (LocationRequest) object;
            if (this.zza == locationRequest.zza && ((isPassive() || this.zzb == locationRequest.zzb) && this.zzc == locationRequest.zzc && isBatched() == locationRequest.isBatched() && ((!isBatched() || this.zzd == locationRequest.zzd) && this.zze == locationRequest.zze && this.zzf == locationRequest.zzf && this.zzg == locationRequest.zzg && this.zzh == locationRequest.zzh && this.zzj == locationRequest.zzj && this.zzk == locationRequest.zzk && this.zzm == locationRequest.zzm && this.zzn.equals(locationRequest.zzn) && Objects.equal(this.zzl, locationRequest.zzl) && Objects.equal(this.zzo, locationRequest.zzo)))) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Pure
    public long getDurationMillis() {
        return this.zze;
    }

    @Deprecated
    @Pure
    public long getExpirationTime() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.zze;
        long j2 = elapsedRealtime + j;
        return ((elapsedRealtime ^ j2) & (j ^ j2)) < 0 ? DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE : j2;
    }

    @Deprecated
    @Pure
    public long getFastestInterval() {
        return getMinUpdateIntervalMillis();
    }

    @Pure
    public int getGranularity() {
        return this.zzj;
    }

    @Deprecated
    @Pure
    public long getInterval() {
        return getIntervalMillis();
    }

    @Pure
    public long getIntervalMillis() {
        return this.zzb;
    }

    @Pure
    public long getMaxUpdateAgeMillis() {
        return this.zzi;
    }

    @Pure
    public long getMaxUpdateDelayMillis() {
        return this.zzd;
    }

    @Pure
    public int getMaxUpdates() {
        return this.zzf;
    }

    @Deprecated
    @Pure
    public long getMaxWaitTime() {
        return Math.max(this.zzd, this.zzb);
    }

    @Pure
    public float getMinUpdateDistanceMeters() {
        return this.zzg;
    }

    @Pure
    public long getMinUpdateIntervalMillis() {
        return this.zzc;
    }

    @Deprecated
    @Pure
    public int getNumUpdates() {
        return getMaxUpdates();
    }

    @Pure
    public int getPriority() {
        return this.zza;
    }

    @Deprecated
    @Pure
    public float getSmallestDisplacement() {
        return getMinUpdateDistanceMeters();
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Long.valueOf(this.zzb), Long.valueOf(this.zzc), this.zzn);
    }

    @Pure
    public boolean isBatched() {
        long j = this.zzd;
        return j > 0 && (j >> 1) >= this.zzb;
    }

    @Deprecated
    @Pure
    public boolean isFastestIntervalExplicitlySet() {
        return true;
    }

    @Pure
    public boolean isPassive() {
        return this.zza == 105;
    }

    public boolean isWaitForAccurateLocation() {
        return this.zzh;
    }

    @NonNull
    @Deprecated
    public LocationRequest setExpirationDuration(long durationMillis) {
        Preconditions.checkArgument(durationMillis > 0, "durationMillis must be greater than 0");
        this.zze = durationMillis;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setExpirationTime(long elapsedRealtime) {
        this.zze = Math.max(1, elapsedRealtime - SystemClock.elapsedRealtime());
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setFastestInterval(long fastestIntervalMillis) {
        boolean z;
        if (fastestIntervalMillis >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "illegal fastest interval: %d", Long.valueOf(fastestIntervalMillis));
        this.zzc = fastestIntervalMillis;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setInterval(long intervalMillis) {
        Preconditions.checkArgument(intervalMillis >= 0, "intervalMillis must be greater than or equal to 0");
        long j = this.zzc;
        long j2 = this.zzb;
        if (j == j2 / 6) {
            this.zzc = intervalMillis / 6;
        }
        if (this.zzi == j2) {
            this.zzi = intervalMillis;
        }
        this.zzb = intervalMillis;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setMaxWaitTime(long maxWaitTimeMillis) {
        boolean z;
        if (maxWaitTimeMillis >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "illegal max wait time: %d", Long.valueOf(maxWaitTimeMillis));
        this.zzd = maxWaitTimeMillis;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setNumUpdates(int maxUpdates) {
        if (maxUpdates > 0) {
            this.zzf = maxUpdates;
            return this;
        }
        throw new IllegalArgumentException("invalid numUpdates: " + maxUpdates);
    }

    @NonNull
    @Deprecated
    public LocationRequest setPriority(int priority) {
        zzae.zza(priority);
        this.zza = priority;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setSmallestDisplacement(float smallestDisplacementMeters) {
        if (smallestDisplacementMeters >= 0.0f) {
            this.zzg = smallestDisplacementMeters;
            return this;
        }
        throw new IllegalArgumentException("invalid displacement: " + smallestDisplacementMeters);
    }

    @NonNull
    @Deprecated
    public LocationRequest setWaitForAccurateLocation(boolean z) {
        this.zzh = z;
        return this;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[");
        if (isPassive()) {
            sb.append(zzae.zzb(this.zza));
        } else {
            sb.append("@");
            if (isBatched()) {
                zzdj.zzb(this.zzb, sb);
                sb.append("/");
                zzdj.zzb(this.zzd, sb);
            } else {
                zzdj.zzb(this.zzb, sb);
            }
            sb.append(" ");
            sb.append(zzae.zzb(this.zza));
        }
        if (isPassive() || this.zzc != this.zzb) {
            sb.append(", minUpdateInterval=");
            sb.append(zzf(this.zzc));
        }
        if (((double) this.zzg) > 0.0d) {
            sb.append(", minUpdateDistance=");
            sb.append(this.zzg);
        }
        if (!isPassive() ? this.zzi != this.zzb : this.zzi != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            sb.append(", maxUpdateAge=");
            sb.append(zzf(this.zzi));
        }
        if (this.zze != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            sb.append(", duration=");
            zzdj.zzb(this.zze, sb);
        }
        if (this.zzf != Integer.MAX_VALUE) {
            sb.append(", maxUpdates=");
            sb.append(this.zzf);
        }
        if (this.zzk != 0) {
            sb.append(", ");
            sb.append(zzai.zza(this.zzk));
        }
        if (this.zzj != 0) {
            sb.append(", ");
            sb.append(zzo.zzb(this.zzj));
        }
        if (this.zzh) {
            sb.append(", waitForAccurateLocation");
        }
        if (this.zzm) {
            sb.append(", bypass");
        }
        if (this.zzl != null) {
            sb.append(", moduleId=");
            sb.append(this.zzl);
        }
        if (!WorkSourceUtil.isEmpty(this.zzn)) {
            sb.append(", ");
            sb.append(this.zzn);
        }
        if (this.zzo != null) {
            sb.append(", impersonation=");
            sb.append(this.zzo);
        }
        sb.append(']');
        return sb.toString();
    }

    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getPriority());
        SafeParcelWriter.writeLong(parcel, 2, getIntervalMillis());
        SafeParcelWriter.writeLong(parcel, 3, getMinUpdateIntervalMillis());
        SafeParcelWriter.writeInt(parcel, 6, getMaxUpdates());
        SafeParcelWriter.writeFloat(parcel, 7, getMinUpdateDistanceMeters());
        SafeParcelWriter.writeLong(parcel, 8, getMaxUpdateDelayMillis());
        SafeParcelWriter.writeBoolean(parcel, 9, isWaitForAccurateLocation());
        SafeParcelWriter.writeLong(parcel, 10, getDurationMillis());
        SafeParcelWriter.writeLong(parcel, 11, getMaxUpdateAgeMillis());
        SafeParcelWriter.writeInt(parcel, 12, getGranularity());
        SafeParcelWriter.writeInt(parcel, 13, this.zzk);
        SafeParcelWriter.writeString(parcel, 14, this.zzl, false);
        SafeParcelWriter.writeBoolean(parcel, 15, this.zzm);
        SafeParcelWriter.writeParcelable(parcel, 16, this.zzn, flags, false);
        SafeParcelWriter.writeParcelable(parcel, 17, this.zzo, flags, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @Pure
    public final int zza() {
        return this.zzk;
    }

    @NonNull
    @Pure
    public final WorkSource zzb() {
        return this.zzn;
    }

    @Nullable
    @Pure
    public final zzd zzc() {
        return this.zzo;
    }

    @Deprecated
    @Nullable
    @Pure
    public final String zzd() {
        return this.zzl;
    }

    @Pure
    public final boolean zze() {
        return this.zzm;
    }

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public static final class Builder {
        public static final long IMPLICIT_MAX_UPDATE_AGE = -1;
        public static final long IMPLICIT_MIN_UPDATE_INTERVAL = -1;
        private int zza;
        private long zzb;
        private long zzc;
        private long zzd;
        private long zze;
        private int zzf;
        private float zzg;
        private boolean zzh;
        private long zzi;
        private int zzj;
        private int zzk;
        @Nullable
        private String zzl;
        private boolean zzm;
        @Nullable
        private WorkSource zzn;
        @Nullable
        private zzd zzo;

        public Builder(int priority, long intervalMillis) {
            boolean z;
            if (intervalMillis >= 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "intervalMillis must be greater than or equal to 0");
            zzae.zza(priority);
            this.zza = priority;
            this.zzb = intervalMillis;
            this.zzc = -1;
            this.zzd = 0;
            this.zze = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            this.zzf = Integer.MAX_VALUE;
            this.zzg = 0.0f;
            this.zzh = true;
            this.zzi = -1;
            this.zzj = 0;
            this.zzk = 0;
            this.zzl = null;
            this.zzm = false;
            this.zzn = null;
            this.zzo = null;
        }

        @NonNull
        public LocationRequest build() {
            long j;
            int i = this.zza;
            long j2 = this.zzb;
            long j3 = this.zzc;
            if (j3 == -1) {
                j3 = j2;
            } else if (i != 105) {
                j3 = Math.min(j3, j2);
            }
            long max = Math.max(this.zzd, this.zzb);
            long j4 = this.zze;
            int i2 = this.zzf;
            float f = this.zzg;
            boolean z = this.zzh;
            long j5 = this.zzi;
            if (j5 == -1) {
                j = this.zzb;
            } else {
                j = j5;
            }
            int i3 = this.zzj;
            int i4 = this.zzk;
            String str = this.zzl;
            boolean z2 = this.zzm;
            WorkSource workSource = r7;
            WorkSource workSource2 = new WorkSource(this.zzn);
            return new LocationRequest(i, j2, j3, max, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, j4, i2, f, z, j, i3, i4, str, z2, workSource, this.zzo);
        }

        @NonNull
        public Builder setDurationMillis(long durationMillis) {
            Preconditions.checkArgument(durationMillis > 0, "durationMillis must be greater than 0");
            this.zze = durationMillis;
            return this;
        }

        @NonNull
        public Builder setGranularity(int granularity) {
            zzo.zza(granularity);
            this.zzj = granularity;
            return this;
        }

        @NonNull
        public Builder setIntervalMillis(long intervalMillis) {
            Preconditions.checkArgument(intervalMillis >= 0, "intervalMillis must be greater than or equal to 0");
            this.zzb = intervalMillis;
            return this;
        }

        @NonNull
        public Builder setMaxUpdateAgeMillis(long maxUpdateAgeMillis) {
            boolean z = true;
            if (maxUpdateAgeMillis != -1 && maxUpdateAgeMillis < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "maxUpdateAgeMillis must be greater than or equal to 0, or IMPLICIT_MAX_UPDATE_AGE");
            this.zzi = maxUpdateAgeMillis;
            return this;
        }

        @NonNull
        public Builder setMaxUpdateDelayMillis(long maxUpdateDelayMillis) {
            Preconditions.checkArgument(maxUpdateDelayMillis >= 0, "maxUpdateDelayMillis must be greater than or equal to 0");
            this.zzd = maxUpdateDelayMillis;
            return this;
        }

        @NonNull
        public Builder setMaxUpdates(int maxUpdates) {
            Preconditions.checkArgument(maxUpdates > 0, "maxUpdates must be greater than 0");
            this.zzf = maxUpdates;
            return this;
        }

        @NonNull
        public Builder setMinUpdateDistanceMeters(float minUpdateDistanceMeters) {
            Preconditions.checkArgument(minUpdateDistanceMeters >= 0.0f, "minUpdateDistanceMeters must be greater than or equal to 0");
            this.zzg = minUpdateDistanceMeters;
            return this;
        }

        @NonNull
        public Builder setMinUpdateIntervalMillis(long minUpdateIntervalMillis) {
            boolean z = true;
            if (minUpdateIntervalMillis != -1 && minUpdateIntervalMillis < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "minUpdateIntervalMillis must be greater than or equal to 0, or IMPLICIT_MIN_UPDATE_INTERVAL");
            this.zzc = minUpdateIntervalMillis;
            return this;
        }

        @NonNull
        public Builder setPriority(int priority) {
            zzae.zza(priority);
            this.zza = priority;
            return this;
        }

        @NonNull
        public Builder setWaitForAccurateLocation(boolean z) {
            this.zzh = z;
            return this;
        }

        @RequiresPermission(anyOf = {"android.permission.WRITE_SECURE_SETTINGS", "android.permission.LOCATION_BYPASS"})
        @NonNull
        public final Builder zza(boolean z) {
            this.zzm = z;
            return this;
        }

        @NonNull
        @Deprecated
        public final Builder zzb(@Nullable String str) {
            if (Build.VERSION.SDK_INT < 30) {
                this.zzl = str;
            }
            return this;
        }

        @NonNull
        public final Builder zzc(int i) {
            boolean z;
            int i2 = 2;
            if (i == 0 || i == 1) {
                i2 = i;
                z = true;
            } else if (i == 2) {
                i = 2;
                z = true;
            } else {
                i2 = i;
                z = false;
            }
            Preconditions.checkArgument(z, "throttle behavior %d must be a ThrottleBehavior.THROTTLE_* constant", Integer.valueOf(i));
            this.zzk = i2;
            return this;
        }

        @RequiresPermission("android.permission.UPDATE_DEVICE_STATS")
        @NonNull
        public final Builder zzd(@Nullable WorkSource workSource) {
            this.zzn = workSource;
            return this;
        }

        public Builder(long intervalMillis) {
            Preconditions.checkArgument(intervalMillis >= 0, "intervalMillis must be greater than or equal to 0");
            this.zzb = intervalMillis;
            this.zza = 102;
            this.zzc = -1;
            this.zzd = 0;
            this.zze = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            this.zzf = Integer.MAX_VALUE;
            this.zzg = 0.0f;
            this.zzh = true;
            this.zzi = -1;
            this.zzj = 0;
            this.zzk = 0;
            this.zzl = null;
            this.zzm = false;
            this.zzn = null;
            this.zzo = null;
        }

        public Builder(@NonNull LocationRequest request) {
            this.zza = request.getPriority();
            this.zzb = request.getIntervalMillis();
            this.zzc = request.getMinUpdateIntervalMillis();
            this.zzd = request.getMaxUpdateDelayMillis();
            this.zze = request.getDurationMillis();
            this.zzf = request.getMaxUpdates();
            this.zzg = request.getMinUpdateDistanceMeters();
            this.zzh = request.isWaitForAccurateLocation();
            this.zzi = request.getMaxUpdateAgeMillis();
            this.zzj = request.getGranularity();
            this.zzk = request.zza();
            this.zzl = request.zzd();
            this.zzm = request.zze();
            this.zzn = request.zzb();
            this.zzo = request.zzc();
        }
    }

    @SafeParcelable.Constructor
    LocationRequest(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) long j, @SafeParcelable.Param(id = 3) long j2, @SafeParcelable.Param(id = 8) long j3, @SafeParcelable.RemovedParam(defaultValueUnchecked = "Long.MAX_VALUE", id = 5) long j4, @SafeParcelable.Param(id = 10) long j5, @SafeParcelable.Param(id = 6) int i2, @SafeParcelable.Param(id = 7) float f, @SafeParcelable.Param(id = 9) boolean z, @SafeParcelable.Param(id = 11) long j6, @SafeParcelable.Param(id = 12) int i3, @SafeParcelable.Param(id = 13) int i4, @SafeParcelable.Param(id = 14) @Nullable String str, @SafeParcelable.Param(id = 15) boolean z2, @SafeParcelable.Param(id = 16) WorkSource workSource, @SafeParcelable.Param(id = 17) @Nullable zzd zzd2) {
        long j7;
        this.zza = i;
        long j8 = j;
        this.zzb = j8;
        this.zzc = j2;
        this.zzd = j3;
        if (j4 == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            j7 = j5;
        } else {
            j7 = Math.min(Math.max(1, j4 - SystemClock.elapsedRealtime()), j5);
        }
        this.zze = j7;
        this.zzf = i2;
        this.zzg = f;
        this.zzh = z;
        this.zzi = j6 != -1 ? j6 : j8;
        this.zzj = i3;
        this.zzk = i4;
        this.zzl = str;
        this.zzm = z2;
        this.zzn = workSource;
        this.zzo = zzd2;
    }
}
