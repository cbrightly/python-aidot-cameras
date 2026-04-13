package com.google.firebase.analytics.connector;

import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final /* synthetic */ class zzb implements EventHandler {
    public static final /* synthetic */ zzb zza = new zzb();

    private /* synthetic */ zzb() {
    }

    public final void handle(Event event) {
        AnalyticsConnectorImpl.zza(event);
    }
}
