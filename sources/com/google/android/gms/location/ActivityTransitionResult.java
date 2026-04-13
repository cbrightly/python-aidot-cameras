package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "ActivityTransitionResultCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class ActivityTransitionResult extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<ActivityTransitionResult> CREATOR = new zzi();
    @SafeParcelable.Field(getter = "getTransitionEvents", id = 1)
    private final List zza;
    @SafeParcelable.Field(getter = "getExtras", id = 2)
    @Nullable
    private Bundle zzb;

    public ActivityTransitionResult(@SafeParcelable.Param(id = 1) @NonNull List<ActivityTransitionEvent> transitionEvents) {
        boolean z;
        this.zzb = null;
        Preconditions.checkNotNull(transitionEvents, "transitionEvents list can't be null.");
        if (!transitionEvents.isEmpty()) {
            for (int i = 1; i < transitionEvents.size(); i++) {
                if (transitionEvents.get(i).getElapsedRealTimeNanos() >= transitionEvents.get(i - 1).getElapsedRealTimeNanos()) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkArgument(z);
            }
        }
        this.zza = Collections.unmodifiableList(transitionEvents);
    }

    @Nullable
    public static ActivityTransitionResult extractResult(@NonNull Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        return (ActivityTransitionResult) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT", CREATOR);
    }

    public static boolean hasResult(@Nullable Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT");
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.zza.equals(((ActivityTransitionResult) o).zza);
    }

    @NonNull
    public List<ActivityTransitionEvent> getTransitionEvents() {
        return this.zza;
    }

    public int hashCode() {
        return this.zza.hashCode();
    }

    public void writeToParcel(@NonNull Parcel dest, int i) {
        Preconditions.checkNotNull(dest);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeTypedList(dest, 1, getTransitionEvents(), false);
        SafeParcelWriter.writeBundle(dest, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }

    @ShowFirstParty
    @SafeParcelable.Constructor
    public ActivityTransitionResult(@SafeParcelable.Param(id = 1) @NonNull List list, @SafeParcelable.Param(id = 2) @Nullable Bundle bundle) {
        this(list);
        this.zzb = bundle;
    }
}
