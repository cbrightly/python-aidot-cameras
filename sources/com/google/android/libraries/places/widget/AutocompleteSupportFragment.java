package com.google.android.libraries.places.widget;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zzhh;
import com.google.android.libraries.places.internal.zzhi;
import com.google.android.libraries.places.internal.zzhj;
import com.google.android.libraries.places.internal.zzhm;
import com.google.android.libraries.places.internal.zzjq;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class AutocompleteSupportFragment extends Fragment {
    private final MutableLiveData zza = new MutableLiveData();
    private final MutableLiveData zzb = new MutableLiveData();
    private zzhi zzc = zzhj.zzn(AutocompleteActivityMode.OVERLAY, zzjq.zzl(), zzhh.FRAGMENT);
    @Nullable
    private PlaceSelectionListener zzd;

    public AutocompleteSupportFragment() {
        super(R.layout.places_autocomplete_fragment);
    }

    @RecentlyNonNull
    public static AutocompleteSupportFragment newInstance() {
        return new AutocompleteSupportFragment();
    }

    private final void zze() {
        Intent build = new Autocomplete.IntentBuilder(this.zzc.zzm()).build(requireContext());
        if (requireView().isEnabled()) {
            requireView().setEnabled(false);
            startActivityForResult(build, 30421);
        }
    }

    private final void zzf(View view) {
        int i;
        if (true != TextUtils.isEmpty((CharSequence) this.zza.getValue())) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 30421) {
            try {
                PlaceSelectionListener placeSelectionListener = this.zzd;
                if (placeSelectionListener == null) {
                    if (Log.isLoggable("Places", 5)) {
                        Log.w("Places", "No PlaceSelectionListener is set. No result will be delivered.");
                    }
                } else if (data == null) {
                    if (Log.isLoggable("Places", 6)) {
                        Log.e("Places", "Intent data was null.");
                    }
                } else if (resultCode == -1) {
                    Place placeFromIntent = Autocomplete.getPlaceFromIntent(data);
                    placeSelectionListener.onPlaceSelected(placeFromIntent);
                    setText(placeFromIntent.getName());
                } else {
                    placeSelectionListener.onError(Autocomplete.getStatusFromIntent(data));
                }
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            try {
                zzhj zzhj = (zzhj) savedInstanceState.getParcelable("options");
                if (zzhj != null) {
                    if (this.zza.getValue() == null) {
                        this.zza.postValue(zzhj.zzm());
                    }
                    if (this.zzb.getValue() == null) {
                        this.zzb.postValue(zzhj.zzl());
                    }
                    this.zzc = zzhj.zzg();
                }
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }

    public void onResume() {
        super.onResume();
        requireView().setEnabled(true);
    }

    public void onSaveInstanceState(@RecentlyNonNull Bundle bundle) {
        bundle.putParcelable("options", this.zzc.zzm());
    }

    public void onViewCreated(@RecentlyNonNull View view, @Nullable Bundle bundle) {
        View findViewById = view.findViewById(R.id.places_autocomplete_search_button);
        View findViewById2 = view.findViewById(R.id.places_autocomplete_clear_button);
        EditText editText = (EditText) view.findViewById(R.id.places_autocomplete_search_input);
        editText.setHint(zzhm.zzc(requireContext(), R.string.places_autocomplete_search_hint));
        findViewById.setOnClickListener(new zze(this));
        editText.setOnClickListener(new zzf(this));
        findViewById2.setOnClickListener(new zzg(this));
        zzf(findViewById2);
        this.zza.observe(getViewLifecycleOwner(), new zzh(this, editText, findViewById2));
        this.zzb.observe(getViewLifecycleOwner(), new zzi(editText, findViewById));
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setActivityMode(@RecentlyNonNull AutocompleteActivityMode mode) {
        this.zzc.zzf(mode);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setCountries(@RecentlyNonNull List<String> countries) {
        this.zzc.zza(countries);
        return this;
    }

    @RecentlyNonNull
    @Deprecated
    public AutocompleteSupportFragment setCountry(@Nullable String country) {
        this.zzc.zzn(country);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setHint(@Nullable CharSequence hint) {
        if (hint == null) {
            try {
                String string = getString(R.string.places_autocomplete_search_hint);
                this.zzc.zzb(string);
                this.zzb.postValue(string);
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        } else {
            this.zzc.zzb(hint.toString());
            this.zzb.postValue(hint);
        }
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setLocationBias(@Nullable LocationBias locationBias) {
        this.zzc.zzd(locationBias);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setLocationRestriction(@Nullable LocationRestriction locationRestriction) {
        this.zzc.zze(locationRestriction);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setOnPlaceSelectedListener(@Nullable PlaceSelectionListener placeSelectionListener) {
        this.zzd = placeSelectionListener;
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setPlaceFields(@RecentlyNonNull List<Place.Field> placeFields) {
        this.zzc.zzh(placeFields);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setText(@Nullable CharSequence text) {
        try {
            this.zzc.zzc(TextUtils.isEmpty(text) ? null : text.toString());
            this.zza.postValue(text);
            return this;
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @RecentlyNonNull
    @Deprecated
    public AutocompleteSupportFragment setTypeFilter(@Nullable TypeFilter typeFilter) {
        this.zzc.zzk(typeFilter);
        return this;
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setTypesFilter(@RecentlyNonNull List<String> typesFilter) {
        this.zzc.zzl(typesFilter);
        return this;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(View view) {
        zze();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(View view) {
        zze();
    }

    @RecentlyNonNull
    public AutocompleteSupportFragment setCountries(@RecentlyNonNull String... countries) {
        this.zzc.zza(zzjq.zzk(countries));
        return this;
    }

    static /* synthetic */ void zzd(EditText editText, View view, CharSequence charSequence) {
        try {
            editText.setHint(charSequence);
            view.setContentDescription(charSequence);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzc(EditText editText, View view, CharSequence charSequence) {
        try {
            editText.setText(charSequence);
            zzf(view);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
