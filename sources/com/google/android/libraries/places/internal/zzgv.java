package com.google.android.libraries.places.internal;

import android.content.Context;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.runtime.TransportRuntime;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgv {
    private final Transport zza = TransportRuntime.getInstance().newFactory("cct").getTransport("LE", zznf.class, zzgu.zza);

    public zzgv(Context context) {
        TransportRuntime.initialize(context.getApplicationContext());
    }

    public final void zza(zznf zznf) {
        this.zza.send(Event.ofData(zznf));
    }
}
