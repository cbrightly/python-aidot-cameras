package com.google.android.libraries.places.widget;

import android.view.View;
import android.widget.EditText;
import androidx.lifecycle.Observer;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzh implements Observer {
    public final /* synthetic */ AutocompleteSupportFragment zza;
    public final /* synthetic */ EditText zzb;
    public final /* synthetic */ View zzc;

    public /* synthetic */ zzh(AutocompleteSupportFragment autocompleteSupportFragment, EditText editText, View view) {
        this.zza = autocompleteSupportFragment;
        this.zzb = editText;
        this.zzc = view;
    }

    public final void onChanged(Object obj) {
        this.zza.zzc(this.zzb, this.zzc, (CharSequence) obj);
    }
}
