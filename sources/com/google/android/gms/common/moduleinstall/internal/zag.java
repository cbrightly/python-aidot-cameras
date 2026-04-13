package com.google.android.gms.common.moduleinstall.internal;

import android.os.Parcel;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zac;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class zag extends zab implements zah {
    public zag() {
        super("com.google.android.gms.common.moduleinstall.internal.IModuleInstallStatusListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zaa(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zac.zab(parcel);
        zab((ModuleInstallStatusUpdate) zac.zaa(parcel, ModuleInstallStatusUpdate.CREATOR));
        return true;
    }
}
