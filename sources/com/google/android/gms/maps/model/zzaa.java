package com.google.android.gms.maps.model;

import android.os.RemoteException;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.maps.zzam;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzaa implements TileProvider {
    final /* synthetic */ TileOverlayOptions zza;
    private final zzam zzb;

    zzaa(TileOverlayOptions tileOverlayOptions) {
        this.zza = tileOverlayOptions;
        this.zzb = tileOverlayOptions.zza;
    }

    @Nullable
    public final Tile getTile(int i, int i2, int i3) {
        try {
            return this.zzb.zzb(i, i2, i3);
        } catch (RemoteException e) {
            return null;
        }
    }
}
