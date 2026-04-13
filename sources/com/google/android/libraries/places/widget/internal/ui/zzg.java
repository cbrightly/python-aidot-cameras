package com.google.android.libraries.places.widget.internal.ui;

import androidx.recyclerview.widget.RecyclerView;
import com.google.android.libraries.places.internal.zzgt;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzg extends RecyclerView.OnScrollListener {
    final /* synthetic */ AutocompleteImplFragment zza;

    zzg(AutocompleteImplFragment autocompleteImplFragment) {
        this.zza = autocompleteImplFragment;
    }

    public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
        if (i == 1) {
            try {
                this.zza.zze.zzg();
                this.zza.zzg.clearFocus();
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }
}
