package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ActivityTransitionEventCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class ActivityTransitionEvent extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<ActivityTransitionEvent> CREATOR = new zzf();
    @SafeParcelable.Field(getter = "getActivityType", id = 1)
    private final int zza;
    @SafeParcelable.Field(getter = "getTransitionType", id = 2)
    private final int zzb;
    @SafeParcelable.Field(getter = "getElapsedRealTimeNanos", id = 3)
    private final long zzc;

    @SafeParcelable.Constructor
    public ActivityTransitionEvent(@SafeParcelable.Param(id = 1) int activityType, @SafeParcelable.Param(id = 2) int transitionType, @SafeParcelable.Param(id = 3) long elapsedRealtimeNanos) {
        ActivityTransition.zza(transitionType);
        this.zza = activityType;
        this.zzb = transitionType;
        this.zzc = elapsedRealtimeNanos;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ActivityTransitionEvent)) {
            return false;
        }
        ActivityTransitionEvent activityTransitionEvent = (ActivityTransitionEvent) object;
        return this.zza == activityTransitionEvent.zza && this.zzb == activityTransitionEvent.zzb && this.zzc == activityTransitionEvent.zzc;
    }

    public int getActivityType() {
        return this.zza;
    }

    public long getElapsedRealTimeNanos() {
        return this.zzc;
    }

    public int getTransitionType() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Integer.valueOf(this.zzb), Long.valueOf(this.zzc));
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.zza;
        sb.append("ActivityType " + i);
        sb.append(" ");
        int i2 = this.zzb;
        sb.append("TransitionType " + i2);
        sb.append(" ");
        long j = this.zzc;
        sb.append("ElapsedRealTimeNanos " + j);
        return sb.toString();
    }

    public void writeToParcel(@NonNull Parcel dest, int i) {
        Preconditions.checkNotNull(dest);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeInt(dest, 1, getActivityType());
        SafeParcelWriter.writeInt(dest, 2, getTransitionType());
        SafeParcelWriter.writeLong(dest, 3, getElapsedRealTimeNanos());
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }
}
