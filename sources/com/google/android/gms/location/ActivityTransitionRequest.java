package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@SafeParcelable.Class(creator = "ActivityTransitionRequestCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class ActivityTransitionRequest extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<ActivityTransitionRequest> CREATOR = new zzh();
    @NonNull
    public static final Comparator<ActivityTransition> IS_SAME_TRANSITION = new zzg();
    @SafeParcelable.Field(getter = "getActivityTransitions", id = 1)
    private final List zza;
    @SafeParcelable.Field(getter = "getTag", id = 2)
    @Nullable
    private final String zzb;
    @SafeParcelable.Field(getter = "getClients", id = 3)
    private final List zzc;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getContextAttributionTag", id = 4)
    @Nullable
    private String zzd;

    public ActivityTransitionRequest(@NonNull List<ActivityTransition> transitions) {
        this(transitions, (String) null, (List) null, (String) null);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityTransitionRequest activityTransitionRequest = (ActivityTransitionRequest) o;
        return Objects.equal(this.zza, activityTransitionRequest.zza) && Objects.equal(this.zzb, activityTransitionRequest.zzb) && Objects.equal(this.zzd, activityTransitionRequest.zzd) && Objects.equal(this.zzc, activityTransitionRequest.zzc);
    }

    public int hashCode() {
        int i;
        int i2;
        int hashCode = this.zza.hashCode() * 31;
        String str = this.zzb;
        int i3 = 0;
        if (str != null) {
            i = str.hashCode();
        } else {
            i = 0;
        }
        int i4 = (hashCode + i) * 31;
        List list = this.zzc;
        if (list != null) {
            i2 = list.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 31;
        String str2 = this.zzd;
        if (str2 != null) {
            i3 = str2.hashCode();
        }
        return i5 + i3;
    }

    public void serializeToIntentExtra(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        SafeParcelableSerializer.serializeToIntentExtra(this, intent, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_REQUEST");
    }

    @NonNull
    public String toString() {
        String valueOf = String.valueOf(this.zza);
        String str = this.zzb;
        String valueOf2 = String.valueOf(this.zzc);
        String str2 = this.zzd;
        return "ActivityTransitionRequest [mTransitions=" + valueOf + ", mTag='" + str + "', mClients=" + valueOf2 + ", mAttributionTag=" + str2 + "]";
    }

    public void writeToParcel(@NonNull Parcel dest, int i) {
        Preconditions.checkNotNull(dest);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeTypedList(dest, 1, this.zza, false);
        SafeParcelWriter.writeString(dest, 2, this.zzb, false);
        SafeParcelWriter.writeTypedList(dest, 3, this.zzc, false);
        SafeParcelWriter.writeString(dest, 4, this.zzd, false);
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }

    @NonNull
    public final ActivityTransitionRequest zza(@Nullable String str) {
        this.zzd = str;
        return this;
    }

    @SafeParcelable.Constructor
    public ActivityTransitionRequest(@SafeParcelable.Param(id = 1) @NonNull List list, @SafeParcelable.Param(id = 2) @Nullable String str, @SafeParcelable.Param(id = 3) @Nullable List list2, @SafeParcelable.Param(id = 4) @Nullable String str2) {
        boolean z;
        List list3;
        Preconditions.checkNotNull(list, "transitions can't be null");
        if (list.size() > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "transitions can't be empty.");
        Preconditions.checkNotNull(list);
        TreeSet treeSet = new TreeSet(IS_SAME_TRANSITION);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityTransition activityTransition = (ActivityTransition) it.next();
            Preconditions.checkArgument(treeSet.add(activityTransition), String.format("Found duplicated transition: %s.", new Object[]{activityTransition}));
        }
        this.zza = Collections.unmodifiableList(list);
        this.zzb = str;
        if (list2 == null) {
            list3 = Collections.emptyList();
        } else {
            list3 = Collections.unmodifiableList(list2);
        }
        this.zzc = list3;
        this.zzd = str2;
    }
}
