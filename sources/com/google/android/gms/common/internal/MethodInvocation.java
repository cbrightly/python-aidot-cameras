package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(creator = "MethodInvocationCreator")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class MethodInvocation extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<MethodInvocation> CREATOR = new zan();
    @SafeParcelable.Field(getter = "getMethodKey", id = 1)
    private final int zaa;
    @SafeParcelable.Field(getter = "getResultStatusCode", id = 2)
    private final int zab;
    @SafeParcelable.Field(getter = "getConnectionResultStatusCode", id = 3)
    private final int zac;
    @SafeParcelable.Field(getter = "getStartTimeMillis", id = 4)
    private final long zad;
    @SafeParcelable.Field(getter = "getEndTimeMillis", id = 5)
    private final long zae;
    @SafeParcelable.Field(getter = "getCallingModuleId", id = 6)
    @Nullable
    private final String zaf;
    @SafeParcelable.Field(getter = "getCallingEntryPoint", id = 7)
    @Nullable
    private final String zag;
    @SafeParcelable.Field(defaultValue = "0", getter = "getServiceId", id = 8)
    private final int zah;
    @SafeParcelable.Field(defaultValue = "-1", getter = "getLatencyMillis", id = 9)
    private final int zai;

    @KeepForSdk
    @Deprecated
    public MethodInvocation(int methodKey, int resultStatusCode, int connectionResultStatusCode, long startTimeMillis, long endTimeMillis, @Nullable String callingModuleId, @Nullable String callingEntryPoint, int serviceId) {
        this(methodKey, resultStatusCode, connectionResultStatusCode, startTimeMillis, endTimeMillis, callingModuleId, callingEntryPoint, serviceId, -1);
    }

    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeInt(parcel, 2, this.zab);
        SafeParcelWriter.writeInt(parcel, 3, this.zac);
        SafeParcelWriter.writeLong(parcel, 4, this.zad);
        SafeParcelWriter.writeLong(parcel, 5, this.zae);
        SafeParcelWriter.writeString(parcel, 6, this.zaf, false);
        SafeParcelWriter.writeString(parcel, 7, this.zag, false);
        SafeParcelWriter.writeInt(parcel, 8, this.zah);
        SafeParcelWriter.writeInt(parcel, 9, this.zai);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @SafeParcelable.Constructor
    public MethodInvocation(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) int i3, @SafeParcelable.Param(id = 4) long j, @SafeParcelable.Param(id = 5) long j2, @SafeParcelable.Param(id = 6) @Nullable String str, @SafeParcelable.Param(id = 7) @Nullable String str2, @SafeParcelable.Param(id = 8) int i4, @SafeParcelable.Param(id = 9) int i5) {
        this.zaa = i;
        this.zab = i2;
        this.zac = i3;
        this.zad = j;
        this.zae = j2;
        this.zaf = str;
        this.zag = str2;
        this.zah = i4;
        this.zai = i5;
    }
}
