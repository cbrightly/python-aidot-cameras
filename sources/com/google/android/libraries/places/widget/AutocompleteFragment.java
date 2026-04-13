package com.google.android.libraries.places.widget;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zzhh;
import com.google.android.libraries.places.internal.zzhm;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzjq;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.List;

@Deprecated
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class AutocompleteFragment extends Fragment {
    private View zza;
    private View zzb;
    private EditText zzc;
    @Nullable
    private LocationBias zzd;
    @Nullable
    private LocationRestriction zze;
    @Nullable
    private String zzf;
    @Nullable
    private TypeFilter zzg;
    @Nullable
    private zzjq zzh;
    @Nullable
    private PlaceSelectionListener zzi;

    private final void zzb() {
        int i;
        if (true != this.zzc.getText().toString().isEmpty()) {
            i = 0;
        } else {
            i = 8;
        }
        this.zzb.setVisibility(i);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            getView().setEnabled(true);
            if (requestCode == 30421) {
                if (this.zzi == null) {
                    if (Log.isLoggable("Places", 5)) {
                        Log.w("Places", "No PlaceSelectionListener is set. No result will be delivered.");
                        requestCode = 30421;
                    } else {
                        requestCode = 30421;
                    }
                } else if (resultCode == -1) {
                    Place placeFromIntent = Autocomplete.getPlaceFromIntent(data);
                    this.zzi.onPlaceSelected(placeFromIntent);
                    setText(placeFromIntent.getName());
                    requestCode = 30421;
                } else {
                    if (resultCode == 2) {
                        this.zzi.onError(Autocomplete.getStatusFromIntent(data));
                        resultCode = 2;
                    }
                    requestCode = 30421;
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @RecentlyNonNull
    public View onCreateView(@RecentlyNonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        try {
            View inflate = inflater.inflate(R.layout.places_autocomplete_fragment, container, false);
            this.zza = inflate.findViewById(R.id.places_autocomplete_search_button);
            this.zzb = inflate.findViewById(R.id.places_autocomplete_clear_button);
            this.zzc = (EditText) inflate.findViewById(R.id.places_autocomplete_search_input);
            this.zzc.setHint(zzhm.zzc(inflater.getContext(), R.string.places_autocomplete_search_hint));
            zzc zzc2 = new zzc(this);
            this.zza.setOnClickListener(zzc2);
            this.zzc.setOnClickListener(zzc2);
            this.zzb.setOnClickListener(new zzd(this));
            zzb();
            inflate.setEnabled(false);
            return inflate;
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public void setCountry(@Nullable String str) {
        this.zzf = str;
    }

    public void setHint(@Nullable CharSequence hint) {
        if (hint == null) {
            try {
                hint = getString(R.string.places_autocomplete_search_hint);
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
        this.zzc.setHint(hint);
        this.zza.setContentDescription(hint);
    }

    public void setLocationBias(@Nullable LocationBias locationBias) {
        this.zzd = locationBias;
    }

    public void setLocationRestriction(@Nullable LocationRestriction locationRestriction) {
        this.zze = locationRestriction;
    }

    public void setOnPlaceSelectedListener(@Nullable PlaceSelectionListener placeSelectionListener) {
        this.zzi = placeSelectionListener;
    }

    public void setTypeFilter(@Nullable TypeFilter typeFilter) {
        this.zzg = typeFilter;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(View view) {
        if (!getView().isEnabled()) {
            zziy.zzc(this.zzh, "Place Fields must be set.");
            if (Log.isLoggable("Places", 6)) {
                Log.e("Places", "Autocomplete activity cannot be launched until fragment is enabled.");
                return;
            }
            return;
        }
        Autocomplete.IntentBuilder intentBuilder = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, this.zzh);
        intentBuilder.setInitialQuery(this.zzc.getText().toString());
        intentBuilder.setHint(this.zzc.getHint().toString());
        intentBuilder.setCountry(this.zzf);
        intentBuilder.setLocationBias(this.zzd);
        intentBuilder.setLocationRestriction(this.zze);
        intentBuilder.setTypeFilter(this.zzg);
        intentBuilder.zza(zzhh.FRAGMENT);
        Intent build = intentBuilder.build(getActivity());
        getView().setEnabled(false);
        startActivityForResult(build, 30421);
    }

    public void onDestroyView() {
        try {
            this.zza = null;
            this.zzb = null;
            this.zzc = null;
            super.onDestroyView();
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public void setText(@Nullable CharSequence text) {
        try {
            this.zzc.setText(text);
            zzb();
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public void setPlaceFields(@RecentlyNonNull List<Place.Field> placeFields) {
        try {
            zziy.zzc(placeFields, "Place Fields must not be null.");
            this.zzh = zzjq.zzj(placeFields);
            getView().setEnabled(true);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
