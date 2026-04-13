package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzca;
import com.google.android.gms.maps.model.RuntimeRemoteException;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzan implements StreetViewLifecycleDelegate {
    private final Fragment zza;
    private final IStreetViewPanoramaFragmentDelegate zzb;

    public zzan(Fragment fragment, IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate) {
        this.zzb = (IStreetViewPanoramaFragmentDelegate) Preconditions.checkNotNull(iStreetViewPanoramaFragmentDelegate);
        this.zza = (Fragment) Preconditions.checkNotNull(fragment);
    }

    public final void onCreate(@Nullable Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            Bundle arguments = this.zza.getArguments();
            if (arguments != null && arguments.containsKey("StreetViewPanoramaOptions")) {
                zzca.zzc(bundle2, "StreetViewPanoramaOptions", arguments.getParcelable("StreetViewPanoramaOptions"));
            }
            this.zzb.onCreate(bundle2);
            zzca.zzb(bundle2, bundle);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            IObjectWrapper onCreateView = this.zzb.onCreateView(ObjectWrapper.wrap(layoutInflater), ObjectWrapper.wrap(viewGroup), bundle2);
            zzca.zzb(bundle2, bundle);
            return (View) ObjectWrapper.unwrap(onCreateView);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        try {
            this.zzb.getStreetViewPanoramaAsync(new zzam(this, onStreetViewPanoramaReadyCallback));
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

    public final void onDestroyView() {
        try {
            this.zzb.onDestroyView();
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

    public final void onInflate(Activity activity, Bundle bundle, @Nullable Bundle bundle2) {
        try {
            Bundle bundle3 = new Bundle();
            zzca.zzb(bundle2, bundle3);
            this.zzb.onInflate(ObjectWrapper.wrap(activity), (StreetViewPanoramaOptions) null, bundle3);
            zzca.zzb(bundle3, bundle2);
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
}
