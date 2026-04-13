package com.google.android.libraries.places.internal;

import com.android.volley.k;
import com.android.volley.toolbox.m;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdm extends m {
    final /* synthetic */ Map zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdm(zzdn zzdn, int i, String str, JSONObject jSONObject, k.b bVar, k.a aVar, Map map) {
        super(0, str, (JSONObject) null, bVar, aVar);
        this.zza = map;
    }

    public final Map getHeaders() {
        return this.zza;
    }
}
