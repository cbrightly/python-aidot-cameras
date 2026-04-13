package com.google.android.libraries.places.internal;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzib implements ViewModelProvider.Factory {
    private final zzhq zza;
    private final zzig zzb;
    private final zzih zzc;

    public zzib(zzhq zzhq, zzig zzig, zzih zzih) {
        this.zza = zzhq;
        this.zzb = zzig;
        this.zzc = zzih;
    }

    public final ViewModel create(Class cls) {
        boolean z;
        if (cls == zzid.class) {
            z = true;
        } else {
            z = false;
        }
        zziy.zze(z, "This factory can only be used to instantiate its enclosing class.");
        return new zzid(this.zza, this.zzb, this.zzc, (zzic) null);
    }

    public final ViewModel create(Class cls, CreationExtras creationExtras) {
        return create(cls);
    }
}
