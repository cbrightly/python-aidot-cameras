package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.zzcb;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzae extends DeferredLifecycleHelper {
    protected OnDelegateCreatedListener zza;
    private final Fragment zzb;
    private Activity zzc;
    private final List zzd = new ArrayList();

    @VisibleForTesting
    zzae(Fragment fragment) {
        this.zzb = fragment;
    }

    static /* synthetic */ void zza(zzae zzae, Activity activity) {
        zzae.zzc = activity;
        zzae.zzc();
    }

    /* access modifiers changed from: protected */
    public final void createDelegate(OnDelegateCreatedListener onDelegateCreatedListener) {
        this.zza = onDelegateCreatedListener;
        zzc();
    }

    public final void zzb(OnMapReadyCallback onMapReadyCallback) {
        if (getDelegate() != null) {
            ((zzad) getDelegate()).getMapAsync(onMapReadyCallback);
        } else {
            this.zzd.add(onMapReadyCallback);
        }
    }

    public final void zzc() {
        if (this.zzc != null && this.zza != null && getDelegate() == null) {
            try {
                MapsInitializer.initialize(this.zzc);
                IMapFragmentDelegate zzf = zzcb.zza(this.zzc, (MapsInitializer.Renderer) null).zzf(ObjectWrapper.wrap(this.zzc));
                if (zzf != null) {
                    this.zza.onDelegateCreated(new zzad(this.zzb, zzf));
                    for (OnMapReadyCallback mapAsync : this.zzd) {
                        ((zzad) getDelegate()).getMapAsync(mapAsync);
                    }
                    this.zzd.clear();
                }
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            } catch (GooglePlayServicesNotAvailableException e2) {
            }
        }
    }
}
