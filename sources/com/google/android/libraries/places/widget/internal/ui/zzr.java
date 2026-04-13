package com.google.android.libraries.places.widget.internal.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.internal.zzgt;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzr extends ListAdapter {
    private int zza;
    private boolean zzb = true;
    private final zzb zzc;

    public zzr(zzb zzb2) {
        super(new zzq((zzp) null));
        this.zzc = zzb2;
    }

    public final void submitList(@Nullable List list) {
        boolean z;
        try {
            int i = 0;
            if (this.zza != 0) {
                z = false;
            } else if (list == null || list.isEmpty()) {
                z = false;
            } else {
                z = true;
            }
            this.zzb = z;
            if (list != null) {
                i = list.size();
            }
            this.zza = i;
            super.submitList(list);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* renamed from: zza */
    public final zzt onCreateViewHolder(ViewGroup viewGroup, int i) {
        try {
            return new zzt(this.zzc, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.places_autocomplete_prediction, viewGroup, false));
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* renamed from: zzb */
    public final void onBindViewHolder(zzt zzt, int i) {
        try {
            zzt.zza((AutocompletePrediction) getItem(i), this.zzb);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
