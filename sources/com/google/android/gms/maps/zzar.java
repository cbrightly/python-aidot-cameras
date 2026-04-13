package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzca;
import com.google.android.gms.maps.model.RuntimeRemoteException;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzar implements StreetViewLifecycleDelegate {
    private final ViewGroup zza;
    private final IStreetViewPanoramaViewDelegate zzb;
    private View zzc;

    public zzar(ViewGroup viewGroup, IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate) {
        this.zzb = (IStreetViewPanoramaViewDelegate) Preconditions.checkNotNull(iStreetViewPanoramaViewDelegate);
        this.zza = (ViewGroup) Preconditions.checkNotNull(viewGroup);
    }

    public final View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
    }

    public final void onDestroyView() {
        throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
    }

    public final void onInflate(Activity activity, Bundle bundle, @Nullable Bundle bundle2) {
        throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
    }

    public final void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        try {
            this.zzb.getStreetViewPanoramaAsync(new zzaq(this, onStreetViewPanoramaReadyCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onDestroy() {
        try {
            this.zzb.onDestroy();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onLowMemory() {
        try {
            this.zzb.onLowMemory();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onPause() {
        try {
            this.zzb.onPause();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onResume() {
        try {
            this.zzb.onResume();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onStart() {
        try {
            this.zzb.onStart();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onStop() {
        try {
            this.zzb.onStop();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            this.zzb.onSaveInstanceState(bundle2);
            zzca.zzb(bundle2, bundle);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onCreate(@Nullable Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            this.zzb.onCreate(bundle2);
            zzca.zzb(bundle2, bundle);
            this.zzc = (View) ObjectWrapper.unwrap(this.zzb.getView());
            this.zza.removeAllViews();
            this.zza.addView(this.zzc);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
