package com.google.android.gms.maps;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;

@TargetApi(11)
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanoramaFragment extends Fragment {
    private final zzao zza = new zzao(this);

    @NonNull
    public static StreetViewPanoramaFragment newInstance() {
        return new StreetViewPanoramaFragment();
    }

    public void getStreetViewPanoramaAsync(@NonNull OnStreetViewPanoramaReadyCallback callback) {
        Preconditions.checkMainThread("getStreetViewPanoramaAsync() must be called on the main thread");
        Preconditions.checkNotNull(callback, "callback must not be null.");
        this.zza.zzb(callback);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ClassLoader classLoader = StreetViewPanoramaFragment.class.getClassLoader();
        if (!(savedInstanceState == null || classLoader == null)) {
            savedInstanceState.setClassLoader(classLoader);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        zzao.zza(this.zza, activity);
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

    @SuppressLint({"NewApi"})
    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
        try {
            super.onInflate(activity, attrs, savedInstanceState);
            zzao.zza(this.zza, activity);
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
        ClassLoader classLoader = StreetViewPanoramaFragment.class.getClassLoader();
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
    public static StreetViewPanoramaFragment newInstance(@Nullable StreetViewPanoramaOptions options) {
        StreetViewPanoramaFragment streetViewPanoramaFragment = new StreetViewPanoramaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("StreetViewPanoramaOptions", options);
        streetViewPanoramaFragment.setArguments(bundle);
        return streetViewPanoramaFragment;
    }
}
