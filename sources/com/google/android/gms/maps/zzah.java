package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.zzcb;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzah extends DeferredLifecycleHelper {
    protected OnDelegateCreatedListener zza;
    private final ViewGroup zzb;
    private final Context zzc;
    @Nullable
    private final GoogleMapOptions zzd;
    private final List zze = new ArrayList();

    @VisibleForTesting
    zzah(ViewGroup viewGroup, Context context, @Nullable GoogleMapOptions googleMapOptions) {
        this.zzb = viewGroup;
        this.zzc = context;
        this.zzd = googleMapOptions;
    }

    /* access modifiers changed from: protected */
    public final void createDelegate(OnDelegateCreatedListener onDelegateCreatedListener) {
        this.zza = onDelegateCreatedListener;
        zzb();
    }

    public final void zza(OnMapReadyCallback onMapReadyCallback) {
        if (getDelegate() != null) {
            ((zzag) getDelegate()).getMapAsync(onMapReadyCallback);
        } else {
            this.zze.add(onMapReadyCallback);
        }
    }

    public final void zzb() {
        if (this.zza != null && getDelegate() == null) {
            try {
                MapsInitializer.initialize(this.zzc);
                IMapViewDelegate zzg = zzcb.zza(this.zzc, (MapsInitializer.Renderer) null).zzg(ObjectWrapper.wrap(this.zzc), this.zzd);
                if (zzg != null) {
                    this.zza.onDelegateCreated(new zzag(this.zzb, zzg));
                    for (OnMapReadyCallback mapAsync : this.zze) {
                        ((zzag) getDelegate()).getMapAsync(mapAsync);
                    }
                    this.zze.clear();
                }
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            } catch (GooglePlayServicesNotAvailableException e2) {
            }
        }
    }
}
