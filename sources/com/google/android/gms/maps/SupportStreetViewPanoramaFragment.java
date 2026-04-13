package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class SupportStreetViewPanoramaFragment extends Fragment {
    private final zzay zza = new zzay(this);

    @NonNull
    public static SupportStreetViewPanoramaFragment newInstance() {
        return new SupportStreetViewPanoramaFragment();
    }

    public void getStreetViewPanoramaAsync(@NonNull OnStreetViewPanoramaReadyCallback callback) {
        Preconditions.checkMainThread("getStreetViewPanoramaAsync() must be called on the main thread");
        Preconditions.checkNotNull(callback, "callback must not be null.");
        this.zza.zzb(callback);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ClassLoader classLoader = SupportStreetViewPanoramaFragment.class.getClassLoader();
        if (!(savedInstanceState == null || classLoader == null)) {
            savedInstanceState.setClassLoader(classLoader);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        zzay.zza(this.zza, activity);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.zza.onCreate(savedInstanceState);
    }

    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.zza.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroy() {
        this.zza.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.zza.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
        try {
            super.onInflate(activity, attrs, savedInstanceState);
            zzay.zza(this.zza, activity);
            this.zza.onInflate(activity, new Bundle(), savedInstanceState);
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    public void onLowMemory() {
        this.zza.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.zza.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.zza.onResume();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        ClassLoader classLoader = SupportStreetViewPanoramaFragment.class.getClassLoader();
        if (!(outState == null || classLoader == null)) {
            outState.setClassLoader(classLoader);
        }
        super.onSaveInstanceState(outState);
        this.zza.onSaveInstanceState(outState);
    }

    public void onStart() {
        super.onStart();
        this.zza.onStart();
    }

    public void onStop() {
        this.zza.onStop();
        super.onStop();
    }

    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @NonNull
    public static SupportStreetViewPanoramaFragment newInstance(@Nullable StreetViewPanoramaOptions options) {
        SupportStreetViewPanoramaFragment supportStreetViewPanoramaFragment = new SupportStreetViewPanoramaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("StreetViewPanoramaOptions", options);
        supportStreetViewPanoramaFragment.setArguments(bundle);
        return supportStreetViewPanoramaFragment;
    }
}
