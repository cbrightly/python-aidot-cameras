package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.NodeApi;
import java.util.ArrayList;
import java.util.List;

public final class zzgu extends zzgm<NodeApi.GetConnectedNodesResult> {
    public zzgu(BaseImplementation.ResultHolder<NodeApi.GetConnectedNodesResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzea zzea) {
        ArrayList arrayList = new ArrayList();
        List<zzfo> list = zzea.zzdx;
        if (list != null) {
            arrayList.addAll(list);
        }
        zza(new zzfj(zzgd.zzb(zzea.statusCode), arrayList));
    }
}
