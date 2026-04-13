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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SafeParcelable.Class(creator = "ActivityTransitionCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class ActivityTransition extends AbstractSafeParcelable {
    public static final int ACTIVITY_TRANSITION_ENTER = 0;
    public static final int ACTIVITY_TRANSITION_EXIT = 1;
    @NonNull
    public static final Parcelable.Creator<ActivityTransition> CREATOR = new zze();
    @SafeParcelable.Field(getter = "getActivityType", id = 1)
    private final int zza;
    @SafeParcelable.Field(getter = "getTransitionType", id = 2)
    private final int zzb;

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public static class Builder {
        private int zza = -1;
        private int zzb = -1;

        @NonNull
        public ActivityTransition build() {
            boolean z;
            boolean z2 = true;
            if (this.zza != -1) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "Activity type not set.");
            if (this.zzb == -1) {
                z2 = false;
            }
            Preconditions.checkState(z2, "Activity transition type not set.");
            return new ActivityTransition(this.zza, this.zzb);
        }

        @NonNull
        public Builder setActivityTransition(int transition) {
            ActivityTransition.zza(transition);
            this.zzb = transition;
            return this;
        }

        @NonNull
        public Builder setActivityType(int i) {
            this.zza = i;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public @interface SupportedActivityTransition {
    }

    @SafeParcelable.Constructor
    ActivityTransition(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2) {
        this.zza = i;
        this.zzb = i2;
    }

    public static void zza(int i) {
        boolean z = true;
        if (i < 0 || i > 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Transition type " + i + " is not valid.");
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ActivityTransition)) {
            return false;
        }
        ActivityTransition activityTransition = (ActivityTransition) object;
        return this.zza == activityTransition.zza && this.zzb == activityTransition.zzb;
    }

    public int getActivityType() {
        return this.zza;
    }

    public int getTransitionType() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Integer.valueOf(this.zzb));
    }

    @NonNull
    public String toString() {
        int i = this.zza;
        int i2 = this.zzb;
        return "ActivityTransition [mActivityType=" + i + ", mTransitionType=" + i2 + "]";
    }

    public void writeToParcel(@NonNull Parcel dest, int i) {
        Preconditions.checkNotNull(dest);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeInt(dest, 1, getActivityType());
        SafeParcelWriter.writeInt(dest, 2, getTransitionType());
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }
}
