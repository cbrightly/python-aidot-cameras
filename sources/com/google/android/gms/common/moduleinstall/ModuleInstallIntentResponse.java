package com.google.android.gms.common.moduleinstall;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ModuleInstallIntentResponseCreator")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class ModuleInstallIntentResponse extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<ModuleInstallIntentResponse> CREATOR = new zab();
    @SafeParcelable.Field(getter = "getPendingIntent", id = 1)
    @Nullable
    private final PendingIntent zaa;

    @SafeParcelable.Constructor
    @KeepForSdk
    public ModuleInstallIntentResponse(@SafeParcelable.Param(id = 1) @Nullable PendingIntent pendingIntent) {
        this.zaa = pendingIntent;
    }

    @Nullable
    public PendingIntent getPendingIntent() {
        return this.zaa;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getPendingIntent(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
