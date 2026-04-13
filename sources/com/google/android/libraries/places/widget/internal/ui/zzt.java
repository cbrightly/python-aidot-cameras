package com.google.android.libraries.places.widget.internal.ui;

import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.internal.zzgt;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzt extends RecyclerView.ViewHolder {
    private final TextView zza;
    private final TextView zzb;
    private AutocompletePrediction zzc;
    private boolean zzd;

    public zzt(zzb zzb2, View view) {
        super(view);
        this.zza = (TextView) view.findViewById(R.id.places_autocomplete_prediction_primary_text);
        this.zzb = (TextView) view.findViewById(R.id.places_autocomplete_prediction_secondary_text);
        this.itemView.setOnClickListener(new zzs(this, zzb2));
    }

    public final void zza(AutocompletePrediction autocompletePrediction, boolean z) {
        this.zzc = autocompletePrediction;
        this.zzd = z;
        this.zza.setText(autocompletePrediction.getPrimaryText(new ForegroundColorSpan(ContextCompat.getColor(this.itemView.getContext(), R.color.places_autocomplete_prediction_primary_text_highlight))));
        SpannableString secondaryText = autocompletePrediction.getSecondaryText((CharacterStyle) null);
        this.zzb.setText(secondaryText);
        if (secondaryText.length() == 0) {
            this.zzb.setVisibility(8);
            this.zza.setGravity(16);
            return;
        }
        this.zzb.setVisibility(0);
        this.zza.setGravity(80);
    }

    public final boolean zzb() {
        return this.zzd;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzc(zzb zzb2, View view) {
        AutocompletePrediction autocompletePrediction = this.zzc;
        if (autocompletePrediction != null) {
            try {
                zzb2.zza.zze(autocompletePrediction, getAdapterPosition());
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }
}
