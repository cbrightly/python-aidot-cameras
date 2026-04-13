package com.google.android.gms.common.api.internal;

import androidx.annotation.BinderThread;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zak;
import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaar extends zac {
    private final WeakReference zaa;

    zaar(zaaw zaaw) {
        this.zaa = new WeakReference(zaaw);
    }

    @BinderThread
    public final void zab(zak zak) {
        zaaw zaaw = (zaaw) this.zaa.get();
        if (zaaw != null) {
            zaaw.zaa.zal(new zaaq(this, zaaw, zaaw, zak));
        }
    }
}
