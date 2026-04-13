package com.google.android.libraries.places.internal;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.k;
import com.android.volley.toolbox.l;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzds extends l {
    final /* synthetic */ Map zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzds(zzdt zzdt, String str, k.b bVar, int i, int i2, ImageView.ScaleType scaleType, Bitmap.Config config, k.a aVar, Map map) {
        super(str, bVar, 0, 0, scaleType, config, aVar);
        this.zza = map;
    }

    public final Map getHeaders() {
        return this.zza;
    }
}
