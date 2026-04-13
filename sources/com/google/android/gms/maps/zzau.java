package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzca;
import com.google.android.gms.maps.model.RuntimeRemoteException;

@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzau implements MapLifecycleDelegate {
    private final Fragment zza;
    private final IMapFragmentDelegate zzb;

    public zzau(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
        this.zzb = (IMapFragmentDelegate) Preconditions.checkNotNull(iMapFragmentDelegate);
        this.zza = (Fragment) Preconditions.checkNotNull(fragment);
    }

    public final void onCreate(@Nullable Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            Bundle arguments = this.zza.getArguments();
            if (arguments != null && arguments.containsKey("MapOptions")) {
                zzca.zzc(bundle2, "MapOptions", arguments.getParcelable("MapOptions"));
            }
            this.zzb.onCreate(bundle2);
            zzca.zzb(bundle2, bundle);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        StrictMode.ThreadPolicy threadPolicy;
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            threadPolicy = StrictMode.getThreadPolicy();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
            IObjectWrapper onCreateView = this.zzb.onCreateView(ObjectWrapper.wrap(layoutInflater), ObjectWrapper.wrap(viewGroup), bundle2);
            StrictMode.setThreadPolicy(threadPolicy);
            zzca.zzb(bundle2, bundle);
            return (View) ObjectWrapper.unwrap(onCreateView);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicy);
            throw th;
        }
    }

    public final void onInflate(Activity activity, Bundle bundle, @Nullable Bundle bundle2) {
        GoogleMapOptions googleMapOptions = (GoogleMapOptions) bundle.getParcelable("MapOptions");
        try {
            Bundle bundle3 = new Bundle();
            zzca.zzb(bundle2, bundle3);
            this.zzb.onInflate(ObjectWrapper.wrap(activity), googleMapOptions, bundle3);
            zzca.zzb(bundle3, bundle2);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        try {
            this.zzb.getMapAsync(new zzat(this, onMapReadyCallback));
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

    public final void zzb() {
        try {
            this.zzb.onExitAmbient();
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

    public final void zza(@Nullable Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            zzca.zzb(bundle, bundle2);
            this.zzb.onEnterAmbient(bundle2);
            zzca.zzb(bundle2, bundle);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
