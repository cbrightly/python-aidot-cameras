package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.internal.base.zaa;
import com.google.android.gms.internal.base.zac;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaf extends zaa implements IInterface {
    zaf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zae(int i) {
        Parcel zaa = zaa();
        zaa.writeInt(i);
        zac(7, zaa);
    }

    public final void zaf(IAccountAccessor iAccountAccessor, int i, boolean z) {
        Parcel zaa = zaa();
        zac.zae(zaa, iAccountAccessor);
        zaa.writeInt(i);
        zac.zac(zaa, z);
        zac(9, zaa);
    }

    public final void zag(zai zai, zae zae) {
        Parcel zaa = zaa();
        zac.zad(zaa, zai);
        zac.zae(zaa, zae);
        zac(12, zaa);
    }
}
