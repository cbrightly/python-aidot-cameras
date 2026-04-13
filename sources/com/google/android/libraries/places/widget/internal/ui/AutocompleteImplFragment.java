package com.google.android.libraries.places.widget.internal.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.internal.zzcn;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zzhj;
import com.google.android.libraries.places.internal.zzhl;
import com.google.android.libraries.places.internal.zzhm;
import com.google.android.libraries.places.internal.zzhn;
import com.google.android.libraries.places.internal.zzhx;
import com.google.android.libraries.places.internal.zzib;
import com.google.android.libraries.places.internal.zzid;
import com.google.android.libraries.places.internal.zzig;
import com.google.android.libraries.places.internal.zzih;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.List;

@SuppressLint({"ValidFragment"})
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class AutocompleteImplFragment extends Fragment {
    private final PlacesClient zza;
    private final zzhj zzb;
    private final zzih zzc;
    private final zzcn zzd;
    /* access modifiers changed from: private */
    public zzid zze;
    private PlaceSelectionListener zzf;
    /* access modifiers changed from: private */
    public EditText zzg;
    private RecyclerView zzh;
    private View zzi;
    private View zzj;
    private View zzk;
    private View zzl;
    private View zzm;
    private View zzn;
    private View zzo;
    private View zzp;
    private TextView zzq;
    private TextView zzr;
    private zzr zzs;
    private final zzj zzt;

    private AutocompleteImplFragment(@LayoutRes int layoutId, PlacesClient client, zzhj options, zzih logger, zzcn clock) {
        super(layoutId);
        this.zzt = new zzj(this, (zzi) null);
        this.zza = client;
        this.zzb = options;
        this.zzc = logger;
        this.zzd = clock;
    }

    public final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        try {
            zzig zzig = new zzig(this.zzb.zzf(), this.zzb.zzh(), this.zzb.zzm(), this.zzd);
            zzid zzid = (zzid) new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new zzib(new zzhx(this.zza, this.zzb, zzig.zzh()), zzig, this.zzc)).get(zzid.class);
            this.zze = zzid;
            zzid.zze(bundle);
            requireActivity().getOnBackPressedDispatcher().addCallback(this, new zzf(this, true));
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final void onPause() {
        super.onPause();
        this.zze.zzi();
    }

    public final void onResume() {
        super.onResume();
        this.zze.zzh();
    }

    public final void onViewCreated(@RecentlyNonNull View view, @Nullable Bundle bundle) {
        String str;
        try {
            this.zzg = (EditText) view.findViewById(R.id.places_autocomplete_search_bar);
            this.zzh = (RecyclerView) view.findViewById(R.id.places_autocomplete_list);
            this.zzi = view.findViewById(R.id.places_autocomplete_back_button);
            this.zzj = view.findViewById(R.id.places_autocomplete_clear_button);
            this.zzk = view.findViewById(R.id.places_autocomplete_search_bar_separator);
            this.zzl = view.findViewById(R.id.places_autocomplete_progress);
            this.zzm = view.findViewById(R.id.places_autocomplete_try_again_progress);
            this.zzn = view.findViewById(R.id.places_autocomplete_powered_by_google);
            this.zzo = view.findViewById(R.id.places_autocomplete_powered_by_google_separator);
            this.zzp = view.findViewById(R.id.places_autocomplete_sad_cloud);
            this.zzq = (TextView) view.findViewById(R.id.places_autocomplete_error_message);
            this.zzr = (TextView) view.findViewById(R.id.places_autocomplete_try_again);
            this.zzg.addTextChangedListener(this.zzt);
            this.zzg.setOnFocusChangeListener(new zzl((zzk) null));
            EditText editText = this.zzg;
            if (TextUtils.isEmpty(this.zzb.zzl())) {
                str = zzhm.zzc(requireContext(), R.string.places_autocomplete_search_hint);
            } else {
                str = this.zzb.zzl();
            }
            editText.setHint(str);
            AutocompleteActivityMode autocompleteActivityMode = AutocompleteActivityMode.FULLSCREEN;
            switch (this.zzb.zzh().ordinal()) {
                case 0:
                    int zza2 = this.zzb.zza();
                    int zzb2 = this.zzb.zzb();
                    if (Color.alpha(zza2) < 255) {
                        zza2 = 0;
                    }
                    if (!(zza2 == 0 || zzb2 == 0)) {
                        int zza3 = zzhn.zza(zza2, ContextCompat.getColor(requireContext(), R.color.places_text_white_alpha_87), ContextCompat.getColor(requireContext(), R.color.places_text_black_alpha_87));
                        int zza4 = zzhn.zza(zza2, ContextCompat.getColor(requireContext(), R.color.places_text_white_alpha_26), ContextCompat.getColor(requireContext(), R.color.places_text_black_alpha_26));
                        view.findViewById(R.id.places_autocomplete_search_bar_container).setBackgroundColor(zza2);
                        Window window = requireActivity().getWindow();
                        if (!zzhn.zzc(zzb2, -1, ViewCompat.MEASURED_STATE_MASK)) {
                            window.setStatusBarColor(zzb2);
                        } else if (Build.VERSION.SDK_INT >= 23) {
                            window.setStatusBarColor(zzb2);
                            window.getDecorView().setSystemUiVisibility(8192);
                        }
                        this.zzg.setTextColor(zza3);
                        this.zzg.setHintTextColor(zza4);
                        zzhn.zzb((ImageView) this.zzi, zza3);
                        zzhn.zzb((ImageView) this.zzj, zza3);
                        break;
                    }
                case 1:
                    int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (identifier > 0) {
                        requireActivity().getWindow().addFlags(67108864);
                        ViewCompat.setPaddingRelative(view, view.getPaddingLeft(), view.getPaddingTop() + getResources().getDimensionPixelSize(identifier), view.getPaddingRight(), view.getPaddingBottom());
                        break;
                    }
                    break;
            }
            this.zzi.setOnClickListener(new zzd(this));
            this.zzj.setOnClickListener(new zzc(this));
            this.zzr.setOnClickListener(new zza(this));
            this.zzs = new zzr(new zzb(this));
            this.zzh.setLayoutManager(new LinearLayoutManager(requireContext()));
            this.zzh.setItemAnimator(new zzo(getResources()));
            this.zzh.setAdapter(this.zzs);
            this.zzh.addOnScrollListener(new zzg(this));
            this.zze.zza().observe(getViewLifecycleOwner(), new zze(this));
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzc(View view) {
        this.zze.zzj();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzg(zzhl zzhl) {
        try {
            this.zzj.setVisibility(0);
            this.zzk.setVisibility(0);
            this.zzl.setVisibility(8);
            this.zzm.setVisibility(8);
            this.zzn.setVisibility(0);
            this.zzo.setVisibility(8);
            this.zzp.setVisibility(8);
            this.zzq.setVisibility(8);
            this.zzr.setVisibility(8);
            AutocompleteActivityMode autocompleteActivityMode = AutocompleteActivityMode.FULLSCREEN;
            switch (zzhl.zzf() - 1) {
                case 0:
                    if (TextUtils.isEmpty(this.zzb.zzm())) {
                        this.zzj.setVisibility(8);
                    }
                    this.zzg.requestFocus();
                    this.zzg.setText(this.zzb.zzm());
                    EditText editText = this.zzg;
                    editText.setSelection(editText.getText().length());
                    return;
                case 1:
                    this.zzs.submitList((List) null);
                    this.zzj.setVisibility(8);
                    this.zzg.getText().clear();
                    return;
                case 2:
                    this.zzl.setVisibility(0);
                    return;
                case 3:
                    this.zzr.setVisibility(8);
                    this.zzm.setVisibility(0);
                    this.zzn.setVisibility(8);
                    this.zzp.setVisibility(0);
                    this.zzq.setVisibility(0);
                    return;
                case 4:
                    this.zzs.submitList(zzhl.zzd());
                    this.zzo.setVisibility(0);
                    return;
                case 5:
                    this.zzs.submitList((List) null);
                    this.zzn.setVisibility(8);
                    this.zzp.setVisibility(0);
                    this.zzr.setVisibility(4);
                    this.zzq.setText(getString(R.string.places_autocomplete_no_results_for_query, zzhl.zze()));
                    this.zzq.setVisibility(0);
                    return;
                case 6:
                    break;
                case 8:
                    AutocompletePrediction zzb2 = zzhl.zzb();
                    zziy.zzc(zzb2, "Prediction should not be null.");
                    this.zzg.clearFocus();
                    this.zzg.removeTextChangedListener(this.zzt);
                    this.zzg.setText(zzb2.getPrimaryText((CharacterStyle) null));
                    this.zzg.addTextChangedListener(this.zzt);
                    break;
                case 9:
                    PlaceSelectionListener placeSelectionListener = this.zzf;
                    Status zza2 = zzhl.zza();
                    if (zza2 != null) {
                        placeSelectionListener.onError(zza2);
                        return;
                    }
                    throw null;
                default:
                    PlaceSelectionListener placeSelectionListener2 = this.zzf;
                    Place zzc2 = zzhl.zzc();
                    if (zzc2 != null) {
                        placeSelectionListener2.onPlaceSelected(zzc2);
                        return;
                    }
                    throw null;
            }
            this.zzs.submitList((List) null);
            this.zzn.setVisibility(8);
            this.zzp.setVisibility(0);
            this.zzr.setVisibility(0);
            this.zzq.setText(getString(R.string.places_search_error));
            this.zzq.setVisibility(0);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final void zzh(@RecentlyNonNull PlaceSelectionListener placeSelectionListener) {
        this.zzf = placeSelectionListener;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzd(View view) {
        try {
            this.zze.zzk();
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zze(AutocompletePrediction autocompletePrediction, int i) {
        try {
            this.zze.zzf(autocompletePrediction, i);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzf(View view) {
        try {
            this.zze.zzl(this.zzg.getText().toString());
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
