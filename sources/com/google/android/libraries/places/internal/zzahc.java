package com.google.android.libraries.places.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahc {
    zzahc() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzahb zzahb = (zzahb) obj;
        zzaha zzaha = (zzaha) obj2;
        if (zzahb.isEmpty()) {
            return 0;
        }
        Iterator it = zzahb.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }
}
