package com.google.android.gms.common.moduleinstall.internal;

import android.os.Parcel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.moduleinstall.ModuleAvailabilityResponse;
import com.google.android.gms.common.moduleinstall.ModuleInstallIntentResponse;
import com.google.android.gms.common.moduleinstall.ModuleInstallResponse;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zac;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class zad extends zab implements zae {
    public zad() {
        super("com.google.android.gms.common.moduleinstall.internal.IModuleInstallCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean zaa(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zac.zab(parcel);
                zae((Status) zac.zaa(parcel, Status.CREATOR), (ModuleAvailabilityResponse) zac.zaa(parcel, ModuleAvailabilityResponse.CREATOR));
                return true;
            case 2:
                zac.zab(parcel);
                zad((Status) zac.zaa(parcel, Status.CREATOR), (ModuleInstallResponse) zac.zaa(parcel, ModuleInstallResponse.CREATOR));
                return true;
            case 3:
                zac.zab(parcel);
                zac((Status) zac.zaa(parcel, Status.CREATOR), (ModuleInstallIntentResponse) zac.zaa(parcel, ModuleInstallIntentResponse.CREATOR));
                return true;
            case 4:
                zac.zab(parcel);
                zab((Status) zac.zaa(parcel, Status.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
