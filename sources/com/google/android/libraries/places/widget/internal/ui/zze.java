package com.google.android.libraries.places.widget.internal.ui;

import androidx.lifecycle.Observer;
import com.google.android.libraries.places.internal.zzhl;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zze implements Observer {
    public final /* synthetic */ AutocompleteImplFragment zza;

    public /* synthetic */ zze(AutocompleteImplFragment autocompleteImplFragment) {
        this.zza = autocompleteImplFragment;
    }

    public final void onChanged(Object obj) {
        this.zza.zzg((zzhl) obj);
    }
}
