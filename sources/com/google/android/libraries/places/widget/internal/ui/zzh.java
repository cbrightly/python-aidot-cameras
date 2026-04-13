package com.google.android.libraries.places.widget.internal.ui;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.internal.zzcn;
import com.google.android.libraries.places.internal.zzcq;
import com.google.android.libraries.places.internal.zzgq;
import com.google.android.libraries.places.internal.zzgr;
import com.google.android.libraries.places.internal.zzgv;
import com.google.android.libraries.places.internal.zzhj;
import com.google.android.libraries.places.internal.zzih;
import com.google.android.libraries.places.internal.zzii;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzh extends FragmentFactory {
    private final int zza;
    private final PlacesClient zzb;
    private final zzhj zzc;
    private final zzih zzd;
    private final zzcn zze = new zzcq();

    public zzh(@LayoutRes int i, Context context, zzhj zzhj) {
        this.zza = i;
        Context applicationContext = context.getApplicationContext();
        zzgq zzd2 = zzgr.zzd(applicationContext);
        zzd2.zzd(2);
        zzgr zze2 = zzd2.zze();
        zzgv zzgv = new zzgv(applicationContext);
        this.zzb = Places.zza(applicationContext, zze2);
        this.zzc = zzhj;
        this.zzd = new zzii(zzgv, zze2);
    }

    public final Fragment instantiate(ClassLoader classLoader, String str) {
        if (FragmentFactory.loadFragmentClass(classLoader, str) == AutocompleteImplFragment.class) {
            return new AutocompleteImplFragment(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }
        return super.instantiate(classLoader, str);
    }
}
