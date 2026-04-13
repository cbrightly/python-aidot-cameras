package com.google.android.gms.common.moduleinstall.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.internal.base.zaa;
import com.google.android.gms.internal.base.zac;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaf extends zaa implements IInterface {
    zaf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.moduleinstall.internal.IModuleInstallService");
    }

    public final void zae(zae zae, ApiFeatureRequest apiFeatureRequest) {
        Parcel zaa = zaa();
        zac.zae(zaa, zae);
        zac.zad(zaa, apiFeatureRequest);
        zac(1, zaa);
    }

    public final void zaf(zae zae, ApiFeatureRequest apiFeatureRequest) {
        Parcel zaa = zaa();
        zac.zae(zaa, zae);
        zac.zad(zaa, apiFeatureRequest);
        zac(3, zaa);
    }

    public final void zag(zae zae, ApiFeatureRequest apiFeatureRequest, zah zah) {
        Parcel zaa = zaa();
        zac.zae(zaa, zae);
        zac.zad(zaa, apiFeatureRequest);
        zac.zae(zaa, zah);
        zac(2, zaa);
    }

    public final void zah(IStatusCallback iStatusCallback, ApiFeatureRequest apiFeatureRequest) {
        Parcel zaa = zaa();
        zac.zae(zaa, iStatusCallback);
        zac.zad(zaa, apiFeatureRequest);
        zac(4, zaa);
    }

    public final void zai(IStatusCallback iStatusCallback, zah zah) {
        Parcel zaa = zaa();
        zac.zae(zaa, iStatusCallback);
        zac.zae(zaa, zah);
        zac(6, zaa);
    }
}
