package com.google.android.libraries.places.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zzhj;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.widget.internal.ui.AutocompleteImplFragment;
import com.google.android.libraries.places.widget.internal.ui.zzh;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class AutocompleteActivity extends AppCompatActivity implements PlaceSelectionListener {
    public static final int RESULT_ERROR = 2;
    @LayoutRes
    private int zza;
    @StyleRes
    private int zzb;
    private boolean zzc = false;

    public AutocompleteActivity() {
        super(R.layout.places_autocomplete_activity);
    }

    private final void zzc(int i, @Nullable Place place, Status status) {
        try {
            Intent intent = new Intent();
            if (place != null) {
                intent.putExtra("places/selected_place", place);
            }
            intent.putExtra("places/status", status);
            setResult(i, intent);
            finish();
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @SuppressLint({"MissingSuperCall"})
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            zziy.zzk(Places.isInitialized(), "Places must be initialized.");
            boolean z = true;
            zziy.zzk(getCallingActivity() != null, "Cannot find caller. startActivityForResult should be used.");
            zzhj zzhj = (zzhj) getIntent().getParcelableExtra("places/AutocompleteOptions");
            if (zzhj != null) {
                AutocompleteActivityMode autocompleteActivityMode = AutocompleteActivityMode.FULLSCREEN;
                switch (zzhj.zzh().ordinal()) {
                    case 0:
                        this.zza = R.layout.places_autocomplete_impl_fragment_fullscreen;
                        this.zzb = R.style.PlacesAutocompleteFullscreen;
                        break;
                    case 1:
                        this.zza = R.layout.places_autocomplete_impl_fragment_overlay;
                        this.zzb = R.style.PlacesAutocompleteOverlay;
                        break;
                }
                getSupportFragmentManager().setFragmentFactory(new zzh(this.zza, this, zzhj));
                setTheme(this.zzb);
                super.onCreate(savedInstanceState);
                AutocompleteImplFragment autocompleteImplFragment = (AutocompleteImplFragment) getSupportFragmentManager().findFragmentById(R.id.places_autocomplete_content);
                if (autocompleteImplFragment == null) {
                    z = false;
                }
                zziy.zzj(z);
                autocompleteImplFragment.zzh(this);
                View findViewById = findViewById(16908290);
                findViewById.setOnTouchListener(new zza(this, autocompleteImplFragment, findViewById));
                findViewById.setOnClickListener(new zzb(this));
                if (zzhj.zzj().isEmpty()) {
                    zzc(2, (Place) null, new Status((int) PlacesStatusCodes.INVALID_REQUEST, "Place Fields must not be empty."));
                    return;
                }
                return;
            }
            throw null;
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public void onError(@RecentlyNonNull Status status) {
        zzc(true != status.isCanceled() ? 2 : 0, (Place) null, status);
    }

    public void onPlaceSelected(@RecentlyNonNull Place place) {
        zzc(-1, place, Status.RESULT_SUCCESS);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(View view) {
        if (this.zzc) {
            zzc(0, (Place) null, new Status(16));
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ boolean zzb(AutocompleteImplFragment autocompleteImplFragment, View view, View view2, MotionEvent motionEvent) {
        this.zzc = false;
        View view3 = autocompleteImplFragment.getView();
        if (view3 == null || motionEvent.getY() <= ((float) view3.getBottom())) {
            return false;
        }
        this.zzc = true;
        view.performClick();
        return true;
    }
}
