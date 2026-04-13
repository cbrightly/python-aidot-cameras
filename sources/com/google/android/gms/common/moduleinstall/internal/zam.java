package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final /* synthetic */ class zam implements RemoteCall {
    public final /* synthetic */ zay zaa;
    public final /* synthetic */ ApiFeatureRequest zab;

    public /* synthetic */ zam(zay zay, ApiFeatureRequest apiFeatureRequest) {
        this.zaa = zay;
        this.zab = apiFeatureRequest;
    }

    public final void accept(Object obj, Object obj2) {
        zay zay = this.zaa;
        ApiFeatureRequest apiFeatureRequest = this.zab;
        ((zaf) ((zaz) obj).getService()).zaf(new zaw(zay, (TaskCompletionSource) obj2), apiFeatureRequest);
    }
}
