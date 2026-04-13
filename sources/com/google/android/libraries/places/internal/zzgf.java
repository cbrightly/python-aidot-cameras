package com.google.android.libraries.places.internal;

import android.content.Context;
import com.android.volley.j;
import com.android.volley.toolbox.r;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.net.PlacesClient;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgf implements zzgi {
    private final zzgk zza;
    private final Context zzb;
    private final zzgr zzc;
    private final zzgf zzd = this;
    private final zzajg zze;

    /* synthetic */ zzgf(Context context, zzgk zzgk, zzgr zzgr, zzge zzge) {
        this.zza = zzgk;
        this.zzb = context;
        this.zzc = zzgr;
        this.zze = zzajf.zza(zzcp.zza());
    }

    private final zzdf zzb() {
        return zzdg.zza(new zzgv(this.zzb), this.zzc, this.zza);
    }

    public final PlacesClient zza() {
        zzgk zzgk = this.zza;
        zzgx zzgx = new zzgx(this.zzb);
        Context applicationContext = this.zzb.getApplicationContext();
        zzaje.zza(applicationContext);
        j a = r.a(applicationContext);
        zzaje.zza(a);
        zzdn zza2 = zzdo.zza(a, new zzfb());
        Context applicationContext2 = this.zzb.getApplicationContext();
        zzaje.zza(applicationContext2);
        j a2 = r.a(applicationContext2);
        zzaje.zza(a2);
        zzfm zza3 = zzfn.zza(zzgk, zzgx, zza2, zzdu.zza(a2), zzb(), (zzcn) this.zze.zzb(), zzeo.zza(), zzes.zza(zzfq.zza()), zzew.zza(), zzfa.zza(zzfq.zza()));
        Context applicationContext3 = this.zzb.getApplicationContext();
        zzaje.zza(applicationContext3);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext3);
        zzaje.zza(fusedLocationProviderClient);
        zzcy zza4 = zzcz.zza(fusedLocationProviderClient, new zzgb(new zzfx()));
        Context applicationContext4 = this.zzb.getApplicationContext();
        zzaje.zza(applicationContext4);
        return zzeh.zza(zza3, zza4, zzde.zza(applicationContext4, (zzcn) this.zze.zzb()), zzb(), (zzcn) this.zze.zzb());
    }
}
