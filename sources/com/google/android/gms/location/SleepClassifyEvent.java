package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "SleepClassifyEventCreator")
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class SleepClassifyEvent extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<SleepClassifyEvent> CREATOR = new zzaf();
    @SafeParcelable.Field(getter = "getTimestampSec", id = 1)
    private final int zza;
    @SafeParcelable.Field(getter = "getConfidence", id = 2)
    private final int zzb;
    @SafeParcelable.Field(getter = "getMotion", id = 3)
    private final int zzc;
    @SafeParcelable.Field(getter = "getLight", id = 4)
    private final int zzd;
    @SafeParcelable.Field(getter = "getNoise", id = 5)
    private final int zze;
    @SafeParcelable.Field(getter = "getLightDiff", id = 6)
    private final int zzf;
    @SafeParcelable.Field(getter = "getNightOrDay", id = 7)
    private final int zzg;
    @SafeParcelable.Field(getter = "getConfidenceOverwrittenByAlarmClockTrigger", id = 8)
    private final boolean zzh;
    @SafeParcelable.Field(getter = "getPresenceConfidence", id = 9)
    private final int zzi;

    @ShowFirstParty
    @SafeParcelable.Constructor
    public SleepClassifyEvent(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) int i3, @SafeParcelable.Param(id = 4) int i4, @SafeParcelable.Param(id = 5) int i5, @SafeParcelable.Param(id = 6) int i6, @SafeParcelable.Param(id = 7) int i7, @SafeParcelable.Param(id = 8) boolean z, @SafeParcelable.Param(id = 9) int i8) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        this.zzd = i4;
        this.zze = i5;
        this.zzf = i6;
        this.zzg = i7;
        this.zzh = z;
        this.zzi = i8;
    }

    @NonNull
    public static List<SleepClassifyEvent> extractEvents(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        if (!hasEvents(intent)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.internal.EXTRA_SLEEP_CLASSIFY_RESULT");
        if (arrayList == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            byte[] bArr = (byte[]) arrayList.get(i);
            Preconditions.checkNotNull(bArr);
            arrayList2.add((SleepClassifyEvent) SafeParcelableSerializer.deserializeFromBytes(bArr, CREATOR));
        }
        return Collections.unmodifiableList(arrayList2);
    }

    public static boolean hasEvents(@Nullable Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.location.internal.EXTRA_SLEEP_CLASSIFY_RESULT");
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SleepClassifyEvent)) {
            return false;
        }
        SleepClassifyEvent sleepClassifyEvent = (SleepClassifyEvent) o;
        return this.zza == sleepClassifyEvent.zza && this.zzb == sleepClassifyEvent.zzb;
    }

    public int getConfidence() {
        return this.zzb;
    }

    public int getLight() {
        return this.zzd;
    }

    public int getMotion() {
        return this.zzc;
    }

    public long getTimestampMillis() {
        return ((long) this.zza) * 1000;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Integer.valueOf(this.zzb));
    }

    @NonNull
    public String toString() {
        int i = this.zza;
        int i2 = this.zzb;
        int i3 = this.zzc;
        int i4 = this.zzd;
        return i + " Conf:" + i2 + " Motion:" + i3 + " Light:" + i4;
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        Preconditions.checkNotNull(out);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 1, this.zza);
        SafeParcelWriter.writeInt(out, 2, getConfidence());
        SafeParcelWriter.writeInt(out, 3, getMotion());
        SafeParcelWriter.writeInt(out, 4, getLight());
        SafeParcelWriter.writeInt(out, 5, this.zze);
        SafeParcelWriter.writeInt(out, 6, this.zzf);
        SafeParcelWriter.writeInt(out, 7, this.zzg);
        SafeParcelWriter.writeBoolean(out, 8, this.zzh);
        SafeParcelWriter.writeInt(out, 9, this.zzi);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
