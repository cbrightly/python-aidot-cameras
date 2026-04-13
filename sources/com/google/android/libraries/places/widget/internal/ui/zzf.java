package com.google.android.libraries.places.widget.internal.ui;

import androidx.activity.OnBackPressedCallback;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzf extends OnBackPressedCallback {
    final /* synthetic */ AutocompleteImplFragment zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzf(AutocompleteImplFragment autocompleteImplFragment, boolean z) {
        super(true);
        this.zza = autocompleteImplFragment;
    }

    public final void handleOnBackPressed() {
        this.zza.zze.zzj();
    }
}
