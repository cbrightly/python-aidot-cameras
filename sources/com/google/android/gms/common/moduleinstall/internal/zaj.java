package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final /* synthetic */ class zaj implements RemoteCall {
    public final /* synthetic */ zay zaa;
    public final /* synthetic */ zaab zab;

    public /* synthetic */ zaj(zay zay, zaab zaab) {
        this.zaa = zay;
        this.zab = zaab;
    }

    public final void accept(Object obj, Object obj2) {
        zay zay = this.zaa;
        zaab zaab = this.zab;
        ((zaf) ((zaz) obj).getService()).zai(new zav(zay, (TaskCompletionSource) obj2), zaab);
    }
}
