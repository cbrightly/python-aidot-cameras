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
import com.google.android.gms.maps.internal.zzcb;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzas extends DeferredLifecycleHelper {
    protected OnDelegateCreatedListener zza;
    private final ViewGroup zzb;
    private final Context zzc;
    @Nullable
    private final StreetViewPanoramaOptions zzd;
    private final List zze = new ArrayList();

    @VisibleForTesting
    zzas(ViewGroup viewGroup, Context context, @Nullable StreetViewPanoramaOptions streetViewPanoramaOptions) {
        this.zzb = viewGroup;
        this.zzc = context;
        this.zzd = streetViewPanoramaOptions;
    }

    /* access modifiers changed from: protected */
    public final void createDelegate(OnDelegateCreatedListener onDelegateCreatedListener) {
        this.zza = onDelegateCreatedListener;
        zzb();
    }

    public final void zza(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        if (getDelegate() != null) {
            ((zzar) getDelegate()).getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
        } else {
            this.zze.add(onStreetViewPanoramaReadyCallback);
        }
    }

    public final void zzb() {
        if (this.zza != null && getDelegate() == null) {
            try {
                MapsInitializer.initialize(this.zzc);
                this.zza.onDelegateCreated(new zzar(this.zzb, zzcb.zza(this.zzc, (MapsInitializer.Renderer) null).zzi(ObjectWrapper.wrap(this.zzc), this.zzd)));
                for (OnStreetViewPanoramaReadyCallback streetViewPanoramaAsync : this.zze) {
                    ((zzar) getDelegate()).getStreetViewPanoramaAsync(streetViewPanoramaAsync);
                }
                this.zze.clear();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            } catch (GooglePlayServicesNotAvailableException e2) {
            }
        }
    }
}
