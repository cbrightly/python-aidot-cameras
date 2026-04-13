package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzgk {
    /* access modifiers changed from: private */
    public static Map<String, CapabilityInfo> zza(List<zzah> list) {
        HashMap hashMap = new HashMap();
        if (list != null) {
            for (zzah next : list) {
                hashMap.put(next.getName(), new zzw(next));
            }
        }
        return hashMap;
    }
}
